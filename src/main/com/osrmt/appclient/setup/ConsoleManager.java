package com.osrmt.appclient.setup;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleManager {
	
	private BufferedReader br = null;

	public int getIntInput() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			String input = br.readLine(); 
			if (input == null || input.length() == 0) {
				return 0;
			}
			return Integer.parseInt(input);
		} catch (Exception ex) {
			System.err.print(ex.getMessage());
			System.err.flush();
			return getIntInput();
		}
	}
	
	public String getStringData(String orig) {
		String s = getStringInput();
		if (s == null || s.trim().length() == 0) {
			return orig;
		} else {
			return s.trim().toUpperCase();
		}
	}

	public String getStringInput() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			return br.readLine(); 
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.flush();
			return getStringInput();
		}
	}

	public void d(String s) {
		System.out.println(s);
		System.out.flush();
	}
	
	public void doption(String s) {
		System.out.print(s);
		System.out.flush();
	}
	
	public void nl() {
		System.out.println("");
		System.out.flush();
	}

}

