package datastructure;

public abstract class SuperVEB {
	protected int u;
	protected SuperVEB summary;
	protected SuperVEB[] cluster;
	protected boolean[] numberList;
	protected int id;
	
	public int getU() {
		return u;
	}
	public void setU(int u) {
		this.u = u;
	}
	public SuperVEB getSummary() {
		return summary;
	}
	
	public SuperVEB[] getCluster() {
		return cluster;
	}

	public boolean[] getNumberList() {
		return numberList;
	}
	
	public void setNumberList(boolean value, int index) {
		this.numberList[index] = value;
	}

	public int getId() {
		return id;
	}

}