package com.example.pathfighter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListadoEnemigos extends AppCompatActivity {
    List<ListElement> elements;
    int id;
    int idPJ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_enemigos);
        id = getIntent().getIntExtra("id", -1);
        idPJ = getIntent().getIntExtra("idPJ", -1);
        init();
    }

    private void init() {
        elements = new ArrayList<>();
        elements.add(new ListElement("add", "Nuevo personaje", "", "",0));
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
            PreparedStatement pst;
            pst = conexion.prepareStatement("SELECT nombre, (SELECT usuario FROM usuarios WHERE id = ?), nivel, clase,id FROM ficha WHERE userId=?");
            pst.setInt(1, id);
            pst.setInt(2, id);
            ResultSet set = pst.executeQuery();


            while (set.next()) {
                elements.add(new ListElement(set.getString(4), set.getString(1), set.getString(2), "Lvl. " + set.getInt(3),set.getInt(5)));
            }
            for (int i = 0; i < 10; i++) {
                String consulta = "SELECT nombre, (SELECT usuario FROM usuarios WHERE codigoAmigo = (SELECT amigo" + i + " FROM usuarios WHERE id = ?)), nivel, clase, id FROM ficha WHERE userId = (SELECT id FROM usuarios WHERE codigoAmigo = (SELECT amigo" + i + " FROM usuarios WHERE id = ?))";
                pst = conexion.prepareStatement(consulta);
                pst.setInt(1, id);
                pst.setInt(2, id);
                set = pst.executeQuery();
                while (set.next()) {
                    elements.add(new ListElement(set.getString(4), set.getString(1), set.getString(2), "Lvl. " + set.getInt(3),set.getInt(5)));
                }

            }


        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            Log.e("Error", e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        ListAdapter listAdapter = new ListAdapter(elements, this);
        RecyclerView recyclerView = findViewById(R.id.RecyclerEnemigos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
        listAdapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selection = recyclerView.getChildAdapterPosition(view);

                Intent intent = new Intent(view.getContext(), Combate.class);
                intent.putExtra("idEnemigo",elements.get(selection).getId());
                intent.putExtra("idPJ", idPJ);
                startActivity(intent);

            }
        });

    }
}