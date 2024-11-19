import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * I learned this PrintStream method for testing print statements from this source:
 * https://www.baeldung.com/java-testing-system-out-println
 */
class PalindromeTest {

    @Test
    void main() {
        // Save the original System.out
        PrintStream originalOut = System.out;

        // Create a ByteArrayOutputStream to capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream testOut = new PrintStream(outputStream);

        // Redirect System.out to the test PrintStream
        System.setOut(testOut);

        // Code to test
        String[] testArgs = {};
        Palindrome.main(testArgs);

        // Restore the original System.out
        System.setOut(originalOut);

        String testOutString = outputStream.toString();
        Scanner output = new Scanner(testOutString);

        assertTrue(Boolean.parseBoolean(output.next()));
        assertFalse(output.hasNext());
    }

    @Test
    void isPalindrome() {
        assertTrue(Palindrome.isPalindrome("civic"));
        assertTrue(Palindrome.isPalindrome("a"));
        assertTrue(Palindrome.isPalindrome("RotAtoR"));
        assertTrue(Palindrome.isPalindrome("nAAn"));
        assertTrue(Palindrome.isPalindrome("aibohphobia"));
        assertTrue(Palindrome.isPalindrome("abcdefghhgfedcba"));
        assertTrue(Palindrome.isPalindrome("abcdefghihgfedcba"));
        assertTrue(Palindrome.isPalindrome(""));
        assertFalse(Palindrome.isPalindrome("hello"));
        assertFalse(Palindrome.isPalindrome("Civic"));
        assertFalse(Palindrome.isPalindrome("nAan"));
        assertFalse(Palindrome.isPalindrome("friday"));
        assertFalse(Palindrome.isPalindrome("oroboros"));
        assertFalse(Palindrome.isPalindrome("marjoram"));
        assertFalse(Palindrome.isPalindrome("laconical"));
        assertFalse(Palindrome.isPalindrome("xerox"));
        assertFalse(Palindrome.isPalindrome("foolproof"));
    }
}