package com.vodia.api.dashboard.domain1.queue;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.vodia.api.dashboard.config.Utility;
import com.vodia.api.dashboard.domain1.queue.cdr.QueueWiseCDR;

@Service
public class QueueService {
	
	
	private static final Logger log = LoggerFactory.getLogger(QueueService.class);
	private static DecimalFormat df2 = new DecimalFormat("#.##");

	/*
	 * private List<QueuesDetails> queueList = Arrays.asList(
	 * 
	 * new QueuesDetails("300", "Customercare_Q", null, 0), new QueuesDetails("301",
	 * "Connecta_Support-Queue", null, 0), new QueuesDetails("302",
	 * "Connecta_Sales-Queue", null, 0), new QueuesDetails("303",
	 * "Service_Delivery_Queue", null, 0), new QueuesDetails("304", "CC-Business-Q",
	 * null, 0) );
	 */
	
	/*
	 * private String name; private int callsInWaiting; private int agentsForQueue;
	 * private String awt; private String aht; private int totalAbandonedCalls;
	 * private int totalNumberOfCalls; private String SLinPercentage;
	 */

	
	
	public List<ACD_Queue_List> getAllQueuesName(HttpServletRequest request){
		log.debug("i am in getAllQueuesName SERVICE ");
		
		 //final String url = "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/acds";
		String dn = (String) request.getSession().getAttribute("dn");
		String url_prop = URLConfig.getURL("GET_ALL_QUEUE_NAMES");
		String GET_ALL_QUEUE_NAMES = MessageFormat.format(url_prop, dn, dn);
		log.debug("GET_ALL_QUEUE_NAMES URL  -"+GET_ALL_QUEUE_NAMES);
		
		HttpSession session = request.getSession();

		//final String url =  "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/user/200@compassoffices.ak1.cloudpbx.net.nz/wallboard";
			// LoginController lc = new LoginController();
			 //String token = lc.VodiaRestAPILogin();
			 String token = (String) request.getSession().getAttribute("token");
			 //log.debug("TOKEN AFTER LOGIN -"+token);
			  RestTemplate restTemplate = new RestTemplate();
			  HttpHeaders requestHeaders = new HttpHeaders();
			  requestHeaders.add("Cookie", "session=" + token);
			 
			 
			HttpEntity requestEntity = new HttpEntity(null, requestHeaders);
			   
			  ResponseEntity<ACD_Queue_List[]> response = restTemplate.exchange(GET_ALL_QUEUE_NAMES, HttpMethod.GET, requestEntity, ACD_Queue_List[].class);
			  List<ACD_Queue_List> acd_cdr_list = Arrays.asList(response.getBody());
			  log.debug("retrurning acd_cdr_list and setting to session" +acd_cdr_list.size());
			  session.setAttribute("ALL_QUEUE_NAMES", acd_cdr_list);
			  return acd_cdr_list;
		//return queueList;
	}
	
	public List<QueueWiseCDR> getQueueWiseCDR(HttpServletRequest request, String queue) {
		log.debug("i am in getQueueWiseCDR SERVICE ");

		String cdrForQueueKey = "cdrForQueue" + queue;
		log.debug("cdrForQueueKey--" + cdrForQueueKey);

		// final String url =
		// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/acds";
		String dn = (String) request.getSession().getAttribute("dn");
		String url_prop = URLConfig.getURL("GET_ACD_CDR_QUEUEWISE");
		String GET_ACD_BY_QUEUE_URL = MessageFormat.format(url_prop, dn, queue, dn);
		log.debug("GET_ACD_CDR_QUEUEWISE_URL   -" + GET_ACD_BY_QUEUE_URL);

		String token = (String) request.getSession().getAttribute("token");
		// log.debug("TOKEN AFTER LOGIN -"+token);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Cookie", "session=" + token);

		HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

		ResponseEntity<QueueWiseCDR[]> response = restTemplate.exchange(GET_ACD_BY_QUEUE_URL, HttpMethod.GET,
				requestEntity, QueueWiseCDR[].class);
		List<QueueWiseCDR> acd_cdr_queuewise_list = Arrays.asList(response.getBody());
		log.debug("retrurning acd_cdr_queuewise_list" + acd_cdr_queuewise_list.size());

		// updaing start date
		for (int i = 0; i < acd_cdr_queuewise_list.size(); i++) {
			acd_cdr_queuewise_list.get(i).setStart(Utility.getConvertedDateQueue(acd_cdr_queuewise_list.get(i).getStart()));
			acd_cdr_queuewise_list.get(i)
					.setFrom(Utility.getConvertedFromAndToPhoneNum(acd_cdr_queuewise_list.get(i).getFrom()));
			acd_cdr_queuewise_list.get(i)
					.setTo(Utility.getConvertedFromAndToPhoneNum(acd_cdr_queuewise_list.get(i).getTo()));
			acd_cdr_queuewise_list.get(i)
					.setAgent(Utility.getConvertedFromAndToPhoneNum(acd_cdr_queuewise_list.get(i).getAgent()));

		}

		request.getSession().setAttribute(cdrForQueueKey, acd_cdr_queuewise_list);
		return acd_cdr_queuewise_list;
		// return queueList;
	}
	
