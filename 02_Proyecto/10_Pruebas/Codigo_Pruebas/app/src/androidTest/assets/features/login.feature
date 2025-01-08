Feature: Inicio de Sesión
  Realizar el inicio de sesión al ingresar un nombre de usuario y contraseña

  @login-error
  Scenario Outline: Entrada inválida de username o password
    Given Yo tengo una actividad de inicio de sesión
    When Ingreso el nombre de usuario <username>
    And Ingreso la contraseña <password>
    And Presiono el botón de enviar
    Then Debería ver un mensaje de error con el texto "Usuario o contraseña incorrectos"

    Examples:
      | username   | password   |
      | invalid    | 123456     |
      | 1718605155 | wrongpass  |
      | invalid    | wrongpass  |

  @login-feature
  Scenario Outline: Entrada válida de username y password
    Given Yo tengo una actividad de inicio de sesión
    When Ingreso el nombre de usuario <username>
    And Ingreso la contraseña <password>
    And Presiono el botón de enviar
    Then Debería ver la siguiente actividad

    Examples:
      | username   | password   |
      | 1718605165 | 1718605155 |
      | 1718605155 | 1718605155 |



