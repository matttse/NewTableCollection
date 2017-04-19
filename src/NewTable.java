import java.util.LinkedList;
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
