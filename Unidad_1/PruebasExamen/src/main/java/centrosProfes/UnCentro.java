//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.2 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2023.10.05 a las 04:15:28 PM CEST 
//


package centrosProfes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para UnCentro complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="UnCentro"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="centro" type="{}Centros"/&gt;
 *         &lt;element name="profesores" type="{}Profesores"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnCentro", propOrder = {
    "centro",
    "profesores"
})
public class UnCentro {

    @XmlElement(required = true)
    protected Centros centro;
    @XmlElement(required = true)
    protected Profesores profesores;

    /**
     * Obtiene el valor de la propiedad centro.
     * 
     * @return
     *     possible object is
     *     {@link Centros }
     *     
     */
    public Centros getCentro() {
        return centro;
    }

    /**
     * Define el valor de la propiedad centro.
     * 
     * @param value
     *     allowed object is
     *     {@link Centros }
     *     
     */
    public void setCentro(Centros value) {
        this.centro = value;
    }

    /**
     * Obtiene el valor de la propiedad profesores.
     * 
     * @return
     *     possible object is
     *     {@link Profesores }
     *     
     */
    public Profesores getProfesores() {
        return profesores;
    }

    /**
     * Define el valor de la propiedad profesores.
     * 
     * @param value
     *     allowed object is
     *     {@link Profesores }
     *     
     */
    public void setProfesores(Profesores value) {
        this.profesores = value;
    }

}
