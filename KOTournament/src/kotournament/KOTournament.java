/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kotournament;

/**
 *
 * @author gadre
 */
public class KOTournament {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        KOTournament kot = new KOTournament(11);
        WPW[] awpw = kot.getSchedule();
    }

    private int _nTeams;
    private WPW[] _awpw;
    private final static int __nTeamsMaxAllowed = 1000000;

    private KOTournament() {
        throw new RuntimeException("Don't do this");
    }

    public KOTournament(int nTeams) {
        if (nTeams > __nTeamsMaxAllowed) {
            throw new RuntimeException("Don't do this");
        }
        _nTeams = nTeams;
        int nTotalGames = _nTeams - 1;
        _awpw = new WPW[nTotalGames];
    }

    public WPW[] getSchedule() {

        int jCountForR2 = 1;
        int nT = _nTeams;
        while (nT / 2 > 0) {
            jCountForR2 *= 2;
            nT /= 2;
        }
        int jCountForR1 = _awpw.length - jCountForR2 + 1;
        int jCurGame;
        int jPlStart = jCountForR2 - jCountForR1;
        for (jCurGame = 0; jCurGame < jCountForR1; jCurGame++) {
            WPW wpw = new WPW();
            wpw._plA = jPlStart + __nTeamsMaxAllowed * 10;
            jPlStart++;
            wpw._plB = jPlStart + __nTeamsMaxAllowed * 10;
            jPlStart++;
            _awpw[jCurGame] = wpw;

        }
        int j;
        jPlStart = 0;
        for (j = 0; j < jCountForR1; j++) {
            WPW wpw = new WPW();
            wpw._plA = jPlStart + __nTeamsMaxAllowed * 10;
            jPlStart++;
            wpw._plB = j + __nTeamsMaxAllowed * 20;

            _awpw[jCurGame] = wpw;
            jCurGame++;
        }
        for (; j < jCountForR2 / 2; j++) {

            WPW wpw = new WPW();
            wpw._plA = jPlStart + __nTeamsMaxAllowed * 10;
            jPlStart++;
            wpw._plB = jPlStart + __nTeamsMaxAllowed * 10;
            jPlStart++;
            _awpw[jCurGame] = wpw;
            jCurGame++;
        }

        return _awpw;
    }

    public class WPW {

        public int _plA;
        public int _plB;

    }

}
