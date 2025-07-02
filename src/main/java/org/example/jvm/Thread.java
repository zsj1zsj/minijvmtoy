package org.example.jvm;

import tech.medivh.classpy.classfile.bytecode.Instruction;

public class Thread {
    private String threadName;
    private JvmStack stack;

    private final PcRegister pcRegister;

    public Thread(String threadName, StackFrame stackFrame, BootStrapClassLoader classLoader) {
        this.threadName = threadName;
        this.stack = new JvmStack();
        this.stack.push(stackFrame);
        this.pcRegister = new PcRegister(stack);
    }

    public void start() {
        for (Instruction instruction : pcRegister) {
            System.out.println(instruction);
        }
    }
}
