package stuff;

import java.util.LinkedList;

public class SyncList<E> extends LinkedList<E> {
	public boolean syncAdd(E e) {
		synchronized (this) {
			return super.add(e);
		}
	}
	
	public E syncRemove(int index) {
		synchronized (this) {
			return super.remove(index);
		}
	}
}
