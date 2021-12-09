using System;
using System.Collections.Generic;
using System.Text;

namespace NumberCrunchDotNet
{
    class DataIndexer
    {
    }
    public class ArrayOps
    {



        public static int[] ComputeIndexBy_sName(EmployeeData[] aed)
        {
            int[] ajIndexRet = new int[aed.Length];
            long jSwapCount = 0;
            Boolean fSwapped = true;
            int jPass = 0;
            int j;
            for (j = 0; j < ajIndexRet.Length; j++)
            {
                ajIndexRet[j] = j;
            }
            while (fSwapped)
            {
                fSwapped = false;
                for (j = 0; j < aed.Length - 1 - jPass; j++)
                {
                    if (String.Compare(aed[ajIndexRet[j]].sName, aed[ajIndexRet[j + 1]].sName) > 0)
                    {
                        int jTemp;
                        jTemp = ajIndexRet[j];
                        ajIndexRet[j] = ajIndexRet[j + 1];
                        ajIndexRet[j + 1] = jTemp;
                        fSwapped = true;
                        jSwapCount++;
                    }
                }
                jPass++;
            }
            Console.WriteLine("Swap Count = " + jSwapCount);
            return ajIndexRet;
        }

        public static void mergeSortedArrays(int[] arrInt1, int[] arrInt2, int[] arrIntMerged)
        {
            int jM = 0;
            int j1 = 0;
            int j2 = 0;
            while (jM < arrIntMerged.Length)
            {
                int jVal;
                if (j2 == arrInt2.Length)
                {
                    jVal = arrInt1[j1];
                    j1++;
                }
                else if (j1 == arrInt1.Length)
                {
                    jVal = arrInt1[j2];
                    j2++;
                }
                else
                {
                    if (arrInt1[j1] < arrInt2[j2])
                    {
                        jVal = arrInt1[j1];
                        j1++;

                    }
                    else
                    {
                        jVal = arrInt2[j2];
                        j2++;
                    }
                }
                arrIntMerged[jM] = jVal;
                jM++;
            }
            Console.WriteLine();
        }

        public static void doBubbleSort(int[] arrInt, Boolean fAscending)
        {

            long jSwapCount = 0;
            Boolean fSwapped = true;
            int jPass = 0;
            int jMultiplier = fAscending ? 1 : -1;

            while (fSwapped)
            {
                fSwapped = false;
                for (int j = 0; j < arrInt.Length - 1 - jPass; j++)
                {
                    if (jMultiplier * arrInt[j] > jMultiplier * arrInt[j + 1])
                    {
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
            Console.WriteLine("Swap Count = " + jSwapCount);
        }

        private static void reverseArray(int[] arrInt)
        {
            int j;
            for (j = 0; j < arrInt.Length / 2; j++)
            {
                int jTemp;
                jTemp = arrInt[j];
                arrInt[j] = arrInt[arrInt.Length - 1 - j];
                arrInt[arrInt.Length - 1 - j] = jTemp;

            }

        }

    }
}
