import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kasper Skov Johansen (kajo14@studen.sdu.dk) and Morten Kristian Jæger (mojae15@student.sdu.dk)
 */

public class PQHeap implements PQ{

	public ArrayList<Element> Queue; 							// Queue used for the heap	

	public PQHeap(int MaxElements){								//constructor
		this.Queue = new ArrayList<>(MaxElements);
	}

	@Override
	public Element extractMin() {								// Prints and removes the smallest number in the heap
		try{
			System.out.println(Queue.get(Queue.size()-1).key);
			Queue.remove(Queue.size()-1);
		}catch(ArrayIndexOutOfBoundsException k){System.out.println("No elements in queue");}
		return null;
	}

	@Override
	public void insert(Element e) {								// Insert an element in the heap at the end of the queue
		final int temp = Queue.size();							// Index of the of the queue
		try{
			Queue.add(temp, e);	
		}catch(ArrayIndexOutOfBoundsException k){
			System.out.println("The queue can not hold any more elements");
		}

	}

	public static void build_Min_Heap(ArrayList<Element> A){	// Create the min heap, by calling Min_heapify multiple times
		for(int i = (int)Math.floor(A.size()-1); i>=0; i--){
			Min_heapify(A, i);
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
	
	private static Element Heap_Extract_Min(ArrayList<Element> A) throws Exception{  //extracts minimum element of a sorted heap
		if (A.size() <= 1) {									// If the Heap has a size of 1 or less, there is no need to 
			throw new Exception("Heap underflow");				// do this, and an error occurs.
		}
		Element min = A.get(0);	
		Element max = A.get(A.size()-1);
		A.set(0, max);
		A.remove(A.size()-1);
		Min_heapify(A, 0);
		return min;
	}
	

	
	private static void Min_Heap_Insert(ArrayList<Element> A, int key, Element e)throws Exception{ //Inserts an element in a heap
		try{
			A.add(A.size(), e);
		}catch(ArrayIndexOutOfBoundsException k){System.out.println("Array is out of bounds");
		int i = A.size();
		if (key >= A.get(i).key) {
			throw new Exception("New key is larger than current key");
		}
		while (i >= 1 && A.get(Parent(i)).key <= A.get(i).key){				// We swap the elements if the parents are larger
			Collections.swap(A, i, Parent(i));								// than their children
			i = Parent(i);													// And set a new i to compare the elements to.
		}
		}
	}
	
	private static int Parent(int i){				// Returns the index of the parent of the Element
		return (int)Math.ceil(i/2)-1;
	}
	
	private static int Left(int i){					// Returns the left index of the Element
		return (int)Math.ceil(2*i)+1;
	}
	
	private static int Right(int i){				// Returns the right index of the Element
		return (int)Math.ceil((2*i)+2);
	}
}
