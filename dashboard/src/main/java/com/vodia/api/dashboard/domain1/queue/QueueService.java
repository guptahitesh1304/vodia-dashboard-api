package com.vodia.api.dashboard.domain1.queue;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.vodia.api.dashboard.config.URLConfig;

@Service
public class QueueService {
	
	
	private static final Logger log = LoggerFactory.getLogger(QueueService.class);

	private List<QueuesDetails> queueList = Arrays.asList(
			
			new QueuesDetails("300", "Customercare_Q", null, 0),
			new QueuesDetails("301", "Connecta_Support-Queue", null, 0),
			new QueuesDetails("302", "Connecta_Sales-Queue", null, 0),
			new QueuesDetails("303", "Service_Delivery_Queue", null, 0),
			new QueuesDetails("304", "CC-Business-Q", null, 0)
			);
	
	/*
	 * private String name; private int callsInWaiting; private int agentsForQueue;
	 * private String awt; private String aht; private int totalAbandonedCalls;
	 * private int totalNumberOfCalls; private String SLinPercentage;
	 */
	
	private List<QueuePerformanceTable> queuePerformanceTableList = Arrays.asList(
			
			new QueuePerformanceTable("1", 3, 4, "2.3", "10.3", 5, 50, "90%"),
			new QueuePerformanceTable("2", 0, 5, "2.4", "8.3", 2, 60, "98%"),
			new QueuePerformanceTable("3", 1, 2, "1.3", "20.2", 15, 100, "85%"),
			new QueuePerformanceTable("4", 6, 3, "5.2", "12.1", 1, 40, "90%"),
			new QueuePerformanceTable("5", 2, 7, "2.0", "08.3", 3, 30, "90"),
			//new QueuePerformanceTable("6", 0, 5, "2.4", "8.3", 2, 60, "98%"),
			new QueuePerformanceTable("7", 1, 2, "1.3", "20.2", 15, 100, "85%"),
			new QueuePerformanceTable("8", 0, 5, "2.4", "8.3", 2, 60, "98%")
			
			);
	
	
	public List<ACD_Queue_List> getAllQueuesName(HttpServletRequest request){
		log.info("i am in getAllQueuesName SERVICE ");
		
		 //final String url = "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/acds";
		String dn = (String) request.getSession().getAttribute("dn");
		String url_prop = URLConfig.getURL("GET_ALL_QUEUE_NAMES");
		String GET_ALL_QUEUE_NAMES = MessageFormat.format(url_prop, dn, dn);
		log.info("GET_ALL_QUEUE_NAMES URL  -"+GET_ALL_QUEUE_NAMES);		

		//final String url =  "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/user/200@compassoffices.ak1.cloudpbx.net.nz/wallboard";
			// LoginController lc = new LoginController();
			 //String token = lc.VodiaRestAPILogin();
			 String token = (String) request.getSession().getAttribute("token");
			 log.info("TOKEN AFTER LOGIN -"+token);
			  RestTemplate restTemplate = new RestTemplate();
			  HttpHeaders requestHeaders = new HttpHeaders();
			  requestHeaders.add("Cookie", "session=" + token);
			 
			 
			HttpEntity requestEntity = new HttpEntity(null, requestHeaders);
			   
			  ResponseEntity<ACD_Queue_List[]> response = restTemplate.exchange(GET_ALL_QUEUE_NAMES, HttpMethod.GET, requestEntity, ACD_Queue_List[].class);
			  List<ACD_Queue_List> acd_cdr_list = Arrays.asList(response.getBody());
			  log.info("retrurning acd_cdr_list" +acd_cdr_list.size());
			  return acd_cdr_list;
		//return queueList;
	}
	
