package it.unibs.fp.ourlib;

public class ArrayEMatrici {
    public static void stampaArray(int[] arrayDaStampare) {
        for (int i = 0; i < arrayDaStampare.length; i++)
            System.out.println(arrayDaStampare[i]);
    }

    public static void stampaArray(double[] arrayDaStampare) {
        for (int i = 0; i < arrayDaStampare.length; i++)
            System.out.println(arrayDaStampare[i]);
    }

    public static void stampaArray(char[] arrayDaStampare) {
        for (int i = 0; i < arrayDaStampare.length; i++)
            System.out.println(arrayDaStampare[i]);
    }

    public static void stampaArray(String[] arrayDaStampare) {
        for (int i = 0; i < arrayDaStampare.length; i++)
            System.out.println(arrayDaStampare[i]);
    }

    public static void stampaArray(int[] arrayDaStampare, int numeroElementiDaStampare) {
        for (int i = 0; i < numeroElementiDaStampare; i++)
            System.out.println(arrayDaStampare[i]);
    }

    public static void stampaArray(double[] arrayDaStampare, int numeroElementiDaStampare) {
        for (int i = 0; i < numeroElementiDaStampare; i++)
            System.out.println(arrayDaStampare[i]);
    }

    public static void stampaArray(char[] arrayDaStampare, int numeroElementiDaStampare) {
        for (int i = 0; i < numeroElementiDaStampare; i++)
            System.out.println(arrayDaStampare[i]);
    }

    public static void stampaArray(String[] arrayDaStampare, int numeroElementiDaStampare) {
        for (int i = 0; i < numeroElementiDaStampare; i++)
            System.out.println(arrayDaStampare[i]);
    }

    public static void stampaMatrice(int[][] arrayDaStampare) {
        for (int i = 0; i < arrayDaStampare.length; i++) {
            for (int j = 0; j < arrayDaStampare[i].length; j++) {
                System.out.print(String.format("%d\t", arrayDaStampare[i][j]));
            }
            System.out.println();
        }
    }

    public static void stampaMatrice(double[][] arrayDaStampare) {
        for (int i = 0; i < arrayDaStampare.length; i++) {
            for (int j = 0; j < arrayDaStampare[i].length; j++) {
                System.out.print(String.format("%.2f\t", arrayDaStampare[i][j]));
            }
            System.out.println();
        }
    }

    public static void stampaMatrice(char[][] arrayDaStampare) {
        for (int i = 0; i < arrayDaStampare.length; i++) {
            for (int j = 0; j < arrayDaStampare[i].length; j++) {
                System.out.print(String.format("%c\t", arrayDaStampare[i][j]));
            }
            System.out.println();
        }
    }

    public static void stampaMatrice(String[][] arrayDaStampare) {
        for (int i = 0; i < arrayDaStampare.length; i++) {
            for (int j = 0; j < arrayDaStampare[i].length; j++) {
                System.out.print(String.format("%s\t", arrayDaStampare[i][j]));
            }
            System.out.println();
        }
    }

    public static void stampaMatrice(int[][] arrayDaStampare, int numeroRigheDaStampare, int numeroColonneDaStampare) {
        for (int i = 0; i < numeroRigheDaStampare; i++) {
            for (int j = 0; j < numeroColonneDaStampare; j++) {
                System.out.print(String.format("%d\t", arrayDaStampare[i][j]));
            }
            System.out.println();
        }
    }

    public static void stampaMatrice(double[][] arrayDaStampare, int numeroRigheDaStampare, int numeroColonneDaStampare) {
        for (int i = 0; i < numeroRigheDaStampare; i++) {
            for (int j = 0; j < numeroColonneDaStampare; j++) {
                System.out.print(String.format("%.2f\t", arrayDaStampare[i][j]));
            }
            System.out.println();
        }
    }

    public static void stampaMatrice(char[][] arrayDaStampare, int numeroRigheDaStampare, int numeroColonneDaStampare) {
        for (int i = 0; i < numeroRigheDaStampare; i++) {
            for (int j = 0; j < numeroColonneDaStampare; j++) {
                System.out.print(String.format("%c\t", arrayDaStampare[i][j]));
            }
            System.out.println();
        }
    }

    public static void stampaMatrice(String[][] arrayDaStampare, int numeroRigheDaStampare, int numeroColonneDaStampare) {
        for (int i = 0; i < numeroRigheDaStampare; i++) {
            for (int j = 0; j < numeroColonneDaStampare; j++) {
                System.out.print(String.format("%s\t", arrayDaStampare[i][j]));
            }
            System.out.println();
        }
    }

}
