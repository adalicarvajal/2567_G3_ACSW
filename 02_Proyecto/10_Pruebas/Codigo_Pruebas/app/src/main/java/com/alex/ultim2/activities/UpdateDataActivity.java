package com.alex.ultim2.activities;


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alex.ultim2.R;
import com.alex.ultim2.models.IGoogleSheets;
import com.alex.ultim2.models.People;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class UpdateDataActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener {

    // Declare the views
    private EditText editTextId;
    private EditText editTextNombre;
    private EditText editTextApellido;
    private EditText editTextEdad;
    private EditText editTextLat;
    private EditText editTextLong;
    private EditText editTextFecNac;
    private EditText editTextAnioGraduacion;
    private EditText editTextCorreo;
    private EditText editTextTelefono;

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;

    private Button buttonSave;
    private Button buttonCancel;
    GoogleMap mMap;
    //recuperar datos
    IGoogleSheets iGoogleSheets1;

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_data_activity_layout);
// En tu actividad, por ejemplo, en el método onCreate
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }

        // Initialize the views
        editTextId=findViewById(R.id.editTextId);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido = findViewById(R.id.editTextApellido);
        editTextEdad = findViewById(R.id.editTextEdad);
        buttonSave = findViewById(R.id.buttonSave);
        buttonCancel=findViewById(R.id.buttonCancel);

        editTextFecNac = findViewById(R.id.editFecNac);
        editTextAnioGraduacion=findViewById(R.id.editTextAnioGraduacion);
        editTextCorreo=findViewById(R.id.editTextCorreo);
        editTextTelefono=findViewById(R.id.editTextTelefono);

        editTextLat=findViewById(R.id.editTextLat);
        editTextLong=findViewById(R.id.editTextLong);
        //calendario
        calendar = Calendar.getInstance();
        // Mostrar el DatePickerDialog cuando el usuario haga clic en el EditText
        editTextAnioGraduacion.setOnClickListener(v -> showYearPickerDialog());

        editTextFecNac.setOnClickListener(v -> showDatePickerDialog());
        // Agregar un TextWatcher al EditText para escuchar los cambios en el texto
        // Limitar el número de caracteres a 10 en el EditText de teléfono
        editTextTelefono.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        // Permitir solo números en el EditText de teléfono
        editTextTelefono.setInputType(InputType.TYPE_CLASS_NUMBER);

        // Agregar un TextWatcher al EditText de correo para validar el correo en tiempo real
        editTextCorreo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Validar si el texto ingresado es una dirección de correo electrónico válida
                boolean isEmailValid = isValidEmail(editable.toString());

                if (isEmailValid) {
                    // El correo electrónico es válido, limpiamos el error si estaba presente
                    editTextCorreo.setError(null);
                } else {
                    // El correo electrónico no es válido, mostramos un mensaje de error
                    editTextCorreo.setError("Correo electrónico inválido");
                }
                // Habilitar o deshabilitar el botón según la validez del correo y el teléfono
                enableSaveButton(isEmailValid, isValidPhone(editTextTelefono.getText().toString()));
            }
        });

        // Agregar un TextWatcher al EditText de teléfono para validar el teléfono en tiempo real
        editTextTelefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Validar si el texto ingresado es un teléfono válido
                boolean isPhoneValid = isValidPhone(editable.toString());

                if (isPhoneValid) {
                    // El teléfono es válido, limpiamos el error si estaba presente
                    editTextTelefono.setError(null);
                } else {
                    // El teléfono no es válido, mostramos un mensaje de error
                    editTextTelefono.setError("Teléfono inválido");
                }
                // Habilitar o deshabilitar el botón según la validez del correo y el teléfono
                enableSaveButton(isValidEmail(editTextCorreo.getText().toString()), isPhoneValid);
            }
        });

        // Inicialmente, el botón debe estar deshabilitado hasta que se ingresen un correo y teléfono válidos
        enableSaveButton(false, false);

        //metodo que carga de la base de datos
        //Google maps
        SupportMapFragment mapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        LoadData();


        // Set a click listener on the Save button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = editTextCorreo.getText().toString();
                String celular = editTextTelefono.getText().toString();
                if (isValidEmail(email)) {
                    saveUpdatedData();
                    Intent intent = new Intent(UpdateDataActivity.this, LoginActivity.class);
                    startActivity(intent);
                    // Finish the activity to return to the previous screen
                    finish();
                } else {
                    // El correo electrónico no es válido, muestra un mensaje de error o realiza otra acción
                    editTextCorreo.setError("Correo electrónico inválido");
                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });

        Button btnMyLocation = findViewById(R.id.btnSelectCurrentLocation);
        btnMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                centerMapOnMyLocation();
            }
        });
    }


    // Method to save the updated data to your database or storage
    private void saveUpdatedData() {
        // Retrieve the updated data from the EditText fields
        ProgressDialog progressDialog = ProgressDialog.show(UpdateDataActivity.this,
                "Actualizando datos",
                "Espere por favor",
                true,
                false);

        String nombre = editTextNombre.getText().toString();
        String apellido = editTextApellido.getText().toString();

        String anio_graduacion=editTextAnioGraduacion.getText().toString();
        String telefono=editTextTelefono.getText().toString();
        String correo=editTextCorreo.getText().toString();

        String fec_nac=editTextFecNac.getText().toString();
        String latitud=editTextLat.getText().toString();
        String longitud=editTextLong.getText().toString();
        String lati=latitud.replace(".",",");
        String longi=longitud.replace(".",",");
        String sheet="personas";




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
                        "    \"field\": \"" + "nombre" + "\",\n" +
                        "    \"value\": \"" + nombre + "\"\n" +
                        "}";
                Call<String> call = iGoogleSheets.getStringRequestBody(jsonRequest);

                Response<String> response = call.execute();
                int code = response.code();
                if (code == 200) {
                    jsonRequest = "{\n" +
                            "    \"sheet\": \"" + sheet + "\",\n" +
                            "    \"id\": \"" + Common.getUsername() + "\",\n" +
                            "    \"field\": \"" + "apellido" + "\",\n" +
                            "    \"value\": \"" + apellido + "\"\n" +
                            "}";
                    call = iGoogleSheets.getStringRequestBody(jsonRequest);
                    response = call.execute();
                    code = response.code();
                    if(code==200){
                        jsonRequest = "{\n" +
                                "    \"sheet\": \"" + sheet + "\",\n" +
                                "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                "    \"field\": \"" + "lat" + "\",\n" +
                                "    \"value\": \"" + lati + "\"\n" +
                                "}";
                        call = iGoogleSheets.getStringRequestBody(jsonRequest);
                        response = call.execute();
                        code = response.code();
                        if(code==200){
                            jsonRequest = "{\n" +
                                    "    \"sheet\": \"" + sheet + "\",\n" +
                                    "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                    "    \"field\": \"" + "log" + "\",\n" +
                                    "    \"value\": \"" + longi + "\"\n" +
                                    "}";
                            call = iGoogleSheets.getStringRequestBody(jsonRequest);
                            response = call.execute();
                            code = response.code();
                            if(code==200){
                                jsonRequest = "{\n" +
                                        "    \"sheet\": \"" + sheet + "\",\n" +
                                        "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                        "    \"field\": \"" + "fec_nac" + "\",\n" +
                                        "    \"value\": \"" + fec_nac + "\"\n" +
                                        "}";
                                call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                response = call.execute();
                                code = response.code();
                                if(code==200){
                                    jsonRequest = "{\n" +
                                            "    \"sheet\": \"" + sheet + "\",\n" +
                                            "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                            "    \"field\": \"" + "anio_graduacion" + "\",\n" +
                                            "    \"value\": \"" + anio_graduacion + "\"\n" +
                                            "}";
                                    call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                    response = call.execute();
                                    code = response.code();
                                    if(code==200){
                                        jsonRequest = "{\n" +
                                                "    \"sheet\": \"" + sheet + "\",\n" +
                                                "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                "    \"field\": \"" + "correo" + "\",\n" +
                                                "    \"value\": \"" + correo + "\"\n" +
                                                "}";
                                        call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                        response = call.execute();
                                        code = response.code();
                                        if(code==200){
                                            jsonRequest = "{\n" +
                                                    "    \"sheet\": \"" + sheet + "\",\n" +
                                                    "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                    "    \"field\": \"" + "telefono" + "\",\n" +
                                                    "    \"value\": \"" + telefono + "\"\n" +
                                                    "}";
                                            call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                            response = call.execute();
                                            code = response.code();
                                            if(code==200){
                                                Common.setLat(Double.parseDouble(latitud));
                                                Common.setLog(Double.parseDouble(longitud));
                                                return;
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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap=googleMap;
        this.mMap.setOnMapClickListener(this);
        this.mMap.setOnMapLongClickListener(this);

        //LatLng quito=new LatLng(-0.2144903,-78.4184578);
        LatLng quito=new LatLng(Common.getLat(),Common.getLog());
        mMap.addMarker(new MarkerOptions().position(quito).title("LUGAR DE RESIDENCIA"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(quito));
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        editTextLat.setText(String.valueOf(latLng.latitude));
        editTextLong.setText(String.valueOf(latLng.longitude));

        mMap.clear();
        LatLng mexico = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(mexico).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mexico));

    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        editTextLat.setText(String.valueOf(latLng.latitude));
        editTextLong.setText(String.valueOf(latLng.longitude));

        mMap.clear();
        LatLng mexico = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(mexico).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mexico));

    }
    private void centerMapOnMyLocation() {
        // Verificar permisos de ubicación
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Obtener la ubicación actual
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                LatLng currentLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                editTextLat.setText(String.valueOf(currentLocation.latitude));
                editTextLong.setText(String.valueOf(currentLocation.longitude));
            }
        } else {
            // Solicitar permisos de ubicación si no están otorgados
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }

    public void LoadData(){
        ProgressDialog progressDialog = ProgressDialog.show(UpdateDataActivity.this,
                "Cargando datos",
                "Espere por favor",
                true,
                false);

        iGoogleSheets1 = Common.iGSGetMethodClient1(Common.BASE_URL1);
        String pathUrl1;
        pathUrl1 = "exec?id=" + Common.getUsername().toString()+"&sheet=personas";
        List<People> peopleList = new ArrayList<>();
        try {
            iGoogleSheets1.getPeople(pathUrl1).enqueue(new Callback<String>() {

                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    try {

                        assert response.body() != null;
                        JSONObject responseObject = new JSONObject(response.body());
                        JSONArray peopleArray = responseObject.getJSONArray("persons");
                        JSONObject object = peopleArray.getJSONObject(0);
                        String id = object.getString("id");
                        String name = object.getString("nombre");
                        String surname = object.getString("apellido");
                        String age = object.getString("edad");
                        String fec_nac=object.getString("fec_nac");
                        String anio = object.getString("anio_graduacion");
                        String correo = object.getString("correo");
                        String telefono= object.getString("telefono");
                        String lati=object.getString("lat");
                        String longi=object.getString("log");
                        String lati2=lati.replace(",",".");
                        String longi2=longi.replace(",",".");

                        double latitud = Double.parseDouble(lati2);
                        double longitud = Double.parseDouble(longi2);
                        //Common.setLat(-0.9339953);
                        //Common.setLog(-78.6248696);
                        Common.setLat(latitud);
                        Common.setLog(longitud);


                        People people = new People(id, name, surname, age);
                        peopleList.add(people);

                        editTextId.setText(id);
                        editTextNombre.setText(name);
                        editTextApellido.setText(surname);
                        editTextEdad.setText(age);
                        editTextFecNac.setText(fec_nac);

                        editTextAnioGraduacion.setText(anio);
                        editTextCorreo.setText(correo);
                        editTextTelefono.setText(telefono);
                        editTextLat.setText(latitud+"");
                        editTextLong.setText(longitud+"");

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

    }

    private void showDatePickerDialog() {
        final int añoActual = calendar.get(Calendar.YEAR);
        final int mesActual = calendar.get(Calendar.MONTH);
        final int diaActual = calendar.get(Calendar.DAY_OF_MONTH);

        // Crear una instancia de DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Cuando el usuario selecciona una fecha, actualiza el EditText con la fecha seleccionada
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String selectedDate = sdf.format(calendar.getTime());
                    editTextFecNac.setText(selectedDate);
                },
                añoActual, mesActual, diaActual);

        // Calcular la fecha máxima permitida: hoy
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -18);

        long fechaMaxima = calendar.getTimeInMillis();
        datePickerDialog.getDatePicker().setMaxDate(fechaMaxima);

        // Escuchar cada vez que el usuario cambie la fecha
        datePickerDialog.getDatePicker().init(añoActual, mesActual, diaActual, (view, year, month, dayOfMonth) -> {
            // Calcular la fecha mínima permitida: 50 años atrás desde la fecha seleccionada
            Calendar minDate = Calendar.getInstance();
            minDate.set(year - 50, month, dayOfMonth);
            long fechaMinima = minDate.getTimeInMillis();
            datePickerDialog.getDatePicker().setMinDate(fechaMinima);
        });

        // Mostrar el DatePickerDialog
        datePickerDialog.show();
    }
    private void showYearPickerDialog() {
        final int añoActual = calendar.get(Calendar.YEAR);

        // Crear una instancia de DatePickerDialog con el modo spinner (para mostrar los años desplegables)
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Cuando el usuario selecciona un año, actualiza el EditText con el año seleccionado
                    calendar.set(Calendar.YEAR, year);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.getDefault());
                    String selectedYear = sdf.format(calendar.getTime());
                    editTextAnioGraduacion.setText(selectedYear);
                },
                añoActual, 0, 1); // El mes y día seleccionados son irrelevantes en este caso

        // Establecer la fecha máxima permitida: hoy
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        // Establecer la fecha mínima permitida: 100 años atrás desde hoy
        Calendar minDate = Calendar.getInstance();
        minDate.set(añoActual - 100, 0, 1);
        datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());

        // Mostrar el DatePickerDialog
        datePickerDialog.show();
    }

    // Función para verificar si una cadena contiene solo números
    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return Pattern.matches(emailPattern, email);
    }

    // Función para verificar si una cadena contiene solo números y tiene 10 caracteres
    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }

    // Función para habilitar o deshabilitar el botón según la validez del correo y el teléfono
    private void enableSaveButton(boolean isEmailValid, boolean isPhoneValid) {
        buttonSave.setEnabled(isEmailValid && isPhoneValid);
    }


}