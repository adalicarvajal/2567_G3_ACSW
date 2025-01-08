package com.alex.ultim2.activities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alex.ultim2.R;
import com.alex.ultim2.models.IGoogleSheets;
import com.alex.ultim2.utils.BaseActivity;
import com.alex.ultim2.utils.Common;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UpdatePasswordActivity extends BaseActivity {

    private EditText editTextCurrentPassword;
    private EditText editTextNewPassword;
    private EditText editTextConfirmPassword;
    private Button buttonUpdatePassword;
    private Button buttonCancel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        editTextCurrentPassword = findViewById(R.id.editTextCurrentPassword);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonUpdatePassword = findViewById(R.id.buttonUpdatePassword);
        buttonCancel=findViewById(R.id.buttonCancel);


        buttonUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(updatePassword()){

                    finish();
                }


            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();


            }
        });
    }

    private boolean updatePassword() {
        String currentPassword = editTextCurrentPassword.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        // Validar si las contraseñas coinciden
        if (!currentPassword.equals(Common.getPassword())) {

            Toast.makeText(this, "La contraseña actual no es la correcta. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show();

            return false;
        }
        if (!newPassword.equals(confirmPassword)) {

            Toast.makeText(this, "Las contraseñas no coinciden. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show();

            return false;
        }
        // Mostrar un mensaje de éxito si la contraseña se actualizó correctamente.
        registerPassword();
        Toast.makeText(this, "Contraseña actualizada con éxito.", Toast.LENGTH_SHORT).show();
        return true;
    }
    private void registerPassword() {
        String newPassword = editTextNewPassword.getText().toString();
        AsyncTask.execute(() -> {
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(Common.BASE_URL_PASS)
                        .build();

                IGoogleSheets iGoogleSheets = retrofit.create(IGoogleSheets.class);
                String jsonRequest = "{\n" +
                        "    \"id\": \"" + Common.getUsername() + "\",\n" +
                        "    \"field\": \"" + "password" + "\",\n" +
                        "    \"value\": \"" + newPassword + "\"\n" +
                        "}";
                Call<String> call = iGoogleSheets.getStringRequestBody(jsonRequest);

                Response<String> response = call.execute();
                int code = response.code();
                if (code == 200) {
                    Common.setPassword(newPassword);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
