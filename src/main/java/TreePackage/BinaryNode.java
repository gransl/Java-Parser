package TreePackage;

/**
 A class that represents nodes in a binary tree.

 @author Frank M. Carrano
 @author Timothy M. Henry
 @version 5.0
 */
public class BinaryNode<T>
{
    private T             data;
    private BinaryNode<T> leftChild;  // Reference to left child
    private BinaryNode<T> rightChild; // Reference to right child

    public BinaryNode()
    {
        this(null); // Call next constructor
    } // end default constructor

    public BinaryNode(T dataPortion)
    {
        this(dataPortion, null, null); // Call next constructor
    } // end constructor

    public BinaryNode(T dataPortion, BinaryNode<T> newLeftChild,
                      BinaryNode<T> newRightChild)
    {
        data = dataPortion;
        leftChild = newLeftChild;
        rightChild = newRightChild;
    } // end constructor

    /** Retrieves the data portion of this node.
     @return  The object in the data portion of the node. */
    public T getData()
    {
        return data;
    } // end getData

    /** Sets the data portion of this node.
     @param newData  The data object. */
    public void setData(T newData)
    {
        data = newData;
    } // end setData

    /** Retrieves the left child of this node.
     @return  A reference to this node's left child. */
    public BinaryNode<T> getLeftChild()
    {
        return leftChild;
    } // end getLeftChild

    /** Sets this node’s left child to a given node.
     @param newLeftChild  A node that will be the left child. */
    public void setLeftChild(BinaryNode<T> newLeftChild)
    {
        leftChild = newLeftChild;
    } // end setLeftChild

    /** Detects whether this node has a left child.
     @return  True if the node has a left child. */
    public boolean hasLeftChild()
    {
        return leftChild != null;
    } // end hasLeftChild

    /** Retrieves the right child of this node.
     @return  A reference to this node's right child. */
    public BinaryNode<T> getRightChild()
    {
        return rightChild;
    } // end getRightChild

    /** Sets this node’s right child to a given node.
     @param newRightChild  A node that will be the right child. */
    public void setRightChild(BinaryNode<T> newRightChild)
    {
        rightChild = newRightChild;
    } // end setRightChild

    /** Detects whether this node has a right child.
     @return  True if the node has a right child. */
    public boolean hasRightChild()
    {
        return rightChild != null;
    } // end hasRightChild

    /** Detects whether this node is a leaf.
     @return  True if the node is a leaf. */
    public boolean isLeaf()
    {
        return (leftChild == null) && (rightChild == null);
    } // end isLeaf

    /** Counts the nodes in the subtree rooted at this node.
     @return  The number of nodes in the subtree rooted at this node. */
    public int getNumberOfNodes()
    {
        int leftNumber = 0;
        int rightNumber = 0;
        if (leftChild != null)
            leftNumber = leftChild.getNumberOfNodes();
        if (rightChild != null)
            rightNumber = rightChild.getNumberOfNodes();
        return 1 + leftNumber + rightNumber;
    } // end getNumberOfNodes

    /** Computes the height of the subtree rooted at this node.
     @return  The height of the subtree rooted at this node. */
    public int getHeight()
    {
        return getHeight(this); // Call private getHeight
    } // end getHeight

    /** Copies the subtree rooted at this node.
     @return  The root of a copy of the subtree rooted at this node. */
    public BinaryNode<T> copy()
    {
        BinaryNode<T> newRoot = new BinaryNode<>(data);
        if (leftChild != null)
            newRoot.setLeftChild(leftChild.copy());

        if (rightChild != null)
            newRoot.setRightChild(rightChild.copy());

        return newRoot;
    } // end copy

    private int getHeight(BinaryNode<T> node)
    {
        int height = 0;
        if (node != null)
            height = 1 + Math.max(getHeight(node.getLeftChild()),
                    getHeight(node.getRightChild()));
        return height;
    } // end getHeight

