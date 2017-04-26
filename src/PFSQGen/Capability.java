package PFSQGen;

import java.util.ArrayList;
import java.util.List;

public class Capability {
	private List<String> predicates;
	private List<String> sbjAuthority;
	private List<String> objAuthority;
	
	
	public Capability(List<String> predicate, List<String> sbjAuthority, List<String> objAuthority) {		
		this.predicates = predicate;
		this.sbjAuthority = sbjAuthority;
		this.objAuthority = objAuthority;
	}
	
	


	public Capability() {
		this.predicates=new ArrayList<String>();
		this.sbjAuthority=new ArrayList<String>();
		this.objAuthority=new ArrayList<String>();
		
	}




	public List<String> getPredicate() {
		return predicates;
	}


	public void setPredicate(List<String> predicate) {
		this.predicates = predicate;
	}


	public List<String> getSbjAuthority() {
		return sbjAuthority;
	}


	public void setSbjAuthority(List<String> sbjAuthority) {
		this.sbjAuthority = sbjAuthority;
	}


	public List<String> getObjAuthority() {
		return objAuthority;
	}


	public void setObjAuthority(List<String> objAuthority) {
		this.objAuthority = objAuthority;
	}
	
	public void addPredicate(String predicate) {
		this.predicates.add(predicate);
	}
	
	public void addSbjAuthority(String sbjAuthority) {
		this.sbjAuthority.add(sbjAuthority);
	}
	
	public void addObjAuthority(String objAuthority) {
		this.objAuthority.add(objAuthority);
	}




	@Override
	public String toString() {
		return "Capability [predicate=" + predicates + ", sbjAuthority=" + sbjAuthority + ", objAuthority="
				+ objAuthority + "]";
	}
	
	

}
