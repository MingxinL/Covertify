package com.Covertify.controller;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Image;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchAlbumsRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

@Controller
@SessionAttributes("AlbumCoverURLs")
public class SpotifyAPIcontroller {
	
	String clientId = "dcf0db8ebbe842028405deca41bb038b"; // Your client id
	String clientSecret = "9592ddfe70634b65b9771c37a3f816af";
	
    @RequestMapping("/callback")
    public ModelAndView signin(){
    	return new ModelAndView("callback");
    }
	
    @PostMapping("spotify")
    public void spotifysign(HttpServletRequest request, HttpServletResponse response) {

//        String client_id = "dcf0db8ebbe842028405deca41bb038b"; // Your client id
//        String client_secret = "9592ddfe70634b65b9771c37a3f816af"; // Your secret
//        String redirect_uri = "http://localhost:8080/Covertify/callback"; // Your redirect uri
//        RandomString rString = new RandomString();
//        String state = rString.getAlphaNumericString(16);
//        String scope= "streaming user-read-private user-read-email user-read-playback-state playlist-modify-public";  
//        try {
//			response.sendRedirect("https://accounts.spotify.com/authorize?" + "response_type=code&client_id=" + client_id +  "&scope=" + scope + "&redirect_uri=" + redirect_uri + "&state=" + state);
//  
//        } catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	RandomString rString = new RandomString();
        String state = rString.getAlphaNumericString(16);
        String scope= "streaming user-read-private user-read-email user-read-playback-state playlist-modify-public";  
        
        
        
        URI redirect_uri = SpotifyHttpManager.makeUri("http://localhost:8080/Covertify/callback"); // Your redirect uri
       final SpotifyApi spotifyApi = new SpotifyApi.Builder()
         .setClientId(clientId)
         .setClientSecret(clientSecret)
         .setRedirectUri(redirect_uri)
         .build();
       final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
         .build();
     
       final URI uri = authorizationCodeUriRequest.execute();
       System.out.println("URI: " + uri.toString());
       try {
	//  response.sendRedirect("https://accounts.spotify.com/authorize?" + "response_type=code&client_id=" + client_id +  "&scope=" + scope + "&redirect_uri=" + redirect_uri + "&state=" + state);
    	   response.sendRedirect(uri.toString());
       } catch (IOException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
       }
    }
    
    
    @GetMapping("search")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws ParseException, SpotifyWebApiException, IOException {
//    	String code = (String) request.getAttribute("code");
//    	System.out.print(code);
//        String client_id = "dcf0db8ebbe842028405deca41bb038b"; // Your client id
//        String client_secret = "9592ddfe70634b65b9771c37a3f816af"; // Your secret
//      //     String redirect_uri = "http://localhost:8080/hw4part6/callback/"; // Your redirect uri
////        String client_id = "9142a80724784eeb80c1bd52b2b36aa3"; // Your client id
////        String client_secret = "69a6391efe9f42ff910725544bfb54a5"; // Your secret
//        String redirect_uri = "http://localhost:8080/Covertify/callback"; // Your redirect uri
//        RandomString rString = new RandomString();
//        String state = rString.getAlphaNumericString(16);
//        String scope= "streaming user-read-private user-read-email user-read-playback-state playlist-modify-public";  
//        try {
//			response.sendRedirect("https://api.spotify.com/v1/search/ania+bowra&type=artist&token="+code);
//  
//        } catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	
        String code = null;
        Cookie[] cookies = (Cookie[]) request.getCookies();
        for (int i = 0; i < cookies.length ; i++) {
         Cookie c = cookies[i];
         if (c.getName().equals("Spotifytoken"))  {
          code = c.getValue();
          System.out.println("code: " + code);
          
         }
          
        }
        
        
        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
             .setClientId(clientId)
             .setClientSecret(clientSecret)
             .build();
        final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
             .build();
        final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
        spotifyApi.setAccessToken(clientCredentials.getAccessToken());
      
        final String q = request.getParameter("search");
        final SearchAlbumsRequest searchAlbumsRequest = spotifyApi.searchAlbums(q)
//                  .market(CountryCode.SE)
//                  .limit(10)
//                  .offset(0)
//                  .includeExternal("audio")
            .build();
        System.out.println("searchAlbumsRequest.toString(): "+ searchAlbumsRequest.toString());
        final Paging<AlbumSimplified> albumSimplifiedPaging = searchAlbumsRequest.execute();

        System.out.println("Search Result: " + albumSimplifiedPaging.toString());
        
        final ArrayList<String> CoverURLs = new ArrayList<String>();
        for (AlbumSimplified item : albumSimplifiedPaging.getItems()) {
        	// add 640 size cover image item
        	CoverURLs.add(item.getImages()[0].getUrl());
        }
        
        
       
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("AlbumCoverURLs", CoverURLs);
		modelAndView.setViewName("callback");
		
        return modelAndView;
    }
}

























    

