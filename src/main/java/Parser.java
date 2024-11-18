import TreePackage.RedBlackTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
    RedBlackTree<String> reservedWordsTree;
    RedBlackTree<String> identifiersTree;
    RedBlackTree<String> reservedFoundTree;


    public Parser(File f) throws FileNotFoundException {
        reservedWordsTree = new RedBlackTree<>();
        identifiersTree = new RedBlackTree<>();
        reservedFoundTree = new RedBlackTree<>();
        initializeReservedWords();
        getIdentifiers(f);
    }

    private void initializeReservedWords() throws FileNotFoundException {
        Scanner reservedWordsDoc = new Scanner(new File("src/main/java/reservedWords.txt"));
        while (reservedWordsDoc.hasNext()) {
            reservedWordsTree.add(reservedWordsDoc.next());
        }
    }

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

    protected RedBlackTree<String> getReservedWordsTree() {
        return reservedWordsTree;
    }

    protected RedBlackTree<String> getIdentifiersTree() {
        return identifiersTree;
    }

    protected RedBlackTree<String> getReservedFoundTree() {
        return reservedFoundTree;
    }

    public void printReservedWordsFound() {
        for (String keyword : reservedFoundTree) {
            System.out.println(keyword);
        }
    }

    public void printIdentifiersFound() {
        for (String identifier : identifiersTree) {
            System.out.println(identifier);
        }
    }

}
