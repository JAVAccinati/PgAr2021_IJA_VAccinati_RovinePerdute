package it.unibs.fp.ourlib;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;

/**
 * Our beloved XML reader.
 * Da un file XML restituisce il nodo radice di albero che contiene tutte le informazioni necessarie
 */
public class OurXMLReader {

    /**
     * Esattamente quello che ho detto sopra
     * @param filePath: String
     * @return nodoRadice: Nodo
     */
    public static Nodo readFile(String filePath) {

        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;

        Nodo root = new Nodo();

        try {
            String path = new File(filePath).getPath();
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(path, new FileInputStream(path));

            Nodo nodoAttuale = root;
            Nodo nuovoNodo = null;

            while (xmlr.hasNext()) { // continua a leggere finché ha eventi a disposizione
                switch (xmlr.getEventType()) { // switch sul tipo di evento
                    case XMLStreamConstants.START_DOCUMENT: // inizio del documento
                        //System.out.println("Start Read Doc " + path);
                        break;
                    case XMLStreamConstants.START_ELEMENT: // inizio di un elemento
                        if (root.getNome().equals("")) {
                            root.setNome(xmlr.getLocalName());
                        } else {
                            nuovoNodo = new Nodo(xmlr.getLocalName(), nodoAttuale);
                            nodoAttuale.addNodo(nuovoNodo);
                            nodoAttuale = nuovoNodo;
                        }
                        for (int i = 0; i < xmlr.getAttributeCount(); i++) {
                            nodoAttuale.addAttributo(xmlr.getAttributeLocalName(i), xmlr.getAttributeValue(i));
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT: // fine di un elemento
                        //System.out.println("END-Tag " + xmlr.getLocalName());
                        nodoAttuale = nodoAttuale.getNodoPadre();
                        break;
                    case XMLStreamConstants.COMMENT: // commenti
                        nodoAttuale.addCommento(xmlr.getText());
                        break;
                    case XMLStreamConstants.CHARACTERS: // content all’interno di un elemento
                        if (xmlr.getText().trim().length() > 0) { // controlla se il testo non contiene solo spazi
                            nodoAttuale.setContenuto(xmlr.getText());
                        }
                        break;
                }
                xmlr.next();
            }

        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del reader:");
            System.out.println(e.getMessage());
        }

        return root;
    }

}
