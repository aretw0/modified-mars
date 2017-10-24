package mars.so.filemanager;

public class SuperBlock {

	private static int sizeBlock; // tamanho dos blocos de alocação
	private static int blocksAmount; // quantidade de blocos do disco
	
	
	public SuperBlock() {
		
	}


	public static int getSizeBlock() {
		return sizeBlock;
	}


	public static void setSizeBlock(int sizeBlock) {
		SuperBlock.sizeBlock = sizeBlock;
	}


	public static int getBlocksAmount() {
		return blocksAmount;
	}


	public static void setBlocksAmount(int blocksAmount) {
		SuperBlock.blocksAmount = blocksAmount;
	}
}
