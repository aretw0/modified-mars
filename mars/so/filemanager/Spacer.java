package mars.so.filemanager;

import mars.tools.FileManagerObserver;

public class Spacer { // nosso gerenciador de espaÃ§o livre
	
	private static boolean init; // inicializado
	private static int [] blocks = new int[FileManagerObserver.getIntComboBoxSelection(FileManagerObserver.blockAmount)];

	public Spacer() {
		
	}

	public static boolean isInit() {
		return init;
	}

	public static void setInit(boolean init) {
		Spacer.init = init;
	}

	public int [] getBlocks() {
		return blocks;
	}

	public void setBlocks(int [] blocks) {
		Spacer.blocks = blocks;
	}
	
	public static boolean alloc(int lenght, File file) {
		// criar o inode e alocar no file
		double allocBloks = Math.ceil(file.getAllocSize()/8);
		Inode aux = file.getInode();
		int k = 0;
		int j = 0;
		for (j = 0; j < blocks.length; j++) {
			if (blocks[j] == 0) 
				break;
		}
		for (int i = 0; i < Math.ceil(lenght/SuperBlock.getSizeBlock()); i++, j++) {				
			aux.getBlocks()[k] = j;
			k++;
			blocks[j] = 1;
			if (k>7) {
				k = 0;
				aux.setNextBlock(new Inode());
				FileSystem.getAllInodes().add(aux.getNextBlock());
				aux = aux.getNextBlock();								
			}
		}
		FileSystem.saveInode();
		return true;
		
	}	
	
	public static void unalloc(int lenght) {
		
	}

	public static boolean alloc(int lenght) {
		int j;
		for (j = 0; j < blocks.length; j++) {
			if (blocks[j] == 0) 
				break;
		}
		for (int i = 0; i < Math.ceil(lenght/SuperBlock.getSizeBlock()); i++, j++) {			
			blocks[j] = 1;
			if (j > blocks.length) {
				FileManagerObserver.m_taLog.setText(FileManagerObserver.m_taLog.getText() + " Espaço insuficiente para armazenar");
				return false;
			}
		}
		return true;
	}
}
