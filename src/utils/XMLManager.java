package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import game.Card;
import game.CardType;
import game.Color;
import game.Deck;
import game.Match;
import game.Player;

public class XMLManager {
    /**
     * Legge e crea un nuovo mazzo di carte a partire dal file XML specificato come
     * parametro
     * 
     * @param filePath
     * @return
     */
    public static Deck readDeck(String filePath) {
        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;

        xmlif = XMLInputFactory.newInstance();

        ArrayList<Card> cards = new ArrayList<Card>();

        try {
            xmlr = xmlif.createXMLStreamReader(filePath, new FileInputStream(filePath));

            while (xmlr.hasNext()) {
                if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT
                        && xmlr.getLocalName().equals("sottomazzo")) {

                    /**
                     * Si guarda il colore del sottomazzo per fare in modo di specificarlo nella
                     * creazione di tutte le carte
                     */
                    String colorName = xmlr.getAttributeValue(1).toUpperCase();
                    Color color = null;

                    if (!colorName.equals("NESSUNO")) {
                        color = Color.valueOf(colorName);
                    }

                    int numOfCards = Integer.parseInt(xmlr.getAttributeValue(2));

                    // Per ogni sottomazzo si leggono tutte le sue carte
                    for (int i = 0; i < numOfCards; i++) {
                        xmlr.nextTag();
                        String value = xmlr.getAttributeValue(2);
                        CardType cardType = getCardType(value);

                        int numValue = cardType == CardType.NUMERO ? Integer.parseInt(value) : -1;

                        cards.add(new Card(numValue, color, cardType));
                        xmlr.nextTag();
                    }
                }

                xmlr.next();
            }

            xmlr.close();

        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }

        return new Deck(cards);
    }

    /**
     * Ritorna il tipo di carta specificato dal nome (vengono "tradotti" i nomi
     * scritti nell'XML nei nomi usati nel programma)
     * 
     * @param name
     * @return il tipo corrispondente
     */
    private static CardType getCardType(String name) {
        if (name.matches("-?\\d+(\\.\\d+)?")) {
            return CardType.NUMERO;
        }

        switch (name) {
            case "PescaDue":
                return CardType.PESCA_DUE;

            case "Stop":
                return CardType.STOP;

            case "CambioGiro":
                return CardType.CAMBIO_GIRO;

            case "PescaQuattro":
                return CardType.PESCA_QUATTRO;

            case "CambiaColore":
                return CardType.CAMBIO_COLORE;

            case "CambioCarte":
                return CardType.CAMBIA_CARTE;
        }

        return null;
    }

    public static void writeStats(Match match) {
        XMLOutputFactory xmlof = null;
        XMLStreamWriter xmlw = null;

        File file = new File("./stats");

        try {
            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(
                    new FileOutputStream(String.format("%s/match%d.xml", file.getName(), file.listFiles().length)),
                    "utf-8");

            ArrayList<Player> players = match.getPlayers();
            Collections.sort(players);

            xmlw.writeStartDocument("utf-8", "1.0");
            xmlw.writeStartElement("classifica_partita");

            xmlw.writeAttribute("data", match.getDateOfMatch().toString());

            xmlw.writeAttribute("numero_giocatori", String.format("%d", players.size()));

            for (Player player : players) {
                xmlw.writeStartElement("giocatore");

                xmlw.writeAttribute("posizione", String.format("%d", players.indexOf(player) + 1));
                xmlw.writeAttribute("nome", player.getName());
                xmlw.writeAttribute("carte_rimaste", String.format("%d", player.getCurrentDeck().remainingCards()));

                xmlw.writeEndElement();
            }

            xmlw.writeEndElement();
            xmlw.writeEndDocument();

            xmlw.flush();
            xmlw.close();

        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public static void writeRanking() {

    }
}