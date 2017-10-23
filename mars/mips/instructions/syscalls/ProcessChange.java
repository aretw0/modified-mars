package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.mips.hardware.RegisterFile;
import mars.so.processmanager.ProcessTable;

public class ProcessChange extends AbstractSyscall {
	
	private int numCall;
	

	public ProcessChange() {
		super(19, "WeChange"); // foi o nome que dei
		
	}

	@Override
	public void simulate(ProgramStatement statement) throws ProcessingException {
		int temp = RegisterFile.getUserRegister("$a0").getValue();
		switch (temp){
		case 1:
			ProcessTable.processChange("roteamento");
			break;
		case 2:
			ProcessTable.processChange("prioridadeFixa");
			break;
		case 3:
			ProcessTable.processChange("prioridadeDin�mica");
			break;
		case 4:
			ProcessTable.processChange("loteria");
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
