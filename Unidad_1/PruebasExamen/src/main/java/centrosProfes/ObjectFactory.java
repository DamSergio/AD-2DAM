//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.2 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2023.10.05 a las 04:15:28 PM CEST 
//


package centrosProfes;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the centrosProfes package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Datoscentros_QNAME = new QName("", "datoscentros");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: centrosProfes
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DatoscentroType }
     * 
     */
    public DatoscentroType createDatoscentroType() {
        return new DatoscentroType();
    }

    /**
     * Create an instance of {@link UnCentro }
     * 
     */
    public UnCentro createUnCentro() {
        return new UnCentro();
    }

    /**
     * Create an instance of {@link Centros }
     * 
     */
    public Centros createCentros() {
        return new Centros();
    }

    /**
     * Create an instance of {@link Profe }
     * 
     */
    public Profe createProfe() {
        return new Profe();
    }

    /**
     * Create an instance of {@link Profesores }
     * 
     */
    public Profesores createProfesores() {
        return new Profesores();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatoscentroType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DatoscentroType }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "datoscentros")
    public JAXBElement<DatoscentroType> createDatoscentros(DatoscentroType value) {
        return new JAXBElement<DatoscentroType>(_Datoscentros_QNAME, DatoscentroType.class, null, value);
    }

}
