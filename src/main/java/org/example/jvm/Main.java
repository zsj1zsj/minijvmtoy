package org.example.jvm;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        HotSpot hotSpot = new HotSpot("org.example.codes.Demo", "/Users/lynn/IdeaProjects/jvmdemo/target/classes;");
        hotSpot.start();
    }
}