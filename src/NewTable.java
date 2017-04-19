import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Vector;

/**
 * 
 */

/**
 * @author Matthew Tse
 * @param <K>
 * @param <E>
 *
 */
public class NewTable<K, E> {

	private Vector< LinkedList< HashPair<K,E> > > table;
	private Object[] data;
	private boolean[] hasBeenUsed;
	private Object[] keys;
	private int manyItems;
	
	public NewTable(int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException("Capacity is negative. ");
		}
		keys = new Object[capacity];
		data = new Object[capacity];
		hasBeenUsed = new boolean[capacity];
	}
	
	/*
	 * @Name: containsKey
	 * 
	 * @Function/Purpose: determine whether a specified key is in this table
	 * 
	 * @Parameters:
	 * 		{K} the non-null key to use
	 * 
	 * @Pre- key cannot be null
	 * 
	 * @Returns-
	 * true: if the table contains an object with the specified key
	 * false: otherwise.
	 * @Additional Comments:
	 * key.equals() is used to compare the key to the keys that are in the table 
	 * 
	 * @Throws- NullPointerException
	 * Indicates that key is null
	 * 
	 */
	public boolean containsKey(K key) {
		return (findIndex(key) != -1);
	}
	
	
	/*
	 * @Name: remove
	 * 
	 * @Function/Purpose: removes an object with a specified key
	 * 
	 * @Parameters:
	 * 		{K} the non-null key to use
	 * 
	 * @Pre- key cannot be null
	 * 
	 * @Post- if an object was found with the specified key, then that object has been
	 * removed and a copy of the removed object is return; otherwise, this
	 * table is unchanged and the null reference is returned. Note that 
	 * key.equals() is used to compare the key to the keys that are in this table 
	 * 
	 * @Throws- NullPointerException
	 * Indicates that key is null
	 * 
	 */
	@SuppressWarnings("unchecked")
	public E remove(K key) {
		int index = findIndex(key);
		E answer = null;
		
		if (index != -1) {
			answer = (E) data[index];
			keys[index] = null;
			data[index] = null;
			manyItems--;
		}
		
		return answer;
		
	}
	
	/*
	 * @Name: put
	 * 
	 * @Function/Purpose: Add a new element to this table using the specified key.
	 * 
	 * @Parameters:
	 * 		{K} the non-null key to use for the new element
	 * 		{E} the new element that's being added to this table
	 * 
	 * @Pre- If there is not already an element with the specified key,
	 * then this table's size must be less than its capacity (i.e. size() < capacity()
	 * Also, neither key nor element may be null
	 * 
	 * @Post- If this table already has an object with the specified key, then that object
	 * is replaced by element, and the return value is a reference to the replaced object. 
	 * Otherwise, the new element is added with the specified key, and the return value is
	 * null
	 * 
	 * @Throws- NullPointerException
	 * Indicates that key or element is null
	 * 
	 */
	@SuppressWarnings("unchecked")
	public E put(K key, E element) {
		
		if (key == null || element == null) {//setup throw
			throw new NullPointerException("key or element is null");
		}
		//set up listiterator that can step through the one linked list that might
		//already have an elenent with the given key
		int i = hash(key);
		LinkedList<HashPair<K,E>> oneList = table.get(i);
		ListIterator<HashPair<K,E>> cursor = oneList.listIterator(0);
		
		//Two other variables for the new HashPair (if needed) and the return value:
		HashPair<K,E> pair;
		E answer;
		
		//step through the one linked list using the iterator
		while (cursor.hasNext()) {
			pair = cursor.next();
			if (pair.key.equals(key)) {//check given key already in list
				answer = pair.Element;
				pair.Element = element;
				return answer;
			}
		}
		
		//specified key was not on oneList, create newnode for the new entry
		pair = new HashPair<K,E>();
		pair.key = key;
		pair.Element = element;
		oneList.add(pair);
		
		return null;		
	}
	
	/*
	 * @Name: hash
	 * 
	 * @Function/Purpose: the return value is a valid index of the table's array
	 * 
	 * @Parameters:
	 * 		{Object} key
	 * 
	 * @Additional Comments:
	 * The index is calculated as the remainder when the absolute value of the key's
	 * hash code is divided by the size of the table's arrays
	 * 
	 */
	private int hash(K key) {
		return Math.abs(key.hashCode()) % data.length;
	}
	
	/*
	 * @Name: get
	 * 
	 * @Function/Purpose: retrieve an object for a specified key
	 * 
	 * @Parameters:
	 * 		{K} the non-null key to use
	 * 
	 * @Pre- key cannot be null
	 * 
	 * @Throws- NullPointerException
	 * Indicates that key is null
	 * 
	 */
    public E get(K key) {
        int index = findIndex(key);
        
        if (index == -1) {
        	return null;
        } else {
        	return (E) data[index];
        }
    }

	/*
	 * @Name: findIndex
	 * 
	 * @Parameters:
	 * 		{K} the non-null key to use for the new element
	 * 
	 * @Post- if the specified key is found in the table, then the return
	 * value is the index of the specified key. otherwise, return value is -1
	 * 
	 */
	private int findIndex(K key) {
		int count = 0;
		int i = hash(key);
		
		while ((count < data.length) && (hasBeenUsed[i])) {
			if (key.equals(keys[i])) {
				return i;
			}
			count++;
			i = nextIndex(i);
		}
		
		
		
		return -1;
	}

	/*
	 * @Name: nextIndex
	 * 
	 * @Function/Purpose: the return value is normally i+1
	 * 
	 * @Parameters:
	 * 		{int} i
	 * 
	 * @Additional comments:
	 * if i+1 is the data.length, return value 0
	 * 
	 */
	private int nextIndex(int i) {
		if (i+1 == data.length) {
			return 0;
		} else {
			return i+1;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

class HashPair<K, E> {
	K key;
	E Element;
}
