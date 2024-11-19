import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * I learned this PrintStream method for testing print statements from this source:
 * https://www.baeldung.com/java-testing-system-out-println
 */
class MainTest {
    static Parser testParser;

    @BeforeAll
    static void setUp() throws FileNotFoundException {
        testParser = new Parser(new File("src/main/java/Palindrome.java"));
    }

    @Test
    void main() throws FileNotFoundException {
        // Save the original System.out
        PrintStream originalOut = System.out;

        // Create a ByteArrayOutputStream to capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream testOut = new PrintStream(outputStream);

        // Redirect System.out to the test PrintStream
        System.setOut(testOut);

        // Code to test
        String[] testArgs = {};
        Main.main(testArgs);

        // Restore the original System.out
        System.setOut(originalOut);

        String testOutString = outputStream.toString();
        Scanner output = new Scanner(testOutString);

        // Test output
        assertEquals("---- Reserved Words Found ---", output.nextLine());
        Iterator<String> itrReserve = testParser.getReservedFoundTree().iterator();
        while (itrReserve.hasNext()) {
            String temp = output.next();
            String expected = itrReserve.next();
            assertEquals(expected, temp);
        }
        assertFalse(itrReserve.hasNext());

        assertEquals("", output.nextLine());
        assertEquals("", output.nextLine());
        assertEquals("---- Identifiers Found ---", output.nextLine());
        Iterator<String> itrIdentify = testParser.getIdentifiersTree().iterator();
        while (itrIdentify.hasNext()) {
            assertEquals(itrIdentify.next(), output.next());
        }
        assertFalse(itrIdentify.hasNext());
    }
}