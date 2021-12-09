using System;
using System.IO;
using System.Text;

namespace NumberCrunchDotNet
{
    public class EmployeeData
    {
        public String sEID;
        public String sName;
        public int nGrade;
        public DateTime dtJoining;
        public double qSalary;
        public String ToMyString()
        {
            StringBuilder sb = new StringBuilder();
            sb.Append(sEID);
            sb.Append(" : ");
            sb.Append(sName);
            sb.Append(" : ");
            sb.Append(nGrade);
            sb.Append(" : ");
            sb.Append(dtJoining);
            sb.Append(" : ");
            sb.Append(qSalary);
            return sb.ToString();

        }
    }
    class Program
    {
        public static void Main(String[] args)
        {
            Main2(args);
            String[] arrstrIn = File.ReadAllLines(@"C:\edu\somedata.csv");
            EmployeeData[] arrED = new EmployeeData[arrstrIn.Length - 1];


            DoSortTest(arrstrIn, arrED);
            DoLinkedListTest(arrED);
        }

        private static void DoSortTest(string[] arrstrIn, EmployeeData[] arrED)
        {
            int j;
            for (j = 1; j < arrstrIn.Length; j++)
            {
                String sCur = arrstrIn[j];
                String[] asParts = sCur.Split(',');
                EmployeeData ed = new EmployeeData();
                ed.sEID = asParts[0];
                ed.sName = asParts[1];
                ed.nGrade = int.Parse(asParts[2]);
                ed.dtJoining = DateTime.Parse(asParts[3]);
                ed.qSalary = double.Parse(asParts[4]);
                arrED[j - 1] = ed;
            }
            int[] ajIndexBy_sName = ArrayOps.ComputeIndexBy_sName(arrED);
            for (j = 0; j < ajIndexBy_sName.Length; j++)
            {
                Console.WriteLine(arrED[ajIndexBy_sName[j]].ToMyString());
            }
        }

        private static void DoLinkedListTest(EmployeeData[] arrED)
        {
            LinkedList<EmployeeData> lstED = new LinkedList<EmployeeData>();
            for (int j = 0; j < arrED.Length; j++)
            {
                lstED.Append(arrED[j]);
            }
            Node<EmployeeData> nodeNext;
            Node<EmployeeData> nodeHead = lstED._nodeHead;
            do
            {
                nodeNext = nodeHead._nodeNext;
                if (nodeNext != null)
                {
                    Console.WriteLine(nodeNext._tPayload.sName);
                    nodeHead = nodeNext;
                }
            } while (nodeNext != null);
            lstED.Remove(3);
            Console.WriteLine("---");
           nodeHead = lstED._nodeHead;
            do
            {
                nodeNext = nodeHead._nodeNext;
                if (nodeNext != null)
                {
                    Console.WriteLine(nodeNext._tPayload.sName);
                    nodeHead = nodeNext;
                }
            } while (nodeNext != null);
            EmployeeData edAt3 =  lstED.Get(3);
            Console.WriteLine(edAt3.sName);

        }

        public static void Main2(String[] args)
        {
            Random rnd = new Random(123);
            int[] arrInt1 = new int[6];
            for (int j = 0; j < arrInt1.Length; j++)
            {
                arrInt1[j] = rnd.Next(100);
            }
            ArrayOps.doBubbleSort(arrInt1, true);
            int[] arrInt2 = new int[9];
            for (int j = 0; j < arrInt2.Length; j++)
            {
                arrInt2[j] = rnd.Next(100);
            }
            ArrayOps.doBubbleSort(arrInt2, true);
            int[] arrIntMerged = new int[arrInt1.Length + arrInt2.Length];
            ArrayOps.mergeSortedArrays(arrInt1, arrInt2, arrIntMerged);

        }




    }
}
