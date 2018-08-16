package com.dpt.main;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dpt.subthreads.CommandExecutor;
import com.dpt.subthreads.LogCommandExecutor;
import com.dpt.subthreads.ShowCommandExecutor;
import com.dpt.utilities.Utility;

public class Run {
	public static String out="";
	public static void main(String[] args) throws Exception {

		// invoke the process, keeping a handle to it for later...
		//final Process _p1 = Runtime.getRuntime().exec("rmdir /s /q D:\\hackathon\\DPT\\myApp");
/*		final Process _p = Runtime.getRuntime().exec("git clone https://github.com/exbalar/myApp.git");
		Thread t = new Thread() {
		    public void run() {
		    try {
		    	BufferedReader br=new BufferedReader(new InputStreamReader(_p.getErrorStream()));
		    	String line ="";
		    	 while((line=br.readLine())!=null)
		    	{
		    		 out = out+line;
		    	}
		        } catch (Exception anExc) {
		            anExc.printStackTrace();
		        }
		    }
		};*/
		CommandExecutor rmvExisting = new CommandExecutor("cmd.exe /c rmdir /s /q D:\\hackathon\\DPT\\myApp");
		rmvExisting.start();
		rmvExisting.join();
		CommandExecutor clone = new CommandExecutor("git clone https://github.com/exbalar/myApp.git");
		clone.start();
		clone.join();
		CommandExecutor switchtoFolder = new CommandExecutor("cmd.exe /c D:");
		switchtoFolder.start();
		switchtoFolder.join();
		//git log --pretty=oneline
		CommandExecutor switchtoFolder2 = new CommandExecutor("cmd.exe /c cd D:\\hackathon\\DPT\\myApp\\ && git log --pretty=oneline");
		switchtoFolder2.start();
		switchtoFolder2.join();
		
		String commitId = Utility.getCommitId(CommandExecutor.out);
		System.out.println(commitId);
		
		/*CommandExecutor gitCheckout = new CommandExecutor("cmd.exe /c cd");
		gitCheckout.start();
		gitCheckout.join();*/
		// + " | sh.exe grep \"diff --git\""
		ShowCommandExecutor gitShow = new ShowCommandExecutor("cmd.exe /c cd D:\\hackathon\\DPT\\myApp\\ && git show --name-only --pretty=\"\" -r " + commitId);
		gitShow.start();
		gitShow.join();
		
		List<String> fileList = ShowCommandExecutor.out;
		Set<String> outputSet = new LinkedHashSet<>();
		Map<String,List<String>> outputMap = new HashMap<>();
		for (String fileName : fileList) {
			LogCommandExecutor gitLog = new LogCommandExecutor("cmd.exe /c cd D:\\hackathon\\DPT\\myApp\\ &&  git log --pretty=oneline  " + fileName);
			gitLog.start();
			gitLog.join();
			outputMap.put(fileName, LogCommandExecutor.out);
			outputSet.addAll(LogCommandExecutor.out);
		}
		System.out.println(ShowCommandExecutor.out.toString()+"!");
		System.out.println(outputSet.toString());
		Utility.writeIntoCSV(outputSet);
		// Handle stdout...
		
/*		Callable<String> callable = new Callable<String>() {

			@Override
			public String call() throws Exception {
	    	System.out.println(_p.getErrorStream());
		    	try {
		    	BufferedReader br=new BufferedReader(new InputStreamReader(_p.getErrorStream()));
		    	String line =null;
		    	String out = null;
		    	 while((line=br.readLine())!=null)
		    	{
		    		 out = out+line;
		    	}
		    	 return out;
		            //Streams.copy(_p.getInputStream(), System.out);
		        } catch (Exception anExc) {
		            anExc.printStackTrace();
		        }
				return null;
			}
		
		};
		String stdout = callable.call();*/
		//callable.
		//System.out.println(stdout);

		// Handle stderr...
		
	}

}