	public String getServiceLevelQueueWise(HttpServletRequest request, String q){
		long getServiceLevelQueueWise_start = System.nanoTime();
		double numOfCalls;
		double numOfCallsWithinServiceLevel;
		String slKey = "sl_"+q;
		List<QueueWiseCDR> acd_cdr_queuewise_list = new ArrayList<QueueWiseCDR>();
		acd_cdr_queuewise_list = getQueueWiseCDR(request, q);
		
		int sl_threshold = Integer.parseInt(URLConfig.getURL("SERVICE_LEVEL_THRESHOLD"));
		numOfCalls = acd_cdr_queuewise_list.size();
		numOfCallsWithinServiceLevel = acd_cdr_queuewise_list.stream()                // convert list to stream
                .filter(line -> line.getDuration().getIvr() < sl_threshold).count(); 
		log.debug("total count of list whichh wait less than 10 sec--" +numOfCallsWithinServiceLevel+"total number of call offered---"+numOfCalls);
		
		int sl_d = (int) (Math.floor((numOfCallsWithinServiceLevel * 100 / numOfCalls) * 100) / 100);
		
		String sl=(Integer.toString(sl_d)).replaceAll("\\.0*$", "");
		
		log.debug("Service Level --"+slKey+"is-"+sl);
		request.getSession().setAttribute(slKey, sl);
		long getServiceLevelQueueWise_end = System.nanoTime();
		log.debug("getServiceLevelQueueWise  execution tijme"+(getServiceLevelQueueWise_end-getServiceLevelQueueWise_start));
		return sl;
		
	}
	public ACD_WallBoard_QueueWise getACDByQueue(HttpServletRequest request, String qid){
		log.debug("i am in getACDByQueue SERVICE ");
		
		String dn = (String) request.getSession().getAttribute("dn");
		String url_prop = URLConfig.getURL("GET_ACD_BY_QUEUE");
		String GET_ACD_BY_QUEUE = MessageFormat.format(url_prop, dn, dn, qid);
		//final String url = "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/user/200@compassoffices.ak1.cloudpbx.net.nz/wallboard/"+ qid;
		log.debug("GET_ACD_BY_QUEUE URL  -"+GET_ACD_BY_QUEUE);		

		QueueACDS queueACDS = new QueueACDS();
		ACD_WallBoard_QueueWise acd_queuewise = new ACD_WallBoard_QueueWise();

		String token = (String) request.getSession().getAttribute("token");
		//log.debug("TOKEN AFTER LOGIN -"+token);
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
		log.debug("returning from  getACDByQueue END");
		return acd_queuewise;
	}
	
	@SuppressWarnings("null")
	public List<ACD_WallBoard_QueueWise> getACDForAllQueue(HttpServletRequest request){
		
		log.debug("calling getACDForAllQueue Start");
		
		List<ACD_Queue_List> acd_queue_list = getAllQueuesName(request);
		List<ACD_WallBoard_QueueWise> list_acd_queue_wallboard = new ArrayList<ACD_WallBoard_QueueWise>();
		List<Object> list_of_agent_list = new ArrayList<Object>();
		
		for(int i=0; i < acd_queue_list.size(); i++) 
        {
			log.debug("getting queue details of +++"+acd_queue_list.get(i).getName());
			list_acd_queue_wallboard.add(i,getACDByQueue(request, acd_queue_list.get(i).getName()));
			//getACDByQueue(auth_token, acd_queue_list.get(i).getName());
        }
		
		log.debug("setting up agents info into session");
		request.getSession().setAttribute("list_acd_queue_wallboard", list_acd_queue_wallboard);
		
		log.debug("calling getACDForAllQueue END");
		return list_acd_queue_wallboard;
		
	}
	
