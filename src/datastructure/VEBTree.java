package datastructure;

public class VEBTree extends SuperVEB{
	private Integer min;
	private Integer max;
	public static int instanceCounter = 0;
	
	public VEBTree(boolean[] zahlenliste) {
		this.numberList = zahlenliste;
		this.u = zahlenliste.length;		
		Integer mintemp = null;
		instanceCounter++;
		this.id = instanceCounter;
        for(int i = 0; i < zahlenliste.length; i++) {
			if(zahlenliste[i]) {
				mintemp = i;
				break;
			}
		}
		this.min = mintemp;
		Integer maxtemp = null;
		for(int i = zahlenliste.length -1; i >= 0; i--) {
			if(zahlenliste[i]) {
				maxtemp = i;
				break;
			}
		}
		this.max = maxtemp;	
		if(u!= 2) {	
			// minimum aus der Zahlenliste entfernen
			boolean[] zahlenlisteKopie = new boolean[u];
			for(int i = 0; i < u; i++) {
				if(this.min != null) {
					if(i == this.min) {
						zahlenlisteKopie[i] = false;
					}
					else {
						zahlenlisteKopie[i] = this.numberList[i];
					}
				}
				else {
					zahlenlisteKopie[i] = this.numberList[i];	
				}	
			}
			// Cluster-Array füllen!
			int anzCluster = (int) Math.sqrt(u);
			this.cluster = new VEBTree[anzCluster];		
			for(int j = 0; j < anzCluster; j++) {
				boolean[] teilArray = new boolean[anzCluster];
				for(int i = 0; i < anzCluster; i++) {
					teilArray[i] = zahlenlisteKopie[(j*anzCluster)+i];
				}
				this.cluster[j] = new VEBTree(teilArray);
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
			this.summary = new VEBTree(listeFuerSummary);	
		}
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}
}

