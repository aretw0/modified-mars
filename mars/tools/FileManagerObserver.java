package mars.tools;

import mars.mips.hardware.AccessNotice;
import mars.mips.hardware.Memory;
import mars.mips.hardware.MemoryAccessNotice;


import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.*;

public class FileManagerObserver extends AbstractMarsToolAndApplication {
	
	private JTextField m_tfAddress;
	private JTextField m_tfIndex;

	private Graphics drawingArea;
    private JPanel canvas;
    private JPanel results;
    
	// Some GUI settings
    private EmptyBorder emptyBorder = new EmptyBorder(4,4,4,4);
    private Font countFonts = new Font("Times", Font.BOLD,12);
    private Color backgroundColor = Color.WHITE;
    
	public static JSpinner blockAmount;
	public static JSpinner blockSize;
	private JProgressBar progressbar;
	
	private JTree treeFile;
	
	/** text field for log output */
	public static JTextArea m_taLog;
	
	
	// Values for Combo Boxes
	
	/* ESTOU ME INSPIRANDO NO MEMORY REFERENCE VISUALIZATION
	 * 
	 * Ia tentar usar os controles dela que são com combobox
	 * 
	 * COM UNIT 16X16
	 * 
	 * DISPLAY 256*256
	 * 1024 BLOCOS
	 * 
	 * DIPLAY 512*512
	 * 
	 * 4096 BLOCOS
	 * 
	 * */
   	
      private final String[] wordsPerUnitChoices  = {"1","2","4","8","16","32","64","128","256","512","1024","2048"};
      private final int defaultWordsPerUnitIndex  = 0;
      private final String[] visualizationUnitPixelWidthChoices  = {"1","2","4","8","16","32"};
      private final int defaultVisualizationUnitPixelWidthIndex  = 4;
      private final String[] visualizationUnitPixelHeightChoices  = {"1","2","4","8","16","32"};
      private final int defaultVisualizationUnitPixelHeightIndex  = 4;
      private final String[] displayAreaPixelWidthChoices  = {"64","128","256","512","1024"};
      private final int defaultDisplayWidthIndex  = 3;
      private final String[] displayAreaPixelHeightChoices  = {"64","128","256","512","1024"};
      private final int defaultDisplayHeightIndex  = 3;
      private final boolean defaultDrawHashMarks = true;
   
      // Values for display canvas.  Note their initialization uses the identifiers just above.
   
      private int unitPixelWidth = Integer.parseInt(visualizationUnitPixelWidthChoices[defaultVisualizationUnitPixelWidthIndex]);
      private int unitPixelHeight = Integer.parseInt(visualizationUnitPixelHeightChoices[defaultVisualizationUnitPixelHeightIndex]);
      private int wordsPerUnit = Integer.parseInt(wordsPerUnitChoices[defaultWordsPerUnitIndex]);
      private int visualizationAreaWidthInPixels = Integer.parseInt(displayAreaPixelWidthChoices[defaultDisplayWidthIndex]);
      private int visualizationAreaHeightInPixels = Integer.parseInt(displayAreaPixelHeightChoices[defaultDisplayHeightIndex]);
   	
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


		treeFile = new JTree();
   		BorderLayout layout = new BorderLayout();
   		layout.setVgap(10);
   		layout.setHgap(10);
   		panel.setLayout(layout);
   	
   		panel.add(buildConfigPanel(), BorderLayout.NORTH);
		panel.add(buildInfoPanel(), BorderLayout.WEST);
		panel.add(buildLogPanel(), BorderLayout.SOUTH);
		panel.add(buildVisualizationArea(), BorderLayout.CENTER);
		panel.add(new JScrollPane(treeFile), BorderLayout.EAST);
   		
   		
   		return panel;
	}
	private JPanel buildInfoPanel() {		
		
		m_tfAddress = new JTextField();
		m_tfIndex = new JTextField();
	
		
		m_tfAddress.setColumns(10);
		m_tfAddress.setEditable(false);
		m_tfAddress.setHorizontalAlignment(JTextField.CENTER);
		m_tfIndex.setColumns(10);
		m_tfIndex.setEditable(false);
		m_tfIndex.setHorizontalAlignment(JTextField.CENTER);			
		
		JPanel panel = new JPanel();	
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new BorderLayout());		
		
		GridBagLayout gbl = new GridBagLayout();		
		panel.setLayout(gbl);
		
		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraints c1 = new GridBagConstraints();
		
		c.insets = new Insets(5, 5, 2, 5);
		c.gridx = 1;		 
		c.gridy = 1;
		
		panel.add(new JLabel("@ Address"), c);		
		c.gridy++;
		panel.add(m_tfAddress, c);
		c.gridy++;
		panel.add(new JLabel("-> Index"), c);
		c.gridy++;
		panel.add(m_tfIndex, c);
						
		outerPanel.add(panel, BorderLayout.NORTH);
		return outerPanel;
	}
	private JPanel buildConfigPanel() {
		JPanel panel = new JPanel();		
		
		blockAmount = new JSpinner();
		blockAmount.setModel(new SpinnerNumberModel(10, 2, 4096, 2));
		blockAmount.setToolTipText("");
	/*	blockAmount.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				unitPixelHeight = unitPixelWidth = (int) blockAmount.getValue();
                 theGrid = createNewGrid();
                 updateDisplay();
				
			}
		});*/
		
		blockSize = new JSpinner();
		blockSize.setModel(new SpinnerNumberModel(10, 1, 100, 1));
		blockSize.setToolTipText("");
		
		progressbar = new JProgressBar(JProgressBar.HORIZONTAL);
		progressbar.setStringPainted(true);
		
		panel.add(new JLabel("# of DISK entries"));
		panel.add(blockSize);
		panel.add(new JLabel("Alocation size"));
		panel.add(blockAmount);
		
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
	
	private JPanel buildSelectPanel() {
		JPanel selectPanel = new JPanel();
		
		return selectPanel;
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
      //  updateBaseAddress();
     }
      protected void reset() {
        resetCounts();
        updateDisplay();
     }
       protected void updateDisplay() {
        canvas.repaint();
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
}
