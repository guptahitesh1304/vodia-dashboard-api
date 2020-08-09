package com.vodia.api.dashboard.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.*;

public class TestLogic {
  public static String getCurrentTimeStamp() {
       SimpleDateFormat formDate = new SimpleDateFormat("ddMMyyyy");

       // String strDate = formDate.format(System.currentTimeMillis()); // option 1
       String strDate = formDate.format(new Date()); // option 2
       return strDate;
  } 
  
  public static String LongDateConvert() {
	  Long longDate = 1595572559444L;

	  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	  Date date = null;
	try {
		date = dateFormat.parse(longDate.toString());
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	  System.out.println("Date : "+date);

	  SimpleDateFormat dateFormatNew = new SimpleDateFormat("dd/MM/yyyy");

	  String formattedDate = dateFormatNew.format(date);

	  System.out.println("Formatted date : "+formattedDate);
	return formattedDate;
  }
  
  
  
  @SuppressWarnings("deprecation")
public static String CDRDateConvert() {
	  
	  //"start":"1595572559.444","connect":"1595572559.445","end":"1595572656.860",
	  String duration = null;
	  String dateString = "1596338598.40".replace(".", "");
	  String date2 = "1595572656.860".replace(".", "");
	  String update = date2.replace(".", "");
	 
	  long l1 = 1595572559444L;
	  long l2 = 1595572656860L;
	  SimpleDateFormat dateFormat = new SimpleDateFormat("MM:dd:hh:mm:ss");
	  long long_date = Long.parseLong(dateString);
	  long long_date2 = Long.parseLong(date2);
	  Date d1 = new Date(long_date);
	  Date d2 = new Date(long_date2);
	  System.out.println("NEW DATE ---"+d1+"New END DATE"+d2);
	  //System.out.println("D1-D2"+(d2-d1));
	//d = dateFormat.parse(dateString);
	//System.out.println("Date : "+d);
	  SimpleDateFormat formatDate = new SimpleDateFormat("hh:mm:ss a"); 
	  System.out.println("formatDate.format(d2)"+formatDate.format(d2)); 
	  System.out.println("dateFormat.format(d2)"+dateFormat.format(d2));
	  long difference = d2.getTime() - d1.getTime();
	  
	  int sec = (int) TimeUnit.MILLISECONDS.toSeconds(difference);
	  
	  int minutes = sec/60;
	  int seconds = sec%60;
	  String dur = String.valueOf(minutes)+"." + String.valueOf(seconds);
			  
	  //System.out.println("min"+duration.valueOf(minutes));
	  //System.out.println("difference"+difference);
	  //String converted_Date = d1.toString();
	  //Date d3 = new Date(converted_Date);
	  //System.out.println("converted_Date"+converted_Date);
	return update;
	  
  }
  
  public static String getConvertedFrom() {
		
		String from ="\"099652200\" <sip:099652200@compassoffices.ak1.cloudpbx.net.nz>";
			
	  String from_converted = from.substring(0, from.indexOf("@")).replace("\"", "").replaceAll("\\s", "").replace("<sip:", "-");
	  from_converted = from_converted.substring(0, from_converted.indexOf("-"));
			return from_converted;
		}
  
  public static String getMd5(String input) 
  { 
      try { 

          // Static getInstance method is called with hashing MD5 
          MessageDigest md = MessageDigest.getInstance("MD5"); 

          // digest() method is called to calculate message digest 
          //  of an input digest() return array of byte 
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
	public static  String print_list_to_string(List<Integer> list) {
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
  public static void main (String args[]) {
       //System.out.println(getConvertedFrom());
       //System.out.println(CDRDateConvert());
	  String username = "test";
	  String un = username;
	  String password = "QWasxx951@#!**";
	  String pwd = getMd5(password);
		String auth_value = un + " " + pwd;
		 String result1 = String.format("%s", username, pwd);
		    //System.out.println(result1);
		//System.out.println(auth_value);
		 
		 List<Integer> a = new ArrayList<Integer>();
		 a.add(100);
		 a.add(200);
		 
		 List<String> b = new ArrayList<String>();
		 b.add("100");
		 b.add("300");
		 
		 System.out.println("combine list"+print_list_to_string(a));
		 System.out.println("combine list"+print_trunk_list_to_string(b));
		 
  }
}
