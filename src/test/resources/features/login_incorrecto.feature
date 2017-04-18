# language: es

 Característica: El usuario debe de hacer login en la aplicacion
 
 	Escenario: Iniciar sesion usuario con un email con formato incorrecto
 		
 		Cuando el usuario se encuentra en la pagina de login
 		Entonces inserta su mail incorrecto "noexisto@gmailcom" y su password "123456"
 		Entonces se logea de manera incorrecta