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
 * <p>Clase Java para Profe complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Profe"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codigoprofesor" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="nombreprofe" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "Profe", propOrder = {
    "codigoprofesor",
    "nombreprofe",
    "salario"
})
public class Profe {

    @XmlElement(required = true)
    protected BigInteger codigoprofesor;
    @XmlElement(required = true)
    protected String nombreprofe;
    protected float salario;

    /**
     * Obtiene el valor de la propiedad codigoprofesor.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCodigoprofesor() {
        return codigoprofesor;
    }

    /**
     * Define el valor de la propiedad codigoprofesor.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCodigoprofesor(BigInteger value) {
        this.codigoprofesor = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreprofe.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreprofe() {
        return nombreprofe;
    }

    /**
     * Define el valor de la propiedad nombreprofe.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreprofe(String value) {
        this.nombreprofe = value;
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
