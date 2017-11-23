package handlers.controls;

import java.util.ArrayList;

/**
 * Created by dwbrite on 5/6/16.
 */
public class KeyList<T> {
	ArrayList<T> values;
	
	ArrayList<String> keys;
	
	private T t;
	
	public KeyList() {
		this.values = new ArrayList<>();
		this.keys = new ArrayList<>();
	}
	
	public T get(String key) {
		return values.get(keys.indexOf(key));
	}
	
	public T get(int key) {
		return values.get(key);
	}
	
	public void set(String key, T value) {
		int index = keys.indexOf(key);
		if (index == -1) {
			values.add(value);
			keys.add(key);
		} else {
			values.set(index, value);
		}
	}
	
	public void remove(String key) {
		values.remove(keys.indexOf(key));
	}
	
	public void remove(T obj) {
		values.remove(obj);
	}
	
	public int indexOf(T obj) { return values.indexOf(obj); }
	
	public int indexOf(String key) { return keys.indexOf(key); }
	
	public int size() { return keys.size(); }
}
