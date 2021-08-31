package com.example.pathfighter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.Provider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    EditText editUsuario, editPass;
    Button btnAddUser;
    Button btnLogin;
    private String connectionUrl = "jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUsuario = (EditText) findViewById(R.id.editUsuario);
        editPass = (EditText) findViewById(R.id.editPass);
        btnAddUser = (Button) findViewById(R.id.btnAddUser);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarUsuario();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Log.i("Entra en Login", "Entra en Login");
                iniciarSesion();
            }
        });
    }

    public void agregarUsuario() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            Connection conexion = DriverManager.getConnection(connectionUrl);
            String usuario = editUsuario.getText().toString();
            String password = editPass.getText().toString();
            PreparedStatement pst;
            pst = conexion.prepareStatement("Select * from usuarios where usuario=?");
            pst.setString(1, usuario);
            ResultSet set = pst.executeQuery();

            if (set.next()) {
                Toast.makeText(getApplicationContext(), "Ya existe un usuario con ese nombre", Toast.LENGTH_SHORT).show();
                editUsuario.setText("");
                editPass.setText("");
            } else {
                pst = conexion.prepareStatement("INSERT INTO usuarios(usuario,contrasenna) VALUES(?,?)");
                pst.setString(1, usuario);
                pst.setString(2, password);
                pst.executeUpdate();
                pst = conexion.prepareStatement("UPDATE usuarios SET codigoAmigo=id+28732 WHERE usuario=? AND contrasenna=?");
                pst.setString(1, usuario);
                pst.setString(2, password);
                pst.executeUpdate();
                Toast.makeText(getApplicationContext(), "Registro completado con éxito", Toast.LENGTH_SHORT).show();
            }


        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    public void iniciarSesion() {

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            Connection conexion = DriverManager.getConnection(connectionUrl);
            String usuario = editUsuario.getText().toString();
            String password = editPass.getText().toString();
            PreparedStatement pst;


            pst = conexion.prepareStatement("Select * from usuarios where usuario=? and contrasenna=?");
            pst.setString(1, usuario);
            pst.setString(2, password);
            ResultSet set = pst.executeQuery();
            if (set.next()) {
                Intent intent = new Intent(this, ListadoFichas.class);
                intent.putExtra("id", set.getInt(1));
                conexion.close();
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Usuario o contraseña erroneos", Toast.LENGTH_SHORT).show();
            }


        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
}