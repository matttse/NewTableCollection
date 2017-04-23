import java.nio.channels.IllegalSelectorException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;
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
	private int maxSize, manyItems;
	
	
	
	public NewTable(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Capacity is not greater than 0. ");
		}
		manyItems = 0;
		keys = new Object[capacity];
		data = new Object[capacity];
		hasBeenUsed = new boolean[capacity];
		maxSize = capacity;
	}
	
	/*
	 * @Name: size
	 * 
	 * @Function/Purpose: returns number of elements currently in the table
	 * 
	 * @Returns-
	 * number of keys in table
	 * 
	 * @Throws- NullPointerException
	 * Indicates that key is null
	 * 
	 */
	public int size() {
		int count = 0;
		
		count = keys.length;
		
		return count;
		
	}

	/*
	 * @Name: clear
	 * 
	 * @Function/Purpose: removes all of the mappings from this map

	 * 
	 * @Post-
	 * The map will be empty after this call returns
	 * 
	 * @Throws- UnsupportedOperationException
	 * if the clear operation is not supported by this map
	 * 
	 */
    public void clear() {
    	manyItems = 0;
		keys = new Object[maxSize];
		data = new Object[maxSize];
		hasBeenUsed = new boolean[maxSize];

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
		
		int index = findIndex(key);
		
		E answer;
		
		if (index != -1) {
			//the key is already in the table
			answer = (E) data[index];
			data[index] = element;
			return answer;
		} else if (manyItems < data.length) {
			//the key is not yet in this table
			index = hash(key);
			while (keys[index] != null)
				index = nextIndex(index);
			keys[index] = key;
			data[index] = element;
			hasBeenUsed[index] = true;
			manyItems++;
			return null;
		} else {
			//table is full
			throw new IllegalStateException("Table is full. ");
		}
				
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
		
        Scanner scan = new Scanner(System.in);
        System.out.println("Table Test\n\n");
        System.out.println("Enter size");
        NewTable<String, Integer> nt = new NewTable<String, Integer>(scan.nextInt());
        
        char ch;
        
        do    
        {
            System.out.println("\nTable Operations\n");
            System.out.println("1. put ");
            System.out.println("2. remove");
            System.out.println("3. get");            
            System.out.println("4. clear");
            System.out.println("5. size");
 
            int choice = scan.nextInt();            
            switch (choice)
            {
            case 1 : 
                System.out.println("Enter key and value");
                String K = scan.next();
                Integer E = scan.nextInt();
                nt.put(K, E); 
                break;                          
            case 2 :                 
                System.out.println("Enter key");
                nt.remove( scan.next() ); 
                break;                        
            case 3 : 
                System.out.println("Enter key");
                System.out.println("Value = "+ nt.get( scan.next() )); 
                break;                                   
            case 4 : 
                nt.clear();
                System.out.println("Hash Table Cleared\n");
                break;
            case 5 : 
                System.out.println("Size = "+ nt.size() );
                break;         
            default : 
                System.out.println("Wrong Entry \n ");
                break;   
            }
            /** Display hash table **/  
 
            System.out.println("\nDo you want to continue (Type y or n) \n");
            ch = scan.next().charAt(0);                        
        } while (ch == 'Y'|| ch == 'y');  
        

	}

}

class HashPair<K, E> {
	K key;
	E Element;
}
