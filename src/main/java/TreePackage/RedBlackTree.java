package TreePackage;

public class RedBlackTree<T extends Comparable<? super T>> extends BinarySearchTree{
    RedBlackNode<T> root;


    public RedBlackTree(T rootEntry, RedBlackNode<T> parent, RedBlackNode<T> left, RedBlackNode<T> right)
    {
        setRootNode(new RedBlackNode<T>(rootEntry, parent, left, right));
    } // end constructor


    protected void setRootNode(RedBlackNode<T> rootNode)
    {
        root = rootNode;
    } // end setRootNode


    protected RedBlackNode<T> getRootNode()
    {
        return root;
    } // end getRootNode


    private void rotateRight(RedBlackNode node) {
        RedBlackNode parent = node.getParent();

        // Point at the node's left child. This will rotate up and become the new root of the subtree at method's end.
        RedBlackNode leftChild = node.getLeftChild();

        /* Set the node's left child to now be its current left child's right child.
         Since the leftChild will soon be the new root of the subtree, it's new right will need to be the old root: node
         But it's old right subtree still needs to be to the right of it, so we will attach it to the left of node. */
        node.setLeftChild(leftChild.getRightChild());

        //Since this subtree now has a new parent, node, as long as it's not null, its parent variable needs to point at it
        if(leftChild.getRightChild() != null) {
            leftChild.getRightChild().setParent(node);
        }

        // Set the new right child of the leftChild to be the node, as outlined earlier.
        leftChild.setRightChild(node);

        // Set node's new parent to be the new root of the subtree, since it is no longer the root.
        node.setParent(leftChild);

        // Set the parent of subtree we just altered to point at the new root and vice versa.
        replaceParentsChild(parent, node, leftChild);
    }


    private void rotateLeft(RedBlackNode node) {
        RedBlackNode parent = node.getParent();

        // Point at the node's right child. This will rotate up and become the new root of the subtree.
        RedBlackNode rightChild = node.getRightChild();

        /* Set the node's right child to be the left child of its current right child.
        * Since rightChild will soon become the new root of the subtree, it's new left child will need to be the old
        * subtree root: node. However, rightChild's current leftSubtree still needs to be to its left somewhere.
        * Since node will be to the left of RightChild after rotation and since everything in rightChild's left subtree
        * is larger than node (since it was to the right of node to begin with, we can attach rightChild's current
        * to the be node's right subtree.*/
        node.setRightChild(rightChild.getLeftChild());

        // After assigning rightChild's leftSubtree to be node's left subtree, it now has a new parent.
        if (rightChild.getLeftChild() != null) {
            rightChild.getLeftChild().setParent(node);
        }

        // Set the rightChild's left to be node, making rightChild the new root of the subtree.
        rightChild.setLeftChild(node);

        // Set node's new parent to be the new root of the subtree, since it is no longer the root.
        node.setParent(rightChild);

        //Set the parent of the subtree we just altered to point at the new root and vice versa.
        replaceParentsChild(parent, node rightChild);
    }


    /**
     * This method will make sure the parent for a subtree is correctly pointing at its new root node and vice versa
     * after a rotation has been performed.
     * @param parent of the subtree
     * @param oldChild the old root of the subtree, which is the old child of the parent
     * @param newChild the new root of the subtree, which is the new child of the parent
     */
    private void replaceParentsChild(RedBlackNode parent, RedBlackNode oldChild, RedBlackNode newChild) {
        /* If there is no parent, we rotated at the very root of the tree, so we must reassign this field to the node
         that has rotated into the root position. */
        if (parent == null) {
            root = newChild;
        } else if (parent.getLeftChild() == oldChild) { // The child is the left subtree of the parent
            parent.setLeftChild(newChild);
        } else if (parent.getRightChild() == oldChild) { // The child is the right subtree of the parent
            parent.setRightChild(newChild);
        } else {
            throw new IllegalStateException("Node is not a child of its parent");
        }

        if (newChild != null) {
            newChild.setParent(parent);
        }
    }






}
