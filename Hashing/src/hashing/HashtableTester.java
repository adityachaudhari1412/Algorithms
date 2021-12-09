/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashing;

import java.util.Random;

/**
 *
 * @author gadre
 */
public class HashtableTester {

    /**
     * @param args the command line arguments
     */
    static Random _random = new Random(12);

    public static void main(String[] args) {
        HashTable ht = new HashTable();
        for (int j = 0; j < 1500; j++) {
            String sKey = getRandomASCIIString(6, 15);
            String sValue = getRandomASCIIString(12, 90);
            ht.put(sKey, sValue);
        }

        _random = new Random(12);

        for (int j = 0; j < 1500; j++) {
            String sKey = getRandomASCIIString(6, 15);
            String sValue = getRandomASCIIString(12, 90);
            String sValueActual = ht.get(sKey);
            if (sValueActual.compareTo(sValue) != 0) {
                System.out.println("Some Error");
            }
        }

    }

    private static String getRandomASCIIString(int nLenMin, int nLenMax) {
        int jLen = _random.nextInt(nLenMax - nLenMin) + nLenMin;
        char[] ach = new char[jLen];
        for (int j = 0; j < ach.length; j++) {
            ach[j] = (char) (' ' + _random.nextInt('~' - ' '));
        }
        String sRet = new String(ach);
        return sRet;
    }

}
