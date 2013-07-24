package com.zgy.util.method;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 为什么搞这个双端队列？
 * 
 * @ClassName: MessagesQueue
 * @Description:A data structure of deque
 * @author:xulei
 * @date Dec 7, 2011 11:30:10 AM
 * @param <E>
 */
public class Deque<E> {

	static final class Node<E> {

		E item;
		Node<E> prev;
		Node<E> next;

		Node(E x, Node<E> p, Node<E> n) {
			item = x;
			prev = p;
			next = n;
		}
	}

	// 不是Serializable对象，为什么关闭序列化？
	private transient Node<E> first;
	private transient Node<E> last;
	// 队列中元素数目
	private transient int count;
	// 元素容量
	private final int capacity;
	// 重入锁（ReentrantLock）是一种递归无阻塞的同步机制 没有interrupt()？
	private final ReentrantLock lock = new ReentrantLock();

	// public MessagesQueue() {
	// this(Integer.MAX_VALUE);
	// }

	/**
	 * constructor
	 * 
	 * @param capacity
	 */
	public Deque(int capacity) {
		if (capacity <= 0)
			throw new IllegalArgumentException();
		this.capacity = capacity;
	}

	/**
	 * 
	 * @param e
	 * @return
	 */
	private boolean linkFirst(E e) {
		if (count >= capacity)
			return false;
		++count;
		Node<E> f = first;
		Node<E> x = new Node<E>(e, null, f);
		first = x;
		if (last == null)
			last = x;
		else
			f.prev = x;
		return true;
	}

	// private boolean linkLast(E e) {
	// if (count >= capacity)
	// return false;
	// ++count;
	// Node<E> l = last;
	// Node<E> x = new Node<E>(e, l, null);
	// last = x;
	// if (first == null)
	// first = x;
	// else
	// l.next = x;
	// return true;
	// }

	/**
	 * 
	 * @return
	 */
	private E unlinkFirst() {
		Node<E> f = first;
		if (f == null)
			return null;
		Node<E> n = f.next;
		first = n;
		if (n == null)
			last = null;
		else
			n.prev = null;
		--count;
		return f.item;
	}

	// @SuppressWarnings("unused")
	// private E unlinkLast() {
	// Node<E> l = last;
	// if (l == null)
	// return null;
	// Node<E> p = l.prev;
	// last = p;
	// if (p == null)
	// first = null;
	// else
	// p.next = null;
	// --count;
	// return l.item;
	// }

	/**
	 * 
	 * @param x
	 */
	private void unlink(Node<E> x) {
		Node<E> p = x.prev;
		Node<E> n = x.next;
		if (p == null) {
			if (n == null)
				first = last = null;
			else {
				n.prev = null;
				first = n;
			}
		} else if (n == null) {
			p.next = null;
			last = p;
		} else {
			p.next = n;
			n.prev = p;
		}
		--count;
	}

	/**
	 * 插入指定的元素在这个队列前面 除非它违反容量限制。 当使用一个capacity-restricted队列， 这种方法一般是可取的
	 * 
	 * @param e
	 * @return
	 */
	public boolean offerFirst(E e) {
		if (e == null)
			throw new NullPointerException();
		lock.lock();
		try {
			return linkFirst(e);
		} finally {
			lock.unlock();
		}
	}

	// public boolean offerLast(E e) {
	// if (e == null)
	// throw new NullPointerException();
	// lock.lock();
	// try {
	// return linkLast(e);
	// } finally {
	// lock.unlock();
	// }
	// }

	/**
	 * 删除元素
	 * 
	 * @param o
	 * @return
	 */
	public boolean remove(Object o) {
		if (o == null)
			return false;
		lock.lock();
		try {
			for (Node<E> p = first; p != null; p = p.next) {
				if (o.equals(p.item)) {
					unlink(p);
					return true;
				}
			}
			return false;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 检索，但不消除，队列的头item （换句话说，这一队列的第一个元素）， 或返回列表空，如果这个队列是空的。 这方法等价于peekfirst()。
	 */
	public E peek() {
		lock.lock();
		try {
			return (first == null) ? null : first.item;
		} finally {
			lock.unlock();
		}
	}

	// public E pollFirst() {
	// lock.lock();
	// try {
	// return unlinkFirst();
	// } finally {
	// lock.unlock();
	// }
	// }

	/**
	 * 删除集合的所有元素（可选操作）。
	 */
	public void clear() {
		lock.lock();
		try {
			while (!isEmpty()) {
				unlinkFirst();
			}
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 判断队列是否为空
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * 返回队列中元素数目
	 * 
	 * @return
	 */
	public int size() {
		lock.lock();
		try {
			return count;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 如果队列包含指定的元素，返回真 当且仅当这个队列包含至少一个元素o ，返回真
	 * 
	 * @param o
	 * @return
	 */
	public boolean contains(Object o) {
		if (o == null)
			return false;
		lock.lock();
		try {
			for (Node<E> p = first; p != null; p = p.next)
				if (o.equals(p.item))
					return true;
			return false;
		} finally {
			lock.unlock();
		}
	}
}
