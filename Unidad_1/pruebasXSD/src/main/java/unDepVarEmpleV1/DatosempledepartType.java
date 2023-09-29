//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.2 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2023.09.26 a las 08:28:26 PM CEST 
//


package unDepVarEmpleV1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para DatosempledepartType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="DatosempledepartType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="departamento" type="{}Departamento"/&gt;
 *         &lt;element name="empleados" type="{}Empleados"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatosempledepartType", propOrder = {
    "departamento",
    "empleados"
})
public class DatosempledepartType {

    @XmlElement(required = true)
    protected Departamento departamento;
    @XmlElement(required = true)
    protected Empleados empleados;

    /**
     * Obtiene el valor de la propiedad departamento.
     * 
     * @return
     *     possible object is
     *     {@link Departamento }
     *     
     */
    public Departamento getDepartamento() {
        return departamento;
    }

    /**
     * Define el valor de la propiedad departamento.
     * 
     * @param value
     *     allowed object is
     *     {@link Departamento }
     *     
     */
    public void setDepartamento(Departamento value) {
        this.departamento = value;
    }

    /**
     * Obtiene el valor de la propiedad empleados.
     * 
     * @return
     *     possible object is
     *     {@link Empleados }
     *     
     */
    public Empleados getEmpleados() {
        return empleados;
    }

    /**
     * Define el valor de la propiedad empleados.
     * 
     * @param value
     *     allowed object is
     *     {@link Empleados }
     *     
     */
    public void setEmpleados(Empleados value) {
        this.empleados = value;
    }

}
