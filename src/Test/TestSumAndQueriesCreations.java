package Test;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import PFSQGen.Queries;
import PFSQGen.Summaries;

public class TestSumAndQueriesCreations {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String summariesTestFile1 = "C:/Users/Vald/workspace/PFSQGen/summaries/SWDF-Sum.n3";
		//String summariesTestFile2 = "C:/Users/Vald/workspace/PFSQGen/summaries/DBPD-Sum.n3";
		//lecture des Summaries pour le dataset1
		Summaries testSum = new Summaries(summariesTestFile1);
		//meme chose pour le dataset2
		System.out.println(testSum.toString());				
		System.out.println(testSum.getCapabilities().size());
		String QueryFile = "C:/Users/Vald/workspace/PFSQGen/CleanQueries/SWDF-CleanQueries.txt";
		Queries Qfile = new Queries(QueryFile);				
		//System.out.println(Qfile.toString());
		ArrayList<String> queriesFileList = new ArrayList<String>();		
		queriesFileList.add("C:/Users/Vald/workspace/PFSQGen/CleanQueries/SWDF-CleanQueries.txt");
		queriesFileList.add("C:/Users/Vald/workspace/PFSQGen/CleanQueries/DBpedia3.5.1-CleanQueries.txt");		
		ArrayList<Queries> cleanQueries = new ArrayList<Queries>();
		cleanQueries.add(new Queries(queriesFileList.get(0)));
		cleanQueries.add(new Queries(queriesFileList.get(1)));	
		System.out.println(cleanQueries.get(0).getQueries().size());
		System.out.println(cleanQueries.get(1).getQueries().size());
		BufferedWriter bw1 = null;
		FileWriter fw1 = null;
		BufferedWriter bw2 = null;
		FileWriter fw2 = null;
		try {
			fw1 = new FileWriter("C:/Users/Vald/workspace/PFSQGen/CleanQueries/ReadableTestQueries1");		
			fw2 = new FileWriter("C:/Users/Vald/workspace/PFSQGen/CleanQueries/ReadableTestQueries2");
			bw1 = new BufferedWriter(fw1);
			bw2 = new BufferedWriter(fw2);
			bw1.write(cleanQueries.get(0).toString());
			bw2.write(cleanQueries.get(1).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