	public List<ACD_Queue_List> getACD(HttpServletRequest request, String acd) {

		// final String url =
		// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/acds";
		List<ACD_Queue_List> acd_cdr_list = null;
		try {
			String dn = (String) request.getSession().getAttribute("dn");
			String url_prop = URLConfig.getURL("GET_ACD");
			String GET_ACD = MessageFormat.format(url_prop, dn, dn, acd);
			log.debug("GET_ACD URL  -" + GET_ACD);
			// final String url =
			// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/user/200@compassoffices.ak1.cloudpbx.net.nz/wallboard/"+acd;
			// LoginController lc = new LoginController();
			// String token = lc.VodiaRestAPILogin();
			String token = (String) request.getSession().getAttribute("token");
			//log.debug("TOKEN AFTER LOGIN -"+token);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.add("Cookie", "session=" + token);

			HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

			ResponseEntity<ACD_Queue_List[]> response = restTemplate.exchange(GET_ACD, HttpMethod.GET, requestEntity,
					ACD_Queue_List[].class);
			acd_cdr_list = Arrays.asList(response.getBody());

		}

		catch (NullPointerException e) {
			log.error("Warning: Nullpointer" + e);
		} catch (Exception e) {
			log.error("Warning: Some Other exception"+e);
		}

		return acd_cdr_list;
		// return queueList;
	}
	
