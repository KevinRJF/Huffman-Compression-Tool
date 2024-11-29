
public class HNode {
	HNode left;
	HNode right;
	String symb;
	int freq;
	
	/**
	 * Creates a leaf node that has a given character and its frequency. 
	 * @param c given character.
	 * @param f given frequency.
	 */
	public HNode(char c, int f) {
		symb = String.valueOf(c);
		freq = f;
		left = null;
		right = null;
	}
	
	/**
	 * Creates node with given left and right children.
	 * @param left given left children.
	 * @param right given right children.
	 */
	public HNode(HNode left, HNode right) {
		this.left = left;
		this.right = right;
		freq = left.freq + right.freq;
		symb = left.symb + right.symb;
	}
	
	/**
	 * Determines if node is a leaf.
	 * @return true if the node is a leaf.
	 */
	public boolean isLeaf() {
		return this.left == null & this.right == null;
	}
	
	/**
	 * Determines if the node contains the given character.
	 * @param ch given character.
	 * @return true if node contains the given character.
	 */
	public boolean contains(char ch) {
		if(symb.indexOf(ch) != -1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the symbol stored in the node
	 * @return symbol stored in the node.
	 */
	public char getSymbol() {
		if(isLeaf()) {
			return symb.charAt(0);
		} else {
			return '\0';
		}
	}
	
	/**
	 * Creates a string representation of the node in the format symbols:frequency.
	 */
	public String toString() {
		String str = "";
		
		str += symb + ":" + freq;
		
		return str;
	}
	
	//extra getters:
	
	public HNode getLeft() {
		return left;
	}
	
	public HNode getRight() {
		return right;
	}
	
	public int getFrequency() {
		return freq;
	}
	
	
}
