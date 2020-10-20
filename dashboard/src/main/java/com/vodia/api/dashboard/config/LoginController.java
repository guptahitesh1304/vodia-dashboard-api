package com.vodia.api.dashboard.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.vodia.api.dashboard.Test.Quote;


@RestController
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	private static final String API_LOGIN_URL = "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/system/session";
	
	//@Autowired
	private MyTrustManager myTrustManager;
	
	@Autowired
	private LoginService ls;
	
	/*
	 * @SuppressWarnings("static-access")
	 * 
	 * @RequestMapping(value = "/login", method = {RequestMethod.GET,
	 * RequestMethod.PUT}) public String VodiaRestAPILogin() { RestTemplate template
	 * = new RestTemplate(); Credentials cred = new Credentials();
	 * cred.setName("auth");
	 * cred.setValue("reportx 4fcd1ee9afb05ccc92c3f223d0df3b75");
	 * 
	 * //HttpEntity<Credentials> request = new HttpEntity<>(cred); Map<String,
	 * String> params = new HashMap<String, String>(); params.put("name", "auth");
	 * params.put("value", "reportx 4fcd1ee9afb05ccc92c3f223d0df3b75");
	 * log.debug("Request ---"+params.toString()); //HttpEntity<String> response =
	 * template.exchange(API_LOGIN_URL, HttpMethod.PUT, params, String.class);
	 * myTrustManager.disableSSL(); RestTemplate restTemplate = new RestTemplate();
	 * restTemplate.put ( API_LOGIN_URL, params); Token token =
	 * restTemplate.getForObject(API_LOGIN_URL, Token.class);
	 * log.debug(token.getToken());
	 * 
	 * 
	 * 
	 * //HttpHeaders headers = response.getHeaders(); //String set_cookie =
	 * headers.getFirst(headers.SET_COOKIE);
	 * 
	 * //log.debug("Response: " + response.toString() + "\n");
	 * log.debug("Set-Cookie: " + token.getToken() + "\n");
	 * log.debug("********* FINISH *******"); //return response.toString(); return
	 * token.toString(); }
	 */
	
	@SuppressWarnings("static-access")
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/loginTest/{username}/{password}/{dn}", method = {RequestMethod.GET, RequestMethod.PUT})
	public HashMap<String, String> VodiaRestAPILogin(HttpServletRequest request, @PathVariable("username") String username, @PathVariable("password") String password) {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", "auth");
	    params.put("value", "reportx z5ler6ti7me8puzo6mg8");
		log.debug("Request ---"+params.toString());
		
		
		// to disable DNS check which is getting failed while validating cert
		myTrustManager.disableSSL();
		
		RestTemplate restTemplate = new RestTemplate();
	
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity<Map<String, String>> entity = new HttpEntity<Map<String, String>>(params,headers);
	    myTrustManager.disableSSL();
	    String response = restTemplate.exchange(
	    		API_LOGIN_URL, HttpMethod.PUT, entity, String.class).getBody();
	    
	    String API_TOKEN = response.replaceAll("[^a-zA-Z0-9]", " ");
	    
	    log.debug("API_TOKEN"+API_TOKEN);
	    request.getSession().setAttribute("token", API_TOKEN);
	    String set_cookie = headers.getFirst(headers.SET_COOKIE);
	    HashMap<String, String> map = new HashMap<>();
	    map.put("status", "ok");
	    map.put("token", API_TOKEN);
	     
	    System.out.println("Response: " + response.toString() + "\n");
	    System.out.println("Set-Cookie: " + set_cookie + "\n");
	    System.out.println("********* FINISH *******");
	      
	    return map;
}
	
	@SuppressWarnings("static-access")
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/login/{username}/{password}/{dn}", method = {RequestMethod.GET, RequestMethod.PUT})
	public HashMap<String, String> VodiaRestAPILogin(HttpServletRequest request, @PathVariable("username") String username, @PathVariable("password") String password, @PathVariable("dn") String dn) {
		
		log.debug("getting login service");
		return ls.VodiaRestAPILogin(request, username, password, dn);
		
}
	
}
