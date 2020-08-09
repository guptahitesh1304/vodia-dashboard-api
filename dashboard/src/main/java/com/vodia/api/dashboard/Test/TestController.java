package com.vodia.api.dashboard.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.vodia.api.dashboard.domain1.queue.ACD_Queue_List;
import com.vodia.api.dashboard.domain1.queue.ACD_WallBoard_QueueWise;
import com.vodia.api.dashboard.domain1.queue.AGENTS;
import com.vodia.api.dashboard.domain1.queue.QueueACDS;
import com.vodia.api.dashboard.domain1.queue.QueuePerformanceTable;
import com.vodia.api.dashboard.domain1.queue.QueueService;
import com.vodia.api.dashboard.domain1.queue.QueuesDetails;

@RestController
public class TestController {
	
	private List<ACD_Queue_List> queueList = Arrays.asList(
			
			new ACD_Queue_List("1", "Customercare_Q"),
			new ACD_Queue_List("2", "Connecta_Support-Queue"),
			new ACD_Queue_List("3", "Connecta_Sales-Queue"),
			new ACD_Queue_List("4", "Service_Delivery_Queue"),
			new ACD_Queue_List("5", "Connecta_Sales-Queue"),
			new ACD_Queue_List("6", "Service_Delivery_Queue"),
			new ACD_Queue_List("7", "CC-Business-Q")
			);
	
	private List<QueuePerformanceTable> queuePerformanceTableList = Arrays.asList(
		
		new QueuePerformanceTable("301", 3, 4, "2.3", "10.3", 5, 50, "90%"),
		new QueuePerformanceTable("302", 0, 5, "2.4", "8.3", 2, 60, "98%"),
		new QueuePerformanceTable("303", 1, 2, "1.3", "20.2", 15, 100, "85%"),
		new QueuePerformanceTable("304", 6, 3, "5.2", "12.1", 1, 40, "90%"),
		new QueuePerformanceTable("303", 1, 2, "1.3", "20.2", 15, 100, "85%"),
		new QueuePerformanceTable("304", 6, 3, "5.2", "12.1", 1, 40, "90%"),
		new QueuePerformanceTable("305", 2, 7, "2.0", "08.3", 3, 30, "90")
		);

	private static final String JSON_URL = "https://gturnquist-quoters.cfapps.io/api/random";
	private static final Logger log = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private QueueService qs;
	
	@RequestMapping("/getAllQueuesNameTest")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<ACD_Queue_List> getAllQueuesNameTest(){
		return queueList;
		
	}
	@RequestMapping("/Test")
	@CrossOrigin(origins = "http://localhost:4200")
	public HashMap<String, String> sayHi() {
		String response = " \"g7lsrphhrkim1jkvbt88\" ";//"g7lsrphhrkim1jkvbt88";
		 
		//String value = response.substring(1);
	    String API_TOKEN = (response.replaceAll("[^a-zA-Z0-9]", " ")).trim();
		//return API_TOKEN;
	    //return "a7lsrphhrkim1jkvbt88";
	    HashMap<String, String> map = new HashMap<>();
	    map.put("status", "ok");
	    map.put("token", API_TOKEN);
	    log.info(API_TOKEN);
	    return map;
		//return null;
	}

	@GetMapping("/TestRestJSONURL/{token}")
	public HashMap<String, String> TestRestJSONURL(@PathVariable("token") String auth_token) {
		log.info("auth_token" +auth_token);
		Quote quote = restTemplate.getForObject(JSON_URL, Quote.class);
		log.info(quote.toString());
		HashMap<String, String> map = new HashMap<>();
		map.put("type", quote.getType());
	    map.put("Quote", quote.getValue().getQuote());
	    map.put("id", quote.getValue().getId());
		//return quote.toString();
	    return map;
	}
	
	@GetMapping("/QueuePerformanceTableTest/{token}")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<QueuePerformanceTable> TestToken(@PathVariable("token") String auth_token) {
	
		List<QueuePerformanceTable> qptl = new ArrayList<QueuePerformanceTable> ();
		QueuePerformanceTable qpt = new QueuePerformanceTable();
		
		List<ACD_Queue_List> acd_qn = queueList;
		ACD_Queue_List acdq = new ACD_Queue_List();
		for(int i=0; i < qptl.size(); i++) 
        {
			qptl.get(i).setName(acd_qn.get(i).getName());
			//qpt = qptl.get(i);
			//acdq = acd_qn.get(i);
			//qpt.setName(acdq.getName());
			//qpt.set(i, element)
        }
		
		return qptl;
		
		
		/*
		 * List<QueuePerformanceTable> qptl = queuePerformanceTableList;
		 * QueuePerformanceTable qpt = new QueuePerformanceTable();
		 * 
		 * List<ACD_Queue_List> acd_qn = queueList; ACD_Queue_List acdq = new
		 * ACD_Queue_List(); for(int i=0; i < qptl.size(); i++) {
		 * qptl.get(i).setName(acd_qn.get(i).getName()); //qpt = qptl.get(i); //acdq =
		 * acd_qn.get(i); //qpt.setName(acdq.getName()); //qpt.set(i, element) }
		 * 
		 * return qptl;
		 */
	}
	
