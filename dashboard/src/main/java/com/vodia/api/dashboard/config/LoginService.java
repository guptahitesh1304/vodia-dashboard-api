package com.vodia.api.dashboard.config;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService {

	//private static final String API_LOGIN_URL = "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/system/session";
	private static final Logger log = LoggerFactory.getLogger(LoginService.class);
	
	//@Autowired
	//private RestTemplate restTemplate;
	private MyTrustManager myTrustManager;
	
	public HashMap<String, String> VodiaRestAPILogin(HttpServletRequest request, String username, String password, String dn) {
		
		String un = username ;
		String pwd = password;
		String auth_value = un + " " + pwd;
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", "auth");
		params.put("value", auth_value);
	    //params.put("value", "reportx 4fcd1ee9afb05ccc92c3f223d0df3b75");
		log.info("Request ---"+params.toString());
		String url = URLConfig.getURL("API_LOGIN_URL");
		String API_LOGIN_URL = MessageFormat.format(url, dn);
		log.info("API_LOGIN_URL"+API_LOGIN_URL);
		
		// to disable DNS check which is getting failed while validating cert
				myTrustManager.disableSSL();
		
		RestTemplate restTemplate = new RestTemplate();
	
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity<Map<String, String>> entity = new HttpEntity<Map<String, String>>(params,headers);
	    String response = restTemplate.exchange(
	    		API_LOGIN_URL, HttpMethod.PUT, entity, String.class).getBody();
	    
	    String API_TOKEN = response.replaceAll("[^a-zA-Z0-9]", " ");
	    
	    log.info("API_TOKEN"+API_TOKEN);
	    request.getSession().setAttribute("token", API_TOKEN);
	    request.getSession().setAttribute("dn", dn);
	    String set_cookie = headers.getFirst(HttpHeaders.SET_COOKIE);
	    HashMap<String, String> map = new HashMap<>();
	    map.put("status", "ok");
	    map.put("token", API_TOKEN);
	     
	    log.info("Response: " + response.toString() + "\n");
	    log.info("Set-Cookie: " + set_cookie + "\n");
	    log.info("get Token from session: " +  request.getSession().getAttribute("token") + "\n");
	    log.info("********* FINISH *******");
	      
	    return map;
		
	}
	
	public String getMd5(String input) 
    { 
        try { 
  
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    } 
	
}
