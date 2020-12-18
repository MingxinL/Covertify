package com.Covertify.controller;

import org.apache.hc.core5.http.ParseException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.Covertify.dao.AlbumDAO;
import com.Covertify.dao.CustomerDAO;
import com.Covertify.dao.DAO;
import com.Covertify.hibernate.entity.Album;
import com.Covertify.hibernate.entity.Customer;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.special.AlbumSimplifiedSpecial;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import com.wrapper.spotify.model_objects.specification.User;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.requests.data.albums.GetAlbumsTracksRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import com.wrapper.spotify.requests.data.search.simplified.special.SearchAlbumsSpecialRequest;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import antlr.collections.List;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

@Controller


public class SpotifyAPIcontroller {

	String clientId = "dcf0db8ebbe842028405deca41bb038b"; // Your client id
	String clientSecret = "9592ddfe70634b65b9771c37a3f816af";
    URI redirect_uri = SpotifyHttpManager.makeUri("http://localhost:8080/Covertify/callback"); // Your redirect uri
    
    @RequestMapping("/callback")
    public ModelAndView signin(HttpServletRequest request,  HttpServletResponse response) throws ParseException, SpotifyWebApiException, IOException{
    	HttpSession session=request.getSession();
    	System.out.println("Session Attr (code): " + session.getAttribute("code"));
    	System.out.println("Session Attr (user): " + session.getAttribute("user"));
    	System.out.println("Session Attr (spotifyApi): " + session.getAttribute("spotifyApi"));

        User currUser = (User) session.getAttribute("user");
 
       CustomerDAO cDao = new CustomerDAO();
       Customer tempCustomer = null;
       if (!cDao.Exist(currUser.getId())){
    	   System.out.println("!cDao.Exist(currUser.getId())");
        tempCustomer = new Customer(currUser.getId(), currUser.getDisplayName(), currUser.getImages()[0].getUrl());
        tempCustomer.setRole("auth");
        
        cDao.saveCustomer(tempCustomer);
       } 
       	String role = cDao.getCustomers(currUser.getId()).getRole();
       	System.out.println("role"+ role);
      
       	session.setAttribute("role", role);
       
       	//to do!!!!!!!!!
       //	session.setAttribute("AuthList",cDao.getAlbums("0qtsgtarmv1zbif195n1df6tu"));
   
       	session.setAttribute("AuthList",cDao.getAlbums("0qtsgtarmv1zbif195n1df6tu"));
       return new ModelAndView("mainPage");
    }
	

//    @ModelAttribute("spotify") SpotifyApi spotifyApi
    
    @PostMapping("spotify")
    public void spotifysign(HttpServletRequest request, HttpServletResponse response) throws ParseException, SpotifyWebApiException, IOException {
//		RandomString rString = new RandomString();
//		String state = rString.getAlphaNumericString(16);
//		String scope= "streaming user-read-private user-read-email user-read-playback-state playlist-modify-public";  
//    	HttpSession session=request.getSession();
//    	session.invalidate();
    	System.out.println("spotify already exists.");
    	HttpSession session=request.getSession(false);
    	
    	if (session!=null) {
    		session.invalidate();
    	} 
    	session = request.getSession();
    	
		SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirect_uri)
                .build();
		
		System.out.println("spotifyApi 112" + spotifyApi);
		session.setAttribute("spotifyApi", spotifyApi);
    	
	   AuthorizationCodeUriRequest authorizationCodeUriRequest = ((SpotifyApi) request.getSession().getAttribute("spotifyApi")).authorizationCodeUri()
			   .show_dialog(true)
			   .build();
	 
	   URI uri = authorizationCodeUriRequest.execute();
//	   System.out.println("URI: " + uri.toString());
	   
