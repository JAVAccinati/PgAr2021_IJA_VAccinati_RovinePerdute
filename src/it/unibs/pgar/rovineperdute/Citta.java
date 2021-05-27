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
    private double distanzaOrigineXY;
    private double distanzaStimataXY;
    private double distanzaOrigineH;
    private double distanzaStimataH;
    private double distanzaRovineXY;
    private double distanzaRovineH;
    private int numeroCittaVisitateXY;
    private int indiceMassimoXY;
    private int numeroCittaVisitateH;
    private int indiceMassimoH;

    public Citta(int id, String nome, int x, int y, int h, Map<Integer, Double[]> link) {
        this.id = id;
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.h = h;
        this.link = link;
        this.distanzaOrigineXY = -1;
        this.distanzaStimataXY = -1;
        this.distanzaOrigineH = -1;
        this.distanzaStimataH = -1;
        this.numeroCittaVisitateXY = -1;
        this.indiceMassimoXY = id;
        this.numeroCittaVisitateH = -1;
        this.indiceMassimoH = id;
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

    public Citta getCittaPadreXY() {
        return cittaPadreXY;
    }

    public void setCittaPadreXY(Citta cittaPadreXY) {
        this.cittaPadreXY = cittaPadreXY;
    }

    public Citta getCittaPadreH() {
        return cittaPadreH;
    }

    public void setCittaPadreH(Citta cittaPadreH) {
        this.cittaPadreH = cittaPadreH;
    }

    public boolean isFinito() {
        return finito;
    }

    public void setFinito(boolean finito) {
        this.finito = finito;
    }

    public double getDistanzaOrigineXY() {
        return distanzaOrigineXY;
    }

    public void setDistanzaOrigineXY(double distanzaOrigineXY) {
        this.distanzaOrigineXY = distanzaOrigineXY;
    }

    public double getDistanzaStimataXY() {
        return distanzaStimataXY;
    }

    public void setDistanzaStimataXY(double distanzaStimataXY) {
        this.distanzaStimataXY = distanzaStimataXY;
    }

    public double getDistanzaOrigineH() {
        return distanzaOrigineH;
    }

    public void setDistanzaOrigineH(double distanzaOrigineH) {
        this.distanzaOrigineH = distanzaOrigineH;
    }

    public double getDistanzaStimataH() {
        return distanzaStimataH;
    }

    public void setDistanzaStimataH(double distanzaStimataH) {
        this.distanzaStimataH = distanzaStimataH;
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

    public int getNumeroCittaVisitateXY() {
        return numeroCittaVisitateXY;
    }

    public void setNumeroCittaVisitateXY(int numeroCittaVisitateXY) {
        this.numeroCittaVisitateXY = numeroCittaVisitateXY;
    }

    public int getIndiceMassimoXY() {
        return indiceMassimoXY;
    }

    public void setIndiceMassimoXY(int indiceMassimoXY) {
        this.indiceMassimoXY = indiceMassimoXY;
    }

    public int getNumeroCittaVisitateH() {
        return numeroCittaVisitateH;
    }

    public void setNumeroCittaVisitateH(int numeroCittaVisitateH) {
        this.numeroCittaVisitateH = numeroCittaVisitateH;
    }

    public int getIndiceMassimoH() {
        return indiceMassimoH;
    }

    public void setIndiceMassimoH(int indiceMassimoH) {
        this.indiceMassimoH = indiceMassimoH;
    }

}
