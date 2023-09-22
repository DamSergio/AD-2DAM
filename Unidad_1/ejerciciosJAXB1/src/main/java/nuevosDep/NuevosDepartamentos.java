package nuevosDep;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Nuevosdepartamentos")
public class NuevosDepartamentos {
	private ArrayList<Departamento> nuevosDepartamentos;

	public NuevosDepartamentos(ArrayList<Departamento> nuevosDepartamentos) {
		super();
		this.nuevosDepartamentos = nuevosDepartamentos;
	}
	
	public NuevosDepartamentos() {
		super();
		this.nuevosDepartamentos = new ArrayList<>();
	}
	
	@XmlElement(name="departamento")
	public ArrayList<Departamento> getNuevosDepartamentos() {
		return nuevosDepartamentos;
	}

	public void setListaDep(ArrayList<Departamento> nuevosDepartamentos) {
		this.nuevosDepartamentos = nuevosDepartamentos;
	}
}
