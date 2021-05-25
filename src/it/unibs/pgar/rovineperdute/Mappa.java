package it.unibs.pgar.rovineperdute;

import it.unibs.fp.ourlib.Nodo;
import it.unibs.fp.ourlib.*;

import java.util.*;

public class Mappa {

    private static ArrayList<Citta> citta = new ArrayList<>();

    public static void main(String[] args) {
        String nomeFile = "src/PgAr_Map_5.xml";
        Nodo root = OurXMLReader.readFile(nomeFile);
        nuovaMappa(root);
        calcoloDistanze();

        System.out.println("ciao");
    }

    public static void nuovaMappa(Nodo root) {
        for (int i = 0; i < root.getNodi().size(); i++) {
            Nodo nodoAttuale = root.getNodi().get(i);
            Map<String, String> attributiNodoAttuale = nodoAttuale.getAttributi();
            ArrayList<String> attributiArray = new ArrayList<>();
            attributiArray.addAll(attributiNodoAttuale.keySet());
            int id = 0;
            String nome = "";
            int x = 0, y = 0, h = 0;
            for (int j = 0; j < attributiArray.size(); j++) {
                switch (attributiArray.get(j)) {
                    case "id":
                        id = Integer.parseInt(attributiNodoAttuale.get(attributiArray.get(j)));
                        break;
                    case "name":
                        nome = attributiNodoAttuale.get(attributiArray.get(j));
                        break;
                    case "x":
                        x = Integer.parseInt(attributiNodoAttuale.get(attributiArray.get(j)));
                        break;
                    case "y":
                        y = Integer.parseInt(attributiNodoAttuale.get(attributiArray.get(j)));
                        break;
                    case "h":
                        h = Integer.parseInt(attributiNodoAttuale.get(attributiArray.get(j)));
                        break;
                }
            }
            Map<Integer, Double[]> link = new HashMap<>();
            for (int j = 0; j < nodoAttuale.getNodi().size(); j++) {
                Nodo linkAttuale = nodoAttuale.getNodi().get(j);
                int idLink = Integer.parseInt(linkAttuale.getAttributi().get("to"));
                Double[] distanze = new Double[]{0.0, 0.0};
                link.put(idLink, distanze);
            }
            Citta cittaAttuale = new Citta(id, nome, x, y, h, link);
            citta.add(cittaAttuale);
        }
    }

    public static void calcoloDistanze() {
        for (int i = 0; i < citta.size(); i++) {
            Citta cittaAttuale = citta.get(i);
            Map<Integer, Double[]> link = cittaAttuale.getLink();
            Set<Integer> setId = link.keySet();                      //#perclarezza
            ArrayList<Integer> id = new ArrayList<>(setId);
            for (int j = 0; j < id.size(); j++) {
                int idLink = id.get(j);
                Citta cittaLink = citta.get(idLink);
                double distanzaXY, distanzaH;
                distanzaXY = Math.sqrt(Math.pow(cittaAttuale.getX() - cittaLink.getX(), 2) + Math.pow(cittaAttuale.getY() - cittaLink.getY(), 2));
                distanzaH = Math.abs(cittaAttuale.getH() - cittaLink.getH());
                Double[] distanze = new Double[]{distanzaXY, distanzaH};
                link.put(idLink, distanze);
            }
        }
    }
}
