package com.Covertify.interceptor;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.model_objects.specification.User;

public class signinInterceptor extends HandlerInterceptorAdapter{
	String clientId = "dcf0db8ebbe842028405deca41bb038b"; // Your client id
	String clientSecret = "9592ddfe70634b65b9771c37a3f816af";
    URI redirect_uri = SpotifyHttpManager.makeUri("http://localhost:8080/Covertify/callback"); 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("sign in prehandle");
		
		User currUser = (User)  request.getSession().getAttribute("user");
	
		System.out.println("request.getQueryString()"+ request.getQueryString());
		if ( request.getQueryString().contains("error=access_denied")) {
			System.out.println("URI"+ request.getQueryString().contains("error=access_denied"));
			response.sendRedirect("http://localhost:8080/Covertify/");
			return false;
		}
		
		
		
	
		return true;
	}

	
	
	

}
