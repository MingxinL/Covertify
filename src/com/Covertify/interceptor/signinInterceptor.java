package com.Covertify.interceptor;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.specification.User;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.data.search.simplified.special.SearchAlbumsSpecialRequest;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

public class signinInterceptor extends HandlerInterceptorAdapter{
	String clientId = "dcf0db8ebbe842028405deca41bb038b"; // Your client id
	String clientSecret = "9592ddfe70634b65b9771c37a3f816af";
    URI redirect_uri = SpotifyHttpManager.makeUri("http://localhost:8080/Covertify/callback"); 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("sign in prehandle");
		HttpSession session=request.getSession();
		String code = request.getParameter("code");
		System.out.println("Line 31 code: " + code);
		System.out.println("Line 32 request: " + request.getRequestURL());
		System.out.println("Line 33 attr code: " + request.getSession().getAttribute("code"));
		System.out.println("Line 34 access token: " + request.getSession().getAttribute("accessToken"));
		
		if (request.getSession().getAttribute("code") == null) {
			// normal situation: it is first time request /callback
			// use "code" once
			System.out.println("DEC_16 request.getParameter(\"code\"): " + code);
			if (code == null) {
				// something wrong, redirect to login
				// TODO: maybe need to display some error info in browser
				session.invalidate();
				response.sendRedirect("http://localhost:8080/Covertify/");
				
				return false;
			} else {
				session.setAttribute("code",code);
				SpotifyApi spotifyApi = (SpotifyApi) session.getAttribute("spotifyApi");
				// TODO: check spotifyApi
				
				AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
	          	          .build();
				AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
				
				spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
//    			System.out.println("getAccessToken()+++: " + authorizationCodeCredentials.getAccessToken());
				spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
//    			System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
    			System.out.println("");
    
			    // get current user profile
			    GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile()
			    	    .build();
			    
			    User user = getCurrentUsersProfileRequest.execute();
			    session.setAttribute("user", user);
			    session.setAttribute("accessToken", authorizationCodeCredentials.getAccessToken());
			    
			}
		} else {
			// other pages
			// access token expired
			
			// check user
			if (session.getAttribute("user") == null || session.getAttribute("spotifyApi") == null) {
				session.invalidate();
				response.sendRedirect("http://localhost:8080/Covertify/");
				return false;
			}
			
		}

		System.out.println("request.getQueryString()"+ request.getQueryString());
		if ( request.getQueryString()!=null && request.getQueryString().contains("error=access_denied")) {
			System.out.println("URI"+ request.getQueryString().contains("error=access_denied"));
			session.invalidate();
			response.sendRedirect("http://localhost:8080/Covertify/");
			return false;
		}
		
		
		
		
		return true;
	}

	
	
	

}
