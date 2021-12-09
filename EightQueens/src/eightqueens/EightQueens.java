package eightqueens;

import java.util.Arrays;
import java.util.Random;

public class EightQueens {

    private class XY {

        public int _jX;
        public int _jY;

        public String toString() {
            return "" + _jX + " , " + _jY;
        }
    }
    private static final char __chEmpty = '-';
    private static final char __chPawnW = 'P';
    private static final char __chBishopW = 'B';
    private static final char __chKnightW = 'n';
    private static final char __chRookW = 'R';
    private static final char __chQueenW = 'Q';
    private static final char __chKingW = 'K';
    private static final char __chPawnB = 'p';
    private static final char __chBishopB = 'b';
    private static final char __chKnightB = 'n';
    private static final char __chRookB = 'r';
    private static final char __chQueenB = 'q';
    private static final char __chKingB = 'k';

    public static void main(String[] args) {
        char[] achBoard = new char[64];
        Arrays.fill(achBoard, __chEmpty);
        XY[] arrXY = new XY[8];
        placeEightQueens(achBoard, arrXY);

    }

    private static char getCharAt(char[] achBoard, int jX, int jY) {
        char chRet = achBoard[jY * 8 + jX];
        return chRet;
    }

    private static void setCharAt(char[] achBoard, int jX, int jY, char ch) {
        achBoard[jY * 8 + jX] = ch;

    }

    private static void printBoard(char[] achBoard) {
        System.out.println("***Board***");
        System.out.println();
        for (int jY = 0; jY < 8; jY++) {
            for (int jX = 0; jX < 8; jX++) {
                char ch = getCharAt(achBoard, jX, jY);
                System.out.print(" " + ch + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void placeEightQueens(char[] achBoard, XY[] arrXY) {
        fPlaceEightQueensRecursive(achBoard, arrXY, 0);
        printBoard(achBoard);
    }
    private static final Random _random = new Random();
    public static int __jFail = 0;

    private static boolean fPlaceEightQueensRecursive(char[] achBoard, XY[] arrXY, int jCurY) {
        boolean fRet = false;

        int jStart = 0;//_random.nextInt(8);

        for (int j = 0; j < 8; j++) {
            int jX = (j + jStart) % 8;
            char ch = getCharAt(achBoard, jX, jCurY);
            if (ch == __chEmpty) {
                if (fBoxIsSafe(arrXY, jX, jCurY)) {
                    setCharAt(achBoard, jX, jCurY, __chQueenB);
                    XY xy = new EightQueens().new XY();
                    xy._jX = jX;
                    xy._jY = jCurY;
                    arrXY[jCurY] = xy;
                    if (jCurY < arrXY.length - 1) {
                        fRet = fPlaceEightQueensRecursive(achBoard, arrXY, jCurY + 1);
                        if (fRet) {
                            break;
                        } else {
                            setCharAt(achBoard, jX, jCurY, __chEmpty);
                            arrXY[jCurY] = null;
                            __jFail++;
                        }
                    } else {
                        fRet = true;

                    }
                }
            }
        }
        return fRet;
    }

    private static boolean fBoxIsSafe(XY[] arrXY, int jX, int jY) {
        boolean fRet = true;
        int j;
        for (j = 0; j < arrXY.length; j++) {
            XY xy = arrXY[j];
            if (xy == null) {
                break;
            }
            if (jX == xy._jX) {
                fRet = false;
                break;
            }
            if (jY == xy._jY) {
                fRet = false;
                break;
            }
            if (Math.abs(jX - xy._jX) == Math.abs(jY - xy._jY)) {
                fRet = false;
                break;
            }
        }

        return fRet;

    }
}
