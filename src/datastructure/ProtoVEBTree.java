package datastructure;

public class ProtoVEBTree extends SuperVEB {	
	private boolean[] a;
	public static int instanceCounter = 0;
	
	public boolean containsValues() {
		boolean containsValues = false;
		if(this.u == 2) {
			for(int i = 0; i < 2; i++) {
				if(this.a[i]) {
					return true;
				}
			}
		}
		else {
			for(int i = 0; i < this.cluster.length; i++) {
				boolean wv = ((ProtoVEBTree) this.cluster[i]).containsValues();
				if(wv) {
					return true;
				}
			}	
		}
		return containsValues;
	}
	
	public ProtoVEBTree(boolean[] zahlenliste) {
		this.numberList = zahlenliste;
		this.u = zahlenliste.length;			
		instanceCounter++;
		this.id = instanceCounter;
		if(u!= 2) {	
			// Cluster-Array füllen!
			int anzCluster = (int) Math.sqrt(u);
			this.cluster = new ProtoVEBTree[anzCluster];		
			for(int j = 0; j < anzCluster; j++) {
				boolean[] teilArray = new boolean[anzCluster];
				for(int i = 0; i < anzCluster; i++) {
					teilArray[i] = zahlenliste[(j*anzCluster)+i];
				}
				this.cluster[j] = new ProtoVEBTree(teilArray);
			}		
			// Summary Struktur anlegen
			boolean[] listeFuerSummary = new boolean[anzCluster];
			for(int i = 0; i < anzCluster; i++) {
				boolean aktVal = false;
				boolean[] aktCluster = this.cluster[i].numberList;
				for(int j = 0; j < anzCluster; j++) {
					if(aktCluster[j]) {
						aktVal = true;
						break;
					}
				}
				listeFuerSummary[i] = aktVal;
			}
			this.summary = new ProtoVEBTree(listeFuerSummary);	
		}
		else { // u ist 2, a füllen!
			this.a = new boolean[this.numberList.length];
			for(int i = 0; i < this.numberList.length; i++) {
				this.a[i] = this.numberList[i];
			}
		
		}
	}

	public boolean[] getA() {
		return a;
	}
	
	public void setA(boolean val, int index) {
		this.a[index] = val;
	}
}

