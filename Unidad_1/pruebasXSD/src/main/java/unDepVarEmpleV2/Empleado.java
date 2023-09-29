//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.2 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2023.09.28 a las 04:46:37 PM CEST 
//


package unDepVarEmpleV2;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Empleado complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Empleado"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="empno" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="apellido" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oficio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="salario" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Empleado", propOrder = {
    "empno",
    "apellido",
    "oficio",
    "salario"
})
public class Empleado {

    @XmlElement(required = true)
    protected BigInteger empno;
    @XmlElement(required = true)
    protected String apellido;
    @XmlElement(required = true)
    protected String oficio;
    protected float salario;

    /**
     * Obtiene el valor de la propiedad empno.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEmpno() {
        return empno;
    }

    /**
     * Define el valor de la propiedad empno.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEmpno(BigInteger value) {
        this.empno = value;
    }

    /**
     * Obtiene el valor de la propiedad apellido.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Define el valor de la propiedad apellido.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellido(String value) {
        this.apellido = value;
    }

    /**
     * Obtiene el valor de la propiedad oficio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOficio() {
        return oficio;
    }

    /**
     * Define el valor de la propiedad oficio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOficio(String value) {
        this.oficio = value;
    }

    /**
     * Obtiene el valor de la propiedad salario.
     * 
     */
    public float getSalario() {
        return salario;
    }

    /**
     * Define el valor de la propiedad salario.
     * 
     */
    public void setSalario(float value) {
        this.salario = value;
    }

}
