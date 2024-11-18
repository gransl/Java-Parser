import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Parser palindromeParser = new Parser(new File("src/main/java/Palindrome.java"));

        System.out.println("---- Reserved Words Found ---");
        palindromeParser.printReservedWordsFound();
        System.out.println();
        System.out.println("---- Identifiers Found ---");

        palindromeParser.printIdentifiersFound();
    }
}
