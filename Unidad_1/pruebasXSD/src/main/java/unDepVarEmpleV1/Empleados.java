//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.2 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2023.09.26 a las 08:28:26 PM CEST 
//


package unDepVarEmpleV1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Empleados complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Empleados"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="emple" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="empno" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                   &lt;element name="apellido" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="oficio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="salario" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Empleados", propOrder = {
    "emple"
})
public class Empleados {

    @XmlElement(required = true)
    protected List<Empleados.Emple> emple;

    /**
     * Gets the value of the emple property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the emple property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmple().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Empleados.Emple }
     * 
     * 
     */
    public List<Empleados.Emple> getEmple() {
        if (emple == null) {
            emple = new ArrayList<Empleados.Emple>();
        }
        return this.emple;
    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType&gt;
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
    @XmlType(name = "", propOrder = {
        "empno",
        "apellido",
        "oficio",
        "salario"
    })
    public static class Emple {

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

}
