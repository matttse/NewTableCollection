Project Name: New Table Collection
Author: Matthew Tse
Date: 04/25/2017

source:
	NewTable
Class HashPair<K, E>
	K key
	E element
	
Class NewTable
- public class NewTable
		The main class
		- parameters:
			- {K} the non-null key to use for the new element
			- {E} the new element that's being added to this table
Specification
- main
		public static void main(String[] args)
		instantiates NewTable constructor
		Object[] data;
		Object[] key;
		int manyItems
		Vector<LinkedList< HashPair<K,E> > > table;
		Methods:
			size
			clear	
			containsKey
			remove
			put
			hash
			get

Specification
- public int size
		returns number of elements currently in the table
	Returns:
		number of keys in table
- public void clear
		removes all of the mappings from this map
	Postcondition:
		The map will be empty after this call returns
- public boolean containsKey
		determine whether a specified key is in this table
	Parameters:
		{K} the non-null key to use
	Returns:
		- true: if the table contains an object with the specified key
		- false: otherwise.
- public E remove
		removes an object with a specified key
	Parameters:
		{K} the non-null key to use
	Postcondition:
		- if an object was found with the specified key, then that object has been
		removed and a copy of the removed object is return; otherwise, this
		table is unchanged and the null reference is returned. Note that 
		key.equals() is used to compare the key to the keys that are in this table 
- public E put
		Add a new element to this table using the specified key.
	Parameters:
		- {K} the non-null key to use for the new element
		- {E} the new element that's being added to this table
	Postcondition:
		- If this table already has an object with the specified key, then that object
		is replaced by element, and the return value is a reference to the replaced object. 
		Otherwise, the new element is added with the specified key, and the return value is
		null
- private int hash
		the return value is a valid index of the table's array
	Parameters:
		- {K} key
- public E get
		retrieve an object for a specified key
	Parameters:
		- {K} the non-null key to use
	
	