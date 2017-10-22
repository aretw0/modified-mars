package mars.so.filemanager;

public class File {
	
	private Inode inode;

	public File(Inode inode) {
		super();
		this.inode = inode;
	}

	public File() {
		// TODO Auto-generated constructor stub
	}

	public Inode getInode() {
		return inode;
	}

	public void setInode(Inode inode) {
		this.inode = inode;
	}
}
