package com.example.pathfighter;

import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Luchador {
    int id;
    String raza;
    String nombre;
    String clase;
    int nivel;
    int fuerza;
    int destreza;
    int constitucion;
    int inteligencia;
    int sabiduria;
    int carisma;
    int arma1;
    String tipoArma1;
    boolean manos_2;
    int arma2;
    String tipoArma2;
    int[] dotes = new int[10];
    int percepcion;
    int sigilo;
    int averiguar;
    int enganar;
    int armadura;
    int armaduraToque;
    int armaduraDesprevenido;
    int sFortaleza;
    int sReflejos;
    int sVoluntad;
    int ataqueBase1;
    int ataqueBase2;
    int ataqueBase3;
    int ataqueBase4;
    int ataque1 = 0;
    int ataque2 = 0;
    int ataque3 = 0;
    int ataque4 = 0;
    int ataqueM1 = 0;
    int ataqueM2 = 0;
    int ataqueM3 = 0;
    int dañoAtaque;
    int criticoAtaque;
    int multiplicadorCritico;
    int dadoAtaque;
    int dañoAtaqueM;
    int criticoAtaqueM;
    int multiplicadorCriticoM;
    int dadoAtaqueM;
    int pGolpe;
    int iniciativa;
    boolean fintaGratis = false;
    boolean fintaMejorada = false;
    boolean desprevenido;
    int dadosPicaro;
    int[] hechizosMago = new int[9];
    boolean isDormido = false;

    public Luchador(int id) {
        this.id = id;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
            PreparedStatement pst;
            pst = conexion.prepareStatement("SELECT nombre, clase, nivel, fuerza, destreza, constitucion, inteligencia, sabiduria, carisma, averiguar, engannar, sigilo, percepcion, arma1, arma2, dote0, dote1, dote2, dote3, dote4, raza, dote5, dote6, dote7, dote8, dote9 FROM ficha where id= ?");
            pst.setInt(1, id);
            ResultSet set = pst.executeQuery();
            set.next();
            nombre = set.getString(1);
            clase = set.getString(2);
            nivel = set.getInt(3);
            fuerza = set.getInt(4);
            destreza = set.getInt(5);
            constitucion = set.getInt(6);
            inteligencia = set.getInt(7);
            sabiduria = set.getInt(8);
            carisma = set.getInt(9);
            averiguar = set.getInt(10);
            enganar = set.getInt(11);
            sigilo = set.getInt(12);
            percepcion = set.getInt(13);
            arma1 = set.getInt(14);
            arma2 = set.getInt(15);
            dotes[0] = set.getInt(16);
            dotes[1] = set.getInt(17);
            dotes[2] = set.getInt(18);
            dotes[3] = set.getInt(19);
            dotes[4] = set.getInt(20);
            raza = set.getString(21);
            dotes[5] = set.getInt(22);
            dotes[6] = set.getInt(23);
            dotes[7] = set.getInt(24);
            dotes[8] = set.getInt(25);
            dotes[9] = set.getInt(26);


            String consulta = "SELECT dadoGolpe, bonusArmadura, ataqueBase1, ataqueBase2, ataqueBase3, ataqueBase4, fortaleza, reflejos, voluntad FROM " + clase + "  where nivel = ? ";
            pst = conexion.prepareStatement(consulta);
            pst.setInt(1, nivel);
            set = pst.executeQuery();
            set.next();
            pGolpe = (set.getInt(1) + constitucion) + (nivel - 1) * (set.getInt(1) / 2 + constitucion);
            armadura = 10 + destreza + set.getInt(3);
            armaduraToque = 10 + destreza;
            armaduraDesprevenido = 10 + set.getInt(3);
            sFortaleza = constitucion + set.getInt(7);
            sReflejos = destreza + set.getInt(8);
            sVoluntad = sabiduria + set.getInt(9);
            ataqueBase1 = set.getInt(3);
            ataqueBase2 = set.getInt(4);
            ataqueBase3 = set.getInt(5);
            ataqueBase4 = set.getInt(6);
            iniciativa = destreza;
            if(clase.equals("Picaro")){
                consulta = "SELECT danoDesprevenido FROM picaro  where nivel = ? ";
                pst = conexion.prepareStatement(consulta);
                pst.setInt(1, nivel);
                set = pst.executeQuery();
                set.next();
                dadosPicaro = set.getInt(1);
            }else if(clase.equals("Mago")){
                consulta = "SELECT cargas1, cargas2, cargas3, cargas4, cargas5, cargas6, cargas7, cargas8, cargas9 FROM mago  where nivel = ? ";
                pst = conexion.prepareStatement(consulta);
                pst.setInt(1, nivel);
                set = pst.executeQuery();
                set.next();
                hechizosMago[0] = set.getInt(1);
                hechizosMago[1] = set.getInt(2);
                hechizosMago[2] = set.getInt(3);
                hechizosMago[3] = set.getInt(4);
                hechizosMago[4] = set.getInt(5);
                hechizosMago[5] = set.getInt(6);
                hechizosMago[6] = set.getInt(7);
                hechizosMago[7] = set.getInt(8);
                hechizosMago[8] = set.getInt(9);
            }

            conexion.close();

        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            Log.e("Error", e.getMessage());
        }
        if (arma1 != 0) calcularArma(arma1, 1);
        if (arma2 != 0) calcularArma(arma2, 2);
        calcularDotes();

    }

    public int calcularArma(int arma, int mano) {
        int nManos;
        int dadosDaño;
        int critico;
        int multiCritico;
        String caracteristica;
        int nManosArma1;
        String tipoDeArma;

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            Connection conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://pathfighter.duckdns.org:1433;databaseName=Pathfighter;user=prueba;password=prueba");
            PreparedStatement pst;
            pst = conexion.prepareStatement("SELECT manos, daño, rango, multiplicadorCritico, caracteristicaPrimaria,[tipoArma] FROM arma WHERE id = ?");
            pst.setInt(1, (arma));
            ResultSet set = pst.executeQuery();
            set.next();
            nManos = set.getInt(1);
            dadosDaño = set.getInt(2);
            critico = set.getInt(3);
            multiCritico = set.getInt(4);
            caracteristica = set.getString(5);
            tipoDeArma = set.getString(6);
            if (mano == 1 && arma == 9) {
                return -1; //No puedes llevar un escudo en tu mano dominante
            }
            if (mano == 2 && arma == 9) {
                dadoAtaqueM = 0;
                criticoAtaqueM = 0;
                multiplicadorCriticoM = 0;
                ataqueM1 = 0;
                armadura++;
                armaduraDesprevenido++;
                arma2=9;
                calcularArma(arma1,1);
                return 1;
            }
            if (mano == 2 && nManos == 2) {
                return -2; //No puedes llevar un arma de dos manos en la mano torpe
            }
            if (manos_2 && mano == 2) {

                    return -3; //Ya llevas un arma de dos manos


            }
            if (mano == 1) {
                dadoAtaque = dadosDaño;
                criticoAtaque = critico;
                multiplicadorCritico = multiCritico;
                if (caracteristica.equals("Fuerza")) {
                    if (ataqueBase1 >= 0) {
                        ataque1 = ataqueBase1 + fuerza;
                    }
                    if (ataqueBase2 >= 0) {
                        ataque2 = ataqueBase2 + fuerza;
                    }
                    if (ataqueBase3 >= 0) {
                        ataque3 = ataqueBase3 + fuerza;
                    }
                    if (ataqueBase4 >= 0) {
                        ataque4 = ataqueBase4 + fuerza;
                    }
                    dañoAtaque = fuerza;
                    if (nManos == 2) {
                        manos_2 = true;
                        dañoAtaque = (int) (dañoAtaque * 1.5);
                        if(arma2!=0){
                            arma2 = 0;
                            calcularArma(0,2);
                            try {

                                pst = conexion.prepareStatement("update ficha  set arma2 = null WHERE id = ?");
                                pst.setInt(1, id);
                                pst.executeUpdate();

                            } catch (SQLException e) {

                            }
                        }

                    }else{
                        manos_2=false;
                    }
                } else {
                    if (ataqueBase1 >= 0) {
                        ataque1 = ataqueBase1 + destreza;
                    }
                    if (ataqueBase2 >= 0) {
                        ataque2 = ataqueBase2 + destreza;
                    }
                    if (ataqueBase3 >= 0) {
                        ataque3 = ataqueBase3 + destreza;
                    }
                    if (ataqueBase4 >= 0) {
                        ataque4 = ataqueBase4 + destreza;
                    }
                    if (nManos == 2) {
                        manos_2 = true;
                        if(arma2!=0){
                            arma2=0;
                            calcularArma(0,2);
                            try {

                                pst = conexion.prepareStatement("update ficha  set arma2 = null WHERE id = ?");
                                pst.setInt(1, id);
                                pst.executeUpdate();

                            } catch (SQLException e) {

                            }
                        }

                    }else{
                        manos_2 = false;
                    }

                }
                tipoArma1 = tipoDeArma;
                arma1=arma;
                return 1;
            }
            if (mano == 2 && arma!=0 && !manos_2) {
                dadoAtaqueM = dadosDaño;
                criticoAtaqueM = critico;
                multiplicadorCriticoM = multiCritico;

                if (caracteristica.equals("Fuerza")) {
                    ataqueM1 = ataqueBase1 + fuerza;
                    dañoAtaqueM = (int) (fuerza * 0.5);
                }
                tipoArma2 = tipoDeArma;
                arma2=arma;
                calcularArma(arma1, 1);

                dobleArma();
                return 1;
            }


        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            Log.e("Error", e.getMessage());
        }


        return 1;
    }

    public void dobleArma() {
        if (arma1 != 0 && arma2 != 0 && arma2 != 9) {
            if ("Ligera".equals(tipoArma2)) {
                ataque1 = ataque1 - 4;
                ataque2 = ataque2 - 4;
                ataque3 = ataque3 - 4;
                ataque4 = ataque4 - 4;
                ataqueM1 = ataqueM1 - 8;
            } else {
                ataque1 = ataque1 - 6;
                ataque2 = ataque2 - 6;
                ataque3 = ataque3 - 6;
                ataque4 = ataque4 - 6;
                ataqueM1 = ataqueM1 - 10;
            }
        }
    }

    public boolean comprobarDote(int dote) {
        for (int i = 0; i < 10; i++) {
            if (dote == dotes[i]) {
                return true;
            }
        }
        return false;
    }

    public int puedeDote(int dote) {
        switch (dote) {
            //Ataque poderoso
            case 1:
                if (fuerza >= 1 && ataqueBase1 >= 1) {
                    return 1;
                } else {
                    return -1;
                }
                //Combate con dos armas
            case 2:
                if (destreza >= 2) {
                    return 1;
                } else {
                    return -1;
                }
                //Combate con dos armas mejorado
            case 3:
                if (destreza >= 3 && ataqueBase1 >= 11 && comprobarDote(2)) {
                    return 1;
                } else {
                    return -1;
                }
                //Combate con dos armas mayor
            case 4:
                if (destreza >= 4 && ataqueBase1 >= 11 && comprobarDote(3)) {
                    return 1;
                } else {
                    return -1;
                }
                //Defensa con dos armas
            case 5:
                if (destreza >= 2 && comprobarDote(2)) {
                    return 1;
                } else {
                    return -1;
                }
                //Doble tajo
            case 6:
                if (destreza >= 2 && comprobarDote(2)) {
                    return 1;
                } else {
                    return -1;
                }
                //Soltura con escudo
            case 7:
                if (ataqueBase1 >= 1) {
                    return 1;
                } else {
                    return -1;
                }
                //Soltura mayor con escudo
            case 8:
                if (comprobarDote(7) && nivel >= 8 && clase.equals("Guerrero")) {
                    return 1;
                } else {
                    return -1;
                }
                //Esquiva
            case 10:
                if (destreza >= 13) {
                    return 1;
                } else {
                    return -1;
                }
                //Golpe arcano
            case 11:
                if (clase.equals("Mago")) {
                    return 1;
                } else {
                    return -1;
                }
                //Pericias en combate
            case 13:
                if (inteligencia >= 1) {
                    return 1;
                } else {
                    return -1;
                }
                //Finta mejorada
            case 14:
                if (inteligencia >= 1 && comprobarDote(13)) {
                    return 1;
                } else {
                    return -1;
                }
                //Finta mayor
            case 15:
                if (inteligencia >= 1 && ataqueBase1 >= 6 && comprobarDote(14)) {
                    return 1;
                } else {
                    return -1;
                }
                //Soltura con un arma
            case 16:
                if (ataqueBase1 >= 1) {
                    return 1;
                } else {
                    return -1;
                }
                //Soltura mayor con un arma
            case 17:
                if (comprobarDote(15) && nivel == 9 && clase.equals("Guerrero")) {
                    return 1;
                } else {
                    return -1;
                }
                //Especializacion con un arma
            case 18:
                if (comprobarDote(15) && nivel == 4 && clase.equals("Guerrero")) {
                    return 1;
                } else {
                    return -1;
                }
                //Especialización mayor con un arma
            case 19:
                if (comprobarDote(17) && comprobarDote(18) && nivel == 12 && clase.equals("Guerrero")) {
                    return 1;
                } else {
                    return -1;
                }

        }

        return 1;
    }

    public int calcularDotes() {
        for (int i = 0; i < 10; i++) {
            if (dotes[i] == 20) {
                ataque1 = ataque1 - fuerza + destreza;
                ataque2 = ataque2 - fuerza + destreza;
                ataque3 = ataque3 - fuerza + destreza;
                ataque4 = ataque4 - fuerza + destreza;
                ataqueM1 = ataqueM1 - fuerza + destreza;

            } else if (dotes[i] == 1) {
                ataque1 = ataque1 - (1 + (ataqueBase1 / 4));
                ataque2 = ataque2 - (1 + (ataqueBase1 / 4));
                ataque3 = ataque3 - (1 + (ataqueBase1 / 4));
                ataque4 = ataque4 - (1 + (ataqueBase1 / 4));
                ataqueM1 = ataqueM1 - (1 + (ataqueBase1 / 4));
                if (manos_2) {
                    dañoAtaque = (int) (dañoAtaque + 1.5 * (2 + (ataqueBase1 / 4) * 2));
                }
                dañoAtaque = dañoAtaque + 2 + (ataqueBase1 / 4) * 2;
                dañoAtaqueM = (int) (dañoAtaque + 0.5 * (2 + (ataqueBase1 / 4) * 2));

            } else if (dotes[i] == 2) {
                ataque1 = ataque1 + 2;
                ataque2 = ataque2 + 2;
                ataque3 = ataque3 + 2;
                ataque4 = ataque4 + 2;
                ataqueM1 = ataqueM1 + 6;

            } else if (dotes[i] == 5 && arma1 != 0 && arma2 != 0) {
                armadura++;
                armaduraDesprevenido++;

            } else if (dotes[i] == 6) {
                dañoAtaqueM = dañoAtaque * 2;

            } else if (dotes[i] == 7 && arma2 == 9) {
                armadura++;
                armaduraDesprevenido++;

            } else if (dotes[i] == 8 && arma2 == 9) {
                armadura++;
                armaduraDesprevenido++;

            } else if (dotes[i] == 9) {
                ataque1++;
                ataque2++;
                ataque3++;
                ataque4++;
                dañoAtaque++;

            } else if (dotes[i] == 10) {
                armadura++;
                armaduraToque++;

            } else if (dotes[i] == 11) {
                dañoAtaque = dañoAtaque + (int) (nivel / 5 + 1);
                dañoAtaqueM = dañoAtaqueM + (int) (nivel / 5 + 1);

            } else if (dotes[i] == 12) {
                iniciativa = iniciativa + 4;

            } else if (dotes[i] == 13) {
                ataque1 = ataque1 - (1 + (ataqueBase1 / 4));
                ataque2 = ataque2 - (1 + (ataqueBase1 / 4));
                ataque3 = ataque3 - (1 + (ataqueBase1 / 4));
                ataque4 = ataque4 - (1 + (ataqueBase1 / 4));
                ataqueM1 = ataqueM1 - (1 + (ataqueBase1 / 4));
                armadura = armadura + (int) (ataqueBase1 / 4);
                armaduraToque = armadura + (int) (ataqueBase1 / 4);

            } else if (dotes[i] == 14) {
                fintaGratis = true;

            } else if (dotes[i] == 15) {
                fintaMejorada = true;

            } else if (dotes[i] == 16 || dotes[i] == 17) {
                ataque1++;
                ataque2++;
                ataque3++;
                ataque4++;
                ataqueM1++;

            } else if (dotes[i] == 18 || dotes[i] == 19) {
                if (manos_2) {
                    dañoAtaque = dañoAtaque + 3;
                } else {
                    dañoAtaque = dañoAtaque + 2;
                }
                dañoAtaqueM = dañoAtaqueM + 2;


            } else if (dotes[i] == 3) {
                ataqueM2 = ataqueM1 - 5;

            } else if (dotes[i] == 4) {
                ataqueM3 = ataqueM1 - 10;
            }
        }
        return 1;
    }

    public int getId() {
        return id;
    }

    public String getTipoArma1() {
        return tipoArma1;
    }

    public boolean isManos_2() {
        return manos_2;
    }

    public String getTipoArma2() {
        return tipoArma2;
    }

    public int getAtaqueBase1() {
        return ataqueBase1;
    }

    public int getDañoAtaque() {
        return dañoAtaque;
    }

    public int getCriticoAtaque() {
        return criticoAtaque;
    }

    public int getMultiplicadorCritico() {
        return multiplicadorCritico;
    }

    public int getDadoAtaque() {
        return dadoAtaque;
    }

    public int getDañoAtaqueM() {
        return dañoAtaqueM;
    }

    public int getCriticoAtaqueM() {
        return criticoAtaqueM;
    }

    public int getMultiplicadorCriticoM() {
        return multiplicadorCriticoM;
    }

    public int getDadoAtaqueM() {
        return dadoAtaqueM;
    }

    public boolean isFintaGratis() {
        return fintaGratis;
    }

    public boolean isFintaMejorada() {
        return fintaMejorada;
    }

    public int getDadosPicaro() {
        return dadosPicaro;
    }

    public int[] getHechizosMago() {
        return hechizosMago;
    }

    public int getIniciativa() {
        return iniciativa;
    }

    public int getFuerza() {
        return fuerza;
    }

    public int getDestreza() {
        return destreza;
    }

    public int getConstitucion() {
        return constitucion;
    }

    public int getInteligencia() {
        return inteligencia;
    }

    public int getSabiduria() {
        return sabiduria;
    }

    public int getCarisma() {
        return carisma;
    }

    public int getPercepcion() {
        return percepcion;
    }

    public int getSigilo() {
        return sigilo;
    }

    public int getAveriguar() {
        return averiguar;
    }

    public int getEnganar() {
        return enganar;
    }

    public int getArmadura() {
        return armadura;
    }

    public int getArmaduraToque() {
        return armaduraToque;
    }

    public int getArmaduraDesprevenido() {
        return armaduraDesprevenido;
    }

    public int getsFortaleza() {
        return sFortaleza;
    }

    public int getsReflejos() {
        return sReflejos;
    }

    public int getsVoluntad() {
        return sVoluntad;
    }

    public int getAtaqueBase2() {
        return ataqueBase2;
    }

    public int getAtaqueBase3() {
        return ataqueBase3;
    }

    public int getAtaqueBase4() {
        return ataqueBase4;
    }

    public int getAtaque1() {
        return ataque1;
    }

    public int getAtaque2() {
        return ataque2;
    }

    public int getAtaque3() {
        return ataque3;
    }

    public int getAtaque4() {
        return ataque4;
    }

    public int getpGolpe() {
        return pGolpe;
    }

    public int getArma1() {
        return arma1;
    }

    public int getArma2() {
        return arma2;
    }

    public int getAtaqueM1() {
        return ataqueM1;
    }

    public int getAtaqueM2() {
        return ataqueM2;
    }

    public int getAtaqueM3() {
        return ataqueM3;
    }

    public String getNombre() {
        return nombre;
    }

    public String getClase() {
        return clase;
    }

    public int getNivel() {
        return nivel;
    }

    public int[] getDotes() {
        return dotes;
    }

    public String getRaza() {
        return raza;
    }

    public void setpGolpe(int pGolpe) {
        this.pGolpe = pGolpe;
    }

    public boolean isDesprevenido() {
        return desprevenido;
    }

    public void setDesprevenido(boolean desprevenido) {
        this.desprevenido = desprevenido;
    }

    public boolean isDormido() {
        return isDormido;
    }

    public void setDormido(boolean dormido) {
        isDormido = dormido;
    }

    public void setHechizosMago(int[] hechizosMago) {
        this.hechizosMago = hechizosMago;
    }
}
