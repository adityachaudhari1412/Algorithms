/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardshuffles;

import java.util.Random;

/**
 *
 * @author gadre
 */
public class ArrayShuffler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int nItems = 52;
        ArrayShuffler arrshuffler = new ArrayShuffler();
        int[] ajDeck = arrshuffler.getRandomizedArray(nItems);
        for (int j = 0; j < ajDeck.length; j++) {
            printItem(ajDeck[j]);
        }

    }
    private Random _rnd = new Random();

    public int[] getRandomizedArray(int nItems) {
        int[] arrIntRet = null;
        if (nItems > 0) {
            arrIntRet = new int[nItems];
            randomizeArray(arrIntRet);
        }
        return arrIntRet;
    }

    public void randomizeArray(int[] arrIntRet) {
        int j;
        for (j = 0; j < arrIntRet.length; j++) {
            arrIntRet[j] = j;
        }
        for (j = 0; j < arrIntRet.length; j++) {
            int jCardSwap = _rnd.nextInt(arrIntRet.length);
            int jTemp;
            jTemp = arrIntRet[j];
            arrIntRet[j] = arrIntRet[jCardSwap];
            arrIntRet[jCardSwap] = jTemp;
        }
    }
    private static char[] __achSuit = {
        'S', 'H', 'D', 'C'
    };
    private static char[] __achVal = {
        '2',
        '3',
        '4',
        '5',
        '6',
        '7',
        '8',
        '9',
        'T',
        'J',
        'Q',
        'K',
        'A'
    };

    public static void printItem(int jCard) {
        int jSuit = jCard / 13;
        int jVal = jCard % 13;

        System.out.println(__achSuit[jSuit] + "-" + __achVal[jVal]);
    }

}
