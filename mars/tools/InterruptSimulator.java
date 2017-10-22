package mars.tools;

import mars.mips.hardware.AccessNotice;
import mars.mips.hardware.Memory;
import mars.mips.hardware.MemoryAccessNotice;
import mars.util.SystemIO;

import java.awt.*;
import java.util.Observable;

import javax.swing.*;

//@SuppressWarnings("serial")
public class InterruptSimulator extends AbstractMarsToolAndApplication {
	private static String name    = "Interrupt Simulator";
	private static String heading =  "Generates process changes";
	private static String version = "Version 1.0 (Arthur)";
	
	protected int lastAddress = -1; // comparativo de endereço
    /**
     * Number of instructions executed until now.
     */
	protected int counter = 0;
	private JTextField counterField;
	/**
     * Timer definition.
     */
	protected int countTimer = 10; // Timer de interrupção
	/**
     * Number of interruptions until now.
     */
	protected int countInter = 0; // contador de interrupções
	private JTextField counterInterField;
    /**
     * Number of instructions until interruption.
     */
    protected int countInst = 3;
	private JProgressBar progressbarInst;
	
	/**
	 * Configuration tools
	*/
	private JToggleButton timerOn;
	private JSpinner timerConfig;	

	public InterruptSimulator(String title, String heading) {
		super(title, heading);
		// TODO Auto-generated constructor stub
	}

    /**
     * Simple construction, likely used by the MARS Tools menu mechanism.
     */
    public InterruptSimulator() {
    	super(name + ", " + version, heading);
    }
    public static void main(String[] args) {
        new InterruptSimulator(heading+", "+version,heading).go();
    }
    
    @Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
    
	@Override
	protected JComponent buildMainDisplayArea() {
		JPanel panel = new JPanel(new GridBagLayout());

		counterField = new JTextField("0", 10);
		counterField.setEditable(false);
		
		counterInterField = new JTextField("0", 10);
		counterInterField.setEditable(false);
		
		progressbarInst = new JProgressBar(JProgressBar.HORIZONTAL);
		progressbarInst.setStringPainted(true);

		timerOn = new JToggleButton("ON/OFF"); 
		timerOn.setToolTipText("Enable interruption");
		
		timerConfig = new JSpinner();
		timerConfig.setModel(new SpinnerNumberModel(10, 2, 100, 1));
		timerConfig.setToolTipText("Sets the time for the interruption");
		
		// Add them to the panel
		
		// Fields
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.gridheight = c.gridwidth = 1;
		c.gridx = 3;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 17, 0);
		panel.add(counterField, c);

		c.insets = new Insets(0, 0, 0, 0);
		c.gridy++;
		panel.add(counterInterField, c);
		
		// progress bar
		c.gridy++;
		panel.add(progressbarInst,c);
		// spinner
		c.gridy++;
		panel.add(timerConfig, c);
		
		// Labels
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = 1;
		c.gridwidth = 2;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 17, 0);
		panel.add(new JLabel("Instructions so far: "), c);
		
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 2;
		c.gridwidth = 1;
		c.gridy++;
		panel.add(new JLabel("Interruptions so far: "), c);
		c.gridy++;
		panel.add(new JLabel("Time so far: "), c);
		c.gridy++;
		panel.add(new JLabel("Timer: "), c);
		
		// lock
		c.insets = new Insets(3, 3, 3, 3);
		c.gridx = 4;
		c.gridy = 2;
		panel.add(timerOn, c);
		
   		return panel;
	}
	
//	@Override
	protected void addAsObserver() {
		addAsObserver(Memory.textBaseAddress, Memory.textLimitAddress);
	}
	
//  @Override
	protected void processMIPSUpdate(Observable memory, AccessNotice notice) {
		if (notice.getAccessType() != AccessNotice.READ) return;
		MemoryAccessNotice m = (MemoryAccessNotice) notice;
		int a = m.getAddress();
		if (a == lastAddress) return;
		lastAddress = a;
		++counter;
		if(timerOn.isSelected()) {
			++countInst;
			SystemIO.printString("-- Interrupção Ligada!\n");
			if (countInst > (int)timerConfig.getValue()) {
				SystemIO.printString("-- Hora de trocar!\n");
				++countInter; // incrementa interrupções
				countInst = 0; // zera o progressBar
				// chame a troca de processos!!!
			}
		}
		else
			SystemIO.printString("-- Interrupção desligada!\n");
		updateDisplay();
	}
	
//  @Override
	protected void initializePreGUI() {
		countInst = 0;
		countTimer = 10;
		countInter = 0;
		lastAddress = -1;
	}
	
//  @Override
	protected void reset() {
		countInst = 0;
		countTimer = 10;
		countInter = 0;
		lastAddress = -1;		
		updateDisplay();
	}
//  @Override
	protected void updateDisplay() {
		counterField.setText(String.valueOf(counter));
		counterInterField.setText(String.valueOf(countInter));
		progressbarInst.setValue(countInst);
		progressbarInst.setMaximum((int)timerConfig.getValue());
	}
}

