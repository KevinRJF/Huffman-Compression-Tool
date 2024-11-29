import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;

public class HuffmanZip {
	
	/**
	 * Encodes the text file with the given name using the Huffman Encoding Algorithm.
	 * @param filename given filename.
	 * @throws IOException
	 */
	public static void encode(String filename) throws IOException {
	    FileReader reader = null;
	    BitOutputStream out = null;

	    try {
	        TreeMap<Character, Integer> frequencies = new TreeMap<>();
	        reader = new FileReader(filename);
	        int c = reader.read();
	        while (c != -1) {
	            char ch = (char) c;
	            frequencies.put(ch, frequencies.getOrDefault(ch, 0) + 1);
	        }
	        
	        reader.close();
	        
	        HuffmanTree tree = new HuffmanTree(frequencies);
	        
	        out = new BitOutputStream(filename + ".hz");
	        reader = new FileReader(filename); 

	        out.writeObject(frequencies);

	        c = reader.read();
	        while (c != -1) {
	            tree.writeCode((char) c, out);
	        }
	    } finally {
	        if (reader != null) {
	            reader.close();
	        }
	        if (out != null) {
	            out.close();
	        }
	    }
	}

	
	/**
	 * Decodes the text file with the given name using the Huffman Encoding Algorithm.
	 * @param filename given filename.
	 * @throws IOException
	 */
	public static void decode(String filename) throws IOException {
	    BitInputStream in = null;
	    FileWriter writer = null;

	    try {
	        in = new BitInputStream(filename);
	        writer = new FileWriter(filename + ".hz");

	        TreeMap<Character, Integer> frequencies = (TreeMap<Character, Integer>) in.readObject();
	        HuffmanTree tree = new HuffmanTree(frequencies);

	        while (in.hasNext()) {
	            char ch = tree.readCode(in);
	            if (ch != '\0') {
	                writer.write(ch);
	            }
	        }
	    } catch (ClassNotFoundException e) {
	        throw new IOException();
	    } finally {
	        if (in != null) {
	            in.close();
	        }
	        if (writer != null) {
	            writer.close();
	        }
	    }
	}

	
	/**
	 * Standard main method
	 * @param args standard array of strings from main method.
	 */
	public static void main(String[] args) {
		
		String encode = "-encode";
		String decode = "-decode";

        if (args.length != 2) {
            System.out.println("incorrect usage");
            return;
        }

        String command = args[0];
        String filename = args[1];

        try {
            if (encode.equals(command)) {
                encode(filename);
            } else if (decode.equals(command)) {
                decode(filename);
            } else {
                System.out.println("invalid command");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
