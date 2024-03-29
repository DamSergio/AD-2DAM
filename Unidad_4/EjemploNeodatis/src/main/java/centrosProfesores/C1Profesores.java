package centrosProfesores;

import java.util.Date;

public class C1Profesores {
	private Integer codProf;
	private String nombreApe;
    private String nombreEspe;
    private Date fechaNac;
    private String sexo;
	private C1Centros c1Centros;
	
	public C1Profesores() {
		super();
		this.codProf = 0;
		this.nombreApe = "";
		this.nombreEspe = "";
		this.fechaNac = new Date();
		this.sexo = "";
		this.c1Centros = new C1Centros();
	}
	
	public C1Profesores(Integer codProf, String nombreApe, String nombreEspe, Date fechaNac, String sexo,
			C1Centros c1Centros) {
		super();
		this.codProf = codProf;
		this.nombreApe = nombreApe;
		this.nombreEspe = nombreEspe;
		this.fechaNac = fechaNac;
		this.sexo = sexo;
		this.c1Centros = c1Centros;
	}

	public Integer getCodProf() {
		return codProf;
	}

	public void setCodProf(Integer codProf) {
		this.codProf = codProf;
	}

	public String getNombreApe() {
		return nombreApe;
	}

	public void setNombreApe(String nombreApe) {
		this.nombreApe = nombreApe;
	}

	public String getNombreEspe() {
		return nombreEspe;
	}

	public void setNombreEspe(String nombreEspe) {
		this.nombreEspe = nombreEspe;
	}

	public Date getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public C1Centros getC1Centros() {
		return c1Centros;
	}

	public void setC1Centros(C1Centros c1Centros) {
		this.c1Centros = c1Centros;
	}

	@Override
	public String toString() {
		return "C1Profesores [codProf=" + codProf + ", nombreApe=" + nombreApe + ", nombreEspe=" + nombreEspe
				+ ", fechaNac=" + fechaNac + ", sexo=" + sexo + ", c1Centros=" + c1Centros + "]";
	}
}
