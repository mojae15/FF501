import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by:
 * Kasper Skov Johansen (kajoh14@student.sdu.dk)
 * Morten Kristian Jaeger (mojae15@student.sdu.dk)
 */
public class PQHeap implements PQ{

	// Queue used for the heap
	public static ArrayList<Element> Queue;

	//constructor
	public PQHeap(int MaxElements){
		this.Queue = new ArrayList<>(MaxElements);
	}

	/**
	 * Extracts the element with least priority from a min-heap structurized heap. This is done by swapping the root element
	 * with the last element in the heap and then removing the new last element. To maintain min-heap structure the new root element
	 * is heapified.
     */
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

	/**
	 * Inserts an element e into the min-heap structurized ArrayList queue. The element e is added as the last element in queue
	 * and therefore to maintain min-heap structure e is compared to its parent. If its smaller it swaps with its parent and is
	 * then compared to its new parent. This is done until the element e is either larger than its parent or is the root.
     */
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

	/**
	 * Heapifies an object of type Element at a given index i in a given arraylist A.
     */
	public static void Min_heapify(ArrayList<Element> A, int i){
		int l = Left(i);
		int r = Right(i);
		int smallest;
		if (l < A.size() && A.get(l).key <= A.get(i).key) {
			smallest = l;
		}
		else {
			smallest = i;
		}
		if (r < A.size() && A.get(r).key <= A.get(smallest).key) {
			smallest = r;
		}
		if (smallest != i) {
			Collections.swap(A, i, smallest);
			Min_heapify(A, smallest);
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
	 * Example by using the root: (2*0)+1 = 1, which is the left child
	 */
	private static int Left(int i){
		return (int)Math.ceil(2*i)+1;
	}
	/**
	 * Returns the right child in the 0-indexed queue
	 * Example by using the root: (2*0)+2 = 2, which is the right child
	 */
	private static int Right(int i){
		return (int)Math.ceil((2*i)+2);
	}
}