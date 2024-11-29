import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

class HuffmanTreeTest {
	@Test
    public void test_HNode() {
        HNode leaf1 = new HNode('a', 10);
        assertTrue(leaf1.isLeaf());
        assertTrue(leaf1.contains('a'));
        assertFalse(leaf1.contains('b'));
        assertEquals(leaf1.getSymbol(), 'a');
        assertEquals(leaf1.toString(), "a:10");
        
        HNode leaf2 = new HNode('b', 20);
        HNode parent = new HNode(leaf1, leaf2);
        assertFalse(parent.isLeaf());
        assertTrue(parent.contains('a'));
        assertTrue(parent.contains('b'));
        assertEquals(parent.getSymbol(), '\0');
        assertEquals(parent.toString(), "ab:30");
    }
    
	@Test
	public void test_HuffmanTree() throws IOException, ClassNotFoundException {
	    TreeMap<Character, Integer> frequencies = new TreeMap<>();
	    frequencies.put('a', 45);
	    frequencies.put('b', 13);
	    frequencies.put('c', 12);
	    frequencies.put('d', 16);
	    frequencies.put('e', 9);
	    
	    HuffmanTree tree = new HuffmanTree(frequencies);
	    
	    for (char c : frequencies.keySet()) {
	        String code = tree.encode(c);
	        String loopCode = tree.encodeLoop(c);
	        assertEquals(code, loopCode);
	        assertEquals(c, tree.decode(code));
	    }
	    
	    assertEquals('\0', tree.decode("111111"));

	    String compressedFilePath = "test_output.hz";
	    BitOutputStream bos = null;

	    try {
	        bos = new BitOutputStream(new FileOutputStream(compressedFilePath));
	        bos.writeObject(frequencies);
	        tree.writeCode('a', bos);
	        tree.writeCode('b', bos);
	    } finally {
	        if (bos != null) {
	            bos.close(); 
	        }
	    }

	    BitInputStream bis = null;
	    char readCharA;
	    char readCharB;

	    try {
	        bis = new BitInputStream(new FileInputStream(compressedFilePath));
	        TreeMap<Character, Integer> readFrequencies = (TreeMap<Character, Integer>) bis.readObject();
	        assertEquals(frequencies, readFrequencies);
	        
	        readCharA = tree.readCode(bis);
	        readCharB = tree.readCode(bis);
	    } finally {
	        if (bis != null) {
	            bis.close();
	        }
	    }

	    assertEquals('a', readCharA);
	    assertEquals('b', readCharB);
	}
}
