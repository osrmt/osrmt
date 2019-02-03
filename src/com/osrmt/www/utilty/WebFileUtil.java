package com.osrmt.www.utilty;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.osframework.framework.logging.Debug;
import com.osframework.framework.utility.*;
import org.apache.struts.action.ActionServlet;

public class WebFileUtil {

	
	public static String displayFile(ActionServlet servlet, String filename) {
        try{
            if(!new File(servlet.getServletContext().getRealPath("")+"/reports/resources").exists()){
                new File(servlet.getServletContext().getRealPath("")+"/reports/resources").mkdir();
            }
            
            copyFile(new File(FileProcess.getTemporaryDirectory() + filename), new File(servlet.getServletContext().getRealPath("")+"/reports/resources/" + filename));
            copyDirectory(new File(FileProcess.getTemporaryDirectory() + filename+"_files"), new File(servlet.getServletContext().getRealPath("")+"/reports/resources/" + filename+"_files"));
        } catch(IOException ex){
            Debug.LogException("WebFileUtil", ex);
        }
        return "reports/resources/"+filename;
	}
	
    
    public static void copyDirectory(File sourceDir, File destDir) throws IOException{
        if (sourceDir.exists()) {
            if(!destDir.exists()){
                destDir.mkdir();
            }
            File[] children = sourceDir.listFiles();
            for(File sourceChild : children){
                String name = sourceChild.getName();
                File destChild = new File(destDir, name);
                if(sourceChild.isDirectory()){
                    copyDirectory(sourceChild, destChild);
                } else{
                    copyFile(sourceChild, destChild);
                }
            }
        }
    }
    
    public static void copyFile(File source, File dest) throws IOException{
        if(!dest.exists()){
            dest.createNewFile();
        }
        InputStream in = null;
        OutputStream out = null;
        try{
            in = new FileInputStream(source);
            out = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int len;
            while((len = in.read(buf)) > 0){
                out.write(buf, 0, len);
            }
        } finally{
            in.close();
            out.close();
        }
    }
	
    public static String zipFiles(String fileName) throws IOException {
        File dir = new File(fileName+"_files");
        File[] fileList = dir.listFiles();
        String[] filenames = new String[]{fileName, fileList[0].getPath(), fileList[1].getPath()};
        byte[] buf = new byte[2048];
        String outFilename = fileName+".zip";
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFilename));
            for (int i=0; i<filenames.length; i++) {
                FileInputStream in = new FileInputStream(filenames[i]);
                out.putNextEntry(new ZipEntry(filenames[i]));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
        } catch (IOException e) {
            Debug.LogException("WebFileUtil", e);
            throw e;
        }
        return outFilename;
    }

}
