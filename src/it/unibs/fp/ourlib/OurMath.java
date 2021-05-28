package it.unibs.fp.ourlib;

public class OurMath {
    public static double logBaseData(double argomento, double base) {
        return Math.log(argomento) / Math.log(base);
    }

    /**
     * Calcola il fattoriale di un numero intero.
     * Se il numero &egrave minore di zero ritorna -1.
     *
     * @param numero:int
     * @return risultato: long
     */
    public static long fattoriale(int numero) {
        long risultato = 1;
        if (numero < 0) {
            return -1;
        } else {
            for (int i = 1; i <= numero; i++) {
                risultato *= i;
            }
        }
        return risultato;
    }

    /**
     * Calcola l'MCD tra due numeri interi e positivi.
     * Qualora uno dei due numeri passati non rispetta questi criteri ritorna -1.
     *
     * @param a: long
     * @param b: long
     * @return mcd: long
     */
    public static long mcd(long a, long b) {
        long result = -1;
        if (a <= 0 || b <= 0) {
            return result;
        }
        long i;
        if (a < b) {
            i = a;
        } else {
            i = b;
        }
        while (true) {
            if (a % i == 0 && b % i == 0) {
                return i;
            }
            i--;
        }
    }

    /**
     * Calcola l'mcm tra due numeri interi e positivi.
     * Qualora uno dei due numeri passati non rispetta questi criteri ritorna -1.
     *
     * @param a: long
     * @param b: long
     * @return mcm: long
     */
    public static long mcm(long a, long b) {
        long result = -1;
        long max;
        if (a <= 0 || b <= 0) {
            return result;
        }
        if (a > b) {
            result = a;
            max = a;
        } else {
            result = b;
            max = b;
        }
        do {
            if (result % a == 0 && result % b == 0) {
                return result;
            }
            result = result + max;
        } while (true);
    }

    /**
     * Valuta se un numero &egrave primo.
     *
     * @param n: long
     * @return ePrimo: boolean
     */
    public static boolean isPrimo(long n) {
        if (n <= 1) {
            return false;
        } else {
            for (int i = 2; i <= n / 2; i++) {
                if (n % i == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Valuta se due numeri sono primi tra loro.
     *
     * @param a: long
     * @param b: long
     * @return sonoPrimiTraLoro: long
     */
    public static boolean primiTraLoro(long a, long b) {
        return mcd(a, b) == 1;
    }


}
