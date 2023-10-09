--
-- TABLA DEPARTAMENTOS SQLITE -  Derby - H2 - HSQLDB
--
CREATE TABLE departamentos (
 dept_no  int NOT NULL PRIMARY KEY,
 dnombre  VARCHAR(15), 
 loc      VARCHAR(15)
) ;

-- Tabla departamenros MYSQL . Esto para las referencias en MySQL, ponerlo
SET FOREIGN_KEY_CHECKS = 0; 
CREATE TABLE departamentos (
 dept_no  int NOT NULL PRIMARY KEY,
 dnombre  VARCHAR(15), 
 loc      VARCHAR(15)
) engine = Innodb;

--oracle
CREATE TABLE departamentos (
 dept_no  int NOT NULL PRIMARY KEY,
 dnombre  VARCHAR2(15), 
 loc      VARCHAR2(15)
);

-- ---------------------------------------------------------------------------
-- INSERCIÓN DE FILAS EN DEPARTAMENTOS PARA TODAS LAS BD

INSERT INTO departamentos VALUES (10,'CONTABILIDAD','SEVILLA');
INSERT INTO departamentos VALUES (20,'INVESTIGACIÓN','MADRID');
INSERT INTO departamentos VALUES (30,'VENTAS','BARCELONA');
INSERT INTO departamentos VALUES (40,'PRODUCCIÓN','BILBAO');


-- ---------------------------------------------------------------------------
-- TABLA EMPLEADOS - SQLITE -Derby - H2 - HSQLDB
-- ---------------------------------------------------------------------------
CREATE TABLE empleados (
 emp_no    INT  NOT NULL PRIMARY KEY,
 apellido  VARCHAR(10), 
 oficio    VARCHAR(10),
 dir       INT,
 fecha_alt DATE ,
 salario   FLOAT,
 comision  FLOAT,
 dept_no   INT NOT NULL REFERENCES departamentos(dept_no)
);


-- ---------------------------------------------------------------------------
-- TABLA EMPLEADOS -  MYSQL
-- ---------------------------------------------------------------------------
CREATE TABLE empleados (
 emp_no    int  NOT NULL PRIMARY KEY,
 apellido  VARCHAR(10),
 oficio    VARCHAR(10),
 dir       int,
 fecha_alt DATE      ,
 salario   float,
 comision  float,
 dept_no  int NOT NULL,
  CONSTRAINT fkempledep FOREIGN KEY  (dept_no) REFERENCES departamentos(dept_no)
) engine = Innodb;

-- ---------------------------------------------------------------------------
-- INSERCIÓN TABLA EMPLEADOS - MYSQL - SQLITE -Derby - H2 - HSQLDB
-- ---------------------------------------------------------------------------
INSERT INTO empleados VALUES (7369,'SÁNCHEZ','EMPLEADO',7902,'1990-12-17',1040,NULL,20);
INSERT INTO empleados VALUES (7499,'ARROYO','VENDEDOR',7698,'1990-02-20',   1500,390,30);
INSERT INTO empleados VALUES (7521,'SALA','VENDEDOR',7698,'1991-02-22',     1625,650,30);
INSERT INTO empleados VALUES (7566,'JIMÉNEZ','DIRECTOR',7839,'1991-04-02',  2900,NULL,20);
INSERT INTO empleados VALUES (7654,'MARTÍN','VENDEDOR',7698,'1991-09-29',2900,1020,30);
INSERT INTO empleados VALUES (7698,'NEGRO','DIRECTOR',7839,'1991-05-01', 3005,NULL,30);
INSERT INTO empleados VALUES (7782,'CEREZO','DIRECTOR',7839,'1991-06-09',  2885,NULL,10);
INSERT INTO empleados VALUES (7788,'GIL','ANALISTA',7566,'1991-11-09',   3000,NULL,20);
INSERT INTO empleados VALUES (7839,'REY','PRESIDENTE',NULL,'1991-11-17',  4100,NULL,10);
INSERT INTO empleados VALUES (7844,'TOVAR','VENDEDOR',7698,'1991-09-08',  1350,0,30);
INSERT INTO empleados VALUES (7876,'ALONSO','EMPLEADO',7788,'1991-09-23', 1430,NULL,20);
INSERT INTO empleados VALUES (7900,'JIMENO','EMPLEADO',7698,'1991-12-03',1335,NULL,30);
INSERT INTO empleados VALUES (7902,'FERNÁNDEZ','ANALISTA',7566,'1991-12-03',    3000,NULL,20);
INSERT INTO empleados VALUES (7934,'MUÑOZ','EMPLEADO',7782,'1992-01-23', 1690,NULL,10);

-- ---------------------------------------------------------------------------
-- EMPLEADOS oracle
-- ---------------------------------------------------------------------------
CREATE TABLE empleados (
 emp_no    int  NOT NULL PRIMARY KEY,
 apellido  VARCHAR2(10),
 oficio    VARCHAR2(10),
 dir       int,
 fecha_alt DATE    ,
 salario   float,
 comision  float,
 dept_no   int  NOT NULL REFERENCES departamentos(dept_no)
);

ALTER SESSION SET NLS_DATE_FORMAT='DD/MM/YYYY';
 
INSERT INTO empleados VALUES (7369,'SANCHEZ','EMPLEADO',7902,'17/12/1990',  1040,NULL,20);
INSERT INTO empleados VALUES (7499,'ARROYO','VENDEDOR',7698,'20/02/1990', 1500,390,30);
INSERT INTO empleados VALUES (7521,'SALA','VENDEDOR',7698,'22/02/1991', 1625,650,30);
INSERT INTO empleados VALUES (7566,'JIMENEZ','DIRECTOR',7839,'02/04/1991', 2900,NULL,20);
INSERT INTO empleados VALUES (7654,'MARTIN','VENDEDOR',7698,'29/09/1991',1600,1020,30);
INSERT INTO empleados VALUES (7698,'NEGRO','DIRECTOR',7839,'01/05/1991', 3005,NULL,30);
INSERT INTO empleados VALUES (7782,'CEREZO','DIRECTOR',7839,'09/06/1991', 2885,NULL,10);
INSERT INTO empleados VALUES (7788,'GIL','ANALISTA',7566,'09/11/1991',3000,NULL,20);
INSERT INTO empleados VALUES (7839,'REY','PRESIDENTE',NULL,'17/11/1991',4100,NULL,10);
INSERT INTO empleados VALUES (7844,'TOVAR','VENDEDOR',7698,'08/09/1991', 1350,0,30);
INSERT INTO empleados VALUES (7876,'ALONSO','EMPLEADO',7788,'23/09/1991', 1430,NULL,20);
INSERT INTO empleados VALUES (7900,'JIMENO','EMPLEADO',7698,'03/12/1991',1335,NULL,30);
INSERT INTO empleados VALUES (7902,'FERNANDEZ','ANALISTA',7566,'03/12/1991',3000,NULL,20);
INSERT INTO empleados VALUES (7934,'MUÑOZ','EMPLEADO',7782,'23/01/1992', 1690,NULL,10);
COMMIT;


