package TreePackage;

/**
 * Node class to go with RedBlackTree
 * @param <T> Generic of Type T
 */
public class RedBlackNode<T> {
    T data;
    /** reference to parent */
    private RedBlackNode<T> parent;
    /** reference to left node */
    private RedBlackNode<T> left;
    /** reference to right node */
    private RedBlackNode<T> right;
    /** to color nodes red or black, uses Enum class */
    private boolean isBlack;


    /**
     * Full constructor. Color of new nodes is always RED.
     * @param newData  data to be added to the node
     * @param newParent parent of this node, only null if root of tree;
     * @param newLeft left child of this node, can be null
     * @param newRight right child of this node, can be null
     */
    public RedBlackNode(T newData, RedBlackNode<T> newParent, RedBlackNode<T> newLeft, RedBlackNode newRight) {
        data = newData;
        parent = newParent;
        left = newLeft;
        right = newRight;
        isBlack = false;
    }

    // --------------------- // --- MUTATORS AND ACCESSORS --- // --------------------- //
    public T getData() {
        return data;
    }

    public void setData(T newData) {
        data = newData;
    }

    public RedBlackNode<T> getParent() {
        return parent;
    }

    public void setParent(RedBlackNode<T> newParent) {
        parent = newParent;
    }

    public RedBlackNode<T> getLeftChild() {
        return left;
    }

    public void setLeftChild(RedBlackNode<T> newLeft) {
        left = newLeft;
    }

    public RedBlackNode<T> getRightChild() {
        return right;
    }

    public void setRightChild(RedBlackNode<T> newRight) {
        right = newRight;
    }

    public void setBlack() {
        isBlack = true;
    }

    public void setRed() {
        isBlack = false;
    }

    public boolean isBlack() {
        return isBlack;
    }

    // --------------------- // --- MUTATORS AND ACCESSORS END --- // --------------------- //


    /** Counts the nodes in the subtree rooted at this node.
     @return  The number of nodes in the subtree rooted at this node. */
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


    /** Computes the height of the subtree rooted at this node.
     * Height includes root node
     @return  The height of the subtree rooted at this node. */
    public int getHeight()
    {
        return getHeight(this); // Call private getHeight
    } // end getHeight


    public int getHeight(RedBlackNode<T> node)
    {
        int height = 0;
        if (node != null) {
            height = 1 + Math.max(getHeight(node.getLeftChild()), getHeight(node.getRightChild()));
        }
        return height;
    }

}
