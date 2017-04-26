package PFSQGen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FedQueriesGenerator {
	private ArrayList<Summaries> summariesList = new ArrayList<Summaries>();
	private ArrayList<Summaries> cleanQueriesList = new ArrayList<Summaries>();
	public static  BufferedWriter bw ;
	
	
	public FedQueriesGenerator(ArrayList<Summaries> summarieslist, ArrayList<Summaries> cleanquerieslist, String outputFile) throws IOException {
		 bw= new BufferedWriter(new FileWriter(new File(outputFile))); //--name/location where the generated queries will be stored
		 this.summariesList=summarieslist;
		 this.cleanQueriesList=cleanquerieslist;
	}
	
	
	public ArrayList<Capability> testCapabilities(){
		//test des predicats joinables:
		//#########!! a completer plus tard !!##############
		Capability tempCap=summariesList.get(0).getCapabilities().get(5);
		ArrayList<Capability> joinableArray=new ArrayList<Capability>();
		joinableArray.add(tempCap);
		return joinableArray;
				
	}
	
	public void generateFederatedQueries(){
		//generation des PFSQ
		//#########!! a completer plus tard !!##############
		ArrayList<Capability> joinableArray=new ArrayList<Capability>();
		joinableArray=testCapabilities();
		
		
	}
	
	

}
