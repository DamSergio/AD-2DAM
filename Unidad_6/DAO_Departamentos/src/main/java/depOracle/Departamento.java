package depOracle;

public class Departamento {
	private int deptNo;
	private String dNombre;
	private String loc;
	
	public Departamento() {
		super();
		this.deptNo = 0;
		this.dNombre = "";
		this.loc = "";
	}
	
	public Departamento(int deptNo, String dNombre, String loc) {
		super();
		this.deptNo = deptNo;
		this.dNombre = dNombre;
		this.loc = loc;
	}

	public int getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}

	public String getdNombre() {
		return dNombre;
	}

	public void setdNombre(String dNombre) {
		this.dNombre = dNombre;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	@Override
	public String toString() {
		return "Departamento [deptNo=" + deptNo + ", dNombre=" + dNombre + ", loc=" + loc + "]";
	}
}
