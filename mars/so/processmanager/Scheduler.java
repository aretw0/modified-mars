package mars.so.processmanager;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Scheduler {
		
	public Scheduler(){
		
	}	
	/**
	 * Efetua o roteamento entre o processo atual para um novo processo
	 */
	public static boolean roteamento(){
		if (ProcessTable.getExeProc() != null){			
			ProcessTable.getProcessList().add(ProcessTable.getExeProc());
		}
		if (ProcessTable.getProcessList().size() > 0){
			ProcessTable.getProcessList().get(0).setEstadoProc("executando");
			ProcessTable.setExeProc(ProcessTable.getProcessList().remove(0));
			return true;
		}
		return false;
	}
	
	public static boolean prioridadeFixa(){
		if (ProcessTable.getExeProc() != null){			
			ProcessTable.getProcessList().add(ProcessTable.getExeProc());
		}
		if (ProcessTable.getProcessList().size() > 0){
			Collections.sort(ProcessTable.getProcessList());
			ProcessTable.getProcessList().get(0).setEstadoProc("executando");
			ProcessTable.setExeProc(ProcessTable.getProcessList().remove(0));			
			return true;
		}		
		return false;
	}
	public static boolean prioridadeDinamica(){		
		if (ProcessTable.getExeProc() != null){		
			ProcessTable.getExeProc().setTimesExec(ProcessTable.getExeProc().getTimesExec()+1);
			if (ProcessTable.getExeProc().getTimesExec() > 
					ProcessTable.getExeProc().getPrioridadeMax()-ProcessTable.getExeProc().getPrioridade()){
				ProcessTable.getExeProc().setTimesExec(0);
				if (ProcessTable.getExeProc().getPrioridade()-1 >= ProcessTable.getExeProc().getPrioridadeMin())
					ProcessTable.getExeProc().setPrioridade(ProcessTable.getExeProc().getPrioridade()-1);
			}
			ProcessTable.getProcessList().add(ProcessTable.getExeProc());
		}
		if (ProcessTable.getProcessList().size() > 0){
			Collections.sort(ProcessTable.getProcessList());
			ProcessTable.getProcessList().get(0).setEstadoProc("executando");
			ProcessTable.setExeProc(ProcessTable.getProcessList().remove(0));
			for (ProcessControlBlock pcb : ProcessTable.getProcessList()){
				pcb.setTimesExec(pcb.getTimesExec()-1);
				if (pcb.getTimesExec() < pcb.getPrioridadeMax()-pcb.getPrioridade()){
					pcb.setTimesExec(0);
					if (pcb.getPrioridade()+1 <= pcb.getPrioridadeMax())
						pcb.setPrioridade(pcb.getPrioridade()+1);
				}
					
			}
			return true;
		}		
		return false;
	}
	public static boolean loteria(){
		if (ProcessTable.getExeProc() != null){			
			ProcessTable.getProcessList().add(ProcessTable.getExeProc());
		}
		if (ProcessTable.getProcessList().size() > 0){
			Random rd = new Random();
			int i = rd.nextInt(ProcessTable.getProcessList().size());
			ProcessTable.getProcessList().get(i).setEstadoProc("executando");
			ProcessTable.setExeProc(ProcessTable.getProcessList().remove(i));
			return true;
		}		
		return false;
	}

}
