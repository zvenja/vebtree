package vebtree;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;


public class VEBFrame extends JFrame {
	private static final long serialVersionUID = -2707712944901661771L;
	private JButton memberButton;
	private JButton initButton;
	private JButton deleteButton;
	private JButton insertButton;
	private JButton succButton;
	private JButton predButton;
	private JButton zoomInButton;
	private JButton zoomOutButton;
	private JButton minButton;
	private JButton maxButton;
	private JLabel infolabel;
	private JTextArea display;
	private JScrollPane scroll;
	private JSplitPane split;
	/**
	 * Konstruktor, Erstellt aufgrund des VEB-Baums den Graphen
	 * @param v
	 */
	public VEBFrame() {	
		super(Constants.FRAME_TITLE);
		this.initFrame();
	}
		
	private void initFrame() {     
		JToolBar toolBar = new JToolBar("");
		toolBar.setFloatable(false);
		toolBar.setRollover(true);
		this.memberButton = new JButton("Suche");
		this.initButton = new JButton("Initialisiere");
		this.deleteButton = new JButton("Wert löschen");
		this.insertButton = new JButton("Wert einfügen");
		this.predButton = new JButton("Vorgänger");
		this.succButton = new JButton("Nachfolger");
		this.zoomInButton = new JButton("Zoom In");
		this.zoomOutButton = new JButton("Zoom Out");
		this.minButton = new JButton("Minimum");
		this.maxButton = new JButton("Maximum");
		toolBar.add(initButton);
		toolBar.add(zoomInButton);
		toolBar.add(zoomOutButton);
		toolBar.add(minButton);
		toolBar.add(maxButton);
		toolBar.add(memberButton);
		toolBar.add(deleteButton);
		toolBar.add(insertButton);
		toolBar.add(predButton);
		toolBar.add(succButton);
		this.add(toolBar, BorderLayout.PAGE_START);
		this.infolabel = new JLabel();
		infolabel.setFont(new Font(infolabel.getFont().getName(), Font.PLAIN,20));
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout((LayoutManager) new BoxLayout(infoPanel, BoxLayout.Y_AXIS));       
		infoPanel.add(infolabel);
	    display = new JTextArea(); 
	    display.setFont(new Font("monospaced", Font.PLAIN, 12));
	    display.setEditable ( false ); 
	    infoPanel.add(display);
	    scroll = new JScrollPane ( display );
	    scroll.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
	    infoPanel.add (scroll);
	    JPanel top = new JPanel();
	    split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, top, infoPanel);
	    split.setDividerLocation(700);
	    this.add(split);
	}

	public JButton getMemberButton() {
		return memberButton;
	}

	public JButton getInitButton() {
		return initButton;
	}

	public JButton getDeleteButton() {
		return deleteButton;
	}

	public JButton getInsertButton() {
		return insertButton;
	}

	public JButton getSuccButton() {
		return succButton;
	}

	public JButton getPredButton() {
		return predButton;
	}

	public JButton getZoomInButton() {
		return zoomInButton;
	}

	public JButton getZoomOutButton() {
		return zoomOutButton;
	}

	public JButton getMinButton() {
		return minButton;
	}
	
	public JButton getMaxButton() {
		return maxButton;
	}
	public JLabel getInfolabel() {
		return infolabel;
	}

	public JTextArea getDisplay() {
		return display;
	}

	public JScrollPane getScroll() {
		return scroll;
	} 
	
	public JSplitPane getSplit() {
		return split;
	}
}
