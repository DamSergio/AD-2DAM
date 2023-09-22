package nuevosDep;

import javax.xml.bind.annotation.XmlType;

@XmlType()
public class Departamento {
	private String deptno;
	private String dnombre;
	private String localidad;
	
	public Departamento(String deptno, String dnombre, String localidad) {
		super();
		this.deptno = deptno;
		this.dnombre = dnombre;
		this.localidad = localidad;
	}
	
	public Departamento() {
		super();
		this.deptno = "";
		this.dnombre = "";
		this.localidad = "";
	}

	public String getDeptno() {
		return deptno;
	}

	public void setDeptno(String deptno) {
		this.deptno = deptno;
	}

	public String getDnombre() {
		return dnombre;
	}

	public void setDnombre(String dnombre) {
		this.dnombre = dnombre;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
}
