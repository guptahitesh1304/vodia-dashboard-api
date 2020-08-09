package com.vodia.api.dashboard.domain1.cdr;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vodia.api.dashboard.config.URLConfig;

@Service
public class CDRService {

	private static final Logger log = LoggerFactory.getLogger(CDRService.class);

	public List<ACD_CDRBean> getCDR(HttpServletRequest request, String start, String end) {
		log.info("i am in getAllQueuesName SERVICE ");

		String dn = (String) request.getSession().getAttribute("dn");
		String url_prop = URLConfig.getURL("CDR_URL_WITH_DATE");
		String CDR_URL_WITH_DATE = MessageFormat.format(url_prop, dn, dn, start, end);
		log.info("CDR_URL URL  -" + CDR_URL_WITH_DATE);

		List<ACD_CDRBean> cdr_converted = new ArrayList<ACD_CDRBean>();
		// final String url =
		// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/cdrs?size=100&page=1";
		String token = (String) request.getSession().getAttribute("token");
		log.info("TOKEN AFTER LOGIN -" + token);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Cookie", "session=" + token);

		HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

		ResponseEntity<ACD_CDRBean[]> response = restTemplate.exchange(CDR_URL_WITH_DATE, HttpMethod.GET, requestEntity,
				ACD_CDRBean[].class);
		List<ACD_CDRBean> acd_cdr_complete = Arrays.asList(response.getBody());

		// updaing trunk id by name
		for (int i = 0; i < acd_cdr_complete.size(); i++) {
			// log.info("getting trunk size"+acd_cdr_complete.get(i).getTrunks().size());
			if (acd_cdr_complete.get(i).getTrunks().size() > 0)
				acd_cdr_complete.get(i).setTrunks(getTrunkName(dn,token, acd_cdr_complete.get(i).getTrunks()));

		}

		log.info("retrurning acd_cdr_complete List" + acd_cdr_complete.size());

		// Convert list into another list

		acd_cdr_complete.stream().map(this::getConvertedCDR).forEachOrdered(cdr_converted::add);
		log.info("retrurning cdr_converted List" + cdr_converted.size());

		return cdr_converted;
		// return queueList;
	}

	public List<ACD_CDRBean> getCDR(HttpServletRequest request, String size) {
		log.info("i am in getAllQueuesName SERVICE ");

		String dn = (String) request.getSession().getAttribute("dn");
//		String url_prop = URLConfig.getURL("CDR_URL");
//		String CDR_URL = MessageFormat.format(url_prop, dn, dn);
//		log.info("CDR_URL URL  -" + CDR_URL);

		List<ACD_CDRBean> cdr_converted = new ArrayList<ACD_CDRBean>();
		final String CDR_URL = "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/cdrs?size="+size+"&page=1";
		String token = (String) request.getSession().getAttribute("token");
		log.info("TOKEN AFTER LOGIN -" + token);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Cookie", "session=" + token);

		HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

		ResponseEntity<ACD_CDRBean[]> response = restTemplate.exchange(CDR_URL, HttpMethod.GET, requestEntity,
				ACD_CDRBean[].class);
		List<ACD_CDRBean> acd_cdr_complete = Arrays.asList(response.getBody());

		// updaing trunk id by name
		for (int i = 0; i < acd_cdr_complete.size(); i++) {
			// log.info("getting trunk size"+acd_cdr_complete.get(i).getTrunks().size());
			if (acd_cdr_complete.get(i).getTrunks().size() > 0)
				acd_cdr_complete.get(i).setTrunks(getTrunkName(dn,token, acd_cdr_complete.get(i).getTrunks()));

		}

		log.info("retrurning acd_cdr_complete List" + acd_cdr_complete.size());

		// Convert list into another list

		acd_cdr_complete.stream().map(this::getConvertedCDR).forEachOrdered(cdr_converted::add);
		log.info("retrurning cdr_converted List" + cdr_converted.size());

		return cdr_converted;
		// return queueList;
	}
	
	public List<ACD_CDRBean> getCDR(HttpServletRequest request) {
		log.info("i am in getAllQueuesName SERVICE ");

		String dn = (String) request.getSession().getAttribute("dn");
//		String url_prop = URLConfig.getURL("CDR_URL");
//		String CDR_URL = MessageFormat.format(url_prop, dn, dn);
//		log.info("CDR_URL URL  -" + CDR_URL);

		List<ACD_CDRBean> cdr_converted = new ArrayList<ACD_CDRBean>();
		final String CDR_URL = "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/cdrs?size=100&page=1";
		String token = (String) request.getSession().getAttribute("token");
		log.info("TOKEN AFTER LOGIN -" + token);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Cookie", "session=" + token);

		HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

		ResponseEntity<ACD_CDRBean[]> response = restTemplate.exchange(CDR_URL, HttpMethod.GET, requestEntity,
				ACD_CDRBean[].class);
		List<ACD_CDRBean> acd_cdr_complete = Arrays.asList(response.getBody());

		// updaing trunk id by name
		for (int i = 0; i < acd_cdr_complete.size(); i++) {
			// log.info("getting trunk size"+acd_cdr_complete.get(i).getTrunks().size());
			if (acd_cdr_complete.get(i).getTrunks().size() > 0)
				acd_cdr_complete.get(i).setTrunks(getTrunkName(dn,token, acd_cdr_complete.get(i).getTrunks()));

		}

		log.info("retrurning acd_cdr_complete List" + acd_cdr_complete.size());

		// Convert list into another list

		acd_cdr_complete.stream().map(this::getConvertedCDR).forEachOrdered(cdr_converted::add);
		log.info("retrurning cdr_converted List" + cdr_converted.size());

		return cdr_converted;
		// return queueList;
	}
	
