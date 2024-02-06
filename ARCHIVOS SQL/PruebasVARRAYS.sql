DECLARE
    salida VARCHAR2(50) := '';
BEGIN
    salida := TELEFONO1('MANUEL');
    DBMS_OUTPUT.PUT_LINE('Primer telefono de MANUEL: ' || salida);
    
    salida := TELEFONO1('pepe');
    DBMS_OUTPUT.PUT_LINE('Primer telefono de PEPE: ' || salida);
    
    salida := TELEFONO1('JUANA');
    DBMS_OUTPUT.PUT_LINE('Primer telefono de JUANA: ' || salida);
    
    salida := TELEFONO1('PETER');
    DBMS_OUTPUT.PUT_LINE('Primer telefono de PETER: ' || salida);
END;
/

DECLARE 
  TEL TELEFONO := TELEFONO(NULL, NULL, NULL); 
BEGIN
  --Cargo el array de teléfono de MARTA
  SELECT TELEF INTO TEL FROM AGENDA WHERE NOMBRE = 'MARTA';
  --Visualizar Datos de su array
  DBMS_OUTPUT.PUT_LINE('Nº DE TELÉFONOS ACTUALES:    ' || TEL.COUNT);
  DBMS_OUTPUT.PUT_LINE('ÍNDICE DEL PRIMER ELEMENTO:  ' || TEL.FIRST);
  DBMS_OUTPUT.PUT_LINE('ÍNDICE DEL ÚLTIMO ELEMENTO:  ' || TEL.LAST);
  DBMS_OUTPUT.PUT_LINE('MÁXIMO Nº DE TLFS PERMITIDO: ' || TEL.LIMIT);
   
  --Añade un número de teléfono a MARTA   
  TEL.EXTEND; 
  TEL(TEL.COUNT):= '123000000';     
  UPDATE AGENDA A SET A.TELEF = TEL WHERE NOMBRE = 'MARTA';       

  --Elimina un teléfono   
  SELECT TELEF INTO TEL FROM AGENDA WHERE NOMBRE = 'MANUEL'; 
  
  --Elimina el último elemento del array        
  --TEL.TRIM;  
  --Elimina todos los elementos 
  TEL.DELETE; 

  --Actualizo los teléfonos de MANUEL, con TEL
  UPDATE AGENDA A SET A.TELEF = TEL WHERE NOMBRE = 'MANUEL';    


END;
/
