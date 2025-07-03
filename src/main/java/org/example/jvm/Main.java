package org.example.jvm;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        HotSpot hotSpot = new HotSpot("org.example.codes.Demo", "/Users/lynn/IdeaProjects/jvmdemo/target/classes:");
        hotSpot.start();
    }
}