	public ACD_CDRBean getConvertedCDR(ACD_CDRBean cdr_obj_to_convert) {
		ACD_CDRBean converted_cdr = new ACD_CDRBean();
		converted_cdr.setCall_id(cdr_obj_to_convert.getCall_id());
		converted_cdr.setExtensions(cdr_obj_to_convert.getExtensions());
		converted_cdr.setTrunks(cdr_obj_to_convert.getTrunks());
		converted_cdr.setFrom(getConvertedFromAndTo(cdr_obj_to_convert.getFrom()));
		converted_cdr.setTo(getConvertedFromAndTo(cdr_obj_to_convert.getTo()));
		converted_cdr.setStart(getConvertedDate(cdr_obj_to_convert.getStart()));
		converted_cdr.setEnd(getConvertedDate(cdr_obj_to_convert.getEnd()));
		converted_cdr.setId(cdr_obj_to_convert.getId());
		converted_cdr.setConnect(getDuration(cdr_obj_to_convert.getStart(), cdr_obj_to_convert.getEnd()));
		return converted_cdr;

	}
	
	public Dashboard_CDR getConvertedCDRDashboard(ACD_CDRBean cdr_obj_to_convert) {
		Dashboard_CDR converted_cdr = new Dashboard_CDR();
		converted_cdr.setCall_id(cdr_obj_to_convert.getCall_id());
		converted_cdr.setExtensions(print_ext_list_to_string(cdr_obj_to_convert.getExtensions()));
		converted_cdr.setTrunks(print_trunk_list_to_string(cdr_obj_to_convert.getTrunks()));
		converted_cdr.setFrom(getConvertedFromAndTo(cdr_obj_to_convert.getFrom()));
		converted_cdr.setTo(getConvertedFromAndTo(cdr_obj_to_convert.getTo()));
		converted_cdr.setStart(getConvertedDate(cdr_obj_to_convert.getStart()));
		converted_cdr.setEnd(getConvertedDate(cdr_obj_to_convert.getEnd()));
		converted_cdr.setId(cdr_obj_to_convert.getId());
		converted_cdr.setConnect(getDuration(cdr_obj_to_convert.getStart(), cdr_obj_to_convert.getEnd()));
		return converted_cdr;

	}

	public String getDuration(String start, String end) {

		long long_start = Long.parseLong(start.replace(".", ""));
		long long_end = Long.parseLong(end.replace(".", ""));
		Date converted_date_start = new Date(long_start);
		Date converted_date_end = new Date(long_end);

		long difference = converted_date_end.getTime() - converted_date_start.getTime();

		int sec = (int) TimeUnit.MILLISECONDS.toSeconds(difference);
		int minutes = sec / 60;
		int seconds = sec % 60;
		String duration = String.valueOf(minutes) + "." + String.valueOf(seconds);
		log.info("getDuration" + duration);
		return duration;
	}

	public String getConvertedDate(String date) {

		long long_date = Long.parseLong(date.replace(".", ""));
		Date converted_date = new Date(long_date);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh.mm aa");
		;
		log.info("getConvertedDate" + converted_date.toString());
		return format.format(converted_date).toString();
	}

	public static String getConvertedFromAndTo(String from) {

		// String from ="\"099652200\"
		// <sip:099652200@compassoffices.ak1.cloudpbx.net.nz>";

		String from_converted = from.substring(0, from.indexOf("@")).replace("\"", "").replaceAll("\\s", "")
				.replace("<sip:", "-").substring(0, from.indexOf("-"));
		from_converted = from_converted.substring(0, from_converted.indexOf("-"));
		log.info("getConvertedFromAndTo" + from_converted);
		return from_converted;
	}

	public static List<String> getTrunkName(String dn, String token, List<String> trunck) {

		
		String url_prop = URLConfig.getURL("GET_ALL_TRUNK_NAME_URL");
		String GET_ALL_TRUNK_NAME_URL = MessageFormat.format(url_prop, dn, dn);
		log.info("GET_ALL_TRUNK_NAME_URL URL  -" + GET_ALL_TRUNK_NAME_URL);

		//final String URL = "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/domain_trunks";
		String trunkId = trunck.get(0);
		String trunk_name = null;
		List<String> trunk_name_list = new ArrayList<String>();

		log.info("TOKEN AFTER LOGIN -" + token);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Cookie", "session=" + token);

		HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

		ResponseEntity<Trunks[]> response = restTemplate.exchange(GET_ALL_TRUNK_NAME_URL, HttpMethod.GET, requestEntity, Trunks[].class);
		List<Trunks> trunks_list = Arrays.asList(response.getBody());
		log.info("retrurning acd_cdr_complete List" + trunks_list.size());

		Trunks trunks_obj = trunks_list.stream().filter(trunk -> trunkId.equals(trunk.getId())).findAny().orElse(null);

		trunk_name = trunks_obj.getName();
		trunk_name_list.add(trunk_name);

		return trunk_name_list;

	}
	
	public static  String print_ext_list_to_string(List<Integer> list) {
		String combine="";
		if(list !=null && list.size()>0) {
			
			 combine = (Integer.toString(list.get(0)))+","+Integer.toString(list.get(1));
		}
		return combine;
		
	}
	
	public static  String print_trunk_list_to_string(List<String> list) {
		String combine="";
		if(list !=null && list.size()>0) {
			
			 combine = list.get(0)+","+list.get(1);
		}
		return combine;
		
	}

}
