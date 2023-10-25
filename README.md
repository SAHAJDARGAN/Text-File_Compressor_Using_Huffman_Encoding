# Text-File_Compressor_Using_Huffman_Encoding
This Java program showcases Huffman encoding, a text compression algorithm that assigns shorter binary codes to frequently occurring characters in a text file. It enables efficient compression and decompression of text files. 

**Prerequisites**
Java Development Kit (JDK)
A text editor or Integrated Development Environment (IDE) for Java development

**How to Use**

**1. Compile the Code**
Compile the HuffmanCoding class using the Java compiler. Open your terminal or command prompt and navigate to the directory containing your Java file. Then run the following command:
javac HuffmanCoding.java
This will create compiled Java class files.

**2. Compress a Text File**
To compress a text file, call the compress method in the HuffmanCoding class. Use the following command:
java HuffmanCoding compress <inputFileName> <outputFileName>

**<inputFileName>** should be the path to the text file you want to compress.
**<outputFileName>** is the name of the compressed file.

For example:
java HuffmanCoding compress input.txt compressedFile.huf
This command will compress the input.txt file and save the compressed data to compressedFile.huf.

**3. Decompress a Compressed File**
To decompress a compressed file, use the decompress method. Run the following command:

java HuffmanCoding decompress <inputFileName> <outputFileName>
**<inputFileName>** should be the path to the compressed file.
**<outputFileName>** is the name of the output text file.
For example:
java HuffmanCoding decompress compressedFile.huf output.txt
This command will decompress the compressedFile.huf and save the decompressed data to output.txt.

**File Formats**
The program uses custom binary file formats for compression and decompression. When you compress a file, the program will create a compressed file with a .huf extension. This compressed file contains the frequency map and the encoded text.

The frequency map is used during decompression to rebuild the Huffman tree, and the encoded text is decoded to recreate the original text.

**Sample Usage**
The main method in the HuffmanCoding class demonstrates the usage of the program. By default, it compresses the inputText.txt file and then decompresses it. You can modify this code to compress and decompress your own files.

public static void main(String[] args) {
    try {
        // Compress the input file
        HuffmanCoding.compress("inputText.txt", "compressedFile.huf");

        // Decompress the compressed file
        HuffmanCoding.decompress("compressedFile.huf", "outputFile.txt");
    } catch (IOException e) {
        e.printStackTrace();
    }
}
**Notes**
The program may not work correctly for non-text files, as it is designed for text compression.
Make sure to provide the correct file paths for input and output files.

**Acknowledgments**
This program is based on the Huffman encoding algorithm and was created for educational purposes. It can be used as a reference for understanding the basic principles of Huffman encoding and how to implement it in Java.
