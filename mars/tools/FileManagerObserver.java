package mars.tools;

import mars.mips.hardware.AccessNotice;
import mars.mips.hardware.Memory;
import mars.mips.hardware.MemoryAccessNotice;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import java.util.*;

public class FileManagerObserver extends AbstractMarsToolAndApplication {
	
	private Graphics drawingArea;
    private JPanel canvas;
    private JPanel results;
    
	// Some GUI settings
    private EmptyBorder emptyBorder = new EmptyBorder(4,4,4,4);
    private Font countFonts = new Font("Times", Font.BOLD,12);
    private Color backgroundColor = Color.WHITE;
    
	public static JComboBox blockAmount;
	public static JComboBox blockSize;
	public static JProgressBar progressbar;
	public static int blockUse = 0; // para definir progressbar
	
	public static DynamicTree treePanel;
	public static DefaultTreeModel treeModel;
	public static DefaultMutableTreeNode rootNode;
	public static java.util.List<DefaultMutableTreeNode> dir;
	
	/** text field for log output */
	public static JTextArea m_taLog;
	
	
	// Values for Combo Boxes
	
	/* ESTOU ME INSPIRANDO NO MEMORY REFERENCE VISUALIZATION
	 * 
	 * Ia tentar usar os controles dela que são com combobox
	 * 
	 * COM UNIT 32X32
	 * DIPLAY 512*256
	 * 16*8 = 128 BLOCOS
	 * 
	 * COM UNIT 16X16
	 * DIPLAY 512*256
	 * 32*16 = 512 BLOCOS
	 * 
	 * COM UNIT 8X8
	 * DIPLAY 512*256
	 * 64*32 = 2048 BLOCOS
	 * 
	 * COM UNIT 4X4
	 * DIPLAY 512*256
	 * 128*64 = 8192 BLOCOS
	 * 
	 *************************
	 * 
	 * COM UNIT 32X32
	 * DIPLAY 256*256
	 * 8*8 = 64 BLOCOS
	 * 
	 * COM UNIT 16X16
	 * DIPLAY 256*256
	 * 16*16 = 256 BLOCOS
	 * 
	 * COM UNIT 8X8
	 * DIPLAY 256*256
	 * 32*32 = 1024 BLOCOS
	 * 
	 * COM UNIT 4X4
	 * DIPLAY 256*256
	 * 64*64 = 4096 BLOCOS
	 * 
	 *************************
	 * 
	 * COM UNIT 32X32
	 * DIPLAY 512*512
	 * 16*16 = 256 BLOCOS
	 * 
	 * COM UNIT 16X16
	 * DIPLAY 512*512
	 * 32*32 = 1024 BLOCOS
	 * 
	 * COM UNIT 8X8
	 * DIPLAY 512*512
	 * 64*64 = 4096 BLOCOS
	 * 
	 * 
	 * */
   	
      private final String[] wordsPerUnitChoices  = {"1","2","4","8","16","32","64","128","256","512","1024","2048"};
      private final int defaultWordsPerUnitIndex  = 0;
      private final String[] blockSizing  = {"128","512","2048","8192"};
      private final int defaultBlockSizing = 2;
      private final String[] visualizationUnitPixelChoices  = {"1","2","4","8","16","32"};
      private final int defaultVisualizationUnitPixelIndex  = 3;
      private final String[] displayAreaPixelChoices  = {"64","128","256","512","1024"};
      private final int defaultDisplayIndex  = 2;
      private final boolean defaultDrawHashMarks = true;
   
      // Values for display canvas.  Note their initialization uses the identifiers just above.
   
      private int unitPixelWidth = Integer.parseInt(visualizationUnitPixelChoices[defaultVisualizationUnitPixelIndex]);
      private int unitPixelHeight = Integer.parseInt(visualizationUnitPixelChoices[defaultVisualizationUnitPixelIndex]);
      private int wordsPerUnit = Integer.parseInt(wordsPerUnitChoices[defaultWordsPerUnitIndex]);
      private int visualizationAreaWidthInPixels = 512;
      private int visualizationAreaHeightInPixels = 256;
   	
   	//`Values for mapping of reference counts to colors for display.
   	
