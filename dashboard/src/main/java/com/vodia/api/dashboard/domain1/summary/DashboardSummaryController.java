package com.vodia.api.dashboard.domain1.summary;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.vodia.api.dashboard.domain1.queue.ACD_WallBoard_QueueWise;

@RestController
public class DashboardSummaryController {
	
	private static final Logger log = LoggerFactory.getLogger(DashboardSummaryController.class);
	
	@Autowired
	private DashboardSummaryService dss;
	

	/*
	 * Sample Response
	 * {"totalInboundCalls":237,"totalOutboundCalls":139,"totalCallRecieved":624,
	 * "totalCallAnswered":376,"totalCallInQueue":3,"totalATT":"61:13:6","totalAWT":
	 * "35:33:30","longestAHT":"115:31:52","longestAWT":"92:5:0","totalCallAbnd":80,
	 * "totalMissedCalls":248}
	 */
	@GetMapping("/getQueueDashboardSummary")
	@CrossOrigin(origins = "*")
	private DashboardSummary getQueueDashboardSummary(HttpServletRequest request) {
		
		log.debug("in getQueueDashboardSummary contoller");
		
		return dss.getDashboardSummary(request);
		
	}
	
	@GetMapping("/getACDByQueueDash/{qid}")
	@CrossOrigin(origins = "*")
	private ACDInfo getACD(HttpServletRequest request,
			@PathVariable("qid") String qid) {

		return dss.getACD(request, qid);

	}
	
	@GetMapping("/getACDForAllQueueDash")
	@CrossOrigin(origins = "*")
	private List<ACDInfo> getACDForAllQueue(HttpServletRequest request) {
		//List<ACD_WallBoard_QueueWise> acd_wallboard_queue_list;
		
		log.debug("calling getACDForAllQueue");
		return dss.getACDForAllQueue(request);
		
	}

}
