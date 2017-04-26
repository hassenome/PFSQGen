package PFSQGen;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Summaries {
	private List<Capability> capabilities = new ArrayList<Capability>();
	private List<String> services = null;
	private String url;
	private String prefix;
	
	public Summaries(String inputFile){
		BufferedReader br = null;
		FileReader fr = null;
		//int line=0;
		boolean syntaxParser=false;
		int charParserPosition=0;
		int lastParserPosition=0;
		int searchPatternPos=0;
		try {

			fr = new FileReader(inputFile);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(inputFile));
			//reading file line by line			
			while ((sCurrentLine = br.readLine()) != null) {
				//System.out.println(sCurrentLine);				
				//extracting capabilities
				if(sCurrentLine.indexOf("capability") >= 0){
					Capability tempCap = new Capability();
					if((sCurrentLine = br.readLine()) != null){
						if(sCurrentLine.indexOf("[") >= 0){
							syntaxParser=true;
							while (((sCurrentLine = br.readLine()) != null)&&(syntaxParser)){
								if(sCurrentLine.indexOf("]") >= 0){
									syntaxParser=false;
									break;
								}else{
									//parsing predicate
									if(sCurrentLine.indexOf("predicate") >= 0){
										charParserPosition=sCurrentLine.indexOf("<");
										lastParserPosition=sCurrentLine.indexOf(">");
										tempCap.addPredicate(sCurrentLine.substring(charParserPosition+1, lastParserPosition));										
									}
									
									//parsing sbjAuthority
									searchPatternPos=sCurrentLine.indexOf("sbjAuthority");
									if( searchPatternPos >= 0){
										//number of Subject Authority
										for(int i=searchPatternPos;i<sCurrentLine.length();i++){
											if(sCurrentLine.charAt(i) == '<'){
												lastParserPosition=sCurrentLine.indexOf('>', i);
												tempCap.addSbjAuthority(sCurrentLine.substring(i+1, lastParserPosition));
												i=lastParserPosition;
											}																						
										}																				
									}
									
									//parsing objAuthority																		
									searchPatternPos=sCurrentLine.indexOf("objAuthority");
									if( searchPatternPos >= 0){
										//number of Subject Authority
										for(int i=searchPatternPos;i<sCurrentLine.length();i++){
											if(sCurrentLine.charAt(i) == '<'){
												lastParserPosition=sCurrentLine.indexOf('>', i);
												tempCap.addObjAuthority(sCurrentLine.substring(i+1, lastParserPosition));
												i=lastParserPosition;
											}																						
										}																				
									}																		
									
								}
							}
							
						}
					}
						this.capabilities.add(tempCap);
					}
					
							}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}

	public List<Capability> getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(List<Capability> capabilities) {
		this.capabilities = capabilities;
	}

	public List<String> getServices() {
		return services;
	}

	public void setServices(List<String> services) {
		this.services = services;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public String toString() {
		return "Summaries [capabilities=" + capabilities + ", services=" + services + ", url=" + url + ", prefix="
				+ prefix + "]";
	}
		
		
	
	
	

}
