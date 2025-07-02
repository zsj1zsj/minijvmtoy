package org.example.jvm;

public class Thread {
    private String threadName;
    private JvmStack stack;

    private final PcRegister pcRegister;

    public Thread(String threadName, StackFrame stackFrame) {
        this.threadName = threadName;
        this.stack = new JvmStack();
        this.stack.push(stackFrame);
        this.pcRegister = new PcRegister(stack);
    }

    public void start() {

    }
}
