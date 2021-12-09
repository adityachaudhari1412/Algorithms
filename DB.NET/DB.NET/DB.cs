using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace DB.NET
{
    public enum EFieldType
    {
        Integer,
        Decimal,
        Text,
        Boolean,
        Datetime,
        Guid,
        Unknown,
    }

    public class DB
    {
        private static String __sTblExt = ".TSV";
        private static int __jCountHeaderLines = 2;
        private List<DBTable> _lstDbTbl = new List<DBTable>();

        private String _sDBName;

        private String _sRootDir;

        private DB(String sRootDir, String sDBName)
        {
            _sRootDir = sRootDir;
            _sDBName = sDBName;
        }

        public static DB CreateDB(String sRootDir, String sDBName)
        {
            if (!sRootDir.EndsWith("\\"))
            {
                sRootDir += '\\';
            }
            Directory.CreateDirectory(sRootDir + sDBName);
            DB dbRet = new DB(sRootDir, sDBName);
            return dbRet;
        }

        public void AddRow(String sNameTbl, IEnumerable<Object> lstData)
        {
            DBTable dbtbl = GetTableByName(sNameTbl);
            AddRow(dbtbl, lstData);
        }

        private DBTable GetTableByName(string sNameTbl)
        {
            DBTable dbtbl = null;
            int j;
            for (j = 0; j < _lstDbTbl.Count; j++)
            {
                if (String.Compare(_lstDbTbl[j]._sName, sNameTbl, true) == 0)
                {
                    dbtbl = _lstDbTbl[j];
                    break;
                }
            }

            return dbtbl;
        }

        public void AddRow(DBTable dbtbl, IEnumerable<Object> lstData)
        {
            String sFileTbl = _sRootDir + _sDBName + '\\' + dbtbl._sName + __sTblExt;
            String[] arrStr = new String[dbtbl._lstField.Count];
            int j;
            for (j = 0; j < dbtbl._lstField.Count; j++)
            {
                arrStr[j] = lstData.ElementAt(j).ToString();
            }

            String sOut = Util.Concat(arrStr);
            String[] arrsOut = new string[] { sOut };
            File.AppendAllLines(sFileTbl, arrsOut);
            dbtbl._lstlstData.Add(lstData);

        }

        public void AddTable(DBTable dbtbl)
        {
            int j;
            for (j = 0; j < _lstDbTbl.Count; j++)
            {
                if (String.Compare(_lstDbTbl[j]._sName, dbtbl._sName, true) == 0)
                {
                    break;
                }
            }
            if (j < _lstDbTbl.Count)
            {
                throw new Exception("Table already Exists");
            }

            String sFileTbl = _sRootDir + _sDBName + '\\' + dbtbl._sName + __sTblExt;
            if (File.Exists(sFileTbl))
            {
                throw new Exception("TBL File already Exists");
            }
            List<String> lstStr;

            String[] arrStrHeader = new String[2];
            List<String> lstStrFN = new List<String>();
            List<String> lstStrFT = new List<String>();
            for (j = 0; j < dbtbl._lstField.Count; j++)
            {
                lstStrFN.Add(dbtbl._lstField[j]._sName);
                lstStrFT.Add(dbtbl._lstField[j]._eft.ToString());
            }
            arrStrHeader[0] = Util.Concat(lstStrFN);
            arrStrHeader[1] = Util.Concat(lstStrFT);
            File.WriteAllLines(sFileTbl, arrStrHeader);
            _lstDbTbl.Add(dbtbl);
            dbtbl._lstlstData = new List<IEnumerable<Object>>();
            dbtbl._lstaIndex = new List<int[]>();
        }

        public void AddIndex(string sNameTbl, int jPosField)
        {
            DBTable dbtbl = GetTableByName(sNameTbl);
            DBField dbField = dbtbl._lstField[jPosField];
            int jSizeIndex = dbtbl._lstlstData.Count();
            int[] arrIndex = new int[jSizeIndex];
            for (int j = 0; j < arrIndex.Length; j++)
            {
                arrIndex[j] = j;
            }
            ComparerTableByPosField ctbps = new ComparerTableByPosField();
            ctbps._dbTable = dbtbl;
            ctbps._jPosField = jPosField;
            Array.Sort(arrIndex, ctbps);
            dbtbl._lstaIndex.Add(arrIndex);
        }

        public class ComparerTableByPosField : IComparer<int>
        {
            public DBTable _dbTable;
            public int _jPosField;

            public int Compare(int j1, int j2)
            {
                Object obj1 = _dbTable._lstlstData.ElementAt(j1).ElementAt(_jPosField);
                Object obj2 = _dbTable._lstlstData.ElementAt(j2).ElementAt(_jPosField);
                int jRet = obj1.ToString().CompareTo(obj2.ToString());
                return jRet;
            }
        }
        public static DB OpenDB(String sRootDir, String sDBName)
        {
            DB dbRet = null;
            if (!sRootDir.EndsWith("\\"))
            {
                sRootDir += '\\';
            }
            String sFQNDir = sRootDir + sDBName;
            if (Directory.Exists(sFQNDir))
            {
                dbRet = new DB(sRootDir, sDBName);
            }

            IEnumerable<String> lstFileNames = Directory.EnumerateFiles(sFQNDir, "*" + __sTblExt, SearchOption.TopDirectoryOnly);
            int jCount = lstFileNames.Count();
            for (int jFile = 0; jFile < jCount; jFile++)
            {
                String sFQNFile = lstFileNames.ElementAt(jFile);
                String sFileName = sFQNFile.Substring(sFQNDir.Length + 1);
                String sTblName = sFileName.Substring(0, sFileName.Length - __sTblExt.Length);
                String[] arrStr = File.ReadAllLines(sFQNFile);
                String[] arrStrFN = Util.GetFieldsPersistedData(arrStr[__jCountHeaderLines - 2]);
                String[] arrStrFT = Util.GetFieldsPersistedData(arrStr[__jCountHeaderLines - 1]);

                DBTable dbTbl = new DBTable();
                dbTbl._sName = sTblName;
                dbRet._lstDbTbl.Add(dbTbl);
                List<DBField> lstdbf = new List<DBField>();
                for (int jField = 0; jField < arrStrFN.Length; jField++)
                {
                    lstdbf.Add(new DBField()
                    {
                        _fAllowNull = false,
                        _fIsIndexed = true,
                        _sName = arrStrFN[jField],
                        _eft = Util.GetEFieldTypeFromString(arrStrFT[jField])
                    }); ;
                }

                dbTbl._lstField = lstdbf;
                dbTbl._lstaIndex = new List<int[]>();

                dbTbl._lstlstData = new List<IEnumerable<Object>>();
                for (int jLine = __jCountHeaderLines; jLine < arrStr.Length; jLine++)
                {
                    String[] arrStrData = Util.GetFieldsPersistedData(arrStr[jLine]);
                    dbTbl._lstlstData.Add(arrStrData);
                }
                for (int jField = 0; jField < dbTbl._lstField.Count; jField++)
                {
                    dbRet.AddIndex(dbTbl._sName, jField);
                }
     

            }

            return dbRet;
        }
    }

    public class DBField
    {
        public EFieldType _eft;
        public Boolean _fAllowNull;
        public Boolean _fIsIndexed;
        public String _sName;
    }

    public class DBTable
    {
        public List<DBField> _lstField;
        public String _sName;
        public List<IEnumerable<Object>> _lstlstData;
        public List<int[]> _lstaIndex;
    }
}