package com.example.pathfighter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreadorFichas extends AppCompatActivity {

    EditText editNombre;
    Spinner spinnerRazas;
    Spinner spinnerClases;
    TextView puntosRestantes;
    NumberPicker strengthPicker;
    NumberPicker dexterityPicker;
    NumberPicker constitutionPicker;
    NumberPicker intelligencePicker;
    NumberPicker wisdomPicker;
    NumberPicker charismaPicker;
    TextView modFuerza;
    TextView modDestreza;
    TextView modConstitucion;
    TextView modInteligencia;
    TextView modCarisma;
    TextView modSabiduria;
    TextView modAveriguar;
    TextView modEnganar;
    TextView modPercepcion;
    TextView modSigilo;
    Button crearFicha;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creador_fichas);
        editNombre=findViewById(R.id.editNombre);
        modFuerza= findViewById(R.id.modFuerza);
        modFuerza.setText("+1");
        modDestreza= findViewById(R.id.modDestreza);
        modDestreza.setText("+1");
        modConstitucion= findViewById(R.id.modConstitucion);
        modConstitucion.setText("+1");
        modInteligencia= findViewById(R.id.modInteligencia);
        modInteligencia.setText("+1");
        modCarisma= findViewById(R.id.modCarisma);
        modCarisma.setText("+1");
        modSabiduria= findViewById(R.id.modSabiduria);
        modSabiduria.setText("+1");
        modAveriguar = findViewById(R.id.modAveriguar);
        modEnganar = findViewById(R.id.modEnganar);
        modPercepcion = findViewById(R.id.modPercepcion);
        modSigilo = findViewById(R.id.modSigilo);
        puntosRestantes = findViewById(R.id.puntosRestantes);
        puntosRestantes.setText("15");
        crearFicha = findViewById(R.id.btnAddPj);
        id = getIntent().getIntExtra("id", -1);
        editNombre.setText("");


        spinnerClases = findViewById(R.id.spinnerClase);
        spinnerRazas = findViewById(R.id.spinnerRaza);
        String[] clases = {"Guerrero","Picaro","Mago"};
        String[] razas = {"Humano","Elfo","Enano"};
        ArrayAdapter<String> adapterClases = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, clases);
        ArrayAdapter<String> adapterRazas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, razas);
        spinnerClases.setAdapter(adapterClases);
        spinnerRazas.setAdapter(adapterRazas);


        strengthPicker = findViewById(R.id.strengthPicker);
        strengthPicker.setMinValue(7);
        strengthPicker.setMaxValue(18);
        strengthPicker.setValue(10);

        dexterityPicker = findViewById(R.id.dexterityPicker);
        dexterityPicker.setMinValue(7);
        dexterityPicker.setMaxValue(18);
        dexterityPicker.setValue(10);

        constitutionPicker = findViewById(R.id.constitutionPicker);
        constitutionPicker.setMinValue(7);
        constitutionPicker.setMaxValue(18);
        constitutionPicker.setValue(10);

        intelligencePicker = findViewById(R.id.intelligencePicker);
        intelligencePicker.setMinValue(7);
        intelligencePicker.setMaxValue(18);
        intelligencePicker.setValue(10);

        wisdomPicker = findViewById(R.id.wisdomPicker);
        wisdomPicker.setMinValue(7);
        wisdomPicker.setMaxValue(18);
        wisdomPicker.setValue(10);

        charismaPicker = findViewById(R.id.charismaPicker);
        charismaPicker.setMinValue(7);
        charismaPicker.setMaxValue(18);
        charismaPicker.setValue(10);


        strengthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int puntos = Integer.parseInt(puntosRestantes.getText().toString());
                int modificador;
                switch (oldVal){
                    case 7: puntos-=4;
                    break;
                    case 8: puntos-=2;
                        break;
                    case 9: puntos-=1;
                        break;
                    case 10: puntos+=0;
                        break;
                    case 11: puntos+=1;
                        break;
                    case 12: puntos+=2;
                        break;
                    case 13: puntos+=3;
                        break;
                    case 14: puntos+=5;
                        break;
                    case 15: puntos+=7;
                        break;
                    case 16: puntos+=10;
                        break;
                    case 17: puntos+=13;
                        break;
                    case 18: puntos+=17;
                        break;
                }
                switch (newVal){
                    case 7: puntos+=4;
                        break;
                    case 8: puntos+=2;
                        break;
                    case 9: puntos+=1;
                        break;
                    case 10: puntos+=0;
                        break;
                    case 11: puntos-=1;
                        break;
                    case 12: puntos-=2;
                        break;
                    case 13: puntos-=3;
                        break;
                    case 14: puntos-=5;
                        break;
                    case 15: puntos-=7;
                        break;
                    case 16: puntos-=10;
                        break;
                    case 17: puntos-=13;
                        break;
                    case 18: puntos-=17;
                        break;
                }
                puntosRestantes.setText(puntos+"");
                modificador=modCalc(newVal);

                if(spinnerRazas.getSelectedItem().toString().equals("Humano")) modificador++;
                if(modificador>=0){
                    modFuerza.setText("+" + modificador);
                }else{
                    modFuerza.setText("" + modificador);
                }


            }
        });
        dexterityPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int puntos = Integer.parseInt(puntosRestantes.getText().toString());
                int modificador;
                switch (oldVal){
                    case 7: puntos-=4;
                        break;
                    case 8: puntos-=2;
                        break;
                    case 9: puntos-=1;
                        break;
                    case 10: puntos+=0;
                        break;
                    case 11: puntos+=1;
                        break;
                    case 12: puntos+=2;
                        break;
                    case 13: puntos+=3;
                        break;
                    case 14: puntos+=5;
                        break;
                    case 15: puntos+=7;
                        break;
                    case 16: puntos+=10;
                        break;
                    case 17: puntos+=13;
                        break;
                    case 18: puntos+=17;
                        break;
                }
                switch (newVal){
                    case 7: puntos+=4;
                        break;
                    case 8: puntos+=2;
                        break;
                    case 9: puntos+=1;
                        break;
                    case 10: puntos+=0;
                        break;
                    case 11: puntos-=1;
                        break;
                    case 12: puntos-=2;
                        break;
                    case 13: puntos-=3;
                        break;
                    case 14: puntos-=5;
                        break;
                    case 15: puntos-=7;
                        break;
                    case 16: puntos-=10;
                        break;
                    case 17: puntos-=13;
                        break;
                    case 18: puntos-=17;
                        break;
                }
                puntosRestantes.setText(puntos+"");
                modificador=modCalc(newVal);

                if(spinnerRazas.getSelectedItem().toString().equals("Humano")) modificador++;
                if(spinnerRazas.getSelectedItem().toString().equals("Elfo")) modificador++;

                if(modificador>=0){
                    modDestreza.setText("+" + modificador);
                    modSigilo.setText("+" + modificador);
                }else{
                    modDestreza.setText("" + modificador);
                    modSigilo.setText("" + modificador);
                }


            }
        });
        constitutionPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int puntos = Integer.parseInt(puntosRestantes.getText().toString());
                int modificador;
                switch (oldVal){
                    case 7: puntos-=4;
                        break;
                    case 8: puntos-=2;
                        break;
                    case 9: puntos-=1;
                        break;
                    case 10: puntos+=0;
                        break;
                    case 11: puntos+=1;
                        break;
                    case 12: puntos+=2;
                        break;
                    case 13: puntos+=3;
                        break;
                    case 14: puntos+=5;
                        break;
                    case 15: puntos+=7;
                        break;
                    case 16: puntos+=10;
                        break;
                    case 17: puntos+=13;
                        break;
                    case 18: puntos+=17;
                        break;
                }
                switch (newVal){
                    case 7: puntos+=4;
                        break;
                    case 8: puntos+=2;
                        break;
                    case 9: puntos+=1;
                        break;
                    case 10: puntos+=0;
                        break;
                    case 11: puntos-=1;
                        break;
                    case 12: puntos-=2;
                        break;
                    case 13: puntos-=3;
                        break;
                    case 14: puntos-=5;
                        break;
                    case 15: puntos-=7;
                        break;
                    case 16: puntos-=10;
                        break;
                    case 17: puntos-=13;
                        break;
                    case 18: puntos-=17;
                        break;
                }
                puntosRestantes.setText(puntos+"");
                modificador=modCalc(newVal);

                if(spinnerRazas.getSelectedItem().toString().equals("Humano")) modificador++;
                if(spinnerRazas.getSelectedItem().toString().equals("Elfo")) modificador--;
                if(spinnerRazas.getSelectedItem().toString().equals("Enano")) modificador++;
                if(modificador>=0){
                    modConstitucion.setText("+" + modificador);
                }else{
                    modConstitucion.setText("" + modificador);
                }


            }
        });
        intelligencePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int puntos = Integer.parseInt(puntosRestantes.getText().toString());
                int modificador;
                switch (oldVal){
                    case 7: puntos-=4;
                        break;
                    case 8: puntos-=2;
                        break;
                    case 9: puntos-=1;
                        break;
                    case 10: puntos+=0;
                        break;
                    case 11: puntos+=1;
                        break;
                    case 12: puntos+=2;
                        break;
                    case 13: puntos+=3;
                        break;
                    case 14: puntos+=5;
                        break;
                    case 15: puntos+=7;
                        break;
                    case 16: puntos+=10;
                        break;
                    case 17: puntos+=13;
                        break;
                    case 18: puntos+=17;
                        break;
                }
                switch (newVal){
                    case 7: puntos+=4;
                        break;
                    case 8: puntos+=2;
                        break;
                    case 9: puntos+=1;
                        break;
                    case 10: puntos+=0;
                        break;
                    case 11: puntos-=1;
                        break;
                    case 12: puntos-=2;
                        break;
                    case 13: puntos-=3;
                        break;
                    case 14: puntos-=5;
                        break;
                    case 15: puntos-=7;
                        break;
                    case 16: puntos-=10;
                        break;
                    case 17: puntos-=13;
                        break;
                    case 18: puntos-=17;
                        break;
                }
                puntosRestantes.setText(puntos+"");
                modificador=modCalc(newVal);

                if(spinnerRazas.getSelectedItem().toString().equals("Humano")) modificador++;
                if(spinnerRazas.getSelectedItem().toString().equals("Elfo")) modificador++;

                if(modificador>=0){
                    modInteligencia.setText("+" + modificador);
                    modEnganar.setText("+" + modificador);
                }else{
                    modInteligencia.setText("" + modificador);
                    modEnganar.setText("" + modificador);
                }


            }
        });
        wisdomPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int puntos = Integer.parseInt(puntosRestantes.getText().toString());
                int modificador;
                switch (oldVal){
                    case 7: puntos-=4;
                        break;
                    case 8: puntos-=2;
                        break;
                    case 9: puntos-=1;
                        break;
                    case 10: puntos+=0;
                        break;
                    case 11: puntos+=1;
                        break;
                    case 12: puntos+=2;
                        break;
                    case 13: puntos+=3;
                        break;
                    case 14: puntos+=5;
                        break;
                    case 15: puntos+=7;
                        break;
                    case 16: puntos+=10;
                        break;
                    case 17: puntos+=13;
                        break;
                    case 18: puntos+=17;
                        break;
                }
                switch (newVal){
                    case 7: puntos+=4;
                        break;
                    case 8: puntos+=2;
                        break;
                    case 9: puntos+=1;
                        break;
                    case 10: puntos+=0;
                        break;
                    case 11: puntos-=1;
                        break;
                    case 12: puntos-=2;
                        break;
                    case 13: puntos-=3;
                        break;
                    case 14: puntos-=5;
                        break;
                    case 15: puntos-=7;
                        break;
                    case 16: puntos-=10;
                        break;
                    case 17: puntos-=13;
                        break;
                    case 18: puntos-=17;
                        break;
                }
                puntosRestantes.setText(puntos+"");
                modificador=modCalc(newVal);

                if(spinnerRazas.getSelectedItem().toString().equals("Humano")) modificador++;
                if(spinnerRazas.getSelectedItem().toString().equals("Enano")) modificador++;

                if(modificador>=0){
                    modSabiduria.setText("+" + modificador);
                }else{
                    modSabiduria.setText("" + modificador);
                }
                if(spinnerRazas.getSelectedItem().toString().equals("Elfo")){
                    if(modificador+2>=0){
                        modPercepcion.setText("+" + (modificador+2));
                    }else{
                        modPercepcion.setText("" + (modificador+2));
                    }
                }else{
                    if(modificador>=0){
                        modPercepcion.setText("+" + modificador);
                    }else{
                        modPercepcion.setText("" + modificador);
                    }
                }
                if(spinnerRazas.getSelectedItem().toString().equals("Enano")){
                    if(modificador+2>=0){
                        modAveriguar.setText("+" + (modificador+2));
                    }else{
                        modAveriguar.setText("" + (modificador+2));
                    }
                }else{
                    if(modificador>=0){
                        modAveriguar.setText("+" + modificador);
                    }else{
                        modAveriguar.setText("" + modificador);
                    }
                }


            }
        });
        charismaPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int puntos = Integer.parseInt(puntosRestantes.getText().toString());
                int modificador;
                switch (oldVal){
                    case 7: puntos-=4;
                        break;
                    case 8: puntos-=2;
                        break;
                    case 9: puntos-=1;
                        break;
                    case 10: puntos+=0;
                        break;
                    case 11: puntos+=1;
                        break;
                    case 12: puntos+=2;
                        break;
                    case 13: puntos+=3;
                        break;
                    case 14: puntos+=5;
                        break;
                    case 15: puntos+=7;
                        break;
                    case 16: puntos+=10;
                        break;
                    case 17: puntos+=13;
                        break;
                    case 18: puntos+=17;
                        break;
                }
                switch (newVal){
                    case 7: puntos+=4;
                        break;
                    case 8: puntos+=2;
                        break;
                    case 9: puntos+=1;
                        break;
                    case 10: puntos+=0;
                        break;
                    case 11: puntos-=1;
                        break;
                    case 12: puntos-=2;
                        break;
                    case 13: puntos-=3;
                        break;
                    case 14: puntos-=5;
                        break;
                    case 15: puntos-=7;
                        break;
                    case 16: puntos-=10;
                        break;
                    case 17: puntos-=13;
                        break;
                    case 18: puntos-=17;
                        break;
                }
                puntosRestantes.setText(puntos+"");
                modificador=modCalc(newVal);

                if(spinnerRazas.getSelectedItem().toString().equals("Humano")) modificador++;
                if(spinnerRazas.getSelectedItem().toString().equals("Enano")) modificador--;

                if(modificador>=0){
                    modCarisma.setText("+" + modificador);
                }else{
                    modCarisma.setText("" + modificador);
                }


            }
        });
        spinnerRazas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                int modificador;

                if (selectedItem.equals("Elfo")) {

                    modificador = modCalc(strengthPicker.getValue());
                    if(modificador>=0){
                        modFuerza.setText("+" + modificador);
                    }else{
                        modFuerza.setText("" + modificador);
                    }
                    modificador = modCalc(dexterityPicker.getValue())+1;
                    if(modificador>=0){
                        modDestreza.setText("+" + modificador);
                        modSigilo.setText("+" + modificador);
                    }else{
                        modDestreza.setText("" + modificador);
                        modSigilo.setText("" + modificador);
                    }

                    modificador = modCalc(constitutionPicker.getValue())-1;
                    if(modificador>=0){
                        modConstitucion.setText("+" + modificador);
                    }else{
                        modConstitucion.setText("" + modificador);
                    }modificador = modCalc(intelligencePicker.getValue())+1;
                    if(modificador>=0){
                        modInteligencia.setText("+" + modificador);
                        modEnganar.setText("+" + modificador);
                    }else{
                        modInteligencia.setText("" + modificador);
                        modEnganar.setText("" + modificador);
                    }
                    modificador = modCalc(wisdomPicker.getValue());
                    if(modificador>=0){
                        modSabiduria.setText("+" + modificador);
                        modAveriguar.setText("+" + (modificador));

                    }else{
                        modSabiduria.setText("" + modificador);
                        modAveriguar.setText("" + (modificador));

                    }
                    if(modificador+2>=0){
                        modPercepcion.setText("+" + (modificador+2));
                    }else{
                        modPercepcion.setText("" + (modificador+2));
                    }

                    modificador = modCalc(charismaPicker.getValue());
                    if(modificador>=0){
                        modCarisma.setText("+" + modificador);
                    }else{
                        modCarisma.setText("" + modificador);
                    }

                }else if(selectedItem.equals("Enano")){
                    modificador = modCalc(strengthPicker.getValue());
                    if(modificador>=0){
                        modFuerza.setText("+" + modificador);
                    }else{
                        modFuerza.setText("" + modificador);
                    }
                    modificador = modCalc(dexterityPicker.getValue());
                    if(modificador>=0){
                        modDestreza.setText("+" + modificador);
                        modSigilo.setText("+" + modificador);
                    }else{
                        modDestreza.setText("" + modificador);
                        modSigilo.setText("" + modificador);
                    }

                    modificador = modCalc(constitutionPicker.getValue())+1;
                    if(modificador>=0){
                        modConstitucion.setText("+" + modificador);
                    }else{
                        modConstitucion.setText("" + modificador);
                    }modificador = modCalc(intelligencePicker.getValue());
                    if(modificador>=0){
                        modInteligencia.setText("+" + modificador);
                        modEnganar.setText("+" + modificador);
                    }else{
                        modInteligencia.setText("" + modificador);
                        modEnganar.setText("" + modificador);
                    }
                    modificador = modCalc(wisdomPicker.getValue())+1;
                    if(modificador>=0){
                        modSabiduria.setText("+" + modificador);
                        modPercepcion.setText("+" + modificador);
                    }else{
                        modSabiduria.setText("" + modificador);
                        modPercepcion.setText("" + modificador);
                    }
                    if(modificador+2>=0){
                        modAveriguar.setText("+" + (modificador+2));
                    }else{
                        modAveriguar.setText("-" + (modificador+2));
                    }
                    modificador = modCalc(charismaPicker.getValue())-1;
                    if(modificador>=0){
                        modCarisma.setText("+" + modificador);
                    }else{
                        modCarisma.setText("" + modificador);
                    }

                }else if(selectedItem.equals("Humano")) {
                    modificador = modCalc(strengthPicker.getValue())+1;
                    if(modificador>=0){
                        modFuerza.setText("+" + modificador);
                    }else{
                        modFuerza.setText("" + modificador);
                    }
                    modificador = modCalc(dexterityPicker.getValue())+1;
                    if(modificador>=0){
                        modDestreza.setText("+" + modificador);
                        modSigilo.setText("+" + modificador);
                    }else{
                        modDestreza.setText("" + modificador);
                        modSigilo.setText("" + modificador);
                    }
                    modificador = modCalc(constitutionPicker.getValue())+1;
                    if(modificador>=0){
                        modConstitucion.setText("+" + modificador);
                    }else{
                        modConstitucion.setText("" + modificador);
                    }modificador = modCalc(intelligencePicker.getValue())+1;
                    if(modificador>=0){
                        modInteligencia.setText("+" + modificador);
                        modEnganar.setText("+" + modificador);
                    }else{
                        modInteligencia.setText("" + modificador);
                        modEnganar.setText("" + modificador);
                    }
                    modificador = modCalc(wisdomPicker.getValue())+1;
                    if(modificador>=0){
                        modSabiduria.setText("+" + modificador);
                        modPercepcion.setText("+" + modificador);
                        modAveriguar.setText(("+" + modificador));
                    }else{
                        modSabiduria.setText("" + modificador);
                        modPercepcion.setText("" + modificador);
                        modAveriguar.setText("" + modificador);
                    }
                    modificador = modCalc(charismaPicker.getValue())+1;
                    if(modificador>=0){
                        modCarisma.setText("+" + modificador);
                    }else{
                        modCarisma.setText("" + modificador);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        crearFicha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((editNombre.getText().toString()).equals("")){
                    Toast.makeText(getApplicationContext(), "El personaje debe tener nombre",Toast.LENGTH_SHORT).show();

                }else if(Integer.parseInt(puntosRestantes.getText().toString())<0){
                    Toast.makeText(getApplicationContext(), "No puedes tener puntos gratuitos negativos",Toast.LENGTH_SHORT).show();
                }else{
                    agregarPersonaje();
                    finish();
                }

            }
        });



        };



    public int modCalc(int valor){
        if(valor>=10){
            return (valor-10)/2;
        }else{
            return (valor-11)/2;
        }

    }
    public int quitarSuma(String numeroTexto){
        return Integer.parseInt(numeroTexto.substring(1));
    }
    public void agregarPersonaje(){
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
            PreparedStatement pst;

            //INSERT INTO ficha (userId,nombre,clase,nivel,raza,fuerza,destreza,constitucion,inteligencia,sabiduria,carisma,averiguar,engannar,sigilo,percepcion)	VALUES(1,'','picaro',1,'Humano',1,2,2,1,3,4,1,3,2,4)

            pst=conexion.prepareStatement("INSERT INTO ficha (userId,nombre,clase,nivel,raza,fuerza,destreza,constitucion,inteligencia,sabiduria,carisma,averiguar,engannar,sigilo,percepcion)" +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pst.setInt(1,id);
            pst.setString(2,editNombre.getText().toString());
            pst.setString(3,spinnerClases.getSelectedItem().toString());
            pst.setInt(4,1);
            pst.setString(5,spinnerRazas.getSelectedItem().toString());
            pst.setInt(6,quitarSuma(modFuerza.getText().toString()));
            pst.setInt(7,quitarSuma(modDestreza.getText().toString()));
            pst.setInt(8,quitarSuma(modConstitucion.getText().toString()));
            pst.setInt(9,quitarSuma(modInteligencia.getText().toString()));
            pst.setInt(10,quitarSuma(modSabiduria.getText().toString()));
            pst.setInt(11,quitarSuma(modCarisma.getText().toString()));
            pst.setInt(12,quitarSuma(modAveriguar.getText().toString()));
            pst.setInt(13,quitarSuma(modEnganar.getText().toString()));
            pst.setInt(14,quitarSuma(modSigilo.getText().toString()));
            pst.setInt(15,quitarSuma(modPercepcion.getText().toString()));
            pst.executeUpdate();

            Toast.makeText(getApplicationContext(), "PERSONAJE AGREGADO CORRECTAMENTE",Toast.LENGTH_SHORT).show();


        }catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e){
            Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }


}