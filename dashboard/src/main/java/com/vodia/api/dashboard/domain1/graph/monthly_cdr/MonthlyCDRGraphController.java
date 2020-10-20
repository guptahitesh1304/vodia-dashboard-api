package com.vodia.api.dashboard.domain1.graph.monthly_cdr;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MonthlyCDRGraphController {
	
	private static final Logger log = LoggerFactory.getLogger(MonthlyCDRGraphController.class);
	
	@Autowired
	private MonthlyCDRGraphService mcgs;
	

	/*
	 * Sample Response
	 * {"totalInboundCalls":237,"totalOutboundCalls":139,"totalCallRecieved":624,
	 * "totalCallAnswered":376,"totalCallInQueue":3,"totalATT":"61:13:6","totalAWT":
	 * "35:33:30","longestAHT":"115:31:52","longestAWT":"92:5:0","totalCallAbnd":80,
	 * "totalMissedCalls":248}
	 */
	@GetMapping("/getMonthlyCDRGraphData")
	private MonthlyCDRGraphData getMonthlyCDRGraph(HttpServletRequest request) {
		
		log.debug("in getMonthlyCDRGraph contoller");
		
		return mcgs.getMonthlyCDRGraph(request);
		
	}

}

