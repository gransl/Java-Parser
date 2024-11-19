package TreePackage;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RedBlackNodeTest {

    private static class TestObject {
        String name;
        int data;

        TestObject(String name, int data) {
            this.name = name;
            this.data = data;
        }
    }


    @Test
    void getData() {
        RedBlackNode<Double> pi = new RedBlackNode<>(3.14, null, null, null);
        assertEquals(3.14, pi.getData());
    }


    @Test
    void setData() {
        RedBlackNode<Double> tau = new RedBlackNode<>(3.14, null, null, null);
        tau.setData(6.28);
        assertEquals(6.28, tau.getData());
    }


    @Test
    void getParent() {
        RedBlackNode<Double> pi = new RedBlackNode<>(3.14, null, null, null);
        RedBlackNode<Double> e = new RedBlackNode<>(2.71, pi, null, null);
        RedBlackNode<Double> tau = new RedBlackNode<>(6.28, pi, null, null);
        assertEquals(pi, e.getParent());
        assertEquals(pi, tau.getParent());
        assertNull(pi.getParent());
    }


    @Test
    void setParent() {
        RedBlackNode<String> pi = new RedBlackNode<>("pi", null, null, null);
        RedBlackNode<String> e = new RedBlackNode<>("e", null, null, null);
        RedBlackNode<String> tau = new RedBlackNode<>("tau", null, null, null);
        e.setParent(pi);
        tau.setParent(pi);
        assertEquals(pi, e.getParent());
        assertEquals(pi, tau.getParent());
        assertNull(pi.getParent());
    }


    @Test
    void getLeftChild() {
        RedBlackNode<String> e = new RedBlackNode<>("e", null, null, null);
        RedBlackNode<String> tau = new RedBlackNode<>("tau", null, null, null);
        RedBlackNode<String> pi = new RedBlackNode<>("pi", null, e, tau);
        assertEquals(e, pi.getLeftChild());
        assertNull(e.getLeftChild());
    }


    @Test
    void setLeftChild() {
        RedBlackNode<String> e = new RedBlackNode<>("e", null, null, null);
        RedBlackNode<String> tau = new RedBlackNode<>("tau", null, null, null);
        RedBlackNode<String> pi = new RedBlackNode<>("pi", null, null, null);
        pi.setLeftChild(e);
        assertEquals(e, pi.getLeftChild());
    }


    @Test
    void getRightChild() {
        RedBlackNode<String> e = new RedBlackNode<>("e", null, null, null);
        RedBlackNode<String> tau = new RedBlackNode<>("tau", null, null, null);
        RedBlackNode<String> pi = new RedBlackNode<>("pi", null, e, tau);
        assertEquals(tau, pi.getRightChild());
        assertNull(tau.getRightChild());
    }


    @Test
    void setRightChild() {
        RedBlackNode<String> e = new RedBlackNode<>("e", null, null, null);
        RedBlackNode<String> tau = new RedBlackNode<>("tau", null, null, null);
        RedBlackNode<String> pi = new RedBlackNode<>("pi", null, null, null);
        pi.setRightChild(tau);
        assertEquals(tau, pi.getRightChild());
    }


    @Test
    void setBlackAndIsBlack() {
        TestObject test1 = new TestObject("test1", 1);
        RedBlackNode<TestObject> testNode1 = new RedBlackNode<>(test1, null, null, null);
        assertFalse(testNode1.isBlack());
        testNode1.setBlack();
        assertTrue(testNode1.isBlack());
        testNode1.setBlack();
        assertTrue(testNode1.isBlack());
    }


    @Test
    void setRed() {
        TestObject test1 = new TestObject("test1", 1);
        RedBlackNode<TestObject> testNode1 = new RedBlackNode<>(test1, null, null, null);
        assertFalse(testNode1.isBlack());
        testNode1.setBlack();
        assertTrue(testNode1.isBlack());
        testNode1.setRed();
        assertFalse(testNode1.isBlack());
        testNode1.setRed();
        assertFalse(testNode1.isBlack());
    }


    @Test
    void getNumberOfNodes() {
        RedBlackNode<Double> pi = new RedBlackNode<>(3.14, null, null, null);
        RedBlackNode<Double> e = new RedBlackNode<>(2.71, null, null, null);
        RedBlackNode<Double> tau = new RedBlackNode<>(6.28, null, null, null);
        RedBlackNode<Double> phi = new RedBlackNode<>(1.618, null, null, null);
        assertEquals(1, pi.getNumberOfNodes());
        pi.setLeftChild(e);
        e.setParent(pi);
        assertEquals(2, pi.getNumberOfNodes());
        pi.setRightChild(tau);
        tau.setParent(pi);
        assertEquals(3, pi.getNumberOfNodes());
        e.setLeftChild(phi);
        phi.setParent(e);
        assertEquals(4, pi.getNumberOfNodes());
    }

    @Test
    void getHeight() {
        RedBlackNode<Double> pi = new RedBlackNode<>(3.14, null, null, null);
        RedBlackNode<Double> e = new RedBlackNode<>(2.71, null, null, null);
        RedBlackNode<Double> tau = new RedBlackNode<>(6.28, null, null, null);
        RedBlackNode<Double> phi = new RedBlackNode<>(1.618, null, null, null);
        RedBlackNode<Double> zero = new RedBlackNode<>(0.0, null, null, null);
        assertEquals(1, pi.getHeight());
        pi.setLeftChild(e);
        e.setParent(pi);
        assertEquals(2, pi.getHeight());
        pi.setRightChild(tau);
        tau.setParent(pi);
        assertEquals(2, pi.getHeight());
        e.setLeftChild(phi);
        phi.setParent(e);
        assertEquals(3, pi.getHeight());
        phi.setLeftChild(zero);
        zero.setParent(phi);
        assertEquals(4, pi.getHeight());
        assertEquals(1, zero.getHeight());
    }

}