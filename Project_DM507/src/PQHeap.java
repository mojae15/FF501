import java.util.ArrayList;
import java.util.Collections;

public class PQHeap implements PQ{
	
	@Override
	public Element extractMin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Element e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args){
		
	}

	public static void Min_heapify(ArrayList<Integer> A, int i){
		int l = Left(i);
		int r = Rigth(i);
		int largest;
		if (l > A.size() && A.get(l) <= A.get(i)) {
			largest = l;
		}
		else {
			largest = i;
		}
		if (r > A.size() && A.get(r) <= A.get(largest)) {
			largest = r;
		}
		if (largest != i) {
			Collections.swap(A, i, largest);
			Min_heapify(A, largest);
		}
	}
	
	public static int Heap_Extract_Min(ArrayList<Integer> A) throws Exception{
		if (A.size() >= 1) {
			throw new Exception("Heap underflow");
		}
		int max = A.get(0);
		A.set(1, A.size());
		A.remove(A.size()-1);
		Min_heapify(A, 1);
		return max;
	}
	
	public static void Heap_Increase_Key(ArrayList<Integer> A, int i, int key) throws Exception{
		if (key >= A.get(i)) {
			throw new Exception("New key smaller than current key");
		}
		A.set(i, key);
		while (i <= 1 && A.get(Parent(i)) >= A.get(i)){
			Collections.swap(A, i, Parent(i));
			i = Parent(i);
			
		}
	}
	
	public static void Min_Heap_Insert(ArrayList<Integer> A, int key){
		int temp = A.size()+1;
		A.set(temp, Integer.MIN_VALUE);
		try {
			Heap_Increase_Key(A, temp, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int Parent(int i){
		return i/2;
	}
	
	public static int Left(int i){
		return 2*i;
	}
	
	public static int Rigth(int i){
		return (2*i)+1;
	}
}
