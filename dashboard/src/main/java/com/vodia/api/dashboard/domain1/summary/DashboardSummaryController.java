package com.vodia.api.dashboard.domain1.summary;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardSummaryController {
	
	private static final Logger log = LoggerFactory.getLogger(DashboardSummaryController.class);
	
	@Autowired
	private DashboardSummaryService dss;
	

	@GetMapping("/getQueueDashboardSummary")
	private DashboardSummary getQueueDashboardSummary(HttpServletRequest request) {
		
		log.info("in getQueueDashboardSummary contoller");
		
		return dss.getDashboardSummary(request);
		
	}

}
