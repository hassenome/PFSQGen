package com.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.util.MatchingPredicates.MatchCapabilities;

import PFSQGen.Capability;
import PFSQGen.Queries;
import PFSQGen.SparqlQueryParser;

public class PFSQGeneration {
	MatchingPredicates joinableArray;
	ArrayList<Queries> CleanQueries;
	private BufferedWriter bw1 = null;
	private FileWriter fw1 = null;
	public PFSQGeneration(MatchingPredicates joinableArray, ArrayList<Queries> cleanQueries, String outputFile) {		
		this.joinableArray = joinableArray;		
		CleanQueries = cleanQueries;
		try {
			fw1 = new FileWriter(outputFile);					
			bw1 = new BufferedWriter(fw1);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();			
			bw1.write("Sparql federated queries generation : "+dtf.format(now)+"\n");			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}								
	}
	
	
	public void executeGeneration(int LIMIT){
		int count=0;
		boolean Nextquery=false; //once we have generated a query this variable will be used to leave the loop and go to the next query
		for(SparqlQueryParser testquery1: CleanQueries.get(0).getQueries()){						
			for(String predicate : testquery1.getPrefixedPred()){
				//System.out.println("predicate ="+predicate);
				predicate=trimURL(predicate);
				int matchIndex = joinableArray.testExistingMatch(predicate);
				if(matchIndex > -1){					
					MatchCapabilities match = joinableArray.getMatchingTable().get(matchIndex);
					//System.out.println("match ="+match.toString());
					//System.out.println("predicate ="+predicate);
					//finding joinability with the other queries
					for(Capability cap : match.getMatchingCapabilities()){
						for(String predcap : cap.getPredicate()){
							Nextquery=false;
						for(SparqlQueryParser testquery2: CleanQueries.get(1).getQueries()){
							if(Nextquery) break; //if the query is already done we go the the next query
							//let's find at least 1 joinable predicate in another query
							for(String predicate2 : testquery2.getPrefixedPred()){								
								predicate2=trimURL(predicate2);
								//System.out.println("cap ="+cap.getPredicate()+" pred2= "+predicate2);
								if(count==LIMIT) return;
								if(predcap.equals(predicate2)){									
									//we found 2 possible joinable queries with a minimum of 1 joinable predicate	
									//let's try to generate a PFSQ
									//System.out.println("Gen= \n"+testquery1+"\n and \n"+testquery2);
									if(generatePFSQuery(testquery1,testquery2,count))
									count++;
									Nextquery=true;
									break;
								}								
							}							
						}																		
					}
					
				}																						
			}
			
			}
			
		}
		try {
			bw1.close();
			fw1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	private String trimURL(String predicate) {
		// TODO Auto-generated method stub
		StringBuilder trimString=new StringBuilder(predicate);
		int pos=trimString.indexOf("<");
		if(pos>-1){
			trimString.deleteCharAt(pos);
		}
		pos=trimString.indexOf(">");
		if(pos>-1){
			trimString.deleteCharAt(pos);
		}
		return trimString.toString();									
	}

/*
	private boolean generatePFSQuery(SparqlQueryParser testquery1, SparqlQueryParser testquery2, int queryNumber) {		
		//let's find out all the joinable predicates that can be applied to a variable
		boolean sucessGen=false;
		StringBuilder PFSQuery=new StringBuilder(testquery1.getQueryString());
		for(String predicate : testquery1.getPred()){
			String trimpredicate=trimURL(predicate);
			int predindex1=testquery1.getPred().indexOf(predicate);
			int matchIndex = joinableArray.testExistingMatch(trimpredicate);			
			if(matchIndex > -1){
				MatchCapabilities match = joinableArray.getMatchingTable().get(matchIndex);					
				for(Capability cap : match.getMatchingCapabilities()){
					for(String predicate2 : testquery2.getPred()){
						String trimpredicate2=trimURL(predicate2);
						//System.out.println(trimpredicate2);
						for(String predcap : cap.getPredicate()){							
						if(predcap.equals(trimpredicate2)){
							//System.out.println(trimpredicate+" match = "+trimpredicate2);							
							int predindex2=testquery2.getPred().indexOf(predicate2);
							//System.out.println(cap.getSbjAuthority().size()+"&&"+testquery2.getSubj().get(predindex2).toString());
							if((cap.getSbjAuthority().size()>0)&&(testquery2.getSubj().get(predindex2).startsWith("?"))){																			
								int predicateParser=PFSQuery.indexOf(testquery1.getObj().get(predindex1),PFSQuery.indexOf("{"));								
								String newline;
								//System.out.println(PFSQuery.charAt(predicateParser+testquery1.getObj().get(predindex1).length()));
								//System.out.println(PFSQuery.charAt(predicateParser+testquery1.getObj().get(predindex1).length()-1));
								if((PFSQuery.charAt(predicateParser+testquery1.getObj().get(predindex1).length()) =='.')||(PFSQuery.charAt(predicateParser+testquery1.getObj().get(predindex1).length()-1) =='.')){
									newline="\n"+testquery1.getSubj().get(predindex1)+" "+predicate2+" "+testquery2.getObj().get(predindex2)+".\n";									
								}else{
								newline=".\n"+testquery1.getSubj().get(predindex1)+" "+predicate2+" "+testquery2.getObj().get(predindex2)+".\n";
								}
								PFSQuery.insert(predicateParser+testquery1.getObj().get(predindex1).length(), newline);
								sucessGen=true;								
							}else{
								//System.out.println(cap.getObjAuthority().size()+"&&"+testquery2.getSubj().get(predindex2).toString());
								if((cap.getObjAuthority().size()>0)&&(testquery2.getObj().get(predindex2).startsWith("?"))){
									int predicateParser=PFSQuery.indexOf(testquery1.getObj().get(predindex1),PFSQuery.indexOf("{"));
									String newline;
									if((PFSQuery.charAt(predicateParser+testquery1.getObj().get(predindex1).length()) =='.')||(PFSQuery.charAt(predicateParser+testquery1.getObj().get(predindex1).length()-1) =='.')){
										newline="\n"+testquery2.getSubj().get(predindex2)+" "+predicate2+testquery1.getObj().get(predindex1)+".\n";									
									}else{
										newline=".\n"+testquery2.getSubj().get(predindex2)+" "+predicate2+testquery1.getObj().get(predindex1)+".\n";
									}									
									PFSQuery.insert(predicateParser+testquery1.getObj().get(predindex1).length(), newline);									
									sucessGen=true;
								}
							}
						}
					}
				  }
				}
		}
		}
		//System.out.println("Query="+PFSQuery.toString());
		if(sucessGen){
		try {
			bw1.write("+----------------------------------------------+\n");
			bw1.write("Query No: "+(queryNumber+1)+"\n");
			bw1.write("Query String:\n"+PFSQuery.toString()+"\n");
			bw1.write("+----------------------------------------------+\n");			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		return sucessGen;
	}
*/
	
	private boolean generatePFSQuery(SparqlQueryParser testquery1, SparqlQueryParser testquery2, int queryNumber) {		
		//let's find out all the joinable predicates that can be applied to a variable
		ArrayList<Integer> usedPredicate=new ArrayList<Integer>(); //to save the predicate that we replaced and avoid replacing them again
		boolean sucessGen=false;
		StringBuilder PFSQuery=new StringBuilder(testquery1.getQueryString());
		for(String predicate : testquery1.getPrefixedPred()){
			String trimpredicate=trimURL(predicate);
			int predindex1=testquery1.getPrefixedPred().indexOf(predicate);
			int matchIndex = joinableArray.testExistingMatch(trimpredicate);			
			if(matchIndex > -1){
				MatchCapabilities match = joinableArray.getMatchingTable().get(matchIndex);					
				for(Capability cap : match.getMatchingCapabilities()){
					for(String predicate2 : testquery2.getPrefixedPred()){
						String trimpredicate2=trimURL(predicate2);
						//System.out.println(trimpredicate2);
						for(String predcap : cap.getPredicate()){							
						if(predcap.equals(trimpredicate2)){
							//System.out.println(trimpredicate+" match = "+trimpredicate2);							
							int predindex2=testquery2.getPrefixedPred().indexOf(predicate2);
							if(!usedPredicate.contains(predindex2)){														
							//System.out.println(cap.getSbjAuthority().size()+"&&"+testquery2.getSubj().get(predindex2).toString());
							try{
							int predicateParser=PFSQuery.indexOf(testquery1.getPred().get(predindex1),PFSQuery.indexOf("{"));
								
							 predicateParser=predicateParser+(testquery1.getObj().get(predindex1).length()+1)+testquery1.getPred().get(predindex1).length()+1;							 
							if((cap.getSbjAuthority().size()>0)&&(testquery2.getObj().get(predindex2).startsWith("?"))){																											
								String newline;								
								if((PFSQuery.charAt(predicateParser) =='.')||(PFSQuery.charAt(predicateParser+1) =='.')){									
									newline="\n"+testquery1.getSubj().get(predindex1)+" "+predicate2+" "+testquery2.getObj().get(predindex2)+".\n";
									PFSQuery.insert(predicateParser+1, newline);
								}else{									
								newline=".\n"+testquery1.getSubj().get(predindex1)+" "+predicate2+" "+testquery2.getObj().get(predindex2)+".\n";
								PFSQuery.insert(predicateParser-1, newline);
								}								
								sucessGen=true;		
								usedPredicate.add(predindex2);
							}else{
								//System.out.println(cap.getObjAuthority().size()+"&&"+testquery2.getSubj().get(predindex2).toString());
								if((cap.getObjAuthority().size()>0)&&(testquery2.getSubj().get(predindex2).startsWith("?"))){									
									String newline;
									if((PFSQuery.charAt(predicateParser) =='.')||(PFSQuery.charAt(predicateParser+1) =='.')){										
										newline="\n"+testquery2.getSubj().get(predindex2)+" "+predicate2+testquery1.getObj().get(predindex1)+".\n";
										PFSQuery.insert(predicateParser+1, newline);
									}else{										
										newline=".\n"+testquery2.getSubj().get(predindex2)+" "+predicate2+testquery1.getObj().get(predindex1)+".\n";
										PFSQuery.insert(predicateParser-1, newline);
									}																											
									sucessGen=true;
									usedPredicate.add(predindex2);
								}
							}
						}catch(Exception e){
							e.printStackTrace();
							System.out.println("Error with : "+testquery1.toString());
							System.out.println(testquery1.getSubj().size()+" "+testquery1.getPred().size()+" "+testquery1.getObj().size()+" "+testquery1.getPred().size()+" "+testquery1.getPrefixedPred().toString());
						}
							}
						}
					}
				  }
				}
		}
		}
		//System.out.println("Query="+PFSQuery.toString());
		if(sucessGen){
		try {
			bw1.write("+----------------------------------------------+\n");
			bw1.write("Query No: "+(queryNumber+1)+"\n");
			bw1.write("Query String:\n"+PFSQuery.toString()+"\n");
			bw1.write("+----------------------------------------------+\n");			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		return sucessGen;
	}
		
	
	
/*	
	
	private boolean generatePFSQuery2(SparqlQueryParser testquery1, SparqlQueryParser testquery2, int queryNumber) {		
		//let's find out all the joinable predicates that can be applied to a variable
		boolean sucessGen=false;
		SparqlQueryParser PFSQuery=new SparqlQueryParser(testquery1.getQueryString());
		for(String predicate : testquery1.getPred()){
			String trimpredicate=trimURL(predicate);
			int predindex1=testquery1.getPred().indexOf(predicate);
			int matchIndex = joinableArray.testExistingMatch(trimpredicate);			
			if(matchIndex > -1){
				MatchCapabilities match = joinableArray.getMatchingTable().get(matchIndex);					
				for(Capability cap : match.getMatchingCapabilities()){
					for(String predicate2 : testquery2.getPred()){
						String trimpredicate2=trimURL(predicate2);
						//System.out.println(trimpredicate2);
						for(String predcap : cap.getPredicate()){							
						if(predcap.equals(trimpredicate2)){
							//System.out.println(trimpredicate+" match = "+trimpredicate2);							
							int predindex2=testquery2.getPred().indexOf(predicate2);
							//System.out.println(cap.getSbjAuthority().size()+"&&"+testquery2.getSubj().get(predindex2).toString());
							if((cap.getSbjAuthority().size()>0)&&(testquery2.getSubj().get(predindex2).startsWith("?"))){																																											
								//System.out.println(PFSQuery.charAt(predicateParser+testquery1.getObj().get(predindex1).length()));
								//System.out.println(PFSQuery.charAt(predicateParser+testquery1.getObj().get(predindex1).length()-1));
								
									newline="\n"+testquery1.getSubj().get(predindex1)+" "+predicate2+" "+testquery2.getObj().get(predindex2)+".\n";									
								
								newline=".\n"+testquery1.getSubj().get(predindex1)+" "+predicate2+" "+testquery2.getObj().get(predindex2)+".\n";
								
								PFSQuery.insert(predicateParser+testquery1.getObj().get(predindex1).length(), newline);
								PFSQuery.getSubj().add(predindex1+1,test);
								PFSQuery.getObj().add(predindex1+1,test);
								sucessGen=true;								
							}else{
								//System.out.println(cap.getObjAuthority().size()+"&&"+testquery2.getSubj().get(predindex2).toString());
								if((cap.getObjAuthority().size()>0)&&(testquery2.getObj().get(predindex2).startsWith("?"))){
									int predicateParser=PFSQuery.indexOf(testquery1.getObj().get(predindex1),PFSQuery.indexOf("{"));
									String newline;
									if((PFSQuery.charAt(predicateParser+testquery1.getObj().get(predindex1).length()) =='.')||(PFSQuery.charAt(predicateParser+testquery1.getObj().get(predindex1).length()-1) =='.')){
										newline="\n"+testquery2.getSubj().get(predindex2)+" "+predicate2+testquery1.getObj().get(predindex1)+".\n";									
									}else{
										newline=".\n"+testquery2.getSubj().get(predindex2)+" "+predicate2+testquery1.getObj().get(predindex1)+".\n";
									}									
									PFSQuery.insert(predicateParser+testquery1.getObj().get(predindex1).length(), newline);									
									sucessGen=true;
								}
							}
						}
					}
				  }
				}
		}
		}
		//System.out.println("Query="+PFSQuery.toString());
		if(sucessGen){
		try {
			bw1.write("+----------------------------------------------+\n");
			bw1.write("Query No: "+(queryNumber+1)+"\n");
			bw1.write("Query String:\n"+PFSQuery.toString()+"\n");
			bw1.write("+----------------------------------------------+\n");			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		return sucessGen;
	}
	
*/	
			
		
	}						
