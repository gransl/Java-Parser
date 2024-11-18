package TreePackage;

import StackAndQueuePackage.LinkedStack;
import StackAndQueuePackage.StackInterface;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Red Black Tree without deletion
 * @param <T> generic of type T
 */
public class RedBlackTree<T extends Comparable<? super T>> implements SearchTreeInterface<T>, Iterable<T>{
    /** root of the RedBlackTree*/
    RedBlackNode<T> root;


    /**
     * Empty Constructor, creates an empty tree
     */
    public RedBlackTree() {
        root = null;
    }


    /**
     * Partial Constructor - creates a tree with a root note with parameter data
     * This class not allow trees to be constructed where user chooses parent, left, or right children.
     * New nodes must be added via add method, so they are added in correct place with correct red/black color.
     * @param rootData
     */
    public RedBlackTree(T rootData) {
        setRootNode(new RedBlackNode<T>(rootData, null, null, null));
        root.setBlack();
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
    public T getEntry(T anEntry) {
        return findEntry(getRootNode(), anEntry);
    } // end getEntry


    private T findEntry(RedBlackNode<T> rootNode, T anEntry) {
        T result = null;

        if (rootNode != null) {
            T rootEntry = rootNode.getData();

            if (anEntry.equals(rootEntry))
                result = rootEntry;
            else if (anEntry.compareTo(rootEntry) < 0)
                result = findEntry(rootNode.getLeftChild(), anEntry);
            else
                result = findEntry(rootNode.getRightChild(), anEntry);
        }

        return result;
    }


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
            root.setBlack(); //Most R-B Trees require root to be black
            return null;
        }

        RedBlackNode<T> node = root;
        T nodeData;
        RedBlackNode<T> parent = null;

        //Traverse to find spot to put new entry
        while (node != null) {
            parent = node;
            nodeData = node.getData();
            if (anEntry.compareTo(nodeData) < 0) { // anEntry smaller than current node
                node = node.getLeftChild();
            } else if (anEntry.compareTo(nodeData) > 0 ) { // anEntry larger than current node
                node = node.getRightChild();
            } else { //anEntry the same as current node
                RedBlackNode<T> temp = node; // save old node
                // create new entry, setting the children and parents to temp's old children and parents.
                RedBlackNode newEntry = new RedBlackNode(anEntry, temp.getParent(),
                                                            temp.getLeftChild(), temp.getRightChild());

                /* Set color to original entry's color, since our BST was a valid R-B Tree before insert,
                if we are inserting into a place that is the same as it*/
                boolean black = temp.isBlack();
                if (black) { // new entry is already red, so change color if it's black
                    newEntry.setBlack();
                }

                //set children's parents to be the new Node.
                if ( temp.getLeftChild() != null) {
                    temp.getLeftChild().setParent(newEntry);
                }
                if ( temp.getRightChild() != null) {
                    temp.getRightChild().setParent(newEntry);
                }

                //set temp's parent to point at new child and vice versa.
                replaceParentsChild(temp.getParent(), temp, newEntry);

                //return old entry
                return temp.getData();
            }
        }

        //Insert new entry
        T parentData = parent.getData();
        RedBlackNode newEntry = new RedBlackNode(anEntry, parent, null, null);

        if (anEntry.compareTo(parentData) < 0) {
            parent.setLeftChild(newEntry);
        } else {
            parent.setRightChild(newEntry);
        }

        //After we add a new node, the properties of R-B trees may be disrupted, so we need to fix them.
        fixRedBlackPropertiesAfterAdd(newEntry);

