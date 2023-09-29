//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.2 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2023.09.28 a las 04:46:37 PM CEST 
//


package unDepVarEmpleV2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the unDepVarEmpleV2 package. 
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

    private final static QName _Departamentoemples_QNAME = new QName("", "departamentoemples");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: unDepVarEmpleV2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DatosempledepartType }
     * 
     */
    public DatosempledepartType createDatosempledepartType() {
        return new DatosempledepartType();
    }

    /**
     * Create an instance of {@link Departamento }
     * 
     */
    public Departamento createDepartamento() {
        return new Departamento();
    }

    /**
     * Create an instance of {@link ListaEmpleados }
     * 
     */
    public ListaEmpleados createListaEmpleados() {
        return new ListaEmpleados();
    }

    /**
     * Create an instance of {@link Empleado }
     * 
     */
    public Empleado createEmpleado() {
        return new Empleado();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatosempledepartType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DatosempledepartType }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "departamentoemples")
    public JAXBElement<DatosempledepartType> createDepartamentoemples(DatosempledepartType value) {
        return new JAXBElement<DatosempledepartType>(_Departamentoemples_QNAME, DatosempledepartType.class, null, value);
    }

}
