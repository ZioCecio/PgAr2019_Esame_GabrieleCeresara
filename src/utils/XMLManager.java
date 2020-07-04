package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import game.Card;
import game.CardType;
import game.Color;
import game.Deck;

public class XMLManager {
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

                    String colorName = xmlr.getAttributeValue(1).toUpperCase();
                    Color color = null;

                    if (!colorName.equals("NESSUNO")) {
                        color = Color.valueOf(colorName);
                    }

                    int numOfCards = Integer.parseInt(xmlr.getAttributeValue(2));

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

        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }

        return new Deck(cards);
    }

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
}