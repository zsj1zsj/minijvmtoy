package org.example.jvm;

import tech.medivh.classpy.classfile.bytecode.Instruction;

import java.util.Iterator;

public class PcRegister implements Iterable<Instruction> {

    private final JvmStack jvmStack;

    public PcRegister(JvmStack jvmStack) {
        this.jvmStack = jvmStack;
    }

    @Override
    public Iterator<Instruction> iterator() {
        return new Itr();
    }

    class Itr implements Iterator<Instruction> {

        @Override
        public boolean hasNext() {
            return !jvmStack.isEmpty();
        }

        @Override
        public Instruction next() {
            StackFrame frame = jvmStack.peek();
            return frame.getNextInstruction();
        }
    }
}
