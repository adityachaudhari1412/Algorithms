using System;

namespace RecursiveAlgos
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello World!");
            int nFact = Factorial(6);
            int nGCD = GCD(123456, 87451);
        }

        private static int GCD(int n1, int n2)
        {
            if ((n1 == 0) || (n2 == 0))
            {
                throw new Exception("Cannot compute GCD of " + n1 + " & " + n2);
            }
            n1 = n1 < 0 ? -n1 : n1;
            n2 = n2 < 0 ? -n2 : n2;
            int nRet = GCDInternal(n1, n2);
            return nRet;
        }
        private static int GCDInternal(int n1, int n2)
        {
            int nRet = n1 % n2;
            if (nRet != 0)
            {
                nRet = GCDInternal(n2, nRet);
            }
            else
            {
                nRet = n2;
            }
            return nRet;
        }

        private static int Factorial(int n)
        {
            int nRet = -1;
            if (n < 0)
            {
                throw new Exception("Cannot compute factorial of " + n);
            }

            nRet = FactorialInternal(n);
            return nRet;
        }
        private static int FactorialInternal(int n)
        {
            int nRet;
            if (n < 2)
            {
                nRet = 1;
            }
            else
            {
                nRet = n * FactorialInternal(n - 1);
            }
            return nRet;
        }
    }
}
