CREATE TYPE TIP_TELEFONOS AS VARRAY(3) OF VARCHAR2(15);
/

CREATE TYPE TIP_DIRECCION AS OBJECT(
  CALLE      VARCHAR2(50),
  POBLACION  VARCHAR2(50),
  CODPOSTAL  NUMBER(5),
  PROVINCIA  VARCHAR2(40)
);
/

CREATE TYPE TIP_CLIENTE AS OBJECT(
  IDCLIENTE  NUMBER,
  NOMBRE     VARCHAR2(50),
  DIREC      TIP_DIRECCION,
  NIF        VARCHAR2(9),
  TELEF      TIP_TELEFONOS
);
/	

CREATE TYPE TIP_PRODUCTO AS OBJECT (
  IDPRODUCTO   NUMBER,
  DESCRIPCION  VARCHAR2(80),
  PVP          NUMBER,
  STOCKACTUAL  NUMBER
);
/

CREATE TYPE TIP_LINEAVENTA AS OBJECT (
  NUMEROLINEA  NUMBER,
  IDPRODUCTO   REF TIP_PRODUCTO,
  CANTIDAD     NUMBER
);
/

CREATE TYPE TIP_LINEAS_VENTA AS TABLE OF TIP_LINEAVENTA;
/

CREATE TYPE TIP_VENTA AS OBJECT (
  IDVENTA     NUMBER,
  IDCLIENTE   REF TIP_CLIENTE,
  FECHAVENTA  DATE,   
  LINEAS   TIP_LINEAS_VENTA, --Tabla anidada
  MEMBER FUNCTION TOTAL_VENTA RETURN NUMBER
);
/

CREATE OR REPLACE TYPE BODY TIP_VENTA AS
    MEMBER FUNCTION TOTAL_VENTA RETURN NUMBER IS
        TOTAL NUMBER:=0;
        LINEA   TIP_LINEAVENTA;
        PRODUCT TIP_PRODUCTO;
    BEGIN
        FOR I IN 1..LINEAS.COUNT LOOP
            LINEA := LINEAS(I);
            SELECT DEREF(LINEA.IDPRODUCTO) INTO PRODUCT FROM DUAL;
            TOTAL := TOTAL + LINEA.CANTIDAD * PRODUCT.PVP;
        END LOOP;
        RETURN TOTAL;
    END;
END;
/

-- CREAR TABLAS
CREATE TABLE TABLA_CLIENTES OF TIP_CLIENTE (
  IDCLIENTE PRIMARY KEY,
  NIF UNIQUE
);
/

CREATE TABLE TABLA_PRODUCTOS OF TIP_PRODUCTO (
  IDPRODUCTO PRIMARY KEY
);
/

CREATE TABLE TABLA_VENTAS OF TIP_VENTA (
  IDVENTA PRIMARY KEY
) NESTED TABLE LINEAS STORE AS TABLA_LINEAS;
/

-- INSERTAR DATOS
INSERT INTO TABLA_CLIENTES VALUES 
(1, 'Luis Gracia', TIP_DIRECCION('C/Las Flores 23', 'Guadalajara',
 '19003', 'Guadalajara'), 
'34343434L', TIP_TELEFONOS( '949876655', '949876655')
);

INSERT INTO TABLA_CLIENTES VALUES 
(2, 'Ana Serrano',TIP_DIRECCION ('C/Galiana 6', 'Guadalajara',
 '19004', 'Guadalajara'), 
'76767667F', TIP_TELEFONOS('94980009')
);

INSERT INTO TABLA_PRODUCTOS VALUES (1, 'CAJA DE CRISTAL DE MURANO', 100, 5);
INSERT INTO TABLA_PRODUCTOS VALUES (2, 'BICICLETA CITY', 120, 15);
INSERT INTO TABLA_PRODUCTOS VALUES (3,'100 L�PICES DE COLORES', 20, 5);
INSERT INTO TABLA_PRODUCTOS VALUES (4, 'OPERACIONES CON BD', 25, 5);
INSERT INTO TABLA_PRODUCTOS VALUES (5, 'APLICACIONES WEB', 25.50, 10);
/

INSERT INTO TABLA_VENTAS 
SELECT 1, REF(C), SYSDATE, TIP_LINEAS_VENTA()
FROM TABLA_CLIENTES C WHERE C.IDCLIENTE = 1;
/

