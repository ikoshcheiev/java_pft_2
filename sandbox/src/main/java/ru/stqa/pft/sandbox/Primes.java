package ru.stqa.pft.sandbox;

public class Primes {
    //l4_m1
    public static boolean isPrimeFast(int n) {
        int m = (int)Math.sqrt(n);
        for (int i = 2; i < m; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPrimeWhile(int n) {
        int i = 2;

        while(i < n && n % i != 0){
            i++;
        }
        return true;
    }

    public static boolean isPrime(long n) {
        for (long i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
