package com.vodia.api.dashboard.domain1.cdr;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import java.util.function.Function;
import com.vodia.api.dashboard.config.URLConfig;
import com.vodia.api.dashboard.config.Utility;

@Service
public class CDRService {

	private static final Logger log = LoggerFactory.getLogger(CDRService.class);
	
	public Map<Integer, Long> getDailyCallsVolumeForGraph(HttpServletRequest request) {

		List<ACD_CDRBean> cdr_converted = new ArrayList<ACD_CDRBean>();
		
		List<Integer> DailyCallsVolumeForGraphConverted = new ArrayList<Integer>();
		long startTime_getCDR = System.nanoTime();
		cdr_converted = getCDRToday(request);
		long endTime_getCDR = System.nanoTime();
		log.debug("time spent in 1 call of getDailyCallsVolumeForGraph getCDR is " +(endTime_getCDR - startTime_getCDR));

		long startTime_Java8_1 = System.nanoTime();
		List<String> DailyCallsVolumeForGraph = cdr_converted.stream().map(c -> c.getStart())
				.collect(Collectors.toList());
		long startTime_Java8_2 = System.nanoTime();
		log.debug("time spent in 2 call of getDailyCallsVolumeForGraph startTime_Java8_1  is " +(startTime_Java8_2 - startTime_Java8_1));
		DailyCallsVolumeForGraph.stream().map(c -> Utility.getHoursFromDateString(c)).forEachOrdered(DailyCallsVolumeForGraphConverted::add);
		log.debug("retrurning cdr_converted List" + DailyCallsVolumeForGraphConverted.size());
		long startTime_Java8_3 = System.nanoTime();
		log.debug("time spent in 3 call of getDailyCallsVolumeForGraph startTime_Java8_2  is " +(startTime_Java8_3 - startTime_Java8_2));
		Map<Integer, Long> frequencyMap =
				DailyCallsVolumeForGraphConverted.stream().collect(Collectors.groupingBy(Function.identity(),
														Collectors.counting()));

		/*
		 * long startTime_Java8_4_loop = System.nanoTime(); log.
		 * info("time spent in 1 call of getDailyCallsVolumeForGraph startTime_Java8_3  is "
		 * +(startTime_Java8_4_loop - startTime_Java8_3)); for (Map.Entry<Integer, Long>
		 * entry : frequencyMap.entrySet()) { System.out.println(entry.getKey() + ": " +
		 * entry.getValue()); } long startTime_Java8_1_loop_end = System.nanoTime();
		 * log.
		 * info("time spent in 1 call of getDailyCallsVolumeForGraph startTime_Java8_4 loop end  is "
		 * +(startTime_Java8_1_loop_end - startTime_Java8_4_loop));
		 */
		return frequencyMap;

	}

	public List<ACD_CDRBean> getCDR(HttpServletRequest request, String start, String end) {
		log.debug("i am in getAllQueuesName SERVICE ");
		List<ACD_CDRBean> cdr_converted = new ArrayList<ACD_CDRBean>();

		try {
			String dn = (String) request.getSession().getAttribute("dn");
			String url_prop = URLConfig.getURL("CDR_URL_WITH_DATE");
			String CDR_URL_WITH_DATE = MessageFormat.format(url_prop, dn, dn, start, end);
			log.debug("CDR_URL URL  -" + CDR_URL_WITH_DATE);

			// final String url =
			// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/cdrs?size=100&page=1";
			String token = (String) request.getSession().getAttribute("token");
			
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.add("Cookie", "session=" + token);

			HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

			ResponseEntity<ACD_CDRBean[]> response = restTemplate.exchange(CDR_URL_WITH_DATE, HttpMethod.GET,
					requestEntity, ACD_CDRBean[].class);
			List<ACD_CDRBean> acd_cdr_complete = Arrays.asList(response.getBody());

			// updaing trunk id by name
			/*
			 * for (int i = 0; i < acd_cdr_complete.size(); i++) { //
			 * log.debug("getting trunk size"+acd_cdr_complete.get(i).getTrunks().size()); if
			 * (acd_cdr_complete.get(i).getTrunks().size() > 0)
			 * acd_cdr_complete.get(i).setTrunks(getTrunkName(dn, token,
			 * acd_cdr_complete.get(i).getTrunks()));
			 * 
			 * }
			 */

			log.debug("retrurning acd_cdr_complete List" + acd_cdr_complete.size());

			// Convert list into another list

			acd_cdr_complete.stream().map(this::getConvertedCDR).forEachOrdered(cdr_converted::add);
			log.debug("retrurning cdr_converted List" + cdr_converted.size());

		}

		catch (NullPointerException e) {
			log.error("Warning: Nullpointer" + e);
		} catch (Exception e) {
			log.error("Warning: Some Other exception"+e);
		}

		return cdr_converted;
		// return queueList;
	}

