import TreePackage.RedBlackTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
    RedBlackTree<String> reservedWordsTree;
    RedBlackTree<String> identifiersTree;

    public Parser(File f) throws FileNotFoundException {
        reservedWordsTree = new RedBlackTree<>();
        identifiersTree = new RedBlackTree<>();
    }

    private void initializeReservedWords(){
        Scanner reservedWordsDoc = new Scanner("reservedWords.txt");
        while (reservedWordsDoc.hasNext()) {
            reservedWordsTree.add(reservedWordsDoc.next());
        }
    }

//    private void setBalancedBST(File codeFile) throws FileNotFoundException {
//
//    }

    private void getIdentifiers(File codeFile) throws FileNotFoundException {
        Scanner codeSample = new Scanner(codeFile);
        codeSample.useDelimiter("[\\\\p{Punct}\\\\s]+");
        //codeSample.useDelimiter("[\n\t\r\f:;<=?@\\]\\[^_`{|}~!\"$%&'()*+/,-.\\\\]");
        while (codeSample.hasNext()) {
            String temp = codeSample.next();
            if (!reservedWordsTree.contains(temp)) {
                identifiersTree.add(temp);
            }
        }
    }
}
