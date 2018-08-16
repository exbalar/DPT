package com.dpt.subthreads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandExecutor extends Thread{
	
	public static String out="";
	Process _p =null;
	public CommandExecutor(String command) throws IOException{
		_p = Runtime.getRuntime().exec(command);
	}
	@Override
	public void run() {
	    try {
	    	out = "";
	    	boolean flag = false;
	    	BufferedReader br=new BufferedReader(new InputStreamReader(_p.getErrorStream()));
	    	String line ="";
	    	 while((line=br.readLine())!=null)
	    	{
	    		 out = out+line;
	    		 flag = true;
	    	}
	    	br=new BufferedReader(new InputStreamReader(_p.getInputStream()));
	    	 while((line=br.readLine())!=null && !flag)
	    	{
	    		 out = out+line;
	    	}
	        } catch (Exception anExc) {
	            anExc.printStackTrace();
	        }
	    
	    
		
	}

}
