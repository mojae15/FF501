import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by:
 * Kasper Skov Johansen (kajoh14@student.sdu.dk)
 * Morten Kristian Jaeger (mojae15@student.sdu.dk)
 */
public class HeapSort {

    //instanced heap of temporary heap size 100
    private static PQHeap heap = new PQHeap(100);

    //Scanner
    static Scanner scan;

    /**
     * Takes an arralist of integers as input.
     * Sorts this input by inserting every element to the heap one by one and then extracting the root one by one.
     */
    public static void sort(ArrayList<Integer> list){
        for (int i = 0; i < list.size(); i++){
            heap.insert(new Element(list.get(i), null));
        }
        for (int i = 0; i < list.size(); i++){
            Element e = heap.extractMin();
            System.out.println(e.key);
        }
    }

    /**
     * Uses a scanner to get an input from the user. This input is inserted into an arraylist which is passed over to the function sort(...)
     */
    public static void main(String[] args){
        // ArrayList used to hold the numbers from the scanner
        ArrayList<Integer> numbers = new ArrayList<>();
        scan = new Scanner(System.in);
        // The scanner runs while there is int input
        while (scan.hasNextInt()){
            numbers.add(scan.nextInt());
        }
        sort(numbers);											
        
        
    }
}