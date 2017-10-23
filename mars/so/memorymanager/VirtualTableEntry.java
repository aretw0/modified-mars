package mars.so.memorymanager;

public class VirtualTableEntry {
/*
 * Criar uma classe �entrada da tabela virtual� com, no m�nimo, os atributos:
o Referenciada
o Modificada
o Prote��o (bits R, W e X)
o Presente/ausente
o N�mero da moldura
 * 
 * */
	private boolean reference;
	private boolean modified;
	private boolean r;
	private boolean w;
	private boolean x;
	/**
	 * presente = 1 ausente = 0
	 */
	private boolean pA;
	private int numFrame;
		
	
	public boolean getReference() {
		return reference;
	}
	public void setReference(boolean reference) {
		this.reference = reference;
	}
	public boolean getModified() {
		return modified;
	}
	public void setModified(boolean modified) {
		this.modified = modified;
	}
	public boolean isR() {
		return r;
	}
	public void setR(boolean r) {
		this.r = r;
	}
	public boolean isW() {
		return w;
	}
	public void setW(boolean w) {
		this.w = w;
	}
	public boolean isX() {
		return x;
	}
	public void setX(boolean x) {
		this.x = x;
	}
	public boolean getPA() {
		return pA;
	}
	public void setPA(boolean state) {
		this.pA = state;
	}
	public int getNumFrame() {
		return numFrame;
	}
	public void setNumFrame(int numFrame) {
		this.numFrame = numFrame;
	}
}
