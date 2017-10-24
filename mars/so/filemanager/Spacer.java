package mars.so.filemanager;

import java.util.ArrayList;
import java.util.List;

import mars.tools.FileManagerObserver;

public class Spacer { // nosso gerenciador de espaço livre
	
	private static boolean init; // inicializado
	private int [] blocks;

	public Spacer() { 
		setBlocks(new int[(int) FileManagerObserver.blockAmount.getValue()]);
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
		this.blocks = blocks;
	}
	
	public static boolean canAlloc(String filename, int size) {
		return init;
		
	}
}
