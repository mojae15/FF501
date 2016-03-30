import java.util.ArrayList;

/**
 * Created by Kasper Skov Johansen (kajoh14@student.sdu.dk) and Morten Kristian Jæger (mojae15@student.sdu.dk)
 */
public class DictBinTree implements  Dict {

    Knud root;
    int TreeSize;
    ArrayList<Integer> Holder = new ArrayList<>();
    private int[] test;

    public DictBinTree(){
        root = null;
        TreeSize = 0;
        int [] tree = new int[TreeSize];
    }

    @Override
    public void insert(int k){
        Knud z = new Knud(null, null, k);
        Knud temp = null;
        if(root==null){
            root = new Knud(null, null, k);
            TreeSize++;
            return;
        }
        Knud x = root;
        while(x!=null){
            temp = x;
            if (z.key < x.key){
                x = x.left;
            }
            else{
                x = x.right;
            }
        }
        if (z.key < temp.key){
            temp.left = z;
            TreeSize++;
        }
        else {
            temp.right = z;
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

    @Override
    public boolean search(int k) {
        Knud x = root;
        while (x.key != null && k!=x.key) {
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
}
