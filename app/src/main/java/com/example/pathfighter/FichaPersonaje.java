package com.example.pathfighter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FichaPersonaje extends AppCompatActivity {

    ImageView iconImageView;
    ImageView levelUp;
    TextView nombrePersonaje;
    TextView nivelPersonaje;
    TextView fueFicha;
    TextView desFicha;
    TextView conFicha;
    TextView intFicha;
    TextView sabFicha;
    TextView carFicha;
    TextView averiguarFicha;
    TextView enganarFicha;
    TextView percepcionFicha;
    TextView sigiloFicha;
    TextView armadura;
    TextView armaduraToque;
    TextView armaduraDesprevenido;
    TextView sFortaleza;
    TextView sReflejos;
    TextView sVoluntad;
    TextView pGolpe;
    TextView ataque1;
    TextView ataque2;
    TextView ataque3;
    TextView ataque4;
    TextView ataqueM1;
    TextView ataqueM2;
    TextView ataqueM3;
    View layoutAtaque1;
    View layoutAtaque2;
    View layoutAtaque3;
    View layoutAtaque4;
    View layoutAtaqueM1;
    View layoutAtaqueM2;
    View layoutAtaqueM3;
    Spinner spinnerArma1;
    Spinner spinnerArma2;
    Spinner dote1;
    Spinner dote2;
    Spinner dote3;
    Spinner dote4;
    Spinner dote5;
    Button caracteristicasClase;
    Button borrarPersonaje;
    Button guardarPersonaje;
    Button luchar;
    String nombrePJ;
    String nombreUsuario;
    Luchador luchador;
    int idFicha;
    int id;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_personaje);
        nombrePJ = getIntent().getStringExtra("nombrePJ");
        nombreUsuario = getIntent().getStringExtra("nombreUsuario");
        id = getIntent().getIntExtra("id",-1);
        idFicha = getIntent().getIntExtra("idPJ", -1);
        iconImageView = findViewById(R.id.iconImageView);
        levelUp = findViewById(R.id.levelUp);
        nombrePersonaje = findViewById(R.id.nombrePersonaje);
        nivelPersonaje = findViewById(R.id.nivelPersonaje);
        fueFicha = findViewById(R.id.fueFicha);
        desFicha = findViewById(R.id.desFicha);
        conFicha = findViewById(R.id.conFicha);
        intFicha = findViewById(R.id.intFicha);
        sabFicha = findViewById(R.id.sabFicha);
        carFicha = findViewById(R.id.carFicha);
        averiguarFicha = findViewById(R.id.averiguarFicha);
        enganarFicha = findViewById(R.id.enganarFicha);
        percepcionFicha = findViewById(R.id.percepcionFicha);
        sigiloFicha = findViewById(R.id.sigiloFicha);
        armadura = findViewById(R.id.armadura);
        armaduraToque = findViewById(R.id.armaduraToque);
        armaduraDesprevenido = findViewById(R.id.armaduraDesprevenido);
        sFortaleza = findViewById(R.id.sFortaleza);
        sReflejos = findViewById(R.id.sReflejos);
        sVoluntad = findViewById(R.id.sVoluntad);
        pGolpe = findViewById(R.id.pGolpe);
        ataque1 = findViewById(R.id.ataque1);
        ataque2 = findViewById(R.id.ataque2);
        ataque3 = findViewById(R.id.ataque3);
        ataque4 = findViewById(R.id.ataque4);
        ataqueM1 = findViewById(R.id.ataqueM1);
        ataqueM2 = findViewById(R.id.ataqueM2);
        ataqueM3 = findViewById(R.id.ataqueM3);
        layoutAtaque1 = findViewById(R.id.layoutAtaque1);
        layoutAtaque2 = findViewById(R.id.layoutAtaque2);
        layoutAtaque3 = findViewById(R.id.layoutAtaque3);
        layoutAtaque4 = findViewById(R.id.layoutAtaque4);
        layoutAtaqueM1 = findViewById(R.id.layoutAtaqueM1);
        layoutAtaqueM2 = findViewById(R.id.layoutAtaqueM2);
        layoutAtaqueM3 = findViewById(R.id.layoutAtaqueM3);
        Spinner spinnerArma1 = findViewById(R.id.spinnerArma1);
        Spinner spinnerArma2 = findViewById(R.id.spinnerArma2);
        String[] armas = {"Armas", "Daga", "Espada corta", "Espada larga", "Mandoble", "Lanza", "Bastón", "Arco corto", "Arco largo", "Escudo"};
        ArrayAdapter<String> adapterArmas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, armas);
        spinnerArma1.setAdapter(adapterArmas);
        spinnerArma2.setAdapter(adapterArmas);
        String[] dotes = {"Dotes", "Ataque poderoso", "Combate con dos armas", "Combate con dos armas mejorado", "Combate con dos armas mayor", "Defensa con dos armas", "Doble tajo", "Soltura con escudo", "Soltura mayor con escudo"
                , "Disparo a bocajarro", "Esquiva", "Golpe arcano", "Iniciativa mejorada", "Pericias en combate", "Finta mejorada", "Finta mayor", "Soltura con un arma", "Soltura mayor con un arma", "Especialización con un arma"
                , "Especialización mayor con un arma", "Sutileza con las armas"};
        Spinner dote1 = findViewById(R.id.dote1);
        Spinner dote2 = findViewById(R.id.dote2);
        Spinner dote3 = findViewById(R.id.dote3);
        Spinner dote4 = findViewById(R.id.dote4);
        Spinner dote5 = findViewById(R.id.dote5);
        ArrayAdapter<String> adapterDotes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dotes);
        dote1.setAdapter(adapterDotes);
        dote2.setAdapter(adapterDotes);
        dote3.setAdapter(adapterDotes);
        dote4.setAdapter(adapterDotes);
        dote5.setAdapter(adapterDotes);
        Button caracteristicasClase = findViewById(R.id.caratcteristicasClase);
        Button borrarPersonaje = findViewById(R.id.borrarPersonaje);
        Button guardarPersonaje = findViewById(R.id.guardarPersonajes);
        Button luchar = findViewById(R.id.luchar);


        caracteristicasClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(context, ExtraClases.class);
                    intent.putExtra("clase",luchador.getClase());
                    intent.putExtra("id", idFicha);
                    startActivity(intent);


            }
        });
        borrarPersonaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("De verdad quieres borrar el personaje? No hay vuelta atrás");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                    StrictMode.setThreadPolicy(policy);
                                    Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                                    Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
                                    PreparedStatement pst;
                                    pst = conexion.prepareStatement("DELETE FROM ficha where nombre = ? and userId =(SELECT id FROM usuarios where usuario = ?)");
                                    pst.setString(1, nombrePJ);
                                    pst.setString(2, nombreUsuario);
                                    pst.executeUpdate();
                                    conexion.close();
                                    finish();
                                } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                                    Log.e("Error", e.getMessage());
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                                //put your code that needed to be executed when okay is clicked
                                dialog.cancel();


                            }
                        });
                builder1.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


            }
        });
        guardarPersonaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        luchar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListadoEnemigos.class);
                intent.putExtra("id", id);
                intent.putExtra("idPJ",idFicha);
                startActivity(intent);
            }
        });
        levelUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                    Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
                    PreparedStatement pst;
                    pst = conexion.prepareStatement("SELECT nivel FROM ficha where nombre = ? and userId =(SELECT id FROM usuarios where usuario = ?)");
                    pst.setString(1, nombrePJ);
                    pst.setString(2, nombreUsuario);
                    ResultSet set = pst.executeQuery();
                    set.next();
                    if (set.getInt(1) == 20) {
                        Toast.makeText(getApplicationContext(), "Ya estás al nivel máximo", Toast.LENGTH_SHORT).show();

                    } else {
                        int nivel = set.getInt(1) + 1;
                        String consulta = "UPDATE ficha set nivel = " + nivel + " where nombre = '" + nombrePJ + "' and userId =(SELECT id FROM usuarios where usuario = '" + nombreUsuario + "')";
                        pst = conexion.prepareStatement(consulta);
                        pst.executeUpdate();
                    }
                } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    Log.e("Error", e.getMessage());
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }
                init();
            }
        });

        init();
        spinnerArma1.setSelection(luchador.getArma1());
        spinnerArma2.setSelection(luchador.getArma2());
        dote1.setSelection(luchador.getDotes()[0]);
        dote2.setSelection(luchador.getDotes()[1]);
        dote3.setSelection(luchador.getDotes()[2]);
        dote4.setSelection(luchador.getDotes()[3]);
        dote5.setSelection(luchador.getDotes()[4]);
        spinnerArma1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    int resultado = luchador.calcularArma(position, 1);
                    if (resultado == 1) {
                        try {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                            Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
                            PreparedStatement pst;
                            pst = conexion.prepareStatement("update ficha  set arma1 = ? WHERE id = ?");
                            pst.setInt(1, position);
                            pst.setInt(2, idFicha);
                            pst.executeUpdate();

                        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        if (resultado == -1) {
                            Toast.makeText(getApplicationContext(), "No puedes llevar un escudo en la mano dominante", Toast.LENGTH_SHORT).show();
                            spinnerArma1.setSelection(0);
                        }
                    }
                }
                initTexts(luchador);
                if (luchador.manos_2) spinnerArma2.setSelection(0);

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerArma2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    int resultado = luchador.calcularArma(position, 2);
                    if (resultado == 1) {
                        try {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                            Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
                            PreparedStatement pst;
                            pst = conexion.prepareStatement("update ficha  set arma2 = ? WHERE id = ?");
                            pst.setInt(1, position);
                            pst.setInt(2, idFicha);
                            pst.executeUpdate();

                        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        if (resultado == -2) {
                            Toast.makeText(getApplicationContext(), "No puedes llevar un arma de dos manos en la mano dominante", Toast.LENGTH_SHORT).show();
                            spinnerArma2.setSelection(0);
                        } else if (resultado == -3) {
                            Toast.makeText(getApplicationContext(), "Ya llevas un arma de dos manos", Toast.LENGTH_SHORT).show();
                            spinnerArma2.setSelection(0);

                        }
                    }
                    initTexts(luchador);
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                        init();
                    } else {
                        if (resultado == -1) {
                            Toast.makeText(getApplicationContext(), "No cumples los requisitos para la dote", Toast.LENGTH_SHORT).show();
                            dote1.setSelection(0);
                        }
                    }

                    initTexts(luchador);
                } else {
                    try {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                        Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
                        PreparedStatement pst;
                        pst = conexion.prepareStatement("update ficha  set dote0 = NULL WHERE id = ?");
                        pst.setInt(1, idFicha);
                        pst.executeUpdate();

                    } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    init();


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
                            pst = conexion.prepareStatement("update ficha  set dote1 = ? WHERE id = ?");
                            pst.setInt(1, position);
                            pst.setInt(2, idFicha);
                            pst.executeUpdate();

                        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                        init();
                    } else {
                        if (resultado == -1) {
                            Toast.makeText(getApplicationContext(), "No cumples los requisitos para la dote", Toast.LENGTH_SHORT).show();
                            dote2.setSelection(0);
                        }
                    }

                    initTexts(luchador);
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
                    init();
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
                            pst = conexion.prepareStatement("update ficha  set dote2 = ? WHERE id = ?");
                            pst.setInt(1, position);
                            pst.setInt(2, idFicha);
                            pst.executeUpdate();

                        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                        init();
                    } else {
                        if (resultado == -1) {
                            Toast.makeText(getApplicationContext(), "No cumples los requisitos para la dote", Toast.LENGTH_SHORT).show();
                            dote3.setSelection(0);
                        }
                    }

                    initTexts(luchador);
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
                    init();
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
                            pst = conexion.prepareStatement("update ficha  set dote3 = ? WHERE id = ?");
                            pst.setInt(1, position);
                            pst.setInt(2, idFicha);
                            pst.executeUpdate();

                        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                        init();
                    } else {
                        if (resultado == -1) {
                            Toast.makeText(getApplicationContext(), "No cumples los requisitos para la dote", Toast.LENGTH_SHORT).show();
                            dote4.setSelection(0);
                        }
                    }

                    initTexts(luchador);
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
                    init();
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
                            pst = conexion.prepareStatement("update ficha  set dote4 = ? WHERE id = ?");
                            pst.setInt(1, position);
                            pst.setInt(2, idFicha);
                            pst.executeUpdate();

                        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                        init();
                    } else {
                        if (resultado == -1) {
                            Toast.makeText(getApplicationContext(), "No cumples los requisitos para la dote", Toast.LENGTH_SHORT).show();
                            dote5.setSelection(0);
                        }
                    }

                    initTexts(luchador);
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
                    init();
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void init() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
            PreparedStatement pst;
            pst = conexion.prepareStatement("SELECT id FROM ficha where nombre = ? and userId =(SELECT id FROM usuarios where usuario = ?)");
            pst.setString(1, nombrePJ);
            pst.setString(2, nombreUsuario);
            ResultSet set = pst.executeQuery();
            set.next();
            idFicha = set.getInt(1);
            luchador = new Luchador(idFicha);

        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            Log.e("Error", e.getMessage());
        }

        initTexts(luchador);


    }

    public void initTexts(Luchador luchador) {
        nivelPersonaje.setText("Lvl\n" + luchador.getNivel());
        if (luchador.getClase().equals("Picaro")) {
            iconImageView.setImageResource(R.drawable.daco_1482423);
        } else if (luchador.getClase().equals("Guerrero")) {
            iconImageView.setImageResource(R.drawable.outline_shield_24);
        } else if (luchador.getClase().equals(("Mago"))) {
            iconImageView.setImageResource(R.drawable.outline_local_fire_department_24);
        }
        nombrePersonaje.setText(luchador.getNombre());
        if (luchador.getFuerza() >= 0) {
            fueFicha.setText("+" + luchador.getFuerza());
        } else {
            fueFicha.setText("-" + luchador.getFuerza());
        }
        if (luchador.getDestreza() >= 0) {
            desFicha.setText("+" + luchador.getDestreza());
        } else {
            desFicha.setText("-" + luchador.getDestreza());
        }
        if (luchador.getConstitucion() >= 0) {
            conFicha.setText("+" + luchador.getConstitucion());
        } else {
            conFicha.setText("-" + luchador.getConstitucion());
        }
        if (luchador.getInteligencia() >= 0) {
            intFicha.setText("+" + luchador.getInteligencia());
        } else {
            intFicha.setText("-" + luchador.getInteligencia());
        }
        if (luchador.getSabiduria() >= 0) {
            sabFicha.setText("+" + luchador.getSabiduria());
        } else {
            sabFicha.setText("-" + luchador.getSabiduria());
        }
        if (luchador.getCarisma() >= 0) {
            carFicha.setText("+" + luchador.getCarisma());
        } else {
            carFicha.setText("-" + luchador.getCarisma());
        }
        if (luchador.getAveriguar() >= 0) {
            averiguarFicha.setText("+" + luchador.getAveriguar());
        } else {
            averiguarFicha.setText("-" + luchador.getAveriguar());
        }
        if (luchador.getEnganar() >= 0) {
            enganarFicha.setText("+" + luchador.getEnganar());
        } else {
            enganarFicha.setText("-" + luchador.getEnganar());
        }
        if (luchador.getPercepcion() >= 0) {
            percepcionFicha.setText("+" + luchador.getPercepcion());
        } else {
            percepcionFicha.setText("-" + luchador.getPercepcion());
        }
        if (luchador.getSigilo() >= 0) {
            sigiloFicha.setText("+" + luchador.getSigilo());
        } else {
            sigiloFicha.setText("-" + luchador.getSigilo());
        }

        armadura.setText("" + luchador.getArmadura());


        armaduraToque.setText("" + luchador.getArmaduraToque());


        armaduraDesprevenido.setText("" + luchador.getArmaduraDesprevenido());


        if (luchador.getsFortaleza() >= 0) {
            sFortaleza.setText("+" + luchador.getsFortaleza());
        } else {
            sFortaleza.setText("-" + luchador.getsFortaleza());
        }
        if (luchador.getsReflejos() >= 0) {
            sReflejos.setText("+" + luchador.getsReflejos());
        } else {
            sReflejos.setText("-" + luchador.getsReflejos());
        }
        if (luchador.getsVoluntad() >= 0) {
            sVoluntad.setText("+" + luchador.getsVoluntad());
        } else {
            sVoluntad.setText("-" + luchador.getsVoluntad());
        }
        pGolpe.setText("" + luchador.getpGolpe());
        ataque1.setText("" + luchador.getAtaque1());

        layoutAtaque2.setVisibility(View.INVISIBLE);
        layoutAtaque3.setVisibility(View.INVISIBLE);
        layoutAtaque4.setVisibility(View.INVISIBLE);
        layoutAtaqueM1.setVisibility(View.INVISIBLE);
        layoutAtaqueM2.setVisibility(View.INVISIBLE);
        layoutAtaqueM3.setVisibility(View.INVISIBLE);

        if (luchador.getAtaqueBase2() > 0) {
            layoutAtaque2.setVisibility(View.VISIBLE);
            ataque2.setText("" + luchador.getAtaque2());
        } else {
            layoutAtaque2.setVisibility(View.INVISIBLE);
        }

        if (luchador.getAtaqueBase3() > 0) {
            layoutAtaque3.setVisibility(View.VISIBLE);
            ataque3.setText("" + luchador.getAtaque3());
        } else {
            layoutAtaque3.setVisibility(View.INVISIBLE);
        }
        if (luchador.getAtaqueBase4() > 0) {
            layoutAtaque4.setVisibility(View.VISIBLE);
            ataque4.setText("" + luchador.getAtaque4());
        } else {
            layoutAtaque4.setVisibility(View.INVISIBLE);
        }
        if (luchador.getArma1() != 0 && luchador.getArma2() != 0 && luchador.getArma2() != 9) {
            layoutAtaqueM1.setVisibility(View.VISIBLE);
            ataqueM1.setText("" + luchador.getAtaqueM1());
            if (luchador.comprobarDote(3)) {
                layoutAtaqueM2.setVisibility(View.VISIBLE);
                ataqueM2.setText("" + luchador.getAtaqueM2());
                if (luchador.comprobarDote(4)) {
                    layoutAtaqueM3.setVisibility(View.VISIBLE);
                    ataqueM3.setText("" + luchador.getAtaqueM3());
                }
            }
        } else {

            layoutAtaqueM1.setVisibility(View.INVISIBLE);
            layoutAtaqueM2.setVisibility(View.INVISIBLE);
            layoutAtaqueM3.setVisibility(View.INVISIBLE);
        }


    }


}