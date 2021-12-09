package biorhythm;

import java.util.Date;

public class BioRhythmCalculator {

    public static final long __lTicksIn1Day = 24 * 60 * 60 * 1000;
    public static final int __jCycleP = 23;
    public static final int __jCycleE = 28;
    public static final int __jCycleI = 33;
    private Date _dtBirth;

    public BioRhythmCalculator(Date dt) {
        _dtBirth = dt;
    }

    /**
     *
     * @param dtTarget The date for computation
     * @return
     */
    public double calcP(Date dtTarget) {
        double rReturn = 999;
        long nDays = getDateDifference(dtTarget);

        if (nDays > 0) {
            rReturn = Math.sin(2 * Math.PI * nDays / __jCycleP);
        }
        return rReturn;
    }

    public double calcE(Date dtTarget) {
        double rReturn = 999;
        long nDays = getDateDifference(dtTarget);

        if (nDays > 0) {
            rReturn = Math.sin(2 * Math.PI * nDays / __jCycleE);
        }
        return rReturn;
    }

    public double calcI(Date dtTarget) {
        double rReturn = 999;
        long nDays = getDateDifference(dtTarget);

        if (nDays > 0) {
            rReturn = Math.sin(2 * Math.PI * nDays / __jCycleI);
        }
        return rReturn;

    }

    private long getDateDifference(Date dtTarget) {
        long jTicksTarget = dtTarget.getTime();
        long jDiff = jTicksTarget - _dtBirth.getTime();
        long nDays = jDiff / __lTicksIn1Day;
        return nDays;
    }
}
