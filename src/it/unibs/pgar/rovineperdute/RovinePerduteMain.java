package it.unibs.pgar.rovineperdute;

import it.unibs.fp.ourlib.OurMenu;

/**
 * Visto che il main era piu' nudo di Rocco ai tempi d'oro questa volta
 * abbiamo deciso di accorpare qui le uniche interazioni con l'utente.
 */
public class RovinePerduteMain {

    //COSTANTI

    public static final String[] NOMI_FILE_INPUT = {"src/PgAr_Map_5.xml", "src/PgAr_Map_12.xml",
            "src/PgAr_Map_50.xml", "src/PgAr_Map_200.xml", "src/PgAr_Map_2000.xml", "src/PgAr_Map_10000.xml"};
    public static final String[] NOMI_FILE_OUTPUT = {"src/Routes_5.xml", "src/Routes_12.xml",
            "src/Routes_50.xml", "src/Routes_200.xml", "src/Routes_2000.xml", "src/Routes_10000.xml", "src/Routes.xml"};
    public static final int[] numeroCitta = {5, 12, 50, 200, 2000, 10000};

    public static final String TITOLO = "" +
            "██████╗░░█████╗░██╗░░░██╗██╗███╗░░██╗███████╗\t  ██████╗░███████╗██████╗░██████╗░██╗░░░██╗████████╗███████╗\n" +
            "██╔══██╗██╔══██╗██║░░░██║██║████╗░██║██╔════╝\t  ██╔══██╗██╔════╝██╔══██╗██╔══██╗██║░░░██║╚══██╔══╝██╔════╝\n" +
            "██████╔╝██║░░██║╚██╗░██╔╝██║██╔██╗██║█████╗░░\t  ██████╔╝█████╗░░██████╔╝██║░░██║██║░░░██║░░░██║░░░█████╗░░\n" +
            "██╔══██╗██║░░██║░╚████╔╝░██║██║╚████║██╔══╝░░\t  ██╔═══╝░██╔══╝░░██╔══██╗██║░░██║██║░░░██║░░░██║░░░██╔══╝░░\n" +
            "██║░░██║╚█████╔╝░░╚██╔╝░░██║██║░╚███║███████╗\t  ██║░░░░░███████╗██║░░██║██████╔╝╚██████╔╝░░░██║░░░███████╗\n" +
            "╚═╝░░╚═╝░╚════╝░░░░╚═╝░░░╚═╝╚═╝░░╚══╝╚══════╝\t  ╚═╝░░░░░╚══════╝╚═╝░░╚═╝╚═════╝░░╚═════╝░░░░╚═╝░░░╚══════╝\n";

    public static final String TITOLO_MENU = "Ricerca la via piu' rapida per i nostri valorosi ricercatori";
    public static final String[] SCELTE_MENU = {"Mappa di 5 citta'", "Mappa di 12 citta'",
            "Mappa di 50 citta'", "Mappa di 200 citta'", "Mappa di 2000 citta'", "Mappa di 10000 citta'", "eaSteR eGg"};

    public static final String MSG_INIZIO_CALCOLO = "Inizio calcolo rotta della mappa con %d citta'";
    public static final String MSG_FINE_CALCOLO = "Calcolo eseguito in %.2f s";
    public static double NANO_SECONDI_IN_SECONDO = 10e8; //?!

    public static final String MSG_FINE = "\n\nGrazie per averci affidato questo prestigioso incarico.\n" +
            "Buona fortuna per la vostra spedizione!\n" +
            "- I JA_VAccinati";

    /**
     * Mostra all'utente le varie mappe e, in base alla scelta, ne calcola il percorso piu' efficace
     * @param args: String[]
     */
    public static void main(String[] args) {
        //Ringraziamo gli sviluppatori di MyMenu, base portante del nostro OurMenu
        OurMenu menu = new OurMenu(TITOLO_MENU, SCELTE_MENU);
        System.out.println(TITOLO);
        int scelta;
        do {
            scelta = menu.scegli();
            System.out.println();
            switch(scelta) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    eseguiCalcoloPercorso(numeroCitta[scelta - 1], scelta - 1, 6);
                    break;
                case 7:
                    for (int i = 0; i < 6; i++) {
                        eseguiCalcoloPercorso(numeroCitta[i], i, i);
                        System.out.println();
                    }
                    break;
                case 0:
                default:
                    System.out.println(MSG_FINE);
                    break;
            }
            System.out.println();
        }while(scelta != 0);
    }

    /**
     * Esegue il calcolo del percorso della mappa desiderata.
     * Stampa inoltre altre informazioni perche' vogliamo tirarcela
     * @param indiceCitta: int
     * @param indiceFileInput: int
     * @param indiceFileOutput: int
     */
    public static void eseguiCalcoloPercorso(int indiceCitta, int indiceFileInput, int indiceFileOutput) {
        //La prima esecuzione richiede piu' tempo, a prescindere dal file scelto in input.
        //Pensiamo sia perche' deve preparare delle risorse che utilizza anche successivamente,
        //ma che poi non devono essere ri-inizializzate
        long startTime = System.nanoTime();
        System.out.println(String.format(MSG_INIZIO_CALCOLO, indiceCitta));
        Mappa.calcolaPercorsi(NOMI_FILE_INPUT[indiceFileInput], NOMI_FILE_OUTPUT[indiceFileOutput]);
        double tempoTrascorso = (System.nanoTime() - startTime) / NANO_SECONDI_IN_SECONDO;
        System.out.println(String.format(MSG_FINE_CALCOLO, tempoTrascorso));
    }

}
