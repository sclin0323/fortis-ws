package com.hoyoung.test;

import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;


public class TestJwt {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIwMTEwMSIsImVtYWlsIjoiZGFubnlAbmN1dC5lZHUudHciLCJuYW1lIjoi6buD5Za757-UKDAxMTAxKSIsImRlcGFydG1lbnRJZCI6IjEyMDIiLCJkZXBhcnRtZW50TmFtZSI6Iue2sui3r-WPiuezu-e1seacjeWLmee1hCIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWUiOiIwMTEwMSIsImNsYWltcyI6WyJ1c2VyIl0sInN5c3RlbUtleSI6IjliNjM3ODliMjdhMTQ3YmZhOWY0Y2UzODgyYmQxM2YxIiwiaWRlbnRpdHlUeXBlIjoiMCIsImV4cCI6MTU5Njk2MDEyMiwiaXNzIjoibmN1dGFwaS5uY3V0LmVkdS50dyIsImF1ZCI6Im5jdXRhcGkifQ.30c6BMgJ6ikmP45nc1zsrZRtnwq8OdB9s03SmHVL-8U";

		
		
		DecodedJWT jwt = JWT.decode(token);
		
		Claim c1 = jwt.getClaim("name");
		
		System.out.println(c1.asString());
		
		/*
		Map<String, Claim> cmap = jwt.getClaims();
		String applicantName = cmap.get("name").asString();
		String applicantTitle = cmap.get("departmentName").asString();
	
		*/
		
	}

}
