
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gadre
 */
public class DynamicArrayTest {

    static Random __rand = new Random();
    static SimpleDateFormat __sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static void main(String[] args) {
        printTimestamp("ArrayList Start");
        ArrayList<String> alstr = new ArrayList();
        for (int j = 0; j < 1024 * 1024 * 16; j++) {
            alstr.add("" + (char) ((' ' + __rand.nextInt(96))));
        }
        printTimestamp("ArrayList End");
        System.out.println(alstr.size());;
        printTimestamp("DynamicArray Start");
        DynamicArray<String> dyna = new DynamicArray<>();
        for (int j = 0; j < 1024 * 1024 * 16; j++) {
            dyna.append("" + (char) ((' ' + __rand.nextInt(96))));
        }
        printTimestamp("DynamicArray End");
        System.out.println(dyna.getCount());;
//
//        for (int j = 0; j < 499; j++) {
//            String sVal = dyna.getAt(j);
//            System.out.println(sVal);
//        }

    }

    private static void printTimestamp(String sPrefix) {
        Date dtNow = new Date();
        String sDtStamp = __sdf.format(dtNow);
        System.out.println(sPrefix + " : " + sDtStamp);
    }
}