   	// This array of (count,color) pairs must be kept sorted! count is low end of subrange. 
   	// This array will grow if user adds colors at additional counter points (see below).
      private CounterColor[] defaultCounterColors = 
                                             { new CounterColor(0, Color.black),
                                               new CounterColor(1, Color.blue), 
                                               new CounterColor(2, Color.green), 
                                               new CounterColor(3, Color.yellow), 
                                               new CounterColor(5, Color.orange), 
                                               new CounterColor(10, Color.red)
         												}; 

      private int[] countTable = { 
              0,    1,    2,    3,    4,     5,     6,      7,      8,       9,   10,   // 0-10
                   20,   30,   40,   50,   100,   200,    300,    400,     500, 1000,   // 11-20
                 2000, 3000, 4000, 5000, 10000, 50000, 100000, 500000, 1000000          // 21-29
           	};
     private final int COUNT_INDEX_INIT = 10;  // array element #10, arbitrary starting point
     	   
  	// The next four are initialized dynamically in initializeDisplayBaseChoices()
     private String[] displayBaseAddressChoices;
     private int[] displayBaseAddresses;
     private int defaultBaseAddressIndex;
     private int baseAddress;
  	
     private Grid theGrid;
     private CounterColorScale counterColorScale;
  	         	
	
	private static String heading =  "File Manager";
	private static String version = " Version 0.1";
	public static int lastAdress = -1; // comparativo de endereço
 	
	protected FileManagerObserver(String title, String heading) {
		super(title, heading);
		// TODO Auto-generated constructor stub
	}
	/**
	*  Simple constructor, likely used by the MARS Tools menu mechanism
	*/
	public FileManagerObserver() {
		super (heading+", "+version, heading);
   	}
	
    public static void main(String[] args) {
        new FileManagerObserver(heading+", "+version,heading).go();
     }
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "File Manager Observer";
	}

	@Override
	protected JComponent buildMainDisplayArea() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel();



   		BorderLayout layout = new BorderLayout();
   		layout.setVgap(10);
   		layout.setHgap(10);
   		panel.setLayout(layout);
   	
   		panel.add(buildConfigPanel(), BorderLayout.NORTH);
   		panel.add(new JScrollPane(buildInfoPanel()), BorderLayout.WEST);
		panel.add(new JScrollPane(buildLogPanel()), BorderLayout.CENTER);
