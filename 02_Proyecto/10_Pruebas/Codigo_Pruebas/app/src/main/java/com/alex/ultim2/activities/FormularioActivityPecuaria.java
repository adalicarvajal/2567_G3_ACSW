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

public class FormularioActivityPecuaria extends BaseActivity {
    CheckBox checkBoxOtros;
    EditText editTextOtros;
    CheckBox checkBoxBovinosLeche;
    CheckBox checkBoxBovinosCarne;
    CheckBox checkBoxPorcinos;
    CheckBox checkBoxBovinosDobleProposito;
    CheckBox checkBoxRumiantesMenores;
    CheckBox checkBoxEspeciesMenores;
    CheckBox checkBoxAviculturaPonedoras;
    CheckBox checkBoxAviculturaBroilers;
    CheckBox checkBoxAcuiculturaAguaDulce;
    CheckBox checkBoxAcuiculturaTropical;
    CheckBox checkBoxPastosForrajes;
    CheckBox checkBoxNutricionAnimal;
    CheckBox checkBoxReproduccionAnimal;
    CheckBox checkBoxSanidad;
    CheckBox checkBoxDesarrolloEvaluacion;

    IGoogleSheets iGoogleSheets1;
    //
    String otros="NO";
    String bovinosleche="NO";
    String bovinoscarne="NO";
    String porcinos1="NO";
    String bovinosdobleproposito="NO";
    String rumiantesmenores="NO";
    String especiesmenores="NO";
    String desarrolloevaluacion="NO";
    String aviculturaponedoras="NO";
    String aviculturabroilers="NO";
    String acuiculturaaguadulce="NO";
    String acuiculturatropical="NO";
    String pastosyforrajes="NO";
    String nutricionanimal="NO";
    String reproduccionanimal="NO";
    String sanidad1="NO";
    String actividadotros="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_pecuaria);

