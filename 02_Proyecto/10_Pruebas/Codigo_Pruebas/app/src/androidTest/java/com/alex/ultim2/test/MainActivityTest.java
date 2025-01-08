package com.alex.ultim2.test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;

import android.content.Intent;
import android.os.SystemClock;

import androidx.test.filters.SmallTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.alex.ultim2.R;
import com.alex.ultim2.activities.LoginActivity;
import com.alex.ultim2.activities.MainActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@SmallTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Rule
    private LoginActivity activity;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule2 = new ActivityTestRule<>(MainActivity.class);

    //@Rule
    //public ActivityTestRule<LoginActivity> loginErrorFeatureTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setup() {
        activityTestRule.launchActivity(new Intent());
        activity = activityTestRule.getActivity();
        assertNotNull("La actividad de inicio de sesión debería estar inicializada", activity);
    }


    @After
    public void tearDown() {
        // Agregar un mensaje de impresión para verificar si el método se ejecuta
        System.out.println("=== Ejecutando tearDown ===");

        // Cierra la actividad de Login si aún está abierta
        if (activity != null) {
            activity.finish();
            System.out.println("Cerrando LoginActivity");
        }
        //finishAffinity();

        // Cierra la actividad de Main si aún está abierta
        if (activityTestRule2.getActivity() != null) {
            activityTestRule2.getActivity().finish();
            System.out.println("Cerrando MainActivity");
        }
    }



    @Given("^Yo tengo una actividad de inicio de sesión$")
    public void I_have_a_login_activity() {
        assertNotNull("La actividad de inicio de sesión debería estar inicializada", activity);
    }

    @When("^Ingreso el nombre de usuario (\\S+)$")
    public void I_input_username(final String username) {
        onView(withId(R.id.usernameEditText)).perform(typeText(username), closeSoftKeyboard());
    }

    @And("^Ingreso la contraseña (\\S+)$")
    public void I_input_password(final String password) {
        onView(withId(R.id.passwordEditText)).perform(typeText(password), closeSoftKeyboard());
    }

    @And("^Presiono el botón de enviar$")
    public void I_press_submit_button() {
        SystemClock.sleep(3000);

        onView(withId(R.id.loginButton)).perform(click());
    }
    @Then("^Debería ver un mensaje de error con el texto \"([^\"]*)\"$")
    public void I_should_see_error_message(final String expectedErrorMessage) {
        onView(withId(R.id.errorTextView))
                .check(matches(isDisplayed())) // Verifica que el mensaje de error sea visible
                .check(matches(withText(expectedErrorMessage))); // Verifica que el texto coincida
    }



    @Then("^Debería ver la siguiente actividad$")
    public void I_should_see_on_next_activity() {
        SystemClock.sleep(3000); // Tiempo para que la transición ocurra

        onView(withId(R.id.updatePasswordButton))
                .check(matches(isDisplayed()));

    }

}
