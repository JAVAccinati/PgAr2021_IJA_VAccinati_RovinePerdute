package it.unibs.pgar.rovineperdute;

import it.unibs.fp.ourlib.Nodo;
import it.unibs.fp.ourlib.*;

import java.util.*;

public class Mappa {

    public static final String TEAM_XY = "Tonathiu";
    public static final String TEAM_H = "Metztli";

    private static ArrayList<Citta> citta = new ArrayList<>();

    public static void main(String[] args) {
        String nomeFile = "src/PgAr_Map_200.xml";
        Nodo rootLettura = OurXMLReader.readFile(nomeFile);
        nuovaMappa(rootLettura);
        calcoloDistanze();
        calcolaLineaAria();

        setupAstar();
        AstarXY();
        AstarH();

        Nodo rootScrittura = creaRoutes();

        //scrivi XML

        //javadoc e UML

        //possiamo non duplicare XY e H?

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
        citta.get(0).setDistanzaOrigineXY(0);
        citta.get(0).setDistanzaStimataXY(0);
        citta.get(0).setDistanzaOrigineH(0);
        citta.get(0).setDistanzaStimataH(0);
    }

    public static void AstarXY() {
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
                double nuovaPossibileDistanza = cittaConsiderata.getDistanzaOrigineXY() + cittaConsiderata.getLink().get(keyList.get(i))[0];
                if (cittaDaCalcolare.getDistanzaOrigineXY() == -1 || cittaDaCalcolare.getDistanzaOrigineXY() > nuovaPossibileDistanza) {
                    sistemazioneNodiXY(cittaConsiderata, cittaDaCalcolare, nuovaPossibileDistanza);
                } else if (cittaDaCalcolare.getDistanzaOrigineXY() == nuovaPossibileDistanza) {
                    if (cittaDaCalcolare.getNumeroCittaVisitateXY() > cittaConsiderata.getNumeroCittaVisitateXY()) {
                        sistemazioneNodiXY(cittaConsiderata, cittaDaCalcolare, nuovaPossibileDistanza);
                    } else if (cittaDaCalcolare.getNumeroCittaVisitateXY() == cittaConsiderata.getNumeroCittaVisitateXY()) {
                        if (cittaDaCalcolare.getIndiceMassimoXY() < cittaConsiderata.getIndiceMassimoXY()) {
                            sistemazioneNodiXY(cittaConsiderata, cittaDaCalcolare, nuovaPossibileDistanza);
                        }
                    }
                }
            }
            cittaDaControllare.get(0).setFinito(true);
            cittaDaControllare.remove(0);
            for (int i = 1; i < cittaDaControllare.size(); i++) {
                if (cittaDaControllare.get(i).getDistanzaStimataXY() < cittaDaControllare.get(0).getDistanzaStimataXY()) {
                    Citta temp = cittaDaControllare.get(0);
                    cittaDaControllare.set(0, cittaDaControllare.get(i));
                    cittaDaControllare.set(i, temp);
                }
            }
        } while (cittaDaControllare.size() != 0);
    }

    private static void sistemazioneNodiXY(Citta cittaConsiderata, Citta cittaDaCalcolare, double nuovaPossibileDistanza) {
        cittaDaCalcolare.setDistanzaOrigineXY(nuovaPossibileDistanza);
        cittaDaCalcolare.setDistanzaStimataXY(cittaDaCalcolare.getDistanzaOrigineXY() + cittaDaCalcolare.getDistanzaRovineXY());
        cittaDaCalcolare.setCittaPadreXY(cittaConsiderata);
        cittaDaCalcolare.setNumeroCittaVisitateXY(cittaConsiderata.getNumeroCittaVisitateXY() + 1);
        if (cittaDaCalcolare.getIndiceMassimoXY() < cittaConsiderata.getIndiceMassimoXY()) {
            cittaDaCalcolare.setIndiceMassimoXY(cittaConsiderata.getIndiceMassimoXY());
        }
    }

    public static void AstarH() {
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
                double nuovaPossibileDistanza = cittaConsiderata.getDistanzaOrigineH() + cittaConsiderata.getLink().get(keyList.get(i))[0];
                if (cittaDaCalcolare.getDistanzaOrigineH() == -1 || cittaDaCalcolare.getDistanzaOrigineH() > nuovaPossibileDistanza) {
                    sistemazioneNodiH(cittaConsiderata, cittaDaCalcolare, nuovaPossibileDistanza);
                } else if (cittaDaCalcolare.getDistanzaOrigineH() == nuovaPossibileDistanza) {
                    if (cittaDaCalcolare.getNumeroCittaVisitateH() > cittaConsiderata.getNumeroCittaVisitateH()) {
                        sistemazioneNodiH(cittaConsiderata, cittaDaCalcolare, nuovaPossibileDistanza);
                    } else if (cittaDaCalcolare.getNumeroCittaVisitateH() == cittaConsiderata.getNumeroCittaVisitateH()) {
                        if (cittaDaCalcolare.getIndiceMassimoH() < cittaConsiderata.getIndiceMassimoH()) {
                            sistemazioneNodiH(cittaConsiderata, cittaDaCalcolare, nuovaPossibileDistanza);
                        }
                    }
                }
            }
            cittaDaControllare.get(0).setFinito(true);
            cittaDaControllare.remove(0);
            for (int i = 1; i < cittaDaControllare.size(); i++) {
                if (cittaDaControllare.get(i).getDistanzaStimataH() < cittaDaControllare.get(0).getDistanzaStimataH()) {
                    Citta temp = cittaDaControllare.get(0);
                    cittaDaControllare.set(0, cittaDaControllare.get(i));
                    cittaDaControllare.set(i, temp);
                }
            }
        } while (cittaDaControllare.size() != 0);
    }

    private static void sistemazioneNodiH(Citta cittaConsiderata, Citta cittaDaCalcolare, double nuovaPossibileDistanza) {
        cittaDaCalcolare.setDistanzaOrigineH(nuovaPossibileDistanza);
        cittaDaCalcolare.setDistanzaStimataH(cittaDaCalcolare.getDistanzaOrigineH() + cittaDaCalcolare.getDistanzaRovineH());
        cittaDaCalcolare.setCittaPadreH(cittaConsiderata);
        cittaDaCalcolare.setNumeroCittaVisitateH(cittaConsiderata.getNumeroCittaVisitateH() + 1);
        if (cittaDaCalcolare.getIndiceMassimoH() < cittaConsiderata.getIndiceMassimoH()) {
            cittaDaCalcolare.setIndiceMassimoH(cittaConsiderata.getIndiceMassimoH());
        }
    }

    public static Nodo creaRouteXY(Nodo nodoRoutes, String nomeTeam) {
        Citta rovinePerdute = citta.get(citta.size() - 1);

        Map<String, String> attributiRoute = new TreeMap<>();
        attributiRoute.put("team", nomeTeam);
        attributiRoute.put("cost", String.valueOf(rovinePerdute.getDistanzaOrigineXY()));
        attributiRoute.put("cities", String.valueOf(rovinePerdute.getNumeroCittaVisitateXY()));

        Nodo route = new Nodo("route", nodoRoutes, null, attributiRoute, null, null);

        ArrayList<Nodo> nodi = new ArrayList<>();
        Citta cittaDaAggiungere = rovinePerdute;
        Map<String, String> attributiCitta = new TreeMap<>();
        attributiCitta.put("id", String.valueOf(cittaDaAggiungere.getId()));
        attributiCitta.put("name", cittaDaAggiungere.getNome());
        Nodo nuovoNodo = new Nodo("city", route, null, attributiCitta, null, null);
        nodi.add(nuovoNodo);

        do{
            cittaDaAggiungere = cittaDaAggiungere.getCittaPadreXY();

            attributiCitta = new TreeMap<>();
            attributiCitta.put("id", String.valueOf(cittaDaAggiungere.getId()));
            attributiCitta.put("name", cittaDaAggiungere.getNome());

            nuovoNodo = new Nodo("city", route, null, attributiCitta, null, null);
            nodi.add(0, nuovoNodo);
        }while(cittaDaAggiungere.getCittaPadreXY() != null);

        route.setNodi(nodi);

        return route;
    }

    public static Nodo creaRouteH(Nodo nodoRoutes, String nomeTeam) {
        Citta rovinePerdute = citta.get(citta.size() - 1);

        Map<String, String> attributiRoute = new TreeMap<>();
        attributiRoute.put("team", nomeTeam);
        attributiRoute.put("cost", String.valueOf(rovinePerdute.getDistanzaOrigineH()));
        attributiRoute.put("cities", String.valueOf(rovinePerdute.getNumeroCittaVisitateH()));

        Nodo route = new Nodo("route", nodoRoutes, null, attributiRoute, null, null);

        ArrayList<Nodo> nodi = new ArrayList<>();
        Citta cittaDaAggiungere = rovinePerdute;
        Map<String, String> attributiCitta = new TreeMap<>();
        attributiCitta.put("id", String.valueOf(cittaDaAggiungere.getId()));
        attributiCitta.put("name", cittaDaAggiungere.getNome());
        Nodo nuovoNodo = new Nodo("city", route, null, attributiCitta, null, null);
        nodi.add(nuovoNodo);

        do{
            cittaDaAggiungere = cittaDaAggiungere.getCittaPadreH();

            attributiCitta = new TreeMap<>();
            attributiCitta.put("id", String.valueOf(cittaDaAggiungere.getId()));
            attributiCitta.put("name", cittaDaAggiungere.getNome());

            nuovoNodo = new Nodo("city", route, null, attributiCitta, null, null);
            nodi.add(0, nuovoNodo);
        }while(cittaDaAggiungere.getCittaPadreH() != null);

        route.setNodi(nodi);

        return route;
    }

    public static Nodo creaRoutes() {
        Nodo routes = new Nodo("routes", null);
        Nodo routeXY = creaRouteXY(routes, TEAM_XY);
        Nodo routeH = creaRouteH(routes, TEAM_H);
        ArrayList<Nodo> nodiRoutes = new ArrayList<>();
        nodiRoutes.add(routeXY);
        nodiRoutes.add(routeH);
        return routes;
    }

}
