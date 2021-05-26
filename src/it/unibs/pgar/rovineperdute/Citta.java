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
    private Citta cittaPadre;
    private boolean finito;
    private double distanzaOrigine;
    private double distanzaStimata;
    private double distanzaRovineXY;
    private double distanzaRovineH;

    public Citta(int id, String nome, int x, int y, int h, Map<Integer, Double[]> link) {
        this.id = id;
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.h = h;
        this.link = link;
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

    public Citta getCittaPadre() {
        return cittaPadre;
    }

    public void setCittaPadre(Citta cittaPadre) {
        this.cittaPadre = cittaPadre;
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

    public double getDistanzaRovineXY() {
        return distanzaRovineXY;
    }

    public void setDistanzaRovineXY(double distanzaRovineXY) {
        this.distanzaRovineXY = distanzaRovineXY;
    }

    public double getDistanzaRovineH() {
        return distanzaRovineH;
    }

    public void setDistanzaRovineH(double distanzaRovineH) {
        this.distanzaRovineH = distanzaRovineH;
    }

}
