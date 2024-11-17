package TreePackage;
//TODO: What do I gain by using inheritance? It feels like I have to redefine the parents, left, and right
public class RedBlackNode<T> extends BinaryNode<T> {
    T data;
    /** reference to parent */
    private RedBlackNode<T> parent;
    /** reference to left node */
    private RedBlackNode<T> left;
    /** reference to right node */
    private RedBlackNode<T> right;
    /** to color nodes red or black, uses Enum class */
    RBColor color;


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
        color = RBColor.RED;
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
        parent = newRight;
    }
    // --------------------- // --- MUTATORS AND ACCESSORS END --- // --------------------- //

    /** Computes the black height of the subtree rooted at this node.
     *
     * @return  The height of the subtree rooted at this node.
     */
    public int getBlackHeight() {
        return getBlackHeight(this);
    }

    private int getBlackHeight(BinaryNode<T> node) {
        int height = 0;
        if (node != null) {
            if (color == RBColor.BLACK) {
                height = 1 + Math.max(getBlackHeight(node.getLeftChild()), getBlackHeight(node.getRightChild()));
            }
        }

        return height;
    }


}
