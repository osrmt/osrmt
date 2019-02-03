package com.osframework.framework.utility;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Enumeration;

import com.osframework.framework.logging.Debug;
public class SerializeUtility {
	
	/**
	 * Serialize the object o to binary file
	 * 
	 * @param file
	 * @param o
	 * @throws IOException
	 */
	public static void serialize(File file, Object o) throws IOException {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try
		{
			fos = new FileOutputStream(file);
			out = new ObjectOutputStream(fos);
			out.writeObject(o);
		}
		finally 
		{
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * Deserialize the file to object
	 * 
	 * @param file
	 * @param o
	 * @throws IOException
	 */
	public static Object deserialize(File file) throws IOException, ClassNotFoundException {
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try
		{
			fis = new FileInputStream(file);
			in = new ObjectInputStream(fis);
			Object o = in.readObject();
			return o;
		}
		finally 
		{
			if (in != null) {
				in.close();
			}
		}
	}

	/**
	 * Serialize the object o to xml file
	 * 
	 * @param file
	 * @param o
	 * @throws IOException
	 */
	public static void serializeXML(File file, Enumeration e1) throws IOException {
		XMLEncoder encoder = null;
		try
		{
			encoder = new XMLEncoder( 
			          new BufferedOutputStream( 
			            new FileOutputStream(file)));
			while (e1.hasMoreElements()) {
				Object o = e1.nextElement();
				encoder.writeObject(o);
				encoder.flush();
			}
		} catch (Exception ex) {
			Debug.LogException("SerializeUtility", ex);
		}
		finally 
		{
			if (encoder != null) {
				encoder.close();
			}
		}
	}

	public static void deserializeXML(File file, Collection list) throws Exception {
		XMLDecoder decoder = null;
		try {
			decoder = new XMLDecoder( 
				      new BufferedInputStream( 
						        new FileInputStream(file)));
	        while (true) {
		        list.add(decoder.readObject());
	        }
		} catch (java.lang.ArrayIndexOutOfBoundsException ae) {
			
		} catch (Exception ex) {
			Debug.LogException("ConnectionProperty", ex);
		} finally {
			if (decoder != null) {
				decoder.close();
			}
		}
	}
}
