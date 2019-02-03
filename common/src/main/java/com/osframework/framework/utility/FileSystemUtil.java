package com.osframework.framework.utility;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.osframework.framework.logging.Debug;

public class FileSystemUtil {
	
	public static void createDirectory(String path)	{	
		path = FileProcess.getFilePath(path,"");
		File file = new File(path);
		file.mkdirs();
	}
	
	public static boolean exists(String path, String filename) {
		return exists (FileProcess.getFilePath(path, filename));
	}
	
	public static boolean exists(String filepath) {
		File file = new File(filepath);
		return file.exists();		
	}
	
	public static long getSize(String path, String filename) {
		if (!exists(path, filename)) {
			Debug.LogError("FileSystemUtil","File does not exist: " + FileProcess.getFilePath(path, filename));
			return 0;
		} else {
			try {
				File file = new File(FileProcess.getFilePath(path, filename));
				return file.length();
			} catch (Exception ex) {
				Debug.LogException("FileSystemUtil",ex);
				return 0;
			}
		}
	}
	
	public static byte[] getBinaryContents(String path, String filename) {
		if (!exists(path, filename)) {
			Debug.LogError("FileSystemUtil","File does not exist: " + FileProcess.getFilePath(path, filename));
			return null;
		} else {
			try {
				File file = new File(FileProcess.getFilePath(path, filename));

				DataInputStream dis = new DataInputStream(
						new FileInputStream(file));
				byte[] binaryBytes = new byte[(int) file.length()]; 
				dis.readFully(binaryBytes); 
				return binaryBytes;
			} catch (Exception ex) {
				Debug.LogException("FileSystemUtil",ex);
				return null;
			}
		}
	}
	
	public static String getTextContents(String path, String filename) {
		return getTextContents(FileProcess.getFilePath(path, filename));
	}
	/**
	 * Returns the string contents of the file 
	 * 
	 * @param path
	 * @param filename
	 * @return
	 */
	public static String getTextContents(String filepath) {
		if (!exists(filepath)) {
			Debug.LogError("FileSystemUtil","File does not exist: " + filepath);
			return "";
		} else {
			try {
				File file = new File(filepath);
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
	
	public static void deleteFile(String path, String filename) {
		File file = new File(FileProcess.getFilePath(path, filename));
		if (file.exists()) {
			if (!file.delete()) {
				System.err.println("Unable to delete " + path + filename);
			}
		}
	}
	
	public static void createFile(String filepath, String content) throws IOException {
		File file = new File(filepath);
		if (file.exists()) {
			if (!file.delete()) {
				Debug.LogError("FileSystemUtil","Unable to delete " + filepath);
			}
		}
		file.createNewFile();
		FileWriter fw = new FileWriter(file);
		fw.write(content);
		fw.close();
	}
	
	public static void createFile(String path, String filename, String content) throws IOException {
		createDirectory(FileProcess.getFilePath(path, ""));
		createFile(FileProcess.getFilePath(path, filename), content);
	}
	
	public static void createBinaryFile(String path, String filename, byte[] content) throws IOException {
		try {
			createDirectory(FileProcess.getFilePath(path, ""));
			File file = new File(FileProcess.getFilePath(path, filename));
			if (file.exists()) {
				if (!file.delete()) {
					Debug.LogError("FileSystemUtil","Unable to delete " + FileProcess.getFilePath(path, filename));
				}
			}
			file.createNewFile();
			DataOutputStream dos = new DataOutputStream(
					new FileOutputStream(file));
			dos.write(content);
			dos.flush();
			dos.close();
		} catch (Exception ex) {
			Debug.LogException("FileSystemUtility",ex);			
		}
	}
	
	public static File[] getFiles(String dirpath) throws Exception {
		File file = new File(dirpath);
		if (file.exists()) {
			if (file.isDirectory()) {
				return file.listFiles();
			} else {
				throw new IllegalArgumentException("Not a directory: " + dirpath);
			}
		} else {
			throw new FileNotFoundException(dirpath);
		}
	}
	
	public static void copyFile(String sourceDirectory, String sourceFilename, String destinationDirectory) {
		if (!exists(sourceDirectory, sourceFilename)) {
			Debug.LogError("FileSystemUtil","Source file does not exist: " + FileProcess.getFilePath(sourceDirectory, sourceFilename));
		} else if (!exists(destinationDirectory, "")) {
			Debug.LogError("FileSystemUtil","Destination directory does not exist: " + FileProcess.getFilePath(destinationDirectory, ""));
		} else {
			try {
				FileUtils.copyFileToDirectory(new File(FileProcess.getFilePath(sourceDirectory, sourceFilename)), new File(destinationDirectory), true);
				
			} catch (Exception ex) {
				Debug.LogException("FileSystemUtil",ex);
			}
		}
	}
	
	public static void renameDirectory(String fromDir, String toDir) {
		File file = new File(fromDir);
		file.renameTo(new File(toDir));
	}

}
