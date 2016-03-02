import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
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

    public static void sort(){                                  //still needs to produce an output
        while(scan.hasNext()){
            lock.lock();
            int current = scan.nextInt();
            heap.insert(new Element(current, null));
            lock.unlock();
        }
        for (int i = heap.Queue.size()-1; i>=0; i--){
            heap.Min_heapify(heap.Queue, i);
        }
        for (Element e : heap.Queue){
            heap.extractMin();
        }
    }



    public static void main(String[] args){
        scan = new Scanner(System.in);  //a listener that listens to ctrl+D must be implemented here
        sort();

    }
}
