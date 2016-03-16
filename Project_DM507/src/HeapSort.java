import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


/**
 * Created by Kasper Skov Johansen (kajo14@studen.sdu.dk) and Morten Kristian Jæger (mojae15@student.sdu.dk)
 */
public class HeapSort {
    private static PQHeap heap = new PQHeap(100);                //max heap-size is 100; can be changed, though

    static Scanner scan;                                        //scanner

    public static void sort(ArrayList<Integer> list){ 			// Given a list of integers, it sorts them in min-heap order,
        														// and prints it out.
    	
        for (int i = 0; i < list.size(); i++){					// Given the integer from the ArrayList, creates an element,					
            heap.insert(new Element(list.get(i), null));		// with the key i, and the data null, as there is no data,
        }														// and inserts it in the queue in PQHeap
        
        heap.build_Min_Heap(heap.Queue);						
        
        for (int i = heap.Queue.size()-1; i>0; i--){
            Collections.swap(heap.Queue, 0, i);
            heap.extractMin();
            heap.Min_heapify(heap.Queue, 0);
        }
        heap.extractMin();

    }
    
    public static void main(String[] args){
    	ArrayList<Integer> numbers = new ArrayList<>();			// ArrayList used to hold the numbers from the scanner
        scan = new Scanner(System.in);      
        while (scan.hasNextInt()){								// The scanner runs while there is int input
        	numbers.add(scan.nextInt());						// Add the numbers to the list
        }
        Runtime.getRuntime().addShutdownHook(new Thread( () -> {	// Add a ShutdownHook that waits for the program to "finish" and then does something, 
        	sort(numbers);											// in this case it sorts and prints the list of numbers.
        }));
        System.exit(0);
    }
}
