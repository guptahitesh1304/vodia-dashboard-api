package com.vodia.api.dashboard.domain1.summary;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
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
import com.vodia.api.dashboard.config.Utility;
import com.vodia.api.dashboard.domain1.cdr.ACD_CDRBean;
import com.vodia.api.dashboard.domain1.cdr.CDRService;
import com.vodia.api.dashboard.domain1.queue.ACD_Queue_List;
import com.vodia.api.dashboard.domain1.queue.QueuePerformanceTable;
import com.vodia.api.dashboard.domain1.queue.QueueService;
import com.vodia.api.dashboard.domain1.queue.qperformanceTableNew.QPerformanceTableDashboardNew;
import com.vodia.api.dashboard.domain1.queue.qperformanceTableNew.QPerformanceTableDashboardNewService;

@Service
public class DashboardSummaryService {

	private static final Logger log = LoggerFactory.getLogger(DashboardSummaryService.class);
	
	private static DecimalFormat df2 = new DecimalFormat("#.##");


	@Autowired
	private QueueService qs;
	@Autowired
	private CDRService cdrs;
	@Autowired
	private QPerformanceTableDashboardNewService qpts;

	public ACDInfo getACD(HttpServletRequest request, String qid) {

		// final String url =
		// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/acds";
		String date = Utility.getCurrentTimeStamp();
		ACDInfo acdInforForThisQueue = null;
		// String date = "13072020";
		// final String url =
		// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/user/200@compassoffices.ak1.cloudpbx.net.nz/acdinfo?group="+qid+"&date="+date;

		try {
			String dn = (String) request.getSession().getAttribute("dn");
			String url_prop = URLConfig.getURL("GET_ACD_DASHBOARD");
			String GET_ACD_DASHBOARD = MessageFormat.format(url_prop, dn, dn, qid, date);
			log.debug("GET_ACD_DASHBOARD URL  -" + GET_ACD_DASHBOARD);
			String token = (String) request.getSession().getAttribute("token");

			log.debug("TOKEN AFTER LOGIN -" + token);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.add("Cookie", "session=" + token);

			HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

			ResponseEntity<ACDInfo> response = restTemplate.exchange(GET_ACD_DASHBOARD, HttpMethod.GET, requestEntity,
					ACDInfo.class);
			acdInforForThisQueue = response.getBody();

			log.debug("getting data for Queue" + qid + "Where totals calls is --" + acdInforForThisQueue.getCalls());

		}

		catch (NullPointerException e) {
			log.error("Warning: Nullpointer" + e);
		} catch (Exception e) {
			log.error("Warning: Some Other exception");
		}

		return acdInforForThisQueue;
		// return queueList;

		/*
		 * {"calls":31,"incalls":2,"outcalls":5,"missed":24,"durationivr":145578,
		 * "durationring":5381,"durationtalk":355096,"durationhold":0,"durationidle":0,
		 * "rw":4,"rr":0,"ra":0,"hc":2,"hw":7,"hr":0,"ui":0,"ai":0,"mc":0,"sumrating":0,
		 * "avgrating":0,"ratedcalls":0,"soap":0,"other":0,"calllist":[],
		 * "avgdurationivr":4696.064516129032,"avgdurationring":173.58064516129033,
		 * "avgdurationtalk":11454.709677419354,"avgdurationhold":0,"avgdurationidle":0}
		 */ }

	@SuppressWarnings("null")
	public List<ACDInfo> getACDForAllQueue(HttpServletRequest request) {

		log.debug("calling getACDForAllQueue Start");

		List<ACD_Queue_List> acd_queue_list = qs.getAllQueuesName(request);
		List<ACDInfo> list_acd_info_for_all_queue = new ArrayList<ACDInfo>();

		for (int i = 0; i < acd_queue_list.size(); i++) {
			log.debug("getting queue details of +++" + acd_queue_list.get(i).getName());
			list_acd_info_for_all_queue.add(i, getACD(request, acd_queue_list.get(i).getName()));
			// getACDByQueue(auth_token, acd_queue_list.get(i).getName());
		}

		log.debug("calling list_acd_info_for_all_queue END");
		return list_acd_info_for_all_queue;

	}

