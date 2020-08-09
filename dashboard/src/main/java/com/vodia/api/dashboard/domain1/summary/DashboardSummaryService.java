package com.vodia.api.dashboard.domain1.summary;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vodia.api.dashboard.config.URLConfig;
import com.vodia.api.dashboard.domain1.queue.ACD_Queue_List;
import com.vodia.api.dashboard.domain1.queue.QueueService;

@Service
public class DashboardSummaryService {
	
	private static final Logger log = LoggerFactory.getLogger(DashboardSummaryService.class);
	
	@Autowired
	private QueueService qs;

	public ACDInfo getACD(HttpServletRequest request,String qid) {

		// final String url =
		// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/acds";
		String date = getCurrentTimeStamp();
		//String date = "13072020";
		//final String url = "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/user/200@compassoffices.ak1.cloudpbx.net.nz/acdinfo?group="+qid+"&date="+date;

		String dn = (String) request.getSession().getAttribute("dn");
		String url_prop = URLConfig.getURL("GET_ACD_DASHBOARD");
		String GET_ACD_DASHBOARD = MessageFormat.format(url_prop, dn, dn, qid, date);
		log.info("GET_ACD_DASHBOARD URL  -" + GET_ACD_DASHBOARD);
		String token = (String) request.getSession().getAttribute("token");
		
		log.info("TOKEN AFTER LOGIN -" + token);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Cookie", "session=" + token);

		HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

		ResponseEntity<ACDInfo> response = restTemplate.exchange(GET_ACD_DASHBOARD, HttpMethod.GET, requestEntity, ACDInfo.class);
		ACDInfo acdInforForThisQueue = response.getBody();

		log.info("getting data for Queue"+qid +"Where totals calls is --"+acdInforForThisQueue.getCalls());
		return acdInforForThisQueue;
		// return queueList;
	}
	
	@SuppressWarnings("null")
	public List<ACDInfo> getACDForAllQueue(HttpServletRequest request){
		
		log.info("calling getACDForAllQueue Start");
		
		List<ACD_Queue_List> acd_queue_list = qs.getAllQueuesName(request);
		List<ACDInfo> list_acd_info_for_all_queue = new ArrayList<ACDInfo>();
		
		for(int i=0; i < acd_queue_list.size(); i++) 
        {
			log.info("getting queue details of +++"+acd_queue_list.get(i).getName());
			list_acd_info_for_all_queue.add(i,getACD(request, acd_queue_list.get(i).getName()));
			//getACDByQueue(auth_token, acd_queue_list.get(i).getName());
        }
		
		log.info("calling list_acd_info_for_all_queue END");
		return list_acd_info_for_all_queue;
		
	}
	
