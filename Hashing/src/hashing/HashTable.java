/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gadre
 */
package hashing;

public class HashTable {

    public class HashRecord {

        public String _sKey;
        public String _sValue;
        public HashRecord _hrNext;
        public int _nCountNext;
    }
    private int _jCollisionCount = 0;
    private int _jBucketArrayDim1 = 101;
    private int _jCount = 0;

    private HashRecord[] _ahr = new HashRecord[_jBucketArrayDim1 * _jBucketArrayDim1];

    private int getHashCode(String sKey, int jBucketMultiplierForEven) {
        int jCode = 0;
        int jMax = sKey.length();
        int jMultiForEven = jBucketMultiplierForEven;
        for (int j = 0; j < jMax; j++) {
            int jCur = (int) sKey.charAt(j);
            if (j % 2 == 0) {
                jCur = jCur * jMultiForEven;
            }
            jCode += jCur;
        }
        jCode = jCode < 0 ? -jCode : jCode;
        jCode %= _jBucketArrayDim1;

        return jCode;
    }

    public HashTable() {

    }

    public boolean put(String sKey, String sValue) {
        boolean fRet = false;
        int jHashcode1 = getHashCode(sKey, 1);
        int jHashcode2 = getHashCode(sKey, -1);
        int jBucket = jHashcode1 * _jBucketArrayDim1 + jHashcode2;
        if (_ahr[jBucket] == null) {
            HashRecord hrec = new HashRecord();
            hrec._sKey = sKey;
            hrec._sValue = sValue;
            hrec._hrNext = null;
            hrec._nCountNext = 0;
            _ahr[jBucket] = hrec;
        } else {
            _jCollisionCount++;
            HashRecord hrCur = _ahr[jBucket];
            do {
                if (sKey.compareTo(hrCur._sKey) == 0) {
                    hrCur._sValue = sValue;
                    fRet = true;
                    break;
                }
                hrCur = hrCur._hrNext;

            } while (hrCur != null);
            if (!fRet) {
                HashRecord hrecExisting = _ahr[jBucket];
                HashRecord hrNew = new HashRecord();
                hrNew._sKey = sKey;
                hrNew._sValue = sValue;
                hrNew._hrNext = hrecExisting;
                hrNew._nCountNext = hrecExisting._nCountNext + 1;
                _ahr[jBucket] = hrNew;
            }
        }
        return fRet;
    }

    public String get(String sKey) {
        String sValue = null;
        int jHashcode1 = getHashCode(sKey, 1);
        int jHashcode2 = getHashCode(sKey, -1);
        int jBucket = jHashcode1 * _jBucketArrayDim1 + jHashcode2;
        if (_ahr[jBucket] != null) {
            HashRecord hrCur = _ahr[jBucket];
            do {
                if (sKey.compareTo(hrCur._sKey) == 0) {
                    sValue = hrCur._sValue;
                    break;
                }
                hrCur = hrCur._hrNext;
            } while (hrCur != null);
        }
        return sValue;
    }

    public int getSize() {
        return _jCount;
    }
}
