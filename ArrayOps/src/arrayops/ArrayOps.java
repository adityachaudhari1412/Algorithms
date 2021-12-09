/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arrayops;

import java.util.Random;

/**
 *
 * @author gadre
 */
public class ArrayOps {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Random rnd = new Random(123);
        int[] arrInt1 = new int[6];
        for (int j = 0; j < arrInt1.length; j++) {
            arrInt1[j] = rnd.nextInt(100);
        }
        doBubbleSort(arrInt1, true);
        int[] arrInt2 = new int[9];
        for (int j = 0; j < arrInt2.length; j++) {
            arrInt2[j] = rnd.nextInt(100);
        }
        doBubbleSort(arrInt2, true);
        int[] arrIntMerged = new int[arrInt1.length + arrInt2.length];
        mergeSortedArrays(arrInt1, arrInt2, arrIntMerged);

    }

    private static void mergeSortedArrays(int[] arrInt1, int[] arrInt2, int[] arrIntMerged) {
        int jM = 0;
        int j1 = 0;
        int j2 = 0;
        while (jM < arrIntMerged.length) {
            int jVal;
            if (arrInt1[j1] < arrInt2[j2]) {
                jVal = arrInt1[j1];
                j1++;

            } else {
                jVal = arrInt2[j2];

                j2++;
            }
            arrIntMerged[jM] = jVal;
            jM++;
        }
        System.out.println();
    }

    public static void doBubbleSort(int[] arrInt, boolean fAscending) {

        long jSwapCount = 0;
        boolean fSwapped = true;
        int jPass = 0;
        int jMultiplier = fAscending ? 1 : -1;

        while (fSwapped) {
            fSwapped = false;
            for (int j = 0; j < arrInt.length - 1 - jPass; j++) {
                if (jMultiplier * arrInt[j] > jMultiplier * arrInt[j + 1]) {
                    int jTemp;
                    jTemp = arrInt[j];
                    arrInt[j] = arrInt[j + 1];
                    arrInt[j + 1] = jTemp;
                    fSwapped = true;
                    jSwapCount++;
                }
            }
            jPass++;
        }
        System.out.println("Swap Count = " + jSwapCount);
    }

    private static void reverseArray(int[] arrInt) {
        int j;
        for (j = 0; j < arrInt.length / 2; j++) {
            int jTemp;
            jTemp = arrInt[j];
            arrInt[j] = arrInt[arrInt.length - 1 - j];
            arrInt[arrInt.length - 1 - j] = jTemp;

        }

    }

}