	public ACD_WallBoard_QueueWise getACDByQueue(HttpServletRequest request, String qid){
		log.info("i am in getAllQueuesName SERVICE ");
		
		String dn = (String) request.getSession().getAttribute("dn");
		String url_prop = URLConfig.getURL("GET_ACD_BY_QUEUE");
		String GET_ACD_BY_QUEUE = MessageFormat.format(url_prop, dn, dn, qid);
		//final String url = "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/user/200@compassoffices.ak1.cloudpbx.net.nz/wallboard/"+ qid;
		log.info("GET_ACD_BY_QUEUE URL  -"+GET_ACD_BY_QUEUE);		

		QueueACDS queueACDS = new QueueACDS();
		ACD_WallBoard_QueueWise acd_queuewise = new ACD_WallBoard_QueueWise();

		String token = (String) request.getSession().getAttribute("token");
		log.info("TOKEN AFTER LOGIN -" + token);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Cookie", "session=" + token);

		HttpEntity requestEntity = new HttpEntity(null, requestHeaders);
		ResponseEntity<String> response = restTemplate.exchange(GET_ACD_BY_QUEUE, HttpMethod.GET, requestEntity, String.class);
		String acd_cdr_by_queue = response.getBody();
		
		JSONObject jsonObject_root = (JSONObject) JSONValue.parse(acd_cdr_by_queue);
		// JSONObject json = new JSONObject(acd_cdr_by_queue);
		JSONObject jo_acds = (JSONObject) jsonObject_root.get("acds");
		JSONObject jo_queue_acds = (JSONObject) jo_acds.get(qid);

		queueACDS = new Gson().fromJson(jo_queue_acds.toString(), QueueACDS.class);
		queueACDS.setqName(qid);
		acd_queuewise = new Gson().fromJson(jsonObject_root.toString(), ACD_WallBoard_QueueWise.class);
		acd_queuewise.getAcds().setQueueACDS(queueACDS);
		log.info("returning from  getACDByQueue END");
		return acd_queuewise;
	}
	
	@SuppressWarnings("null")
	public List<ACD_WallBoard_QueueWise> getACDForAllQueue(HttpServletRequest request){
		
		log.info("calling getACDForAllQueue Start");
		
		List<ACD_Queue_List> acd_queue_list = getAllQueuesName(request);
		List<ACD_WallBoard_QueueWise> list_acd_queue_wallboard = new ArrayList<ACD_WallBoard_QueueWise>();
		
		for(int i=0; i < acd_queue_list.size(); i++) 
        {
			log.info("getting queue details of +++"+acd_queue_list.get(i).getName());
			list_acd_queue_wallboard.add(i,getACDByQueue(request, acd_queue_list.get(i).getName()));
			//getACDByQueue(auth_token, acd_queue_list.get(i).getName());
        }
		
		log.info("calling getACDForAllQueue END");
		return list_acd_queue_wallboard;
		
	}
	
	public List<ACD_Queue_List> getACD(HttpServletRequest request, String acd){
		
		 //final String url = "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/acds";
		 
		String dn = (String) request.getSession().getAttribute("dn");
		String url_prop = URLConfig.getURL("GET_ACD");
		String GET_ACD = MessageFormat.format(url_prop, dn, dn, acd);
		log.info("GET_ACD URL  -"+GET_ACD);		
		//final String url =  "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/user/200@compassoffices.ak1.cloudpbx.net.nz/wallboard/"+acd;
			// LoginController lc = new LoginController();
			 //String token = lc.VodiaRestAPILogin();
			 String token = (String) request.getSession().getAttribute("token");
			 log.info("TOKEN AFTER LOGIN -"+token);
			 RestTemplate restTemplate = new RestTemplate();
			 HttpHeaders requestHeaders = new HttpHeaders();
			 requestHeaders.add("Cookie", "session=" + token);
			 
			 
			HttpEntity requestEntity = new HttpEntity(null, requestHeaders);
			   
			  ResponseEntity<ACD_Queue_List[]> response = restTemplate.exchange(GET_ACD, HttpMethod.GET, requestEntity, ACD_Queue_List[].class);
			  List<ACD_Queue_List> acd_cdr_list = Arrays.asList(response.getBody());
			  
			  return acd_cdr_list;
		//return queueList;
	}
	
