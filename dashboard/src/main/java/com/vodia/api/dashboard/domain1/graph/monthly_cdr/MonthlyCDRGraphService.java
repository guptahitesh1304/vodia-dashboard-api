package com.vodia.api.dashboard.domain1.graph.monthly_cdr;

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
import com.vodia.api.dashboard.config.Utility;
import com.vodia.api.dashboard.domain1.queue.ACD_Queue_List;
import com.vodia.api.dashboard.domain1.queue.QueueService;
import com.vodia.api.dashboard.domain1.summary.ACDInfo;

@Service
public class MonthlyCDRGraphService {



	private static final Logger log = LoggerFactory.getLogger(MonthlyCDRGraphService.class);

	@Autowired
	private QueueService qs;

	public ACDInfo getACD(HttpServletRequest request, String qid) {

		// final String url =
		// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/acds";
		//String date = getCurrentTimeStamp();
		ACDInfo acdInforForThisQueue = null;
		// String date = "13072020";
		// final String url =
		// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/user/200@compassoffices.ak1.cloudpbx.net.nz/acdinfo?group="+qid+"&date="+date;

		try {
			String dn = (String) request.getSession().getAttribute("dn");
				log.debug("--Retrieving data for Dashboard Monthly Chart--");
				
				String my = Utility.getMonthAndYearByJava8();
				log.debug("getting results for the month"+my);
				
				String url = URLConfig.getURL("GET_MONTHLY_CDR_QUEUEWISE");
				String GET_MONTHLY_CDR_QUEUEWISE = MessageFormat.format(url, dn, dn, qid, my);
				log.debug("GET_MONTHLY_CDR_QUEUEWISE URL  -" + GET_MONTHLY_CDR_QUEUEWISE);
				String token = (String) request.getSession().getAttribute("token");

				log.debug("TOKEN AFTER LOGIN -" + token);
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.add("Cookie", "session=" + token);

				HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

				ResponseEntity<ACDInfo> response = restTemplate.exchange(GET_MONTHLY_CDR_QUEUEWISE, HttpMethod.GET, requestEntity,
						ACDInfo.class);
				acdInforForThisQueue = response.getBody();

				log.debug("getting data for Queue" + qid + "Where totals calls is --" + acdInforForThisQueue.getCalls());
		

		}

		catch (NullPointerException e) {
			log.error("Warning: Nullpointer" + e);
		} catch (Exception e) {
			log.error("Warning: Some Other exception"+e);
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

	public MonthlyCDRGraphData getMonthlyCDRGraph(HttpServletRequest request) {

		log.debug("calling getMonthlyCDRGraph Start");

		MonthlyCDRGraphData mon_crd_data = new MonthlyCDRGraphData();

		try {

			List<ACDInfo> list_acd_info_for_all_queue = getACDForAllQueue(request).stream()
					.filter(c -> c.getCalls() != null).collect(Collectors.toList());

			log.debug("<!-----Result after null values filtered-----!>");
			log.debug(list_acd_info_for_all_queue.size() + "\n");
//		List<Integer> copy = list_acd_info_for_all_queue.stream().map(c -> c.getAvgdurationtalk()).collect(Collectors.toList());
//		Integer[] arr = copy.stream().toArray(Integer[] ::new); 
//		int average = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getAvgdurationtalk()).collect(Collectors.toList())).stream().mapToInt(val -> val).average().orElse(0);

			Integer totalCallRecieved = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getCalls())
					.collect(Collectors.toList())).stream().mapToInt(Integer::intValue).sum();

			Integer totalOutboundCalls = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getOutcalls())
					.collect(Collectors.toList())).stream().mapToInt(Integer::intValue).sum();

			Integer totalInboundCalls = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getIncalls())
					.collect(Collectors.toList())).stream().mapToInt(Integer::intValue).sum();

			Integer totalMissedCall = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getMissed())
					.collect(Collectors.toList())).stream().mapToInt(Integer::intValue).sum();

			Integer totalCallAnswered = totalCallRecieved - totalMissedCall;

			Integer hr = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getHr()).collect(Collectors.toList()))
					.stream().mapToInt(Integer::intValue).sum();

			Integer hw = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getHw()).collect(Collectors.toList()))
					.stream().mapToInt(Integer::intValue).sum();

			Integer other = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getOther())
					.collect(Collectors.toList())).stream().mapToInt(Integer::intValue).sum();

			Integer totalCallAbnd = hr + hw + other;

			Integer totalAWTnSec = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getAvgdurationivr())
					.collect(Collectors.toList())).stream().mapToInt(val -> val).average().orElse(0);

			String totalAWT = Utility.getConvertedTimeInHrsMinSec(totalAWTnSec);

			Integer totalATTinSec = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getAvgdurationtalk())
					.collect(Collectors.toList())).stream().mapToInt(val -> val).average().orElse(0);

			String totalATT = Utility.getConvertedTimeInHrsMinSec(totalATTinSec);

			Integer longestAHTInSec = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getAvgdurationivr())
					.collect(Collectors.toList())).stream().mapToInt(val -> val).max().orElse(0);
			String longestAHT = Utility.getConvertedTimeInHrsMinSec(longestAHTInSec);

			Integer longestAWTInSec = (int) (list_acd_info_for_all_queue.stream().map(c -> c.getAvgdurationtalk())
					.collect(Collectors.toList())).stream().mapToInt(val -> val).max().orElse(0);
			String longestAWT = Utility.getConvertedTimeInHrsMinSec(longestAWTInSec);

			Integer totalCallInQueue = 3;

			log.debug("totalCallRecieved--" + totalCallRecieved);
			log.debug("totalOutboundCalls--" + totalOutboundCalls);
			log.debug("totalInboundCalls--" + totalInboundCalls);
			log.debug("totalMissedCall--" + totalMissedCall);
			log.debug("totalCallAnswered--" + totalCallAnswered);
			log.debug("totalCallRecieved--" + totalCallRecieved);
			log.debug("hw-" + hw + "-hr-" + hr + "-other-" + other + "totalCallAbnd--" + totalCallAbnd);
			log.debug("totalAWT--" + totalAWT);
			log.debug("totalATT--" + totalATT);
			log.debug("longestAWT--" + longestAWT);
			log.debug("totalCallInQueue--" + totalCallInQueue);
			log.debug("longestAHT--" + longestAHT);

			mon_crd_data.setTotalATT(totalATT);
			mon_crd_data.setTotalAWT(totalAWT);
			mon_crd_data.setTotalCallAbnd(totalCallAbnd);
			mon_crd_data.setTotalCallAnswered(totalCallAnswered);
			mon_crd_data.setTotalInboundCalls(totalInboundCalls);
			mon_crd_data.setTotalOutboundCalls(totalOutboundCalls);
			mon_crd_data.setTotalCallRecieved(totalCallRecieved);
			mon_crd_data.setTotalMissedCalls(totalMissedCall);
			mon_crd_data.setTotalCallInQueue(totalCallInQueue);
			mon_crd_data.setLongestAHT(longestAHT);
			mon_crd_data.setLongestAWT(longestAWT);

		}

		catch (NullPointerException e) {
			log.error("Warning: Nullpointer" + e);
		} catch (Exception e) {
			log.error("Warning: Some Other exception"+e);
		}

		return mon_crd_data;

	}

	public static String getCurrentTimeStamp() {
		SimpleDateFormat formDate = new SimpleDateFormat("ddMMyyyy");

		// String strDate = formDate.format(System.currentTimeMillis()); // option 1
		String strDate = formDate.format(new Date()); // option 2
		return strDate;
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
