package com.vodia.api.dashboard.domain1.queue;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.vodia.api.dashboard.domain1.queue.cdr.QueueWiseCDR;


@RestController
public class QueueController {

	private static final Logger log = LoggerFactory.getLogger(QueueController.class);

	@Autowired
	private QueueService qs;

	@GetMapping("/getAllQueuesName")
	@CrossOrigin(origins = "*")
	private List<ACD_Queue_List> getAllQueuesName(HttpServletRequest request) {

		return qs.getAllQueuesName(request);

		/*
		 * final String url =
		 * "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/acds";
		 * // LoginController lc = new LoginController(); //String token =
		 * lc.VodiaRestAPILogin(); String token = auth_token;
		 * log.debug("TOKEN AFTER LOGIN -"+token); RestTemplate restTemplate = new
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

	/*
	 * Sample Response
	 * 
	 * [{"name":"300","displayName":"CC-Business-Q","callsInWaiting":0,
	 * "agentsForQueue":7,"awt":"318","aht":"414","totalAbandonedCalls":3,
	 * "totalNumberOfCalls":16,"loggedInAgents":0,"slinPercentage":
	 * "8.646616541353383"},{"name":"301","displayName":"Connecta_Support-Queue",
	 * "callsInWaiting":0,"agentsForQueue":5,"awt":"10","aht":"359",
	 * "totalAbandonedCalls":1,"totalNumberOfCalls":13,"loggedInAgents":0,
	 * "slinPercentage":"65.0"},{"name":"302","displayName":"Connecta_Sales-Queue",
	 * "callsInWaiting":0,"agentsForQueue":6,"awt":"0","aht":"246",
	 * "totalAbandonedCalls":0,"totalNumberOfCalls":38,"loggedInAgents":0,
	 * "slinPercentage":"87.66666666666667"},{"name":"303","displayName":
	 * "Service_Delivery_Queue","callsInWaiting":0,"agentsForQueue":1,"awt":"418",
	 * "aht":"344","totalAbandonedCalls":4,"totalNumberOfCalls":16,"loggedInAgents":
	 * 0,"slinPercentage":"11.956521739130435"},{"name":"305","displayName":
	 * "Activata","callsInWaiting":0,"agentsForQueue":5,"awt":"64","aht":"341",
	 * "totalAbandonedCalls":18,"totalNumberOfCalls":130,"loggedInAgents":0,
	 * "slinPercentage":"68.33333333333333"},{"name":"306","displayName":"CC-Res-Q",
	 * "callsInWaiting":0,"agentsForQueue":8,"awt":"300","aht":"358",
	 * "totalAbandonedCalls":35,"totalNumberOfCalls":268,"loggedInAgents":0,
	 * "slinPercentage":"20.666666666666668"},{"name":"310","displayName":"",
	 * "callsInWaiting":0,"agentsForQueue":1,"awt":"0","aht":"0",
	 * "totalAbandonedCalls":0,"totalNumberOfCalls":0,"loggedInAgents":0,
	 * "slinPercentage":"66.66666666666667"},{"name":"307","displayName":
	 * "MobileCallBAck-Q","callsInWaiting":0,"agentsForQueue":6,"awt":"0","aht":
	 * "207","totalAbandonedCalls":19,"totalNumberOfCalls":143,"loggedInAgents":0,
	 * "slinPercentage":"84.33333333333333"}]
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/getQueuePerformanceTable")
	@CrossOrigin(origins = "*")
	private List<QueuePerformanceTable> getQueuePerformanceTable(HttpServletRequest request) {

		log.debug("in contoller getQueuePerformanceTable");
		return qs.getQueuePerformanceTable(request);

	}

	@GetMapping("/getACDByQueue/{qid}")
	@CrossOrigin(origins = "*")
	private ACD_WallBoard_QueueWise getACDByQueue(HttpServletRequest request, @PathVariable("qid") String qid) {

		return qs.getACDByQueue(request, qid);

	}

	@GetMapping("/getACDForAllQueue")
	@CrossOrigin(origins = "*")
	private List<ACD_WallBoard_QueueWise> getACDForAllQueue(HttpServletRequest request) {
		// List<ACD_WallBoard_QueueWise> acd_wallboard_queue_list;

		log.debug("calling getACDForAllQueue");
		return qs.getACDForAllQueue(request);

	}

	@SuppressWarnings({ "unchecked", "null" })
	@GetMapping("/getQueueWiseCDR/{queue}")
	@CrossOrigin(origins = "*")
	private List<QueueWiseCDR> getQueueWiseCDR(HttpServletRequest request, @PathVariable("queue") String queue) {
		// List<ACD_WallBoard_QueueWise> acd_wallboard_queue_list;
		List<QueueWiseCDR> listQueueWiseCDR = null;
		log.debug("calling getQueueWiseCDR");
		listQueueWiseCDR = (List<QueueWiseCDR>) request.getSession().getAttribute("cdrForQueue" + queue);
		if (listQueueWiseCDR != null || listQueueWiseCDR.size() > 0) {
			log.debug("listQueueWiseCDR getting from session ");
			return listQueueWiseCDR;
		} else {
			return qs.getQueueWiseCDR(request, queue);

		}
	}
	
	/*
	 * #https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/acd/306@compassoffices.ak1.cloudpbx.net.nz/stat?date=20200902&days=1 -- 

	 * {"agents":[{"agent":"203","calls":3,"missed":2,"ivr":2301,"ring":1795,"talk":
	 * 2016,"hold":944,"idle":{"calls":3,"duration":860552}},{"agent":"205","calls":
	 * 3,"missed":1,"ivr":1550,"ring":21,"talk":3808,"hold":579,"idle":{"calls":3,
	 * "duration":285095}},{"agent":"208","calls":1,"missed":1,"ivr":438,"ring":6,
	 * "talk":71,"hold":0,"idle":{"calls":1,"duration":443889}},{"agent":"221",
	 * "calls":2,"missed":0,"ivr":200,"ring":17,"talk":1415,"hold":761,"idle":{
	 * "calls":2,"duration":505343}},{"agent":"223","calls":2,"missed":0,"ivr":371,
	 * "ring":7,"talk":581,"hold":0,"idle":{"calls":2,"duration":56441}}],
	 * "redirect_waiting":0,"redirect_ringing":0,"redirect_anonymous":0,
	 * "hangup_waiting":3,"hangup_ringing":1,"user_interaction":0,
	 * "admin_interaction":0,"max_calls":0,"soap":0,"other":6}
	 */
	/*
	 * https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/acds
	 * [{"name":"300","display":"Business-Q","agents":["203","205","208","221"],
	 * "calls":0},{"name":"303","display":"SD_Q","agents":["219","218"],"calls":0},{
	 * "name":"305","display":"Activata","agents":["270","273"],"calls":1},{"name":
	 * "306","display":"Res-Q","agents":["223","203","205","208","211","212"],
	 * "calls":1},{"name":"307","display":"Mobile_CB-Q","agents":["203","205","208",
	 * "211","212"],"calls":1},{"name":"308","display":"","agents":[],"calls":0}]
	 */
}
