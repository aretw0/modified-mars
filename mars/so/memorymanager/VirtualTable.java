package mars.so.memorymanager;

import java.util.ArrayList;
import java.util.List;

import mars.mips.so.processmanager.ProcessTable;
import mars.tools.MemoryManagerObserver;
import mars.util.SystemIO;

public class VirtualTable {
	
	private static List<VirtualTableEntry> tabelaVirtual = new ArrayList<VirtualTableEntry>();
	private static int[] memFisica = new int[MemoryManager.moldSize];
	
	public static void checkTable(String x, String desloc){
		int endInicio = ProcessTable.getFirstAdress(); 
		String endAtual;
		if (tabelaVirtual.size() < MemoryManager.tableSize){
			//primeira execu��o			
			for (int i = 0; i < MemoryManager.moldSize; i++)
				memFisica[i] = -1;
			while (tabelaVirtual.size() < MemoryManager.tableSize){
				VirtualTableEntry vte = new VirtualTableEntry();
				vte.setPA(false);
				VirtualTable.getTabelaVirtual().add(vte);
				endAtual = Integer.toBinaryString(endInicio);				
				String[] temp = {endAtual.substring(endAtual.length() - MemoryManager.deslocSize - MemoryManager.indexSize
						, endAtual.length() - MemoryManager.deslocSize), "Sem moldura"};
				endInicio += 4*MemoryManager.pageSize;
				MemoryManagerObserver.updateDisplay(temp);
			}
		}
		int index = Integer.parseInt(x, 2);			
		if (tabelaVirtual.get(index).getPA()){	
			//p�gina est� na mem�ria fisica, alterar o referenciado
			tabelaVirtual.get(index).setReference(true);
			MemoryManagerObserver.hitsCounts.setText(String.valueOf((Integer.parseInt(MemoryManagerObserver.hitsCounts.getText())+1)));
			SystemIO.printString("HIT \n");
		}
		else{
			// n�o est� na mem�ria fisica, verificar se tem espa�o
			int i;
			for (i = 0; i < MemoryManager.moldSize; i++){
				if (memFisica[i] == -1)
					break;
			}
			if (i < MemoryManager.moldSize){
				//tem espa�o na memFicisa
				tabelaVirtual.get(index).setPA(true);	
				//tabelaVirtual.get(index).setReference(true);
				memFisica[i] = index;
				tabelaVirtual.get(index).setNumFrame(i);
				MemoryManagerObserver.updateDisplay(String.valueOf(i), index);
				SystemIO.printString("MISS: alocado na memoria fisica \n");
				MemoryManagerObserver.missCounts.setText(String.valueOf((Integer.parseInt(MemoryManagerObserver.missCounts.getText())+1)));
			}else{
				//n�o tem espa�o na memFisica
				SystemIO.printString("MISS: não tem espalço na memFisica, chamando algoritmo de substitui��o de p�gina \n");
				MemoryManagerObserver.missCounts.setText(String.valueOf((Integer.parseInt(MemoryManagerObserver.missCounts.getText())+1)));
				MemoryManagerObserver.SubsTPagina.setText(String.valueOf((Integer.parseInt(MemoryManagerObserver.SubsTPagina.getText())+1)));
				switch (MemoryManager.metodoPaginacao) {
				case "FIFO":
					PageReplacer.FIFO(x, desloc);
					break;
				case "Segunda Chance":
					PageReplacer.SecondChance(x, desloc);
					break;
				case "NRU":
					PageReplacer.nru(x, desloc);
					break;
				case "LRU":
					PageReplacer.lru(x, desloc);
					break;
				}
				
			}
		}
	}

	

	public static List<VirtualTableEntry> getTabelaVirtual() {
		return tabelaVirtual;
	}

	public static void setTabelaVirtual(List<VirtualTableEntry> tabelaVirtual) {
		VirtualTable.tabelaVirtual = tabelaVirtual;
	}

	public static int[] getMemFisica() {
		return memFisica;
	}

	public static void setMemFisica(int[] memFisica) {
		VirtualTable.memFisica = memFisica;
	}	
}
