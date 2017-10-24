package mars.so.filemanager;

import java.util.ArrayList;
import java.util.List;

public class Directory extends File{
	
	private int mySelf;
	private int myFather;
	private List<File> files;

	public Directory(int mySelf, int myFather, Inode inode, List<File> files,
			int[] idNodes) {
		super();
		this.mySelf = mySelf;
		this.myFather = myFather;
		this.files = files;
	}

	public Directory() {
		files = new ArrayList<File>();
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

	public int getMyFather() {
		return myFather;
	}

	public void setMyFather(int myFather) {
		this.myFather = myFather;
	}


}