    public static class BinarySearchTree<T extends Comparable<? super T>>
            extends BinaryTree<T> implements SearchTreeInterface<T>
    {
        public BinarySearchTree()
        {
            super();
        } // end default constructor

        public BinarySearchTree(T rootEntry)
        {
            super();
            setRootNode(new BinaryNode<T>(rootEntry));
        } // end constructor

        // Disable setTree (see Segment 26.6)
        public void setTree(T rootData, BinaryTreeInterface<T> leftTree,
                            BinaryTreeInterface<T> rightTree)
        {
            throw new UnsupportedOperationException();
        } // end setTree

        public T getEntry(T anEntry)
        {
            return findEntry(getRootNode(), anEntry);
        } // end getEntry

        private T findEntry(BinaryNode<T> rootNode, T anEntry)
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

        public boolean contains(T entry)
        {
            return getEntry(entry) != null;
        } // end contains

        public T add(T newEntry)
        {
            T result = null;

            if (isEmpty())
                setRootNode(new BinaryNode<>(newEntry));
            else
                result = addEntry(getRootNode(), newEntry);

            return result;
        } // end add

        // Adds anEntry to the nonempty subtree rooted at rootNode.
        private T addEntry(BinaryNode<T> rootNode, T anEntry)
        {
            // Assertion: rootNode != null
            T result = null;
            int comparison = anEntry.compareTo(rootNode.getData());

            if (comparison == 0)
            {
                result = rootNode.getData();
                rootNode.setData(anEntry);
            }
            else if (comparison < 0)
            {
                if (rootNode.hasLeftChild())
                    result = addEntry(rootNode.getLeftChild(), anEntry);
                else
                    rootNode.setLeftChild(new BinaryNode<>(anEntry));
            }
            else
            {
                // Assertion: comparison > 0

                if (rootNode.hasRightChild())
                    result = addEntry(rootNode.getRightChild(), anEntry);
                else
                    rootNode.setRightChild(new BinaryNode<>(anEntry));
            } // end if

            return result;
        } // end addEntry

        public T remove(T anEntry)
        {
            ReturnObject oldEntry = new ReturnObject(null);
            BinaryNode<T> newRoot = removeEntry(getRootNode(), anEntry, oldEntry);
            setRootNode(newRoot);

            return oldEntry.get();
        } // end remove

        // Removes an entry from the tree rooted at a given node.
        // Parameters:
        //    rootNode A reference to the root of a tree.
        //    anEntry  The object to be removed.
        //    oldEntry An object whose data field is null.
        //    Returns: The root node of the resulting tree; if anEntry matches
        //             an entry in the tree, oldEntry's data field is the entry
        //             that was removed from the tree; otherwise it is null.
        private BinaryNode<T> removeEntry(BinaryNode<T> rootNode, T anEntry,
                                          ReturnObject oldEntry)
        {
            if (rootNode != null)
            {
                T rootData = rootNode.getData();
                int comparison = anEntry.compareTo(rootData);

                if (comparison == 0)       // anEntry == root entry
                {
                    oldEntry.set(rootData);
                    rootNode = removeFromRoot(rootNode);
                }
                else if (comparison < 0)   // anEntry < root entry
                {
                    BinaryNode<T> leftChild = rootNode.getLeftChild();
                    BinaryNode<T> subtreeRoot = removeEntry(leftChild, anEntry, oldEntry);
                    rootNode.setLeftChild(subtreeRoot);
                }
                else                       // anEntry > root entry
                {
                    BinaryNode<T> rightChild = rootNode.getRightChild();
                    // A different way of coding than for left child:
                    rootNode.setRightChild(removeEntry(rightChild, anEntry, oldEntry));
                } // end if
            } // end if

            return rootNode;
        } // end removeEntry

        // Removes the entry in a given root node of a subtree.
        // rootNode is the root node of the subtree.
        // Returns the root node of the revised subtree.
        private BinaryNode<T> removeFromRoot(BinaryNode<T> rootNode)
        {
            // Case 1: rootNode has two children
            if (rootNode.hasLeftChild() && rootNode.hasRightChild())
            {
                // Find node with largest entry in left subtree
                BinaryNode<T> leftSubtreeRoot = rootNode.getLeftChild();
                BinaryNode<T> largestNode = findLargest(leftSubtreeRoot);

                // Replace entry in root
                rootNode.setData(largestNode.getData());

                // Remove node with largest entry in left subtree
                rootNode.setLeftChild(removeLargest(leftSubtreeRoot));
            } // end if

            // Case 2: rootNode has at most one child
            else if (rootNode.hasRightChild())
                rootNode = rootNode.getRightChild();
            else
                rootNode = rootNode.getLeftChild();

            // Assertion: If rootNode was a leaf, it is now null

            return rootNode;
        } // end removeFromRoot

        // Finds the node containing the largest entry in a given tree.
        // rootNode is the root node of the tree.
        // Returns the node containing the largest entry in the tree.
        private BinaryNode<T> findLargest(BinaryNode<T> rootNode)
        {
            if (rootNode.hasRightChild())
                rootNode = findLargest(rootNode.getRightChild());

            return rootNode;
        } // end findLargest

        // Removes the node containing the largest entry in a given tree.
        // rootNode is the root node of the tree.
        // Returns the root node of the revised tree.
        private BinaryNode<T> removeLargest(BinaryNode<T> rootNode)
        {
            if (rootNode.hasRightChild())
            {
                BinaryNode<T> rightChild = rootNode.getRightChild();
                rightChild = removeLargest(rightChild);
                rootNode.setRightChild(rightChild);
            }
            else
                rootNode = rootNode.getLeftChild();

            return rootNode;
        } // end removeLargest

        private class ReturnObject
        {
            private T item;

            private ReturnObject(T entry)
            {
                item = entry;
            } // end constructor

            public T get()
            {
                return item;
            } // end get

            public void set(T entry)
            {
                item = entry;
            } // end set
        } // end ReturnObject
    } // end BinarySearchTree
} // end BinaryNode
