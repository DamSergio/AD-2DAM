//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.2 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2023.10.05 a las 04:15:28 PM CEST 
//


package centrosProfes;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Centros complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Centros"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codigocentro" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="nombrecentro" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="director" type="{}Profe"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Centros", propOrder = {
    "codigocentro",
    "nombrecentro",
    "direccion",
    "director"
})
public class Centros {

    @XmlElement(required = true)
    protected BigInteger codigocentro;
    @XmlElement(required = true)
    protected String nombrecentro;
    @XmlElement(required = true)
    protected String direccion;
    @XmlElement(required = true)
    protected Profe director;

    /**
     * Obtiene el valor de la propiedad codigocentro.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCodigocentro() {
        return codigocentro;
    }

    /**
     * Define el valor de la propiedad codigocentro.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCodigocentro(BigInteger value) {
        this.codigocentro = value;
    }

    /**
     * Obtiene el valor de la propiedad nombrecentro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombrecentro() {
        return nombrecentro;
    }

    /**
     * Define el valor de la propiedad nombrecentro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombrecentro(String value) {
        this.nombrecentro = value;
    }

    /**
     * Obtiene el valor de la propiedad direccion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Define el valor de la propiedad direccion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDireccion(String value) {
        this.direccion = value;
    }

    /**
     * Obtiene el valor de la propiedad director.
     * 
     * @return
     *     possible object is
     *     {@link Profe }
     *     
     */
    public Profe getDirector() {
        return director;
    }

    /**
     * Define el valor de la propiedad director.
     * 
     * @param value
     *     allowed object is
     *     {@link Profe }
     *     
     */
    public void setDirector(Profe value) {
        this.director = value;
    }

}
