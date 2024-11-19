package TreePackage;

/**
 * Node class to go with RedBlackTree
 *
 * This class is package-private, only available to classes in the TreePackage.
 * Typically, I would define this as a private inner class, I decided to make it its own class like BinaryNode which I
 * realize now was its own class because several different trees used it. Honestly, I ran out of time to make this
 * change and I felt this was the simplest choice to preserve encapsulation.
 *
 * @param <T> Generic of Type T
 */
class RedBlackNode<T> {
    /** data to store in node */
    T data;
    /** reference to parent */
    private RedBlackNode<T> parent;
    /** reference to left node */
    private RedBlackNode<T> left;
    /** reference to right node */
    private RedBlackNode<T> right;
    /** to color nodes red or black*/
    private boolean isBlack;


    /**
     * Full constructor. Color of new nodes is always RED.
     * @param newData  data to be added to the node
     * @param newParent parent of this node, only null if root of tree;
     * @param newLeft left child of this node, can be null
     * @param newRight right child of this node, can be null
     */
    public RedBlackNode(T newData, RedBlackNode<T> newParent, RedBlackNode<T> newLeft, RedBlackNode<T> newRight) {
        data = newData;
        parent = newParent;
        left = newLeft;
        right = newRight;
        isBlack = false;
    }

    // --------------------- // --- MUTATORS AND ACCESSORS --- // --------------------- //
    /**
     * returns data stored in node
     *
     * @return data stored in node
     */
    public T getData() {
        return data;
    }

    /**
     * sets a new data value in the node
     * @param newData data to store in node
     */
    public void setData(T newData) {
        data = newData;
    }

    /**
     * Returns parent of this node.
     * @return parent of this node, can be null if node has no parent
     */
    public RedBlackNode<T> getParent() {
            return parent;
    }

    /**
     * Sets a new parent for this node
     * @param newParent node that will be new parent of this node
     */
    public void setParent(RedBlackNode<T> newParent) {
        parent = newParent;
    }

    /**
     * Returns left child of this node.
     * @return left child of this node, can be null if node has no left child
     */
    public RedBlackNode<T> getLeftChild() {
        return left;
    }

    /**
     * Sets a new left child for this node.
     * @param newLeft node that will be new left child of this node
     */
    public void setLeftChild(RedBlackNode<T> newLeft) {
        left = newLeft;
    }

    /**
     * Returns right child of this node.
     * @return right child of this node, can be null if node has no left child
     */
    public RedBlackNode<T> getRightChild() {
        return right;
    }

    /**
     * Sets a new right child for this node.
     * @param newRight node that will be new right child of this node.
     */
    public void setRightChild(RedBlackNode<T> newRight) {
        right = newRight;
    }

    /**
     * sets this node's color to black
     */
    public void setBlack() {
        isBlack = true;
    }

    /**
     * sets this node's color to red
     */
    public void setRed() {
        isBlack = false;
    }

    /**
     * Determines if a node is black, if it is not, it must be red.
     * @return true if node is black, false if node is red
     */
    public boolean isBlack() {
        return isBlack;
    }

    // --------------------- // --- MUTATORS AND ACCESSORS END --- // --------------------- //


    /** Counts the nodes in the subtree rooted at this node.
     * @return  The number of nodes in the subtree rooted at this node.
     */
    public int getNumberOfNodes()
    {
        int leftNumber = 0;
        int rightNumber = 0;
        if (left != null)
            leftNumber = left.getNumberOfNodes();
        if (right != null)
            rightNumber = right.getNumberOfNodes();
        return 1 + leftNumber + rightNumber;
    } // end getNumberOfNodes


    /**
     * Computes the height of the subtree rooted at this node. Height includes root node.
     *
     * @return  The height of the subtree rooted at this node.
     */
    public int getHeight()
    {
        return getHeight(this); // Call private getHeight
    } // end getHeight


    /**
     * Private helper function of getHeight.
     *
     * @param node node to calculate height of
     * @return height of current node
     */
    private int getHeight(RedBlackNode<T> node)
    {
        int height = 0;
        if (node != null) {
            height = 1 + Math.max(getHeight(node.getLeftChild()), getHeight(node.getRightChild()));
        }
        return height;
    }

}
