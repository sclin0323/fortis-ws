package com.hoyoung.test;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.hoyoung.fortis.python.PythonResponse;

public class TestSoap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestTemplate rt = new RestTemplate();
	
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl("http://eip.ncut.edu.tw/ldapService.do?wsdl")
				.queryParam("in0", "sessionId");
		
		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<Object> res = rt.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, Object.class);

	}

}
