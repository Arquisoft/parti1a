# language: es

Característica: Un usuario debe poder crear comentarios de una sugerencia

  Escenario: Crear un comentario de una sugerencia
  	Dado el usuario logueado "jualo123@participant"  
    Cuando ese usuario comenta una sugerencia
    Entonces al acceder a los comentarios de la sugerencia puede ver su comentario
