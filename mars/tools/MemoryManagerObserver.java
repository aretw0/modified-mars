   package mars.tools;
   import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import java.awt.*;
   import java.awt.event.*;
import java.util.Observable;
import java.util.Vector;

import mars.*;
import mars.mips.hardware.AccessNotice;
import mars.mips.hardware.Memory;
import mars.mips.hardware.MemoryAccessNotice;
import mars.mips.so.memorymanager.MemoryManager;
import mars.mips.so.processmanager.ProcessTable;
import mars.util.SystemIO;
	
/*
Copyright (c) 2003-2006,  Pete Sanderson and Kenneth Vollmar

Developed by Pete Sanderson (psanderson@otterbein.edu)
and Kenneth Vollmar (kenvollmar@missouristate.edu)

Permission is hereby granted, free of charge, to any person obtaining 
a copy of this software and associated documentation files (the 
"Software"), to deal in the Software without restriction, including 
without limitation the rights to use, copy, modify, merge, publish, 
distribute, sublicense, and/or sell copies of the Software, and to 
permit persons to whom the Software is furnished to do so, subject 
to the following conditions:

The above copyright notice and this permission notice shall be 
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, 
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR 
ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION 
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

(MIT license, http://www.opensource.org/licenses/mit-license.html)
 */
	
   /**
	 * The "hello world" of MarsTools!
	 */
    @SuppressWarnings("serial")
	public class MemoryManagerObserver extends AbstractMarsToolAndApplication {    	
    	public static JTextField hitsCounts;
    	public static JTextField missCounts;
    	public static JTextField SubsTPagina;
    	public static JTextField TempoTotal;
    	public static JTextField TempoSubst;
    	public static JTextField TempoAcessoMem;
    	public static JTextField TempoAcessoDisco;
    	
    	JTextArea JLog;

    	
    	public static JComboBox<Integer> qtdPaginaMemVirtual;
    	public static JComboBox<Integer> tamPagina;
    	public static JComboBox<Integer> qtdMolduraMemFisica;
    	public static JComboBox<String> metodoPaginacao;

   
    	private static String heading =  "Memory Manager";
		private static String version = " Version 2.0";
		private static JTable table;
		private static JTextArea hitMissMessage;
		public static int lastAdress = -1;
   	/**
   	 * Simple constructor, likely used to run a stand-alone memory reference visualizer.
   	 * @param title String containing title for title bar
   	 * @param heading String containing text for heading shown in upper part of window.
   	 */
       public MemoryManagerObserver(String title, String heading) {
         super(title,heading);
      }
   	 
   	 /**
   	  *  Simple constructor, likely used by the MARS Tools menu mechanism
   	  */
       public MemoryManagerObserver() {
         super (heading+", "+version, heading);
      }
   		 
   		 
   	/**
   	 * Main provided for pure stand-alone use.  Recommended stand-alone use is to write a 
   	 * driver program that instantiates a MemoryReferenceVisualization object then invokes its go() method.
   	 * "stand-alone" means it is not invoked from the MARS Tools menu.  "Pure" means there
   	 * is no driver program to invoke the application.
   	 */
       public static void main(String[] args) {
         new MemoryManagerObserver(heading+", "+version,heading).go();
      }
   	
   
       /**
   	  *  Required method to return Tool name.  
   	  *  @return  Tool name.  MARS will display this in menu item.
   	  */
       public String getName() {
         return "Memory Manager Observer";
      }
   	
      /**
   	 *  Implementation of the inherited abstract method to build the main 
   	 *  display area of the GUI.  It will be placed in the CENTER area of a 
   	 *  BorderLayout.  The title is in the NORTH area, and the controls are 
   	 *  in the SOUTH area.
   	 */
     protected JComponent buildMainDisplayArea() {
    	 
    	JPanel painel = new JPanel();

   		BorderLayout layout = new BorderLayout();
   		layout.setVgap(10);
   		layout.setHgap(10);
   		painel.setLayout(layout); 
   		
   		JScrollPane scrollPane = new JScrollPane();
	 	scrollPane.setBounds(196, 11, 343, 397);
	 	painel.add(scrollPane);
	 		
	 	table = new JTable();
	 	table.setModel(new DefaultTableModel(
	 		new Object[][] {
	 		},
	 		new String[] {
	 			"Index da Tabela", "Moldura"
	 		} 			
	 		) {
	 		Class[] columnTypes = new Class[] {
	 			String.class, String.class
	 		};
	 		public Class<?> getColumnClass(int columnIndex) {
	 			return columnTypes[columnIndex];
	 		}
	 		boolean[] columnEditables = new boolean[] {
	 			false, true
	 		};
	 		public boolean isCellEditable(int row, int column) {
	 			return columnEditables[column];
	 		}
	 	    });
	 		table.getColumnModel().getColumn(0).setResizable(false);
	 		table.getColumnModel().getColumn(0).setPreferredWidth(87);
	 		table.getColumnModel().getColumn(1).setResizable(false);
	 		table.getColumnModel().getColumn(1).setPreferredWidth(92);
	 		scrollPane.setViewportView(table);	
	 		
	 		hitMissMessage = new JTextArea();
	 		hitMissMessage.setEditable(false);
	 		hitMissMessage.setLineWrap(true);
	 		hitMissMessage.setWrapStyleWord(true);
	 		hitMissMessage.setFont(new Font("Ariel",Font.PLAIN,12));
	        
	        int valor = 0;
	        if(ProcessTable.getExeProc()!=null){
	         valor = ProcessTable.getExeProc().getPidProc();
	        }
	        hitMissMessage.setText(
	             " "
	           );
	        hitMissMessage.setCaretPosition(0); // Assure first line is visible and at top of scroll pane.
			painel.add(hitMissMessage);
			//painel.add(buildConfigPanel(), BorderLayout.NORTH);
			painel.add(buildInfoPanel(), BorderLayout.EAST);
			painel.add(buildSelectPainel(), BorderLayout.WEST);
			painel.add(scrollPane);			
			return painel;
	      }
     
     private JPanel buildSelectPainel() {
    	Vector<Integer> tamPg = new Vector<Integer>();
 		tamPg.add(new Integer(2));
 		tamPg.add(new Integer(4));
 		tamPg.add(new Integer(8));
 		tamPg.add(new Integer(16));
 		// sizes.add(new Integer(32));

 		Vector<Integer> qtdBloc = new Vector<Integer>();
 		qtdBloc.add(new Integer(4));
 		qtdBloc.add(new Integer(8));
 		qtdBloc.add(new Integer(16));
 		qtdBloc.add(new Integer(32));

 		Vector<Integer> qtdMemFis = new Vector<Integer>();
 		qtdMemFis.add(new Integer(2));
 		qtdMemFis.add(new Integer(4));
 		qtdMemFis.add(new Integer(8));
 		qtdMemFis.add(new Integer(12));
 		
 		Vector<String> metodos = new Vector<String>();
 		metodos.add("NRU");
 		metodos.add("FIFO");
 		metodos.add("Segunda Chance");
 		metodos.add("LRU"); 
    	 
 		metodoPaginacao = new JComboBox<String>(metodos);
 		metodoPaginacao.setSelectedIndex(1);
		qtdPaginaMemVirtual = new JComboBox<Integer>(qtdBloc);
		qtdPaginaMemVirtual.setSelectedIndex(2);
		qtdMolduraMemFisica = new JComboBox<Integer>(qtdMemFis);
		tamPagina = new JComboBox<Integer>(tamPg);
 		
 		metodoPaginacao.setAlignmentX(CENTER_ALIGNMENT);
 		qtdPaginaMemVirtual.setAlignmentX(CENTER_ALIGNMENT);
 		qtdMolduraMemFisica.setAlignmentX(CENTER_ALIGNMENT);
 		tamPagina.setAlignmentX(CENTER_ALIGNMENT);	
 		
 		JPanel panel = new JPanel();
 		JPanel outerPanel = new JPanel();
 		outerPanel.setLayout(new BorderLayout());

 		GridBagLayout gbl = new GridBagLayout();
 		panel.setLayout(gbl);

 		GridBagConstraints c = new GridBagConstraints();

 		c.insets = new Insets(5, 5, 2, 5);
 		c.gridx = 1;
 		c.gridy = 1;

 		panel.add(new JLabel("Qt. Paginas"), c);
 		c.gridy++;
 		panel.add(qtdPaginaMemVirtual, c);
 		
 		c.gridy++;
 		panel.add(new JLabel("Tamanho da página"), c);
 		c.gridy++;
 		panel.add(tamPagina, c);
 		
 		c.gridy++;
 		panel.add(new JLabel("Qt. Molduras"), c);
 		c.gridy++;
 		panel.add(qtdMolduraMemFisica, c);
 		
 		c.gridy++;
 		panel.add(new JLabel("Método paginação"), c);
 		c.gridy++;
 		panel.add(metodoPaginacao, c);

 		outerPanel.add(panel, BorderLayout.NORTH);
 		return outerPanel;
	}	

 	private JPanel buildInfoPanel() {
 		hitsCounts = new JTextField();
 		hitsCounts.setColumns(10);
 		hitsCounts.setEditable(false);
 		hitsCounts.setAlignmentX(CENTER_ALIGNMENT);
 		hitsCounts.setText(String.valueOf(0));
 		
 		missCounts = new JTextField();
 		missCounts.setColumns(10);
 		missCounts.setEditable(false);
 		missCounts.setAlignmentX(CENTER_ALIGNMENT);
 		missCounts.setText(String.valueOf(0));
 		
// 		message = new JTextField();
// 		message.setColumns(10);
// 		message.setEditable(false);
// 		message.setAlignmentX(CENTER_ALIGNMENT);
 		
 		SubsTPagina = new JTextField();
 		SubsTPagina.setColumns(10);
 		SubsTPagina.setEditable(false);
 		SubsTPagina.setAlignmentX(CENTER_ALIGNMENT);
 		SubsTPagina.setText(String.valueOf(0));
// 		
// 		TempoTotal = new JTextField();
// 		TempoTotal.setColumns(10);
// 		TempoTotal.setEditable(false);
// 		TempoTotal.setAlignmentX(CENTER_ALIGNMENT);
// 		
// 		TempoAcessoDisco = new JTextField();
// 		TempoAcessoDisco.setColumns(10);
// 		TempoAcessoDisco.setEditable(false);
// 		TempoAcessoDisco.setAlignmentX(CENTER_ALIGNMENT);
// 		
// 		TempoAcessoMem = new JTextField();
// 		TempoAcessoMem.setColumns(10);
// 		TempoAcessoMem.setEditable(false);
// 		TempoAcessoMem.setAlignmentX(CENTER_ALIGNMENT);
// 		
// 		TempoSubst = new JTextField();
// 		TempoSubst.setColumns(10);
// 		TempoSubst.setEditable(false);
// 		TempoSubst.setAlignmentX(CENTER_ALIGNMENT);

 		JPanel panel = new JPanel();
 		JPanel outerPanel = new JPanel();
 		outerPanel.setLayout(new BorderLayout());

 		GridBagLayout gbl = new GridBagLayout();
 		panel.setLayout(gbl);

 		GridBagConstraints c = new GridBagConstraints();

 		c.insets = new Insets(5, 5, 2, 5);
 		c.gridx = 1;
 		c.gridy = 1;

 		panel.add(new JLabel("Hits"), c);
 		c.gridy++;
 		panel.add(hitsCounts, c);
 		
 		c.gridy++;
 		panel.add(new JLabel("Miss"), c);
 		c.gridy++;
 		panel.add(missCounts, c);
 		
// 		c.gridy++;
// 		panel.add(new JLabel("PC"), c);
// 		c.gridy++;
// 		panel.add(message, c);
 		
 		c.gridy++;
 		panel.add(new JLabel("Substituições de Pagina"), c);
 		c.gridy++;
 		panel.add(SubsTPagina, c);
// 		
// 		c.gridy++;
// 		panel.add(new JLabel("Tempo de acesso a Memoria"), c);
// 		c.gridy++;
// 		panel.add(TempoAcessoMem, c);
// 		
// 		c.gridy++;
// 		panel.add(new JLabel("Tempo de acesso ao disco"), c);
// 		c.gridy++;
// 		panel.add(TempoAcessoDisco, c);
// 		
// 		c.gridy++;
// 		panel.add(new JLabel("Tempo de Substituiçoes"), c);
// 		c.gridy++;
// 		panel.add(TempoSubst, c);
// 		
// 		c.gridy++;
// 		panel.add(new JLabel("Tempo Total"), c);
// 		c.gridy++;
// 		panel.add(TempoTotal, c);

 		outerPanel.add(panel, BorderLayout.NORTH);
 		return outerPanel;
 	}
 	
	      public static void updateDisplay(String[] dados) {    	  
	    	  ((DefaultTableModel)table.getModel()).addRow(dados);          
	      } 
	      public static void updateDisplay(int indexRemove) {   
	    	   ((DefaultTableModel)table.getModel()).removeRow(indexRemove);      	   
	        }
	       public static void updateDisplay(String dados, int index) {   
	    	   ((DefaultTableModel)table.getModel()).setValueAt(dados, index, 1);    	   
	        }
	       
	       protected void processMIPSUpdate(Observable memory, AccessNotice notice) {
	    	   if (notice.getAccessType() != AccessNotice.READ) return;
				MemoryAccessNotice m = (MemoryAccessNotice) notice;
				int a = m.getAddress();
				if (a == lastAdress) return;
				lastAdress = a;
				MemoryManager.VerificarMemoria(lastAdress);			
	        }
	       
	       protected void addAsObserver() {
	    	   addAsObserver(Memory.textBaseAddress, Memory.textLimitAddress);
	       }
	   }