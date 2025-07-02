package org.example.jvm;

import org.example.jvm.BootStrapClassLoader;
import tech.medivh.classpy.classfile.ClassFile;

import java.io.File;
import java.util.Arrays;

public class HotSpot {
    private String mainClass;

    private BootStrapClassLoader classLoader;


    public HotSpot(String mainClass, String classPathString) {
        this.mainClass = mainClass;
        this.classLoader = new BootStrapClassLoader(Arrays.asList(classPathString.split(File.separator)));
    }


    public void start() throws ClassNotFoundException {
        ClassFile classFile = classLoader.loadClass(mainClass);
    }
}