	public DashboardSummary getDashboardSummary(HttpServletRequest request) {

		log.debug("calling getDashboardSummary Start");

		DashboardSummary dashboard_summary = new DashboardSummary();
		long getDashboardSummary_START = System.nanoTime();

		try {

			
			List<ACDInfo> list_acd_info_for_all_queue = getACDForAllQueue(request).stream()
					.filter(c -> c.getCalls() != null).collect(Collectors.toList());
			long getACDForAllQueue_DASH_START = System.nanoTime();
			log.debug("getACDForAllQueue_DASH_START  execution tijme---"+(getACDForAllQueue_DASH_START-getDashboardSummary_START));

			
			List<QPerformanceTableDashboardNew> qPerformanceTable_new = qpts.getQueuePerformanceTableForDashboard(request);
			long qPerformanceTable_new_DASH_START = System.nanoTime();
			log.debug("qPerformanceTable_new_DASH_START  execution tijme---"+(qPerformanceTable_new_DASH_START-getACDForAllQueue_DASH_START));

			log.debug("<!-----Result after null values filtered-----!>");
			log.debug(list_acd_info_for_all_queue.size() + "\n");
			
			/*
			 * log.debug("getting data from queue acd"); List<QueuePerformanceTable>
			 * queue_performance_table = new ArrayList<QueuePerformanceTable>();
			 * 
			 * log.debug("checking if getQueuePerformanceTable is in Session");
			 * List<QueuePerformanceTable> qPerformanceTable = (List<QueuePerformanceTable>)
			 * request.getSession().getAttribute("qPerformanceTable"); if (qPerformanceTable
			 * != null && qPerformanceTable.size() > 0) {
			 * log.debug("getting getQueuePerformanceTable is in Session");
			 * queue_performance_table = qPerformanceTable; } else {
			 * log.debug("getQueuePerformanceTable is not  in Session");
			 * queue_performance_table = qs.getQueuePerformanceTable(request);
			 * 
			 * }
			 */
			
			/*
			 * log.debug("getting data from queue acd NEW ");
			 * List<QPerformanceTableDashboardNew> queue_performance_table_new = new
			 * ArrayList<QPerformanceTableDashboardNew>();
			 * 
			 * log.debug("checking if getQueuePerformanceTable is in Session");
			 * List<QPerformanceTableDashboardNew> qPerformanceTable_new =
			 * (List<QPerformanceTableDashboardNew>)
			 * request.getSession().getAttribute("qPerformanceTableNEW"); if
			 * (qPerformanceTable_new != null && qPerformanceTable_new.size() > 0) {
			 * log.debug("getting getQueuePerformanceTable is in Session");
			 * queue_performance_table_new = qPerformanceTable_new; } else {
			 * log.debug("getQueuePerformanceTable is not  in Session");
			 * queue_performance_table_new =
			 * qpts.getQueuePerformanceTableForDashboard(request);
			 * 
			 * }
			 */
//		List<Integer> copy = list_acd_info_for_all_queue.stream().map(c -> c.getAvgdurationtalk()).collect(Collectors.toList());
//		Integer[] arr = copy.stream().toArray(Integer[] ::new); 
//		int average = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getAvgdurationtalk()).collect(Collectors.toList())).stream().mapToInt(val -> val).average().orElse(0);

			Integer totalCallRecieved = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getCalls())
					.collect(Collectors.toList())).stream().mapToInt(Integer::intValue).sum();
			log.debug("totalCallRecieved--" + totalCallRecieved);

