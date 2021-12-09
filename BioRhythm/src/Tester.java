
import biorhythm.BioRhythmCalculator;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gadre
 */
public class Tester {

    public static void main(String[] args) {
        Date dt = new Date(90, 7, 29);
        BioRhythmCalculator brc = new BioRhythmCalculator(dt);
        Date dtNow = new Date(121, 6, 23);
        double qP = brc.calcP(dtNow);
        double qE = brc.calcE(dtNow);
        double qI = brc.calcI(dtNow);
        System.out.println(qP);
        System.out.println(qE);
        System.out.println(qI);
    }
}
