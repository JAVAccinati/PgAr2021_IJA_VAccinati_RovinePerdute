package it.unibs.fp.ourlib;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;

/**
 * Our even more beloved XML writer
 *
 * A partire da un nodo radice di un albero scrive in un file l'XML corrispondente
 */
public class OurXMLWriter implements XMLStreamWriter {

    //ATTRIBUTI

    private static XMLStreamWriter xmlw = null;
    private int indentazione = 0;

    //COSTRUTTORE

    public OurXMLWriter(String nomeFile) {
        XMLOutputFactory xmlof = null;
        try {
            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(nomeFile), "utf-8");
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del writer: ");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Come ho detto in testa alla classe
     * @param nomeFile: String
     * @param root: Nodo
     */
    public static void writeFile(String nomeFile, Nodo root) {
        try {
            OurXMLWriter writer = new OurXMLWriter(nomeFile);
            writer.writeStartDocument("utf-8", "1.0");
            writer.writeNode(writer, root);
            writer.writeEndDocument(); // scrittura della fine del documento
            writer.flush(); // svuota il buffer e procede alla scrittura
            writer.close(); // chiusura del documento e delle risorse impiegate
        } catch (Exception e) {
            System.out.println("Errore nella scrittura " + e.getMessage());
        }
    }

    /**
     * Classe che si occupa in particolare della scrittura degli attributi
     * @param writer: OurXMLWriter
     * @param root: Nodo
     */
    public static void writeAttributes(OurXMLWriter writer, Nodo root) {
        try {
            Map<String, String> attributi = root.getAttributi();
            ArrayList<String> keys = new ArrayList<>(attributi.keySet());
            //bisogna numerare gli attributi in modo tale che venga rispettato l'ordine scelto
            //successivamente il PRIMO numero viene eliminato e stampato correttamente
            //se piu' di 10 attributi: numerare da 01 a xx e fare il substring a partire dall'indice 2
            //FORSE IN UN FUTURO LO FAREMO IN AUTOMATICO MA NON OGGI <3
            for (int i = 0; i < keys.size(); i++) {
                writer.writeAttribute(keys.get(i).substring(1), attributi.get(keys.get(i)));
            }
        } catch (Exception e) {
            System.out.println("Errore nella scrittura " + e.getMessage());
        }
    }

    /**
     * Si occupa di scrivere i vari nodi dell'albero
     * @param writer: OurXMLWriter
     * @param root: Nodo
     */
    public static void writeNode(OurXMLWriter writer, Nodo root) {
        try {
            ArrayList<Nodo> nodi = root.getNodi();
            if (nodi.size() == 0) {
                writeEmptyNode(writer, root); //scrittura caso EmptyElements
            } else {
                writer.writeStartElement(root.getNome()); // scrittura del tag radice
                writeAttributes(writer, root);
                for (int i = 0; i < nodi.size(); i++) {
                    writeNode(writer, nodi.get(i));
                }
                writer.writeEndElement();
            }

            //per characters (testo) e comment (commenti), dipende dalla situazione
            //    writeCharacters(...) writeComment(...)

        } catch (Exception e) { // se c’è un errore viene eseguita questa parte
            System.out.println("Errore nella scrittura " + e.getMessage());
        }

    }

    /**
     * Scrittura nel caso si abbia un empty element
     * @param writer: OurXMLWriter
     * @param root: Nodo
     */
    public static void writeEmptyNode(OurXMLWriter writer, Nodo root) {
        try {
            writer.writeEmptyElement(root.getNome());
            writeAttributes(writer, root);
        } catch (Exception e) {
            System.out.println("Errore nella scrittura " + e.getMessage());
        }
    }

    /**
     * Metodo che gestisce l'indentazione dei vari elementi
     * @throws XMLStreamException
     */
    public void indenta() throws XMLStreamException {
        writeCharacters("\n");
        for (int i = 0; i < indentazione; i++) {
            writeCharacters("\t");
        }
    }

    @Override
    /**
     * Oltre a scrivere l'inizio di un elemento, gestisce anche l'indentazione
     */
    public void writeStartElement(String localName) throws XMLStreamException {
        indenta();
        xmlw.writeStartElement(localName);
        indentazione++;
    }

    @Override
    public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
        xmlw.writeStartElement(localName, namespaceURI);
    }

