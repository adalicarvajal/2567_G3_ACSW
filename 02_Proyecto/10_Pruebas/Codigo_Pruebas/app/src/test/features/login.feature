Feature: Login en la app

  Scenario: Usuario con credenciales correctas puede iniciar sesión
    Given El usuario está en la pantalla de login
    When El usuario ingresa un nombre de usuario y contraseña válidos
    Then El usuario debería ser redirigido a la pantalla principal

  Scenario: Usuario con credenciales incorrectas recibe un error
    Given El usuario está en la pantalla de login
    When El usuario ingresa un nombre de usuario o contraseña incorrecta
    Then El usuario debería ver un mensaje de error