	public List<ACD_CDRBean> getCDR(HttpServletRequest request, String size) {
		log.debug("i am in getAllQueuesName SERVICE ");

		String dn = (String) request.getSession().getAttribute("dn");
//		String url_prop = URLConfig.getURL("CDR_URL");
//		String CDR_URL = MessageFormat.format(url_prop, dn, dn);
//		log.debug("CDR_URL URL  -" + CDR_URL);

		List<ACD_CDRBean> cdr_converted = new ArrayList<ACD_CDRBean>();
		
		try {
		String url_prop = URLConfig.getURL("CDR_URL_WITH_SIZE");
		String CDR_URL_WITH_SIZE = MessageFormat.format(url_prop, dn, dn, size);
		log.debug("CDR_URL_WITH_SIZE URL  -" + CDR_URL_WITH_SIZE);
				//"https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/cdrs?size="+size+"&page=1";
		String token = (String) request.getSession().getAttribute("token");
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Cookie", "session=" + token);

		HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

		ResponseEntity<ACD_CDRBean[]> response = restTemplate.exchange(CDR_URL_WITH_SIZE, HttpMethod.GET, requestEntity,
				ACD_CDRBean[].class);
		List<ACD_CDRBean> acd_cdr_complete = Arrays.asList(response.getBody());

		// updaing trunk id by name
		for (int i = 0; i < acd_cdr_complete.size(); i++) {
			// log.debug("getting trunk size"+acd_cdr_complete.get(i).getTrunks().size());
			if (acd_cdr_complete.get(i).getTrunks().size() > 0)
				acd_cdr_complete.get(i).setTrunks(getTrunkName(dn,token, acd_cdr_complete.get(i).getTrunks()));

		}

		log.debug("retrurning acd_cdr_complete List" + acd_cdr_complete.size());

		// Convert list into another list

		acd_cdr_complete.stream().map(this::getConvertedCDR).forEachOrdered(cdr_converted::add);
		log.debug("retrurning cdr_converted List" + cdr_converted.size());
		
		}

		catch (NullPointerException e) {
			log.error("Warning: Nullpointer" + e);
		} catch (Exception e) {
			log.error("Warning: Some Other exception"+e);
		}

		return cdr_converted;
		// return queueList;
	}
	public List<ACD_CDRBean> getCDRToday(HttpServletRequest request) {
		log.debug("i am in getCDRToday SERVICE ");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String today = LocalDate.now().format(formatter);

		long startTime = System.nanoTime();
		String dn = (String) request.getSession().getAttribute("dn");
//		String url_prop = URLConfig.getURL("CDR_URL");
//		String CDR_URL = MessageFormat.format(url_prop, dn, dn);
//		log.debug("CDR_URL URL  -" + CDR_URL);

		List<ACD_CDRBean> cdr_converted = new ArrayList<ACD_CDRBean>();
		try {
			String url_prop = URLConfig.getURL("CDR_URL_TODAY");
			String CDR_URL_TODAY = MessageFormat.format(url_prop, dn, dn, today);
			log.debug("CDR_URL_TODAY URL  -" + CDR_URL_TODAY);
			// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/cdrs?size=100&page=1";
			String token = (String) request.getSession().getAttribute("token");
			
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.add("Cookie", "session=" + token);

			HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

			ResponseEntity<ACD_CDRBean[]> response = restTemplate.exchange(CDR_URL_TODAY, HttpMethod.GET,
					requestEntity, ACD_CDRBean[].class);
			List<ACD_CDRBean> acd_cdr_complete = Arrays.asList(response.getBody());
			long endtime = System.nanoTime();
			log.debug("time to get data from CDR_URL_WITH_DEFAULT"+(endtime-startTime));
			log.debug("");
			/*
			 * // updaing trunk id by name for (int i = 0; i < acd_cdr_complete.size(); i++)
			 * { //
			 * log.debug("getting trunk size"+acd_cdr_complete.get(i).getTrunks().size()); if
			 * (acd_cdr_complete.get(i).getTrunks().size() > 0)
			 * acd_cdr_complete.get(i).setTrunks(getTrunkName(dn, token,
			 * acd_cdr_complete.get(i).getTrunks()));
			 * 
			 * }
			 */

			log.debug("retrurning acd_cdr_complete List" + acd_cdr_complete.size());

			long updating_truck_time = System.nanoTime();
			log.debug("updating_truck_time CDR_URL_WITH_DEFAULT"+(updating_truck_time-endtime));
			
			// Convert list into another list

			acd_cdr_complete.stream().map(this::getConvertedCDR).forEachOrdered(cdr_converted::add);
			log.debug("retrurning cdr_converted List" + cdr_converted.size());
			long updating_cdr_time = System.nanoTime();
			log.debug("updating_cdr_time CDR_URL_WITH_DEFAULT"+(updating_cdr_time-updating_truck_time));

		}

		catch (NullPointerException e) {
			log.error("Warning: Nullpointer" + e);
		} catch (Exception e) {
			log.error("Warning: Some Other exception"+e);
		}

		return cdr_converted;
		// return queueList;
	}
	
