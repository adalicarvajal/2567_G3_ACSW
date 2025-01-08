package com.alex.ultim2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alex.ultim2.R;
import com.alex.ultim2.models.IGoogleSheets;
import com.alex.ultim2.models.User;
import com.alex.ultim2.utils.BaseActivity;
import com.alex.ultim2.utils.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView errorTextView;
    IGoogleSheets iGoogleSheets;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Obtener referencias a los elementos del layout
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        errorTextView = findViewById(R.id.errorTextView);
        iGoogleSheets = Common.iGSGetMethodClient(Common.BASE_URL_PASS);
        //nuevo codigo
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d("NotificationData", "Key: " + key + " Value: " + value);
            }
        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el nombre de usuario y contraseña ingresados
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String pathUrl;
                pathUrl ="exec?id=" + username;
                List<User> userList = new ArrayList<>();

                try {
                    //pathUrl ="exec?spreadsheet_id=" + Common.GOOGLE_SHEET_ID + "&sheet=" + Common.SHEET_NAME;

                    iGoogleSheets.getPeople(pathUrl).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                            try {
                                assert response.body() != null;
                                JSONObject responseObject = new JSONObject(response.body());
                                JSONArray peopleArray = responseObject.getJSONArray("personas");

                                for (int i = 0; i < peopleArray.length(); i++) {
                                    JSONObject object = peopleArray.getJSONObject(i);
                                    String username = object.getString("id");
                                    String password1 = object.getString("password");


                                    User user = new User(username,password1);
                                    userList.add(user);


                                }

                                int size = userList.size();
                                if (size>0){
                                    String pass=userList.get(0).getPassword();
                                    // Verificar si las credenciales son válidas (puedes ajustar esta lógica según tus requisitos)
                                    if (isValidCredentials(username, password,pass)) {
                                        // Las credenciales son válidas, continuar con la lógica de inicio de sesión
                                        Common.setUsername(userList.get(0).getUsername());
                                        Common.setPassword(userList.get(0).getPassword());
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        errorTextView.setVisibility(View.GONE); // Ocultar el mensaje de error si estaba visible
                                        // Agrega aquí tu código para iniciar sesión exitosamente
                                    } else {
                                        // Las credenciales son inválidas, mostrar mensaje de error
                                        errorTextView.setVisibility(View.VISIBLE);
                                        errorTextView.setText("Usuario o contraseña incorrectos");
                                    }

                                }else{
                                    errorTextView.setVisibility(View.VISIBLE);
                                    errorTextView.setText("Usuario o contraseña incorrectos");
                                }




                            } catch (JSONException je) {
                                je.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });
    }

    private boolean isValidCredentials(String username, String password,String pass) {
        if(password.equals(pass)){
            return true;
        }else{
            return false;
        }



    }

}


