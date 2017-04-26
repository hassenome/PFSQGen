package Test;

import java.util.ArrayList;

import com.util.MatchingPredicates;

public class TestMatchingPredicates {

	
	public static void main(String[] args) {
	ArrayList<String> fileList = new ArrayList<String>();	
	fileList.add("/home/vald/workspace/PFSQGen/summaries/SWDF-Sum.n3");
	fileList.add("/home/vald/workspace/PFSQGen/summaries/DBPD-Sum.n3");	
	MatchingPredicates matcher = new MatchingPredicates(fileList);
	System.out.println(matcher.toString());				
	
	}
	
	
	
}