			Integer totalOutboundCalls = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getOutcalls())
					.collect(Collectors.toList())).stream().mapToInt(Integer::intValue).sum();
			log.debug("totalOutboundCalls--" + totalOutboundCalls);

			Integer totalInboundCalls = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getIncalls())
					.collect(Collectors.toList())).stream().mapToInt(Integer::intValue).sum();
			log.debug("totalInboundCalls--" + totalInboundCalls);

			Integer totalMissedCall = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getMissed())
					.collect(Collectors.toList())).stream().mapToInt(Integer::intValue).sum();
			log.debug("totalMissedCall--" + totalMissedCall);

			Integer totalCallAnswered = totalCallRecieved - totalMissedCall;
			log.debug("totalCallAnswered--" + totalCallAnswered);

			Integer hr = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getHr()).collect(Collectors.toList()))
					.stream().mapToInt(Integer::intValue).sum();

			Integer hw = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getHw()).collect(Collectors.toList()))
					.stream().mapToInt(Integer::intValue).sum();

			Integer other = (int) (qPerformanceTable_new.stream().map(c -> c.getCall_back_request())
					.collect(Collectors.toList())).stream().mapToInt(Integer::intValue).sum();

			Integer totalCallAbnd = hr + hw /* + other */;
			log.debug("hw-" + hw + "-hr-" + hr + "-other-" + other + "totalCallAbnd--" + totalCallAbnd);


			//double avg = qptl.stream().map(c -> c.getCallsInWaiting()).collect(Collectors.toList()).stream().mapToInt(val -> val).average().orElse(0);

			
			 

			double totalAWTnSec = (list_acd_info_for_all_queue.stream().filter(l -> l.getCalls()!=null && l.getCalls() > 0 && l.getAvgdurationivr() !=null).map(c -> c.getAvgdurationivr()).collect(Collectors.toList())
					.stream().mapToInt(val -> val).average().orElse(0))/60000;
			log.debug("Dashboard totalAWTnSec--" + totalAWTnSec);

			String totalAWT = df2.format(totalAWTnSec);
			log.debug("totalAWT--" + totalAWT);

			double totalATTinSec = (list_acd_info_for_all_queue.stream().filter(l -> l.getCalls()!=null && l.getCalls() > 0 && l.getAvgdurationtalk() !=null).map(c -> c.getAvgdurationtalk()).collect(Collectors.toList())
					.stream().mapToInt(val -> val).average().orElse(0))/60000;;
			log.debug("Dashboard totalATTinSec--" + totalATTinSec);

			String totalATT = df2.format(totalATTinSec);
			log.debug("totalATT--" + totalATT);


			String longestAWT = Integer.toString(other);
			log.debug("Call back request total--" + longestAWT);

			Integer totalCallInQueue = (int) (qPerformanceTable_new.stream().map(c -> c.getCallsInWaiting())
					.collect(Collectors.toList())).stream().mapToInt(Integer::intValue).sum();
			log.debug("totalCallInQueue--" + totalCallInQueue);

			dashboard_summary.setTotalATT(totalATT);
			dashboard_summary.setTotalAWT(totalAWT);
			dashboard_summary.setTotalCallAbnd(totalCallAbnd);
			dashboard_summary.setTotalCallAnswered(totalCallAnswered);
			dashboard_summary.setTotalInboundCalls(totalInboundCalls);
			dashboard_summary.setTotalOutboundCalls(totalOutboundCalls);
			dashboard_summary.setTotalCallRecieved(totalCallRecieved);
			dashboard_summary.setTotalMissedCalls(totalMissedCall);
			dashboard_summary.setTotalCallInQueue(totalCallInQueue);
			dashboard_summary.setLongestAHT("");
			dashboard_summary.setLongestAWT(longestAWT);

		}

		catch (NullPointerException e) {
			log.error("Warning: Nullpointer" + e);
			log.debug("ERROR: Nullpointer exception-------");
			e.printStackTrace();
		} catch (Exception e) {
			log.error("Warning: Some Other exception");
			e.printStackTrace();
		}

		long getDashboardSummary_END = System.nanoTime();
		log.debug("getDashboardSummary  execution tijme---"+(getDashboardSummary_END-getDashboardSummary_START));

		return dashboard_summary;

	}



	/*
	 * public static String getConvertedTimeInHrsMinSec(Integer input) {
	 * 
	 * Integer timeInSec = input; int seconds; int minutes; int hours; String
	 * convertedTime; String hrs = null; String mins = null; String sec = null;
	 * log.debug("Enter the number of seconds : " + timeInSec); hours = timeInSec /
	 * 3600; minutes = (timeInSec % 3600) / 60; int seconds_output = (timeInSec %
	 * 3600) % 60; if (hours > 0) hrs = Integer.toString(hours); if (minutes > 0)
	 * mins = Integer.toString(minutes); sec = Integer.toString(seconds_output);
	 * convertedTime = hrs + ":" + mins + ":" + sec; return convertedTime; }
	 */

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


