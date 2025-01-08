package com.alex.ultim2.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.alex.ultim2.R;
import com.alex.ultim2.models.IGoogleSheets;
import com.alex.ultim2.models.Persona;
import com.alex.ultim2.utils.BaseActivity;
import com.alex.ultim2.utils.Common;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class UserDetailsActivity extends BaseActivity implements OnMapReadyCallback{
    //public class UserDetailsActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    private EditText editTextId;
    private EditText editTextNombre;
    private EditText editTextApellido;
    private EditText editTextEdad;
    private EditText editTextFecNac;
    private EditText editTextAnioGraduacion;
    private EditText editTextCorreo;
    private EditText editTextTelefono;
    private EditText editTextCiudad;
    private EditText editTextPais;
    private Button buttonBack;
    private Persona persona;
    //
    ProgressDialog progressDialog;
    IGoogleSheets iGoogleSheets1;
    // de googlemaps
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        // Asigna los EditTexts a las variables de la clase
        editTextId = findViewById(R.id.editTextId);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido = findViewById(R.id.editTextApellido);
        editTextEdad = findViewById(R.id.editTextEdad);
        editTextFecNac = findViewById(R.id.editFecNac);
        editTextAnioGraduacion=findViewById(R.id.editTextAnioGraduacion);
        editTextCorreo=findViewById(R.id.editTextCorreo);
        editTextTelefono=findViewById(R.id.editTextTelefono);
        editTextCiudad=findViewById(R.id.editTextCiudadResidencia);
        editTextPais=findViewById(R.id.editTextPaisResidencia);

        iGoogleSheets1 = Common.iGSGetMethodClient1(Common.BASE_URL1);
        String pathUrl1;
        pathUrl1 = "exec?id=" + Common.getUsername().toString()+"&sheet=personas";



        //metodo que carga de la base de datos
        //Google maps
        SupportMapFragment mapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        String pathUrl;
        progressDialog = ProgressDialog.show(UserDetailsActivity.this,
                "Cargando resultados",
                "Espere por favor",
                true,
                false);
        try {
            //editTextId.setText(pathUrl1);

            iGoogleSheets1.getPeople(pathUrl1).enqueue(new Callback<String>() {

                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    try {

                        assert response.body() != null;
                        JSONObject responseObject = new JSONObject(response.body());
                        JSONArray peopleArray = responseObject.getJSONArray("persons");

                        //editTextId.setText(String.valueOf(peopleArray.length()));

                        JSONObject object = peopleArray.getJSONObject(0);

                        String id = object.getString("id");

                        String name = object.getString("nombre");

                        String surname = object.getString("apellido");
                        String fec_nac=object.getString("fec_nac");
                        String age = object.getString("edad");
                        String anio = object.getString("anio_graduacion");
                        String correo = object.getString("correo");
                        String telefono= object.getString("telefono");

                        String ciudad = object.getString("ciudad_residencia");
                        String pais = object.getString("pais_residencia");

                        String lati=object.getString("lat");
                        String longi=object.getString("log");
                        String lati2=lati.replace(",",".");
                        String longi2=longi.replace(",",".");

                        double latitud = Double.parseDouble(lati2);
                        double longitud = Double.parseDouble(longi2);

                        Common.setLat(latitud);
                        Common.setLog(longitud);
                        persona=new Persona(id,name,surname,fec_nac,age,anio,correo,telefono,ciudad,pais);
                        setPersonaView(persona);


                        onMapReady(mMap);
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


        // Asigna el Listener al botón de regreso
        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresar al MainActivity
                finish();
            }
        });
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;
        // Obtén las coordenadas desde Common
        double lat = Common.getLat();
        double lng = Common.getLog();

        // Verifica si las coordenadas son válidas (distintas de 0) antes de agregar el marcador y mover la cámara
        if (lat != 0 && lng != 0) {
            LatLng quito = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(quito).title("LUGAR DE RESIDENCIA"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(quito));
        }
    }
    public void setPersonaView(Persona persona){
        editTextId.setText(persona.getId());
        editTextNombre.setText(persona.getName());
        editTextApellido.setText(persona.getSurname());
        editTextFecNac.setText(persona.getFec_nac());
        editTextEdad.setText(persona.getAge());
        editTextAnioGraduacion.setText(persona.getAnio());
        editTextCorreo.setText(persona.getCorreo());
        editTextTelefono.setText(persona.getTelefono());
        editTextCiudad.setText(persona.getCiudad());
        editTextPais.setText(persona.getPais());
    }
}
