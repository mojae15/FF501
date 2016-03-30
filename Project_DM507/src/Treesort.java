import java.util.Scanner;

/**
 * Created by Kasper Skov Johansen (kajoh14@student.sdu.dk) and Morten Kristian Jæger (mojae15@student.sdu.dk)
 */
public class Treesort {

	static Scanner scan;
	
    public static void main(String[] args){
        scan = new Scanner(System.in);
        DictBinTree dictionary = new DictBinTree();

        while(scan.hasNextInt()){
            dictionary.insert(scan.nextInt());
        }

        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            int[] dic = dictionary.orderedTraversal();
            for (int i: dic){
                System.out.println(i);
            }
        }));
        System.exit(0);

    }
}
