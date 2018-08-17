


package com.dpt.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
		//git log --after="2013-11-12 00:00" --before="2013-11-12 23:59"
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String before = simpleDateFormat.format(new Date()).trim() + " 00:00:00";
		//System.out.println(before);
		final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, -1);
	    String after = simpleDateFormat.format(cal.getTime()).trim() + " 00:00:00";
	    //System.out.println(after);
	    //System.out.println("cmd.exe /c cd D:\\hackathon\\DPT\\myApp\\ && git log --pretty=oneline --after="+after.trim()+ " --before=" +before.trim());
		ShowCommandExecutor switchtoFolder2 = new ShowCommandExecutor("cmd.exe /c cd D:\\hackathon\\DPT\\myApp\\ && git log --pretty=oneline --after=\""+after.trim()+ "\" --before=\"" +before.trim()+"\"");

		switchtoFolder2.start();
		switchtoFolder2.join();



		/*CommandExecutor gitCheckout = new CommandExecutor("cmd.exe /c cd");
		gitCheckout.start();
		gitCheckout.join();*/
		// + " | sh.exe grep \"diff --git\""
		Set<String> fileList = new LinkedHashSet<>();
		//System.out.println(ShowCommandExecutor.out);
		for (String commitIdLine: ShowCommandExecutor.out) {

			String commitId = Utility.getCommitId(commitIdLine);
			//System.out.println(commitId);
			ShowCommandExecutor gitShow = new ShowCommandExecutor("cmd.exe /c cd D:\\hackathon\\DPT\\myApp\\ && git show --name-only --pretty=\"\" -r " + commitId);
			gitShow.start();
			gitShow.join();
			fileList.addAll(ShowCommandExecutor.out);
		}
		Set<String> outputSet = new LinkedHashSet<>();
		Map<String,List<String>> outputMap = new HashMap<>();
		for (String fileName : fileList) {
			LogCommandExecutor gitLog = new LogCommandExecutor("cmd.exe /c cd D:\\hackathon\\DPT\\myApp\\ &&  git log --pretty=oneline  " + fileName);
			gitLog.start();
			gitLog.join();
			outputMap.put(fileName, LogCommandExecutor.out);
			outputSet.addAll(LogCommandExecutor.out);
		}
		//System.out.println(ShowCommandExecutor.out.toString()+"!");
		System.out.println(outputSet.toString());
		Utility.writeIntoCSV(outputSet);

	}

}

