package com.example.pathfighter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExtraClases extends AppCompatActivity {
    Button returnExtraclases;
    Button returnExtraCLases_guerrero;
    ImageView levelsExtra;
    ImageView levelsExtra_guerrero;
    String clase;
    int idFicha;
    Luchador luchador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clase = getIntent().getStringExtra("clase");
        idFicha = getIntent().getIntExtra("id",-1);

        if (clase.equals("Guerrero")) {
            setContentView(R.layout.activity_extra_clases_guerrero);
            levelsExtra_guerrero = findViewById(R.id.levelsExtra_guerrero);
            returnExtraCLases_guerrero = findViewById(R.id.returnExtraClases_guerrero);
            String[] dotes = {"Dotes", "Ataque poderoso", "Combate con dos armas", "Combate con dos armas mejorado", "Combate con dos armas mayor", "Defensa con dos armas", "Doble tajo", "Soltura con escudo", "Soltura mayor con escudo"
                    , "Disparo a bocajarro", "Esquiva", "Golpe arcano", "Iniciativa mejorada", "Pericias en combate", "Finta mejorada", "Finta mayor", "Soltura con un arma", "Soltura mayor con un arma", "Especialización con un arma"
                    , "Especialización mayor con un arma", "Sutileza con las armas"};
            Spinner dote1 = findViewById(R.id.dote6);
            Spinner dote2 = findViewById(R.id.dote7);
            Spinner dote3 = findViewById(R.id.dote8);
            Spinner dote4 = findViewById(R.id.dote9);
            Spinner dote5 = findViewById(R.id.dote10);
            ArrayAdapter<String> adapterDotes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dotes);
            dote1.setAdapter(adapterDotes);
            dote2.setAdapter(adapterDotes);
            dote3.setAdapter(adapterDotes);
            dote4.setAdapter(adapterDotes);
            dote5.setAdapter(adapterDotes);
            luchador = new Luchador(idFicha);
            dote1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        int resultado = luchador.puedeDote(position);
                        if (resultado == 1 && !luchador.comprobarDote(position)) {
                            try {
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);
                                Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                                Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
                                PreparedStatement pst;
                                pst = conexion.prepareStatement("update ficha  set dote0 = ? WHERE id = ?");
                                pst.setInt(1, position);
                                pst.setInt(2, idFicha);
                                pst.executeUpdate();

                            } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            if (resultado == -1) {
                                Toast.makeText(getApplicationContext(), "No cumples los requisitos para la dote", Toast.LENGTH_SHORT).show();
                                dote1.setSelection(0);
                            }
                        }


                    } else {
                        try {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                            Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
                            PreparedStatement pst;
                            pst = conexion.prepareStatement("update ficha  set dote5 = NULL WHERE id = ?");
                            pst.setInt(1, idFicha);
                            pst.executeUpdate();

                        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }


                    }

                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            dote2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        int resultado = luchador.puedeDote(position);
                        if (resultado == 1 && !luchador.comprobarDote(position)) {
                            try {
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);
                                Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                                Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
                                PreparedStatement pst;
                                pst = conexion.prepareStatement("update ficha  set dote6 = ? WHERE id = ?");
                                pst.setInt(1, position);
                                pst.setInt(2, idFicha);
                                pst.executeUpdate();

                            } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            if (resultado == -1) {
                                Toast.makeText(getApplicationContext(), "No cumples los requisitos para la dote", Toast.LENGTH_SHORT).show();
                                dote2.setSelection(0);
                            }
                        }

                    } else {
                        try {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                            Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
                            PreparedStatement pst;
                            pst = conexion.prepareStatement("update ficha  set dote1 = NULL WHERE id = ?");
                            pst.setInt(1, idFicha);
                            pst.executeUpdate();

                        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            dote3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        int resultado = luchador.puedeDote(position);
                        if (resultado == 1 && !luchador.comprobarDote(position)) {
                            try {
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);
                                Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                                Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
                                PreparedStatement pst;
                                pst = conexion.prepareStatement("update ficha  set dote7 = ? WHERE id = ?");
                                pst.setInt(1, position);
                                pst.setInt(2, idFicha);
                                pst.executeUpdate();

                            } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            if (resultado == -1) {
                                Toast.makeText(getApplicationContext(), "No cumples los requisitos para la dote", Toast.LENGTH_SHORT).show();
                                dote3.setSelection(0);
                            }
                        }

                    } else {
                        try {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                            Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
                            PreparedStatement pst;
                            pst = conexion.prepareStatement("update ficha  set dote2 = NULL WHERE id = ?");
                            pst.setInt(1, idFicha);
                            pst.executeUpdate();

                        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            dote4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        int resultado = luchador.puedeDote(position);
                        if (resultado == 1 && !luchador.comprobarDote(position)) {
                            try {
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);
                                Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                                Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
                                PreparedStatement pst;
                                pst = conexion.prepareStatement("update ficha  set dote8 = ? WHERE id = ?");
                                pst.setInt(1, position);
                                pst.setInt(2, idFicha);
                                pst.executeUpdate();

                            } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            if (resultado == -1) {
                                Toast.makeText(getApplicationContext(), "No cumples los requisitos para la dote", Toast.LENGTH_SHORT).show();
                                dote4.setSelection(0);
                            }
                        }

                    } else {
                        try {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                            Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
                            PreparedStatement pst;
                            pst = conexion.prepareStatement("update ficha  set dote3 = NULL WHERE id = ?");
                            pst.setInt(1, idFicha);
                            pst.executeUpdate();

                        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            dote5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        int resultado = luchador.puedeDote(position);
                        if (resultado == 1 && !luchador.comprobarDote(position)) {
                            try {
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);
                                Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                                Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
                                PreparedStatement pst;
                                pst = conexion.prepareStatement("update ficha  set dote9 = ? WHERE id = ?");
                                pst.setInt(1, position);
                                pst.setInt(2, idFicha);
                                pst.executeUpdate();

                            } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            if (resultado == -1) {
                                Toast.makeText(getApplicationContext(), "No cumples los requisitos para la dote", Toast.LENGTH_SHORT).show();
                                dote5.setSelection(0);
                            }
                        }

                    } else {
                        try {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                            Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
                            PreparedStatement pst;
                            pst = conexion.prepareStatement("update ficha  set dote4 = NULL WHERE id = ?");
                            pst.setInt(1, idFicha);
                            pst.executeUpdate();


                        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            returnExtraCLases_guerrero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        } else {
            setContentView(R.layout.activity_extra_clases);
            levelsExtra = findViewById(R.id.levelsExtra);
            returnExtraclases = findViewById(R.id.returnExtraClases);
            if (clase.equals("Mago")) {
                levelsExtra.setImageResource(R.drawable.mago);
            } else {
                levelsExtra.setImageResource(R.drawable.picaro);
            }

            returnExtraclases.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

    }


}