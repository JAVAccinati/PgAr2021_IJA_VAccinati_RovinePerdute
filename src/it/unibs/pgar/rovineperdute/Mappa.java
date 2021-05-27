package it.unibs.pgar.rovineperdute;

import it.unibs.fp.ourlib.Nodo;
import it.unibs.fp.ourlib.*;

import java.util.*;

public class Mappa {

    public static final String[] NOMI_TEAM = {"Tonathiu", "Metztli"};

    private static ArrayList<Citta> citta = new ArrayList<>();

    public static void main(String[] args) {

        String nomeFileInput = "src/PgAr_Map_5.xml";
        String nomeFileOutput = "src/routes.xml";

        Nodo rootLettura = OurXMLReader.readFile(nomeFileInput);
        nuovaMappa(rootLettura);
        calcoloDistanze();
        calcolaLineaAria();

        ArrayList<Double> valoriXML = Astar(); //Costo carburante - Numero citta' - Costo carburante - Numero citta'

        Nodo rootScrittura = creaRoutes(valoriXML);

        OurXMLWriter.writeFile(nomeFileOutput, rootScrittura);

        //javadoc e UML

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
            ArrayList<Integer> id = cittaAttuale.getKeyLink();
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

    public static void calcolaLineaAria() {
        Citta rovine = citta.get(citta.size() - 1);
        for (int i = 0; i < citta.size(); i++) {
            Citta cittaDaCalcolare = citta.get(i);
            cittaDaCalcolare.setDistanzaRovineXY(Math.sqrt(Math.pow(cittaDaCalcolare.getX() - rovine.getX(), 2) + Math.pow(cittaDaCalcolare.getY() - rovine.getY(), 2)));
            cittaDaCalcolare.setDistanzaRovineH(Math.abs(cittaDaCalcolare.getH() - rovine.getH()));
        }
    }

    public static void setupAstar() {
        Citta campoBase = citta.get(0);
        campoBase.setDistanzaOrigine(0);
        campoBase.setDistanzaStimata(0);
        campoBase.setNumeroCittaVisitate(0);
        for (int i = 1; i < citta.size(); i++) {
            Citta cittaDaSistemare = citta.get(i);
            cittaDaSistemare.setDistanzaOrigine(-1);
            cittaDaSistemare.setDistanzaStimata(-1);
            cittaDaSistemare.setNumeroCittaVisitate(-1);
            cittaDaSistemare.setIndiceMassimo(cittaDaSistemare.getId());
            cittaDaSistemare.setFinito(false);
        }

    }

    public static ArrayList<Double> Astar() {
        ArrayList<Double> valoriXML = new ArrayList<>();
        for (int indiceVeicolo = 0; indiceVeicolo < 2; indiceVeicolo++) {
            setupAstar();
            ArrayList<Citta> cittaDaControllare = new ArrayList<>();
            cittaDaControllare.add(citta.get(0));
            do {
                Citta cittaConsiderata = cittaDaControllare.get(0);
                ArrayList<Integer> keyList = cittaConsiderata.getKeyLink();
                for (int i = 0; i < keyList.size(); i++) {
                    Citta cittaDaCalcolare = citta.get(keyList.get(i));
                    if (!cittaDaControllare.contains(cittaDaCalcolare) && !cittaDaCalcolare.isFinito()) {
                        cittaDaControllare.add(cittaDaCalcolare);
                    }
                    double nuovaPossibileDistanza = cittaConsiderata.getDistanzaOrigine() + cittaConsiderata.getLink().get(keyList.get(i))[indiceVeicolo];
                    if (cittaDaCalcolare.getDistanzaOrigine() == -1 || cittaDaCalcolare.getDistanzaOrigine() > nuovaPossibileDistanza) {
                        sistemazioneNodi(indiceVeicolo, cittaConsiderata, cittaDaCalcolare, nuovaPossibileDistanza);
                    } else if (cittaDaCalcolare.getDistanzaOrigine() == nuovaPossibileDistanza) {
                        if (cittaDaCalcolare.getNumeroCittaVisitate() > cittaConsiderata.getNumeroCittaVisitate()) {
                            sistemazioneNodi(indiceVeicolo, cittaConsiderata, cittaDaCalcolare, nuovaPossibileDistanza);
                        } else if (cittaDaCalcolare.getNumeroCittaVisitate() == cittaConsiderata.getNumeroCittaVisitate()) {
                            if (cittaDaCalcolare.getIndiceMassimo() < cittaConsiderata.getIndiceMassimo()) {
                                sistemazioneNodi(indiceVeicolo, cittaConsiderata, cittaDaCalcolare, nuovaPossibileDistanza);
                            }
                        }
                    }
                }
                cittaDaControllare.get(0).setFinito(true);
                cittaDaControllare.remove(0);
                for (int i = 1; i < cittaDaControllare.size(); i++) {
                    if (cittaDaControllare.get(i).getDistanzaStimata() < cittaDaControllare.get(0).getDistanzaStimata()) {
                        Citta temp = cittaDaControllare.get(0);
                        cittaDaControllare.set(0, cittaDaControllare.get(i));
                        cittaDaControllare.set(i, temp);
                    }
                }
            } while (cittaDaControllare.size() != 0);
            Citta rovine = citta.get(citta.size() - 1);
            valoriXML.add(rovine.getDistanzaOrigine());
            valoriXML.add((double) rovine.getNumeroCittaVisitate());
        }
        return valoriXML;
    }

    private static void sistemazioneNodi(int indiceVeicolo, Citta cittaConsiderata, Citta cittaDaCalcolare, double nuovaPossibileDistanza) {
        cittaDaCalcolare.setDistanzaOrigine(nuovaPossibileDistanza);
        cittaDaCalcolare.setDistanzaStimata(cittaDaCalcolare.getDistanzaOrigine() + cittaDaCalcolare.getDistanzaRovine(indiceVeicolo));
        cittaDaCalcolare.setCittaPadre(indiceVeicolo, cittaConsiderata);
        cittaDaCalcolare.setNumeroCittaVisitate(cittaConsiderata.getNumeroCittaVisitate() + 1);
        if (cittaDaCalcolare.getIndiceMassimo() < cittaConsiderata.getIndiceMassimo()) {
            cittaDaCalcolare.setIndiceMassimo(cittaConsiderata.getIndiceMassimo());
        }
    }

    public static Nodo creaRoute(int indiceVeicolo, Nodo nodoRoutes, ArrayList<Double> valoriXML) {
        Citta rovinePerdute = citta.get(citta.size() - 1);

        Map<String, String> attributiRoute = new TreeMap<>();
        attributiRoute.put("team", NOMI_TEAM[indiceVeicolo]);
        attributiRoute.put("cost", String.valueOf(valoriXML.get(indiceVeicolo * 2)));
        attributiRoute.put("cities", String.valueOf(valoriXML.get(1 + indiceVeicolo * 2)));

        Nodo route = new Nodo("route", nodoRoutes, null, attributiRoute, null, null);

        ArrayList<Nodo> nodi = new ArrayList<>();
        Citta cittaDaAggiungere = rovinePerdute;
        Map<String, String> attributiCitta = new TreeMap<>();
        attributiCitta.put("id", String.valueOf(cittaDaAggiungere.getId()));
        attributiCitta.put("name", cittaDaAggiungere.getNome());
        Nodo nuovoNodo = new Nodo("city", route, null, attributiCitta, null, null);
        nodi.add(nuovoNodo);

        do {
            cittaDaAggiungere = cittaDaAggiungere.getCittaPadre(indiceVeicolo);

            attributiCitta = new TreeMap<>();
            attributiCitta.put("id", String.valueOf(cittaDaAggiungere.getId()));
            attributiCitta.put("name", cittaDaAggiungere.getNome());

            nuovoNodo = new Nodo("city", route, null, attributiCitta, null, null);
            nodi.add(0, nuovoNodo);
        } while (cittaDaAggiungere.getCittaPadre(indiceVeicolo) != null);

        route.setNodi(nodi);

        return route;
    }

    public static Nodo creaRoutes(ArrayList<Double> valoriXML) {
        Nodo routes = new Nodo("routes", null);
        Nodo routeXY = creaRoute(0, routes, valoriXML);
        Nodo routeH = creaRoute(1, routes, valoriXML);
        ArrayList<Nodo> nodiRoutes = new ArrayList<>();
        nodiRoutes.add(routeXY);
        nodiRoutes.add(routeH);
        routes.setNodi(nodiRoutes);
        return routes;
    }

}
