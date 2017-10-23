package mars.so.processmanager;

import java.util.ArrayList;
import java.util.List;

import mars.mips.hardware.RegisterFile;
import mars.util.SystemIO;

public class ProcessTable {	
	
	/**
	 * Processo que est� executando no momento
	 */
	private static ProcessControlBlock exeProc;	
	/**
	 * Lista de processos existente no escalonador
	 */
	private static List<ProcessControlBlock> processList = new ArrayList<ProcessControlBlock>();	
	
	public ProcessTable(ProcessControlBlock exeProc) {		
		ProcessTable.exeProc = exeProc;		
	}	
	
	/**
	 * 
	 * caso queira adicionar outros m�todos de escalonamento � s� inserir no switch case e alterar o parametro do processChange
	 * 
	 * @param escalonador
	 */
	public static void processChange(String escalonador){
		if (exeProc != null){	
			exeProc.setEstadoProc("pronto");
			exeProc.setEndInicio(RegisterFile.getProgramCounter());
			exeProc.getContexto().clear();
			for (int i = 0; i < RegisterFile.getRegisters().length;i++){
				exeProc.getContexto().add(RegisterFile.getValue(i));
			}
			exeProc.getContexto().add(RegisterFile.getValue(33));
			exeProc.getContexto().add(RegisterFile.getValue(34));			
		}
		switch (escalonador){
		case "roteamento":			
			if (Scheduler.roteamento()){
				RegisterFile.setProgramCounter(exeProc.getEndInicio());
				for (int i = 0; i < exeProc.getContexto().size();i++){
					RegisterFile.updateRegister(i, exeProc.getContexto().get(i));
				}
			}			
			break;
		case "prioridadeFixa":
			if (Scheduler.prioridadeFixa()){
				RegisterFile.setProgramCounter(exeProc.getEndInicio());
				for (int i = 0; i < exeProc.getContexto().size();i++){
					RegisterFile.updateRegister(i, exeProc.getContexto().get(i));
				}
			}	
			break;
		case "prioridadeDin�mica":
			if (Scheduler.prioridadeDinamica()){
				RegisterFile.setProgramCounter(exeProc.getEndInicio());
				for (int i = 0; i < exeProc.getContexto().size();i++){
					RegisterFile.updateRegister(i, exeProc.getContexto().get(i));
				}
			}	
			break;
		case "loteria":
			if (Scheduler.loteria()){
				RegisterFile.setProgramCounter(exeProc.getEndInicio());
				for (int i = 0; i < exeProc.getContexto().size();i++){
					RegisterFile.updateRegister(i, exeProc.getContexto().get(i));
				}
			}	
			break;
		default:
			break;
		}	
		
	}
	/**
	 * 
	 * caso queira adicionar outros m�todos de escalonamento � s� inserir no switch case e alterar o parametro do processChange
	 * 
	 * @param escalonador
	 */
	public static void processTerminate(String escalonador){
		SystemIO.printString("Processo finalizado");
		exeProc = null;
		processChange(escalonador);
	}
	
	/**
	 * adiciona novo PCB a lista
	 * @param pcb
	 */
	public static void addNewPCB(ProcessControlBlock pcb){
		processList.add(pcb);
	}
	
	public static ProcessControlBlock getExeProc() {
		return exeProc;
	}
	public static void setExeProc(ProcessControlBlock exeProc) {
		ProcessTable.exeProc = exeProc;
	}

	public static List<ProcessControlBlock> getProcessList() {
		return processList;
	}

	public static void setProcessList(List<ProcessControlBlock> processList) {
		ProcessTable.processList = processList;
	}
	
	public static int getFirstAdress(){
		int menor = 2147483647;
		for (ProcessControlBlock pcb : processList){
			if (pcb.getEndInicio() < menor)
				menor = pcb.getEndInicio();
		}
		return menor;
		
	}
}
