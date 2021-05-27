package it.unibs.pgar.rovineperdute;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Citta {

    private int id;
    private String nome;
    private int x;
    private int y;
    private int h;
    private Map<Integer, Double[]> link; //primo valore dell'array: Tonatiuh (X, Y), secondo valore dell'array: Metztli (H)
    private Citta cittaPadreXY;
    private Citta cittaPadreH;
    private boolean finito;
    private double distanzaOrigine;
    private double distanzaStimata;
    private double distanzaRovineXY;
    private double distanzaRovineH;
    private int numeroCittaVisitate;
    private int indiceMassimo;

    public Citta(int id, String nome, int x, int y, int h, Map<Integer, Double[]> link) {
        this.id = id;
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.h = h;
        this.link = link;
        this.distanzaOrigine = -1;
        this.distanzaStimata = -1;
        this.numeroCittaVisitate = -1;
        this.indiceMassimo = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Map<Integer, Double[]> getLink() {
        return link;
    }

    public ArrayList<Integer> getKeyLink() {
        Map<Integer, Double[]> link = this.getLink();
        Set<Integer> setId = link.keySet();
        return new ArrayList<>(setId);
    }

    public void setLink(Map<Integer, Double[]> link) {
        this.link = link;
    }

    public Citta getCittaPadre(int indiceVeicolo) {
        if(indiceVeicolo == 0) {
            return cittaPadreXY;
        } else {
            return cittaPadreH;
        }
    }

    public void setCittaPadre(int indiceVeicolo, Citta cittaPadre) {
        if(indiceVeicolo == 0) {
            this.cittaPadreXY = cittaPadre;
        } else {
            this.cittaPadreH = cittaPadre;
        }
    }

    public boolean isFinito() {
        return finito;
    }

    public void setFinito(boolean finito) {
        this.finito = finito;
    }

    public double getDistanzaOrigine() {
        return distanzaOrigine;
    }

    public void setDistanzaOrigine(double distanzaOrigine) {
        this.distanzaOrigine = distanzaOrigine;
    }

    public double getDistanzaStimata() {
        return distanzaStimata;
    }

    public void setDistanzaStimata(double distanzaStimata) {
        this.distanzaStimata = distanzaStimata;
    }

    public double getDistanzaRovine(int indiceVeicolo) {
        if(indiceVeicolo == 0) {
            return distanzaRovineXY;
        } else {
            return distanzaRovineH;
        }
    }

    public void setDistanzaRovineXY(double distanzaRovineXY) {
            this.distanzaRovineXY = distanzaRovineXY;
    }

    public void setDistanzaRovineH(double distanzaRovineH) {
            this.distanzaRovineH = distanzaRovineH;
    }

    public int getNumeroCittaVisitate() {
        return numeroCittaVisitate;
    }

    public void setNumeroCittaVisitate(int numeroCittaVisitate) {
        this.numeroCittaVisitate = numeroCittaVisitate;
    }

    public int getIndiceMassimo() {
        return indiceMassimo;
    }

    public void setIndiceMassimo(int indiceMassimo) {
        this.indiceMassimo = indiceMassimo;
    }

}
