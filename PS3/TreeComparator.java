import java.util.Comparator;
import java.util.PriorityQueue;
//This methods overides the Comparator methos
public class TreeComparator implements Comparator<BinaryTree<info>>{
	@Override
	public int compare(BinaryTree<info> arg0, BinaryTree<info> arg1) {
		// This methods returns -1,0,1 and these numbers indicate teu false and equal
		if(arg0.getData().getFrequency()<arg1.getData().getFrequency()) {
			return -1;
		}
		else if(arg0.getData().getFrequency()>arg1.getData().getFrequency()) {
			return 1;
		}
		return 0;
    }

	
}
