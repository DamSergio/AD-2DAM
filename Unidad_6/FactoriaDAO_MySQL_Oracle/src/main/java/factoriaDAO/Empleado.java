package factoriaDAO;

import java.io.Serializable;
import java.sql.Date;

public class Empleado implements Serializable {
	
	private int empno;
	
	private String apellido;
	private String oficio;
	private int dir;
	private Date fechaAlt;
	private float salario;
	private float comision;
	private int deptno;
	
	public Empleado() {
	}
	
	public Empleado(int empno,  String apellido, String oficio, int dir, Date fechaAlt, float salario,
			float comision,int deptno) {
		super();
		this.empno = empno;
		this.deptno = deptno;
		this.apellido = apellido;
		this.oficio = oficio;
		this.dir = dir;
		this.fechaAlt = fechaAlt;
		this.salario = salario;
		this.comision = comision;
	}
	
	public int getEmpno() {
		return empno;
	}
	public void setEmpno(int empno) {
		this.empno = empno;
	}
	public int getDeptno() {
		return deptno;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getOficio() {
		return oficio;
	}
	public void setOficio(String oficio) {
		this.oficio = oficio;
	}
	public int getDir() {
		return dir;
	}
	public void setDir(int dir) {
		this.dir = dir;
	}
	public Date getFechaAlt() {
		return fechaAlt;
	}
	public void setFechaAlt(Date fechaAlt) {
		this.fechaAlt = fechaAlt;
	}
	public float getSalario() {
		return salario;
	}
	public void setSalario(float salario) {
		this.salario = salario;
	}
	public float getComision() {
		return comision;
	}
	public void setComision(float comision) {
		this.comision = comision;
	}
	@Override
	public String toString() {
		return "Empleados [empno=" + empno + ", deptno=" + deptno + ", apellido=" + apellido + ", oficio=" + oficio
				+ ", dir=" + dir + ", fechaAlt=" + fechaAlt + ", salario=" + salario + ", comision=" + comision + "]";
	}


}
