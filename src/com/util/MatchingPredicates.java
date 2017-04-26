package com.util;

import java.util.ArrayList;
import java.util.List;

import PFSQGen.Capability;
import PFSQGen.MatchingCapabilities;
import PFSQGen.Summaries;

public class MatchingPredicates {
	private Summaries summariesList1 ;
	private Summaries summariesList2 ;
	private List<MatchCapabilities> matchingTable;
	
	public class MatchCapabilities{
		private String predicate;
		private List<Capability> joinableWith;
		
		
		
		public MatchCapabilities(String predicate) {
			this.predicate = predicate;
			this.joinableWith = new ArrayList<Capability>();
		}
		public String getPredicate() {
			return predicate;
		}
		public void setPredicate(String predicate) {
			this.predicate = predicate;
		}
		public List<Capability> getMatchingCapabilities() {
			return joinableWith;
		}
		public void setMatchingCapabilities(List<Capability> matchingCapabilities) {
			this.joinableWith = matchingCapabilities;
		}
		
		public void addMatchingCapability(Capability capability){
			this.joinableWith.add(capability);
		}
		@Override
		public String toString() {
			return "\n MatchCapabilities [predicate=" + predicate + ", matchingCapabilities=" + joinableWith + "]";
		}
		
		
						
	}
	
	public MatchingPredicates(ArrayList<String> sumFiles){
		summariesList1 = new Summaries(sumFiles.get(0));
		summariesList2 = new Summaries(sumFiles.get(1));
		matchingTable = new ArrayList <MatchCapabilities>();
		
		//testing matching capabilities
		int i=0;
		for (Capability testMatch: summariesList1.getCapabilities()){
			for(i=0;i<summariesList2.getCapabilities().size();i++){
				Capability resultMatch= testMatchingAuth(testMatch,summariesList2.getCapabilities().get(i));				
				if((resultMatch.getSbjAuthority().size()>0)||(resultMatch.getObjAuthority().size()>0)){					
					int matchPosition = testExistingMatch(testMatch.getPredicate().get(0));
					if(matchPosition > -1){						
						matchingTable.get(matchPosition).addMatchingCapability(resultMatch);
					}else{
						//System.out.println(testMatch.getPredicate().get(0));
						MatchCapabilities match= new MatchCapabilities(testMatch.getPredicate().get(0));
						match.addMatchingCapability(resultMatch);
						matchingTable.add(match);					
						}
					}
				}
				
			}
			
			
		}
		
		

	private Capability testMatchingAuth(Capability testMatch, Capability capability) {
		// TODO Auto-generated method stub		
		Capability resultMatch= new Capability();		
		resultMatch.setPredicate(capability.getPredicate());		
		//testing subjects compatibilities using loop on SbjAuthority		
		for (String testAuth: testMatch.getSbjAuthority()){
			for(int i=0;i<capability.getSbjAuthority().size();i++){
				if(testAuth.equals(capability.getSbjAuthority().get(i))){
					resultMatch.addSbjAuthority(capability.getSbjAuthority().get(i));
				}
			}
		}
		
		
		//testing objects compatibilities using loop on ObjAuthority		
				
				for (String testAuth: testMatch.getObjAuthority()){
					for(int i=0;i<capability.getObjAuthority().size();i++){
						if(testAuth.equals(capability.getObjAuthority().get(i))){
							//System.out.println(i+"-- 1="+testAuth+"  2="+capability.getObjAuthority().get(i));
							resultMatch.addObjAuthority(capability.getObjAuthority().get(i));
						}
					}
				}
				
				return resultMatch;
		
		
	}
	
	public int testExistingMatch(String predicate){
		for (int i=0;i<matchingTable.size();i++){
			if(matchingTable.get(i).getPredicate().equals(predicate)){
				//System.out.println(predicate+" found "+i);
				return i;				
			}
		}		
		return -1;
	}



	public Summaries getSummariesList1() {
		return summariesList1;
	}



	public void setSummariesList1(Summaries summariesList1) {
		this.summariesList1 = summariesList1;
	}



	public Summaries getSummariesList2() {
		return summariesList2;
	}



	public void setSummariesList2(Summaries summariesList2) {
		this.summariesList2 = summariesList2;
	}



	public List<MatchCapabilities> getMatchingTable() {
		return matchingTable;
	}



	public void setMatchingTable(List<MatchCapabilities> matchingTable) {
		this.matchingTable = matchingTable;
	}



	@Override
	public String toString() {
		return "MatchingPredicates [matchingTable=" + matchingTable + "]";
	}
	
	
	

}
