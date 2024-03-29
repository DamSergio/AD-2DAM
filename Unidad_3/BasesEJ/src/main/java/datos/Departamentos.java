package datos;
// Generated 14 dic 2023 15:57:23 by Hibernate Tools 5.6.15.Final

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * Departamentos generated by hbm2java
 */
public class Departamentos implements java.io.Serializable {

	private BigInteger deptNo;
	private String dnombre;
	private String loc;
	private BigInteger numples;
	private Double media;
	private Set empleadoses = new HashSet(0);

	public Departamentos() {
	}

	public Departamentos(BigInteger deptNo) {
		this.deptNo = deptNo;
	}

	public Departamentos(BigInteger deptNo, String dnombre, String loc, BigInteger numples, Double media,
			Set empleadoses) {
		this.deptNo = deptNo;
		this.dnombre = dnombre;
		this.loc = loc;
		this.numples = numples;
		this.media = media;
		this.empleadoses = empleadoses;
	}

	public BigInteger getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(BigInteger deptNo) {
		this.deptNo = deptNo;
	}

	public String getDnombre() {
		return this.dnombre;
	}

	public void setDnombre(String dnombre) {
		this.dnombre = dnombre;
	}

	public String getLoc() {
		return this.loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public BigInteger getNumples() {
		return this.numples;
	}

	public void setNumples(BigInteger numples) {
		this.numples = numples;
	}

	public Double getMedia() {
		return this.media;
	}

	public void setMedia(Double media) {
		this.media = media;
	}

	public Set getEmpleadoses() {
		return this.empleadoses;
	}

	public void setEmpleadoses(Set empleadoses) {
		this.empleadoses = empleadoses;
	}

}
