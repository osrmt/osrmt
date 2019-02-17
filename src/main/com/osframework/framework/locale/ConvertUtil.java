package com.osframework.framework.locale;

import java.io.*;

public class ConvertUtil {

	
	 /**
	  * Convert ISO8859-1 format string (which is the default sent by IE
	  * to the UTF-8 format that the database is in.
	  */
	 public static String toUTF8(String isoString)
	 {
		 return isoString;
		 /*
	  String utf8String = null;
	  if (null != isoString && !isoString.equals(""))
	  {
	   try
	   {
	    //byte[] stringBytesISO = isoString.getBytes("ISO-8859-1");
	    //utf8String = new String(stringBytesISO, "UTF-8");
		   System.out.println("v2");
		   System.out.println(isoString);
		   byte[] sbytes = isoString.getBytes("UTF-8");
		   utf8String = new String(sbytes);
		   System.out.println(utf8String);
	   }
	   catch(UnsupportedEncodingException e)
	   {
	    // As we can't translate just send back the best guess.
	    System.out.println("UnsupportedEncodingException is: " + e.getMessage());
	    utf8String = isoString;
	   }
	  }
	  else
	  {
	   utf8String = isoString;
	  }
	  return utf8String;
	  */
	 }

}
