package com.dpt.subthreads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LogCommandExecutor extends Thread{
	
	public static List<String> out= null;
	Process _p =null;
	public LogCommandExecutor(String command) throws IOException{
		_p = Runtime.getRuntime().exec(command);
	}
	@Override
	public void run() {
	    try {
	    	out = new ArrayList<>();
	    	boolean flag = false;
	    	BufferedReader br=new BufferedReader(new InputStreamReader(_p.getErrorStream()));
	    	String line ="";
	    	 while((line=br.readLine())!=null && (line.contains("[") && line.contains("]")))
	    	{
	    		 out.add(line.substring((line.indexOf("[")+1), line.indexOf("]")));
	    		 flag = true;
	    	}
	    	br=new BufferedReader(new InputStreamReader(_p.getInputStream()));
	    	 while((line=br.readLine())!=null && !flag && (line.contains("[") && line.contains("]"))) 
	    	{
	    		 out.add(line.substring((line.indexOf("[")+1), line.indexOf("]")));
	    	}
	        } catch (Exception anExc) {
	            anExc.printStackTrace();
	        }
	}

}
