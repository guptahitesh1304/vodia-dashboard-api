package com.vodia.api.dashboard.domain1.queue.qperformanceTableNew;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QPerformanceTableDashboardNewController {
	
	private static final Logger log = LoggerFactory.getLogger(QPerformanceTableDashboardNewController.class);

	@Autowired
	private QPerformanceTableDashboardNewService qpts;
	
	@SuppressWarnings("unchecked")
	@GetMapping("/getQueuePerformanceTableForDashboard")
	@CrossOrigin(origins = "*")
	private List<QPerformanceTableDashboardNew> getQueuePerformanceTableForDashboard(HttpServletRequest request) {

		log.debug("in contoller getQueuePerformanceTableForDashboard");
		return qpts.getQueuePerformanceTableForDashboard(request);

	}
	@SuppressWarnings("unchecked")
	@GetMapping("/getLiveACDSStatsAllQueue")
	@CrossOrigin(origins = "*")
	private List<LiveACDSStatsAllQueue> getLiveACDSStatsAllQueue(HttpServletRequest request) {

		log.debug("in contoller getLiveACDSStatsAllQueue");
		return qpts.getLiveACDSStatsAllQueue(request);

	}
	@SuppressWarnings("unchecked")
	@GetMapping("/getPerformanceByQueuePart2/{qid}")
	@CrossOrigin(origins = "*")
	private LiveACDSStatsAllQueue_Part2 getPerformanceByQueuePart2(HttpServletRequest request, @PathVariable("qid") String qid) {

		log.debug("in contoller getAgentPerformanceByQueue");
		return qpts.getPerformanceByQueuePart2(request, qid);

	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/getAgentPerformanceByQueue/{qid}")
	@CrossOrigin(origins = "*")
	private AgentsLiveStatsQWise getAgentPerformanceByQueue(HttpServletRequest request, @PathVariable("qid") String qid) {

		log.debug("in contoller getAgentPerformanceByQueue");
		return qpts.getAgentPerformanceByQueue(request, qid);

	}

	@SuppressWarnings("unchecked")
	@GetMapping("/getAgentPerformanceComplete")
	@CrossOrigin(origins = "*")
	private List<AgentsLiveStatsQWise> getAgentPerformanceComplete(HttpServletRequest request) {

		log.debug("in contoller getAgentPerformanceByQueue");
		return qpts.getAgentPerformanceComplete(request);

	}

}
