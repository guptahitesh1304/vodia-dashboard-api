package com.vodia.api.dashboard.domain1.agentsStats;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.vodia.api.dashboard.config.URLConfig;

@Service
public class AgentStatsService {

	private static final Logger log = LoggerFactory.getLogger(AgentStatsService.class);

	private Integer TotalNumberOfAgents;
	private Integer Agent_Ready;
	private Integer Agent_NotReady;
	private Integer Agent_Working;
	private Integer Agent_Talking;

	public List<ExtentionSetting> getAllAgentSetting(HttpServletRequest request) {
		log.info("i am in getAllQueuesName SERVICE ");

		// final String url =
		// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/acds";
		String dn = (String) request.getSession().getAttribute("dn");
		String url_prop = URLConfig.getURL("GET_ALL_EXTENTIONS");
		String GET_ALL_EXTENTIONS = MessageFormat.format(url_prop, dn, dn);
		log.info("GET_ALL_EXTENTIONS URL  -" + GET_ALL_EXTENTIONS);
		
		// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/user/200@compassoffices.ak1.cloudpbx.net.nz/accounts?type=extensions";
		String token = (String) request.getSession().getAttribute("token");

		log.info("TOKEN AFTER LOGIN -" + token);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Cookie", "session=" + token);

		HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

		ResponseEntity<ExtentionSetting[]> response = restTemplate.exchange(GET_ALL_EXTENTIONS, HttpMethod.GET,
				requestEntity, ExtentionSetting[].class);
		List<ExtentionSetting> all_agent_setting = Arrays.asList(response.getBody());
		log.info("retrurning acd_cdr_list" + all_agent_setting.size());
		return all_agent_setting;
		// return queueList;
	}

	public AgentStatsToDashboard agentStatsToDashboard(HttpServletRequest request) {

		log.info("calling agentStatsToDashboard Start");

		List<ExtentionSetting> all_agent_setting = getAllAgentSetting(request);
		AgentStatsToDashboard agent_Stats = new AgentStatsToDashboard();

		if (all_agent_setting != null && all_agent_setting.size() > 0) {
			TotalNumberOfAgents = all_agent_setting.size();
			Agent_Working = getAgentsWorking(all_agent_setting);
			Agent_Talking = getAgentsTalkingn(all_agent_setting);
			Agent_Ready = Agent_Working - Agent_Talking;
			Agent_NotReady = TotalNumberOfAgents - Agent_Ready;
			agent_Stats.setTotalNumberOfAgents(all_agent_setting.size());
			agent_Stats.setAgent_Talking(Agent_Talking);
			agent_Stats.setAgent_Working(Agent_Working);
			agent_Stats.setAgent_NotReady(Agent_NotReady);
			agent_Stats.setAgent_Ready(Agent_Ready);
		}

		log.info("calling agent_Stats END");
		return agent_Stats;

	}

	public int getAgentsLoggedIn(List<ExtentionSetting> agents) {

		int loggedInAgents = 0;

		List<ExtentionSetting> ag = agents;

		long count = ag.stream().filter(c -> c.getLogin()).count();

		return loggedInAgents = (int) count;
	}

	public int getAgentsTalkingn(List<ExtentionSetting> agents) {

		int connectedAgents = 0;

		List<ExtentionSetting> ag = agents;

		long count = ag.stream().filter(c -> c.getChatstatus().equalsIgnoreCase("online")).count();

		return connectedAgents = (int) count;
	}

	public int getAgentsWorking(List<ExtentionSetting> agents) {

		int agentsWorking = 0;

		List<ExtentionSetting> ag = agents;

		long count = ag.stream().filter(c -> c.getLogin()).count();

		return agentsWorking = (int) count;
	}

}