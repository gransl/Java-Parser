package TreePackage;

import java.util.Iterator;

public class RedBlackTree<T extends Comparable<? super T>> implements SearchTreeInterface<T>{
    /** root of the RedBlackTree*/
    RedBlackNode<T> root;

    /**
     * Full Constructor
     * @param rootEntry
     * @param parent
     * @param left
     * @param right
     */
    public RedBlackTree(T rootEntry, RedBlackNode<T> parent, RedBlackNode<T> left, RedBlackNode<T> right)
    {
        setRootNode(new RedBlackNode<T>(rootEntry, parent, left, right));
    }

    // --------------------- // --- SEARCH/ADD/DELETE --- // --------------------- //

    /**
     * Searches for a specific entry in this tree.
     *
     * @param anEntry An object to be found.
     * @return True if the object was found in the tree.
     */
    @Override
    public boolean contains(T anEntry) {
        return getEntry(anEntry) != null;
    }

    @Override
    public T getEntry(T anEntry)
    {
        return findEntry(getRootNode(), anEntry);
    } // end getEntry


    private T findEntry(RedBlackNode<T> rootNode, T anEntry)
    {
        T result = null;

        if (rootNode != null)
        {
            T rootEntry = rootNode.getData();

            if (anEntry.equals(rootEntry))
                result = rootEntry;
            else if (anEntry.compareTo(rootEntry) < 0)
                result = findEntry(rootNode.getLeftChild(), anEntry);
            else
                result = findEntry(rootNode.getRightChild(), anEntry);
        } // end if

        return result;
    } // end findEntry


    /**
     * Adds a new entry to this tree, if it does not match an existing
     * object in the tree. Otherwise, replaces the existing object with
     * the new entry.
     *
     * @param anEntry An object to be added to the tree.
     * @return Either null if anEntry was not in the tree but has been added, or
     * the existing entry that matched the parameter anEntry
     * and has been replaced in the tree.
     */
    @Override
    public T add(T anEntry) {
        if (isEmpty()) {
            root = new RedBlackNode(anEntry, null, null, null);
            root.setBlack();
            return null;
        }
        return null;
    }




    /**
     * Removes a specific entry from this tree.
     *
     * @param anEntry An object to be removed.
     * @return Either the object that was removed from the tree or
     * null if no such object exists.
     */
    @Override
    public T remove(T anEntry) {
        throw new UnsupportedOperationException();
    }
    // --------------------- // --- SEARCH/ADD/DELETE END --- // --------------------- //


    // ----------------------- // --- ROTATION METHODS --- // ----------------------- //
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
        replaceParentsChild(parent, node, rightChild);
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
    // ----------------------- // --- ROTATION METHODS END --- // ----------------------- //


    /**
     * Creates an iterator that traverses all entries in this tree.
     *
     * @return An iterator that provides sequential and ordered access
     * to the entries in the tree.
     */
    @Override
    public Iterator<T> getInorderIterator() {
        return null;
    }


    @Override
    public T getRootData() {
        return root.getData();
    }


    /**
     * @param rootNode
     */
    private void setRootNode(RedBlackNode<T> rootNode) {
        root = rootNode;
    }


    /**
     * @return
     */
    private RedBlackNode<T> getRootNode() {
        return root;
    }


    /**
     * @return
     */
    @Override
    public int getHeight() {
        int height = 0;
        if (root != null) {
            height = root.getHeight();
        }
        return getHeight();
    }


    /**
     * @return
     */
    public int getBlackHeight() {
        int blackHeight = 0;
        if (root != null) {
            blackHeight = root.getBlackHeight();
        }
        return getHeight();
    }

    /**
     * @return
     */
    @Override
    public int getNumberOfNodes() {
        int numberOfNodes = 0;
        if (root != null)
            numberOfNodes = root.getNumberOfNodes();
        return numberOfNodes;
    }

    /**
     * @return
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     *
     */
    @Override
    public void clear() {
        root = null;
    }
}