	public List<QueuePerformanceTable> getQueuePerformanceTable(HttpServletRequest request) {
		log.info("i am in getQueuePerformanceTable SERVICE ");
		List<QueuePerformanceTable> qptl = new ArrayList<QueuePerformanceTable>();
		
		String name;
		int callsInWaiting = 0;
		int agentsForQueue;
		String awt;
		String aht;
		int totalAbandonedCalls;
		int totalNumberOfCalls;
		String SLinPercentage = "";
		int loggedInAgents;
		List<ACD_WallBoard_QueueWise> list_acd_queue_wallboard = getACDForAllQueue(request);
		log.info("got the queue list_acd_queue_wallboard.size() list+" + list_acd_queue_wallboard.size());

		for (int i = 0; i < list_acd_queue_wallboard.size(); i++) {
			QueuePerformanceTable qpt = new QueuePerformanceTable();
			name = list_acd_queue_wallboard.get(i).getAcds().getQueueACDS().getqName();
			agentsForQueue = list_acd_queue_wallboard.get(i).getAgents().size();
			loggedInAgents = getAgentsLoggedInForGivenQueue(list_acd_queue_wallboard.get(i).getAgents());
			awt = Integer.toString(
					list_acd_queue_wallboard.get(i).getAcds().getQueueACDS().getDuration().getAverage().getRing());
			aht = Integer.toString(
					list_acd_queue_wallboard.get(i).getAcds().getQueueACDS().getDuration().getAverage().getTalk());
			totalAbandonedCalls = list_acd_queue_wallboard.get(i).getAcds().getQueueACDS().getExit().getOther()
					+ list_acd_queue_wallboard.get(i).getAcds().getQueueACDS().getHangup().getRinging()
					+ list_acd_queue_wallboard.get(i).getAcds().getQueueACDS().getHangup().getRinging()
					+ list_acd_queue_wallboard.get(i).getAcds().getQueueACDS().getHangup().getWaiting();
			totalNumberOfCalls = list_acd_queue_wallboard.get(i).getCurrent().size()
					+ list_acd_queue_wallboard.get(i).getHistory().size();
			SLinPercentage = Integer.toString((list_acd_queue_wallboard.get(i).getAcds().getQueueACDS().getDuration()
					.getAverage().getRing()
					+ list_acd_queue_wallboard.get(i).getAcds().getQueueACDS().getDuration().getAverage().getIvr())
					/ 10);
			
			log.info("queue name --"+name);
			 log.info("agentsForQueue--"+agentsForQueue);
			 log.info("loggedInAgents--"+loggedInAgents);
			 log.info("awt--"+awt);
			 log.info("aht--"+aht);
			 log.info("totalAbandonedCalls--"+totalAbandonedCalls);
			 log.info("SLinPercentage--"+SLinPercentage);
			qpt.setName(name);
			qpt.setAht(aht);
			qpt.setAwt(awt);
			qpt.setTotalAbandonedCalls(totalAbandonedCalls);
			qpt.setAgentsForQueue(agentsForQueue);
			qpt.setCallsInWaiting(callsInWaiting);
			qpt.setLoggedInAgents(loggedInAgents);
			qpt.setTotalNumberOfCalls(totalNumberOfCalls);
			qpt.setSLinPercentage(SLinPercentage);
			qptl.add(i, qpt);
		}

		log.info("returning Queue Performance List"+qptl.size());
		(qptl.stream().map(c -> c.getName()).collect(Collectors.toList())).stream().forEach(s -> log.info("qptl returned" +s));
		return qptl;
	}
	
public int getAgentsLoggedInForGivenQueue(List<AGENTS> agents) {
		
		int loggedInAgents = 0;
		
		List<AGENTS> ag = new ArrayList<AGENTS>();
		
		long count = ag
				  .stream()
				  .filter(c -> c.getLogin())
				  .count();
		
		return loggedInAgents = (int)count;
	}
	
	
}
/*[
 {
    "name":"300",
    "display":"Customercare_Q",
    "agents":[

    ],
    "calls":0
 },
 {
    "name":"301",
    "display":"Connecta_Support-Queue",
    "agents":[

    ],
    "calls":0
 },
 {
    "name":"302",
    "display":"Connecta_Sales-Queue",
    "agents":[

    ],
    "calls":0
 },
 {
    "name":"303",
    "display":"Service_Delivery_Queue",
    "agents":[

    ],
    "calls":0
 },
 {
    "name":"305",
    "display":"Activata-WFH-Q",
    "agents":[

    ],
    "calls":0
 },
 {
    "name":"306",
    "display":"CC-Business-Q",
    "agents":[

    ],
    "calls":0
 },
 {
    "name":"307",
    "display":"pravesh-test",
    "agents":[

    ],
    "calls":0
 }
]*/