import TreePackage.RedBlackTree;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;


class ParserTest {
    static Parser testParser;

    @BeforeAll
    static void setUp() throws FileNotFoundException {
        testParser = new Parser(new File("src/test/java/TestClass.java"));
    }


    @Test
    void getReservedWordsTree() throws FileNotFoundException {
        RedBlackTree<String> reserveTestTree = testParser.getReservedWordsTree();
        assertEquals(54, reserveTestTree.getNumberOfNodes());
        Scanner doc = new Scanner(new File("src/main/java/reservedWords.txt"));
        while (doc.hasNext()) {
            assertTrue(reserveTestTree.contains(doc.next()));
        }
    }

    @Test
    void getIdentifiersTree() {
        RedBlackTree<String> identifiersTestTree = testParser.getIdentifiersTree();
        assertEquals(22, identifiersTestTree.getNumberOfNodes());
        assertTrue(identifiersTestTree.contains("String"));
        assertTrue(identifiersTestTree.contains("str"));
        assertTrue(identifiersTestTree.contains("num"));
        assertTrue(identifiersTestTree.contains("testByte"));
        assertTrue(identifiersTestTree.contains("testChar"));
        assertTrue(identifiersTestTree.contains("Suit"));
        assertTrue(identifiersTestTree.contains("suit"));
        assertTrue(identifiersTestTree.contains("CONSTANT"));
        assertTrue(identifiersTestTree.contains("3"));
        assertTrue(identifiersTestTree.contains("0"));
        assertTrue(identifiersTestTree.contains("TestClass"));
        assertTrue(identifiersTestTree.contains("SPADES"));
        assertTrue(identifiersTestTree.contains("HEARTS"));
        assertTrue(identifiersTestTree.contains("CLUBS"));
        assertTrue(identifiersTestTree.contains("DIAMONDS"));
        assertTrue(identifiersTestTree.contains("setStr"));
        assertTrue(identifiersTestTree.contains("newStr"));
        assertTrue(identifiersTestTree.contains("need"));
        assertTrue(identifiersTestTree.contains("to"));
        assertTrue(identifiersTestTree.contains("downcast"));
        assertTrue(identifiersTestTree.contains("the"));
        assertTrue(identifiersTestTree.contains("integer"));
    }

    @Test
    void getReservedFoundTree() {
        RedBlackTree<String> reserveFoundTestTree = testParser.getReservedFoundTree();
        assertEquals(13, reserveFoundTestTree.getNumberOfNodes());
        assertTrue(reserveFoundTestTree.contains("byte"));
        assertTrue(reserveFoundTestTree.contains("char"));
        assertTrue(reserveFoundTestTree.contains("class"));
        assertTrue(reserveFoundTestTree.contains("double"));
        assertTrue(reserveFoundTestTree.contains("enum"));
        assertTrue(reserveFoundTestTree.contains("final"));
        assertTrue(reserveFoundTestTree.contains("int"));
        assertTrue(reserveFoundTestTree.contains("private"));
        assertTrue(reserveFoundTestTree.contains("protected"));
        assertTrue(reserveFoundTestTree.contains("public"));
        assertTrue(reserveFoundTestTree.contains("static"));
        assertTrue(reserveFoundTestTree.contains("this"));
        assertTrue(reserveFoundTestTree.contains("void"));
    }

    @Test
    void printReservedWordsFound() {
        // Save the original System.out
        PrintStream originalOut = System.out;

        // Create a ByteArrayOutputStream to capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream testOut = new PrintStream(outputStream);

        // Redirect System.out to the test PrintStream
        System.setOut(testOut);

        // Code to test
        testParser.printReservedWordsFound();

        // Restore the original System.out
        System.setOut(originalOut);

        String testOutString = outputStream.toString();
        Scanner output = new Scanner(testOutString);
        Iterator<String> itr = testParser.getReservedFoundTree().iterator();
        while (output.hasNext()) {
            assertEquals(itr.next(), output.next());
        }
        assertFalse(itr.hasNext());
    }

    @Test
    void printIdentifiersFound() {
        // Save the original System.out
        PrintStream originalOut = System.out;

        // Create a ByteArrayOutputStream to capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream testOut = new PrintStream(outputStream);

        // Redirect System.out to the test PrintStream
        System.setOut(testOut);

        // Code to test
        testParser.printIdentifiersFound();

        // Restore the original System.out
        System.setOut(originalOut);

        String testOutString = outputStream.toString();
        Scanner output = new Scanner(testOutString);
        Iterator<String> itr = testParser.getIdentifiersTree().iterator();
        while (output.hasNext()) {
            assertEquals(itr.next(), output.next());
        }
        assertFalse(itr.hasNext());
    }
}