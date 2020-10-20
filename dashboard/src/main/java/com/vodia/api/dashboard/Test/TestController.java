package com.vodia.api.dashboard.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
import com.vodia.api.dashboard.config.URLConfig;
import com.vodia.api.dashboard.domain1.queue.ACD_Queue_List;
import com.vodia.api.dashboard.domain1.queue.ACD_WallBoard_QueueWise;
import com.vodia.api.dashboard.domain1.queue.AGENTS;
import com.vodia.api.dashboard.domain1.queue.QueueACDS;
import com.vodia.api.dashboard.domain1.queue.QueuePerformanceTable;
import com.vodia.api.dashboard.domain1.queue.QueueService;
import com.vodia.api.dashboard.domain1.queue.QueuesDetails;
import com.vodia.api.dashboard.domain1.queue.cdr.QueueWiseCDR;
import com.vodia.api.dashboard.domain1.queue.qperformanceTableNew.Agent;
import com.vodia.api.dashboard.domain1.queue.qperformanceTableNew.AgentsLiveStatsQWise;
import com.vodia.api.dashboard.domain1.queue.qperformanceTableNew.LiveACDSStatsAllQueue;
import com.vodia.api.dashboard.domain1.queue.qperformanceTableNew.LiveACDSStatsAllQueue_Part2;
import com.vodia.api.dashboard.domain1.summary.DashboardSummary;

@RestController
public class TestController {
	//qwise_acd_agents_stats.json

	private List<ACD_Queue_List> queueList = Arrays.asList(

			new ACD_Queue_List("1", "Customercare_Q"), new ACD_Queue_List("2", "Connecta_Support-Queue"),
			new ACD_Queue_List("3", "Connecta_Sales-Queue"), new ACD_Queue_List("4", "Service_Delivery_Queue"),
			new ACD_Queue_List("5", "Connecta_Sales-Queue"), new ACD_Queue_List("6", "Service_Delivery_Queue"),
			new ACD_Queue_List("7", "CC-Business-Q"));

	private List<QueuePerformanceTable> queuePerformanceTableList = Arrays.asList(

			new QueuePerformanceTable("301", "301", 3, 4, "2.3", "10.3", 5, 50, "90%"),
			new QueuePerformanceTable("302", "302", 0, 5, "2.4", "8.3", 2, 60, "98%"),
			new QueuePerformanceTable("303", "303", 1, 2, "1.3", "20.2", 15, 100, "85%"),
			new QueuePerformanceTable("304", "304", 6, 3, "5.2", "12.1", 1, 40, "90%"),
			new QueuePerformanceTable("305", "305", 1, 2, "1.3", "20.2", 15, 100, "85%"),
			new QueuePerformanceTable("306", "306", 6, 3, "5.2", "12.1", 1, 40, "90%"),
			new QueuePerformanceTable("307", "307", 2, 7, "2.0", "08.3", 3, 30, "90"));

	private static final String JSON_URL = "https://gturnquist-quoters.cfapps.io/api/random";
	private static final Logger log = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private QueueService qs;

	@RequestMapping("/getAllQueuesNameTest")
	@CrossOrigin(origins = "*")
	public List<ACD_Queue_List> getAllQueuesNameTest() {
		return queueList;

	}

	@RequestMapping("/Test")
	@CrossOrigin(origins = "*")
	public HashMap<String, String> sayHi() {
		String response = " \"g7lsrphhrkim1jkvbt88\" ";// "g7lsrphhrkim1jkvbt88";

		// String value = response.substring(1);
		String API_TOKEN = (response.replaceAll("[^a-zA-Z0-9]", " ")).trim();
		// return API_TOKEN;
		// return "a7lsrphhrkim1jkvbt88";
		HashMap<String, String> map = new HashMap<>();
		map.put("status", "ok");
		map.put("token", API_TOKEN);
		log.debug(API_TOKEN);
		return map;
		// return null;
	}

	@GetMapping("/TestRestJSONURL/{token}")
	public HashMap<String, String> TestRestJSONURL(@PathVariable("token") String auth_token) {
		log.debug("auth_token" + auth_token);
		Quote quote = restTemplate.getForObject(JSON_URL, Quote.class);
		log.debug(quote.toString());
		HashMap<String, String> map = new HashMap<>();
		map.put("type", quote.getType());
		map.put("Quote", quote.getValue().getQuote());
		map.put("id", quote.getValue().getId());
		// return quote.toString();
		return map;
	}

