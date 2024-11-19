import TreePackage.RedBlackTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Parser class takes a file, preferably of Java code, and parses which words are user-identified words and which
 * are reserved keywords.
 *
 * @author Sandra Gran
 * @date 11-18-24
 */
public class Parser {
    /** tree that holds all the reserved words for Java */
    RedBlackTree<String> reservedWordsTree;
    /** tree that holds all the user-identified words found in the file, no duplicates*/
    RedBlackTree<String> identifiersTree;
    /** tree that holds all of the reserved words found in the file, no duplicates*/
    RedBlackTree<String> reservedFoundTree;


    /**
     * Constructor
     * @param f a file, preferably Java code,
     * @throws FileNotFoundException if provided file cannot be found.
     */
    public Parser(File f) throws FileNotFoundException {
        reservedWordsTree = new RedBlackTree<>();
        identifiersTree = new RedBlackTree<>();
        reservedFoundTree = new RedBlackTree<>();
        initializeReservedWords();
        getIdentifiers(f);
    }

    /**
     * Populates a table full of the reserved words from Java.
     * @throws FileNotFoundException If file provided in constructor cannot be found.
     */
    private void initializeReservedWords() throws FileNotFoundException {
        Scanner reservedWordsDoc = new Scanner(new File("src/main/java/reservedWords.txt"));
        while (reservedWordsDoc.hasNext()) {
            reservedWordsTree.add(reservedWordsDoc.next());
        }
    }

    /**
     * Parses the file, disregards white space and punctuation, and separates the reserved keywords
     * from the user-identified words by putting them in two separate trees.
     * @param codeFile a file, preferably with Java Code
     * @throws FileNotFoundException If file provided in constructor cannot be found.
     */
    private void getIdentifiers(File codeFile) throws FileNotFoundException {
        Scanner codeSample = new Scanner(codeFile);
        codeSample.useDelimiter("[:;<\s>=?@\\]\\[^_`{|}~!\"$%&'()*+/,-.\\\\]");
        while (codeSample.hasNext()) {
            String temp = codeSample.next().trim();
            if (reservedWordsTree.contains(temp)) {
                reservedFoundTree.add(temp);
            } else {
                if (temp.length() > 0) {
                    identifiersTree.add(temp);
                }
            }
        }
    }

    /**
     * Returns a tree with all the reserved words.
     * @return a tree with all the reserved words
     */
    protected RedBlackTree<String> getReservedWordsTree() {
        return reservedWordsTree;
    }

    /**
     * Returns a tree with all the user-identified words found. No duplicates.
     * @return a tree with all the user-identified words
     */
    protected RedBlackTree<String> getIdentifiersTree() {
        return identifiersTree;
    }

    /**
     * Returns a tree with all the reserved keywords found. No duplicates.
     * @return a tree with all the reserved keywords
     */
    protected RedBlackTree<String> getReservedFoundTree() {
        return reservedFoundTree;
    }

    /**
     * Prints a list of all the reserved keywords found.
     */
    public void printReservedWordsFound() {
        for (String keyword : reservedFoundTree) {
            System.out.println(keyword);
        }
    }

    /**
     * Prints a list of all the user-identified words found.
     */
    public void printIdentifiersFound() {
        for (String identifier : identifiersTree) {
            System.out.println(identifier);
        }
    }

}
