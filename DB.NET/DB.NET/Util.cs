using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DB.NET
{
    public class Util
    {
        public static String Concat(IEnumerable<String> lstStr)
        {
            StringBuilder sb = new StringBuilder();
            int j;
            int jMax = lstStr.Count();
            for (j = 0; j < jMax; j++)
            {
                if (j > 0)
                {
                    sb.Append('\t');
                }
                sb.Append(lstStr.ElementAt(j));
            }
            return sb.ToString();
        }
        public static String[] GetFieldsPersistedData(String s)
        {
            return s.Split('\t');
        }
        public static EFieldType GetEFieldTypeFromString(String s)
        {
            EFieldType eftRet = EFieldType.Unknown;

            if (String.CompareOrdinal(s, "Integer") == 0)
            {
                eftRet = EFieldType.Integer;
            }
            else
            if (String.CompareOrdinal(s, "Decimal") == 0)
            {
                eftRet = EFieldType.Decimal;
            }
            else if (String.CompareOrdinal(s, "Text") == 0)
            {
                eftRet = EFieldType.Text;
            }
            else if (String.CompareOrdinal(s, "Boolean") == 0)
            {
                eftRet = EFieldType.Boolean;
            }
            else if (String.CompareOrdinal(s, "Datetime") == 0)
            {
                eftRet = EFieldType.Datetime;
            }
            else if (String.CompareOrdinal(s, "Guid") == 0)
            {
                eftRet = EFieldType.Guid;
            }
            else
            {
                throw new Exception("Bad EFieldType text");
            }
            return eftRet;
        }
    }
}
