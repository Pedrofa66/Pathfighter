package com.example.pathfighter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListadoFichas extends AppCompatActivity {

    List<ListElement> elements;
    Button botonAmigo;
    TextView codigoAmigo;
    EditText nuevoCodigoAmigo;
    int id;

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_fichas);
        codigoAmigo = findViewById(R.id.codigoAmigo);
        nuevoCodigoAmigo = findViewById(R.id.nuevoCodigoAmigo);
        botonAmigo = findViewById(R.id.botonAmigo);
        id = getIntent().getIntExtra("id", -1);


        init();
    }


    public void init() {
        elements = new ArrayList<>();
        elements.add(new ListElement("add", "Nuevo personaje", "", "",0));
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
            PreparedStatement pst;
            pst = conexion.prepareStatement("SELECT codigoAmigo FROM usuarios WHERE id = ?");
            pst.setInt(1,id);
            ResultSet set = pst.executeQuery();
            set.next();
            codigoAmigo.setText("Codigo Amigo: "+set.getString(1));
            botonAmigo.setText("Añadir Amigo");
            pst = conexion.prepareStatement("SELECT nombre, (SELECT usuario FROM usuarios WHERE id = ?), nivel, clase, id FROM ficha WHERE userId=?");
            pst.setInt(1, id);
            pst.setInt(2, id);
            set = pst.executeQuery();


            while (set.next()) {
                elements.add(new ListElement(set.getString(4), set.getString(1), set.getString(2), "Lvl. " + set.getInt(3), set.getInt(5)));
            }


        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            Log.e("Error", e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        ListAdapter listAdapter = new ListAdapter(elements, this);
        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

        botonAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int i;
                    boolean hueco=false;
                    String huecoAmigo="";
                    boolean loTienes=false;
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                    Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
                    PreparedStatement pst;
                    int codigoAmigo=Integer.parseInt(nuevoCodigoAmigo.getText().toString());
                    for(i = 0; i<10 &&!hueco &&!loTienes; i++ ){
                        huecoAmigo="amigo" + i;
                        pst = conexion.prepareStatement("SELECT " + huecoAmigo + " FROM usuarios WHERE id = ?");
                        pst.setInt(1, id);
                        ResultSet set =pst.executeQuery();
                        set.next();
                        if(set.getInt(1)==codigoAmigo){
                            loTienes=true;
                        }else{
                            if(set.getInt(1)<28000){
                                hueco= true;
                            }
                        }

                    }
                    if(hueco){
                        pst = conexion.prepareStatement("update usuarios  set "+ huecoAmigo +" = ? WHERE id = ?");
                        pst.setInt(1, codigoAmigo);
                        pst.setInt(2, id);
                        pst.executeUpdate();
                    }else{
                        Toast.makeText(getApplicationContext(), "No te queda hueco, nunca pensé que tuvieses más de 10 amigos", Toast.LENGTH_SHORT).show();
                    }


                } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });

        listAdapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selection = recyclerView.getChildAdapterPosition(view);
                if (selection == 0) {
                    Intent intent = new Intent(view.getContext(), CreadorFichas.class);
                    intent.putExtra("id", id);
                    startActivityForResult(intent, 1);
                } else {
                    Intent intent = new Intent(view.getContext(), FichaPersonaje.class);
                    intent.putExtra("id",id);
                    intent.putExtra("nombrePJ", elements.get(selection).getNombre());
                    intent.putExtra("nombreUsuario", elements.get(selection).getUsuario());
                    intent.putExtra("idPJ",elements.get(selection).getId());
                    startActivity(intent);
                }
            }
        });


    }
}