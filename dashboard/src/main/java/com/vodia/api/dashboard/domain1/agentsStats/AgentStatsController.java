package com.vodia.api.dashboard.domain1.agentsStats;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AgentStatsController {

	private static final Logger log = LoggerFactory.getLogger(AgentStatsController.class);
	
	@Autowired
	private AgentStatsService ass;

	
	@GetMapping("/getAgentStatsToDashboard")
	private AgentStatsToDashboard getAgentStatsToDashboard(HttpServletRequest request) {
		//List<ACD_WallBoard_QueueWise> acd_wallboard_queue_list;
		
		log.info("calling agentStatsToDashboard");
		return ass.agentStatsToDashboard(request);
		
	}
}
