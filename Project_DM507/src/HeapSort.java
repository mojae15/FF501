import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by Kasper on 01-03-2016.
 */
public class HeapSort {
    private static PQHeap heap = new PQHeap(100);                //max heap-size is 100; can be changed, though

    static Scanner scan;                                        //scanner
    private static Lock lock = new ReentrantLock();             //lock

    public static void sort(ArrayList<Integer> list){                                  //still needs to produce an output
        /* while(scan.hasNext()){                               //sorts the list in decreasing order
            lock.lock();                                        //can be used for scan input
            int current = scan.nextInt();
            heap.insert(new Element(current, null));
            lock.unlock();
        } */
        for (int i = 0; i < list.size(); i++){
            heap.insert(new Element(list.get(i), null));
        }
        
        heap.build_Min_Heap(heap.Queue);
        
        for (int i = heap.Queue.size()-1; i>0; i--){
            Collections.swap(heap.Queue, 0, i);
            heap.extractMin();
            heap.Min_heapify(heap.Queue, 0);
        }
        heap.extractMin();
//        System.out.println(count);

    }
    
    public static void main(String[] args){
    	ArrayList<Integer> numbers = new ArrayList<>();
        scan = new Scanner(System.in);      
        while (scan.hasNextInt()){			// The scanner runs while there is int input
        	numbers.add(scan.nextInt());	// Add the numbers to the list
        }
        Runtime.getRuntime().addShutdownHook(new Thread( () -> {	// Add a ShutdownHook that waits for the program to "finish" and then does something, 
        	sort(numbers);											// in this case it sorts and prints the list of numbers.
        }));
    }
}
