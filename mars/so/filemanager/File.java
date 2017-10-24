package mars.so.filemanager;

public class File {

	private String name; // nome do arquivo
	private Inode inode;
	private int descritor;

	public File(Inode inode) {
		super();
		this.inode = inode;
	}

	public File() {

	}

	public Inode getInode() {
		return inode;
	}

	public void setInode(Inode inode) {
		this.inode = inode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getDescritor() {
		return descritor;
	}

	public void setDescritor(int descritor) {
		this.descritor = descritor;
	}

	@Override
	public String toString() {
		return 1+"#"+name+"#"+FileSystem.getAllInodes().indexOf(inode);
	}
}
