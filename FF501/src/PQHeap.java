import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by:
 * Kasper Skov Johansen (kajoh14@student.sdu.dk)
 * Morten Kristian Jaeger (mojae15@student.sdu.dk)
 */
public class PQHeap implements PQ{

	// Queue used for the heap
	public static ArrayList<Vertex> Queue;

	//constructor
	public PQHeap(int MaxVertexs){
		this.Queue = new ArrayList<>(MaxVertexs);
	}
	
	//Current size
	public static int cSize = 0;

	/**
	 * Extracts the Vertex with least priority from a min-heap structurized heap. This is done by swapping the root Vertex
	 * with the last Vertex in the heap and then removing the new last Vertex. To maintain min-heap structure the new root Vertex
	 * is heapified.
     */
	@Override
	public Vertex extractMin() {
		try{
			Vertex min = Queue.get(0);
			Vertex max = Queue.get(Queue.size()-1);
			Queue.set(0, max);
			Queue.remove(Queue.size()-1);
			Min_heapify(Queue, 0);
			cSize--;
			return min;
		}catch(ArrayIndexOutOfBoundsException k){
			k.printStackTrace();
		}
		return null;
	}

	/**
	 * Inserts an Vertex e into the min-heap structurized ArrayList queue. The Vertex e is added as the last Vertex in queue
	 * and therefore to maintain min-heap structure e is compared to its parent. If its smaller it swaps with its parent and is
	 * then compared to its new parent. This is done until the Vertex e is either larger than its parent or is the root.
     */
	@Override
	public void insert(Vertex e) {
		int i = Queue.size();
		try {
			Queue.add(e);
		} catch (ArrayIndexOutOfBoundsException k) {
			System.out.println("Array is out of bounds");
		}
		while (i > 0 && Queue.get(Parent(i)).cost >= Queue.get(i).cost) {
			Collections.swap(Queue, i, Parent(i));
			i = Parent(i);
		}
		cSize++;
	}

	/**
	 * Heapifies an object of type Vertex at a given index i in a given arraylist A.
     */
	public static void Min_heapify(ArrayList<Vertex> A, int i){
		int l = Left(i);
		int r = Right(i);
		int smallest;
		if (l < A.size() && A.get(l).cost <= A.get(i).cost) {
			smallest = l;
		}
		else {
			smallest = i;
		}
		if (r < A.size() && A.get(r).cost <= A.get(smallest).cost) {
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
	
	public int getSize(){
		return cSize;
	}
}