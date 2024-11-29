import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;

public class HuffmanTree {
	private HNode root;
	
	/**
	 * Builds a Huffman Tree from the given characters and their corresponding frequencies.
	 * @param frequencies
	 */
	public HuffmanTree(TreeMap<Character, Integer> frequencies) {
		PriorityQueue<HNode> pq = new PriorityQueue<>(new HNodeComparator());
		
		for(Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
			HNode node = new HNode(entry.getKey(), entry.getValue());
			pq.offer(node);
		}
		
		while(pq.size() > 1) { //stop when queue only has root
			HNode left = pq.poll(); //pop
			HNode right = pq.poll(); //pop
			
			HNode merge = new HNode(left, right);
			
			pq.offer(merge); //push(insert)
		}
		
		root = pq.poll();
	}
	
	/**
	 * Creates the binary encoding of the given symbol as a string of '0' and '1' characters
	 * @param symbol given symbol.
	 * @return binary encoding of the given symbol.
	 */
	public String encodeLoop(char symbol) {
	    if (root == null) {
	    	return "";
	    }
	    
	    Queue<HNode> nodeQueue = new LinkedList<>();
	    Queue<String> pathQueue = new LinkedList<>();
	    
	    nodeQueue.offer(root);
	    pathQueue.offer("");
	    
	    while (!nodeQueue.isEmpty()) {
	        HNode current = nodeQueue.poll();
	        String currentPath = pathQueue.poll();
	        
	        if (current.isLeaf()) {
	            if (current.contains(symbol)) {
	                return currentPath;
	            }
	        } else {
	            if (current.getLeft() != null && current.getLeft().contains(symbol)) {
	                nodeQueue.offer(current.getLeft());
	                pathQueue.offer(currentPath + "0");
	            }
	            
	            if (current.getRight() != null && current.getRight().contains(symbol)) {
	                nodeQueue.offer(current.getRight());
	                pathQueue.offer(currentPath + "1");
	            }
	        }
	    }
	    return "";
	}

	
	/**
	 * Creates the binary encoding of the given symbol as a string of '0' and '1' characters
	 * @param symbol given symbol.
	 * @return binary encoding of the given symbol.
	 */
	public String encode(char symbol) {
		return encode(symbol, root);
	}
	
	/**
	 * Creates recursively binary encoding of the given symbol as a string of '0' and '1' characters
	 * @param symbol given symbol.
	 * @param curr given node.
	 * @return binary encoding of the given symbol.
	 */
	public String encode(char symbol, HNode curr) {
		//recursive
		
		if(curr.isLeaf()) {
			if(curr.contains(symbol)) {
				return "";
			} else {
				return null;
			}
		}
		
		String left = encode(symbol, curr.getLeft());
		if(left != null) {
			return "0" + left;
		}
		
		String right = encode(symbol, curr.getRight());
		if(right != null) {
			return "1" + right;
		}
		
		return null;
	}
	
	/**
	 * Returns the symbol that corresponds to the given code.
	 * @param code given String of binary code.
	 * @return symbol that corresponds to each part of the given code.
	 */
	public char decode(String code) {
		HNode curr = root;
		
		for(char bit : code.toCharArray()) {
			if(curr == null) {
				return '\0';
			}
			
			if(bit == '0') {
				curr = curr.left;
			} else if(bit == '1') {
				curr = curr.right;
			} else {
				return '\0';
			}
		}
		
		if(curr.isLeaf()) {
			return curr.getSymbol();
		} else {
			return '\0';
		}
	}
	
	/**
	 * Writes the individual bits of the binary encoding of the given symbol to the given stream.
	 * @param symbol given symbol.
	 * @param stream given stream.
	 * @throws IOException
	 */
	public void writeCode(char symbol, BitOutputStream stream) throws IOException {
	    if (root == null) {
	    	return;
	    }
	    
	    Queue<HNode> nodeQueue = new LinkedList<>();
	    Queue<String> pathQueue = new LinkedList<>();
	    
	    nodeQueue.offer(root);
	    pathQueue.offer("");
	    
	    while (!nodeQueue.isEmpty()) {
	        HNode current = nodeQueue.poll();
	        String currentPath = pathQueue.poll();
	        
	        if (current.isLeaf()) {
	            if (current.contains(symbol)) {
	                for (char bit : currentPath.toCharArray()) {
	                    if (bit == '0') {
	                        stream.writeBit(0);
	                    } else {
	                        stream.writeBit(1);
	                    }
	                }
	                return;
	            }
	        } else {
	            if (current.getLeft() != null && current.getLeft().contains(symbol)) {
	                nodeQueue.offer(current.getLeft());
	                pathQueue.offer(currentPath + "0");
	            }
	            if (current.getRight() != null && current.getRight().contains(symbol)) {
	                nodeQueue.offer(current.getRight());
	                pathQueue.offer(currentPath + "1");
	            }
	        }
	    }
	}
	
	/**
	 * Reads from the given stream the individual bits of the binary encoding of the next symbol.
	 * @param stream given stream.
	 * @return the corresponding character.
	 * @throws IOException
	 */
	public char readCode(BitInputStream stream) throws IOException {
		HNode curr = root;
		
		while(!curr.isLeaf()) {
			if(!stream.hasNext()) {
				return '\0';
			}
			int bit = stream.readBit();
			if(bit == 0) {
				curr = curr.left;
			} else {
				curr = curr.right;
			}
			
			if(curr == null) {
				return '\0';
			}
		}
		return curr.getSymbol();
	}
}
