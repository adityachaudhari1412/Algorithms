package numbercrunching;

public class NumberCrunching {

    public static void main(String[] args) {

        int jA = 111;
        int jB = 102;
        int jC = 999;

        if (jA > jB) {
            if (jA > jC) {
                System.out.println(jA);
            } else {
                System.out.println(jC);
            }
        } else {
            if (jB > jC) {
                System.out.println(jB);
            } else {
                System.out.println(jC);
            }
        }

        // ----
        int jMax = jA;
        if (jB > jMax) {
            jMax = jB;
        }
        if (jC > jMax) {
            jMax = jC;
        }
        System.out.println(jMax);

        // ---
        int[] arrNum = new int[6];

        arrNum[0] = 111;
        arrNum[1] = 11;
        arrNum[2] = 1011;
        arrNum[3] = 1181;
        arrNum[4] = 9111;
        arrNum[5] = 1511;
        jMax = arrNum[0];
        int jMin = arrNum[0];
        int jSum = arrNum[0];
        int j = 1;
        while (j < arrNum.length) {
            if (arrNum[j] > jMax) {
                jMax = arrNum[j];
            }
            if (arrNum[j] < jMin) {
                jMin = arrNum[j];
            }
            jSum += arrNum[j];
            j++;
        }
        System.out.println(jMax);
        System.out.println(jMin);
        System.out.println(jSum);
    }

}
