import java.util.ArrayList;

/**
 * Created by Kasper Skov Johansen (kajoh14@student.sdu.dk) and Morten Kristian Jæger (mojae15@student.sdu.dk)
 */
public class DictBinTree implements  Dict {

    Knud root;
    int TreeSize;
    ArrayList<Integer> Holder = new ArrayList<>();
    private int[] test;

    /**
     * Constructor
     */
    public DictBinTree(){
        root = null;
        TreeSize = 0;
        int [] tree = new int[TreeSize];
    }

    /**
     * Creates a new knud with the key k.
     * If the root does not exist, the knud we just created will be inserted as the root.
     * Else, the knud is inserted into the appropriate place in the tree.
     */
    @Override
    public void insert(int k){
        Knud z = new Knud(null, null, k);
        Knud y = null;
        if(root==null){
            root = z;
            TreeSize++;
            return;
        }
        Knud x = root;
        while(x!=null){
            y = x;
            if (z.key < x.key){
                x = x.left;
            }
            else{
                x = x.right;
            }
        }
        if (z.key < y.key){
            y.left = z;
            TreeSize++;
        }
        else {
            y.right = z;
            TreeSize++;
        }
        
    }

    public void treeWalk(Knud x){
        if(x!=null){
            treeWalk(x.left);
            Holder.add(x.key);
            treeWalk(x.right);
        }    
    }

    @Override
    public int[] orderedTraversal() {
    	test = new int[TreeSize];
        treeWalk(root);
        for (int i = 0; i < Holder.size(); i++) {
			test[i] = Holder.get(i);
		}
        return test;
    }
    
    /**
     * Searches through the tree, iteratively
     * @param k
     * @return
     */
    private boolean Search(int k){
    	Knud x = root;
        while (x.key != null && k != x.key) {
            if(k < x.key){
                x = x.left;
            }else{
                x = x.right;
            }
        }
        if(x.key == k) {
            return true;
        }
        return false;
    }
    
    @Override
    public boolean search(int k) {
    	return Search(k);
    }
}
