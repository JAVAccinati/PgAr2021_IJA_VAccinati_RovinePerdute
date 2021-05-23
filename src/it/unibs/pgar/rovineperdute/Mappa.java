package it.unibs.pgar.rovineperdute;

import it.unibs.fp.ourlib.Nodo;
import it.unibs.fp.ourlib.*;

import java.util.ArrayList;

public class Mappa {

    private ArrayList<Citta> citta;

    public static void main(String[] args) {
        String nomeFile = "src/PgAr_Map_5.xml";
        Nodo root = OurXMLReader.readFile(nomeFile);
        nuovaMappa(root);
    }

    public static void nuovaMappa(Nodo root) {

    }

}
