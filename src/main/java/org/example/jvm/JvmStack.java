package org.example.jvm;

import java.util.ArrayDeque;
import java.util.Deque;

public class JvmStack {
    private final Deque<StackFrame> stack = new ArrayDeque<>();

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public StackFrame peek() {
        return stack.peek();
    }

    public void push(StackFrame stackFrame) {
        stack.push(stackFrame);
    }

    public StackFrame pop() {
        return stack.pop();
    }
}
