package mars.so.filemanager;

public class FileProtection {
	
	private int idRole; // identificador do dono/grupo/outros
	private boolean r;
	private boolean w;
	private boolean x;

	public FileProtection() {

	}
	public FileProtection(int id, boolean r, boolean w, boolean x) {
		this.idRole = id;
		this.r = r;
		this.w = w;
		this.x = x;
	}

	public int getIdRole() {
		return idRole;
	}

	public void setIdRole(int idRole) {
		this.idRole = idRole;
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
	
	public String getRights(){
		String right;
		
		if(this.r) 
			right = "R";
		else
			right = "-";
		if (this.w)
			right+= "W";
		else
			right+= "-";
		if (this.x)
			right+= "X";
		else
			right+= "-";
		
		return right;
	}
}