    @Override
    public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
        xmlw.writeStartElement(prefix, localName, namespaceURI);
    }

    @Override
    public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
        xmlw.writeEmptyElement(namespaceURI, localName);
    }

    @Override
    public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
        xmlw.writeEmptyElement(prefix, namespaceURI, localName);
    }

    @Override
    public void writeEmptyElement(String localName) throws XMLStreamException {
        indenta();
        xmlw.writeEmptyElement(localName);
    }

    @Override
    /**
     * Oltre a scrivere la fine di un elemento, gestisce il livello di intentazione.
     */
    public void writeEndElement() throws XMLStreamException {
        indentazione--;
        indenta();
        xmlw.writeEndElement();
    }

    @Override
    public void writeEndDocument() throws XMLStreamException {
        xmlw.writeEndDocument();
    }

    @Override
    public void close() throws XMLStreamException {
        xmlw.close();
    }

    @Override
    public void flush() throws XMLStreamException {
        xmlw.flush();
    }

    @Override
    public void writeAttribute(String localName, String value) throws XMLStreamException {
        xmlw.writeAttribute(localName, value);
    }

    @Override
    public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws
            XMLStreamException {
        xmlw.writeAttribute(prefix, namespaceURI, localName, value);
    }

    @Override
    public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
        xmlw.writeAttribute(namespaceURI, localName, value);
    }

    @Override
    public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
        xmlw.writeNamespace(prefix, namespaceURI);
    }

    @Override
    public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
        xmlw.writeDefaultNamespace(namespaceURI);
    }

    @Override
    public void writeComment(String data) throws XMLStreamException {
        xmlw.writeComment(data);
    }

    @Override
    public void writeProcessingInstruction(String target) throws XMLStreamException {
        xmlw.writeProcessingInstruction(target);
    }

    @Override
    public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
        xmlw.writeProcessingInstruction(target, data);
    }

    @Override
    public void writeCData(String data) throws XMLStreamException {
        xmlw.writeCData(data);
    }

    @Override
    public void writeDTD(String dtd) throws XMLStreamException {
        xmlw.writeDTD(dtd);
    }

    @Override
    public void writeEntityRef(String name) throws XMLStreamException {
        xmlw.writeEntityRef(name);
    }

    @Override
    public void writeStartDocument() throws XMLStreamException {
        xmlw.writeStartDocument();
    }

    @Override
    public void writeStartDocument(String version) throws XMLStreamException {
        xmlw.writeStartDocument(version);
    }

    @Override
    public void writeStartDocument(String encoding, String version) throws XMLStreamException {
        xmlw.writeStartDocument(encoding, version);
    }

    @Override
    public void writeCharacters(String text) throws XMLStreamException {
        xmlw.writeCharacters(text);
    }

    @Override
    public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
        xmlw.writeCharacters(text, start, len);
    }

    @Override
    public String getPrefix(String uri) throws XMLStreamException {
        return xmlw.getPrefix(uri);
    }

    @Override
    public void setPrefix(String prefix, String uri) throws XMLStreamException {
        xmlw.setPrefix(prefix, uri);
    }

    @Override
    public void setDefaultNamespace(String uri) throws XMLStreamException {
        xmlw.setDefaultNamespace(uri);
    }

    @Override
    public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
        xmlw.setNamespaceContext(context);
    }

    @Override
    public NamespaceContext getNamespaceContext() {
        return xmlw.getNamespaceContext();
    }

    @Override
    public Object getProperty(String name) throws IllegalArgumentException {
        return xmlw.getProperty(name);
    }
}