//		panel.add(buildVisualizationArea(), BorderLayout.CENTER);
   		
   		
   		return panel;
	}
	private JPanel buildInfoPanel() {		
		return treePanel = new DynamicTree();
	}
	private JPanel buildConfigPanel() {
		JPanel panel = new JPanel();
		blockSize = new JComboBox(wordsPerUnitChoices);
		blockSize.setEditable(false);
		blockSize.setEditable(false);
		blockSize.setBackground(backgroundColor);
		blockSize.setSelectedIndex(defaultWordsPerUnitIndex);
		blockSize.setToolTipText("Number of memory words represented by one visualization element (rectangle)");
		blockSize.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     wordsPerUnit = getIntComboBoxSelection(blockSize);
                     reset();
                  }
               });
		blockAmount = new JComboBox(blockSizing);
		blockAmount.setEditable(false);
		blockAmount.setBackground(backgroundColor);
		blockAmount.setSelectedIndex(defaultBlockSizing);
		blockAmount.setToolTipText("Number of rectangles representing memory");
		blockAmount.addActionListener(
	                new ActionListener() {
	                   public void actionPerformed(ActionEvent e) {
	                     int value = getIntComboBoxSelection(blockAmount);
	                     switch (value) {
	                     
		                    case 128:
								unitPixelHeight = unitPixelWidth = 32;
		                    	break;
							case 512:
								unitPixelHeight = unitPixelWidth = 16;
								break;
							case 2048:
							unitPixelHeight = unitPixelWidth = 8;
								break;
							case 8192:
							unitPixelHeight = unitPixelWidth = 4;
								break;
							default:
								break;
	                     }
	                     theGrid = createNewGrid();
	                     updateDisplay();
	                  }
	               });

		progressbar = new JProgressBar(JProgressBar.HORIZONTAL);
		progressbar.setStringPainted(true);
		
		panel.add(new JLabel("# of DISK entries"));
		panel.add(blockAmount);
		panel.add(new JLabel("Alocation size"));
		panel.add(blockSize);
		
		panel.add(new JLabel("Use so far:"));
		panel.add(progressbar);
		
		return panel;
	}
	
	private JPanel buildLogPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		m_taLog = new JTextArea();
		m_taLog.setRows(6);
		m_taLog.setEditable(false);

		panel.add(new JLabel("Log"), BorderLayout.NORTH);
		panel.add(new JScrollPane(m_taLog), BorderLayout.CENTER);
		
		return panel;
	}
	
	protected void initializePreGUI() {
        counterColorScale = new CounterColorScale(defaultCounterColors);
     	// NOTE: Can't call "createNewGrid()" here because it uses settings from
     	//       several combo boxes that have not been created yet.  But a default grid
     	//       needs to be allocated for initial canvas display.
        theGrid = new Grid(visualizationAreaHeightInPixels/unitPixelHeight,
                           visualizationAreaWidthInPixels/unitPixelWidth);
   }
     protected void initializePostGUI() {
        wordsPerUnit = Integer.parseInt(wordsPerUnitChoices[defaultWordsPerUnitIndex]);
        theGrid = createNewGrid();
//        updateBaseAddress();
     }
      protected void reset() {
        resetCounts();
        updateDisplay();
     }
       protected void updateDisplay() {
//        canvas.repaint();
        progressbar.setValue(blockUse);
        progressbar.setMaximum(getIntComboBoxSelection(blockAmount));
     }
	
	private JComponent buildVisualizationArea() {
        canvas = new GraphicsPanel();
        canvas.setPreferredSize(getDisplayAreaDimension());
        canvas.setToolTipText("Disk reference count visualization area");
        return canvas;
     }
	
	
	protected void processMIPSUpdate(Observable memory, AccessNotice notice) {
		if (notice.getAccessType() != AccessNotice.READ) return;
		MemoryAccessNotice m = (MemoryAccessNotice) notice;
		int a = m.getAddress();
/*
 * 		Exemplo de como se adiciona a JTree
		p1 = treePanel.addObject(rootNode,"p1");
		p2 = treePanel.addObject(rootNode,"p2");
		p3 = treePanel.addObject(rootNode,"p3");
		treePanel.addObject(p1,"Dir1");
		treePanel.addObject(p1,"Dir2");
		treePanel.addObject(p1,"Dir3");*/
		if (a == lastAdress) return;
		
		
		/*
		 * Precisamos mostrar informações referentes
		 * */
		
    }
       
   protected void addAsObserver() {
	   addAsObserver(Memory.textBaseAddress, Memory.textLimitAddress);
   }
 	// reset all counters in the Grid.
   private void resetCounts() {
     theGrid.reset();
  }
   private Dimension getDisplayAreaDimension() {
       return new Dimension(visualizationAreaWidthInPixels, visualizationAreaHeightInPixels);
    }
   private Grid createNewGrid() {
       int rows = visualizationAreaHeightInPixels/unitPixelHeight;
       int columns = visualizationAreaWidthInPixels/unitPixelWidth;
       return new Grid(rows,columns);
   }
   public static int getIntComboBoxSelection(JComboBox comboBox) {
       try {
          return Integer.parseInt((String)comboBox.getSelectedItem());
       } 
           catch (NumberFormatException nfe) {
          	// Can occur only if initialization list contains badly formatted numbers.  This
          	// is a developer's error, not a user error, and better be caught before release.
             return 1;
          }
   }
   private class GraphicsPanel extends JPanel {
       // override default paint method to assure visualized reference pattern is produced every time
   	 // the panel is repainted.
       public void paint(Graphics g) {
         paintGrid(g, theGrid);
         paintHashMarks(g, theGrid);
      }
   
      // Paint (ash marks on the grid.  Their color is chosef to be in
   	// "contrast" to the current color for reference count of zero.
       private void paintHashMarks(Graphics g, Grid grid) {
         g.setColor(getContrastingColor(counterColorScale.getColor(0)));
         int leftX=0;
         int rightX=visualizationAreaWidthInPixels;
         int upperY=0;
         int lowerY=visualizationAreaHeightInPixels;  
         // draw vertical hash marks
         for (int j=0; j<grid.getColumns(); j++) {
            g.drawLine(leftX, upperY, leftX, lowerY); 
            leftX += unitPixelWidth;   // faster than multiplying
         }
         leftX = 0;
      	// draw horizontal hash marks
         for (int i=0; i<grid.getRows(); i++) {
            g.drawLine(leftX, upperY, rightX, upperY); 
            upperY += unitPixelHeight;   // faster than multiplying
         }
      }
   	
   	// Paint the color codes for reference counts.
       private void paintGrid(Graphics g, Grid grid) {
         int upperLeftX = 0, upperLeftY = 0;
         for (int i=0; i<grid.getRows(); i++) {
            for (int j=0; j<grid.getColumns(); j++) {
               g.setColor(counterColorScale.getColor(grid.getElementFast(i,j)));
               g.fillRect(upperLeftX, upperLeftY, unitPixelWidth, unitPixelHeight); 
               upperLeftX += unitPixelWidth;   // faster than multiplying
            }
         	// get ready for next row...
            upperLeftX = 0;
            upperLeftY += unitPixelHeight;     // faster than multiplying
         }
      }
   	
       private Color getContrastingColor(Color color) {
      /* Usual and quick method is to XOR with 0xFFFFFF. Here's a better but slower 
         algorithm from www.codeproject.com/tips/JbColorContrast.asp :
      	If all 3 color components are "close" to 0x80 (midpoint - choose your tolerance),
      	you can get better contrast by adding 0x7F7F7F then ANDing with 0xFFFFFF.
      */
         return new Color(color.getRGB() ^ 0xFFFFFF);
      }
   }
	
   
   private class CounterColorScale {
       CounterColor[] counterColors;
    	
        CounterColorScale(CounterColor[] colors) {
          counterColors = colors;
       }
    
    	// return color associated with specified counter value
        private Color getColor(int count) {
          Color result = counterColors[0].associatedColor;
          int index=0;
          while (index < counterColors.length && count >= counterColors[index].colorRangeStart) {
             result = counterColors[index].associatedColor;
             index++;
          }
          return result;
       }		
    	
    	 // For a given counter value, return the counter value at the high end of the range of
    	 // counter values having the same color.
        private int getHighEndOfRange(int count) {
          int highEnd = Integer.MAX_VALUE;
          if (count < counterColors[counterColors.length-1].colorRangeStart) {
             int index=0;
             while (index < counterColors.length-1 && count >= counterColors[index].colorRangeStart) {
                highEnd = counterColors[index+1].colorRangeStart - 1;
                index++;
             }
          }
          return highEnd;
       }
    	
    	// The given entry should either be inserted into the the scale or replace an existing
    	// element.  The latter occurs if the new CounterColor has same starting counter value
    	// as an existing one.
        private void insertOrReplace(CounterColor newColor) {
          int index = Arrays.binarySearch(counterColors, newColor);
          if (index >= 0) { // found, so replace
             counterColors[index] = newColor;
          } 
          else { // not found, so insert
             int insertIndex = -index-1;
             CounterColor[] newSortedArray = new CounterColor[counterColors.length+1]; 
             System.arraycopy(counterColors, 0, newSortedArray, 0, insertIndex); 
             System.arraycopy(counterColors, insertIndex, newSortedArray, insertIndex+1, counterColors.length-insertIndex); 
             newSortedArray[insertIndex] = newColor; 
             counterColors = newSortedArray;
          }
       }
    }
 	
 	
 	///////////////////////////////////////////////////////////////////////////////////////
    // Each object represents beginning of a counter value range (non-negative integer) and
 	// color for rendering the range.  High end of the range is defined as low end of the
 	// next range minus 1.  For last range, high end is Integer.MAX_VALUE.
     private class CounterColor implements Comparable {
       private int colorRangeStart;
       private Color associatedColor;
        public CounterColor(int start, Color color) {
          this.colorRangeStart = start;
          this.associatedColor = color;
       }
    	
    	// Necessary for sorting in ascending order of range low end.
        public int compareTo(Object other) {
          if (other instanceof CounterColor) {
             return this.colorRangeStart - ((CounterColor)other).colorRangeStart;
          } 
          else {
             throw new ClassCastException();
          }
       }
    }
    
 	
    ////////////////////////////////////////////////////////////////////////
 	// Represents grid of memory access counts
     private class Grid {
    
       int[][] grid;
       int rows, columns;
    	 		  
        private Grid(int rows, int columns) {
          grid = new int[rows][columns];
          this.rows = rows;
          this.columns = columns;
        // automatically initialized to 0, so I won't bother to....
       }
     
        private int getRows() {
          return rows;
       }
    	
        private int getColumns() {
          return columns;
       }
    
    	// Returns value in given grid element; -1 if row or column is out of range.			
        private int getElement(int row, int column) {
          return (row>=0 && row<=rows && column>=0 && column<=columns) ? grid[row][column] : -1;			
       }
    
    	// Returns value in given grid element without doing any row/column index checking.
    	// Is faster than getElement but will throw array index out of bounds exception if
    	// parameter values are outside the bounds of the grid.			
        private int getElementFast(int row, int column) {
          return grid[row][column];			
       }
    	      	
    	// Increment the given grid element and return incremented value.
    	// Returns -1 if row or column is out of range.
        private int incrementElement(int row, int column) {
          return (row>=0 && row<=rows && column>=0 && column<=columns) ? ++grid[row][column] : -1;
       }
    	
    	// Just set all grid elements to 0.
        private void reset() {
          for (int i=0; i<rows; i++) {
             for (int j=0; j<columns; j++) {
                grid[i][j] = 0;
             }
          }
       }
    }
     public class DynamicTree extends JPanel {
    	    
    	    protected JTree tree;
    	    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    	 
    	    public DynamicTree() {
    	        super(new GridLayout(1,0));
    	            
    	        rootNode = new DefaultMutableTreeNode("Mars"); 
    	        treeModel = new DefaultTreeModel(rootNode);
    	        treeModel.addTreeModelListener(new MyTreeModelListener());
    	        tree = new JTree(treeModel);
    	        tree.setEditable(false);
    	        tree.getSelectionModel().setSelectionMode
    	                (TreeSelectionModel.SINGLE_TREE_SELECTION);
    	        tree.setShowsRootHandles(true);
    	 
    	        JScrollPane scrollPane = new JScrollPane(tree);
    	        add(scrollPane);
    	    }
    	 
    	    /** Remove all nodes except the root node. */
    	    public void clear() {
    	        rootNode.removeAllChildren();
    	        treeModel.reload();
    	    }
    	 
    	    /** Remove the currently selected node. */
    	    public void removeCurrentNode() {
    	        TreePath currentSelection = tree.getSelectionPath();
    	        if (currentSelection != null) {
    	            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
    	                         (currentSelection.getLastPathComponent());
    	            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
    	            if (parent != null) {
    	                treeModel.removeNodeFromParent(currentNode);
    	                return;
    	            }
    	        } 
    	 
    	        // Either there was no selection, or the root was selected.
    	        toolkit.beep();
    	    }
    	 
    	    /** Add child to the currently selected node. */
    	    public DefaultMutableTreeNode addObject(Object child) {
    	        DefaultMutableTreeNode parentNode = null;
    	        TreePath parentPath = tree.getSelectionPath();
    	 
    	        if (parentPath == null) {
    	            parentNode = rootNode;
    	        } else {
    	            parentNode = (DefaultMutableTreeNode)
    	                         (parentPath.getLastPathComponent());
    	        }
    	 
    	        return addObject(parentNode, child, true);
    	    }
    	 
    	    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
    	                                            Object child) {
    	        return addObject(parent, child, false);
    	    }
    	 
    	    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
    	                                            Object child, 
    	                                            boolean shouldBeVisible) {
    	        DefaultMutableTreeNode childNode = 
    	                new DefaultMutableTreeNode(child);
    	 
    	        if (parent == null) {
    	            parent = rootNode;
    	        }
    	     
    	    //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
    	        treeModel.insertNodeInto(childNode, parent, 
    	                                 parent.getChildCount());
    	 
    	        //Make sure the user can see the lovely new node.
    	        if (shouldBeVisible) {
    	            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
    	        }
    	        return childNode;
    	    }
    	 
    	    class MyTreeModelListener implements TreeModelListener {
    	        public void treeNodesChanged(TreeModelEvent e) {
    	            DefaultMutableTreeNode node;
    	            node = (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());
    	 
    	            /*
    	             * If the event lists children, then the changed
    	             * node is the child of the node we've already
    	             * gotten.  Otherwise, the changed node and the
    	             * specified node are the same.
    	             */
    	 
    	                int index = e.getChildIndices()[0];
    	                node = (DefaultMutableTreeNode)(node.getChildAt(index));
    	 
    	            System.out.println("The user has finished editing the node.");
    	            System.out.println("New value: " + node.getUserObject());
    	        }
    	        public void treeNodesInserted(TreeModelEvent e) {
    	        }
    	        public void treeNodesRemoved(TreeModelEvent e) {
    	        }
    	        public void treeStructureChanged(TreeModelEvent e) {
    	        }
    	    }
    	}

}
