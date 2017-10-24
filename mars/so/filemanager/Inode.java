package mars.so.filemanager;

import java.util.Iterator;
import java.util.List;

public class Inode {
	
	private int idOwner; // id do dono
	private List<FileProtection> rules;
	private int byteSize; 
	private int blockSize;
	private String dateBirth; // data de criação
	private String dateChange; // data de modificação
	private Boolean hide; // flag para ocultar o arquivo

	private int [] blocks;
	private Inode nextBlock;

	/* Os 7 primeiros endereços correspondem aos endereços do disco
	indicados pelo gerenciador de espaço livre
	 O último endereço corresponde a um ponteiro para uma nova estrutura
	com 8 novos endereços, similar a estrutura do i-node, exceto que não
	há atributos */
	
	public Inode(String name, int idOwner, List<FileProtection> rules,
			int byteSize, int blockSize, String dateBirth, String dateChange,
			Boolean hide, int [] blocks) {
		super();
		this.idOwner = idOwner;
		this.rules = rules;
		this.byteSize = byteSize;
		this.blockSize = blockSize;
		this.dateBirth = dateBirth;
		this.dateChange = dateChange;
		this.hide = hide;
		this.blocks = blocks;
	}
	
	public Inode() {
		this.blocks = new int[8];
	}
		
	public int getIdOwner() {
		return idOwner;
	}

	public void setIdOwner(int idOwner) {
		this.idOwner = idOwner;
	}

	public List<FileProtection> getRules() {
		return rules;
	}

	public void setRules(List<FileProtection> rules) {
		this.rules = rules;
	}
	
	public void addRole(int id, boolean r, boolean w, boolean x) {
		FileProtection newRole = new FileProtection(id, r, w, x);
		this.rules.add(newRole);
	}
	public void addRole(FileProtection newRole) {
		this.rules.add(newRole);
	}
	
	public FileProtection getRights(int id) { // verifica se tem direitos se tiver os retorna
		for (Iterator role = this.rules.iterator(); role.hasNext();) {
			FileProtection type = (FileProtection) role.next();
			if (id == type.getIdRole()) 
				return type;
		}
		return null;
	}

	public int getByteSize() {
		return byteSize;
	}

	public void setByteSize(int byteSize) {
		this.byteSize = byteSize;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public String getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(String dateBirth) {
		this.dateBirth = dateBirth;
	}

	public String getDateChange() {
		return dateChange;
	}

	public void setDateChange(String dateChange) {
		this.dateChange = dateChange;
	}

	public Boolean getHide() {
		return hide;
	}

	public void setHide(Boolean hide) {
		this.hide = hide;
	}
	
	public int[] getBlocks() {
		return blocks;
	}

	public void setBlocks(int[] blocks) {
		this.blocks = blocks;
	}

	public Inode getNextBlock() {
		return nextBlock;
	}

	public void setNextBlock(Inode nextBlock) {
		this.nextBlock = nextBlock;
	}
	
	@Override
	public String toString() {
		String rt = idOwner+"#"+byteSize+"#"+blockSize+"#"+dateBirth+"#"+dateChange;
		Inode blocoAtual = this;
		while (blocoAtual !=  null) {
			for (int i = 0; i < blocoAtual.getBlocks().length; i++)
				rt += "#"+blocoAtual.getBlocks()[i];
			blocoAtual = blocoAtual.getNextBlock();
		}
		return rt;		
	}
}
