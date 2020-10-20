package com.vodia.api.dashboard.domain1.cdr;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.vodia.api.dashboard.config.LoginController;
import com.vodia.api.dashboard.domain1.agentsStats.AgentStatsService;

@RestController
public class CDRController {
	private static final Logger log = LoggerFactory.getLogger(CDRController.class);
	@Autowired
	private CDRService cdrs;

	@GetMapping("/getacd-cdr/{start}/{end}")
	@CrossOrigin(origins = "*")
	private List<ACD_CDRBean> getCDR(HttpServletRequest request, @PathVariable("start") String start, @PathVariable("end") String end) {

		log.debug("getting data for getacd-cdr/{start}/{end}");
		return cdrs.getCDR(request, start, end);

	}
	
	@GetMapping("/getacd-cdr/{size}")
	@CrossOrigin(origins = "*")
	private List<ACD_CDRBean> getCDR(HttpServletRequest request, @PathVariable("size") String size) {
		log.debug("getting data for getacd-cdr/{size}");

		return cdrs.getCDR(request, size);

	}
	
	@GetMapping("/getacd-cdr")
	@CrossOrigin(origins = "*")
	private List<ACD_CDRBean> getCDR(HttpServletRequest request) {
		log.debug("getting data for getacd-cdr");

		return cdrs.getCDR(request);

	}
	
	@GetMapping("/getDailyCallsGraph")
	@CrossOrigin(origins = "*")
	private Map<Integer, Long> getDailyCallsVolumeForGraph(HttpServletRequest request) {
		
		long startTime = System.nanoTime();
		log.debug("getting data for getDailyCallsVolumeForGraph"+startTime);

		return cdrs.getDailyCallsVolumeForGraph(request);

	}
}
