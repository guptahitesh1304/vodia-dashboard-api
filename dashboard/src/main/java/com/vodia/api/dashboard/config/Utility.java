package com.vodia.api.dashboard.config;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utility {

	private static final Logger log = LoggerFactory.getLogger(Utility.class);

	public static String getConvertedTimeInHrsMinSec(Integer input) {

		Integer timeInSec = input;
		int seconds;
		int minutes;
		int hours;
		String convertedTime;
		String hrs = null;
		String mins = null;
		String sec = null;
		log.debug("Enter the number of seconds : " + timeInSec);
		hours = timeInSec / 3600;
		minutes = (timeInSec % 3600) / 60;
		int seconds_output = (timeInSec % 3600) % 60;
		if (hours > 0)
			hrs = Integer.toString(hours);
		if (minutes > 0)
			mins = Integer.toString(minutes);
		sec = Integer.toString(seconds_output);
		if (hrs != null && mins != null && mins != null)
			convertedTime = hrs + ":" + mins + ":" + sec;
		else if (mins != null && mins != null)
			convertedTime = mins + ":" + sec;
		else {
			convertedTime = sec;
		}
		log.debug("Converted Sec to H:M:S " + convertedTime);
		return convertedTime;
	}

	public static String getConvertedDateCDR(String d) {
		log.debug("getConvertedDateCDR date change requested " + d);

		if(d !=null && !d.trim().isEmpty()) {
			String date = convertDateForCorrectValue(d);
		long long_date = Long.parseLong(date.replace(".", ""));
		Date converted_date = new Date(long_date);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh.mm aa");
		log.debug("getConvertedDateCDR " + converted_date.toString());
		return format.format(converted_date).toString();
		}
		else {
			return "";
		}
	}
	
	public static String getConvertedDateQueue(String d) {
		log.debug("getConvertedDateCDR date change requested " + d);

		if(d !=null && !d.trim().isEmpty()) {
			String date = convertDateForCorrectValue(d);
		long long_date = Long.parseLong(date.replace(".", ""));
		Date converted_date = new Date(long_date);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh.mm aa");
		log.debug("getConvertedDateCDR " + converted_date.toString());
		return format.format(converted_date).toString();
		}
		else {
			return "";
		}
	}
	public static String convertDateForCorrectValue(String str) {
		
		String toConvert = str;
		String[] parts = toConvert.split("\\.");
		String afterDecimal = parts[1];
		String beforeDecimal = parts[0];
		int afterDecimalLength = afterDecimal.length();
		if(afterDecimalLength > 2) {
			return toConvert;
		}
		else if(afterDecimalLength == 1) {
			afterDecimal+="00";
			toConvert = beforeDecimal+afterDecimal;
			return toConvert;
		}
		else {
			afterDecimal+="0";
			toConvert = beforeDecimal+afterDecimal;
			return toConvert;
		}
		
	}

	public static String getDuration(String startDate, String endDate) {
		log.debug("getting duration between--" +startDate+"-and-"+endDate);
		String start;
		String end;

		if (startDate != null && !startDate.trim().isEmpty() && endDate != null && !endDate.trim().isEmpty()) {
			start = convertDateForCorrectValue(startDate);
			end = convertDateForCorrectValue(endDate);
			long long_start = Long.parseLong(start.replace(".", ""));
			long long_end = Long.parseLong(end.replace(".", ""));
			Date converted_date_start = new Date(long_start);
			Date converted_date_end = new Date(long_end);

			long difference = converted_date_end.getTime() - converted_date_start.getTime();

			int sec = (int) TimeUnit.MILLISECONDS.toSeconds(difference);
			int minutes = sec / 60;
			int seconds = sec % 60;
			String duration = String.valueOf(minutes) + "." + String.valueOf(seconds);
			log.debug("getDuration" + duration);
			return duration;
		} else
			return "";
	}

	public static String print_ext_list_to_string(List<Integer> list) {
		String combine = "";
		if (list != null && list.size() > 0) {

			combine = (Integer.toString(list.get(0))) + "," + Integer.toString(list.get(1));
		}
		return combine;

	}

	public static String print_trunk_list_to_string(List<String> list) {
		String combine = "";
		if (list != null && list.size() > 0) {

			combine = list.get(0) + "," + list.get(1);
		}
		return combine;

	}

	public static String getConvertedFromAndTo(String from) {
		/*
		 * 
		 * // String from ="\"099652200\" //
		 * <sip:099652200@compassoffices.ak1.cloudpbx.net.nz>";
		 * 
		 * String from_converted = ""; if (from != null && !from.trim().isEmpty()) {
		 * log.debug("change this Phone Number" + from); from_converted =
		 * from.substring(0, from.indexOf("@")).replace("\"", "").replaceAll("\\s", "")
		 * .replace("<sip:", "-").substring(0, from.indexOf("-")); from_converted =
		 * from_converted.substring(0, from_converted.indexOf("-"));
		 * log.debug("getConvertedFromAndTo" + from_converted); } return from_converted;
		 */

		String from_converted = "";
		if (from != null && !from.trim().isEmpty()) {
			log.debug("change this Phone Number" + from);

			from_converted = from.replace("<sip:", "-");// .substring(0, from.indexOf("-"));
			from_converted = from_converted.substring(0, from_converted.indexOf("-")).replace("\"", "")
					.replaceAll("\\s", "");
			log.debug("getConvertedFromAndTo" + from_converted);

		}
		return from_converted;

	}

	

	public static String getConvertedFromAndToPhoneNum(String from) {

		String from_converted = "";
		if (from != null && !from.trim().isEmpty()) {
			log.debug("change this Phone Number" + from);

			from_converted = from.replace("<sip:", "-");// .substring(0, from.indexOf("-"));
			from_converted = from_converted.substring(0, from_converted.indexOf("-")).replace("\"", "")
					.replaceAll("\\s", "");
			log.debug("getConvertedFromAndTo" + from_converted);

		}
		return from_converted;
	}

	public static String getMonthAndYearByJava8() {

		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int monthJava8 = localDate.getMonthValue();
		int year = localDate.getYear();
		String my = new DecimalFormat("00").format(monthJava8) + Integer.toString(year);
		return my;
	}
	
	public static int getHoursFromDateString(String dateString) {
		String s = dateString;// "28/08/2020 08.49 PM";
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh.mm aa", Locale.ENGLISH);
		Date date = null;
		try {
			date = formatter.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date.getHours();
	}
	
	public static String getCurrentTimeStamp() {
		SimpleDateFormat formDate = new SimpleDateFormat("ddMMyyyy");

		// String strDate = formDate.format(System.currentTimeMillis()); // option 1
		String strDate = formDate.format(new Date()); // option 2
		return strDate;
	}
	
	public static boolean isACallBackQueue(String d) {
		
		boolean isACallBackQueue = false;
		String callBackQueue = "307 308 309 310";
		log.debug("isACallBackQueue" + d);

		if(d !=null && !d.trim().isEmpty()) {
			if (callBackQueue.contains(d)) {
				isACallBackQueue = true;
		return isACallBackQueue;
			}
		}
		else {
			return isACallBackQueue;
		}
		return isACallBackQueue;
	}

}
