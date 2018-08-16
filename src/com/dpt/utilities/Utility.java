package com.dpt.utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Utility {

	public static List<String>  getAllFiles (String msg){
		
		return null;
	}
	
	public static String getCommitId (String msg){
		
		return msg.substring(0,40);
	}
	
	public static void writeIntoCSV(Set<String> input) throws IOException {
		
		FileWriter writer = new FileWriter(new Date().getTime()+"_output.csv");
		//String collect = input.stream().collect(Collectors.joining(""));
		for(String txt: input) {
			writer.write(txt);
			writer.write("\n");
		}
		writer.close();
	}
}
