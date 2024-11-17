package TreePackage;

public class RedBlackNode<T> extends BinaryNode<T> {
    /** reference to parent */
    private BinaryNode<T> parent;
    /** to color nodes red or black, uses Enum class */
    RBColor color;

    public RedBlackNode() {
        super();
        parent = null;
        color = RBColor.RED;
    }

}