        checkBoxOtros = findViewById(R.id.checkBoxOtros);
        checkBoxDesarrolloEvaluacion= findViewById(R.id.checkBoxDesarrolloEvaluacion);
        editTextOtros = findViewById(R.id.editTextOtros);
        checkBoxBovinosLeche= findViewById(R.id.checkBoxBovinosLeche);
        checkBoxBovinosCarne= findViewById(R.id.checkBoxBovinosCarne);
        checkBoxPorcinos= findViewById(R.id.checkBoxPorcinos);
        checkBoxBovinosDobleProposito= findViewById(R.id.checkBoxBovinosDobleProposito);
        checkBoxRumiantesMenores= findViewById(R.id.checkBoxRumiantesMenores);
        checkBoxEspeciesMenores= findViewById(R.id.checkBoxEspeciesMenores);
        checkBoxAviculturaPonedoras= findViewById(R.id.checkBoxAviculturaPonedoras);
        checkBoxAviculturaBroilers= findViewById(R.id.checkBoxAviculturaBroilers);
        checkBoxAcuiculturaAguaDulce= findViewById(R.id.checkBoxAcuiculturaAguaDulce);
        checkBoxAcuiculturaTropical= findViewById(R.id.checkBoxAcuiculturaTropical);
        checkBoxPastosForrajes= findViewById(R.id.checkBoxPastosForrajes);
        checkBoxNutricionAnimal= findViewById(R.id.checkBoxNutricionAnimal);
        checkBoxReproduccionAnimal= findViewById(R.id.checkBoxReproduccionAnimal);
        checkBoxSanidad= findViewById(R.id.checkBoxSanidad);
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
            Toast.makeText(FormularioActivityPecuaria.this, "Guardando cambios", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FormularioActivityPecuaria.this, MainActivity.class);
            startActivity(intent);

        });

        Button botonRegresar = findViewById(R.id.botonRegresar);
        botonRegresar.setOnClickListener(v -> {
            Toast.makeText(FormularioActivityPecuaria.this, "Botón Regresar presionado", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
    public void LoadActivities(){

        ProgressDialog progressDialog = ProgressDialog.show(FormularioActivityPecuaria.this,
                "Cargando datos",
                "Espere por favor",
                true,
                false);
        iGoogleSheets1 = Common.iGSGetMethodClient1(Common.BASE_URL1);
        String pathUrl1;
        pathUrl1 = "exec?id=" + Common.getUsername().toString()+"&sheet=area_actividad_pecuaria";

        try {
            iGoogleSheets1.getPeople(pathUrl1).enqueue(new Callback<String>() {

                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    try {

                        assert response.body() != null;
                        JSONObject responseObject = new JSONObject(response.body());
                        JSONArray postgradosArray = responseObject.getJSONArray("persons");
                        if(postgradosArray.length()>0){

                            JSONObject object = postgradosArray.getJSONObject(0);
                            String bovinos_leche = object.getString("bovinos leche");
                            String bovinos_carne = object.getString("bovinos carne");
                            String porcinos = object.getString("porcinos");
                            String bovinos_doble_proposito = object.getString("bovinos doble proposito");
                            String rumiantes_menores = object.getString("rumiantes menores");
                            String especies_menores = object.getString("especies menores");
                            String avicultura_ponedoras = object.getString("avicultura ponedoras");
                            String avicultura_broilers = object.getString("avicultura broilers");
                            String acuicultura_agua_dulce = object.getString("acuicultura agua dulce");
                            String acuicultura_tropical = object.getString("acuicultura tropical");
                            String pastos_y_forrajes = object.getString("pastos y forrajes");
                            String nutricion_animal = object.getString("nutricion animal");
                            String reproduccion_animal = object.getString("reproduccion animal");
                            String sanidad = object.getString("sanidad");
                            String desarrollo_y_evaluacion_de_proyectos = object.getString("desarrollo y evaluacion de proyectos");
                            String otros = object.getString("otros");

                            if(bovinos_leche.equals("SI")){
                                checkBoxBovinosLeche.setChecked(true);

                            }
                            if(bovinos_carne.equals("SI")){
                                checkBoxBovinosCarne.setChecked(true);
                            }
                            if(porcinos.equals("SI")){
                                checkBoxPorcinos.setChecked(true);
                            }
                            if(bovinos_doble_proposito.equals("SI")){
                                checkBoxBovinosDobleProposito.setChecked(true);
                            }
                            if(rumiantes_menores.equals("SI")){
                                checkBoxRumiantesMenores.setChecked(true);;
                            }
                            if(especies_menores.equals("SI")){
                                checkBoxEspeciesMenores.setChecked(true);
                            }
                            if(desarrollo_y_evaluacion_de_proyectos.equals("SI")){
                                checkBoxDesarrolloEvaluacion.setChecked(true);
                            }
                            if(avicultura_ponedoras.equals("SI")){
                                checkBoxAviculturaPonedoras.setChecked(true);
                            }
                            if(avicultura_broilers.equals("SI")){
                                checkBoxAviculturaBroilers.setChecked(true);
                            }
                            if(acuicultura_agua_dulce.equals("SI")){
                                checkBoxAcuiculturaAguaDulce.setChecked(true);
                            }
                            if(acuicultura_tropical.equals("SI")){
                                checkBoxAcuiculturaTropical.setChecked(true);
                            }
                            if(pastos_y_forrajes.equals("SI")){
                                checkBoxPastosForrajes.setChecked(true);
                            }
                            if(nutricion_animal.equals("SI")){
                                checkBoxNutricionAnimal.setChecked(true);
                            }
                            if(reproduccion_animal.equals("SI")){
                                checkBoxReproduccionAnimal.setChecked(true);
                            }
                            if(sanidad.equals("SI")){
                                checkBoxSanidad.setChecked(true);
                            }

                            if(otros.equals("SI")){
                                String actividadotros=object.getString("actividad_otros");
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
        ProgressDialog progressDialog = ProgressDialog.show(FormularioActivityPecuaria.this,
                "Actualizando datos",
                "Espere por favor",
                true,
                false);


        if(checkBoxBovinosLeche.isChecked()){
            bovinosleche="SI";
        }
        if(checkBoxBovinosCarne.isChecked()){
            bovinoscarne="SI";
        }
        if(checkBoxPorcinos.isChecked()){
            porcinos1="SI";
        }
        if(checkBoxBovinosDobleProposito.isChecked()){
            bovinosdobleproposito="SI";
        }
        if(checkBoxRumiantesMenores.isChecked()){
            rumiantesmenores="SI";
        }

        if(checkBoxEspeciesMenores.isChecked()){
            especiesmenores="SI";
        }
        if(checkBoxDesarrolloEvaluacion.isChecked()){
            desarrolloevaluacion="SI";
        }
        if(checkBoxAviculturaPonedoras.isChecked()){
            aviculturaponedoras="SI";
        }
        if(checkBoxAviculturaBroilers.isChecked()){
            aviculturabroilers="SI";
        }
        if(checkBoxAcuiculturaAguaDulce.isChecked()){
            acuiculturaaguadulce="SI";
        }
        if(checkBoxAcuiculturaTropical.isChecked()){
            acuiculturatropical="SI";
        }
        if(checkBoxPastosForrajes.isChecked()){
            pastosyforrajes="SI";
        }
        if(checkBoxNutricionAnimal.isChecked()){
            nutricionanimal="SI";
        }
        if(checkBoxReproduccionAnimal.isChecked()){
            reproduccionanimal="SI";
        }
        if(checkBoxSanidad.isChecked()){
            sanidad1="SI";
        }
        //
        if(checkBoxOtros.isChecked()){
            otros="SI";
            actividadotros=editTextOtros.getText().toString();
        }


        String sheet="area_actividad_pecuaria";




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
                        "    \"field\": \"" + "bovinos leche" + "\",\n" +
                        "    \"value\": \"" + bovinosleche + "\"\n" +
                        "}";
                Call<String> call = iGoogleSheets.getStringRequestBody(jsonRequest);

                Response<String> response = call.execute();
                int code = response.code();
                if (code == 200) {
                    jsonRequest = "{\n" +
                            "    \"sheet\": \"" + sheet + "\",\n" +
                            "    \"id\": \"" + Common.getUsername() + "\",\n" +
                            "    \"field\": \"" + "bovinos carne" + "\",\n" +
                            "    \"value\": \"" + bovinoscarne + "\"\n" +
                            "}";
                    call = iGoogleSheets.getStringRequestBody(jsonRequest);
                    response = call.execute();
                    code = response.code();
                    if(code==200){
                        jsonRequest = "{\n" +
                                "    \"sheet\": \"" + sheet + "\",\n" +
                                "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                "    \"field\": \"" + "porcinos" + "\",\n" +
                                "    \"value\": \"" + porcinos1 + "\"\n" +
                                "}";
                        call = iGoogleSheets.getStringRequestBody(jsonRequest);
                        response = call.execute();
                        code = response.code();
                        if(code==200){
                            jsonRequest = "{\n" +
                                    "    \"sheet\": \"" + sheet + "\",\n" +
                                    "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                    "    \"field\": \"" + "bovinos doble proposito" + "\",\n" +
                                    "    \"value\": \"" + bovinosdobleproposito + "\"\n" +
                                    "}";
                            call = iGoogleSheets.getStringRequestBody(jsonRequest);
                            response = call.execute();
                            code = response.code();
                            if(code==200){
                                jsonRequest = "{\n" +
                                        "    \"sheet\": \"" + sheet + "\",\n" +
                                        "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                        "    \"field\": \"" + "rumiantes menores" + "\",\n" +
                                        "    \"value\": \"" + rumiantesmenores + "\"\n" +
                                        "}";
                                call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                response = call.execute();
                                code = response.code();
                                if(code==200){
                                    jsonRequest = "{\n" +
                                            "    \"sheet\": \"" + sheet + "\",\n" +
                                            "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                            "    \"field\": \"" + "especies menores" + "\",\n" +
                                            "    \"value\": \"" + especiesmenores+ "\"\n" +
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
                                                    "    \"field\": \"" + "avicultura ponedoras" + "\",\n" +
                                                    "    \"value\": \"" + aviculturaponedoras + "\"\n" +
                                                    "}";
                                            call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                            response = call.execute();
                                            code = response.code();
                                            if(code==200){
                                                jsonRequest = "{\n" +
                                                        "    \"sheet\": \"" + sheet + "\",\n" +
                                                        "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                        "    \"field\": \"" + "avicultura broilers" + "\",\n" +
                                                        "    \"value\": \"" + aviculturabroilers + "\"\n" +
                                                        "}";
                                                call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                                response = call.execute();
                                                code = response.code();
                                                if(code==200){
                                                    jsonRequest = "{\n" +
                                                            "    \"sheet\": \"" + sheet + "\",\n" +
                                                            "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                            "    \"field\": \"" + "acuicultura agua dulce" + "\",\n" +
                                                            "    \"value\": \"" + acuiculturaaguadulce+ "\"\n" +
                                                            "}";
                                                    call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                                    response = call.execute();
                                                    code = response.code();
                                                    if(code==200){
                                                        jsonRequest = "{\n" +
                                                                "    \"sheet\": \"" + sheet + "\",\n" +
                                                                "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                                "    \"field\": \"" + "acuicultura tropical" + "\",\n" +
                                                                "    \"value\": \"" + acuiculturatropical + "\"\n" +
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
                                                                        "    \"field\": \"" + "actividad_otros" + "\",\n" +
                                                                        "    \"value\": \"" + actividadotros + "\"\n" +
                                                                        "}";
                                                                call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                                                response = call.execute();
                                                                code = response.code();
                                                                if(code==200){
                                                                    jsonRequest = "{\n" +
                                                                            "    \"sheet\": \"" + sheet + "\",\n" +
                                                                            "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                                            "    \"field\": \"" + "pastos y forrajes" + "\",\n" +
                                                                            "    \"value\": \"" + pastosyforrajes + "\"\n" +
                                                                            "}";
                                                                    call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                                                    response = call.execute();
                                                                    code = response.code();
                                                                    if(code==200){
                                                                        jsonRequest = "{\n" +
                                                                                "    \"sheet\": \"" + sheet + "\",\n" +
                                                                                "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                                                "    \"field\": \"" + "nutricion animal" + "\",\n" +
                                                                                "    \"value\": \"" + nutricionanimal + "\"\n" +
                                                                                "}";
                                                                        call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                                                        response = call.execute();
                                                                        code = response.code();
                                                                        if(code==200){
                                                                            jsonRequest = "{\n" +
                                                                                    "    \"sheet\": \"" + sheet + "\",\n" +
                                                                                    "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                                                    "    \"field\": \"" + "reproduccion animal" + "\",\n" +
                                                                                    "    \"value\": \"" + reproduccionanimal + "\"\n" +
                                                                                    "}";
                                                                            call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                                                            response = call.execute();
                                                                            code = response.code();
                                                                            if(code==200){
                                                                                jsonRequest = "{\n" +
                                                                                        "    \"sheet\": \"" + sheet + "\",\n" +
                                                                                        "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                                                        "    \"field\": \"" + "sanidad" + "\",\n" +
                                                                                        "    \"value\": \"" + sanidad1 + "\"\n" +
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

