package mars.so.processmanager;

import java.util.List;

import mars.mips.hardware.Register;

public class ProcessControlBlock implements Comparable<ProcessControlBlock>{	
	
	/**
	 * Lista de registradores
	 */
	private List<Integer> contexto;
	/**
	 * Endere�o da instru��o a ser executada do processo
	 */
	private int endInicio;
	/**
	 * Endere�o da primeira instru��o do processo
	 */
	private int startAdress;
	/**
	 * Endere�o da ultima instru��o do processo
	 */
	private int endAdress;
	/**
	 * Processo ID(� o endere�o do label)
	 */
	private int pidProc;
	/**
	 * Estado do processo (Executando, Pronto, *Bloqueado*)
	 */
	private String estadoProc;	
	/**
	 * 	Prioridade fixa do processo
	 */
	private int prioridade;
	/**
	 * 	Prioridade maxima do processo
	 */
	private int prioridadeMax;
	/**
	 * 	Prioridade minima do processo
	 */
	private int prioridadeMin;
	/**
	 * 	Quantidade de vezes que foi executado sem ser escalonado
	 */
	private int timesExec;
	
	/* TRABALHO 2.0
	 * 
	 * Adicione atributos na sua classe PCB (Process Control Block) para manter
		informa��es a respeito do gerenciamento de mem�ria do processo tais como:
		o Registrador de limite superior da mem�ria do processo
		o Registrador de limite inferior da mem�ria do processo
		o Entre outros que for necess�rio para gerenciamento de mem�ria
	 * 
	 * 
	 * */
	
	private String regLimSup;
	private String regLimInf;
	
	public ProcessControlBlock(int endInicio, int pidProc,
			String estadoProc, List<Integer> contexto) {
		super();
		this.endInicio = endInicio;
		this.pidProc = pidProc;
		this.estadoProc = estadoProc;
		this.setContexto(contexto);		
	}
	
	public ProcessControlBlock(int endInicio, int pidProc,
			String estadoProc, int prioridade, List<Integer> contexto) {
		super();
		this.endInicio = endInicio;
		this.pidProc = pidProc;
		this.estadoProc = estadoProc;
		this.prioridade = prioridade;
		this.setContexto(contexto);
	}
	
	public ProcessControlBlock(int endInicio, int endAdress,int pidProc,
			String estadoProc, int prioridade, int prioridadeMax, int prioridadeMin, List<Integer> contexto) {
		super();
		this.endInicio = endInicio;
		this.startAdress = endInicio;
		this.pidProc = pidProc;
		this.estadoProc = estadoProc;
		this.prioridade = prioridade;
		this.prioridadeMax = prioridadeMax;
		this.prioridadeMin = prioridadeMin;
		this.setContexto(contexto);
		this.timesExec = 0;
		this.endAdress = endAdress;
	}
	
	@Override
	public String toString(){
		return endInicio +" "+ pidProc +" "+ estadoProc + contexto.get(17);
	}
	public List<Integer> getContexto() {
		return contexto;
	}
	public void setContexto(List<Integer> contexto) {
		this.contexto = contexto;
	}
	public int getEndInicio() {
		return endInicio;
	}
	public void setEndInicio(int endInicio) {
		this.endInicio = endInicio;
	}
	public int getPidProc() {
		return pidProc;
	}
	public void setPidProc(int pidProc) {
		this.pidProc = pidProc;
	}
	public String getEstadoProc() {
		return estadoProc;
	}
	public void setEstadoProc(String estadoProc) {
		this.estadoProc = estadoProc;
	}

	public int getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

	public int getPrioridadeMax() {
		return prioridadeMax;
	}

	public void setPrioridadeMax(int prioridadeMax) {
		this.prioridadeMax = prioridadeMax;
	}

	public int getPrioridadeMin() {
		return prioridadeMin;
	}

	public void setPrioridadeMin(int prioridadeMin) {
		this.prioridadeMin = prioridadeMin;
	}

	public int getTimesExec() {
		return timesExec;
	}

	public void setTimesExec(int timesExec) {
		this.timesExec = timesExec;
	}

	public String getRegLimSup() {
		return regLimSup;
	}

	public void setRegLimSup(String regLimSup) {
		this.regLimSup = regLimSup;
	}

	public String getRegLimInf() {
		return regLimInf;
	}

	public void setRegLimInf(String regLimInf) {
		this.regLimInf = regLimInf;
	}

	public int getStartAdress() {
		return startAdress;
	}

	public void setStartAdress(int startAdress) {
		this.startAdress = startAdress;
	}

	public int getEndAdress() {
		return endAdress;
	}

	public void setEndAdress(int endAdress) {
		this.endAdress = endAdress;
	}

	public int compareTo(ProcessControlBlock arg0) {
		if (this.prioridade > arg0.getPrioridade()) {
	          return -1;
	     }
	     if (this.prioridade < arg0.getPrioridade()) {
	          return 1;
	     }
		return 0;
	}
}
