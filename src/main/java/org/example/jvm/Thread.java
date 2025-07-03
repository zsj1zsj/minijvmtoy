package org.example.jvm;

import tech.medivh.classpy.classfile.ClassFile;
import tech.medivh.classpy.classfile.MethodInfo;
import tech.medivh.classpy.classfile.bytecode.GetStatic;
import tech.medivh.classpy.classfile.bytecode.Instruction;
import tech.medivh.classpy.classfile.bytecode.InvokeVirtual;
import tech.medivh.classpy.classfile.constant.ConstantMethodrefInfo;
import tech.medivh.classpy.classfile.constant.ConstantPool;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Thread {
    private String threadName;
    private JvmStack stack;

    private final PcRegister pcRegister;

    private BootStrapClassLoader classLoader;

    public Thread(String threadName, StackFrame stackFrame, BootStrapClassLoader classLoader) {
        this.threadName = threadName;
        this.stack = new JvmStack();
        this.stack.push(stackFrame);
        this.pcRegister = new PcRegister(stack);
//        this.classLoader = new BootStrapClassLoader();
    }

    public void start() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for (Instruction instruction : pcRegister) {
            System.out.println(instruction);
            ConstantPool constantPool = stack.peek().constantPool;
            switch (instruction.getOpcode()) {
                case getstatic -> {
                    GetStatic getStatic = (GetStatic) instruction;
                    String className = getStatic.getClassName(constantPool);
                    String fieldName = getStatic.getFieldName(constantPool);
                    Object staticField;
                    if (className.contains("java")) {
                        Class<?> aClass = Class.forName(className);
                        Field declaredField = aClass.getField(fieldName);
                        staticField = declaredField.get(null);
                        stack.peek().pushObjectToOperandStack(staticField);
                    }
                }
                case iconst_1 -> {
                    stack.peek().pushObjectToOperandStack(1);
                }
                case iconst_3 -> {
                    stack.peek().pushObjectToOperandStack(3);
                }
                case invokevirtual -> {
                    InvokeVirtual invokeVirtual = (InvokeVirtual) instruction;
                    ConstantMethodrefInfo methodInfo = invokeVirtual.getMethodInfo(constantPool);
                    String className = methodInfo.className(constantPool);
                    String methodName = methodInfo.methodName(constantPool);
                    List<String> params = methodInfo.paramClassName(constantPool);
                    if (className.contains("java")) {
                        Class<?> aClass = Class.forName(className);
                        Method declaredMethod = aClass.getDeclaredMethod(methodName, params.stream().map(this::nameToClass).toArray(Class[]::new));
                        Object[] args = new Object[params.size()];
                        for (int i = args.length - 1; i >= 0; i--) {
                            args[i] = stack.peek().operandStack.pop();
                        }
                        Object result = declaredMethod.invoke(stack.peek().operandStack.pop(),args);
                        if(!methodInfo.isVoid(constantPool)){
                            stack.peek().pushObjectToOperandStack(result);
                        }
                        return;
                    }
                    ClassFile classFile = classLoader.loadClass(className);
                    MethodInfo finalMethodInfo = classFile.getMethods(methodName).get(0);
                    Object[] args = new Object[params.size() + 1];
                    for (int i = args.length - 1; i >= 0; i--) {
                        args[i] = stack.peek().operandStack.pop();
                    }
                    StackFrame stackFrame = new StackFrame(finalMethodInfo, classFile.getConstantPool(), args);
                    stack.push(stackFrame);
                }
                case _return -> {
                    this.stack.pop();
                }
            }
        }
    }

    private Class<?> nameToClass(String className) {
        if(className.equals("int")){
            return int.class;
        }
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
