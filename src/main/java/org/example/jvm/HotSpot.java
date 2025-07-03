package org.example.jvm;


import tech.medivh.classpy.classfile.ClassFile;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class HotSpot {
    private String mainClass;

    private BootStrapClassLoader classLoader;


    public HotSpot(String mainClass, String classPathString) {
        this.mainClass = mainClass;
        System.out.println(File.pathSeparator);
        this.classLoader = new BootStrapClassLoader(Arrays.asList(classPathString.split(File.pathSeparator)));
    }


    public void start() throws ClassNotFoundException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        ClassFile mainClassFile = classLoader.loadClass(mainClass);
        mainClassFile.getMainMethod();
        StackFrame mainFrame = new StackFrame(mainClassFile.getMainMethod(), mainClassFile.getConstantPool());
        Thread mainThread = new Thread("main", mainFrame, classLoader);
        mainThread.start();
    }
}
