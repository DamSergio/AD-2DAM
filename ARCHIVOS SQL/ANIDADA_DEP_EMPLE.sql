create or replace type tipo_empleado as object (
 emp_no    int  ,
 apellido  VARCHAR2(10),
 oficio    VARCHAR2(10),
 dir       int,
 fecha_alt DATE,
 salario   float,
 comision  float,
 dept_no   int
 );
 /
 
CREATE or replace TYPE anidadaemple AS TABLE OF tipo_empleado;
/

CREATE TABLE deparemples (
 dept_no   int NOT NULL PRIMARY KEY,
 dnombre  VARCHAR2(15), 
 loc      VARCHAR2(15),
 empleados anidadaemple
) NESTED TABLE empleados STORE AS EMPLES_ANIDADA;
/

Insert into  deparemples (dept_no, dnombre, loc, empleados)   
          select dept_no, dnombre, loc, anidadaemple() from departamentos;
/

CREATE OR REPLACE PROCEDURE crearanidadaemple AS
    CURSOR departamentos_id IS SELECT dept_no FROM departamentos;
    CURSOR empleados(dept NUMBER) IS SELECT * FROM empleados WHERE dept_no = dept;
    existe NUMBER;
BEGIN
    FOR dept IN departamentos_id LOOP
        FOR emple IN empleados(dept.dept_no) LOOP
            SELECT COUNT(e.emp_no) INTO existe FROM deparemples, TABLE(empleados) e WHERE e.emp_no = emple.emp_no;
            IF (EXISTE > 0) THEN
                CONTINUE;
            END IF;
            
            INSERT INTO TABLE(SELECT empleados FROM deparemples WHERE dept_no = dept.dept_no) VALUES(TIPO_EMPLEADO(emple.emp_no, emple.apellido, emple.oficio, emple.dir, emple.fecha_alt, emple.salario, emple.comision, emple.dept_no));
        END LOOP;
    END LOOP;
END;
