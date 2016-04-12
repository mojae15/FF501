import java.util.Scanner;

/**
 * Created by Kasper Skov Johansen (kajoh14@student.sdu.dk) and Morten Kristian Jæger (mojae15@student.sdu.dk)
 */
public class Treesort {
	
	//Scanner used to read the input
	static Scanner scan;
	
	/**
	 * Method that traverses the dictionary and print out the traversed nubmers
	 * @param dictionary
	 */
	public static void sort(DictBinTree dictionary){
		int[] dic = dictionary.orderedTraversal();
		for (int i: dic){
			System.out.println(i);
		}
	}
	
    public static void main(String[] args){
        scan = new Scanner(System.in);
        DictBinTree dictionary = new DictBinTree();

        while(scan.hasNextInt()){
            dictionary.insert(scan.nextInt());
        }
        sort(dictionary);

    }
}
