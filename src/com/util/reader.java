package com.util;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class reader {

	/**
	 * @param args
	 */
	private static final String query = "PREFIX  skos: <http://www.w3.org/2004/02/skos/core#>\n"+
"PREFIX  bibo: <http://purl.org/ontology/bibo/>\n"+
"PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"+
"PREFIX  foaf: <http://xmlns.com/foaf/0.1/>\n"+
"PREFIX  swrcext: <http://www.cs.vu.nl/~mcaklein/onto/swrc_ext/2005/05#>\n"+

"SELECT DISTINCT  ?pred ?author_url ?author_name ?author_pref_label\n"+
"WHERE\n"+
  "{ GRAPH ?graph\n"+
      "{   { <http://data.semanticweb.org/conference/eswc/2014/paper/ws/HSWI/4> bibo:authorList ?authorList }\n"+
        "UNION\n"+
         "{ <http://data.semanticweb.org/conference/eswc/2014/paper/ws/HSWI/4> swrcext:authorList ?authorList }\n"+
        "?authorList ?pred ?author_url\n"+
         "{ ?author_url foaf:name ?author_name }\n"+
        "UNION\n"+
        "{ ?author_url rdfs:label ?author_name }\n"+
        "OPTIONAL\n"+
      "{ ?author_url skos:prefLabel ?author_pref_label }\n"+
     "}\n"+
  "}\n"+
"ORDER BY ?pred";

	public static void main(String[] args) {
		ArrayList<String> subj = new ArrayList<String>();
		ArrayList<String> obj = new ArrayList<String>();
		ArrayList<String> pred = new ArrayList<String>();
		Pattern pattern = Pattern.compile("\\{(.*?)\\}");
		//Pattern pattern1= Pattern.compile("(\\s(.*?)\\s)");
		Matcher matcher = pattern.matcher(query);
		//Matcher matcher1;
		while (matcher.find())
		{
			String s = matcher.group().replaceAll("[{}]", "");
			String[] result = s.split("\\s");
			for (int i = 0; i< result.length; i++){
				switch(i){
				case 1: subj.add(result[i]);break;
				case 2: pred.add(result[i]);break;
				case 3: obj.add(result[i]);break;
				default : break;
				}
			}
		}
		for (String str : pred){
			System.out.println(str);
		}
	}
}
