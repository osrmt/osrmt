package utilities;

import java.io.*;
import java.util.*;

public class FileSystemUtil {
	
	public static void CreateDirectory(String path){
		
		if (!path.endsWith("\\")) {
			path = path + "\\";
		}
		File file = new File(path);
		file.mkdirs();
	}
	
	public static boolean Exists(String path, String filename) {
		if (!path.endsWith("\\")) {
			path = path + "\\";
		}
		File file = new File(path + filename);
		return file.exists();		
	}
	
	/**
	 * Returns the string contents of the file 
	 * 
	 * @param path
	 * @param filename
	 * @return
	 */
	public static String getContents(String path, String filename) {
		if (!Exists(path, filename)) {
			System.err.println("File does not exist: " + path + filename);
			return "";
		} else {
			if (!path.endsWith("\\")) {
				path = path + "\\";
			}
			try {
				File file = new File(path + filename);
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String s = reader.readLine();
				StringBuffer sb = new StringBuffer((int) file.length());
				while (s!=null) {
					sb.append(s);
					sb.append("\r\n");
					s = reader.readLine();
				}
				reader.close();
				return sb.toString();
			} catch (Exception ex) {
				ex.printStackTrace(System.err);
				return "";
			}
		}
		
	}
	
	public static void DeleteFile(String path, String filename) {
		if (!path.endsWith("\\")) {
			path = path + "\\";
		}
		File file = new File(path + filename);
		if (file.exists()) {
			if (!file.delete()) {
				System.err.println("Unable to delete " + path + filename);
			}
		}
	}
	
	public static void CreateFile(String path, String filename, String content) throws IOException {
		if (!path.endsWith("\\")) {
			path = path + "\\";
		}
		File file = new File(path + filename);
		if (file.exists()) {
			if (!file.delete()) {
				System.err.println("Unable to delete " + path + filename);
			}
		}
		file.createNewFile();
		FileWriter fw = new FileWriter(file);
		fw.write(content);
		fw.close();
	}
	
	public static Vector getRequirements(String dir, String to_find) {
		try {
	            int i; 
	            Vector results = new Vector(); 
	            Hashtable hash = new Hashtable();

	            String dir_list[]=(new File(dir)).list(); 
	            for (i = 0; i < dir_list.length; i++) { 
	                File to_test = new File(dir,dir_list[i]); 
	                if (to_test.isDirectory()) { 
	                    Vector v = getRequirements(to_test.getAbsolutePath(),to_find);
                        Enumeration e1 = v.elements();
                        while (e1.hasMoreElements()) {
                        	String next = (String) e1.nextElement();
                        	if (!hash.containsKey(next)) {
                        		results.add(next);
                        		hash.put(next, next);
                        	}
                        }
	                } else { 
	                    if ((to_test.getName()).indexOf(to_find)>-1) { 
	                        String contents = getContents(dir, to_test.getName());
	                        Vector v = getReqFromContent(to_test.getName(), contents);
	                        Enumeration e1 = v.elements();
	                        while (e1.hasMoreElements()) {
	                        	String next = (String) e1.nextElement();
	                        	if (!hash.containsKey(next)) {
	                        		results.add(next);
	                        		hash.put(next, next);
	                        	}
	                        }
	                    }
	                } 
	            } 
	            return results;
		} catch (Exception ex) {
			System.err.println(ex.toString());
			return new Vector();
		}
	}
	
	private static Vector getReqFromContent(String filename, String contents) {
		Vector v = new Vector();
		Hashtable hash = new Hashtable();
		int start = contents.indexOf("req# ");
		while (start >-1) {
			int endOfLine = start + 10;//contents.indexOf("\n", start); 
			if (endOfLine > start && endOfLine < start+50) {
				String req = contents.substring(start,endOfLine).trim();
				if (!hash.containsKey(req)) {
					v.add(req);
					hash.put(req,req);
				}
			}
			start = contents.indexOf("req# ", start+1);
		}
		return v;
	}

}
