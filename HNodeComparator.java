import java.util.Comparator;

public class HNodeComparator implements Comparator<HNode> {
	
	/**
	 * Compares the given nodes.
	 * 
	 * @param a the first node to compare
	 * @param b the second node to compare
	 * 
	 * @return negative value when a < b
	 * 			positive value when a > b
	 * 			0				when a == b
	 */
	public int compare(HNode a, HNode b) {
		int freqCompare = Integer.compare(a.getFrequency(), b.getFrequency());
        if (freqCompare != 0) {
            return freqCompare;
        }
        return a.toString().compareTo(b.toString());
	}
}