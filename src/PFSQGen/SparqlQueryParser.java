package PFSQGen;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import java.util.ArrayList;

public class SparqlQueryParser {

	/**
	 * @param args
	 */
	private String QueryString;
	ArrayList<String> prefixes = new ArrayList<String>();
	ArrayList<String> subj = new ArrayList<String>();
	ArrayList<String> obj = new ArrayList<String>();
	ArrayList<String> pred = new ArrayList<String>();
	ArrayList<String> prefixedPred = new ArrayList<String>();
	ArrayList<String> logicConnector = new ArrayList<String>();	
	public SparqlQueryParser(String s){
		QueryString = s;
		//prefixes parsing
		Pattern prefixPattern = Pattern.compile("PREFIX(.*?)>");
		Matcher prefixMatcher = prefixPattern.matcher(s);		
		while (prefixMatcher.find())
		{			
			prefixes.add(prefixMatcher.group());					
		}
		
		//System.out.println(prefixes.toString());
		
		//Pattern pattern = 
		//Pattern pattern1= Pattern.compile("(\\s(.*?)\\s)");
		Matcher matcher = Pattern.compile("\\{.*\\}",Pattern.DOTALL).matcher(s);
		//Matcher matcher1;
		while (matcher.find())
		{
			String str = matcher.group().replaceAll("[{}]", "");
			String[] fullresult = str.split("\n");
			for(int j=0;j<fullresult.length;j++){							
			String[] result = (fullresult[j].trim()).split("\\s");
			//System.out.println(fullresult[j].trim());
			for (int i = 0; i< result.length; i++){
				//prefix replacement				
				int temppos=0;
				if(((temppos=result[i].indexOf(':'))>-1)&&(result[i].indexOf('<')==-1)&&(i==1)){					
					for (String prefix : this.prefixes) {						
			               if(prefix.contains(result[i].substring(0, temppos))){
			            	   String fullAdress=prefix.substring(prefix.indexOf('<'), prefix.lastIndexOf('>'));
			            	   fullAdress=fullAdress+result[i].substring(temppos+1, result[i].length())+'>';
			            	   //System.out.println("fullAdress="+fullAdress);
			            	   //result[i]=fullAdress;
			            	   prefixedPred.add(fullAdress);
			            	   break;
			               }
					}
				}else{
					if((i==1)&&(result[i]!=".")&&(result[i].indexOf("(")==-1))
						prefixedPred.add(result[i]);
				}
				result[i]=result[i].trim();
				if((result[i]!=".")){
				if((result[i].indexOf("?")==-1)&&(result[i].indexOf(":")==-1)){
					logicConnector.add(result[i]);
				}else{
				if((result[i].indexOf("(")==-1)||(result[i].indexOf("<")>-1))	
				switch(i){
				case 0: subj.add(result[i]);break;
				case 1: pred.add(result[i]);break;
				case 2: obj.add(result[i]);break;
				default :					
					break;
				}
				}
				}
			}
		}
		}
	}
	
	public SparqlQueryParser(){
		this.QueryString = null;
	}
	
	public String getQueryString(){
		return QueryString;
	}
	public ArrayList<String> getPred(){
		return pred;
	}
	public ArrayList<String> getSubj(){
		return subj;
	}
	public ArrayList<String> getObj(){
		return obj;
	}

	@Override
	public String toString() {		
		return "Query [QueryString=" + QueryString + ", subj=" + subj + ", obj=" + obj + ", pred=" + pred + "]";
	}

	public ArrayList<String> getPrefixedPred() {
		return prefixedPred;
	}

	public void setPrefixedPred(ArrayList<String> prefixedPred) {
		this.prefixedPred = prefixedPred;
	}

	
	
	
	
	
}