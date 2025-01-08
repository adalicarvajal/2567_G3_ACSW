package com.alex.ultim2.test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.alex.ultim2.R;
import com.alex.ultim2.activities.LoginActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@RunWith(AndroidJUnit4.class)
public class UpdatePasswordTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        // Lógica para iniciar sesión
        onView(withId(R.id.usernameEditText))
                .perform(typeText("17186051556"), closeSoftKeyboard());
        onView(withId(R.id.passwordEditText))
                .perform(typeText("1718605155"), closeSoftKeyboard());
        onView(withId(R.id.loginButton))
                .perform(click());

        // Esperar a que la actividad principal se cargue
        SystemClock.sleep(2000);
    }





    @When("^Presiono el botón de actualizar contraseña$")
    public void presionoElBotonDeActualizarContraseña() {
        onView(withId(R.id.updatePasswordButton))
                .perform(click());
    }

    @Then("^Debería ver la actividad de actualizar contraseña$")
    public void deberiaVerLaActividadDeActualizarContraseña() {
        onView(withId(R.id.buttonUpdatePassword ))
                .check(matches(isDisplayed()));
    }

    @And("^Ingreso la contraseña actual (\\S+)$")
    public void ingresoLaContraseñaActual(String currentPassword) {
        onView(withId(R.id.editTextCurrentPassword))
                .perform(typeText(currentPassword), closeSoftKeyboard());
    }

    @And("^Ingreso la nueva contraseña (\\S+)$")
    public void ingresoLaNuevaContraseña(String newPassword) {
        onView(withId(R.id.editTextNewPassword))
                .perform(typeText(newPassword), closeSoftKeyboard());
    }

    @And("^Confirmo la nueva contraseña (\\S+)$")
    public void confirmoLaNuevaContraseña(String confirmPassword) {
        onView(withId(R.id.editTextConfirmPassword))
                .perform(typeText(confirmPassword), closeSoftKeyboard());
    }

    @And("^Presiono el botón para guardar la nueva contraseña$")
    public void presionoElBotonParaGuardarLaNuevaContraseña() {
        onView(withId(R.id.buttonUpdatePassword))
                .perform(click());
    }

    @Then("^Debería ver un mensaje de éxito$")
    public void deberiaVerUnMensajeDeExito() {
        SystemClock.sleep(2000); // Esperar a que el mensaje de éxito sea visible
        onView(withText("Contraseña actualizada con éxito."))
                .check(matches(isDisplayed()));
    }

    @After("@change-password-feature")
    public void tearDown() {

        // Cerrar la aplicación
        Espresso.pressBack(); // Simula el botón de retroceso
        //InstrumentationRegistry.getInstrumentation().finishActivity(); // Finaliza la actividad actual
    }
}
