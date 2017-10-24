package mars.mips.instructions.syscalls;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.mips.hardware.RegisterFile;
import mars.so.filemanager.Directory;
import mars.so.filemanager.File;
import mars.so.filemanager.FileSystem;
import mars.so.processmanager.ProcessControlBlock;
import mars.so.processmanager.ProcessTable;
import mars.util.SystemIO;

public class Fork extends AbstractSyscall {
			
	private int numCall; 
	public boolean hasLoad = false;

	public Fork() {
		super(18, "WeFork"); // foi o nome que dei
		
	}

	@Override
	public void simulate(ProgramStatement statement) throws ProcessingException {
		if (!hasLoad) {
			FileSystem.loadInode();
			FileSystem.loadFiles();	
			hasLoad = true;	
		}

		List<Integer> temp = new ArrayList<Integer>();
		for (int i = 0; i < RegisterFile.getRegisters().length;i++){
			temp.add(RegisterFile.getValue(i));
		}
		temp.add(RegisterFile.getValue(33));
		temp.add(RegisterFile.getValue(34));		
		ProcessTable.addNewPCB(new ProcessControlBlock(RegisterFile.getUserRegister("$a0").getValue(), RegisterFile.getUserRegister("$a1").getValue(),
				RegisterFile.getUserRegister("$a0").getValue(), "pronto", RegisterFile.getUserRegister("$v1").getValue(),
				RegisterFile.getUserRegister("$a2").getValue(), RegisterFile.getUserRegister("$a3").getValue(), temp));		
		SystemIO.printString("Adicionando processo: " + RegisterFile.getUserRegister("$a0").getValue() + "\n");
	}

	public int getNumCall() {
		return numCall;
	}

	public void setNumCall(int numCall) {
		this.numCall = numCall;
	}

}
