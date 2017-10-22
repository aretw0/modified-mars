package mars.mips.so.memorymanager;

import mars.tools.MemoryManagerObserver;
import mars.util.SystemIO;

public class PageReplacer {
	static int lastRemoved = 0;
	static int[] lruDuration; // precisa inicializar-lo na primeira execução com o número de molduras
	static int nFrames;
	static int nPages;
	
	public static void FIFO(String x, String desloc){
		int index = Integer.parseInt(x, 2);	
		MemoryManagerObserver.updateDisplay("Sem moldura",VirtualTable.getMemFisica()[lastRemoved%MemoryManager.moldSize]);
		VirtualTable.getTabelaVirtual().get(VirtualTable.getMemFisica()[lastRemoved%MemoryManager.moldSize]).setPA(false);
		VirtualTable.getTabelaVirtual().get(VirtualTable.getMemFisica()[lastRemoved%MemoryManager.moldSize]).setReference(false);
		VirtualTable.getMemFisica()[lastRemoved%MemoryManager.moldSize] = -1;
		VirtualTable.getMemFisica()[lastRemoved%MemoryManager.moldSize] = index;	
		VirtualTable.getTabelaVirtual().get(index).setPA(true);	
		//VirtualTable.getTabelaVirtual().get(index).setReference(true);
		MemoryManagerObserver.updateDisplay(String.valueOf(lastRemoved%MemoryManager.moldSize), index);
		lastRemoved++;
				
	}
	public static void SecondChance(String x, String desloc){
		for (int i = 0; i < MemoryManager.moldSize; i++){
			if (VirtualTable.getTabelaVirtual().get(VirtualTable.getMemFisica()[i]).getReference()){
				VirtualTable.getTabelaVirtual().get(VirtualTable.getMemFisica()[i]).setReference(false);				
			}else{
				int index = Integer.parseInt(x, 2);	
				MemoryManagerObserver.updateDisplay("Sem moldura",VirtualTable.getMemFisica()[i]);
				VirtualTable.getTabelaVirtual().get(VirtualTable.getMemFisica()[i]).setPA(false);
				VirtualTable.getTabelaVirtual().get(VirtualTable.getMemFisica()[i]).setReference(false);
				VirtualTable.getMemFisica()[i] = -1;
				VirtualTable.getMemFisica()[i] = index;	
				VirtualTable.getTabelaVirtual().get(index).setPA(true);	
				//VirtualTable.getTabelaVirtual().get(index).setReference(true);
				MemoryManagerObserver.updateDisplay(String.valueOf(i), index);
				break;
			}
			
		}
		FIFO(x,desloc);
		
	}
	public static void nru(String x, String desloc) {
		
		SystemIO.printString("----- Algoritmo NRU -----");
		int index = Integer.parseInt(x, 2);	
		int indexNRU = -1;
		int classeNRU = -1;
		for (VirtualTableEntry i : VirtualTable.getTabelaVirtual()) {
			
			// se tiver presente na memória fisica
			// alguém vai estar, se ele foi chamado é porque ta cheia a mem fisica né?
			if (i.getPA()) {
				
				// sempre pega o primeiro candidato, se houver um igual considera
				if ((i.getModified() == false) && (i.getReference() == false)) {
					// se for classe 00
					
					indexNRU = VirtualTable.getTabelaVirtual().indexOf(i);
					classeNRU = 0;
				} else if ((i.getModified() == true) && (i.getReference() == false)) {
					// se for classe 01
					if(classeNRU >= 1 || classeNRU == -1) {
						classeNRU = 1;
						indexNRU = VirtualTable.getTabelaVirtual().indexOf(i);
					}
				} else if ((i.getModified() == false) && (i.getReference() == true)) {
					// se for classe 10
					if (classeNRU >= 2 || classeNRU == -1) {
						classeNRU = 2;
						indexNRU = VirtualTable.getTabelaVirtual().indexOf(i);
					}
				} else if (classeNRU == 3 || classeNRU == -1) {
						classeNRU = 3;
						indexNRU = VirtualTable.getTabelaVirtual().indexOf(i);
				}
			}
		}
		// saindo desse for é para eu ter o index da tabela que irá ser substituido
		SystemIO.printString("-- NRU: Index "+ indexNRU + " Classe " + classeNRU + "\n");

		MemoryManagerObserver.updateDisplay("Sem moldura",indexNRU);
		
		VirtualTable.getTabelaVirtual().get(indexNRU).setPA(false);
		VirtualTable.getTabelaVirtual().get(indexNRU).setReference(false);
		
		VirtualTable.getTabelaVirtual().get(index).setPA(true);	
		// ja inicia na classe 2?
		VirtualTable.getTabelaVirtual().get(index).setReference(false);
		int i;
		for ( i = 0; i < VirtualTable.getMemFisica().length; i++) {
			if (VirtualTable.getMemFisica()[i] == indexNRU) {
				VirtualTable.getMemFisica()[i] = index;
				break;
			}
		}			
		MemoryManagerObserver.updateDisplay(String.valueOf(i), index);
			
	}
	
	// definido um numero de pages (n) e um numero de page frames (f)
	public static void lru(String x, String desloc) {
		int index = Integer.parseInt(x, 2);	
		SystemIO.printString("----- Algoritmo LRU -----");
		
		int i,j,max;
		boolean found = false;
		/* lruDuration já instanciada com o numero de page frames
		// duration=new int[f];
		for(i=0;i<nPages;i++)
		{
			if(i<nFrames)
			{
				for(j=0;j<nFrames;j++)
				{
					j=i;
					frames[j]=page[i];
					j=f;
					faults++;
				}
			}
			else if(i>=nFrames)
			{
				for(j=0;j>nFrames;j++)
					lruDuration[j]++;
				for(j=0;j<nFrames;j++)
				{
					if(page[i]==frames[j])
					{
						found=true;
						duration[j]=0;
					}
				}
				if(found==false)
				{
					max=0;
					for(j=0;j<nFrames;j++)
					{
						if(lruDuration[j]>lruDuration[max])
							max=j;
					}
					frames[max]=page[i];
					lruDuration[max]=0;
					faults++;
				}
			}
			found=false;
		}
		System.out.println(&quot;Number of Page Faults = &quot;+faults);
	 //System.out.println(&quot;Fault rate = &quot;+(faults*1.0/n));
	  * */
	}	
}
