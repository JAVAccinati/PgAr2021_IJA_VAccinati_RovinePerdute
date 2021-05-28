package it.unibs.pgar.rovineperdute;

import it.unibs.fp.ourlib.Nodo;
import it.unibs.fp.ourlib.*;

import java.util.*;

/**
 * Si occupa della creazione del percorso piu' efficiente.
 * Per questo fine crea un array delle citta' dopo aver ricevuto il nodo radice dalla lettura del file XML e converte
 * il percorso, calcolato grazie ad un algoritmo A*, in un albero per poter chiamare il writerXML
 */
public class Mappa {

    //COSTANTI

    public static final String[] NOMI_TEAM = {"Tonathiu", "Metztli"};

    //ATTRIBUTI

    private static ArrayList<Citta> citta;

    private static double[] distanze = new double[2];
    private static int[] numeroCitta = new int[2];

    //METODI

    /**
     * Il cuore della classe Mappa; aggrega tutte le chiamate ai metodi necessarie perche'
     * la classe svolga il suo compito
     * @param nomeFileInput: String
     * @param nomeFileOutput: String
     */
    public static void calcolaPercorsi(String nomeFileInput, String nomeFileOutput) {
        citta = new ArrayList<>();

        //inizializzazione dell'array delle citta'
        Nodo rootLettura = OurXMLReader.readFile(nomeFileInput);
        nuovaMappa(rootLettura);

        //calcolo attributi mancanti delle citta'
        calcoloDistanze();
        calcolaLineaAria();

        //algoritmo magico
        Astar();

        //conversione e scrittura del file risultante
        Nodo rootScrittura = creaRoutes();
        OurXMLWriter.writeFile(nomeFileOutput, rootScrittura);
    }

    /**
     * Grazie a questo metodo inizializziamo un'array di citta' con tutte le informazioni necessarie
     * partendo dal nodo radice dell'albero ottenuto dalla lettura del file input.
     * @param root: Nodo
     */
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

    /**
     * Calcola e salva nell'apposito attributo le distanze che ci sono tra una citta' e quelle ad essa
     * collegate.
     * Questo avviene per ogni citta' salvata.
     */
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

    /**
     * Calcola la distanza il linea d'aria, sia lungo il piano X,Y che lungo H, tra le rovine e ogni citta'.
     * Salva cosi' questo valore nell'apposito attributo di citta'.
     * Questa distanza ci e' necessaria per la definizione di funzione euristica nell'algoritmo A*.
     */
    public static void calcolaLineaAria() {
        Citta rovine = citta.get(citta.size() - 1);
        for (int i = 0; i < citta.size(); i++) {
            Citta cittaDaCalcolare = citta.get(i);
            cittaDaCalcolare.setDistanzaRovineXY(Math.sqrt(Math.pow(cittaDaCalcolare.getX() - rovine.getX(), 2) + Math.pow(cittaDaCalcolare.getY() - rovine.getY(), 2)));
            cittaDaCalcolare.setDistanzaRovineH(Math.abs(cittaDaCalcolare.getH() - rovine.getH()));
        }
    }

    /**
     * Setup degli attributi delle citta' prima di poter applicare l'algoritmo A*
     */
    public static void setupAstar() {
        Citta campoBase = citta.get(0);
        campoBase.setDistanzaOrigine(0);
        campoBase.setDistanzaStimata(0);
        campoBase.setNumeroCittaVisitate(1);
        for (int i = 1; i < citta.size(); i++) {
            Citta cittaDaSistemare = citta.get(i);
            cittaDaSistemare.setDistanzaOrigine(-1);
            cittaDaSistemare.setDistanzaStimata(-1);
            cittaDaSistemare.setNumeroCittaVisitate(-1);
            cittaDaSistemare.setIndiceMassimo(cittaDaSistemare.getId());
            cittaDaSistemare.setFinito(false);
        }

    }

