package mars.so.filemanager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import mars.so.processmanager.ProcessTable;

public class FileSystem {
	
	private static SuperBlock suBlock;
	private static List<File> allFiles = new ArrayList<File>();
	private static List<Inode> allInodes = new ArrayList<Inode>();
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
	
	public static String openFile(String filename, int mode, int descritor) {	
		filename = ProcessTable.getExeProc().getPidProc()+"/"+filename;
		String [] files = filename.split("/");
		File dir = FileSystem.getAllFiles().get(0);
		System.out.println("File : " + filename);
		for (String st : files) {
			boolean exist = false;
			System.out.println(" Dir : " + dir.toString());
			if (dir instanceof  Directory) {
				System.out.println("dir � instance of directory");
				for (File file : ((Directory)dir).getFiles()) {
					if (file.getName().equals(st)) {
						//diret�rio existe
						dir = file;
						exist = true;
						break;
					}
				}
				if (!exist) {
					//diretorio n�o existe, criar um novo
					Directory file = new Directory();
					file.setName(st);
					file.setMyFather(dir);
					FileSystem.getAllFiles().add(file);
					dir = file;
				}
			}else {
				//dir n�o � um diretorio
				if (st.equals(dir.getName())) {
					
				}
			}
		}
		
		
//		if (mode == 1 || mode == 9) {
//			filename = ProcessTable.getExeProc().getPidProc()+"/"+filename;
//			String [] files = filename.split("/");
//			File dir = FileSystem.getAllFiles().get(0);
//			for (String st : files) {
//				if (dir instanceof  Directory) {
//					for (File file : ((Directory)dir).getFiles()) {
//						if (file.getName().equals(st)) {
//							dir = file;
//							break;
//						}						
//					}
//					File file = new Directory();
//					((Directory)file).setName(st);
//					((Directory)file).setMyFather(dir);
//					((Directory)dir).getFiles().add(file);
//					dir = file;
//					FileSystem.getAllFiles().add(file);					
//					
//				}else {
//					boolean exist = false;
//					for (File file : ((Directory)dir).getFiles()) {
//						if (file.getName().equals(st)) {
//							dir = file;
//							exist = true;
//							break;
//						}						
//					}
//					if (exist) {
//						
//					}else {
//						File file = new File();
//						((File)file).setName(st);
//						((File)file).setDescritor(descritor);
//						((Directory)dir).getFiles().add(file);
//						FileSystem.getAllFiles().add(file);						
//					}
//				}
//					
//					
//			}
//			return null;			
//			
//		}
		return null;
	}
	
	public static void saveFiles() {
		FileWriter arq;
		try {
			arq = new FileWriter("Files.txt");
			PrintWriter save = new PrintWriter(arq);
			for (File file : FileSystem.getAllFiles()) {
				if (!file.getClass().isInstance(new Directory()))
					save.println(file.toString());
				else {
					save.println(((Directory)file).toString());
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void saveInode() {
		FileWriter arq;
		try {
			arq = new FileWriter("Inodes.txt");
			PrintWriter save = new PrintWriter(arq);
			for (Inode inode : FileSystem.getAllInodes()) {
				save.println(inode.toString());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadInode() {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("Inodes.txt"));
		    String line = br.readLine();
		    while (line != null) {
		    	String [] parans = line.split("#");
		    	Inode inode = new Inode();
		    	inode.setIdOwner(Integer.valueOf(parans[0]));
		    	inode.setByteSize(Integer.valueOf(parans[1]));
		    	inode.setBlockSize(Integer.valueOf(parans[2]));
		    	inode.setDateBirth(parans[3]);
		    	inode.setDateChange(parans[4]);		    	
		    	FileSystem.getAllInodes().add(inode);
		    	int [] block = new int[8];
		    	int j = 0;
		    	for (int k = 0; k < Math.ceil((parans.length-5)/8); k++) {
			    	for (int i = 5; i < parans.length; i++) {
			    		block[j] = Integer.valueOf(parans[i]);
			    		if (j>7)
			    			break;
			    	}
			    	Inode aux = new Inode();
			    	inode.setBlocks(block);			    	
			    	if (j > 7) {	
			    		inode.setNextBlock(aux);
				    	inode = aux;				    	
			    	}
			    	j = 0;
		    	}		    	
		        line = br.readLine();
		    }
		    br.close();
		} catch (RuntimeException | IOException e){
			e.printStackTrace();
		}
	}
	
	public static void loadFiles() {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("Files.txt"));
		    String line = br.readLine();
		    while (line != null) {
		    	String [] parans = line.split("#");
		    	File file = new File();
		    	if (parans[0] == "1") {
		    		 file = new File();
		    		 file.setName(parans[1]);
		    		 file.setInode(FileSystem.getAllInodes().get(Integer.valueOf(parans[2])));
		    		 FileSystem.getAllFiles().add(file);
		    	}
		    	else {
		    		file = new Directory();
		    		file.setName(parans[1]);
		    		for (int i = 2; i < parans.length;i++)
		    			((Directory)file).getDirIndex().add(parans[i]);
		    		FileSystem.getAllFiles().add(file);
		    	}
		    	line = br.readLine();
		    	
		    }
		    for (File file : FileSystem.getAllFiles()) {
		    	if (file.getClass().isInstance(new Directory())) {
		    		((Directory)file).loadDir();
		    	}
		    }
		    br.close();
		} catch (RuntimeException | IOException e){
			e.printStackTrace();
		}
	}
}
