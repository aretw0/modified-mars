package mars.so.filemanager;

import java.util.List;

public class Directory {
	
	private int mySelf;
	private int myFather;
	private Inode inode;
	private List<String> files;
	private int[] idNodes;

	public Directory(int mySelf, int myFather, Inode inode, List<String> files,
			int[] idNodes) {
		super();
		this.mySelf = mySelf;
		this.myFather = myFather;
		this.inode = inode;
		this.files = files;
		this.idNodes = idNodes;
	}

	public Directory() {
		// TODO Auto-generated constructor stub
	}

	public Inode getInode() {
		return inode;
	}

	public void setInode(Inode inode) {
		this.inode = inode;
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	public int[] getIdNodes() {
		return idNodes;
	}

	public void setIdNodes(int[] idNodes) {
		this.idNodes = idNodes;
	}
}
