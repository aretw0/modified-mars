package mars.so.filemanager;

public class File implements Cloneable {

	private String name; // nome do arquivo
	private Inode inode;
	private String path;
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

	@Override
	public String toString() {
		return 1+"#"+name+"#"+FileSystem.getAllInodes().indexOf(inode);
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
