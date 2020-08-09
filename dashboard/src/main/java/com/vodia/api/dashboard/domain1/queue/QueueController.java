package com.vodia.api.dashboard.domain1.queue;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


@RestController
public class QueueController {

	private static final Logger log = LoggerFactory.getLogger(QueueController.class);
	
	@Autowired
	private QueueService qs;
	
	@GetMapping("/getAllQueuesName")
	private List<ACD_Queue_List> getAllQueuesName(HttpServletRequest request) {
		
		return qs.getAllQueuesName(request);
		   
		/*
		 * final String url =
		 * "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/acds";
		 * // LoginController lc = new LoginController(); //String token =
		 * lc.VodiaRestAPILogin(); String token = auth_token;
		 * log.info("TOKEN AFTER LOGIN -"+token); RestTemplate restTemplate = new
		 * RestTemplate(); HttpHeaders requestHeaders = new HttpHeaders();
		 * requestHeaders.add("Cookie", "session=" + token);
		 * 
		 * 
		 * HttpEntity requestEntity = new HttpEntity(null, requestHeaders);
		 * 
		 * ResponseEntity<QueuesDetails[]> response = restTemplate.exchange(url,
		 * HttpMethod.GET, requestEntity, QueuesDetails[].class); List<QueuesDetails>
		 * acd_cdr_list = Arrays.asList(response.getBody());
		 * 
		 * return acd_cdr_list;
		 */ 
		   
		}
	
	@GetMapping("/getQueuePerformanceTable")
	private List<QueuePerformanceTable> getQueuePerformanceTable(HttpServletRequest request) {
		
		log.info("in contoller");
		
		return qs.getQueuePerformanceTable(request);
		
	}
	
	@GetMapping("/getACDByQueue/{qid}")
	private ACD_WallBoard_QueueWise getACDByQueue(HttpServletRequest request,
			@PathVariable("qid") String qid) {

		return qs.getACDByQueue(request, qid);

	}
	
	@GetMapping("/getACDForAllQueue")
	private List<ACD_WallBoard_QueueWise> getACDForAllQueue(HttpServletRequest request) {
		//List<ACD_WallBoard_QueueWise> acd_wallboard_queue_list;
		
		log.info("calling getACDForAllQueue");
		return qs.getACDForAllQueue(request);
		
	}
}
