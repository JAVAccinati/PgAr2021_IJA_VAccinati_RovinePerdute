package it.unibs.pgar.rovineperdute;

import java.util.Map;

public class Citta {

    private int id;
    private String nome;
    private int x;
    private int y;
    private int h;
    private Map<Integer, Integer[]> link; //primo valore dell'array: Tonatiuh (X, Y), secondo valore dell'array: Metztli (H)

    public Citta(int id, String nome, int x, int y, int h, Map<Integer, Integer[]> link) {
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

    public Map<Integer, Integer[]> getLink() {
        return link;
    }

    public void setLink(Map<Integer, Integer[]> link) {
        this.link = link;
    }

}
