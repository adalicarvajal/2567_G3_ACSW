Feature: Cambio de Contraseña
  Probar la funcionalidad de cambio de contraseña desde la actividad principal.

  @change-password-feature
  Scenario Outline: Actualizar contraseña correctamente
    Given Yo tengo una actividad de inicio de sesión
    When Ingreso el nombre de usuario <username>
    And Ingreso la contraseña <password>
    And Presiono el botón de enviar
    Then Debería ver la siguiente actividad
    When Presiono el botón de actualizar contraseña
    Then Debería ver la actividad de actualizar contraseña
    And Ingreso la contraseña actual <currentPassword>
    And Ingreso la nueva contraseña <newPassword>
    And Confirmo la nueva contraseña <confirmPassword>
    And Presiono el botón para guardar la nueva contraseña
    Then Debería ver un mensaje de éxito

    Examples:
      | username       | password      | currentPassword | newPassword | confirmPassword |
      | 1718605155     | newpass123    | newpass123      | 1718605155  | 1718605155      |
