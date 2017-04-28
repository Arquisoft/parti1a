# language: es

Característica: Inicio de sesión con un usuario normal

  Escenario: Inicio de sesión con Juana Alonso
    Dada la lista de usuarios:
      | name                 | password |
      | jualo@participant.es | jualo123 |
    Cuando introduzco el usuario "jualo@participant.es" y la contraseña "jualo123"
    Entonces entro en la pantalla de sugerencias