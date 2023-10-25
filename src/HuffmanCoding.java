
import java.util.*;
import java.io.*;

class HuffmanNode implements Comparable<HuffmanNode> {
    char data;
    int frequency;
    HuffmanNode left;
    HuffmanNode right;

    public HuffmanNode(char data, int frequency) {
        this.data = data;
        this.frequency = frequency;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    @Override
    public int compareTo(HuffmanNode other) {
        return this.frequency - other.frequency;
    }
}

public class HuffmanCoding {
    private static Map<Character, String> huffmanCodes = new HashMap<>();

    public static void compress(String inputFileName, String outputFileName) throws IOException {
        String text = readTextFile(inputFileName);
        Map<Character, Integer> frequencyMap = buildFrequencyMap(text);
        HuffmanNode root = buildHuffmanTree(frequencyMap);
        buildHuffmanCodes(root, "");
        String encodedText = encodeText(text);
        writeEncodedFile(outputFileName, frequencyMap, encodedText);
    }

    public static void decompress(String inputFileName, String outputFileName) throws IOException {
        Map<Character, Integer> frequencyMap = readFrequencyMap(inputFileName);
        HuffmanNode root = buildHuffmanTree(frequencyMap);
        String encodedText = readEncodedText(inputFileName);
        String decodedText = decodeText(encodedText, root);
        writeTextFile(outputFileName, decodedText);
    }

    private static String readTextFile(String fileName) throws IOException {
        // Read the contents of the input file
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
        }
        return text.toString();
    }

    private static Map<Character, Integer> buildFrequencyMap(String text) {
        // Build a frequency map for characters in the text
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        return frequencyMap;
    }

    private static HuffmanNode buildHuffmanTree(Map<Character, Integer> frequencyMap) {
        // Build the Huffman tree using a priority queue
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            priorityQueue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();
            HuffmanNode combined = new HuffmanNode('\0', left.frequency + right.frequency);
            combined.left = left;
            combined.right = right;
            priorityQueue.add(combined);
        }

        return priorityQueue.poll();
    }

    private static void buildHuffmanCodes(HuffmanNode node, String code) {
        // Build Huffman codes for each character
        if (node == null) return;
        if (node.isLeaf()) {
            huffmanCodes.put(node.data, code);
        }
        buildHuffmanCodes(node.left, code + "0");
        buildHuffmanCodes(node.right, code + "1");
    }

    private static String encodeText(String text) {
        // Encode the text using Huffman codes
        StringBuilder encodedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            encodedText.append(huffmanCodes.get(c));
        }
        return encodedText.toString();
    }

    private static void writeEncodedFile(String fileName, Map<Character, Integer> frequencyMap, String encodedText) throws IOException {
        // Write the encoded file
        try (DataOutputStream output = new DataOutputStream(new FileOutputStream(fileName))) {
            // Write the frequency map to the file
            output.writeInt(frequencyMap.size());
            for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
                output.writeChar(entry.getKey());
                output.writeInt(entry.getValue());
            }
            // Write the encoded text to the file
            int padding = 8 - (encodedText.length() % 8);
            output.writeByte(padding); // Store the number of padding bits
            encodedText = appendPadding(encodedText, padding);
            for (int i = 0; i < encodedText.length(); i += 8) {
                String byteString = encodedText.substring(i, i + 8);
                int byteValue = Integer.parseInt(byteString, 2);
                output.writeByte(byteValue);
            }
        }
    }

    private static String appendPadding(String binaryString, int padding) {
        // Append padding bits to the binary string
        StringBuilder paddedString = new StringBuilder(binaryString);
        for (int i = 0; i < padding; i++) {
            paddedString.append('0');
        }
        return paddedString.toString();
    }

    private static Map<Character, Integer> readFrequencyMap(String fileName) throws IOException {
        // Read the frequency map from the encoded file
        Map<Character, Integer> frequencyMap = new HashMap<>();
        try (DataInputStream input = new DataInputStream(new FileInputStream(fileName))) {
            int numEntries = input.readInt();
            for (int i = 0; i < numEntries; i++) {
                char character = input.readChar();
                int frequency = input.readInt();
                frequencyMap.put(character, frequency);
            }
        }
        return frequencyMap;
    }

    private static String readEncodedText(String fileName) throws IOException {
        // Read the encoded text from the encoded file
        StringBuilder encodedText = new StringBuilder();
        try (DataInputStream input = new DataInputStream(new FileInputStream(fileName))) {
            int padding = input.readByte(); // Read the padding bits
            while (true) {
                try {
                    int byteValue = input.readByte() & 0xFF;
                    String byteString = Integer.toBinaryString(byteValue);
                    if (encodedText.length() == 0) {
                        byteString = byteString.substring(padding);
                    }
                    encodedText.append(byteString);
                } catch (EOFException e) {
                    break;
                }
            }
        }
        return encodedText.toString();
    }


    private static String decodeText(String encodedText, HuffmanNode root) {
        // Decode the encoded text using the Huffman tree
        StringBuilder decodedText = new StringBuilder();
        HuffmanNode current = root;
        for (char bit : encodedText.toCharArray()) {
            if (bit == '0') {
                current = current.left;
            } else {
                current = current.right;
            }
            if (current.isLeaf()) {
                decodedText.append(current.data);
                current = root;
            }
        }
        return decodedText.toString();
    }

    private static void writeTextFile(String fileName, String text) throws IOException {
        // Write the decoded text to the output file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(text);
        }
    }

    public static void main(String[] args) {
        try {
            // Compress the input file
            HuffmanCoding.compress("C:\\Users\\sahaj dargan\\Desktop\\onlyCVspecificProjects\\textFileCompressorUsingHoffmanEncoding\\inputText.txt", "compressedFile.huf");

            // Decompress the compressed file
            HuffmanCoding.decompress("compressedFile.huf", "outputFile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
