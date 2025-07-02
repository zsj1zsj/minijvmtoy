package org.example.jvm;

import tech.medivh.classpy.classfile.MethodInfo;
import tech.medivh.classpy.classfile.bytecode.Instruction;
import tech.medivh.classpy.classfile.constant.ConstantPool;

import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class StackFrame {

    private MethodInfo methodInfo;
    private Object[] localVariables;

    private Deque<Object> operandStack;

    private List<Instruction> codes;
    private ConstantPool constantPool;

    int currentIndex;

    public StackFrame(MethodInfo methodInfo, ConstantPool constantPool, Object... args) {
        this.methodInfo = methodInfo;
        this.localVariables = new Object[methodInfo.getMaxLocals()]; //max是局部变量表的的大小
        this.operandStack = new ArrayDeque<>();
        this.codes = methodInfo.getCodes();
        this.constantPool = constantPool;
        System.arraycopy(args, 0, localVariables, 0, args.length);
    }

    public Instruction getNextInstruction() {
        return codes.get(currentIndex++);
    }
}
