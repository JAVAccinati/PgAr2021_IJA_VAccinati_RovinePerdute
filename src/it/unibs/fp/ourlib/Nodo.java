package it.unibs.fp.ourlib;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Nodo {

    private String nome;
    private Nodo nodoPadre;
    private ArrayList<Nodo> nodi;
    private Map<String, String> attributi;
    private String contenuto;
    private ArrayList<String> commenti;

    public Nodo() {
        this.nome = "";
        this.nodoPadre = null;
        this.nodi = new ArrayList<>();
        this.attributi = new TreeMap<>();
        this.contenuto = "";
        this.commenti = new ArrayList<>();
    }

    public Nodo(String nome, Nodo nodoPadre) {
        this.nome = nome;
        this.nodoPadre = nodoPadre;
        this.nodi = new ArrayList<>();
        this.attributi = new TreeMap<>();
        this.contenuto = "";
        this.commenti = new ArrayList<>();
    }

    public Nodo(String nome, Nodo nodoPadre, ArrayList<Nodo> nodi, Map<String, String> attributi, String contenuto, ArrayList<String> commenti) {
        this.nome = nome;
        this.nodoPadre = nodoPadre;
        if(nodi != null) {
            this.nodi = nodi;
        } else {
            this.nodi = new ArrayList<>();
        }
        if(attributi != null) {
            this.attributi = attributi;
        } else {
            this.attributi = new TreeMap<>();
        }
        this.contenuto = contenuto;
        this.commenti = commenti;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Nodo getNodoPadre() {
        return nodoPadre;
    }

    public void setNodoPadre(Nodo nodoPadre) {
        this.nodoPadre = nodoPadre;
    }

    public ArrayList<Nodo> getNodi() {
        return nodi;
    }

    public void setNodi(ArrayList<Nodo> nodi) {
        this.nodi = nodi;
    }

    public Map<String, String> getAttributi() {
        return attributi;
    }

    public void setAttributi(Map<String, String> attributi) {
        this.attributi = attributi;
    }

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public ArrayList<String> getCommenti() {
        return commenti;
    }

    public void setCommenti(ArrayList<String> commenti) {
        this.commenti = commenti;
    }

    public void addNodo(Nodo nodo) {
        nodi.add(nodo);
    }

    public void addAttributo(String nomeAttributo, String valoreAttributo) {
        attributi.put(nomeAttributo, valoreAttributo);
    }

    public void addCommento(String commento) {
        commenti.add(commento);
    }

}
