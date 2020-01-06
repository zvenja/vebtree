package vebtree;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import datastructure.ProtoVEBTree;
import datastructure.SuperVEB;
import datastructure.VEBTree;


/**
 * Enthält die Logik für die Anwendung
 * @author svenja
 *
 */
public class Controller {
	 private SuperVEB vEBTree;
	 private mxGraph graph;
	 private mxGraphComponent graphComponent;
	 private VEBFrame myFrame; 
	 private ArrayList<VEBContainer> containerList;
	 
	 /**
	  * Konstruktor
	  * @param v
	  * @param frame
	  */
	 public Controller(SuperVEB v, VEBFrame frame) {
		 this.vEBTree = v;
		 this.graph = new mxGraph();
		 this.graphComponent = new mxGraphComponent(graph);
		 this.myFrame = frame;
		 this.containerList = new ArrayList<>();		 
	 }
	  
	/**
	  * Funktion, die aufgerufen wird, bei Click auf auf SuchButton
	  */
	 public void clickOnSearch() { 
		 Thread searchThread = new Thread() {
			 @Override
			 public void run() {
				 String numberToSearch = JOptionPane.showInputDialog(myFrame, "Welcher Wert soll gesucht werden?", null);		
				 if(inputOk(numberToSearch, 0)) {
					 outputText("START: Suche: " + numberToSearch);
					 boolean result = containsValue(vEBTree, Integer.parseInt(numberToSearch));
					 defaultStyleContainers();
					 String response = "ENDE: Suche, Ergebnis: " + numberToSearch;
					 if(result) {
						 response += " ist vorhanden";
					 }
					 else {
						 response += " ist nicht vorhanden";	 
					 }	
					 outputText(response);
				}
				 else {
					 outputText("Suche von " + numberToSearch + " nicht möglich");
				 }
			 }
		 };
		 searchThread.start();
	 }

	 	
	 /**
	  * Graph wieder auf Ursprungsfarben zurücksetzen.
	  */
	 private void defaultStyleContainers() {
		 for(int i = 0; i < this.containerList.size(); i++) {
			 graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, Constants.getBackgroundColor(this.containerList.get(i).isSummary()), new Object[]{this.containerList.get(i).getMainCont()}); //changes the color to red
			 graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "black", new Object[]{this.containerList.get(i).getMainCont()});
			 graphComponent.refresh();	
		 }
	 }
		
	 /**
	 * Erstellt einen Container-Objekt, das im Graphen als Knoten angezeigt wird
	 * @param graph
	 * @param xPos
	 * @param yPos
	 * @param v 
	 * @return
	 */
	 public VEBContainer createContainer(mxGraph graph, int xPos, int yPos, SuperVEB veb, boolean isSummary) {
		 Object mainSumm = null;
		 ArrayList<Object> clusters = new ArrayList<>();
		 VEBContainer c = new VEBContainer();
		 Object mainContainer;
		 if(veb instanceof ProtoVEBTree) {
			 ProtoVEBTree p = (ProtoVEBTree) veb;
			 mainContainer = graph.insertVertex(graph.getDefaultParent(), null, "Proto-vEB(" + p.getU() + ") - ID: " + p.getId(), xPos, yPos, 150,80, "defaultVertex;fillColor="+Constants.getBackgroundColor(isSummary)+";strokeColor=black;strokeWidth=2;labelPosition=ALIGN_LEFT;verticalLabelPosition=ALIGN_TOP;verticalAlign=ALIGN_TOP;fontColor=black");	
			 graph.insertVertex(mainContainer, "u", "u: " + veb.getU(), 15, 20, 30, 15,Constants.BLOCK_STYLE_WITH_TEXT);
			 if(veb.getU() != 2){
				 mainSumm = graph.insertVertex(mainContainer, null, null, 20, 40, 15, 15,Constants.BLOCK_STYLE_SUMMARY);	
				 for(int i = 0; i < Math.sqrt(p.getU()); i++) {
					 xPos = 40 + (i*15);
					 Object clust = graph.insertVertex(mainContainer, null, null, xPos, 40, 15, 15, Constants.BLOCK_STYLE);
					 clusters.add(clust);
				}
			 }
			 else { 
				 for(int i = 0; i < 2; i++) {
					 xPos = 60 + (i*15);
					 String beschr = "0";
					 if(p.getA()[i]) {
						 beschr = "1";
					 }
					 graph.insertVertex(mainContainer, null, beschr, xPos, 20, 15, 15,Constants.BLOCK_STYLE);
				 }
			 }
			 c.setV(p);
		 }
		 else { 
			 VEBTree v = (VEBTree) veb;
			 mainContainer = graph.insertVertex(graph.getDefaultParent(), null, "vEB(" + v.getU() + ") - ID: " + v.getId(), xPos, yPos, 150,80, "defaultVertex;fillColor="+Constants.getBackgroundColor(isSummary)+";strokeColor=black;strokeWidth=2;labelPosition=ALIGN_LEFT;verticalLabelPosition=ALIGN_TOP;verticalAlign=ALIGN_TOP;fontColor=black");	
			 graph.insertVertex(mainContainer, null, "u: " + v.getU(), 15, 20, 30, 15, Constants.BLOCK_STYLE_WITH_TEXT);
			 graph.insertVertex(mainContainer, null, "min: " + v.getMin(), 50, 20, 45, 15,Constants.BLOCK_STYLE_WITH_TEXT);
			 graph.insertVertex(mainContainer, null, "max: " + v.getMax(), 100, 20, 45, 15,Constants.BLOCK_STYLE_WITH_TEXT);
			 if(v.getU() != 2){
				 mainSumm = graph.insertVertex(mainContainer, "summary", null, 20, 40, 15, 15,Constants.BLOCK_STYLE_SUMMARY);	
				 for(int i = 0; i < Math.sqrt(v.getU()); i++) {
					 xPos = 40 + (i*15);
					 Object clust = graph.insertVertex(mainContainer, null, null, xPos, 40, 15, 15,Constants.BLOCK_STYLE);
					 clusters.add(clust);
				 }
			 }
			 c.setV(v);
		 }
		 if(veb.getU() != 2) {
			 c.setClusterPointer(clusters);
			 c.setSumPointer(mainSumm);
		 }
		 c.setMainCont(mainContainer);	
		 c.setSummary(isSummary);
		 return c;
	 }
		
	public void clickOnInitTree() {
		final JPanel panel = new JPanel();
		JCheckBox[] checkBoxList = new JCheckBox[16];
		for(int i = 0; i < checkBoxList.length;i++) {
			checkBoxList[i] = new JCheckBox(String.valueOf(i));
			panel.add(checkBoxList[i]);
		}
		JRadioButton rbVEB = new JRadioButton("vEB-Baum");
		JRadioButton rbProto = new JRadioButton("Proto-vEB-Baum");
		panel.add(rbVEB);
		panel.add(rbProto);
		JOptionPane.showMessageDialog(null, panel);
		boolean[] numbers = new boolean[16];
		for(int i = 0; i < numbers.length; i++) {
			if(checkBoxList[i].isSelected()) {
				numbers[i] = true;
	        }
	        else {
	        	numbers[i] = false;
	        }
		}
		if(rbVEB.isSelected()) {
			VEBTree.instanceCounter=0;
			VEBTree vEBNeu = new VEBTree(numbers);
			this.vEBTree = vEBNeu;	
		}
		else {
			ProtoVEBTree.instanceCounter=0;
			ProtoVEBTree vEBNeu = new ProtoVEBTree(numbers);
			this.vEBTree = vEBNeu;
		}
		this.drawGraph();	
	}
	 
	public void clickOnPredSucc(boolean vorg) {
		Thread vorgThread = new Thread() {
			@Override
			public void run() {
				String type = "Nachfolger";
				if(vorg) {
					type = "Vorgänger";
				}
				String number = JOptionPane.showInputDialog(myFrame, "Von welchem Wert soll der" + type + " bestimmt werden?", null);
				if(inputOk(number,0)) {
					Integer wert;
					outputText("START: " + type + "suche von" + number );
					if(vorg) {
						wert = predecessor(vEBTree, Integer.parseInt(number));
					}
					else {
						wert = successor(vEBTree, Integer.parseInt(number));	
					}
					defaultStyleContainers();
					outputText("Der " + type + " Wert von " + number + " ist " + Constants.coalesce(wert));
					outputText("ENDE: " + type + "suche von" + number );
				}
			}
		};
		vorgThread.start();
	}
	
	
	public void clickOnMax() {
		Thread maxThread = new Thread() {
			@Override
			public void run() {
				outputText("START: Maximum wird bestimmt");
				JOptionPane.showMessageDialog(myFrame, "Maximum wird bestimmt");
				Integer max = maximum(vEBTree);
				defaultStyleContainers();
				JOptionPane.showMessageDialog(myFrame, "Maximum ist: " + Constants.coalesce(max));
				outputText("ENDE: Maximum ist: " + Constants.coalesce(max));
			}
	    };
	    maxThread.start();
	}
			
	/**
	* Die Methode erstellt den Graphen.
	*/
	private void drawGraph() {
		this.fillLabel(this.vEBTree);
		String edgeStyle = Constants.EDGE_STYLE;
		Object parent = graph.getDefaultParent();
		graph.getModel().beginUpdate();
		this.containerList = new ArrayList<>();
		try {		
			VEBContainer v1 = createContainer(graph, 800, 20, this.vEBTree, false);
			this.containerList.add(v1);
			for(int i = 0; i < this.vEBTree.getCluster().length; i++) {
				int yPos = 150;
				int xPos = 600 + (i*260);
				int xPosSum = xPos - 200;
				VEBContainer vebcont = this.createContainer(graph, xPos, yPos, this.vEBTree.getCluster()[i], false);
				this.containerList.add(vebcont);
				graph.insertEdge(parent, null, null, v1.getClusterPointer().get(i), vebcont.getMainCont(), edgeStyle);
				VEBContainer sumneu = this.createContainer(graph, xPosSum, 350, this.vEBTree.getCluster()[i].getSummary(), true);
				this.containerList.add(sumneu);
				graph.insertEdge(parent, null, null, vebcont.getSumPointer(), sumneu.getMainCont(), edgeStyle); 
				for(int j = 0; j < this.vEBTree.getCluster()[i].getCluster().length; j++) {
					int yPosJ = 250;
					int xPosJ = 400 + (j*160) + (2*i*160);
					VEBContainer abcJ = this.createContainer(graph, xPosJ, yPosJ,this.vEBTree.getCluster()[i].getCluster()[j], false);
					this.containerList.add(abcJ);
					graph.insertEdge(parent, null, null, vebcont.getClusterPointer().get(j), abcJ.getMainCont(), edgeStyle);
				}
			}
			// Summary erstellen
			VEBContainer sum = this.createContainer(graph, 200, 150, this.vEBTree.getSummary(),true);
			this.containerList.add(sum);
			graph.insertEdge(parent, null, null, v1.getSumPointer(), sum.getMainCont(), edgeStyle);
			// Summary von summary
			VEBContainer sumsum = this.createContainer(graph, 100, 350, this.vEBTree.getSummary().getSummary(), true);
			this.containerList.add(sumsum);
			graph.insertEdge(parent, null, null, sum.getSumPointer(), sumsum.getMainCont(), edgeStyle);
			// Cluster-Elemente von Summary erstellen
			for(int k = 0; k < this.vEBTree.getSummary().getCluster().length; k++) {
				int yPosJ = 250;
				int xPosJ =  50 + (k*160);
				VEBContainer abcJ = this.createContainer(graph, xPosJ, yPosJ, this.vEBTree.getSummary().getCluster()[k],false);
				this.containerList.add(abcJ);
				graph.insertEdge(parent, null, null, sum.getClusterPointer().get(k), abcJ.getMainCont(),edgeStyle);
			}  
		}		
		finally {
			graph.getModel().endUpdate();
		}
	}
			
	void clickOnMin() {
		Thread minThread = new Thread() {
			@Override
			public void run() {
				outputText("START: Minimum wird bestimmt");
		    	JOptionPane.showMessageDialog(myFrame, "Minimum wird bestimmt");
		    	Integer min = minimum(vEBTree);
		    	defaultStyleContainers();
		    	JOptionPane.showMessageDialog(myFrame, "Minimum ist: " + Constants.coalesce(min));	
		    	outputText("ENDE: Minimum ist: " + Constants.coalesce(min));
		    }
		};
		minThread.start();  	
	}
	
	void clickOnInsertOrDelete(boolean ins) {
		Thread minThread = new Thread() {
			@Override
			public void run() {
				String number;
				String verb = "gelöscht";
				int check = 1;
				boolean val = false;
				if(ins) {
					verb = "eingefügt";
					check= 2;
					val = true;
				 }
				 number = JOptionPane.showInputDialog(myFrame, "Welcher Wert soll " + verb + " werden?", null);
				 if(inputOk(number, check)) {
					 outputText("START: " + number +  " wird " + verb);
					 if(ins) {
						 insert(vEBTree, Integer.parseInt(number));
					 }
					 else {
						 deleteElement(vEBTree, Integer.parseInt(number));
					 }
					 outputText("ENDE: " +  number + " wurde " + verb);
					 vEBTree.setNumberList(val, Integer.parseInt(number)); 
					 drawGraph();
				}
				else {
					outputText(Constants.coalesce(number) + " kann nicht " + verb + " werden.");
				}
			}
		};
		minThread.start();  	
	}
	
	public void addActionListeners() {
		this.myFrame.getZoomInButton().addActionListener(e -> zoom(true));
		this.myFrame.getZoomOutButton().addActionListener(e -> zoom(false));
		this.myFrame.getInitButton().addActionListener(e -> clickOnInitTree());
		this.myFrame.getMinButton().addActionListener(e -> clickOnMin());
		this.myFrame.getInsertButton().addActionListener(e -> clickOnInsertOrDelete(true));
		this.myFrame.getMaxButton().addActionListener( e -> clickOnMax());
		this.myFrame.getMemberButton().addActionListener(e -> clickOnSearch());
		this.myFrame.getDeleteButton().addActionListener(e -> clickOnInsertOrDelete(false));
		this.myFrame.getPredButton().addActionListener(e -> clickOnPredSucc(true));
		this.myFrame.getSuccButton().addActionListener(e -> clickOnPredSucc(false));
	}
	
	public void zoom(boolean zoomIn) {
		if(zoomIn) {
			this.graphComponent.zoomIn();
		}
		else {
			this.graphComponent.zoomOut();
		}
	}
		
	/**
	* Die Funktion wird in der Main-Methode aufgerufen. Sie dient der Erstellung
	* der Oberfläche.
	*/
	public void createGui() {
		this.drawGraph();
		this.myFrame.getSplit().setTopComponent((graphComponent));
		this.myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.myFrame.setSize(900, 820);
		this.myFrame.setVisible(true);
	}
	
	private int high(Integer x, SuperVEB v) {
		return (x / (int)(Math.sqrt(v.getU())));
	}
	
	private int low(Integer x, SuperVEB v) {
		return (x % (int)(Math.sqrt(v.getU())));
	}
	
	private int index(Integer x, Integer y, SuperVEB v) {
		return ((x * (int)(Math.sqrt(v.getU())))+y);
	}
	
	/**
	 * Die Funktion überprüft, ob der Wert x vorhanden ist. Implementierung für Proto VEB-Baum
	 * @param veb
	 * @param x
	 * @return
	 * schon fertig für Proto
	 * angepasst auf neues Konzept
	 */
	public boolean containsValue(SuperVEB veb, Integer x) {
		this.showInfos(veb, x);
		this.animateContainer(veb);	
		if(veb instanceof ProtoVEBTree) { // PROTO
			ProtoVEBTree p = (ProtoVEBTree) veb;
			if (p.getU() == 2) {
				return p.getA()[x];
			}
			else {
				return containsValue(veb.getCluster()[this.high(x, p)], this.low(x, p)); 
			} 
		}
		else { // VEB
			VEBTree v = (VEBTree) veb; 
			if(x.equals(v.getMax()) || x.equals(v.getMin())) { 
				return true;
			}
			else if (v.getU() == 2) {
				return false;
			}
			else {
				return containsValue(v.getCluster()[this.high(x, v)], low(x, v));
			}	
		}
	}
	
	/**
	 * Löscht x aus dem Baum.
	 * @param v
	 * @param x
	 */
	public void deleteElement(SuperVEB veb, Integer x) {
		this.showInfos(veb, x);
		this.animateContainer(veb);
		this.drawGraph();
		if(veb instanceof ProtoVEBTree) { // Proto
			ProtoVEBTree p = (ProtoVEBTree) veb;
			if(p.getU() == 2) {
				p.getA()[x] = false; 	
			}
			else {
				deleteElement(p.getCluster()[high(x, p)], low(x, p));
				// nur Summary aktualisieren, falls Cluster keine weiteren Elemente enthält
				if(! ((ProtoVEBTree) p.getCluster()[high(x, p)]).containsValues()) {
					deleteElement(p.getSummary(), high(x, p));
				}		
			}	
		}
		else { // VEB
			VEBTree v = (VEBTree) veb;
			if(v.getMin().equals(v.getMax())) {
				v.setMin(null);
				v.setMax(null);
			}
			else if(v.getU() == 2) {
				this.outputText("u von " + v.getId() + " ist 2");
				if(x == 0) {
					v.setMin(1);
				}
				else {
					v.setMin(0);
				}
				v.setMax(v.getMin());
			}
			else {
				if(x.equals(v.getMin())) {
					Integer firstCluster = ((VEBTree) v.getSummary()).getMin();
					x = index(firstCluster, ((VEBTree)v.getCluster()[firstCluster]).getMin(), v);
					v.setMin(x);
				}
				this.deleteElement(v.getCluster()[high(x,v)], low(x,v));
				if(((VEBTree)v.getCluster()[high(x,v)]).getMin() == null) {
					this.deleteElement(v.getSummary(), high(x,v));
					if(x.equals(v.getMax())) {
						Integer summaryMax = ((VEBTree)v.getSummary()).getMax();
						if(summaryMax == null) {
							v.setMax(v.getMin());
						}
						else {
							v.setMax(index(summaryMax, ((VEBTree)v.getCluster()[summaryMax]).getMax(),v));
						}
					}
				}
				else if(x.equals(v.getMax())) {
					v.setMax(index(high(x,v), ((VEBTree)v.getCluster()[high(x,v)]).getMax(), v));
				}
			}	
		}
	}
	
	/**
	 * Bestimmt den Vorgänger von x.
	 * @param v
	 * @param x
	 * @return Vorgänger von x
	 */
	public Integer predecessor(SuperVEB veb, Integer x) {
		this.showInfos(veb, x);
		this.animateContainer(veb);
		if(veb instanceof VEBTree) { // VEB
			VEBTree v = (VEBTree) veb;
			if(v.getU() == 2) {
					if(x==1 && v.getMin() != null && v.getMin() == 0) {
						return 0;
					}
					else {
						return null;
					}
			}
			else if(v.getMax() != null && x > v.getMax()) {
				return v.getMax();
			}
			else {
				Integer minLow = ((VEBTree)v.getCluster()[high(x, v)]).getMin();
				if(minLow != null && low(x, v) > minLow) {
					Integer offset=predecessor(v.getCluster()[high(x,v)], low(x, v));
					return index(high(x,v), offset,v);
				}
				else {
					Integer vorgCluster = predecessor(v.getSummary(), high(x,v));
					if(vorgCluster == null) {
						if(v.getMin() != null && x > v.getMin()) {
							return v.getMin();
						}
						else {
							return null;
						}
					}
					else {
						Integer offset= (((VEBTree)v.getCluster()[vorgCluster]).getMax());
						return index(vorgCluster, offset,v);
					}
				}
			}
		}
		else {  // PROTO
			ProtoVEBTree v = (ProtoVEBTree) veb;
			if(v.getU() == 2) {
				if(x  == 1 && v.getA()[0]) {
					return 0;
				}
				else {
					return null;	
				}
			}
			else {
				Integer offset = predecessor(v.getCluster()[high(x, v)], low(x,v));
				if(offset != null ) {
					return index(high(x, v), offset, v);
				}
				else {
					Integer nachfCluster = predecessor(v.getSummary(), high(x, v));
					if(nachfCluster == null) {
						return null;
					}
					else {
						offset = this.maximum(v.getCluster()[nachfCluster]);
						return index(nachfCluster, offset, v);
					}
				}
			}
		}
	}
	
	public Integer minimum(SuperVEB veb) {
		this.showInfos(veb);
		this.animateContainer(veb);
		if(veb instanceof ProtoVEBTree) { // PROTO
			ProtoVEBTree p = (ProtoVEBTree) veb;
			if(p.getU() == 2) {
				if(p.getA()[0]) {
					return 0;
				}
				else if (p.getA()[1]) {
					return 1;
				}
				else {
					return null;
				}
			}
			else {
				Integer minCluster = minimum(veb.getSummary());
				if(minCluster == null) {
					return null;
				}
				else {
					Integer offset = minimum(veb.getCluster()[minCluster]);
					return index(minCluster, offset, veb);
				}
			}			
		}
		else { // VEB
			VEBTree v = (VEBTree) veb;
			return v.getMin();
		}
	}
	
	public Integer maximum(SuperVEB veb) {
		this.showInfos(veb);
		this.animateContainer(veb);
		if(veb instanceof ProtoVEBTree) { // PROTO
			ProtoVEBTree p = (ProtoVEBTree) veb;
			if(p.getU() == 2) {
				if (p.getA()[1]) {
					return 1;
				}
				else if(p.getA()[0]) {
					return 0;
				}
				else {
					return null;
				}
			}
			else {
				Integer maxCluster = maximum(veb.getSummary());
				if(maxCluster == null) {
					return null;
				}
				else {
					Integer offset = maximum(veb.getCluster()[maxCluster]);
					return index(maxCluster, offset, veb);
				}
			}	 
		} 
		else { // VEB
			VEBTree v = (VEBTree) veb;
			return v.getMax();
		}
	}
	
	/**
	 * Bestimmt den Nachfolger von x
	 * @param v
	 * @param x
	 * @return Nachfolger von x
	 */
	public Integer successor(SuperVEB veb, Integer x) {
		this.showInfos(veb, x);
		this.animateContainer(veb);
		if(veb instanceof VEBTree) { // VEB
			VEBTree v = (VEBTree) veb;
			if(v.getU() == 2) {
				if(x  == 0 && v.getMax() == 1) {
					return 1;
				}
				else
					return null;
			}
			else if(v.getMin() != null && x < v.getMin()) {
				return v.getMin();
			}
			else { 
				Integer maxLow  = ((VEBTree)(v.getCluster()[high(x, v)])).getMax();
				if(maxLow != null && low(x, v) < maxLow) {
					Integer offset = successor(v.getCluster()[high(x, v)],low(x,v));
					return index(high(x,v), offset,v);
				}
				else {
					Integer succCluster= successor(v.getSummary(), high(x,v));
					if(succCluster == null) {
						return null;
					}
					else {
						Integer offset = ((VEBTree)(v.getCluster()[succCluster])).getMin();
						return index(succCluster, offset,v);
					}
				}
			}
		}
		else { // Proto-VEB
			ProtoVEBTree v = (ProtoVEBTree) veb;
			if(v.getU() == 2) {
				if(x  == 0 && v.getA()[1]) {
					return 1;
				}
				else {
					return null;	
				}		
			}
			else {
				Integer offset = successor(v.getCluster()[high(x, v)], low(x,v));
				if(offset != null ) {
					return index(high(x, v), offset, v);
				}
				else {
					Integer succCluster = successor(v.getSummary(), high(x, v));
					if(succCluster == null) {
						return null;
					}
					else {
						offset = this.minimum(v.getCluster()[succCluster]);
						return index(succCluster, offset, v);
					}
				}
			}
		}
	}
	
	/**
	 * Fügt Werte in einen leeren vEB-Baum ein.
	 * @param v
	 * @param x
	 */
	public void emptyInsert(SuperVEB veb, Integer x) {
		this.showInfos(veb, x);
		this.animateContainer(veb);
		this.drawGraph();
		if(veb instanceof VEBTree) {
			VEBTree v = (VEBTree) veb;
			v.setMin(x);
			v.setMax(x);
		}
	}
	

	/**
	 * Fügt den Wert x in den Baum ein.
	 * @param v
	 * @param x
	 */
	public void insert(SuperVEB veb, Integer x) {
		this.showInfos(veb,x);
		this.animateContainer(veb);
		this.drawGraph();
		if(veb instanceof ProtoVEBTree) { // Proto
			ProtoVEBTree p = (ProtoVEBTree) veb;
			if(p.getU() == 2) {
				p.setA(true, x);
			}
			else {
				insert(p.getCluster()[high(x, p)], low(x, p));
				insert(p.getSummary(), high(x, p));
			}
		}
		else { // VEB
			VEBTree v = (VEBTree) veb;
			if(v.getMin() == null) {
				this.emptyInsert(v, x);
			}
			else {
				if(x < v.getMin()) {
					Integer xSpeicher = x;
					Integer vMinSpeicher = v.getMin();
					v.setMin(xSpeicher);
					x = vMinSpeicher;
				}
				if(v.getU() > 2) {
					if(((VEBTree)v.getCluster()[high(x,v)]).getMin() == null) {
						insert(v.getSummary(), high(x, v));
						this.emptyInsert(v.getCluster()[high(x,v)], low(x,v));
					}
					else {
						this.insert(v.getCluster()[high(x,v)], low(x, v));
					}
				}
				if(x > v.getMax()) {
					v.setMax(x);
				}
			}	
		}
	}
	
	private void fillLabel(SuperVEB v) {
		String text = "van-Emde-Boas-Baum: ";
		if(v instanceof ProtoVEBTree) {
			text = "Proto-van-Emde-Boas-Baum: ";
		}
		for(int i = 0; i < v.getNumberList().length; i++) {
			if(v.getNumberList()[i]) {
				text+= i + ",";
			}
		}
		text = text.substring(0, text.length() - 1);
		this.myFrame.getInfolabel().setText(text);  
	}
	
	private boolean inputOk(String s, int checkInListe) {
		boolean result = true;
		if(s == null ) {
			return false;
		}
		try {
			int sInInt = Integer.parseInt(s);
			if(sInInt < Constants.MIN_VALUE ||  sInInt > Constants.MAX_VALUE) {
				return false;
			}
			// falls checkInListe 0 oder andere Zahl: keine Beachtung, ob Wert in Liste vorhanden ist oder nicht
			if(checkInListe == 1) { //1 bedeutet Wert muss in Liste sein
				result = this.vEBTree.getNumberList()[sInInt];
			}
			if (checkInListe == 2) { // 2 bedeutet, Wert darf nicht in Liste sein
				result = !(this.vEBTree.getNumberList()[sInInt]);
			}
		}
		catch(NumberFormatException nfe) {
			return false;
		}
		return result;
	}
	
	/**
	 * Der übergebene Text wird im Infobereich der Anwendung angezeigt. Der Text
	 * wird an den vorhandenen Text angehängt und es wird zum Ende gescrollt.
	 * 
	 * @param text der anzuzeigende Text
	 */
	private void outputText(String text) {		
		SwingUtilities.invokeLater(() -> {
			this.myFrame.getDisplay().append("\n" + text);
			JScrollBar scroll = this.myFrame.getScroll().getVerticalScrollBar();
			scroll.setValue(scroll.getMaximum()); // an das Ende scrollen
		});	
	}
	
	private void showInfos(SuperVEB veb, Integer x) {
		this.outputText("Neues V: " + veb.getId() + ", neues X: " + Constants.coalesce(x));
	}
	
	private void showInfos(SuperVEB veb) {
		this.outputText("Neues V: " + veb.getId() );
	}
	
	/**
	 * Markiert den übergebenen Container und wartet danach.
	 * @param veb
	 */
	public void animateContainer(SuperVEB veb) {
		this.defaultStyleContainers(); 
		Object found = null;	
		for(int i = 0; i < containerList.size(); i++) {
			if(containerList.get(i).getV() == veb) {
				found = containerList.get(i).getMainCont();
		        break;
			}
		}
		if(found != null) {
			try {
		        graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, Constants.BACKGROUND_COLOR_ANIMATED, new Object[]{found});
		        graphComponent.refresh();
		        Thread.sleep(Constants.SLEEP_MS);  
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}  
		}
	}	
}