	public List<ACD_CDRBean> getCDR(HttpServletRequest request) {
		log.debug("i am in getAllQueuesName SERVICE ");

		long startTime = System.nanoTime();
		String dn = (String) request.getSession().getAttribute("dn");
//		String url_prop = URLConfig.getURL("CDR_URL");
//		String CDR_URL = MessageFormat.format(url_prop, dn, dn);
//		log.debug("CDR_URL URL  -" + CDR_URL);

		List<ACD_CDRBean> cdr_converted = new ArrayList<ACD_CDRBean>();
		try {
			String url_prop = URLConfig.getURL("CDR_URL_WITH_DEFAULT");
			String CDR_URL_WITH_DEFAULT = MessageFormat.format(url_prop, dn, dn);
			log.debug("CDR_URL_WITH_DEFAULT URL  -" + CDR_URL_WITH_DEFAULT);
			// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/cdrs?size=100&page=1";
			String token = (String) request.getSession().getAttribute("token");
			
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.add("Cookie", "session=" + token);

			HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

			ResponseEntity<ACD_CDRBean[]> response = restTemplate.exchange(CDR_URL_WITH_DEFAULT, HttpMethod.GET,
					requestEntity, ACD_CDRBean[].class);
			List<ACD_CDRBean> acd_cdr_complete = Arrays.asList(response.getBody());
			long endtime = System.nanoTime();
			log.debug("time to get data from CDR_URL_WITH_DEFAULT"+(endtime-startTime));
			log.debug("");
			/*
			 * // updaing trunk id by name for (int i = 0; i < acd_cdr_complete.size(); i++)
			 * { //
			 * log.debug("getting trunk size"+acd_cdr_complete.get(i).getTrunks().size()); if
			 * (acd_cdr_complete.get(i).getTrunks().size() > 0)
			 * acd_cdr_complete.get(i).setTrunks(getTrunkName(dn, token,
			 * acd_cdr_complete.get(i).getTrunks()));
			 * 
			 * }
			 */

			log.debug("retrurning acd_cdr_complete List" + acd_cdr_complete.size());

			long updating_truck_time = System.nanoTime();
			log.debug("updating_truck_time CDR_URL_WITH_DEFAULT"+(updating_truck_time-endtime));
			
			// Convert list into another list

			acd_cdr_complete.stream().map(this::getConvertedCDR).forEachOrdered(cdr_converted::add);
			log.debug("retrurning cdr_converted List" + cdr_converted.size());
			long updating_cdr_time = System.nanoTime();
			log.debug("updating_cdr_time CDR_URL_WITH_DEFAULT"+(updating_cdr_time-updating_truck_time));

		}

		catch (NullPointerException e) {
			log.error("Warning: Nullpointer" + e);
		} catch (Exception e) {
			log.error("Warning: Some Other exception"+e);
		}

		return cdr_converted;
		// return queueList;
	}
	
