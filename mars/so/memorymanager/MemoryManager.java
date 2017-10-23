package mars.so.memorymanager;

import javax.swing.JTextField;

import mars.mips.so.processmanager.ProcessTable;
import mars.tools.MemoryManagerObserver;
import mars.util.SystemIO;

public class MemoryManager {
	
	/**
	 * Tamanho maximo da tabela
	 */
	public static int tableSize = 32;
	/**
	 * Tamanho maximo da pagina
	 */
	public static int pageSize = 2;	
	/**
	 * quantidade de maxima de blocos
	 */
	public static int moldSize = 4;	
	/**
	 * quantidade de bits de deslocamento para deslocar 8 bytes usa 3 bits
	 */
	public static int deslocSize = 0;	
	/**
	 * quantidade de bits de indexacao para indexar 16 bytes usa 4 bti
	 */
	public static int indexSize = 0;
	/**	 
	 * 
	 */
	public static VirtualTable table;
	/**
	 * 	 
	 */
	public static boolean firstRun = true;
	/**
	 * 	 
	 */
	public static String metodoPaginacao;
	
	//verifica quando a instrucao muda
	public static void VerificarMemoria(int pc){
		//pega o valor do registrador pc
		int pcAtual = pc;
		//verifica se a instru��o pertence ao programa que est� em execu��o
		if (ProcessTable.getExeProc() != null)
			{
			if(pcAtual < ProcessTable.getExeProc().getStartAdress() || pcAtual > ProcessTable.getExeProc().getEndAdress() ){
				SystemIO.printString("Instrucao nao Acessivel");
				System.exit(0);
			}else{
				//transforma o endereco de pc em uma string de bits
				String enderecoVirtual = Integer.toBinaryString(pcAtual);
				
				
				if (firstRun) {					
					tableSize = (int) (MemoryManagerObserver.qtdPaginaMemVirtual.getSelectedItem());
					MemoryManagerObserver.qtdPaginaMemVirtual.setEnabled(false);
			    	pageSize = (int) (MemoryManagerObserver.tamPagina.getSelectedItem());
			    	MemoryManagerObserver.tamPagina.setEnabled(false);
			    	moldSize = (int) (MemoryManagerObserver.qtdMolduraMemFisica.getSelectedItem());
			    	MemoryManagerObserver.qtdMolduraMemFisica.setEnabled(false);
			    	metodoPaginacao = (String) MemoryManagerObserver.metodoPaginacao.getSelectedItem();
			    	MemoryManagerObserver.metodoPaginacao.setEnabled(false);
					MemoryManager.indexSize = ((int) (Math.log(MemoryManager.tableSize)/Math.log(2)));
					MemoryManager.deslocSize = ((int) (Math.log(MemoryManager.pageSize)/Math.log(2))+2);	
					PageReplacer.lruDuration = new int[moldSize];
					// sei que posso pegar esses valores no pr�prio pagereplacer, mas adicionei assim pra facilitar
					PageReplacer.nPages = tableSize; 
					PageReplacer.nFrames = moldSize;				
					firstRun = false;
				}
				
				String desloc = enderecoVirtual.substring(enderecoVirtual.length()-deslocSize,enderecoVirtual.length());
				
				int inicio = enderecoVirtual.length() - deslocSize - indexSize;
				int ultimo = enderecoVirtual.length() - deslocSize;
				//pega a parte dos bits do indce
				String x = enderecoVirtual.substring(inicio, ultimo);
				//index � o numero da pagina que tem a instru��o 
				
				VirtualTable.checkTable(x, desloc);				
				
			}
		}
	}	
	
}