       try {
    	   response.sendRedirect(uri.toString());
       } catch (IOException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
       }
    }
    
    
    
    @GetMapping("search")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws ParseException, SpotifyWebApiException, IOException {

    	HttpSession session=request.getSession();
    	SpotifyApi spotifyApi = (SpotifyApi) session.getAttribute("spotifyApi");
        final String q = request.getParameter("search");
        
        if (request.getAttribute("unsafe_request") == "true") {
        	return new ModelAndView("securityerror");
        }
       
        ModelAndView modelAndView = new ModelAndView();
   //     if (q != "") {
	       // final SearchAlbumsRequest searchAlbumsRequest = spotifyApi.searchAlbums(q)
        SearchAlbumsSpecialRequest searchAlbumsRequest = spotifyApi.searchAlbumsSpecial(q)
	        		
	//                  .market(CountryCode.SE)
	//                  .limit(10)
	//                  .offset(0)
	//                  .includeExternal("audio")
	            .build();
//	        System.out.println("searchAlbumsRequest.toString(): "+ searchAlbumsRequest.toString());
	    Paging<AlbumSimplifiedSpecial> albumSimplifiedPaging = searchAlbumsRequest.execute();
	
//	        System.out.println("Search Result: " + albumSimplifiedPaging.toString());
	        
	     ArrayList<String> CoverURLs = new ArrayList<String>();
	        ArrayList<Album> albumList = new ArrayList<>();
	       
	        for (AlbumSimplifiedSpecial item : albumSimplifiedPaging.getItems()) {
	        	// add 640 size cover image item
	        	CoverURLs.add(item.getImages()[0].getUrl());
	        	Album albumItem = new Album();
	        	albumItem.setId(item.getId());
	        	albumItem.setImage(item.getImages()[0].getUrl());
	        	albumItem.setName(item.getName());
	        	albumList.add(albumItem);
	        	GetAlbumsTracksRequest getAlbumsTracksRequest = spotifyApi.getAlbumsTracks(item.getId())
//        	          .limit(1)
//	        	          .offset(0)
//	        	          .market(CountryCode.SE)
	        	    .build();
	        	Paging<TrackSimplified> trackSimplifiedPaging = getAlbumsTracksRequest.execute();
	        	System.out.println("trackSimplifiedPage"+trackSimplifiedPaging.getItems()[0].getPreviewUrl());
	        	albumItem.setPreUrl(trackSimplifiedPaging.getItems()[0].getPreviewUrl());
	        	
	        }
	       
			modelAndView.addObject("AlbumCoverURLs", CoverURLs);
			modelAndView.addObject("albumList",albumList);
			
     //   }
        modelAndView.setViewName("mainPage");
        return modelAndView;
    }
    
    
    
    @GetMapping("/ChoiceOfCovertify")
    public ModelAndView AddAlbumsintoCovertify(HttpServletRequest request){
 	
    	HttpSession session = request.getSession();
    	User user = (User) session.getAttribute("user");
//    	String customerId = "0qtsgtarmv1zbif195n1df6tu"; // Liang
//    	String customerId = "21z5ocxxaci7ehx26cob7lhey"; // Jiao
    	String customerId = user.getId();
    	
    	ModelAndView modelAndView = new ModelAndView();
    	CustomerDAO cdao = new CustomerDAO();
   
    	modelAndView.addObject("AuthList",cdao.getAlbums(customerId));
    	modelAndView.setViewName( "mainPage");
    	//DAO.close();
    	
    	return modelAndView;
    }
    
    
    
    @GetMapping("album/add")
    public String addAlbum(HttpServletRequest request, @RequestParam("albumId") String theId, @RequestParam("albumName") String theName, @RequestParam("albumImage") String theImage, @RequestParam("albumPreUrl") String thePreUrl){
 	
    	HttpSession session = request.getSession();
    	User user = (User) session.getAttribute("user");
    	
    	AlbumDAO adao = new AlbumDAO();
    	  CustomerDAO cdao = new CustomerDAO();
    	  
    	  Album tempAlbum = null;

    	  if (!adao.Exist(theId)) {
    		 System.out.println("!adao.Exist(theId)");
    	   tempAlbum = new Album(theId,theName,theImage,0,thePreUrl);
    	   adao.saveAlbums(tempAlbum);
    	  }
    	  
    	  // get the customers
    	  //String customerId = "0qtsgtarmv1zbif195n1df6tu"; // Liang
//    	  String customerId = "21z5ocxxaci7ehx26cob7lhey"; // Jiao
    	  String customerId = user.getId();
    	  
    	  Customer tempCustomer = cdao.getCustomers(customerId);
    	  
    	  // check if the customers have add album before        
    	  boolean hasAddBefore = false;
    	  System.out.println("row 245 " + cdao.getAlbums(customerId));
    	  //System.out.println("\t\tREAD--tempCustomer.getAlbums()\n" + tempCustomer.getAlbums());
    	  for(Album album : cdao.getAlbums(customerId)) {
    		  System.out.println("album.getId()--" + album.getId());
    		  System.out.println("        theId--" + theId);
    	   if (album.getId()==theId) {
	    	    hasAddBefore = true;
	    	    break;
    	   }
    	  }
    	  if (!hasAddBefore) {
    	   // add customers to the album
    		System.out.println("!hasAddBefore");
    	   tempAlbum = adao.getAlbums(theId);
    	   //tempAlbum.addCustomer(tempCustomer);
    	   adao.addCustomer(tempAlbum, tempCustomer);
    	   
    	   adao.AddTime(tempAlbum); 
    	  } 
    	  
    	  //DAO.close();
    	  
//    	  // if user is auth, will update the main page 
//    	  System.out.println("253 role "+session.getAttribute("role") );
//    	  System.out.println("254 role "+session.getAttribute("role").getClass());
    	  String role = (String)session.getAttribute("role");
    	  System.out.println(role.contains("auth"));
//    	  if (role.contains("auth")) {
    	  System.out.println("role"+session.getAttribute("role") );
    
    	 // to do !!!!!!
    	  session.setAttribute("AuthList",cdao.getAlbums("0qtsgtarmv1zbif195n1df6tu"));
   
    	 // System.out.println("auth's album" + session.getAttribute("AuthList"));
   	//  }

//    	     // send over to our form
    	     return "addDBsuccess";
    }