	public DashboardSummary getDashboardSummary(HttpServletRequest request) {
		
		log.info("calling getDashboardSummary Start");
		
		DashboardSummary dashboard_summary = new DashboardSummary();
		
		List<ACDInfo> list_acd_info_for_all_queue = getACDForAllQueue(request).stream().filter(c -> c.getCalls() != null).collect(Collectors.toList());
		
        log.info("<!-----Result after null values filtered-----!>");
        log.info(list_acd_info_for_all_queue.size() + "\n");
//		List<Integer> copy = list_acd_info_for_all_queue.stream().map(c -> c.getAvgdurationtalk()).collect(Collectors.toList());
//		Integer[] arr = copy.stream().toArray(Integer[] ::new); 
//		int average = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getAvgdurationtalk()).collect(Collectors.toList())).stream().mapToInt(val -> val).average().orElse(0);

		 Integer totalCallRecieved = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getCalls()).collect(Collectors.toList()))
					.stream().mapToInt(Integer::intValue) .sum();
		 
		 Integer totalOutboundCalls = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getOutcalls()).collect(Collectors.toList()))
					.stream().mapToInt(Integer::intValue) .sum();
		 
		 Integer totalInboundCalls = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getIncalls()).collect(Collectors.toList()))
					.stream().mapToInt(Integer::intValue) .sum();
		 
		 Integer totalMissedCall = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getMissed()).collect(Collectors.toList()))
					.stream().mapToInt(Integer::intValue) .sum();
		 
		 Integer totalCallAnswered = totalCallRecieved - totalMissedCall;
		 
		 Integer hr = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getHr()).collect(Collectors.toList()))
				.stream().mapToInt(Integer::intValue) .sum();
		 
		 Integer hw = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getHw()).collect(Collectors.toList()))
					.stream().mapToInt(Integer::intValue) .sum();
		 
		 Integer other = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getOther()).collect(Collectors.toList()))
					.stream().mapToInt(Integer::intValue) .sum();
		 
		 Integer totalCallAbnd = hr + hw + other;
		 
		 
		 Integer totalAWT = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getAvgdurationring()).collect(Collectors.toList()))
					.stream().mapToInt(val -> val).average().orElse(0);
		 
		 Integer totalATT = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getAvgdurationtalk()).collect(Collectors.toList()))
					.stream().mapToInt(val -> val).average().orElse(0);
		 
		 Integer longestAHT = (int) 14.32;
		 Integer longestAWT = (int) 3.43;
		 Integer totalCallInQueue = 3;
		 
		 log.info("totalCallRecieved--"+totalCallRecieved);
		 log.info("totalOutboundCalls--"+totalOutboundCalls);
		 log.info("totalInboundCalls--"+totalInboundCalls);
		 log.info("totalMissedCall--"+totalMissedCall);
		 log.info("totalCallAnswered--"+totalCallAnswered);
		 log.info("totalCallRecieved--"+totalCallRecieved);
		 log.info("hw-"+hw+"-hr-"+hr+"-other-"+other+"totalCallAbnd--"+totalCallAbnd);
		 log.info("totalAWT--"+totalAWT);
		 log.info("totalATT--"+totalATT);
		 log.info("longestAWT--"+longestAWT);
		 log.info("totalCallInQueue--"+totalCallInQueue);
		 log.info("longestAHT--"+longestAHT);
		 
		
		 dashboard_summary.setTotalATT(totalATT);
		 dashboard_summary.setTotalAWT(totalAWT);
		 dashboard_summary.setTotalCallAbnd(totalCallAbnd);
		 dashboard_summary.setTotalCallAnswered(totalCallAnswered);
		 dashboard_summary.setTotalInboundCalls(totalInboundCalls);
		 dashboard_summary.setTotalOutboundCalls(totalOutboundCalls);
		 dashboard_summary.setTotalCallRecieved(totalCallRecieved);
		 dashboard_summary.setTotalCallInQueue(totalCallInQueue);
		 dashboard_summary.setLongestAHT(longestAHT);
		 dashboard_summary.setLongestAWT(longestAWT);
	
		return dashboard_summary;
		
	}
	
	public static String getCurrentTimeStamp() {
	       SimpleDateFormat formDate = new SimpleDateFormat("ddMMyyyy");

	       // String strDate = formDate.format(System.currentTimeMillis()); // option 1
	       String strDate = formDate.format(new Date()); // option 2
	       return strDate;
	  } 
	
	/*
	 * public int getAvgOfElement(List<ACDInfo> list_acd_info_for_all_queue) {
	 * 
	 * int loggedInAgents = 0;
	 * 
	 * List<ACDInfo> ag = list_acd_info_for_all_queue;
	 * 
	 * ag.stream().
	 * 
	 * 
	 * return loggedInAgents = (int)count; }
	 * 
	 * public static double findAverageWithoutUsingStream(int[] array) { int sum =
	 * findSumWithoutUsingStream(array); return (double) sum / array.length; }
	 * 
	 * public static double findAverageUsingStream(int[] array) { return
	 * Arrays.stream(array).average().orElse(Double.NaN); }
	 * 
	 * public static int findSumUsingStream(Integer[] array) { return
	 * Arrays.stream(array) .mapToInt(Integer::intValue) .sum(); }
	 */
	
}


