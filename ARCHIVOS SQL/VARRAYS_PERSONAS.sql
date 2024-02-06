CREATE TYPE emples AS VARRAY(5) OF PERSONA;
/

CREATE TABLE grupos 
(
  NOMBRE VARCHAR2(15),
  litaemples emples
);
/

SELECT dnombre, persona(emp_no, apellido, direccion(loc, NULL, NULL), fecha_alt ) 
FROM empleados JOIN departamentos USING(dept_no);


--RELLENAR TABLA
DECLARE 
    CURSOR c1 IS SELECT * FROM departamentos;
    CURSOR c2(de NUMBER) IS SELECT * FROM empleados WHERE dept_no=de;
    lista emples;
BEGIN
  FOR rd IN c1 LOOP
   lista := emples();
   FOR re IN c2(rd.dept_no) LOOP
      IF (lista.COUNT < lista.LIMIT) THEN
         lista.EXTEND;
         lista(lista.COUNT):= persona(re.emp_no, re.apellido, direccion(NULL, rd.loc, NULL), re.fecha_alt );
       END IF;
   END LOOP;
   INSERT INTO grupos VALUES(rd.dnombre, lista);
   DBMS_OUTPUT.PUT_LINE('Insertado dep:'||rd.dnombre||', con '||lista.count||' empleados.');
  
  END LOOP;
END;
/

--MOSTRAR TABLA
DECLARE
    CURSOR grupos IS SELECT * FROM grupos;
BEGIN
    FOR grupo IN grupos LOOP
        DBMS_OUTPUT.PUT_LINE('Grupo: ' || grupo.nombre);
        
        IF (grupo.litaemples IS NULL) THEN
            DBMS_OUTPUT.PUT_LINE('No tiene array');
            CONTINUE;
        END IF;
        
        IF (grupo.litaemples.COUNT = 0) THEN
            DBMS_OUTPUT.PUT_LINE('No tiene empleados');
            CONTINUE;
        END IF;
        
        FOR i IN 1 .. grupo.litaemples.COUNT LOOP
            DBMS_OUTPUT.PUT_LINE('    empleado: ' || grupo.litaemples(i).nombre || ', Calle: ' || grupo.litaemples(i).direc.ciudad);
        END LOOP;
    END LOOP;
END;
