import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by:
 * Kasper Skov Johansen (kajoh14@student.sdu.dk)
 * Morten Kristian Jæger (mojae15@student.sdu.dk)
 */
public class PQHeap implements PQ{

	public static ArrayList<Element> Queue; 							// Queue used for the heap

	public PQHeap(int MaxElements){								//constructor
		this.Queue = new ArrayList<>(MaxElements);
	}

	@Override
	public Element extractMin() {	
		try{
			Element min = Queue.get(0);
			Element max = Queue.get(Queue.size()-1);
			Queue.set(0, max);
			Queue.remove(Queue.size()-1);
			Min_heapify(Queue, 0);
			return min;
		}catch(ArrayIndexOutOfBoundsException k){
			System.out.println("No elements in queue");
		}
		return null;
	}

	@Override
	public void insert(Element e) {
		int i = Queue.size();
		try {
			Queue.add(e);				
		} catch (ArrayIndexOutOfBoundsException k) {
			System.out.println("Array is out of bounds");
		}
		while (i > 0 && Queue.get(Parent(i)).key >= Queue.get(i).key) {
			Collections.swap(Queue, i, Parent(i));                                
			i = Parent(i);                                                    
		}
	}

	public static void Min_heapify(ArrayList<Element> A, int i){ 
		int l = Left(i);										
		int r = Right(i);
		int smallest;
		if (l < A.size() && A.get(l).key <= A.get(i).key) { 	// Compares the key of the left element to the parent, and sets
			smallest = l;										// "smallest" to hold the smallest of the two.
		}
		else {
			smallest = i;
		}
		if (r < A.size() && A.get(r).key <= A.get(smallest).key) { 	// Compares the key of the right element to the key of the
			smallest = r;											// smallest element, and changes "smallest" accordingly.
		}
		if (smallest != i) {									// If the smallest element of the parent, left and right was not the paren
			Collections.swap(A, i, smallest);					// swap i and the smallest, since i is larger than it
			Min_heapify(A, smallest);							// Call Min_heapify recursively until we have gone through the whole list.
		}
	}
	
	/**
	 * Returns the parent
	 * Example using the root's left child: ceil(1/2.)-1 = 0, which is the root's index
	 */
	private static int Parent(int i){
		return (int)Math.ceil(i/2.)-1;
	}
	
	/**
	 * Returns the left child in the 0-indexed queue
	 * Excample by using the root: (2*0)+1 = 1, which is the left child
	 */
	private static int Left(int i){					
		return (int)Math.ceil(2*i)+1;
	}
	/**
	 * Returns the right child in the 0-indexed queue
	 * Excample by using the root: (2*0)+2 = 2, which is the right child
	 */
	private static int Right(int i){				
		return (int)Math.ceil((2*i)+2);
	}
}
