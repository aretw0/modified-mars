package mars.so.filemanager;

import javax.swing.tree.DefaultMutableTreeNode;

public class File implements Cloneable {

	private String name; // nome do arquivo
	private Inode inode;
	private String path;
	private DefaultMutableTreeNode treeNode;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public DefaultMutableTreeNode getTreeNode() {
		return treeNode;
	}

	public void setTreeNode(DefaultMutableTreeNode treeNode) {
		this.treeNode = treeNode;
	}

	@Override
	public String toString() {
		int indexInode = FileSystem.getAllInodes().indexOf(inode);
		if (indexInode >= 0)
			return 1+"#"+name+"#"+indexInode;
		return 1+"#"+name+"#";
	}
	
	public int getAllocSize() {
		int size = 0;
		Inode in = inode;
		if (inode == null) {
			return 0;
		}else {	
			while (in != null) {
				for (int i : in.getBlocks()) {
					if (i != -1) {
						size++;
					}
				}
				in = in.getNextBlock();
			}
		}
			
		return size;
		
	}
	
	public File clone() {
		try {
			return (File) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
}
