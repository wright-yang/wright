package com.sap.cic.code.challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    private List<Card> cardList;

    public static Game create() {
        return new Game();
    }

    private Game() {
        this.cardList = new ArrayList<>();
    }

    public void add(List<Card> cards) {
        cardList.addAll(cards);
    }

    //EDIT ME PLEASE!
    public Hand showHand() throws GameException {

        Hand max = getMax(cardList);

        return max;
    }

    public static Hand getMax(List<Card> cards){

        List<Card> cardList;
        List <Long> allValue = new ArrayList<>();
        List <Integer> allLevel = new ArrayList<>();

        for (int a = 0; a < 3; a++){
            for (int b = a + 1; b < 4; b++) {
                for(int c = b + 1; c < 5; c++){
                    for(int d = c + 1; d < 6; d++){
                        for (int e = d + 1; e < 7; e++) {

                            cardList = new ArrayList<>();

                            cardList.add(cards.get(a));
                            cardList.add(cards.get(b));
                            cardList.add(cards.get(c));
                            cardList.add(cards.get(d));
                            cardList.add(cards.get(e));

                            int level = DeZhouPokerType(cardList);
                            allLevel.add(level);
                            System.out.println("level=" + level);

                            long value = getvalue(level, cardList);
                            allValue.add(value);
                            System.out.println("value=" + value);

                        }
                    }
                }
            }
        }
        Collections.sort(allValue);
        long maxValue = allValue.get(allValue.size()-1);
        System.out.println("max value=" + maxValue);

        Collections.sort(allLevel);
        int maxLevel = allLevel.get(allLevel.size()-1);
        System.out.println("max level=" + maxLevel);

        if (maxLevel==13){

            return Hand.ROYAL_FLUSH;

        }else if (maxLevel==12){

            return Hand.STRAIGHT_FLUSH;


        }else if (maxLevel==11){

            return Hand.FOUR_OF_A_KIND;

        }else if (maxLevel==10){

            return Hand.FULL_HOUSE;


        }else if (maxLevel==9){

            return Hand.FLUSH;

        }else if (maxLevel==8){
            return Hand.STRAIGHT;


        }else if (maxLevel==7){
            return Hand.THREE_OF_A_KIND;


        }else if (maxLevel==6){
            return Hand.TWO_PAIRS;


        }else if (maxLevel==5){

            return Hand.PAIR;

        }else if (maxLevel==4){

            return Hand.HIGHCARD;

        }

        return Hand.UNKNOWN;


    }


    /**
     * 是否是同花
     * @param cardList
     * @return
     */
    public static int flushLevel(List<Card> cardList){

        int max =0;
        for (int i=0;i<cardList.size();i++){
            String color =cardList.get(i).suit.value;
            int count =1;
            for (int j =i+1;j<cardList.size();j++){

                if (cardList.get(j).suit.value.equals(color))
                    count++;
            }
            if (max<count)
                max =count;
        }
        if (max>=5)
            return 9;
        return 0;

    }


    /**
     * 判断是否是顺子
     * @param cardList
     * @return
     */
    public static int straightLevel(List<Card> cardList){

        Collections.sort(cardList);

        for(int i = 0; i < 4; i++) {
            if (cardList.get(i).kind.rank + 1 != cardList.get(i+1).kind.rank)
                return 0;

        }
        return 8;

    }

    /**
     *
     * 判断是否是同花顺
     * @param cardList
     * @return
     */
    public static int straightFlushLevel(List<Card> cardList){
        int straight = straightLevel(cardList);
        if (straight==0)
            return 0;
        int hand = flushLevel(cardList);
        if (hand!=0){
            Collections.sort(cardList);
            if (cardList.get(4).kind.rank==13)
                return 13;
            else
                return 12;

        }
        return 0;
    }


    public static int DeZhouPokerType(List<Card> cardlist) {
        int count = 0;
        for (int i = 0; i<cardlist.size(); i++) {
            for (int j = i + 1; j < cardlist.size(); j++) {
                if (cardlist.get(i).kind.rank == cardlist.get(j).kind.rank) {
                    count ++;
                }
            }
        }

        int straightFlush = straightFlushLevel(cardlist);
        if (straightFlush!=0) { //皇家同花顺、同花顺
            return straightFlush;
        }
        if (count == 6) {// 四条
            return 11;
        }
        if (count == 4) { //葫芦
            return 10;
        }
        int flush = flushLevel(cardlist);
        if (flush==9){ //同花
            return 9;
        }
        int straight =straightLevel(cardlist);
        if (straight==8){ //顺子
            return 8;
        }
        if (count == 3) { //三条
            return  7;
        }
        if (count == 2) { //两对
            return  6;
        }
        if (count == 1) {//一对
            return 5;
        }
        if (count==0){ //高牌
            return 4;
        }
        return 3;
    }


    public static long getvalue (int level, List<Card> cardlist) {
        long lon = 1000000000l * 100;
        long pkvalue = 0l;
        for(int i = 0; i < cardlist.size(); i++) {
            int interval = 1;
            for (int j = i; j < cardlist.size()-1; j++) {
                interval *= 100;
            }
            pkvalue  += cardlist.get(i).kind.rank * interval;

        }
        pkvalue += level * lon;

        return pkvalue;

    }
}
