package com.sap.cic.code.challenge;

import java.util.*;

public class Test {

    public static void main(String[] args) throws GameException {
        Game game = Game.create();
        List<Card> cards = Arrays.asList(
                new Card(Card.Kind.TEN, Card.Suit.CLUBS),
                new Card(Card.Kind.FOUR, Card.Suit.CLUBS),
                new Card(Card.Kind.SEVEN, Card.Suit.DIAMONDS),
                new Card(Card.Kind.KING, Card.Suit.CLUBS),
                new Card(Card.Kind.EIGHT, Card.Suit.CLUBS),
                new Card(Card.Kind.ACE, Card.Suit.HEARTS),
                new Card(Card.Kind.EIGHT, Card.Suit.CLUBS)
        );
        game.add(cards);
        Hand hand = game.showHand();

        System.out.println(hand);

    }
}
