package com.zgy.util.method;

import java.util.LinkedList;

public class Stack<T> {

	private LinkedList<T> storage = new LinkedList<T>();

	/** 入栈 */
	public void push(T v) {
		storage.addFirst(v);
	}

	/** 出栈，但不删除 */
	public T peek() {
		return storage.getFirst();
	}

	/** 出栈 */
	public T pop() {
		return storage.removeFirst();
	}

	/** 栈是否为空 */
	public boolean empty() {
		return storage.isEmpty();
	}

	/** 打印栈元素 */
	public String toString() {
		return storage.toString();
	}
}