	@GetMapping("/QueuePerformanceTableTest/{token}")
	@CrossOrigin(origins = "*")
	public List<QueuePerformanceTable> TestToken(@PathVariable("token") String auth_token) {

		List<QueuePerformanceTable> qptl = new ArrayList<QueuePerformanceTable>();
		QueuePerformanceTable qpt = new QueuePerformanceTable();

		List<ACD_Queue_List> acd_qn = queueList;
		ACD_Queue_List acdq = new ACD_Queue_List();
		for (int i = 0; i < qptl.size(); i++) {
			qptl.get(i).setName(acd_qn.get(i).getName());
			// qpt = qptl.get(i);
			// acdq = acd_qn.get(i);
			// qpt.setName(acdq.getName());
			// qpt.set(i, element)
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
	@CrossOrigin(origins = "*")
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
		String SLinPercentage = "" + "%";
		int loggedInAgents;

		name = acdq_wallboard.getAcds().getQueueACDS().getqName();
		agentsForQueue = acdq_wallboard.getAgents().size();
		loggedInAgents = getAgentsLoggedInForGivenQueue(acdq_wallboard.getAgents());
		awt = Integer.toString(acdq_wallboard.getAcds().getQueueACDS().getDuration().getAverage().getRing());
		aht = Integer.toString(acdq_wallboard.getAcds().getQueueACDS().getDuration().getAverage().getTalk());
		totalAbandonedCalls = acdq_wallboard.getAcds().getQueueACDS().getExit().getOther()
				+ acdq_wallboard.getAcds().getQueueACDS().getHangup().getRinging()
				+ acdq_wallboard.getAcds().getQueueACDS().getHangup().getRinging()
				+ acdq_wallboard.getAcds().getQueueACDS().getHangup().getWaiting();
		totalNumberOfCalls = acdq_wallboard.getCurrent().size() + acdq_wallboard.getHistory().size();
		SLinPercentage = Integer.toString((acdq_wallboard.getAcds().getQueueACDS().getDuration().getAverage().getRing()
				+ acdq_wallboard.getAcds().getQueueACDS().getDuration().getAverage().getIvr()) / 10);
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

		long count = ag.stream().filter(c -> c.getLogin()).count();

		return loogedInAgents = (int) count;
	}

	@GetMapping("/getQueueACDTest")
	@CrossOrigin(origins = "*")
	public ACD_WallBoard_QueueWise getQueueACD() {
		String qid = "302";
		QueueACDS queueACDS = new QueueACDS();
		ACD_WallBoard_QueueWise qpt = new ACD_WallBoard_QueueWise();
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(
					"/Users/hitesh/Desktop/Professional/Projects/Pravesh_nz/Vodia-Api/dashboard/ACDResponse.Json"));

			// A JSON object. Key value pairs are unordered. JSONObject supports
			// java.util.Map interface.
			JSONObject jsonObject_root = (JSONObject) obj;

			JSONObject jo_acds = (JSONObject) jsonObject_root.get("acds");
			JSONObject jo_queue_acds = (JSONObject) jo_acds.get(qid);

			queueACDS = new Gson().fromJson(jo_queue_acds.toString(), QueueACDS.class);
			queueACDS.setqName(qid);
			qpt = new Gson().fromJson(jsonObject_root.toString(), ACD_WallBoard_QueueWise.class);
			qpt.getAcds().setQueueACDS(queueACDS);
		} catch (FileNotFoundException e) {
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

	@GetMapping("/getQueueWiseCDRTest")
	@CrossOrigin(origins = "*")
	public List<QueueWiseCDR> getQueueWiseCDRTest() {

		QueueWiseCDR queueACDS = new QueueWiseCDR();
		List<QueueWiseCDR> l = new ArrayList<QueueWiseCDR>();
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser
					.parse(new FileReader("/Users/hitesh/git/vodia-dashboard-api/dashboard/cdr_queuewise.json"));

			// A JSON object. Key value pairs are unordered. JSONObject supports
			// java.util.Map interface.
			JSONArray jsonObject_root = (JSONArray) obj;

			JSONObject jo_queue_acds = (JSONObject) jsonObject_root.get(0);

			l = Arrays.asList(new Gson().fromJson(jsonObject_root.toString(), QueueWiseCDR[].class));
			for (int i = 0; i < l.size(); i++) {
				l.get(i).setStart(getConvertedDate(l.get(i).getStart()));
				l.get(i).setFrom(getConvertedFromAndToPhoneNum(l.get(i).getFrom()));
				l.get(i).setTo(getConvertedFromAndToPhoneNum(l.get(i).getTo()));
				l.get(i).setAgent(getConvertedFromAndToPhoneNum(l.get(i).getAgent()));

			}

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

		List<QueueWiseCDR> result = l.stream() // convert list to stream
				.filter(line -> line.getDuration().getRing() < 10).collect(Collectors.toList()); // .count();

		System.out.println("total count of list whichh wait less than 10 sec" + result.size()
				+ "total number of call offered" + l.size());

		double d1 = result.size();
		double d2 = l.size();

		double sl = d1 * 100 / d2;
		System.out.println("Service Level" + sl);

		return result;
	}

	@GetMapping("/getMonthlyCDRGraphDataTest")
	@CrossOrigin(origins = "*")
	public DashboardSummary getMonthlyCDRGraphDataTest() {

		DashboardSummary queueACDS = new DashboardSummary();
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(
					new FileReader("/Users/hitesh/git/vodia-dashboard-api/dashboard/acd-cdr-graph-monthly.json"));

			// A JSON object. Key value pairs are unordered. JSONObject supports
			// java.util.Map interface.
			JSONObject jsonObject_root = (JSONObject) obj;

			queueACDS = new Gson().fromJson(jsonObject_root.toString(), DashboardSummary.class);

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

		return queueACDS;
	}
	
	@GetMapping("/getqwiseacd_and_agent_stats")
	@CrossOrigin(origins = "*")
	public AgentsLiveStatsQWise  getqwiseacd_and_agent_stats() {

		Agent[] agents_live_stats_QWise_part2 = null;
		List<LiveACDSStatsAllQueue> liveACDSStatsAllQueue = null;
		List<Agent> l = new ArrayList<Agent>();
		AgentsLiveStatsQWise alsqw = new AgentsLiveStatsQWise();
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(
					new FileReader("/Users/hitesh/git/vodia-dashboard-api/dashboard/qwise_acd_agents_stats.json"));

			// A JSON object. Key value pairs are unordered. JSONObject supports
			// java.util.Map interface.
			
			JSONObject jsonObject_root = (JSONObject) obj;
			JSONArray jsonObject_agents  = (JSONArray) jsonObject_root.get("agents");

			agents_live_stats_QWise_part2 = new Gson().fromJson(jsonObject_agents.toString(), Agent[].class);
			
			l = Arrays.asList(agents_live_stats_QWise_part2);
			alsqw.setAgents(l);
			alsqw.setQid("300");
			
			log.debug("returning from  agents_live_stats_QWise_part2 END"+l.get(0).getAgent());

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

		return alsqw;
	}
	
	@GetMapping("/getLiveACDSStatsAllQueueTest")
	@CrossOrigin(origins = "*")
	public List<String> getLiveACDSStatsAllQueueTest(HttpServletRequest request) {
		


		List<String> distinctElements = null;
		List<Agent> l = new ArrayList<Agent>();
		Integer Agent_Working = 0;
		Integer Agent_Talking = 0;
		List<LiveACDSStatsAllQueue> alsqw_list = new ArrayList<LiveACDSStatsAllQueue>();
		List<String> total_agents_in_queue = new ArrayList<String>();
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(
					new FileReader("/Users/hitesh/git/vodia-dashboard-api/dashboard/agent_acdwise_details.json"));

			// A JSON object. Key value pairs are unordered. JSONObject supports
			// java.util.Map interface.
			
			JSONArray jsonObject_root = (JSONArray) obj;

			JSONObject jo_queue_acds = (JSONObject) jsonObject_root.get(0);
			
			alsqw_list = Arrays.asList(new Gson().fromJson(jsonObject_root.toString(), LiveACDSStatsAllQueue[].class));

			//alsqw_list = new Gson().fromJson(jsonObject_root.toString(), LiveACDSStatsAllQueue[].class);
			
			//alsqw_list = Arrays.asList(alsqw);
			
			System.out.println("returning from  agents_live_stats_QWise_part2 END"+alsqw_list.size());
			
			for (int i = 0; i < alsqw_list.size(); i++) {
				if(alsqw_list.get(i).getAgents() !=null && alsqw_list.get(i).getAgents().size() > 0)
				total_agents_in_queue.addAll(i, alsqw_list.get(i).getAgents());
				//Agent_Working +=liveACDSStatsAllQueue_list.get(i).getAgents().size();
				Agent_Talking += alsqw_list.get(i).getCalls();
				
			}
			distinctElements = total_agents_in_queue.stream()
	                .distinct()
	                .collect(Collectors.toList());
			

			int totalAgents = distinctElements.size();
			Agent_Working = totalAgents;
			
			System.out.println("Total_login=Agent_Working--"+totalAgents);

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

		//["223","223","211","212","223","223","223","218","219","211","212","221","205","212","205","211","212","223","211","205","212","205","211","212"]
		//return total_agents_in_queue;
		
		//["223","211","212","218","219","221","205"]
		return distinctElements;
	
	}

	public String getConvertedDate(String date) {
		// System.out.println("date change requested "+date);

		long long_date = Long.parseLong(date.replace(".", "").substring(0, date.length() - 3));
		// System.out.println("long date "+long_date);
		Date converted_date = new Date(long_date);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh.mm aa");
		log.debug("getConvertedDate" + converted_date.toString());
		return format.format(converted_date).toString();
	}

	public static String getConvertedFromAndToPhoneNum(String from) {

		String from_converted = "";
		if (from != null && !from.trim().isEmpty()) {
			System.out.println("change this" + from);

			from_converted = from.replace("<sip:", "-");// .substring(0, from.indexOf("-"));
			from_converted = from_converted.substring(0, from_converted.indexOf("-")).replace("\"", "")
					.replaceAll("\\s", "");
			log.debug("getConvertedFromAndTo" + from_converted);

		}
		return from_converted;
	}

}
