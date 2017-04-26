package PFSQGen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.text.html.HTMLDocument.Iterator;

public class Queries {

	/**
	 * @param args
	 */
	private ArrayList<SparqlQueryParser> queries = new ArrayList<SparqlQueryParser>();
	
	public Queries(String FILENAME){
		BufferedReader br = null;
		FileReader fr = null;
		try {

			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			String sCurrentLine;
			br = new BufferedReader(new FileReader(FILENAME));

			while ((sCurrentLine = br.readLine()) != null) {
				if(sCurrentLine.startsWith("Query String:")){
					String temp = new String(URLDecoder.decode(sCurrentLine.substring(14)));
					SparqlQueryParser q = new SparqlQueryParser(temp);
					queries.add(q);
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
	public void affiche(){
		String temp = "";		
		if(queries.size() != 0){
			for (SparqlQueryParser q: queries){
				//System.out.println("9lawiiii");
				temp = temp + " \n" + q.getQueryString();
				break;
			}
	}
		System.out.println(temp);
}
	@Override
	public String toString() {
		return "Queries [queries=" + queries + "]";
	}
	public ArrayList<SparqlQueryParser> getQueries() {
		return queries;
	}
	public void setQueries(ArrayList<SparqlQueryParser> queries) {
		this.queries = queries;
	}
	
	
	
}	
