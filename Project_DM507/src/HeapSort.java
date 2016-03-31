import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by:
 * Kasper Skov Johansen (kajoh14@student.sdu.dk)
 * Morten Kristian Jæger (mojae15@student.sdu.dk)
 */
public class HeapSort {
	
	//Temporary heap size
    private static PQHeap heap = new PQHeap(100);

    //Scanner
    static Scanner scan;                                        

    public static void sort(ArrayList<Integer> list){
        for (int i = 0; i < list.size(); i++){										
            heap.insert(new Element(list.get(i), null));		
        }                                                      
        for (int i = 0; i < list.size(); i++){
            Element e = heap.extractMin();
            System.out.println(e.key);
        }
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
