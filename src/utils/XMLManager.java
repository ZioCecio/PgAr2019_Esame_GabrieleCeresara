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
    private static ArrayList<PlayerModel> playerModels;

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

    /**
     * Scrive le statistiche di un match
     * 
     * @param match
     */
    public static void writeStats(Match match) {
        XMLOutputFactory xmlof = null;
        XMLStreamWriter xmlw = null;

        File file = new File("./stats");

        try {
            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(
                    new FileOutputStream(String.format("%s/match%d.xml", file.getName(), file.listFiles().length + 1)),
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

    /**
     * Mostra le statistiche di una partita specificata come parametro
     * 
     * @param filePath
     */
    public static void showStats(String filePath) {
        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;

        try {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(filePath, new FileInputStream(filePath));

            while (xmlr.hasNext()) {
                if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT
                        && xmlr.getLocalName().equals("classifica_partita")) {

                    int numOfPlayers = Integer.parseInt(xmlr.getAttributeValue(1));
                    String date = xmlr.getAttributeValue(0);

                    System.out.println(
                            String.format("Partita disputata il giorno %s da %d giocatori: ", date, numOfPlayers));

                    for (int i = 0; i < numOfPlayers; i++) {
                        xmlr.nextTag();

                        String name = xmlr.getAttributeValue(1);
                        int cards = Integer.parseInt(xmlr.getAttributeValue(2));

                        System.out.println(String.format("%d) %s con %d carte", i + 1, name, cards));

                        xmlr.nextTag();
                    }
                }

                xmlr.next();
            }

            xmlr.close();

        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

    /**
     * Scrive le informazioni riguardati ogni partita di ogni giocatore, aggiornando
     * o aggiungendo eventuali dati
     * 
     * @param match
     */
    public static void writeRanking(Match match) {
        readRankings();

        Collections.sort(match.getPlayers());

        for (Player player : match.getPlayers()) {
            PlayerModel pastData = playerModels.stream().filter(p -> p.getName().equals(player.getName())).findAny()
                    .orElse(null);

            if (pastData == null) {
                playerModels
                        .add(new PlayerModel(player.getName(), player.getCurrentDeck().remainingCards() == 0 ? 1 : 0,
                                player.getCurrentDeck().remainingCards(), 1));
            } else {
                pastData.increment(player.getCurrentDeck().remainingCards() == 0 ? 1 : 0,
                        player.getCurrentDeck().remainingCards());
            }
        }

        XMLOutputFactory xmlof = null;
        XMLStreamWriter xmlw = null;

        try {
            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(new FileOutputStream("./stats/playerStats.xml"), "utf-8");

            xmlw.writeStartDocument("utf-8", "1.0");
            xmlw.writeStartElement("giocatori");
            xmlw.writeAttribute("numero", String.format("%d", playerModels.size()));

            for (PlayerModel pm : playerModels) {
                xmlw.writeStartElement("giocatore");

                xmlw.writeAttribute("nome", pm.getName());
                xmlw.writeAttribute("numero_vittorie", String.format("%d", pm.getNumberOfVictory()));
                xmlw.writeAttribute("carte_totali", String.format("%d", pm.getTotalCards()));
                xmlw.writeAttribute("partite_totali", String.format("%d", pm.getTotalMatches()));

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

    /**
     * Legge dal file tutte le statistiche di tutti i giocatori
     */
    private static void readRankings() {
        playerModels = new ArrayList<PlayerModel>();

        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;

        xmlif = XMLInputFactory.newInstance();

        try {
            xmlr = xmlif.createXMLStreamReader("./stats/playerStats.xml",
                    new FileInputStream("./stats/playerStats.xml"));

            while (xmlr.hasNext()) {
                if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT
                        && xmlr.getLocalName().equals("giocatori")) {
                    int numOfPlayers = Integer.parseInt(xmlr.getAttributeValue(0));

                    for (int i = 0; i < numOfPlayers; i++) {
                        xmlr.nextTag();

                        String name = xmlr.getAttributeValue(0);
                        int numberOfVictory = Integer.parseInt(xmlr.getAttributeValue(1));
                        int totalCards = Integer.parseInt(xmlr.getAttributeValue(2));
                        int totalMatches = Integer.parseInt(xmlr.getAttributeValue(3));

                        playerModels.add(new PlayerModel(name, numberOfVictory, totalCards, totalMatches));

                        xmlr.nextTag();
                    }
                }

                xmlr.next();
            }

            xmlr.close();

        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ritorna le statistiche lette dal file
     * 
     * @return
     */
    public static ArrayList<PlayerModel> getRanking() {
        if (playerModels == null) {
            readRankings();
        }

        return playerModels;
    }
}