//    
//    
    @GetMapping("album/readAlbums") 
    public ModelAndView readAlbum(HttpServletRequest request){
    	HttpSession session = request.getSession();
    	User user = (User) session.getAttribute("user");
//    	String customerId = "0qtsgtarmv1zbif195n1df6tu"; // Liang
//    	String customerId = "21z5ocxxaci7ehx26cob7lhey"; // Jiao
    	String customerId = user.getId();
    	ModelAndView modelAndView = new ModelAndView();
    	CustomerDAO cdao = new CustomerDAO();
   
    	modelAndView.addObject("albumList",cdao.getAlbums(customerId));
    		System.out.println("\t\tmodel contains albumList:" + modelAndView.getModel());
    		System.out.println("\t\talbumList:" + modelAndView.getModel().get("albumList"));
    	modelAndView.setViewName( "CustomerAlbum");
    	//DAO.close();
    	return modelAndView;
    }
//    
//    
//    
    @GetMapping("album/delete") 
    public void deleteAlbum(@RequestParam("albumId") String theId, HttpServletRequest request, HttpServletResponse response) throws IOException{
    	HttpSession session = request.getSession();
    	User user = (User) session.getAttribute("user");
//    	String customerId = "0qtsgtarmv1zbif195n1df6tu"; // Liang
//    	String customerId = "21z5ocxxaci7ehx26cob7lhey"; // Jiao
    	String customerId = user.getId();
    	
    	CustomerDAO cdao = new CustomerDAO();
    	AlbumDAO adao = new AlbumDAO();
    	Customer customer = cdao.getCustomers(customerId);
    	Album  album = adao.getAlbums(theId);
    	System.out.println("318 line" + customer.getName());
    	System.out.println("319 line" + album.getName());
    	
    	
    	adao.deleteAlbums(album, customer);
    	
    	System.out.println("line 321  ");
 
    	if (album.getTime() <= 1) {
    		adao.deleteAlbum(theId);
    	} else {
    		adao.ReduceTime(theId);
    	}
    	String role = (String)session.getAttribute("role");
    	//if (role.contains("auth")) {
    		 System.out.println("role"+session.getAttribute("role") );
    		
    	  session.setAttribute("AuthList",cdao.getAlbums("0qtsgtarmv1zbif195n1df6tu"));
    	  System.out.println("auth's album" + session.getAttribute("AuthList"));
    //	  }
//    	
    	//DAO.close();
    	response.sendRedirect("http://localhost:8080/Covertify/album/readAlbums");
    	
   
  //  	request.setAttribute("albumList",cdao.getAlbums(customerId));
//		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/CustomerAlbum.jsp");
//		
//    	//response.forward("http://localhost:8080/Covertify/album/readAlbums");
//		try {
//			rd.forward(request, response);
//		} catch (ServletException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
//    
//    
//    
//    @GetMapping("auth/delete") 
//    public String AuthdeleteAlbum(@RequestParam("albumId") String theId, HttpServletRequest request, HttpServletResponse response) throws IOException{
//    	HttpSession session = request.getSession();
//    	User user = (User) session.getAttribute("user");
////    	String customerId = "0qtsgtarmv1zbif195n1df6tu"; // Liang
////    	String customerId = "21z5ocxxaci7ehx26cob7lhey"; // Jiao
//    	String customerId = user.getId();
//    	
//    	CustomerDAO cdao = new CustomerDAO();
//    	AlbumDAO adao = new AlbumDAO();
//    	cdao.deleteAlbums (customerId, theId);
//    	
//    	//DAO.close();
// 
//    	if (adao.getAlbums(theId).getTime() <= 1) {
//    		adao.deleteAlbum(theId);
//    	} else {
//    		adao.ReduceTime(theId);
//    	}
//    	String role = (String)session.getAttribute("role");
//    	 
//    	System.out.println("role"+session.getAttribute("role") );
//    		
//    	session.setAttribute("AuthList",cdao.getAlbums("0qtsgtarmv1zbif195n1df6tu"));
//    	System.out.println("auth's album" + session.getAttribute("AuthList"));
//    	 
//    	
//    	//DAO.close();
//    	return "mainPage";
//    	
//   
//
//    }
    
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request,  HttpServletResponse response, HttpSession httpsession) {
		
    	try {
   
    		httpsession.invalidate();
			response.sendRedirect("http://localhost:8080/Covertify/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    @RequestMapping("/")
    public String init(HttpServletRequest request,  HttpServletResponse response) {
		
    	return "init";
    	
    }
    
    
    
}
























    