	public ACD_CDRBean getConvertedCDR(ACD_CDRBean cdr_obj_to_convert) {
		log.debug("inside getConvertedCDR");
		ACD_CDRBean converted_cdr = new ACD_CDRBean();
		try {
			converted_cdr.setCall_id(cdr_obj_to_convert.getCall_id());
			converted_cdr.setExtensions(cdr_obj_to_convert.getExtensions());
			converted_cdr.setTrunks(cdr_obj_to_convert.getTrunks());
			converted_cdr.setFrom(Utility.getConvertedFromAndTo(cdr_obj_to_convert.getFrom()));
			converted_cdr.setTo(Utility.getConvertedFromAndTo(cdr_obj_to_convert.getTo()));
			converted_cdr.setStart(Utility.getConvertedDateCDR(cdr_obj_to_convert.getStart()));
			converted_cdr.setEnd(Utility.getConvertedDateCDR(cdr_obj_to_convert.getEnd()));
			converted_cdr.setId(cdr_obj_to_convert.getId());
			converted_cdr.setConnect(Utility.getDuration(cdr_obj_to_convert.getStart(), cdr_obj_to_convert.getEnd()));
		}

		catch (NullPointerException e) {
			log.error("Warning: Nullpointer" + e);
		} catch (Exception e) {
			log.error("Warning: Some Other exception" + e);
		}

		return converted_cdr;

	}

	
	public Dashboard_CDR getConvertedCDRDashboard(ACD_CDRBean cdr_obj_to_convert) {
		log.debug("inside getConvertedCDRDashboard");
		Dashboard_CDR converted_cdr = new Dashboard_CDR();
		try {
			converted_cdr.setCall_id(cdr_obj_to_convert.getCall_id());
			converted_cdr.setExtensions(Utility.print_ext_list_to_string(cdr_obj_to_convert.getExtensions()));
			converted_cdr.setTrunks(Utility.print_trunk_list_to_string(cdr_obj_to_convert.getTrunks()));
			converted_cdr.setFrom(Utility.getConvertedFromAndTo(cdr_obj_to_convert.getFrom()));
			converted_cdr.setTo(Utility.getConvertedFromAndTo(cdr_obj_to_convert.getTo()));
			converted_cdr.setStart(Utility.getConvertedDateCDR(cdr_obj_to_convert.getStart()));
			converted_cdr.setEnd(Utility.getConvertedDateCDR(cdr_obj_to_convert.getEnd()));
			converted_cdr.setId(cdr_obj_to_convert.getId());
			converted_cdr.setConnect(Utility.getDuration(cdr_obj_to_convert.getStart(), cdr_obj_to_convert.getEnd()));

		}

		catch (NullPointerException e) {
			log.error("Warning: Nullpointer" + e);
		} catch (Exception e) {
			log.error("Warning: Some Other exception" + e);
		}

		return converted_cdr;

	}




	public static List<String> getTrunkName(String dn, String token, List<String> trunck) {
		
		List<String> trunk_name_list = new ArrayList<String>();

		try {

			String url_prop = URLConfig.getURL("GET_ALL_TRUNK_NAME_URL");
			String GET_ALL_TRUNK_NAME_URL = MessageFormat.format(url_prop, dn, dn);
			log.debug("GET_ALL_TRUNK_NAME_URL URL  -" + GET_ALL_TRUNK_NAME_URL);

			// final String URL =
			// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/domain_trunks";
			String trunkId = trunck.get(0);
			String trunk_name = null;
			

			
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.add("Cookie", "session=" + token);

			HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

			ResponseEntity<Trunks[]> response = restTemplate.exchange(GET_ALL_TRUNK_NAME_URL, HttpMethod.GET,
					requestEntity, Trunks[].class);
			List<Trunks> trunks_list = Arrays.asList(response.getBody());
			log.debug("retrurning acd_cdr_complete List" + trunks_list.size());

			Trunks trunks_obj = trunks_list.stream().filter(trunk -> trunkId.equals(trunk.getId())).findAny()
					.orElse(null);

			trunk_name = trunks_obj.getName();
			trunk_name_list.add(trunk_name);

		}

		catch (NullPointerException e) {
			log.error("Warning: Nullpointer" + e);
		} catch (Exception e) {
			log.error("Warning: Some Other exception" +e);
		}

		return trunk_name_list;

	}


}
