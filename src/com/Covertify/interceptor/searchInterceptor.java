package com.Covertify.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wrapper.spotify.SpotifyApi;

public class searchInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("prehandle");
		//System.out.println(request.getPathInfo());
		String search = request.getParameter("search");
		System.out.println(search);
		 if(inputCheck(search)) {
		
			request.setAttribute("unsafe_request", "true");
		}
		 
	
		return true;
	}

	
	
	private boolean inputCheck(String value) {
		if (value != null) {
			return(value.matches("<script>(.*?)</script>")||value == "");
		}else {
			return false;
		}
		
	}
	

}
