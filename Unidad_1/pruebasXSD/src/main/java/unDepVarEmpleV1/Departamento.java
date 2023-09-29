//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.2 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2023.09.26 a las 08:28:26 PM CEST 
//


package unDepVarEmpleV1;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Departamento complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Departamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codigodep" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="nombredep" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="localidad" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="presupuesto" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Departamento", propOrder = {
    "codigodep",
    "nombredep",
    "localidad",
    "presupuesto"
})
public class Departamento {

    @XmlElement(required = true)
    protected BigInteger codigodep;
    @XmlElement(required = true)
    protected String nombredep;
    @XmlElement(required = true)
    protected String localidad;
    @XmlElement(required = true)
    protected BigDecimal presupuesto;

    /**
     * Obtiene el valor de la propiedad codigodep.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCodigodep() {
        return codigodep;
    }

    /**
     * Define el valor de la propiedad codigodep.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCodigodep(BigInteger value) {
        this.codigodep = value;
    }

    /**
     * Obtiene el valor de la propiedad nombredep.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombredep() {
        return nombredep;
    }

    /**
     * Define el valor de la propiedad nombredep.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombredep(String value) {
        this.nombredep = value;
    }

    /**
     * Obtiene el valor de la propiedad localidad.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalidad() {
        return localidad;
    }

    /**
     * Define el valor de la propiedad localidad.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalidad(String value) {
        this.localidad = value;
    }

    /**
     * Obtiene el valor de la propiedad presupuesto.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPresupuesto() {
        return presupuesto;
    }

    /**
     * Define el valor de la propiedad presupuesto.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPresupuesto(BigDecimal value) {
        this.presupuesto = value;
    }

}