	@GetMapping("/getQueuePerformanceTableTest/{token}")
	@CrossOrigin(origins = "http://localhost:4200")
	public QueuePerformanceTable getQueuePerformanceTable(@PathVariable("token") String auth_token) {
	
		QueuePerformanceTable qpt = new QueuePerformanceTable();
		ACD_WallBoard_QueueWise acdq_wallboard = getQueueACD();
		 String name;
		 int callsInWaiting;
		 int agentsForQueue;
		 String awt;
		 String aht;
		 int totalAbandonedCalls;
		 int totalNumberOfCalls;
		 String SLinPercentage = ""+"%";
		 int loggedInAgents;
		 
		 name = acdq_wallboard.getAcds().getQueueACDS().getqName();
		 agentsForQueue = acdq_wallboard.getAgents().size();
		 loggedInAgents = getAgentsLoggedInForGivenQueue(acdq_wallboard.getAgents());
		 awt = Integer.toString(acdq_wallboard.getAcds().getQueueACDS().getDuration().getAverage().getRing());
		 aht = Integer.toString(acdq_wallboard.getAcds().getQueueACDS().getDuration().getAverage().getTalk());
		 totalAbandonedCalls = acdq_wallboard.getAcds().getQueueACDS().getExit().getOther() + acdq_wallboard.getAcds().getQueueACDS().getHangup().getRinging()  
				  + acdq_wallboard.getAcds().getQueueACDS().getHangup().getRinging()
				  + acdq_wallboard.getAcds().getQueueACDS().getHangup().getWaiting();
		 totalNumberOfCalls = acdq_wallboard.getCurrent().size() + acdq_wallboard.getHistory().size();	
		 SLinPercentage = Integer.toString((acdq_wallboard.getAcds().getQueueACDS().getDuration().getAverage().getRing() 
				 + acdq_wallboard.getAcds().getQueueACDS().getDuration().getAverage().getIvr())/10);
		 qpt.setName(name);
		 qpt.setAht(aht);
		 qpt.setAwt(awt);
		 qpt.setTotalAbandonedCalls(totalAbandonedCalls);
		 qpt.setAgentsForQueue(agentsForQueue);
		 qpt.setCallsInWaiting(loggedInAgents);
		 qpt.setTotalNumberOfCalls(totalNumberOfCalls);
		 qpt.setSLinPercentage(SLinPercentage);
		return qpt;
	}
	
	public int getAgentsLoggedInForGivenQueue(List<AGENTS> agents) {
		
		int loogedInAgents = 0;
		
		List<AGENTS> ag = agents;
		
		long count = ag
				  .stream()
				  .filter(c -> c.getLogin())
				  .count();
		
		return loogedInAgents = (int)count;
	}
	
	@GetMapping("/getQueueACDTest")
	@CrossOrigin(origins = "http://localhost:4200")
	public ACD_WallBoard_QueueWise getQueueACD() {
		String qid = "302";
		QueueACDS queueACDS = new QueueACDS();
		ACD_WallBoard_QueueWise qpt = new ACD_WallBoard_QueueWise();
		JSONParser parser = new JSONParser();
		try {
		Object obj = parser.parse(new FileReader("/Users/hitesh/Desktop/Professional/Projects/Pravesh_nz/Vodia-Api/dashboard/ACDResponse.Json"));
		 
		// A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
		JSONObject jsonObject_root = (JSONObject) obj;

		JSONObject jo_acds = (JSONObject) jsonObject_root.get("acds");
		JSONObject jo_queue_acds = (JSONObject) jo_acds.get(qid);
		
		queueACDS = new Gson().fromJson(jo_queue_acds.toString(), QueueACDS.class);
		queueACDS.setqName(qid);
		qpt = new Gson().fromJson(jsonObject_root.toString(), ACD_WallBoard_QueueWise.class);
		qpt.getAcds().setQueueACDS(queueACDS);
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  return qpt;
	}
	
	
}
