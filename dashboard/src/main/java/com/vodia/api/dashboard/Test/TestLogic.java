package com.vodia.api.dashboard.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.vodia.api.dashboard.domain1.queue.QueuePerformanceTable;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TestLogic {

	private static DecimalFormat df2 = new DecimalFormat("#.##");

	private static List<QueuePerformanceTable> queuePerformanceTableList = Arrays.asList(

			new QueuePerformanceTable("301", "301", 3, 4, "0", "10.3", 5, 0, "90%"),
			new QueuePerformanceTable("302", "302", 0, 5, "0", "8.3", 2, 0, "98%"),
			new QueuePerformanceTable("303", "303", 1, 2, "46", "20.2", 15, 100, "85%"),
			new QueuePerformanceTable("304", "304", 8, 3, "11:56", "12.1", 1, 40, "90%"),
			new QueuePerformanceTable("305", "305", 1, 2, "0", "20.2", 0, 0, "85%"),
			new QueuePerformanceTable("306", "306", 6, 3, "0", "12.1", 0, 0, "90%"),
			new QueuePerformanceTable("307", "307", 2, 7, "0", "08.3", 0, 0, "90"));

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
		SimpleDateFormat formDate = new SimpleDateFormat("dd/MM/yyyy hh.mm aa");

		// String strDate = formDate.format(System.currentTimeMillis()); // option 1
		String strDate = formDate.format(new Date()); // option 2
		return strDate;
	}

	public static String LongDateConvert() {
		Long longDate = 1597994704410L;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		Date date = null;
		try {
			date = dateFormat.parse(longDate.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Date : " + date);

		SimpleDateFormat dateFormatNew = new SimpleDateFormat("dd/MM/yyyy");

		String formattedDate = dateFormatNew.format(date);

		System.out.println("Formatted date : " + formattedDate);
		return formattedDate;
	}

	public static String getConvertedDate(String date) {
		System.out.println("date change requested " + date);

		long long_date = Long.parseLong(date.replace(".", "")/* .substring(0, date.length() - 3) */);
		Date converted_date = new Date(long_date);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh.mm aa");
		System.out.println("getConvertedDate" + converted_date.toString());
		return format.format(converted_date).toString();
	}

	public static String getAvg() {

		List<QueuePerformanceTable> qptl = queuePerformanceTableList;
		// Integer totalAWTnSec = (int) (list_acd_info_for_all_queue.stream().map(c ->
		// c.getAvgdurationivr())
		// .collect(Collectors.toList())).stream().mapToInt(val ->
		// val).average().orElse(0);
		double avg = qptl.stream().filter(l -> l.getTotalNumberOfCalls() > 0).map(c -> c.getAwt().replace(":", "."))
				.collect(Collectors.toList()).stream().mapToDouble(val -> Double.parseDouble(val)).average().orElse(0);
		// .collect(Collectors.toList())).stream().mapToInt(val ->
		// val).average().orElse(0);

		String avg_s = df2.format(avg);
		return avg_s;

	}

	public static int getMax() {

		List<QueuePerformanceTable> qptl = queuePerformanceTableList;
		// Integer totalAWTnSec = (int) (list_acd_info_for_all_queue.stream().map(c ->
		// c.getAvgdurationivr())
		// .collect(Collectors.toList())).stream().mapToInt(val ->
		// val).average().orElse(0);
		int avg = qptl.stream().map(c -> c.getAwt()).collect(Collectors.toList()).stream()
				.mapToInt(val -> Integer.parseInt(val)).max().orElse(0);
		// .collect(Collectors.toList())).stream().mapToInt(val ->
		// val).average().orElse(0);
		return avg;

	}

	@SuppressWarnings("deprecation")
	public static String CDRDateConvert() {

		// "start":"1595572559.444","connect":"1595572559.445","end":"1595572656.860",
		String duration = null;
		String dateString = "1598417815.193".replace(".", "");
		String date2 = "1598417815.193".replace(".", "");
		String update = date2.replace(".", "");

		long l1 = 1595572559444L;
		long l2 = 1595572656860L;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM:dd:hh:mm:ss");
		long long_date = Long.parseLong(dateString);
		long long_date2 = Long.parseLong(date2);
		Date d1 = new Date(long_date);
		Date d2 = new Date(long_date2);
		System.out.println("NEW DATE ---" + d1 + "New END DATE" + d2);
		// System.out.println("D1-D2"+(d2-d1));
		// d = dateFormat.parse(dateString);
		// System.out.println("Date : "+d);
		SimpleDateFormat formatDate = new SimpleDateFormat("hh:mm:ss a");
		System.out.println("formatDate.format(d2)" + formatDate.format(d2));
		System.out.println("dateFormat.format(d2)" + dateFormat.format(d2));
		long difference = d2.getTime() - d1.getTime();

		int sec = (int) TimeUnit.MILLISECONDS.toSeconds(difference);

		int minutes = sec / 60;
		int seconds = sec % 60;
		String dur = String.valueOf(minutes) + "." + String.valueOf(seconds);

		// System.out.println("min"+duration.valueOf(minutes));
		// System.out.println("difference"+difference);
		// String converted_Date = d1.toString();
		// Date d3 = new Date(converted_Date);
		// System.out.println("converted_Date"+converted_Date);
		return update;

	}

	public static String getConvertedTimeInHrsMinSec(Integer input) {

		Integer timeInSec = input;
		int seconds;
		int minutes;
		int hours;
		String convertedTime;
		String hrs = null;
		String mins = null;
		String sec = null;
		hours = timeInSec / 3600;
		minutes = (timeInSec % 3600) / 60;
		int seconds_output = (timeInSec % 3600) % 60;
		if (hours > 0)
			hrs = Integer.toString(hours);
		if (minutes > 0)
			mins = Integer.toString(minutes);
		sec = Integer.toString(seconds_output);
		convertedTime = hrs + ":" + mins + ":" + sec;
		return convertedTime;
	}

	public static String filterByToday() {

		String dateString = "1597994704.410".replace(".", "");
		return dateString;

	}

	public static String getConvertedFrom() {

		String from = "\"099652200\" <sip:099652200@compassoffices.ak1.cloudpbx.net.nz>";

		String from_converted = from.substring(0, from.indexOf("@")).replace("\"", "").replaceAll("\\s", "")
				.replace("<sip:", "-");
		from_converted = from_converted.substring(0, from_converted.indexOf("-"));
		return from_converted;
	}

	public static String getMd5(String input) {
		try {

			// Static getInstance method is called with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// digest() method is called to calculate message digest
			// of an input digest() return array of byte
			byte[] messageDigest = md.digest(input.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static String print_list_to_string(List<Integer> list) {
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

	public static Integer getMonthOfYear() {
		java.util.Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		return month;

	}

	public static Integer getMonthOfYearByJava8() {

		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int monthJava8 = localDate.getMonthValue();
		return monthJava8;
	}

	public static String getMonthAndYearByJava8() {

		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int monthJava8 = 10;// localDate.getMonthValue();
		int year = localDate.getYear();
		String my = new DecimalFormat("00").format(monthJava8) + Integer.toString(year);
		return my;
	}

	public static String getConvertedDateTest(String date) {
		System.out.println("getConvertedDateTest date change requested " + date);

		long long_date = Long.parseLong(date.replace(".", ""));
		Date converted_date = new Date(long_date);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh.mm aa");
		;
		System.out.println("getConvertedDateTest" + converted_date.toString());
		return format.format(converted_date).toString();
	}

	public static String getConvertedDateCDR(String d) {
		System.out.println("getConvertedDateCDR date change requested " + d);

		if (d != null && !d.trim().isEmpty()) {
			String date = convertDateForCorrectValue(d);
			long long_date = Long.parseLong(date.replace(".", ""));
			Date converted_date = new Date(long_date);
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh.mm aa");
			System.out.println("getConvertedDateCDR " + converted_date.toString());
			return format.format(converted_date).toString();
		} else {
			return "";
		}
	}

	public static String convertDateForCorrectValue(String str) {

		String toConvert = str;
		String[] parts = toConvert.split("\\.");
		String afterDecimal = parts[1];
		String beforeDecimal = parts[0];
		int afterDecimalLength = afterDecimal.length();

		// String separator =".";
		// int sepPos = toConvert.indexOf(separator);
		// String afterDecimal = toConvert.substring(sepPos + separator.length());
		// int afterDecimalLength = toConvert.substring(sepPos +
		// separator.length()).length();
		if (afterDecimalLength > 2) {
			return toConvert;
		} else {
			afterDecimal += "0";
			toConvert = beforeDecimal + afterDecimal;
			return toConvert;
		}

	}

	public static String getDuration(String startDate, String endDate) {
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
			System.out.println("getDuration" + duration);
			return duration;
		} else
			return "";
	}

	public static boolean isACallBackQueue(String d) {

		boolean isACallBackQueue = false;
		String callBackQueue = "307 308 309 310";

		if (d != null && !d.trim().isEmpty()) {
			if (callBackQueue.contains(d)) {
				isACallBackQueue = true;
				return isACallBackQueue;
			}
		} else {
			return isACallBackQueue;
		}
		return isACallBackQueue;
	}

	public static int getAllAgents(){
		List<String> a1 = new ArrayList<String>();
		List<String> a2 = new ArrayList<String>();
		List<String> a3 = new ArrayList<String>();
		List<String> a4 = new ArrayList<String>();
		Collection<Object>ab = new ArrayList<Object>();
		ab.add(a1);
		ab.add(a2);
		ab.add(a3);
		
		a1.add(0,"200");
		a1.add(1,"202");
		a1.add(2,"203");
		a2.add(0,"201");
		a2.add(1,"204");
		a2.add(2,"205");
		a3.add(0,"201");
		a3.add(1,"202");
		a4.add(0,null);
		List<Object> a = new ArrayList<Object>();
//		a.addAll(0,a1);
//		a.addAll(1,a2);
//		a.addAll(2,a3);
//		a.addAll(3,a4);
		
		for(int i=0; i<=4; i++) {
		a.add(i, ab);
		System.out.println("size"+a.size());
		}
		List<Object> distinctElements = a.stream()
                .distinct()
                .collect(Collectors.toList());
		return distinctElements.size();
	}
	public static void main(String args[]) {
		// System.out.println(getConvertedFrom());
		// System.out.println(CDRDateConvert());
		String username = "test";
		String un = username;
		String password = "ou92Q8Qb5hKZy^S^";
		String pwd = getMd5(password);
		String auth_value = un + " " + pwd;
		String result1 = String.format("%s", username, pwd);
		System.out.println(result1);
		System.out.println(auth_value);

		List<Integer> a = new ArrayList<Integer>();
		a.add(100);
		a.add(200);

		List<String> b = new ArrayList<String>();
		b.add("100");
		b.add("300");

		// System.out.println("combine list"+print_list_to_string(a));
		// System.out.println("combine list"+print_trunk_list_to_string(b));

		// System.out.println(getCurrentTimeStamp());

		// System.out.println(getConvertedTimeInHrsMinSec(11454));

		// System.out.println(getConvertedDate("1598417815.193"));

		double numOfCalls = 300.0;
		double numOfCallsWithinServiceLevel = 292.0;
		int sl = (int) (Math.floor((numOfCallsWithinServiceLevel * 100 / numOfCalls) * 100) / 100);
		String sl_i = "20.0".replaceAll("\\.0*$", "");// Integer.toString(sl);

		//System.out.println("Service Level --" + sl_i);
		// System.out.println(getMonthOfYear());
		// System.out.println(getMonthAndYearByJava8());
		// getConvertedDateTest("1598501048.320");
		// getConvertedDateTest("1598526293.575");

		///System.out.println("----------AFTRER CHNAGE -------------");
		// getConvertedDateCDR("1598529053.953");
		// getConvertedDateCDR("1598529056.955");

		// getConvertedDateCDR(convertDateForCorrectValue("1598501048.32"));

		// getDuration("1598529053.953","1598529056.955" );
		// System.out.println(getAvg());
		// System.out.println(getHoursFromDateString("28/08/2020 08.49 PM"));
		long et1 = 2687324700L;
		long et2 = 3657439700L;
		long et3 = 15876614100L;
		long et4 = 8838556600L;
		long et5 = 485900L;

		long convert1 = TimeUnit.SECONDS.convert(et1, TimeUnit.NANOSECONDS);
		long convert2 = TimeUnit.SECONDS.convert(et2, TimeUnit.NANOSECONDS);
		long convert3 = TimeUnit.SECONDS.convert(et3, TimeUnit.NANOSECONDS);
		long convert4 = TimeUnit.SECONDS.convert(et4, TimeUnit.NANOSECONDS);

		long convert5 = TimeUnit.SECONDS.convert(et5, TimeUnit.NANOSECONDS);

       System.out.println(convert1 + " qptl seconds");
        System.out.println(convert2 + " list_acd_queue_wallboard  execution tijme seconds");
        System.out.println(convert3 + " getServiceLevelQueueWise  execution tijme seconds");
        System.out.println(convert4 + " seconds");
        
		 System.out.println(convert5 + " seconds");
//        
//        Date converted_date = new Date();
//        System.out.println(converted_date + " converted_date");
//		SimpleDateFormat format = new SimpleDateFormat("YYYYMMDD");
//		System.out.println(format.format(converted_date));
//		
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//		System.out.println(LocalDate.now().format(formatter));
		//
		//System.out.println("Size"+getAllAgents());
		//System.out.println("isACallBackQueue" + isACallBackQueue("307"));
	}
}
