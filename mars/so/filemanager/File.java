package mars.so.filemanager;

public class File {

	private String name; // nome do arquivo
	private Inode inode;

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
	
	@Override
	public String toString() {
		return name+"#"+FileSystem.getAllInodes().indexOf(inode);
	}
}
