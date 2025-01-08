package com.alex.ultim2.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alex.ultim2.R;
import com.alex.ultim2.models.IGoogleSheets;
import com.alex.ultim2.utils.BaseActivity;
import com.alex.ultim2.utils.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class FormularioActivityAgricola extends BaseActivity {
    CheckBox checkBoxOtros;
    EditText editTextOtros;
    CheckBox checkBoxHortalizas;
    CheckBox checkBoxFrutales;
    CheckBox checkBoxCultivosAndinos;
    CheckBox checkBoxCultivosTropicales;
    CheckBox checkBoxFloricultura;
    CheckBox checkBoxOrnamentales;
    CheckBox checkBoxDesarrolloEvaluacion;
    CheckBox checkBoxControlCalidad;
    CheckBox checkBoxManejoRemediacion;
    CheckBox checkBoxAgroecologiaMedioambiente;
    CheckBox checkBoxNutricionVegetal;
    IGoogleSheets iGoogleSheets1;
    //
    String otros="NO";
    String hortalizas="NO";
    String frutales="NO";
    String cultivosandinos="NO";
    String cultivostropicales="NO";
    String floricultura="NO";
    String ornamentales="NO";
    String desarrolloevaluacion="NO";
    String controlcalidad="NO";
    String manejoremediacion="NO";
    String agroecologia="NO";
    String nuticionvegetal="NO";
    String actividadotros="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_agricola);

        checkBoxOtros = findViewById(R.id.checkBoxOtros);
        checkBoxHortalizas= findViewById(R.id.checkBoxHortalizas);
        checkBoxFrutales= findViewById(R.id.checkBoxFrutales);
        checkBoxCultivosAndinos= findViewById(R.id.checkBoxCultivosAndinos);
        checkBoxCultivosTropicales= findViewById(R.id.checkBoxCultivosTropicales);
        checkBoxFloricultura= findViewById(R.id.checkBoxFloricultura);
        checkBoxOrnamentales= findViewById(R.id.checkBoxOrnamentales);
        checkBoxDesarrolloEvaluacion= findViewById(R.id.checkBoxDesarrolloEvaluacion);
        checkBoxControlCalidad= findViewById(R.id.checkBoxControlCalidad);
        checkBoxManejoRemediacion= findViewById(R.id.checkBoxManejoRemediacion);
        checkBoxAgroecologiaMedioambiente= findViewById(R.id.checkBoxAgroecologiaMedioambiente);
        checkBoxNutricionVegetal= findViewById(R.id.checkBoxNutricionVegetal);
        editTextOtros = findViewById(R.id.editTextOtros);
        LoadActivities();

        checkBoxOtros.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                editTextOtros.setVisibility(View.VISIBLE);
            } else {
                editTextOtros.setVisibility(View.GONE);
            }
        });

        Button botonGuardar = findViewById(R.id.botonGuardar);
        botonGuardar.setOnClickListener(v -> {
            saveUpdatedData();
            Toast.makeText(FormularioActivityAgricola.this, "Guardando cambios", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FormularioActivityAgricola.this, MainActivity.class);
            startActivity(intent);

        });

        Button botonRegresar = findViewById(R.id.botonRegresar);
        botonRegresar.setOnClickListener(v -> {
            Toast.makeText(FormularioActivityAgricola.this, "Botón Regresar presionado", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
    public void LoadActivities(){

        ProgressDialog progressDialog = ProgressDialog.show(FormularioActivityAgricola.this,
                "Cargando datos",
                "Espere por favor",
                true,
                false);
        iGoogleSheets1 = Common.iGSGetMethodClient1(Common.BASE_URL1);
        String pathUrl1;
        pathUrl1 = "exec?id=" + Common.getUsername().toString()+"&sheet=area_actividad_agricola";

        try {
            iGoogleSheets1.getPeople(pathUrl1).enqueue(new Callback<String>() {

                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    try {

                        assert response.body() != null;
                        JSONObject responseObject = new JSONObject(response.body());
                        JSONArray postgradosArray = responseObject.getJSONArray("persons");
                        if(postgradosArray.length()>0){

                            JSONObject object = postgradosArray.getJSONObject(0);
                            String hortalizas = object.getString("hortalizas");
                            String frutales = object.getString("frutales");
                            String cultivos_andinos = object.getString("cultivos_andinos");
                            String cultivos_tropicales = object.getString("cultivos_tropicales");
                            String floricultura = object.getString("floricultura");
                            String ornamentales = object.getString("ornamentales");
                            String desarrollo_y_evaluacion_de_proyectos = object.getString("desarrollo y evaluacion de proyectos");
                            String control_de_calidad = object.getString("control de calidad");
                            String manejo_y_remediacion = object.getString("manejo y remediacion");
                            String agroecologia_y_medioambiente = object.getString("agroecologia y medioambiente");
                            String nutricion_vegetal = object.getString("nutricion vegetal");
                            String otros = object.getString("otros");

                            if(hortalizas.equals("SI")){
                                checkBoxHortalizas.setChecked(true);

                            }
                            if(frutales.equals("SI")){
                                checkBoxFrutales.setChecked(true);
                            }
                            if(cultivos_andinos.equals("SI")){
                                checkBoxCultivosAndinos.setChecked(true);
                            }
                            if(cultivos_tropicales.equals("SI")){
                                checkBoxCultivosTropicales.setChecked(true);
                            }
                            if(floricultura.equals("SI")){
                                checkBoxFloricultura.setChecked(true);;
                            }
                            if(ornamentales.equals("SI")){
                                checkBoxOrnamentales.setChecked(true);
                            }
                            if(desarrollo_y_evaluacion_de_proyectos.equals("SI")){
                                checkBoxDesarrolloEvaluacion.setChecked(true);
                            }
                            if(control_de_calidad.equals("SI")){
                                checkBoxControlCalidad.setChecked(true);
                            }
                            if(manejo_y_remediacion.equals("SI")){
                                checkBoxManejoRemediacion.setChecked(true);
                            }
                            if(agroecologia_y_medioambiente.equals("SI")){
                                checkBoxAgroecologiaMedioambiente.setChecked(true);
                            }
                            if(nutricion_vegetal.equals("SI")){
                                checkBoxNutricionVegetal.setChecked(true);
                            }

                            if(otros.equals("SI")){
                                String actividadotros=object.getString("area_otros");
                                checkBoxOtros.setChecked(true);
                                editTextOtros.setText(actividadotros);
                                //actividades.add("Otros: "+actividadotros);
                            }

                        }
                        progressDialog.dismiss();


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
    private void saveUpdatedData() {
        // Retrieve the updated data from the EditText fields
        ProgressDialog progressDialog = ProgressDialog.show(FormularioActivityAgricola.this,
                "Actualizando datos",
                "Espere por favor",
                true,
                false);


        if(checkBoxHortalizas.isChecked()){
            hortalizas="SI";
        }
        if(checkBoxFrutales.isChecked()){
            frutales="SI";
        }
        if(checkBoxCultivosAndinos.isChecked()){
            cultivosandinos="SI";
        }
        if(checkBoxCultivosTropicales.isChecked()){
            cultivostropicales="SI";
        }
        if(checkBoxFloricultura.isChecked()){
            floricultura="SI";
        }

        if(checkBoxOrnamentales.isChecked()){
            ornamentales="SI";
        }
        if(checkBoxDesarrolloEvaluacion.isChecked()){
            desarrolloevaluacion="SI";
        }
        if(checkBoxControlCalidad.isChecked()){
            controlcalidad="SI";
        }
        if(checkBoxManejoRemediacion.isChecked()){
            manejoremediacion="SI";
        }
        if(checkBoxAgroecologiaMedioambiente.isChecked()){
            agroecologia="SI";
        }
        if(checkBoxNutricionVegetal.isChecked()){
            nuticionvegetal="SI";
        }
        if(checkBoxOtros.isChecked()){
            otros="SI";
            actividadotros=editTextOtros.getText().toString();
        }


        String sheet="area_actividad_agricola";




        AsyncTask.execute(() -> {
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(Common.BASE_URL1)
                        .build();

                IGoogleSheets iGoogleSheets = retrofit.create(IGoogleSheets.class);


                String jsonRequest = "{\n" +
                        "    \"sheet\": \"" + sheet + "\",\n" +
                        "    \"id\": \"" + Common.getUsername() + "\",\n" +
                        "    \"field\": \"" + "hortalizas" + "\",\n" +
                        "    \"value\": \"" + hortalizas + "\"\n" +
                        "}";
                Call<String> call = iGoogleSheets.getStringRequestBody(jsonRequest);

                Response<String> response = call.execute();
                int code = response.code();
                if (code == 200) {
                    jsonRequest = "{\n" +
                            "    \"sheet\": \"" + sheet + "\",\n" +
                            "    \"id\": \"" + Common.getUsername() + "\",\n" +
                            "    \"field\": \"" + "frutales" + "\",\n" +
                            "    \"value\": \"" + frutales + "\"\n" +
                            "}";
                    call = iGoogleSheets.getStringRequestBody(jsonRequest);
                    response = call.execute();
                    code = response.code();
                    if(code==200){
                        jsonRequest = "{\n" +
                                "    \"sheet\": \"" + sheet + "\",\n" +
                                "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                "    \"field\": \"" + "cultivos_andinos" + "\",\n" +
                                "    \"value\": \"" + cultivosandinos + "\"\n" +
                                "}";
                        call = iGoogleSheets.getStringRequestBody(jsonRequest);
                        response = call.execute();
                        code = response.code();
                        if(code==200){
                            jsonRequest = "{\n" +
                                    "    \"sheet\": \"" + sheet + "\",\n" +
                                    "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                    "    \"field\": \"" + "cultivos_tropicales" + "\",\n" +
                                    "    \"value\": \"" + cultivostropicales + "\"\n" +
                                    "}";
                            call = iGoogleSheets.getStringRequestBody(jsonRequest);
                            response = call.execute();
                            code = response.code();
                            if(code==200){
                                jsonRequest = "{\n" +
                                        "    \"sheet\": \"" + sheet + "\",\n" +
                                        "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                        "    \"field\": \"" + "floricultura" + "\",\n" +
                                        "    \"value\": \"" + floricultura + "\"\n" +
                                        "}";
                                call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                response = call.execute();
                                code = response.code();
                                if(code==200){
                                    jsonRequest = "{\n" +
                                            "    \"sheet\": \"" + sheet + "\",\n" +
                                            "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                            "    \"field\": \"" + "ornamentales" + "\",\n" +
                                            "    \"value\": \"" + ornamentales + "\"\n" +
                                            "}";
                                    call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                    response = call.execute();
                                    code = response.code();
                                    if(code==200){
                                        jsonRequest = "{\n" +
                                                "    \"sheet\": \"" + sheet + "\",\n" +
                                                "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                "    \"field\": \"" + "desarrollo y evaluacion de proyectos" + "\",\n" +
                                                "    \"value\": \"" + desarrolloevaluacion + "\"\n" +
                                                "}";
                                        call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                        response = call.execute();
                                        code = response.code();
                                        if(code==200){
                                            jsonRequest = "{\n" +
                                                    "    \"sheet\": \"" + sheet + "\",\n" +
                                                    "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                    "    \"field\": \"" + "control de calidad" + "\",\n" +
                                                    "    \"value\": \"" + controlcalidad + "\"\n" +
                                                    "}";
                                            call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                            response = call.execute();
                                            code = response.code();
                                            if(code==200){
                                                jsonRequest = "{\n" +
                                                        "    \"sheet\": \"" + sheet + "\",\n" +
                                                        "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                        "    \"field\": \"" + "manejo y remediacion" + "\",\n" +
                                                        "    \"value\": \"" + manejoremediacion + "\"\n" +
                                                        "}";
                                                call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                                response = call.execute();
                                                code = response.code();
                                                if(code==200){
                                                    jsonRequest = "{\n" +
                                                            "    \"sheet\": \"" + sheet + "\",\n" +
                                                            "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                            "    \"field\": \"" + "agroecologia y medioambiente" + "\",\n" +
                                                            "    \"value\": \"" + agroecologia + "\"\n" +
                                                            "}";
                                                    call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                                    response = call.execute();
                                                    code = response.code();
                                                    if(code==200){
                                                        jsonRequest = "{\n" +
                                                                "    \"sheet\": \"" + sheet + "\",\n" +
                                                                "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                                "    \"field\": \"" + "nutricion vegetal" + "\",\n" +
                                                                "    \"value\": \"" + nuticionvegetal + "\"\n" +
                                                                "}";
                                                        call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                                        response = call.execute();
                                                        code = response.code();
                                                        if(code==200){
                                                            jsonRequest = "{\n" +
                                                                    "    \"sheet\": \"" + sheet + "\",\n" +
                                                                    "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                                    "    \"field\": \"" + "otros" + "\",\n" +
                                                                    "    \"value\": \"" + otros + "\"\n" +
                                                                    "}";
                                                            call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                                            response = call.execute();
                                                            code = response.code();
                                                            if(code==200){
                                                                jsonRequest = "{\n" +
                                                                        "    \"sheet\": \"" + sheet + "\",\n" +
                                                                        "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                                        "    \"field\": \"" + "area_otros" + "\",\n" +
                                                                        "    \"value\": \"" + actividadotros + "\"\n" +
                                                                        "}";
                                                                call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                                                response = call.execute();
                                                                code = response.code();
                                                                if(code==200){
                                                                    return;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        progressDialog.dismiss();
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

