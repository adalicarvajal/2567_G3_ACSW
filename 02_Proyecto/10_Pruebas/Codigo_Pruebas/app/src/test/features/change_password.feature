Feature: Cambio de Contraseña
  Probar la funcionalidad de cambio de contraseña desde la actividad principal.

  @change-password-feature
  Scenario Outline: Actualizar contraseña correctamente
    Given Estoy en la actividad principal y he iniciado sesión
    When Presiono el botón de actualizar contraseña
    And Ingreso la contraseña actual <currentPassword>
    And Ingreso la nueva contraseña <newPassword>
    And Confirmo la nueva contraseña <confirmPassword>
    And Presiono el botón para guardar la nueva contraseña
    Then Debería ver un mensaje de éxito

    Examples:
      | currentPassword | newPassword | confirmPassword |
      | 1718605155      | newpass123  | newpass123      |
