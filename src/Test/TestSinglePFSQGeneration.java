package Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.util.MatchingPredicates;
import com.util.PFSQGeneration;

import PFSQGen.Queries;

public class TestSinglePFSQGeneration{
		
	public static void main(String[] args) {
		ArrayList<String> sumFileList = new ArrayList<String>();
		sumFileList.add("C:/Users/Vald/workspace/PFSQGen/summaries/SWDF-Sum.n3");
		sumFileList.add("C:/Users/Vald/workspace/PFSQGen/summaries/DBPD-Sum.n3");
		
		MatchingPredicates joinableArray = new MatchingPredicates(sumFileList);
		//System.out.println(joinableArray);
		ArrayList<String> queriesFileList = new ArrayList<String>();
		queriesFileList.add("C:/Users/Vald/workspace/PFSQGen/CleanQueries/SWDF-sample.txt");
		queriesFileList.add("C:/Users/Vald/workspace/PFSQGen/CleanQueries/DBpedia-sample.txt");
		
		ArrayList<Queries> cleanQueries = new ArrayList<Queries>();
		cleanQueries.add(new Queries(queriesFileList.get(0)));
		cleanQueries.add(new Queries(queriesFileList.get(1)));	
		System.out.println("Total loaded queries : "+(cleanQueries.get(0).getQueries().size()+cleanQueries.get(1).getQueries().size()));
		//System.out.println(cleanQueries.get(0).toString()+"\n----\n"+cleanQueries.get(1).toString());				
		String outputFile = ("C:/Users/Vald/workspace/PFSQGen/GeneratedQueries/Gen3.txt");																
		PFSQGeneration Generator = new PFSQGeneration(joinableArray,cleanQueries,outputFile);
		long startTime = System.currentTimeMillis();
		Generator.executeGeneration(1000000);	
		System.out.println("Federated queries Generation Time (sec): "+ (System.currentTimeMillis()-startTime)/1000);
		System.out.print("Federated queries stored at "+ outputFile);
		
	}								
	
}