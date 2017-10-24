package mars.so.filemanager;

import java.util.ArrayList;
import java.util.List;

public class Directory extends File{
	
	private int mySelf;
	private File myFather;
	private static List<String> dirIndex;
	private static List<File> files;

	public Directory(int mySelf, File myFather, Inode inode, List<File> files,
			int[] idNodes) {
		super();
		this.mySelf = mySelf;
		this.myFather = myFather;
		this.files = files;
	}

	public Directory() {
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
		for (File file : files) {
			str += "#"+FileSystem.getAllFiles().indexOf(file);
		}
		return str;
	}
	
	public static void loadDir() {
		for (String st : dirIndex) {
			files.add(FileSystem.getAllFiles().get(Integer.valueOf(st)));
		}
	}

}