	public List<QueuePerformanceTable> getQueuePerformanceTable(HttpServletRequest request) {
		log.debug("i am in getQueuePerformanceTable SERVICE ");
		long startTime = System.nanoTime();
		List<QueuePerformanceTable> qptl = new ArrayList<QueuePerformanceTable>();

		String queue_id;
		String queue_name;
		int callsInWaiting = 0;
		int agentsForQueue;
		int totalAgents;
		int call_back_request;
		String awt;
		String aht;
		int totalAbandonedCalls;
		int totalNumberOfCalls;
		String SLinPercentage = "";
		int loggedInAgents;
		try {
			long list_acd_queue_wallboard_time_start = System.nanoTime();
			log.debug("calling getACDForAllQueue from getQueuePerformanceTable");
			List<ACD_WallBoard_QueueWise> list_acd_queue_wallboard = getACDForAllQueue(request);
			long list_acd_queue_wallboard_time = System.nanoTime();
			log.debug("got the ACD list for all queue+" + list_acd_queue_wallboard.size());

			log.debug("list_acd_queue_wallboard  execution tijme"+(list_acd_queue_wallboard_time-list_acd_queue_wallboard_time_start));
			@SuppressWarnings("unchecked")
			List<ACD_Queue_List> all_queue_name_list = (List<ACD_Queue_List>) request.getSession()
					.getAttribute("ALL_QUEUE_NAMES");
			log.debug("getting call queue name from session" + all_queue_name_list.size());

			for (int i = 0; i < list_acd_queue_wallboard.size(); i++) {
				QueuePerformanceTable qpt = new QueuePerformanceTable();
				queue_id = list_acd_queue_wallboard.get(i).getAcds().getQueueACDS().getqName();
				
				String toSearchName = queue_id;
				queue_name = all_queue_name_list.stream().filter(p -> p.getName().equals(toSearchName)).findFirst()
						.orElse(null).getDisplay();

				agentsForQueue = list_acd_queue_wallboard.get(i).getAgents().size();
				
				loggedInAgents = (int) list_acd_queue_wallboard.get(i).getAgents().stream().filter(c->c.getLogin()).count();
				
				 //= getAgentsLoggedInForGivenQueue(list_acd_queue_wallboard.get(i).getAgents());
				
				awt = Utility.getConvertedTimeInHrsMinSec(
						list_acd_queue_wallboard.get(i).getAcds().getQueueACDS().getDuration().getAverage().getIvr());
				
				aht = Utility.getConvertedTimeInHrsMinSec(
						list_acd_queue_wallboard.get(i).getAcds().getQueueACDS().getDuration().getAverage().getTalk());

				totalAbandonedCalls = list_acd_queue_wallboard.get(i).getAcds().getQueueACDS().getHangup().getRinging()
						+ list_acd_queue_wallboard.get(i).getAcds().getQueueACDS().getHangup().getWaiting();
				
				totalNumberOfCalls = list_acd_queue_wallboard.get(i).getCurrent().size()
						+ list_acd_queue_wallboard.get(i).getHistory().size();
				
				call_back_request = list_acd_queue_wallboard.get(i).getAcds().getQueueACDS().getExit().getOther();
				
				callsInWaiting = list_acd_queue_wallboard.get(i).getCurrent().size();

				log.debug("Setting Service Level");
				String slKey = "sl_"+queue_id;
				String SLinPercentageSession = (String) request.getSession().getAttribute(slKey);
				if(SLinPercentageSession !=null) {
					SLinPercentage= SLinPercentageSession;
					
				}
				else {
					SLinPercentage = getServiceLevelQueueWise(request, queue_id);
				}
				

				log.debug("queue id --" + queue_id);
				log.debug(" queue_name --" + queue_name);
				log.debug("agentsForQueue--" + agentsForQueue);
				log.debug("loggedInAgents--" + loggedInAgents);
				log.debug("awt--" + awt);
				log.debug("aht--" + aht);
				log.debug("totalAbandonedCalls--" + totalAbandonedCalls);
				log.debug("totalNumberOfCalls--" + totalNumberOfCalls);
				log.debug("SLinPercentage--" + SLinPercentage);
				log.debug("call_back_request--" + call_back_request);
				qpt.setName(queue_id);
				qpt.setDisplayName(queue_name);
				qpt.setAht(aht);
				qpt.setAwt(awt);
				qpt.setTotalAbandonedCalls(totalAbandonedCalls);
				qpt.setAgentsForQueue(agentsForQueue);
				qpt.setCallsInWaiting(callsInWaiting);
				qpt.setLoggedInAgents(loggedInAgents);
				qpt.setTotalNumberOfCalls(totalNumberOfCalls);
				qpt.setSLinPercentage(SLinPercentage);
				qpt.setCall_back_request(call_back_request);
				qptl.add(i, qpt);
			}

			log.debug("returning total list for getQueuePerformanceTable on the Dashboard--" + qptl.size());
			long qptl_time = System.nanoTime();
			log.debug("qptl_time  execution tijme"+(qptl_time-list_acd_queue_wallboard_time));

		}

		catch (NullPointerException e) {
			log.error("Warning: Nullpointer" + e);
		} catch (Exception e) {
			log.error("Warning: Some Other exception"+e);
		}

		log.debug("getting total number of agents");
		totalAgents = qptl.stream().map(c -> c.getAgentsForQueue()).collect(Collectors.toList()).stream()
		.mapToInt(val -> val).max().orElse(0);
		request.getSession().setAttribute("totalAgents", totalAgents);
		log.debug("setting totalAgents info into session"+totalAgents);
		log.debug("returning Queue Performance List" + qptl.size());
		
		log.debug("setting qPerformanceTable info into session");
		request.getSession().setAttribute("qPerformanceTable", qptl);
		
//		OptionalDouble SLAvgForAllQ_d = qptl.stream().map(c -> c.getSLinPercentage()).collect(Collectors.toList())
//				.stream().mapToInt(val -> Integer.parseInt(val)).average();
		
//		String SLAvgForAllQ = df2.format(SLAvgForAllQ_d);
//		request.getSession().setAttribute("SLAvgForAllQ", SLAvgForAllQ);
//		log.debug("Dashboard SLAvgForAllQ--" + SLAvgForAllQ);
		return qptl;
	}
	
	public int getAgentsLoggedInForGivenQueue(List<AGENTS> agents) {

		int loggedInAgents = 0;

		List<AGENTS> ag = new ArrayList<AGENTS>();

		long count = ag.stream().filter(c -> c.getLogin()).count();
		loggedInAgents = (int) count;

		return loggedInAgents;
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