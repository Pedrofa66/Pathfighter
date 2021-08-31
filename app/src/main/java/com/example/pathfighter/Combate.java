package com.example.pathfighter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Combate extends AppCompatActivity {
    List<TextElement> log;
    int idPJ;
    int idEnemigo;
    Luchador luchador;
    Luchador luchadorEnemigo;
    ImageView ImagenPJ;
    ImageView ImagenEnemigo;
    Button iniciarCombate;
    boolean combateIniciado=false;
    boolean critico;
    int maxRondas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combate);
        ImagenPJ = findViewById(R.id.imagenPJ);
        ImagenEnemigo = findViewById(R.id.imagenEnemigo);
        iniciarCombate = findViewById(R.id.iniciarCombate);
        combateIniciado=false;
        maxRondas=20;
        idPJ = getIntent().getIntExtra("idPJ", 1);
        idEnemigo = getIntent().getIntExtra("idEnemigo", 1);
        luchador = new Luchador(idPJ);
        luchadorEnemigo = new Luchador(idEnemigo);
        actualizarImagenes();
        iniciarCombate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!combateIniciado){
                    combateIniciado=true;
                    iniciarCombate.setText("Finalizar combate");
                    combate(luchador,luchadorEnemigo);
                }else{
                    finish();
                }
            }
        });
        log = new ArrayList<>();
        log.add(new TextElement("Inicia el combate"));
        log.add(new TextElement(luchador.getNombre() + " VS. " + luchadorEnemigo.getNombre()));
        Collections.reverse(log);
        TextAdapter textAdapter = new TextAdapter(log);
        RecyclerView recyclerView = findViewById(R.id.logCombate);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(textAdapter);




    }

    private void combate(Luchador luchador, Luchador luchadorEnemigo) {


        if (tiradaEnfrentada(d20(luchador.getIniciativa()), d20(luchadorEnemigo.getIniciativa()))) {
            if (tiradaEnfrentada(d20(luchador.getSigilo()), d20(luchadorEnemigo.getPercepcion()))) {
                atacar(luchador, luchadorEnemigo);
            }
        } else {
            if (tiradaEnfrentada(d20(luchadorEnemigo.getSigilo()), d20(luchador.getPercepcion()))) {
                atacar(luchadorEnemigo, luchador);
            }
        }

        while (luchador.getpGolpe() > 0 && luchadorEnemigo.getpGolpe() > 0 && maxRondas>0) {


            if (tiradaEnfrentada(d20(luchador.getIniciativa()), d20(luchadorEnemigo.getIniciativa()))) {
                if (!luchador.isDormido() || (tiradaEnfrentada(d20(luchador.getsVoluntad()), (luchadorEnemigo.getNivel() + luchadorEnemigo.getInteligencia() + 10)))) {
                    luchador.setDormido(false);
                    if (luchador.comprobarDote(14)) {
                        finta(luchador, luchadorEnemigo);
                    } else {
                        elegir(luchador, luchadorEnemigo);
                    }
                }
                if ((!luchadorEnemigo.isDormido() || (tiradaEnfrentada(d20(luchadorEnemigo.getsVoluntad()), (luchador.getNivel() + luchador.getInteligencia() + 10))))&&luchadorEnemigo.getpGolpe()>0) {
                    luchadorEnemigo.setDormido(false);
                    if (luchadorEnemigo.comprobarDote(14)) {
                        finta(luchadorEnemigo, luchador);
                    } else {
                        elegir(luchadorEnemigo, luchador);
                    }
                }


            } else {
                if (!luchadorEnemigo.isDormido() || (tiradaEnfrentada(d20(luchadorEnemigo.getsVoluntad()), (luchador.getNivel() + luchador.getInteligencia() + 10)))) {
                    luchadorEnemigo.setDormido(false);
                    if (luchadorEnemigo.comprobarDote(14)) {
                        finta(luchadorEnemigo, luchador);
                    } else {
                        elegir(luchadorEnemigo, luchador);
                    }

                }

                if ((!luchador.isDormido() || (tiradaEnfrentada(d20(luchador.getsVoluntad()), (luchadorEnemigo.getNivel() + luchadorEnemigo.getInteligencia() + 10)))) && luchador.getpGolpe()>0) {
                    luchador.setDormido(false);
                    if (luchador.comprobarDote(14)) {
                        finta(luchador, luchadorEnemigo);
                    } else {
                        elegir(luchador, luchadorEnemigo);
                    }

                }

            }


            maxRondas--;
        }
        if(maxRondas==0 && luchador.getpGolpe()>0 && luchadorEnemigo.getpGolpe()>0){
            addString(luchador.getNombre() + " y " + luchadorEnemigo.getNombre()  + " han luchado hasta la extenuación");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Ha sido un empate")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        if (luchador.getpGolpe() > 0) {
            //PopUp victoria de luchador
            addString(luchadorEnemigo.getNombre() + " ha caido");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Victoria!!\nLa victoria ha sido para " + luchador.getNombre())
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        } else {
            addString(luchador.getNombre() + " ha caido");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("DERROTA\nLa victoria ha sido para " + luchadorEnemigo.getNombre())
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }


    }

    private void elegir(Luchador luchador, Luchador luchadorEnemigo) {
        if (luchador.getClase().equals("Mago")) {
            int nivelHechizo = nivelHechizo(luchador)+1;
            int danoHechizo;
            if (nivelHechizo != -1) {
                double random = Math.random();
                if (random > 0.66) {
                    for (int i = 0; i > nivelHechizo; i++) {
                        danoHechizo = (int) (Math.random() * 7);
                        if (tiradaEnfrentada(d20(luchadorEnemigo.getsReflejos()), (luchador.getNivel() + luchador.getInteligencia() + 10)))
                            danoHechizo = (int) danoHechizo / 2;
                        luchadorEnemigo.setpGolpe((luchadorEnemigo.getpGolpe() - danoHechizo));
                        addString(luchador.getNombre() + " le ha lanzado una bola de fuego haciéndole " + danoHechizo);
                    }
                } else if (random > 0.33) {
                    for (int i = 0; i > nivelHechizo; i++) {
                        danoHechizo = (int) (Math.random() * 11);
                        if (tiradaEnfrentada(d20(luchadorEnemigo.getsFortaleza()), (luchador.getNivel() + luchador.getInteligencia() + 10)))
                            danoHechizo = (int) danoHechizo / 2;
                        luchadorEnemigo.setpGolpe((luchadorEnemigo.getpGolpe() - danoHechizo));
                        addString(luchador.getNombre() + " le ha lanzado un rayo de ácido haciéndole " + danoHechizo);
                    }

                } else {
                    luchadorEnemigo.setDormido(true);
                    addString(luchador.getNombre() + "Ha dormido a " + luchadorEnemigo.getNombre());
                }

            } else {
                if (Math.random() > 0.5) {
                    finta(luchador, luchadorEnemigo);
                } else {
                    atacar(luchador, luchadorEnemigo);
                }
            }
        } else {
            if (Math.random() > 0.5) {
                finta(luchador, luchadorEnemigo);
            } else {
                atacar(luchador, luchadorEnemigo);
            }
        }

        finta(luchador, luchadorEnemigo);
    }

    private int nivelHechizo(Luchador luchador) {
        int[] hechizosMago = luchador.getHechizosMago();
        for (int i = 8; i >= 0; i--) {
            if (hechizosMago[i] > 0) {
                return i;
            }
        }
        return -1;
    }

    private void finta(Luchador luchador, Luchador luchadorEnemigo) {
        if (tiradaEnfrentada(d20(luchador.getEnganar()), (luchadorEnemigo.getSabiduria() + 10))) {
            luchadorEnemigo.setDesprevenido(true);
            if (luchador.comprobarDote(14)) {
                atacar(luchador, luchadorEnemigo);
            }
        } else {
            addString(luchador.getNombre() + " intenta fintar pero falla");
        }
    }

    private void atacar(Luchador atacante, Luchador atacado) {
        int defensa;
        if (atacado.isDesprevenido()) {
            defensa = atacado.getArmaduraDesprevenido();
        } else {
            defensa = atacado.getArmadura();
        }
        if (tiradaEnfrentada(d20(atacante.getAtaque1()), defensa)|critico) {
            //Atacante golpea
            addString(atacante.getNombre() + " impacta en " + atacado.getNombre());
            tiradaDano(atacante.getDañoAtaque(), atacante.getDadoAtaque(), atacado, atacante);

        } else {
            addString((atacado.getNombre() + " esquiva a " + atacante.getNombre()));
            //Atacado evita el ataque
        }

        if (atacante.getAtaqueBase2() > 0) {
            if (tiradaEnfrentada(d20(atacante.getAtaque2()), defensa)|critico) {
                //Atacante golpea
                addString(atacante.getNombre() + " impacta en " + atacado.getNombre());
                tiradaDano(atacante.getDañoAtaque(), atacante.getDadoAtaque(), atacado, atacante);

            } else {
                addString((atacado.getNombre() + " esquiva a " + atacante.getNombre()));
                //Atacado evita el ataque
            }

        }
        if (atacante.getAtaqueBase3() > 0) {
            if (tiradaEnfrentada(d20(atacante.getAtaque3()), defensa)|critico) {
                //Atacante golpea
                addString(atacante.getNombre() + " impacta en " + atacado.getNombre());
                tiradaDano(atacante.getDañoAtaque(), atacante.getDadoAtaque(), atacado, atacante);

            } else {
                addString((atacado.getNombre() + " esquiva a " + atacante.getNombre()));
                //Atacado evita el ataque
            }

        }
        if (atacante.getAtaqueBase4() > 0) {
            if (tiradaEnfrentada(d20(atacante.getAtaque4()), defensa)|critico) {
                //Atacante golpea
                addString(atacante.getNombre() + " impacta en " + atacado.getNombre());
                tiradaDano(atacante.getDañoAtaque(), atacante.getDadoAtaque(), atacado, atacante);

            } else {
                addString((atacado.getNombre() + " esquiva a " + atacante.getNombre()));
                //Atacado evita el ataque
            }
        }
        if (atacante.getArma2() != 0 && atacante.getArma2() != 9) {
            if (tiradaEnfrentada(d20(atacante.getAtaqueM1()), defensa)|critico) {
                //Atacante golpea
                addString(atacante.getNombre() + " impacta en " + atacado.getNombre());
                tiradaDano(atacante.getDañoAtaqueM(), atacante.getDadoAtaqueM(), atacado, atacante);

            } else {
                addString((atacado.getNombre() + " esquiva a " + atacante.getNombre()));
                //Atacado evita el ataque
            }
        }
        if (atacante.comprobarDote(3) && (atacante.getArma2() != 0 && atacante.getArma2() != 9)) {
            if (tiradaEnfrentada(d20(atacante.getAtaqueM2()), defensa)|critico) {
                //Atacante golpea
                addString(atacante.getNombre() + " impacta en " + atacado.getNombre());
                tiradaDano(atacante.getDañoAtaque(), atacante.getDadoAtaque(), atacado, atacante);

            } else {
                addString((atacado.getNombre() + " esquiva a " + atacante.getNombre()));
                //Atacado evita el ataque
            }
        }
        if (atacante.comprobarDote(4) && (atacante.getArma2() != 0 && atacante.getArma2() != 9)) {
            if (tiradaEnfrentada(d20(atacante.getAtaqueM3()), defensa)|critico) {
                //Atacante golpea
                addString(atacante.getNombre() + " impacta en " + atacado.getNombre());
                tiradaDano(atacante.getDañoAtaque(), atacante.getDadoAtaque(), atacado, atacante);

            } else {
                addString((atacado.getNombre() + " esquiva a " + atacante.getNombre()));
                //Atacado evita el ataque
            }
        }
    }

    private void tiradaDano(int dañoAtaque, int dadoAtaque, Luchador atacado, Luchador atacante) {

        int dano = (int) (Math.random() * dadoAtaque) + dañoAtaque;

        if (atacado.isDesprevenido()) {
            if (atacante.getClase().equals("Picaro")) {
                for (int i = 0; i < atacante.getDadosPicaro(); i++) {
                    dano = dano + (int) (Math.random() * 7);
                }
            }
        }


        if (critico) {
            addString("GOLPE CRÍTICO!!! ");
            dano = dano * 2;
        }
        addString(dano + " puntos de daño");
        atacado.setpGolpe(atacado.getpGolpe() - dano);
    }

    private void addString(String s) {

        Collections.reverse(log);
        log.add(new TextElement(s));
        Collections.reverse(log);
        TextAdapter textAdapter = new TextAdapter(log);
        RecyclerView recyclerView = findViewById(R.id.logCombate);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(textAdapter);
    }

    private boolean tiradaEnfrentada(int valorAComprobar, int valorComprobado) {
        if (valorAComprobar > valorComprobado) {
            return true;
        } else {
            return false;
        }
    }

    private int d20(int bonificador) {
        int resultado = (int) (Math.random() * 21);
        if (resultado == 20) {
            critico = true;
        } else {
            critico = false;
        }
        return resultado + bonificador;
    }

    private void actualizarImagenes() {
        if (luchador.getRaza().equals("Humano")) {
            if (luchador.getClase().equals("Picaro")) {
                ImagenPJ.setImageResource(R.drawable.rogue_human);
            } else if (luchador.getClase().equals("Mago")) {
                ImagenPJ.setImageResource(R.drawable.mage_human);
            } else {
                ImagenPJ.setImageResource(R.drawable.warrior_human);
            }
        } else if (luchador.getRaza().equals("Elfo")) {
            if (luchador.getClase().equals("Picaro")) {
                ImagenPJ.setImageResource(R.drawable.rogue_elf);
            } else if (luchador.getClase().equals("Mago")) {
                ImagenPJ.setImageResource(R.drawable.mage_elf);
            } else {
                ImagenPJ.setImageResource(R.drawable.warrior_elf);
            }
        } else {
            if (luchador.getClase().equals("Picaro")) {
                ImagenPJ.setImageResource(R.drawable.rogue_dwarf);
            } else if (luchador.getClase().equals("Mago")) {
                ImagenPJ.setImageResource(R.drawable.mage_dwarf);
            } else {
                ImagenPJ.setImageResource(R.drawable.warrior_dwarf);
            }
        }
        if (luchadorEnemigo.getRaza().equals("Humano")) {
            if (luchadorEnemigo.getClase().equals("Picaro")) {
                ImagenEnemigo.setImageResource(R.drawable.rogue_human);
            } else if (luchadorEnemigo.getClase().equals("Mago")) {
                ImagenEnemigo.setImageResource(R.drawable.mage_human);
            } else {
                ImagenEnemigo.setImageResource(R.drawable.warrior_human);
            }
        } else if (luchadorEnemigo.getRaza().equals("Elfo")) {
            if (luchadorEnemigo.getClase().equals("Picaro")) {
                ImagenEnemigo.setImageResource(R.drawable.rogue_elf);
            } else if (luchadorEnemigo.getClase().equals("Mago")) {
                ImagenEnemigo.setImageResource(R.drawable.mage_elf);
            } else {
                ImagenEnemigo.setImageResource(R.drawable.warrior_elf);
            }
        } else {
            if (luchadorEnemigo.getClase().equals("Picaro")) {
                ImagenEnemigo.setImageResource(R.drawable.rogue_dwarf);
            } else if (luchadorEnemigo.getClase().equals("Mago")) {
                ImagenEnemigo.setImageResource(R.drawable.mage_dwarf);
            } else {
                ImagenEnemigo.setImageResource(R.drawable.warrior_dwarf);
            }
        }
    }

}