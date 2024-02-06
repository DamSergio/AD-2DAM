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
  --Cargo el array de tel�fono de MARTA
  SELECT TELEF INTO TEL FROM AGENDA WHERE NOMBRE = 'MARTA';
  --Visualizar Datos de su array
  DBMS_OUTPUT.PUT_LINE('N� DE TEL�FONOS ACTUALES:    ' || TEL.COUNT);
  DBMS_OUTPUT.PUT_LINE('�NDICE DEL PRIMER ELEMENTO:  ' || TEL.FIRST);
  DBMS_OUTPUT.PUT_LINE('�NDICE DEL �LTIMO ELEMENTO:  ' || TEL.LAST);
  DBMS_OUTPUT.PUT_LINE('M�XIMO N� DE TLFS PERMITIDO: ' || TEL.LIMIT);
   
  --A�ade un n�mero de tel�fono a MARTA   
  TEL.EXTEND; 
  TEL(TEL.COUNT):= '123000000';     
  UPDATE AGENDA A SET A.TELEF = TEL WHERE NOMBRE = 'MARTA';       

  --Elimina un tel�fono   
  SELECT TELEF INTO TEL FROM AGENDA WHERE NOMBRE = 'MANUEL'; 
  
  --Elimina el �ltimo elemento del array        
  --TEL.TRIM;  
  --Elimina todos los elementos 
  TEL.DELETE; 

  --Actualizo los tel�fonos de MANUEL, con TEL
  UPDATE AGENDA A SET A.TELEF = TEL WHERE NOMBRE = 'MANUEL';    


END;
/
