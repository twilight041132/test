package com.edu.kindergarten.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.json.JSONObject;

/**
 * File��ز�����
 * 
 */
public class FileUtils {

	/**
	 * �ж��ļ��Ƿ���ڣ�����������򴴽�
	 * @param path
	 * @return boolean
	 */
	public static boolean ensureFileExists(String path){
    	
    	File f = new File(path);
    	if (f.exists()) {
			return true;
		}else {
			int secondSlash = path.indexOf('/', 1);
			if (secondSlash < 1) return false;
			String directoryPath = path.substring(0, secondSlash);
			File directory = new File(directoryPath);
			if (!directory.exists()) {
				return false;
			}
			
			f.getParentFile().mkdirs();
			try {
				return f.createNewFile();
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			return false;
		}
    }
    
	/**
	 * ����json����ָ��Ŀ¼�ļ�
	 * @param path
	 * @param json
	 */
    public static void saveJsonFile(String path, JSONObject json){
    	OutputStream outputStream = null;
    	byte buffer[] = json.toString().getBytes();
    	
    	try {
    		ensureFileExists(path);
    		outputStream = new FileOutputStream(path, false);
    		outputStream.write(buffer);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				outputStream.close();
			} catch (IOException e2) {
				// TODO: handle exception
			}
		}
    	
    }
}