INSERT INTO TABLE
(SELECT V.LINEAS FROM TABLA_VENTAS V WHERE V.IDVENTA = 1) 
(SELECT 1, REF(P), 1 FROM TABLA_PRODUCTOS P WHERE P.IDPRODUCTO = 1);

INSERT INTO TABLE
(SELECT V.LINEAS FROM TABLA_VENTAS V WHERE V.IDVENTA = 1) 
(SELECT 2, REF(P), 2 FROM TABLA_PRODUCTOS P WHERE P.IDPRODUCTO = 2);
/

INSERT INTO TABLA_VENTAS 
SELECT 2, REF(C), SYSDATE, TIP_LINEAS_VENTA()
FROM TABLA_CLIENTES C WHERE C.IDCLIENTE = 1;
/

INSERT INTO TABLE
(SELECT V.LINEAS FROM TABLA_VENTAS V WHERE V.IDVENTA = 2) 
(SELECT 1, REF(P), 2 FROM TABLA_PRODUCTOS P WHERE P.IDPRODUCTO = 1);

INSERT INTO TABLE
(SELECT V.LINEAS FROM TABLA_VENTAS V WHERE V.IDVENTA = 2) 
(SELECT 2, REF(P), 1 FROM TABLA_PRODUCTOS P WHERE P.IDPRODUCTO = 4);

INSERT INTO TABLE
(SELECT V.LINEAS FROM TABLA_VENTAS V WHERE V.IDVENTA = 2) 
(SELECT 3, REF(P), 4 FROM TABLA_PRODUCTOS P WHERE P.IDPRODUCTO = 5);
/

SELECT  IDVENTA, DEREF(IDCLIENTE).NOMBRE NOMBRE,
DEREF(IDCLIENTE).IDCLIENTE IDCLIENTE,T.TOTAL_VENTA() TOTAL
FROM TABLA_VENTAS T;
/

SELECT P.IDVENTA IDV, DEREF(P.IDCLIENTE).NOMBRE NOMBRE,
DETALLE.NUMEROLINEA LINEA,
DEREF(DETALLE.IDPRODUCTO).DESCRIPCION PRODUCTO,
DETALLE.CANTIDAD,
DETALLE.CANTIDAD*DEREF(DETALLE.IDPRODUCTO).PVP IMPORTE,
DEREF(DETALLE.IDPRODUCTO).PVP PVP,
DEREF(DETALLE.IDPRODUCTO).STOCKACTUAL STOCK
FROM TABLA_VENTAS P, TABLE(P.LINEAS) DETALLE;
/

CREATE OR REPLACE PROCEDURE VER_VENTA (ID NUMBER) AS
  IMPORTE NUMBER;  
  TOTAL_V NUMBER;
CLI TIP_CLIENTE:=TIP_CLIENTE(NULL,NULL,NULL,NULL, NULL);
  FEC DATE;
  --cursor para recorrer la tabla anidada del idventa
  --que se recibe, recorre las l�neas de venta
CURSOR C1 IS 
    SELECT NUMEROLINEA LIN, DEREF(IDPRODUCTO) PROD,CANTIDAD FROM THE
     (SELECT T.LINEAS FROM TABLA_VENTAS T WHERE IDVENTA=ID);
BEGIN
  --obtener datos de la venta
  SELECT DEREF(IDCLIENTE), FECHAVENTA, V.TOTAL_VENTA() 
INTO CLI, FEC, TOTAL_V
  FROM TABLA_VENTAS V WHERE IDVENTA = ID;

DBMS_OUTPUT.PUT_LINE('NUMERO DE VENTA: '||ID|| 
' * Fecha de venta: '|| FEC);
DBMS_OUTPUT.PUT_LINE('CLIENTE: '||CLI.NOMBRE);  
  DBMS_OUTPUT.PUT_LINE('DIRECCION: '||CLI.DIREC.CALLE);
DBMS_OUTPUT.PUT_LINE('============================================'F);

FOR I IN C1 LOOP  
IMPORTE:= I.CANTIDAD * I.PROD.PVP;
DBMS_OUTPUT.PUT_LINE(I.LIN||'*'|| I.PROD.DESCRIPCION ||'*'||
        I.PROD.PVP||'*'|| I.CANTIDAD||'*'||IMPORTE);
END LOOP;
  DBMS_OUTPUT.PUT_LINE('Total Venta: '||TOTAL_V);
END VER_VENTA;
/

--Ejecutamos el procedimiento para visualizar los datos de la venta 2:

BEGIN
  VER_VENTA(2);
END;
/
