package vebtree;

import java.util.ArrayList;

import datastructure.SuperVEB;

public class VEBContainer {	
	private Object sumPointer;
	private Object mainCont;	
	private ArrayList<Object> clusterPointer;
	private SuperVEB v; 
	private boolean isSummary;
	public Object getSumPointer() {
		return sumPointer;
	}
	public void setSumPointer(Object sumPointer) {
		this.sumPointer = sumPointer;
	}
	public Object getMainCont() {
		return mainCont;
	}
	public void setMainCont(Object mainCont) {
		this.mainCont = mainCont;
	}
	public ArrayList<Object> getClusterPointer() {
		return clusterPointer;
	}
	public void setClusterPointer(ArrayList<Object> clusterPointer) {
		this.clusterPointer = clusterPointer;
	}
	public SuperVEB getV() {
		return v;
	}
	public void setV(SuperVEB v) {
		this.v = v;
	}
	public boolean isSummary() {
		return isSummary;
	}
	public void setSummary(boolean isSummary) {
		this.isSummary = isSummary;
	}
}
