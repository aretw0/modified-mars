package mars.so.filemanager;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class FileSystem {
	
	private static SuperBlock suBlock;
	private static List<File> allFiles;
	private static List<Inode> allInodes;
	public static String homeDir = "fileSystem/";

	public FileSystem() {
		
	}

	public static SuperBlock getSuBlock() {
		return suBlock;
	}

	public static void setSuBlock(SuperBlock suBlock) {
		FileSystem.suBlock = suBlock;
	}

	public static List<File> getAllFiles() {
		return allFiles;
	}

	public static void setAllFiles(List<File> allFiles) {
		FileSystem.allFiles = allFiles;
	}

	public static String getHomeDir() {
		return homeDir;
	}

	public static void setHomeDir(String homeDir) {
		FileSystem.homeDir = homeDir;
	}

	public static List<Inode> getAllInodes() {
		return allInodes;
	}

	public static void setAllInodes(List<Inode> allInodes) {
		FileSystem.allInodes = allInodes;
	}
	
	public static String openFile(String filename) {
		String [] files = filename.split("/");
		filename = homeDir;
		for (int i = 0; i < files.length; i++) {
			if (i == 0) 
				filename += files[i];
			else
				filename += "-"+files[i];
		}
			
		
		return filename;
		
	}
	
	public static void saveFiles() {
		FileWriter arq;
		try {
			arq = new FileWriter("arquivos.txt");
			PrintWriter save = new PrintWriter(arq);
			for (File file : FileSystem.getAllFiles())
				if (file.getClass().isInstance(new File()))
					save.print(file.toString());
		}
	}
}
