package mars.so.filemanager;

import java.util.ArrayList;
import java.util.List;

public class Directory extends File{
	
	private int mySelf;
	private File myFather;
	private List<String> dirIndex;
	private List<File> files;

	public Directory(int mySelf, File myFather, Inode inode, List<File> files,
			int[] idNodes) {
		super();
		this.mySelf = mySelf;
		this.myFather = myFather;
		this.files = files;
	}

	public Directory(String name) {
		setName(name);
		files = new ArrayList<File>();
		dirIndex = new ArrayList<String>();
	}
	
	public Directory(String name, Directory father) {
		setName(name);
		this.myFather = father;
		files = new ArrayList<File>();
		dirIndex = new ArrayList<String>();
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public int getMySelf() {
		return mySelf;
	}

	public void setMySelf(int mySelf) {
		this.mySelf = mySelf;
	}

	public File getMyFather() {
		return myFather;
	}

	public void setMyFather(File myFather) {
		this.myFather = myFather;
	}

	public List<String> getDirIndex() {
		return dirIndex;
	}

	public void setDirIndex(List<String> dirIndex) {
		this.dirIndex = dirIndex;
	}

	@Override
	public String toString() {
		String str = 0+"#"+getName();
		for (File file : getFiles()) {
			str += "#"+FileSystem.getAllFiles().indexOf(file);
		}
		return str;
	}
	
	public void loadDir() {
		for (String st : dirIndex) {
			files.add(FileSystem.getAllFiles().get(Integer.valueOf(st)));
		}
	}
	
	public boolean add(File file) {
		getFiles().add(file);
		return FileSystem.getAllFiles().add(file);
	}
	
	public Directory clone() {
		return (Directory) super.clone();
	}

}
