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
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.User;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;

@Controller
@SessionAttributes("AlbumCoverURLs")
public class SpotifyAPIcontroller {
	
	String clientId = "dcf0db8ebbe842028405deca41bb038b"; // Your client id
	String clientSecret = "9592ddfe70634b65b9771c37a3f816af";
    URI redirect_uri = SpotifyHttpManager.makeUri("http://localhost:8080/Covertify/callback"); // Your redirect uri
    SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirect_uri)
            .build();
    
    @RequestMapping("/callback")
    public ModelAndView signin(HttpServletRequest request,  HttpServletResponse response) throws ParseException, SpotifyWebApiException, IOException{
       	System.out.println("request.getQueryString(): " + request.getQueryString());
        

       String code = request.getParameter("code");

       System.out.println("code: " + code);
   
       // TODO: these 2 lines can only be triggered once
       AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
       	          .build();
       AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
       
       // Set access and refresh token for further "spotifyApi" object usage
       spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
       System.out.println("getAccessToken()+++: " + authorizationCodeCredentials.getAccessToken());
       spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
       System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
       System.out.println("");
       
    // get current user profile
       GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile()
       	    .build();
       User user = getCurrentUsersProfileRequest.execute();
       System.out.println("Display name: " + user.getDisplayName());
       
       System.out.println("User id+++++++++++++++++++++++++=" + user.getId());
       
       Cookie cookie = new Cookie("userName", URLEncoder.encode(user.getDisplayName(), "UTF-8"));
       Cookie cookie2 = new Cookie("userImage", user.getImages()[0].getUrl());
       response.addCookie(cookie);
       response.addCookie(cookie2);

       return new ModelAndView("mainPage");
    }
	
    
    
    
    @PostMapping("spotify")
    public void spotifysign(HttpServletRequest request, HttpServletResponse response) throws ParseException, SpotifyWebApiException, IOException {
		RandomString rString = new RandomString();
		String state = rString.getAlphaNumericString(16);
		String scope= "streaming user-read-private user-read-email user-read-playback-state playlist-modify-public";  

	   AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
	     .build();
	 
	   URI uri = authorizationCodeUriRequest.execute();
	   System.out.println("URI: " + uri.toString());

       try {
    	   response.sendRedirect(uri.toString());
       } catch (IOException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
       }
    }
    
    
    
    
    @GetMapping("search")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws ParseException, SpotifyWebApiException, IOException {
 
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
		modelAndView.setViewName("mainPage");
		
        return modelAndView;
    }
    
    
    @GetMapping("add")
    public void addAlbum(HttpServletRequest request, HttpServletResponse response) throws ParseException, SpotifyWebApiException, IOException {
 
        String q = (String) request.getParameter("album");
        System.out.print("album url :" + q);
		
    }
    
}

























    

