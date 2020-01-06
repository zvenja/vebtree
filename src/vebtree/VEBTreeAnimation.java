package vebtree;

import javax.swing.SwingUtilities;

import datastructure.VEBTree;

public class VEBTreeAnimation {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int universe = 16;
	    		boolean[] m = new boolean[universe];
	    		for(int i = 0; i < m.length; i++) {
	    			m[i] = false;
	    		}
	    		VEBTree myVEB = new VEBTree(m);		
	    		VEBFrame frame = new VEBFrame();	
	    		Controller c = new Controller(myVEB, frame);		
	    		c.createGui();
	    		c.addActionListeners();
	        }
	    });
	}
}
