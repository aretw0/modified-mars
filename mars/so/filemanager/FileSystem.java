package mars.so.filemanager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import mars.so.processmanager.ProcessTable;
import mars.tools.FileManagerObserver;

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
	
	public static File openFile(String filename, int mode) {
		FileManagerObserver.m_taLog.setText(FileManagerObserver.m_taLog.getText() + "Tentando abrir arquivo: " + filename+"\n");
		filename = ProcessTable.getExeProc().getPidProc()+"/"+filename;		
		String [] files = filename.split("/");
		
		int indexOrigi = 0;
		Directory dir = ((Directory)FileSystem.getAllFiles().get(indexOrigi)).clone();
		for (String path : files) {
			if (!path.contains(".")) { 
				// é diretorio
				Directory aux = getDir(path, (Directory)FileSystem.getAllFiles().get(indexOrigi));
				if (aux != null) {
					//diretorio existe
					FileManagerObserver.m_taLog.setText(FileManagerObserver.m_taLog.getText() + "Diretório " + path + "/ existe, abrindo.. \n");
					indexOrigi = FileSystem.getAllFiles().indexOf(aux);
					dir = aux;
				}else {
					// diretorio não existe
					FileManagerObserver.m_taLog.setText(FileManagerObserver.m_taLog.getText() + "Diretório " + path + "/ não existe, criando diretório..\n");
					if (mode == 1 || mode == 9) {
						// se modo de escrita: criar novo dir												
						aux = new Directory(path, dir);
						((Directory)FileSystem.getAllFiles().get(indexOrigi)).add(aux);
						FileManagerObserver.m_taLog.setText(FileManagerObserver.m_taLog.getText() + aux.getName() + "/ criado\n");
						indexOrigi = FileSystem.getAllFiles().indexOf(aux);
						dir = aux;
					}else {
						// se mode de escrita o dir deve exixtir
						return null;
					}
				}
			}else {
				// é arquivo
				File aux = getFile(path, (Directory)FileSystem.getAllFiles().get(indexOrigi));
				if (aux != null) {
					FileManagerObserver.m_taLog.setText(FileManagerObserver.m_taLog.getText() + "Arquivo " + path + " existe, abrindo...\n");
					//arquivo existe, retorna o arqvuivo
					aux.setPath(homeDir+filename.replace("/", "-"));
					return aux;
				}else{
					// arquivo não existe
					FileManagerObserver.m_taLog.setText(FileManagerObserver.m_taLog.getText() + "Arquivo " +path + " não existe, criando...\n");
					aux = new File();
					if (mode == 1 || mode == 9) {
						// se mode de leitura, cria o arquivo e seta o path
						aux.setName(path);
						aux.setPath(homeDir+filename.replace("/", "-"));
						((Directory)FileSystem.getAllFiles().get(indexOrigi)).add(aux);
						FileManagerObserver.m_taLog.setText(FileManagerObserver.m_taLog.getText() + aux.getName() + " criado com caminho = " +
						" " +aux.getPath().replaceAll("-", "/") + "\n");
						return aux;
					}else {
						// se mode de escrita o arquivo deve exixtir
						return null;
					}

				}
			}
		}
		return null;
	}
	
	public static void saveFiles() {
		FileWriter arq;
		try {
			arq = new FileWriter("Files.txt");
			PrintWriter save = new PrintWriter(arq);
			for (File file : FileSystem.getAllFiles()) {
				if (!(file instanceof Directory))
					save.println(file.toString());
				else {
					save.println(((Directory)file).toString());
				}
			}
			arq.close();
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
			arq.close();
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
		    		file = new Directory(parans[1]);
		    		for (int i = 2; i < parans.length;i++)
		    			((Directory)file).getDirIndex().add(parans[i]);
		    		FileSystem.getAllFiles().add(file);
		    	}
		    	line = br.readLine();
		    	
		    }
		    for (File file : FileSystem.getAllFiles()) {
		    	if (file instanceof Directory && !file.getName().contains(".")) {
		    		((Directory)file).loadDir();
		    	}
		    }
		    br.close();
		} catch (RuntimeException | IOException e){
			e.printStackTrace();
		}
	}
	
	public static Directory getDir(String path, Directory dir) {
		for (File file : ((Directory)dir).getFiles()) {
			if (file.getName().equals(path)) {
				return ((Directory)file);
			}
		}
		return null;
	}
	
	public static File getFile(String path, Directory dir) {
		for (File file : ((Directory)dir).getFiles()) {
			if (file.getName().equals(path)) {
				return file;
			}
		}
		return null;
	}
}
