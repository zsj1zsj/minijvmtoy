package org.example.codes;

public class Demo {
    public static void main(String[] args) {
        System.out.println(3);
        System.out.println(max(3, 1));
    }

    public static int max(int a, int b) {
        if (a > b) {
            return a;
        }
        return b;
    }
}
