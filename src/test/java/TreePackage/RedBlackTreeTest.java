package TreePackage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class RedBlackTreeTest {
    public static RedBlackTree<String> stringTree;
    public static RedBlackTree<Double> emptyTree;

    @BeforeAll
    static void setUp() {
        emptyTree = new RedBlackTree();
        stringTree = new RedBlackTree("m");
        stringTree.add("h");
        stringTree.add("b");
        stringTree.add("w");
        stringTree.add("q");
        stringTree.add("z");
        stringTree.add("e");
    }


    @Test
    void emptyConstructor() {
        assertNull(emptyTree.getRootNode());
    }

    @Test
    void partialConstructor() {
        RedBlackTree<Double> constantTree = new RedBlackTree(3.14);
        RedBlackNode<Double> rootNode = constantTree.getRootNode();
        assertEquals(3.14, rootNode.getData());
        assertNull(rootNode.getParent());
        assertNull(rootNode.getLeftChild());
        assertNull(rootNode.getRightChild());
        assertTrue(rootNode.isBlack());
    }

    @Test
    void add() {
        //populate tree & set variables
        RedBlackTree<Integer> intTree = new RedBlackTree();
        assertNull(intTree.add(13));
        assertEquals(13, intTree.getRootData());

        assertNull(intTree.add(7));
        assertNull(intTree.add(26));
        RedBlackNode<Integer> rootNode = intTree.getRootNode();
        RedBlackNode<Integer> leftChild = rootNode.getLeftChild();
        RedBlackNode<Integer> rightChild = rootNode.getRightChild();

        //check values & relationships
        assertEquals(13, intTree.getRootData());
        assertNull(rootNode.getParent());
        assertEquals(7, leftChild.getData());
        assertEquals(26, rightChild.getData());
        assertEquals(13, leftChild.getParent().getData());
        assertEquals(13, rightChild.getParent().getData());
        assertNull(leftChild.getLeftChild());
        assertNull(leftChild.getRightChild());

        //check colors
        assertFalse(leftChild.isBlack());
        assertFalse(rightChild.isBlack());
        assertTrue(rootNode.isBlack());

        //test adding same entry
        assertEquals(7, intTree.add(7));
        assertEquals(13, intTree.add(13));
    }


    @Test
    void addLeftLeftRedAunt() {
        //populate tree & set variables
        RedBlackTree<Integer> intTree = new RedBlackTree(13);
        assertEquals(13, intTree.getRootData());
        intTree.add(7);
        intTree.add(26);
        RedBlackNode<Integer> rootNode = intTree.getRootNode();
        RedBlackNode<Integer> leftChild = rootNode.getLeftChild();
        RedBlackNode<Integer> rightChild = rootNode.getRightChild();
        assertNull(intTree.add(1));
        RedBlackNode<Integer> leftLeftChild = leftChild.getLeftChild();

        //check values & relationships
        assertEquals(13, intTree.getRootData());
        assertNull(rootNode.getParent());
        assertEquals(7, leftChild.getData());
        assertEquals(26, rightChild.getData());
        assertEquals(13, leftChild.getParent().getData());
        assertEquals(13, rightChild.getParent().getData());
        assertEquals(1, leftChild.getLeftChild().getData());
        assertEquals(7, leftLeftChild.getParent().getData());
        assertNull(leftChild.getRightChild());

        //check colors
        assertTrue(rootNode.isBlack()); //root always black
        assertTrue(leftChild.isBlack());
        assertTrue(rightChild.isBlack());
        assertFalse(leftLeftChild.isBlack());
    }

    @Test
    void addLeftRightRedAunt() {
        //populate tree & set variables
        RedBlackTree<Integer> intTree = new RedBlackTree(13);
        assertEquals(13, intTree.getRootData());
        intTree.add(7);
        intTree.add(26);
        RedBlackNode<Integer> rootNode = intTree.getRootNode();
        RedBlackNode<Integer> leftChild = rootNode.getLeftChild();
        RedBlackNode<Integer> rightChild = rootNode.getRightChild();
        assertNull(intTree.add(10));
        RedBlackNode<Integer> leftRightChild = leftChild.getRightChild();

        //check values & relationships
        assertEquals(13, intTree.getRootData());
        assertNull(rootNode.getParent());
        assertEquals(7, leftChild.getData());
        assertEquals(26, rightChild.getData());
        assertEquals(13, leftChild.getParent().getData());
        assertEquals(13, rightChild.getParent().getData());
        assertEquals(10, leftRightChild.getData());
        assertEquals(7, leftRightChild.getParent().getData());
        assertNull(leftChild.getLeftChild());

        //check colors
        assertTrue(rootNode.isBlack()); //root always black
        assertTrue(leftChild.isBlack());
        assertTrue(rightChild.isBlack());
        assertFalse(leftRightChild.isBlack());
    }




    @Test
    void addRightLeftRedAunt() {
        //populate tree & set variables
        RedBlackTree<Integer> intTree = new RedBlackTree(13);
        assertEquals(13, intTree.getRootData());
        intTree.add(7);
        intTree.add(26);
        RedBlackNode<Integer> rootNode = intTree.getRootNode();
        RedBlackNode<Integer> leftChild = rootNode.getLeftChild();
        RedBlackNode<Integer> rightChild = rootNode.getRightChild();
        assertNull(intTree.add(20));
        RedBlackNode<Integer> rightLeftChild = rightChild.getLeftChild();

        //check values & relationships
        assertEquals(13, intTree.getRootData());
        assertNull(rootNode.getParent());
        assertEquals(7, leftChild.getData());
        assertEquals(26, rightChild.getData());
        assertEquals(13, leftChild.getParent().getData());
        assertEquals(13, rightChild.getParent().getData());
        assertEquals(20, rightLeftChild.getData());
        assertEquals(26, rightLeftChild.getParent().getData());
        assertNull(rightChild.getRightChild());

        //check colors
        assertTrue(rootNode.isBlack()); //root always black
        assertTrue(leftChild.isBlack());
        assertTrue(rightChild.isBlack());
        assertFalse(rightLeftChild.isBlack());
    }


    @Test
    void addRightRightRedAunt() {
        //populate tree & set variables
        RedBlackTree<Integer> intTree = new RedBlackTree(13);
        assertEquals(13, intTree.getRootData());
        intTree.add(7);
        intTree.add(26);
        RedBlackNode<Integer> rootNode = intTree.getRootNode();
        RedBlackNode<Integer> leftChild = rootNode.getLeftChild();
        RedBlackNode<Integer> rightChild = rootNode.getRightChild();
        assertNull(intTree.add(30));
        RedBlackNode<Integer> rightRightChild = rightChild.getRightChild();

        //check values & relationships
        assertEquals(13, intTree.getRootData());
        assertNull(rootNode.getParent());
        assertEquals(7, leftChild.getData());
        assertEquals(26, rightChild.getData());
        assertEquals(13, leftChild.getParent().getData());
        assertEquals(13, rightChild.getParent().getData());
        assertEquals(30, rightRightChild.getData());
        assertEquals(26, rightRightChild.getParent().getData());
        assertNull(rightChild.getLeftChild());

        //check colors
        assertTrue(rootNode.isBlack()); //root always black
        assertTrue(leftChild.isBlack());
        assertTrue(rightChild.isBlack());
        assertFalse(rightRightChild.isBlack());
    }

    @Test
    void addLeftLeftBlackAunt() {
        //populate tree & set variables
        RedBlackTree<Integer> intTree = new RedBlackTree(13);
        assertEquals(13, intTree.getRootData());
        intTree.add(7);
        intTree.add(26);
        intTree.add(1);
        assertNull(intTree.add(-9));
        RedBlackNode<Integer> rootNode = intTree.getRootNode();
        RedBlackNode<Integer> leftChild = rootNode.getLeftChild();
        RedBlackNode<Integer> rightChild = rootNode.getRightChild();
        RedBlackNode<Integer> leftLeftChild = leftChild.getLeftChild();
        RedBlackNode<Integer> leftRightChild = leftChild.getRightChild();

        //check values & relationships
        assertEquals(13, intTree.getRootData());
        assertNull(rootNode.getParent());
        assertEquals(1, leftChild.getData());
        assertEquals(26, rightChild.getData());
        assertEquals(13, leftChild.getParent().getData());
        assertEquals(13, rightChild.getParent().getData());
        assertEquals(-9, leftLeftChild.getData());
        assertEquals(7, leftRightChild.getData());
        assertEquals(1, leftLeftChild.getParent().getData());
        assertEquals(1, leftRightChild.getParent().getData());

        //check colors
        assertTrue(rootNode.isBlack()); //root always black
        assertTrue(leftChild.isBlack());
        assertTrue(rightChild.isBlack());
        assertFalse(leftLeftChild.isBlack());
        assertFalse(leftRightChild.isBlack());
    }

    @Test
    void addLeftRightBlackAunt() {
        //populate tree & set variables
        RedBlackTree<Integer> intTree = new RedBlackTree(13);
        assertEquals(13, intTree.getRootData());
        intTree.add(7);
        intTree.add(26);
        intTree.add(20);
        assertNull(intTree.add(23));
        RedBlackNode<Integer> rootNode = intTree.getRootNode();
        RedBlackNode<Integer> leftChild = rootNode.getLeftChild();
        RedBlackNode<Integer> rightChild = rootNode.getRightChild();
        RedBlackNode<Integer> rightLeftChild = rightChild.getLeftChild();
        RedBlackNode<Integer> rightRightChild = rightChild.getRightChild();

        //check values & relationships
        assertEquals(13, intTree.getRootData());
        assertNull(rootNode.getParent());
        assertEquals(7, leftChild.getData());
        assertEquals(23, rightChild.getData());
        assertEquals(13, leftChild.getParent().getData());
        assertEquals(13, rightChild.getParent().getData());
        assertEquals(20, rightLeftChild.getData());
        assertEquals(26, rightRightChild.getData());
        assertEquals(23, rightLeftChild.getParent().getData());
        assertEquals(23, rightRightChild.getParent().getData());

        //check colors
        assertTrue(rootNode.isBlack()); //root always black
        assertTrue(leftChild.isBlack());
        assertTrue(rightChild.isBlack());
        assertFalse(rightLeftChild.isBlack());
        assertFalse(rightRightChild.isBlack());

        //additional add for coverage
        intTree.add(29);
        intTree.add(24);
        intTree.add(30);
        assertEquals(29,rightRightChild.getRightChild().getData());
        assertEquals(24,rightRightChild.getLeftChild().getData());
        assertEquals(30,rightRightChild.getRightChild().getRightChild().getData());
        assertFalse(rootNode.isBlack()); //13, no longer the root
        assertTrue(leftChild.isBlack()); //7
        assertTrue(rightChild.isBlack()); //23, new root
        assertTrue(rightLeftChild.isBlack()); //20
        assertFalse(rightRightChild.isBlack()); //26
        assertTrue(rightRightChild.getRightChild().isBlack()); //29
        assertTrue(rightRightChild.getLeftChild().isBlack()); //24
        assertFalse(rightRightChild.getRightChild().getRightChild().isBlack()); //30
    }

    @Test
    void addRightLeftBlackAunt() {
        //populate tree & set variables
        RedBlackTree<Integer> intTree = new RedBlackTree(13);
        assertEquals(13, intTree.getRootData());
        intTree.add(7);
        intTree.add(26);
        intTree.add(10);
        assertNull(intTree.add(8));
        RedBlackNode<Integer> rootNode = intTree.getRootNode();
        RedBlackNode<Integer> leftChild = rootNode.getLeftChild();
        RedBlackNode<Integer> rightChild = rootNode.getRightChild();
        RedBlackNode<Integer> leftLeftChild = leftChild.getLeftChild();
        RedBlackNode<Integer> leftRightChild = leftChild.getRightChild();

        //check values & relationships
        assertEquals(13, intTree.getRootData());
        assertNull(rootNode.getParent());
        assertEquals(8, leftChild.getData());
        assertEquals(26, rightChild.getData());
        assertEquals(13, leftChild.getParent().getData());
        assertEquals(13, rightChild.getParent().getData());
        assertEquals(7, leftLeftChild.getData());
        assertEquals(10, leftRightChild.getData());
        assertEquals(8, leftLeftChild.getParent().getData());
        assertEquals(8, leftRightChild.getParent().getData());

        //check colors
        assertTrue(rootNode.isBlack()); //root always black
        assertTrue(leftChild.isBlack());
        assertTrue(rightChild.isBlack());
        assertFalse(leftLeftChild.isBlack());
        assertFalse(leftRightChild.isBlack());
    }

    @Test
    void extraRotationTest() {
        RedBlackTree<Integer> intTree = new RedBlackTree(13);
        assertEquals(13, intTree.getRootData());
        intTree.add(8);
        intTree.add(26);
        intTree.add(10);
        intTree.add(5);
        intTree.add(2);
        intTree.add(6);
        intTree.add(-3);
        RedBlackNode<Integer> rootNode = intTree.getRootNode();
        RedBlackNode<Integer> leftChild = rootNode.getLeftChild();
        RedBlackNode<Integer> rightChild = rootNode.getRightChild();
        RedBlackNode<Integer> leftLeftChild = leftChild.getLeftChild();
        RedBlackNode<Integer> leftRightChild = leftChild.getRightChild();
        RedBlackNode<Integer> rightLeftChild = rightChild.getLeftChild();
        RedBlackNode<Integer> rightRightChild = rightChild.getRightChild();

        //check values & relationships
        assertEquals(8, intTree.getRootData());
        assertNull(rootNode.getParent());
        assertEquals(5, leftChild.getData());
        assertEquals(13, rightChild.getData());
        assertEquals(8, leftChild.getParent().getData());
        assertEquals(8, rightChild.getParent().getData());
        assertEquals(2, leftLeftChild.getData());
        assertEquals(6, leftRightChild.getData());
        assertEquals(5, leftLeftChild.getParent().getData());
        assertEquals(5, leftRightChild.getParent().getData());
        assertEquals(10, rightLeftChild.getData());
        assertEquals(26, rightRightChild.getData());
        assertEquals(13, rightLeftChild.getParent().getData());
        assertEquals(13, rightRightChild.getParent().getData());
        assertEquals(-3, leftLeftChild.getLeftChild().getData());

        //check colors
        assertTrue(rootNode.isBlack()); //8
        assertFalse(leftChild.isBlack()); //5
        assertFalse(rightChild.isBlack()); //13
        assertTrue(leftLeftChild.isBlack()); //2
        assertTrue(leftRightChild.isBlack()); //4
        assertTrue(rightLeftChild.isBlack()); //10
        assertTrue(rightRightChild.isBlack()); //26
        assertFalse(leftLeftChild.getLeftChild().isBlack()); //-3
    }

    @Test
    void addRightRightBlackAunt() {
        //populate tree & set variables
        RedBlackTree<Integer> intTree = new RedBlackTree(13);
        assertEquals(13, intTree.getRootData());
        intTree.add(7);
        intTree.add(26);
        intTree.add(30);
        assertNull(intTree.add(50));
        RedBlackNode<Integer> rootNode = intTree.getRootNode();
        RedBlackNode<Integer> leftChild = rootNode.getLeftChild();
        RedBlackNode<Integer> rightChild = rootNode.getRightChild();
        RedBlackNode<Integer> rightLeftChild = rightChild.getLeftChild();
        RedBlackNode<Integer> rightRightChild = rightChild.getRightChild();

        //check values & relationships
        assertEquals(13, intTree.getRootData());
        assertNull(rootNode.getParent());
        assertEquals(7, leftChild.getData());
        assertEquals(30, rightChild.getData());
        assertEquals(13, leftChild.getParent().getData());
        assertEquals(13, rightChild.getParent().getData());
        assertEquals(26, rightLeftChild.getData());
        assertEquals(50, rightRightChild.getData());
        assertEquals(30, rightLeftChild.getParent().getData());
        assertEquals(30, rightRightChild.getParent().getData());

        //check colors
        assertTrue(rootNode.isBlack()); //root always black
        assertTrue(leftChild.isBlack());
        assertTrue(rightChild.isBlack());
        assertFalse(rightLeftChild.isBlack());
        assertFalse(rightRightChild.isBlack());
    }

    @Test
    void contains() {
        assertTrue(stringTree.contains("h"));
        assertTrue(stringTree.contains("e"));
        assertTrue(stringTree.contains("q"));
        assertFalse(stringTree.contains("hello"));
    }

    @Test
    void getEntry() {
        assertEquals("h", stringTree.getEntry("h"));
        assertEquals("e", stringTree.getEntry("e"));
        assertEquals("q", stringTree.getEntry("q"));
        assertNotEquals("hello", stringTree.getEntry("hello"));
    }

    @Test
    void remove() {
        assertThrows(UnsupportedOperationException.class, ()-> stringTree.remove("e"));
    }

    @Test
    void getInorderIterator() {
        Iterator<String> strItr = stringTree.getInorderIterator();
        assertTrue(strItr.hasNext());
        assertEquals("b", strItr.next());
        assertThrows(UnsupportedOperationException.class, ()-> strItr.remove());
        assertTrue(strItr.hasNext());
        assertEquals("e", strItr.next());
        assertTrue(strItr.hasNext());
        assertEquals("h", strItr.next());
        assertTrue(strItr.hasNext());
        assertEquals("m", strItr.next());
        assertTrue(strItr.hasNext());
        assertEquals("q", strItr.next());
        assertTrue(strItr.hasNext());
        assertEquals("w", strItr.next());
        assertTrue(strItr.hasNext());
        assertEquals("z", strItr.next());
        assertFalse(strItr.hasNext());
        assertThrows(NoSuchElementException.class, ()-> strItr.next());
    }

    @Test
    void getHeight() {
        assertEquals(0, emptyTree.getHeight());
        assertEquals(4, stringTree.getHeight());
    }

    @Test
    void getNumberOfNodes() {
        assertEquals(0, emptyTree.getNumberOfNodes());
        assertEquals(7, stringTree.getNumberOfNodes());
    }

    @Test
    void isEmpty() {
        assertTrue(emptyTree.isEmpty());
        assertFalse(stringTree.isEmpty());
    }

    @Test
    void clear() {
        RedBlackTree<Integer> intTree = new RedBlackTree(13);
        assertEquals(13, intTree.getRootData());
        intTree.add(7);
        intTree.add(26);
        intTree.add(30);
        assertFalse(intTree.isEmpty());
        intTree.clear();
        assertTrue(intTree.isEmpty());
    }

    @Test
    void getRootData() {
        assertThrows(EmptyTreeException.class, ()-> emptyTree.getRootData());
        assertEquals("h", stringTree.getRootData());
    }
}