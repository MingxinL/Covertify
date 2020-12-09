package com.Covertify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Controller
public class SpotifyAPIcontroller {
	
    @RequestMapping("/callback")
    public ModelAndView signin(){
    	return new ModelAndView("callback");
    }
	
    @PostMapping("spotify")
    public void spotifysign(HttpServletRequest request, HttpServletResponse response) {

        String client_id = "dcf0db8ebbe842028405deca41bb038b"; // Your client id
        String client_secret = "9592ddfe70634b65b9771c37a3f816af"; // Your secret
      //     String redirect_uri = "http://localhost:8080/hw4part6/callback/"; // Your redirect uri
//        String client_id = "9142a80724784eeb80c1bd52b2b36aa3"; // Your client id
//        String client_secret = "69a6391efe9f42ff910725544bfb54a5"; // Your secret
        String redirect_uri = "http://localhost:8080/Covertify/callback"; // Your redirect uri
        RandomString rString = new RandomString();
        String state = rString.getAlphaNumericString(16);
        String scope= "streaming user-read-private user-read-email user-read-playback-state playlist-modify-public";  
        try {
			response.sendRedirect("https://accounts.spotify.com/authorize?" + "response_type=code&client_id=" + client_id +  "&scope=" + scope + "&redirect_uri=" + redirect_uri + "&state=" + state);
  
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    @GetMapping("search")
    public void search(HttpServletRequest request, HttpServletResponse response) {
    	String code = (String) request.getAttribute("code");
    	System.out.print(code);
        String client_id = "dcf0db8ebbe842028405deca41bb038b"; // Your client id
        String client_secret = "9592ddfe70634b65b9771c37a3f816af"; // Your secret
      //     String redirect_uri = "http://localhost:8080/hw4part6/callback/"; // Your redirect uri
//        String client_id = "9142a80724784eeb80c1bd52b2b36aa3"; // Your client id
//        String client_secret = "69a6391efe9f42ff910725544bfb54a5"; // Your secret
        String redirect_uri = "http://localhost:8080/Covertify/callback"; // Your redirect uri
        RandomString rString = new RandomString();
        String state = rString.getAlphaNumericString(16);
        String scope= "streaming user-read-private user-read-email user-read-playback-state playlist-modify-public";  
        try {
			response.sendRedirect("https://api.spotify.com/v1/search/ania+bowra&type=artist&token="+code);
  
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
