package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.mips.hardware.RegisterFile;
import mars.mips.so.processmanager.ProcessTable;

public class ProcessTerminate extends AbstractSyscall {
	
	private int numCall;
	
	public ProcessTerminate() {
		super(20, "WeTerminate");
		
	}

	@Override
	public void simulate(ProgramStatement statement) throws ProcessingException {
		int temp = RegisterFile.getUserRegister("$a0").getValue();
		switch (temp){
		case 1:
			ProcessTable.processTerminate("roteamento");
			break;
		case 2:
			ProcessTable.processTerminate("prioridadeFixa");
			break;
		case 3:
			ProcessTable.processTerminate("prioridadeDinâmica");
			break;
		case 4:
			ProcessTable.processTerminate("loteria");
			break;
			default:
				break;
		}

	}

	public int getNumCall() {
		return numCall;
	}

	public void setNumCall(int numCall) {
		this.numCall = numCall;
	}

}
