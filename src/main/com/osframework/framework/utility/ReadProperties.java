/*
 * ReadProperties.java
 *
 * Created on March 15, 2007, 5:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/**    Copyright (C) 2006  PSC (Poland Solution Center)
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA 
 */
package com.osframework.framework.utility;
import java.util.*;
import java.io.*;
/**
 * This class is for reading properties files, results are returned in HashMap
 * @author Jacek Kowalczyk kowalczykj@tt.com.pl
 */
public class ReadProperties {
    
    /** Creates a new instance of ReadProperties */
    public ReadProperties() {
    }
//    public static String filename2 = "c:\\temp\\plik.txt";
//    public static void main(String []args){
//        getParameters(filename2);
//    }
    /**
     * gets properties from file and returns in hashMap
     * @param fileName name of the properties file, or path to this file
     * @return HashMap with key value pairs, as in the property file
     * property=value of the property
     */
    public static java.util.HashMap<String, String> getParameters(String fileName){
        java.util.HashMap<String, String> map = new java.util.HashMap<String, String>();
        
        try{
            java.io.File file = new java.io.File(fileName);
            if (file.exists()==false){
                System.out.println("file "+fileName+" not found ");
            }else {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;
                System.out.println("lines:");
                String [] pairs = {" "," "};
                while ((line= br.readLine())!=null){
                    //System.out.println(line);
                    if (line.startsWith("#")){
                        System.out.println("this is the comment: "+line);
                    } else if(!line.contains("=")){
                        System.out.println("It is not propper line in property file: "+line);
                    } else if (line.contains("=")){
                        pairs = line.split("=");
                        map.put(pairs[0], pairs[1]);
                        //System.out.println("properties : key "+pairs[0]+", value "+ pairs[1]);
                    }
                    
                    
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            
            return null;
        }
        
        return map;
    }
    
}
