

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

	private Vector<LinkedList< HashPair<K,E> > > table;	
	private Object[] data;
	private Object[] keys;
	private int manyItems;
	
	public NewTable(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Capacity is not greater than 0. ");
		}
		table = new Vector<LinkedList< HashPair<K,E> > >(capacity);
		LinkedList<HashPair<K, E>> list = new LinkedList<HashPair<K, E>>();
		manyItems = 0;
		keys = new Object[capacity];
		data = new Object[capacity];
		for (int i = 0; i < capacity; i++) {
			System.out.print(list);
			System.out.print(i);
			table.add(list);
		}
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
		for (int i = 0; i < table.capacity(); i++) {
			if (table.get(i) != null) {
				count++;
			}
		}
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
    	for (int i = 0; i < data.length; i++) {
			keys[i] = null;
			data[i] = null;
		}
    	table.clear();

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
		return (hash(key) != -1);
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
	public E remove(K key) {
        int index = hash(key);
        
        if (index != -1) {
    		//the key is not already in the table
    		//setup a listiterator that can step through the one linkedlist that might
    		//already have an element with the given key
    		LinkedList<HashPair<K, E>> oneList = table.get(index);	
    		ListIterator<HashPair<K,E>> cursor = oneList.listIterator(0);
    		//two variables for the new hashpair
    		HashPair<K,E> pair;
    		while (cursor.hasNext()) {//step through the one linked list using the iterator
    			pair = cursor.next();
    			if (pair.key.equals(key)) {//check given key already in list
    				cursor.remove();
    				manyItems--;
    				keys[index] = null;
    				data[index] = null;
    			}
    		}
        }
        System.out.println("not found");
		return null;
		
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
	public E put(K key, E element) {
		if (key == null || element == null) {
			throw new NullPointerException("key or element is null");
		}
		int index = hash(key);
		//the key is not already in the table
		//setup a listiterator that can step through the one linkedlist that might
		//already have an element with the given key
		LinkedList<HashPair<K, E>> oneList = table.get(index);	
		ListIterator<HashPair<K,E>> cursor = oneList.listIterator(0);
		//two variables for the new hashpair
		HashPair<K,E> pair;
		E answer = null;
		while (cursor.hasNext()) {//step through the one linked list using the iterator
			pair = cursor.next();
			if (pair.key.equals(key)) {//check given key already in list
				answer = pair.element;
				pair.element = element;
				manyItems++;
				keys[index] = key;
				data[index] = element;
				return answer;
			}
		}
		//the specified key was not on oneList, so create a new node for the new entry
		pair = new HashPair<K,E>();
		pair.key = key;
		pair.element = element;
		oneList.add(pair);
		
		return answer;
				
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
		return Math.abs(key.hashCode()) % table.size();
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
        int index = hash(key);
        
        if (index != -1) {
    		//the key is not already in the table
    		//setup a listiterator that can step through the one linkedlist that might
    		//already have an element with the given key
    		LinkedList<HashPair<K, E>> oneList = table.get(index);	
    		ListIterator<HashPair<K,E>> cursor = oneList.listIterator(0);
    		//two variables for the new hashpair
    		HashPair<K,E> pair;
    		E answer = null;
    		while (cursor.hasNext()) {//step through the one linked list using the iterator
    			pair = cursor.next();
    			if (pair.key.equals(key)) {//check given key already in list
    				answer = pair.element;
    				return answer;
    			}
    		}
        }
		return null;
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
            System.out.println("4. contains key");
            System.out.println("5. clear");
            System.out.println("6. size");
 
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
                System.out.println("Enter key");
                System.out.println("Value = "+ nt.containsKey( scan.next() )); 
                break;
            case 5 : 
                nt.clear();
                System.out.println("Hash Table Cleared\n");
                System.exit(0);
                break;
            case 6 : 
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
	E element;
}
