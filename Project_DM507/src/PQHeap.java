import java.util.ArrayList;
import java.util.Collections;

public class PQHeap implements PQ{

	public ArrayList<Element> Queue; //this is the queue

	public PQHeap(int MaxElements){								//constructor
		synchronized (this){
			this.Queue = new ArrayList<>(MaxElements);
		}
	}

	@Override
	public Element extractMin() {
		// TODO Auto-generated method stub
		try{
			Queue.remove(0);
		}catch(ArrayIndexOutOfBoundsException k){System.out.println("No elements in queue");}
		return null;
	}

	@Override
	public void insert(Element e) {
		// TODO Auto-generated method stub
		final int temp = Queue.size();
		try{
			synchronized (this){
				Queue.add(temp, e);
			}
		}catch(ArrayIndexOutOfBoundsException k){
			System.out.println("The queue can not hold any more elements");
		}

	}



	public static void Min_heapify(ArrayList<Element> A, int i){ //changed <Integer> to <Element>
		int l = Left(i);
		int r = Rigth(i);
		int smallest;
		if (l < A.size() && A.get(l).key <= A.get(i).key) { //skift maaske til l < A.size()
			smallest = l;
		}
		else {
			smallest = i;
		}
		if (r < A.size() && A.get(r).key <= A.get(smallest).key) { //skift maaske til r < A.size()
			smallest = r;
		}
		if (smallest != i) {
			Collections.swap(A, i, smallest);
			Min_heapify(A, smallest);
		}
	}
	
	private static Element Heap_Extract_Min(ArrayList<Element> A) throws Exception{  //changed <Integer> to <Element>
		if (A.size() <= 1) {								//skift maaske til A.size() <= 1
			throw new Exception("Heap underflow");
		}
		Element min = A.get(0);
		Element max = A.get(A.size()-1);
		A.set(0, max);
		A.remove(A.size()-1);
		Min_heapify(A, 0);
		return min;
	}
	
	private static void Heap_Increase_Key(ArrayList<Element> A, int i, int key) throws Exception{
		if (key <= A.get(i).key) {
			throw new Exception("New key smaller than current key");
		}
		A.get(i).key = key;
		while (i >= 1 && A.get(Parent(i)).key >= A.get(i).key){				//skift maaske til i >= 1
			Collections.swap(A, i, Parent(i));
			i = Parent(i);
		}
	}
	
	private static void Min_Heap_Insert(ArrayList<Element> A, int key){
		int temp = A.size();					//lav maaske om til blot temp = A.size(), da arraylist er nul-indekseret
		try{
			A.get(temp).key= Integer.MIN_VALUE;
		}catch(ArrayIndexOutOfBoundsException k){
			System.out.println("The queue can not hold any more elements");
		}
		try {
			Heap_Increase_Key(A, temp, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static int Parent(int i){
		return i/2;
	}
	
	private static int Left(int i){
		return 2*i;
	}
	
	private static int Rigth(int i){
		return (2*i)+1;
	}
}