    /**
     * Applicazione dell'algoritmo A* ad un array di citta'.
     * Il metodo svolge conseguentemente l'algoritmo per le necessita' del veicolo "Tonathiu"(0) e poi per Metztli(1).
     *
     * Come funzione euristica consideriamo la somma tra la distanza reale della citta' dal campo base e la distanza in
     * linea d'aria con le rovine perdute.
     */
    public static void Astar() {
        for (int indiceVeicolo = 0; indiceVeicolo < 2; indiceVeicolo++) {
            setupAstar();
            //cittaDaControllare -> citta' dalla quale ci si deve spostare
            //cittaConsiderata -> citta' collegata a cittaDaControllare di cui si sta analizzando l'efficienza del percorso
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
                    //valore della funzione euristica
                    double nuovaPossibileDistanza = cittaConsiderata.getDistanzaOrigine() + cittaConsiderata.getLink().get(keyList.get(i))[indiceVeicolo];
                    if (cittaDaCalcolare.getDistanzaOrigine() == -1 || cittaDaCalcolare.getDistanzaOrigine() > nuovaPossibileDistanza) {
                        sistemazioneNodi(indiceVeicolo, cittaConsiderata, cittaDaCalcolare, nuovaPossibileDistanza);
                    } //controllo caso due percorsi con lo stesso costo in carburante
                    else if (cittaDaCalcolare.getDistanzaOrigine() == nuovaPossibileDistanza) {
                        if (cittaDaCalcolare.getNumeroCittaVisitate() > cittaConsiderata.getNumeroCittaVisitate()) {
                            sistemazioneNodi(indiceVeicolo, cittaConsiderata, cittaDaCalcolare, nuovaPossibileDistanza);
                        } //controllo caso due percorsi con lo stesso costo in carburante E stesso numero di citta' visitate
                        else if (cittaDaCalcolare.getNumeroCittaVisitate() == cittaConsiderata.getNumeroCittaVisitate()) {
                            if (cittaDaCalcolare.getIndiceMassimo() < cittaConsiderata.getIndiceMassimo()) {
                                sistemazioneNodi(indiceVeicolo, cittaConsiderata, cittaDaCalcolare, nuovaPossibileDistanza);
                            }
                        }
                    }
                }
                cittaDaControllare.get(0).setFinito(true);
                cittaDaControllare.remove(0);
                //Selezione secondo funzione euristica della prossima citta da cui muoversi
                for (int i = 1; i < cittaDaControllare.size(); i++) {
                    if (cittaDaControllare.get(i).getDistanzaStimata() < cittaDaControllare.get(0).getDistanzaStimata()) {
                        Citta temp = cittaDaControllare.get(0);
                        cittaDaControllare.set(0, cittaDaControllare.get(i));
                        cittaDaControllare.set(i, temp);
                    }
                }
            } while (cittaDaControllare.size() != 0);
            Citta rovine = citta.get(citta.size() - 1);
            distanze[indiceVeicolo] = Math.round(rovine.getDistanzaOrigine() * 100.0) / 100.0;
            numeroCitta[indiceVeicolo] = rovine.getNumeroCittaVisitate();
        }
    }

    /**
     * Aggiornamento dei valori della citta' che e' stata calcolata
     * @param indiceVeicolo: int
     * @param cittaConsiderata: Citta
     * @param cittaDaCalcolare: Citta
     * @param nuovaPossibileDistanza: double
     */
    private static void sistemazioneNodi(int indiceVeicolo, Citta cittaConsiderata, Citta cittaDaCalcolare, double nuovaPossibileDistanza) {
        cittaDaCalcolare.setDistanzaOrigine(nuovaPossibileDistanza);
        cittaDaCalcolare.setDistanzaStimata(cittaDaCalcolare.getDistanzaOrigine() + cittaDaCalcolare.getDistanzaRovine(indiceVeicolo));
        cittaDaCalcolare.setCittaPadre(indiceVeicolo, cittaConsiderata);
        cittaDaCalcolare.setNumeroCittaVisitate(cittaConsiderata.getNumeroCittaVisitate() + 1);
        if (cittaDaCalcolare.getIndiceMassimo() < cittaConsiderata.getIndiceMassimo()) {
            cittaDaCalcolare.setIndiceMassimo(cittaConsiderata.getIndiceMassimo());
        }
    }

    /**
     * Crea l'albero rappresentante un singolo percorso migliore (o per Tonathiu o per Metztli).
     * Ritorna il nodo radice.
     * @param indiceVeicolo: int
     * @param nodoRoutes: Nodo
     * @return nodoRadice: Nodo
     */
    public static Nodo creaRoute(int indiceVeicolo, Nodo nodoRoutes) {
        Citta rovinePerdute = citta.get(citta.size() - 1);

        Map<String, String> attributiRoute = new TreeMap<>();
        //La dicitura nNomeAttributo la usiamo per poter ordinare a nostro piacimento i vari attributi
        attributiRoute.put("1team", NOMI_TEAM[indiceVeicolo]);
        attributiRoute.put("2cost", String.valueOf(distanze[indiceVeicolo]));
        attributiRoute.put("3cities", String.valueOf(numeroCitta[indiceVeicolo]));

        Nodo route = new Nodo("route", nodoRoutes, null, attributiRoute, null, null);

        ArrayList<Nodo> nodi = new ArrayList<>();
        Citta cittaDaAggiungere = rovinePerdute;
        Map<String, String> attributiCitta = new TreeMap<>();
        attributiCitta.put("1id", String.valueOf(cittaDaAggiungere.getId()));
        attributiCitta.put("2name", cittaDaAggiungere.getNome());
        Nodo nuovoNodo = new Nodo("city", route, null, attributiCitta, null, null);
        nodi.add(nuovoNodo);

        do {
            cittaDaAggiungere = cittaDaAggiungere.getCittaPadre(indiceVeicolo);

            attributiCitta = new TreeMap<>();
            attributiCitta.put("1id", String.valueOf(cittaDaAggiungere.getId()));
            attributiCitta.put("2name", cittaDaAggiungere.getNome());

            nuovoNodo = new Nodo("city", route, null, attributiCitta, null, null);
            nodi.add(0, nuovoNodo);
        } while (cittaDaAggiungere.getCittaPadre(indiceVeicolo) != null);

        route.setNodi(nodi);

        return route;
    }

    /**
     * Accorpa i 2 percorsi (X,Y e H) unendoli in un solo albero di cui restituisce il nodo radice
     * @return nodoRadice: Nodo
     */
    public static Nodo creaRoutes() {
        Nodo routes = new Nodo("routes", null);
        Nodo routeXY = creaRoute(0, routes);
        Nodo routeH = creaRoute(1, routes);
        ArrayList<Nodo> nodiRoutes = new ArrayList<>();
        nodiRoutes.add(routeXY);
        nodiRoutes.add(routeH);
        routes.setNodi(nodiRoutes);
        return routes;
    }

}