        return null;
    }


    /**
     *
     * Property 1: All Nodes must be Red or Black
     * @param node
     */
    private void fixRedBlackPropertiesAfterAdd(RedBlackNode<T> node) {
        RedBlackNode parent = node.getParent();

        // Property 2: The Root must be black. (Incidentally, this is an optional rule, not all R-B enforce)
        if (parent == null) { // The only node without a parent is the root.
            node.setBlack();
            return;
        }

        // For all future cases, we only need to perform recoloring if the parent is RED
        if (!parent.isBlack()) {
            RedBlackNode<T> grandparent = parent.getParent();
            RedBlackNode<T> aunt = getAunt(parent);

            // Situation: Aunt is Red -> Recolor the parent, grandparent and aunt
            // I believe this usually occurs when there are two consecutive reds
            if (aunt != null && !aunt.isBlack()) {
                aunt.setBlack();
                parent.setBlack();
                grandparent.setRed();

                /* Recursively call to see if properties need to be adjusted up the trees. This will not cause
                 infinite recursion because our tree is getting simpler since we are moving upwards */
                fixRedBlackPropertiesAfterAdd(grandparent);
            }

            // Property 4: Every path from root to NIL node must contain the same number of black nodes (black height)
            // parent is left child of grandparent and aunt is black
            else if (parent == grandparent.getLeftChild()) {
                //Violating node is in left-right subtree, requiring a left-right rotation
                if (node == parent.getRightChild()) {
                    rotateLeft(parent);
                    parent = node;
                    // right rotation is done after we leave this if statement
                }

                //Violating node (was always or now is) in left-left subtree, requiring a right rotation.
                rotateRight(grandparent);

                //recolor original parent and grandparent;
                parent.setBlack();
                grandparent.setRed();
            }

            else {
                //Violating node is in right-left subtree requiring a right-left rotation
                if (node == parent.getLeftChild()) {
                    rotateRight(parent);
                    parent = node;
                    // left rotation is done after we leave this statement
                }

                //Violating node (was always or now is) in right-right subtree, requiring a left rotation.
                rotateLeft(grandparent);

                //recolor original parent and grandparent;
                parent.setBlack();
                grandparent.setRed();
            }
        }
    }

    private RedBlackNode<T> getAunt(RedBlackNode<T> parent) {
        RedBlackNode<T> grandparent = parent.getParent();
        if (grandparent.getLeftChild() == parent) {
            return grandparent.getRightChild();
        } else if (grandparent.getRightChild() == parent) {
            return grandparent.getLeftChild();
        } else {
            throw new IllegalStateException("Parent is not a child of its grandparent.");
        }
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
        return new InorderIterator();
    }


    /**
     * Creates an iterator that traverses all entries in this tree.
     * (Identical to InorderIterator, I need this method header to satisfy the requirements of the Iterable
     * interface, which I would like to use so my trees can use for each loops).
     *
     * @return An iterator that provides sequential and ordered access to the entries in the tree.
     *
     */
    public Iterator<T> iterator() {
        return new InorderIterator();
    }


    @Override
    public T getRootData() {
        if (isEmpty())
            throw new EmptyTreeException();
        else
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
    protected RedBlackNode<T> getRootNode() {
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
        return height;
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


    private class InorderIterator implements Iterator<T> {
        private StackInterface<RedBlackNode<T>> nodeStack;
        private RedBlackNode<T> currentNode;

        public InorderIterator()
        {
            nodeStack = new LinkedStack<>();
            currentNode = root;
        } // end default constructor

        public boolean hasNext()
        {
            return !nodeStack.isEmpty() || (currentNode != null);
        } // end hasNext

        public T next()
        {
            RedBlackNode<T> nextNode = null;

            // Find leftmost node with no left child
            while (currentNode != null)
            {
                nodeStack.push(currentNode);
                currentNode = currentNode.getLeftChild();
            } // end while

            // Get leftmost node, then move to its right subtree
            if (!nodeStack.isEmpty())
            {
                nextNode = nodeStack.pop();
                // Assertion: nextNode != null, since nodeStack was not empty
                // before the pop
                currentNode = nextNode.getRightChild();
            }
            else
                throw new NoSuchElementException();

            return nextNode.getData();
        } // end next

        public void remove()
        {
            throw new UnsupportedOperationException();
        } // end remove
    } // end InorderIterator
}
