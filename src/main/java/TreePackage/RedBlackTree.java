package TreePackage;

import StackAndQueuePackage.LinkedQueue;
import StackAndQueuePackage.LinkedStack;
import StackAndQueuePackage.QueueInterface;
import StackAndQueuePackage.StackInterface;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Red Black Tree without Remove.
 *
 * I decided to not use inheritance because I needed a different kind of node for this class, and although perhaps
 * there was a way to make it work by having the node inherit from BinaryNode as well, I ran into issues with old
 * methods being fully compatible with this class. Since I felt short on time, I decided to make this its own class.
 * I wanted to recognize that this class did not have to be built from scratch completely, though. There are several
 * methods that work on this tree as well as any other binary tree. Aside from the add method, and the many methods that
 * support the add method, nearly all other methods are borrowed from BinaryTree.java, with the update that they use the
 * RedBlackNode node.
 *
 * 
 * I used this guide to help me build this tree: https://www.happycoders.eu/algorithms/red-black-tree-java/
 * (I really enjoyed making this tree!)
 * @param <T> generic of type T
 */
public class RedBlackTree<T extends Comparable<? super T>> implements SearchTreeInterface<T>, TreeIteratorInterface<T>,
        Iterable<T> {
    /** root of the RedBlackTree */
    private RedBlackNode<T> root;


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
     * @param rootData information to be stored in the initial root of the tree
     */
    public RedBlackTree(T rootData) {
        root = new RedBlackNode<T>(rootData, null, null, null);
        root.setBlack();
    }


    // --------------------- // --- SEARCH/ADD/DELETE --- // --------------------- //

    /**
     * {@inheritDoc}
     * Time Complexity: O(log n) (halving the tree everytime we call the search again)
     * Red-Black Trees can guarantee O(log n) time because their properties maintain a balance rule where no single
     * path from node to root can be twice as long as the shortest path. If we imagine a perfect tree of n nodes, the
     * shortest path in such a tree would be log(n), therefore the longest path must be no longer than 2*log(n), which
     * is an O(log n) time complexity.
     */
    @Override
    public boolean contains(T anEntry) {
        return getEntry(anEntry) != null;
    }


    /**
     * {@inheritDoc}
     * Time Complexity: O(log n) (same reason as contains)
     */
    @Override
    public T getEntry(T anEntry) {
        return findEntry(getRootNode(), anEntry);
    } // end getEntry


    /**
     * private helper function of getEntry
     *
     * @param rootNode root of the subtree we are searching
     * @param anEntry data value we are trying to locate
     * @return Either the object that was found in the tree or null if no such object exists.
     *
     */
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
     * {@inheritDoc}
     *
     * Time Complexity: O(log n)
     * When we add an entry, we need to do search similar to the contains method to find where it goes in the
     * tree, which is an O(log n) operation. Then adjustments to the tree to rebalance involve either changing the
     * color of nodes recursively up the tree, which are O(1) operations in O(logn) time maximu, or at some point along
     * the way of recoloring up the tree, we may need to stop and do a rotation and a recolor. This rotation also breaks
     * the loop, and the rotation itself is also comprised of only a few O(1) operations. Therefore, add is a O(log n)
     * time complexity operation.
     */
    @Override
    public T add(T anEntry) {
        if (isEmpty()) {
            root = new RedBlackNode<>(anEntry, null, null, null);
            root.setBlack(); //Most R-B Trees require root to be black
            return null;
        }

        RedBlackNode<T> node = root;
        T nodeData;
        RedBlackNode<T> parent = null;

        // Traverse to find spot to put new entry
        while (node != null) {
            parent = node;
            nodeData = node.getData();
            if (anEntry.compareTo(nodeData) < 0) { // anEntry is smaller than current node
                node = node.getLeftChild();
            } else if (anEntry.compareTo(nodeData) > 0 ) { // anEntry is larger than current node
                node = node.getRightChild();
            } else { // anEntry is the same as current node
                RedBlackNode<T> temp = node; // save old node
                // create new entry, setting the children and parents to temp's old children and parents.
                RedBlackNode<T> newEntry = createDuplicateNode(anEntry, temp);

                // Set children's parents to be the new node
                if ( temp.getLeftChild() != null) {
                    temp.getLeftChild().setParent(newEntry);
                }
                if ( temp.getRightChild() != null) {
                    temp.getRightChild().setParent(newEntry);
                }

                // Set temp's parent to point at new child and vice versa.
                replaceParentsChild(temp.getParent(), temp, newEntry);

                //Return old entry
                return temp.getData();
            }
        }

        //Insert new entry
        T parentData = parent.getData();
        RedBlackNode<T> newEntry = new RedBlackNode<>(anEntry, parent, null, null);

        if (anEntry.compareTo(parentData) < 0) {
            parent.setLeftChild(newEntry);
        } else {
            parent.setRightChild(newEntry);
        }

        //After we add a new node, the properties of R-B trees may be disrupted, so we need to fix them.
        fixRedBlackPropertiesAfterAdd(newEntry);

        return null; // if we get here, a new entry has been added
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public T remove(T anEntry) {
        throw new UnsupportedOperationException();
    }
    // --------------------- // --- SEARCH/ADD/DELETE END --- // --------------------- //


    // -------------------- // --- HELPER FUNCTIONS FOR ADD --- // -------------------- //

    /**
     * private helper function for add() for code clarity
     *
     * @param anEntry data for entry you want to duplicate
     * @param temp node that you want to duplicate
     * @param <T> generic of type T
     * @return the duplicate node
     */
    private static <T extends Comparable<? super T>> RedBlackNode<T> createDuplicateNode(T anEntry, RedBlackNode<T> temp) {
        RedBlackNode<T> newEntry = new RedBlackNode<>(anEntry, temp.getParent(), temp.getLeftChild(), temp.getRightChild());

        /* Set color to original entry's color, since our BST was a valid R-B Tree before insert, if we are inserting
        into a place that is the same as it. */
        boolean black = temp.isBlack();
        if (black) { // new entry is already red, so change color if it's black
            newEntry.setBlack();
        }
        return newEntry;
    }


    /**
     * Method fix any rule violations that might have occurred after a new node is added.
     *
     * @param node that we are currently inspecting for and to fix rule violations
     */
    private void fixRedBlackPropertiesAfterAdd(RedBlackNode<T> node) {
        RedBlackNode<T> parent = node.getParent();

        // This part helps fix the property: The Root must be black.
        if (parent == null) { // The only node without a parent is the root.
            node.setBlack();
            return; // if this is the violation there is nothing more to check.
        }

        /* For all future cases, we only need to perform recoloring if the parent is RED
         If parent is black, this will not cause any issues as all new adds are initially red nodes
         This means that we won't violate the property: No consecutive red nodes
         Adding a red also means we won't violate the property: all paths root to NIL leaf have the same number
         of black nodes.

         This means if our parent is red then our parent is red then there is a violation we must fix:
         No consecutive red nodes */
        if (!parent.isBlack()) {
            RedBlackNode<T> grandparent = parent.getParent();
            RedBlackNode<T> aunt = getAunt(parent);

            // Situation: Aunt is Red -> Recolor the parent, grandparent and aunt
            if (aunt != null && !aunt.isBlack()) {
                aunt.setBlack();
                parent.setBlack();
                grandparent.setRed();

                /* This recolor  could cause different property violations up the tree.
                 Recursively call this function to see if properties need to be adjusted up the trees. This will
                 not cause infinite recursion because our tree is getting simpler since we are moving upwards */
                fixRedBlackPropertiesAfterAdd(grandparent);
            }

            // Situation: Aunt is Black -> Do a Rotation and a recolor.
            /* If the node in question is in the left-left or right-right subtree then we will do a single rotation
               and swap the colors of the parent and grandparent of that node. If the node is in the left-right
               subtree or the right-left subtree then we will do a double rotation and swap the color of the
               node in question (which becomes the parent node by the second rotation) and the grandparent. */
            else if (parent == grandparent.getLeftChild()) {
                //Violating node is in left-right subtree, requiring a left-right rotation
                if (node == parent.getRightChild()) {
                    rotateLeft(parent); // shifts the imbalance to be left-left
                    parent = node; // node is now the parent of the subtree
                    // Right rotation is done after we leave this if statement.
                }

                // Node in question (was always or now is) in left-left subtree, requiring a right rotation.
                rotateRight(grandparent);

                // Recolor parent and grandparent.
                parent.setBlack();
                grandparent.setRed();
            }

            else {
                // Violating node is in right-left subtree requiring a right-left rotation
                if (node == parent.getLeftChild()) {
                    rotateRight(parent); //shifts the imbalance to be right-right
                    parent = node; // node is now the parent of the subtree
                    // Left rotation is done after we leave this if statement.
                }

                // Node in question (was always or now is) in right-right subtree, requiring a left rotation.
                rotateLeft(grandparent);

                // Recolor parent and grandparent.
                parent.setBlack();
                grandparent.setRed();
            }
        }
    }


    /**
     * private helper function of fixRedBlackPropertiesAfterAdd.
     * The beauty of Red-Black Trees is that they figure out height balancing not through recalculating the height
     * of nodes, but by the color of particular nodes. The aunt node of a new node being added is particularly
     * helpful in deciding what actions need to be taken to fix any rule violations and rebalance the tree.
     * The aunt node is the sibling node of a parent node. (Also sometimes called uncle nodes).
     *
     * @param parent parent of the node in question
     * @return the aunt of the node in question, or the sibling of the parent parameter
     */
    private RedBlackNode<T> getAunt(RedBlackNode<T> parent) {
        RedBlackNode<T> grandparent = parent.getParent();
        if (grandparent.getLeftChild() == parent) { // need to figure out which side is parent and which side is aunt
            return grandparent.getRightChild();
        } else if (grandparent.getRightChild() == parent) {
            return grandparent.getLeftChild();
        } else { // just in case some wires get crossed...
            throw new IllegalStateException("Parent is not a child of its grandparent.");
        }
    }


    // ----------------------- // --- ROTATION METHODS --- // ----------------------- //

    /**
     * Corrects an imbalance on the left side of a left subtree.
     *
     * @param node node that we need to rotate to fix the imbalance
     */
    private void rotateRight(RedBlackNode<T> node) {
        RedBlackNode<T> parent = node.getParent();

        // Point at the node's left child. This will rotate up and become the new root of the subtree at method's end.
        RedBlackNode<T> leftChild = node.getLeftChild();

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


    /**
     * Corrects an imbalance on the right side of a right subtree.
     *
     * @param node node that we need to rotate to fix the imbalance
     */
    private void rotateLeft(RedBlackNode<T> node) {
        RedBlackNode<T> parent = node.getParent();

        // Point at the node's right child. This will rotate up and become the new root of the subtree.
        RedBlackNode<T> rightChild = node.getRightChild();

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
     *
     * @param parent of the subtree
     * @param oldChild the old root of the subtree, which is the old child of the parent
     * @param newChild the new root of the subtree, which is the new child of the parent
     */
    private void replaceParentsChild(RedBlackNode<T> parent, RedBlackNode<T> oldChild, RedBlackNode<T> newChild) {
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
    // ----------------------- // --- END ROTATION METHODS --- // ----------------------- //

    // ------------------- // --- END HELPER FUNCTIONS FOR ADD --- // ------------------- //

    /**
     * Creates an iterator that traverses all entries using a preorder traversal.
     * Preorder traversal visits parents before children.
     *
     * @return  an iterator with a preorder iteration
     *
     */
    @Override
    public Iterator<T> getPreorderIterator() {
        return new PreorderIterator();
    }

    /**
     * Creates an iterator that traverses all entries using a postorder traversal.
     * Postorder traversal visits children before parents
     *
     * @return  an iterator with a level-order iteration
     */
    @Override
    public Iterator<T> getPostorderIterator() {
        return new PostorderIterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<T> getInorderIterator() {
        return new InorderIterator();
    }


    /**
     * Creates an iterator that traverses all entries using a level order traversal.
     * Level order traversal visits all nodes on a level before going to the next.
     *
     * @return  an iterator with a level-order iteration
     */
    @Override
    public Iterator<T> getLevelOrderIterator() {
        return new LevelOrderIterator();
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


    /**
     * Retrieves the data stored in the root node.
     * @return data stored in root node
     */
    @Override
    public T getRootData() {
        if (isEmpty())
            throw new EmptyTreeException();
        else
            return root.getData();
    }


    /**
     * Returns the root node of the tree.
     *
     * @return the root node of the tree
     */
    public RedBlackNode<T> getRootNode() {
        return root;
    }


    /**
     * Returns the height of the tree (including root)
     * @return height of the tree (including root)
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
     * Returns the number of nodes in the tree
     * @return number of nodes in the tree
     */
    @Override
    public int getNumberOfNodes() {
        int numberOfNodes = 0;
        if (root != null)
            numberOfNodes = root.getNumberOfNodes();
        return numberOfNodes;
    }


    /**
     * Detects whether the tree is empty (contains no nodes)
     * @return true if tree is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }


    /**
     * clears all entries in the tree
     */
    @Override
    public void clear() {
        root = null;
    }


    /**
     * Creates an Iterator that uses an inorder traversal
     */
    private class InorderIterator implements Iterator<T> {
        /** Holds the nodes as we traverse down a tree and pops them in correct order when we come back up. */
        private StackInterface<RedBlackNode<T>> nodeStack;
        /** The current node in the iteration. */
        private RedBlackNode<T> currentNode;


        /** Constructor */
        public InorderIterator() {
            nodeStack = new LinkedStack<>();
            currentNode = root;
        }


        /**
         * Detects if there is another node in the iteration.
         * @return true if there is another node in the iteration, false otherwise
         */
        public boolean hasNext() {
            return !nodeStack.isEmpty() || (currentNode != null);
        }


        /**
         * Returns the next node in the iteration.
         * @return the next node in the iteration
         * @throws NoSuchElementException if there are no more nodes left in the iteration
         */
        public T next() {
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
        }


        /**
         * Remove method not supported for this tree.
         */
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    } // end InorderIterator


    /**
     * Creates an Iterator that uses a preorder traversal
     */
    private class PreorderIterator implements Iterator<T> {
        /** Holds the nodes that we do not need to pop yet as we traverse the tree */
        private StackInterface<RedBlackNode<T>> nodeStack;
        // note! We do not need a variable to keep track of the current Node! More on this below!

        /** Constructor */
        public PreorderIterator()
        {
            nodeStack = new LinkedStack<>();
            if (root != null)
                nodeStack.push(root);
        } // end default constructor

        /**
         * Detects if there is another node in the iteration.
         * @return true if there is another node in the iteration, false otherwise
         */
        public boolean hasNext() {
            return !nodeStack.isEmpty();
        } // end hasNext

        /**
         * Returns the next node in the iteration.
         *
         * @return the next node in the iteration
         * @throws NoSuchElementException if there are no more nodes left in the iteration
         */
        public T next() {
            RedBlackNode<T> nextNode;

            if (hasNext()) {
                // This is why we don't need a variable to keep track of the current node!
                // We pop the nodes almost immediately because in a preorder traversal we return a node
                // as soon as we visit it.
                nextNode = nodeStack.pop();
                RedBlackNode<T> leftChild = nextNode.getLeftChild();
                RedBlackNode<T> rightChild = nextNode.getRightChild();

                // This is the reason we need the stack at all!
                // Push into stack in reverse order of recursive calls
                // We need to pop children left children before right children so right children.
                // This means right children need to go on the stack first.
                if (rightChild != null)
                    nodeStack.push(rightChild);

                if (leftChild != null)
                    nodeStack.push(leftChild);
            }
            else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        } // end next


        /**
         * Remove method not supported for this tree.
         */
        public void remove()
        {
            throw new UnsupportedOperationException();
        } // end remove
    } // end PreorderIterator

    /**
     * Creates an Iterator that uses a postorder traversal
     */
    private class PostorderIterator implements Iterator<T> {
        /** Holds the nodes that we do not need to pop yet as we traverse the tree */
        private StackInterface<RedBlackNode<T>> nodeStack;
        /** The current node in the iteration. */
        private RedBlackNode<T> currentNode;


        /** Constructor */
        public PostorderIterator()
        {
            nodeStack = new LinkedStack<>();
            currentNode = root;
        } // end default constructor


        /**
         * Detects if there is another node in the iteration.
         * @return true if there is another node in the iteration, false otherwise
         */
        public boolean hasNext()
        {
            return !nodeStack.isEmpty() || (currentNode != null);
        } // end hasNext


        /**
         * Returns the next node in the iteration.
         *
         * @return the next node in the iteration
         * @throws NoSuchElementException if there are no more nodes left in the iteration
         */
        public T next()
        {
            RedBlackNode<T> leftChild, rightChild, nextNode = null;

            // Find leftmost leaf
            // Postorder is left-right-root, we visit children before parents, so we need to stack up all the nodes
            // we find until we find the leftmost leaf, then we go to the right child, and add it to the stack if
            // its a leaf, or else traverse down that subtree in a similar fashion as before.
            while (currentNode != null)
            {
                nodeStack.push(currentNode);
                leftChild = currentNode.getLeftChild();
                if (leftChild == null)
                    currentNode = currentNode.getRightChild();
                else
                    currentNode = leftChild;
            } // end while

            // Stack is not empty either because we just pushed a node, or
            // it wasn't empty initially since hasNext() is true.
            // But Iterator specifies an exception for next() in case
            // hasNext() is false.

            if (!nodeStack.isEmpty())
            {
                // if we have exited the method for, we are at the bottom most leaf for this subtree
                nextNode = nodeStack.pop();
                // nextNode != null since stack was not empty before pop

                RedBlackNode<T> parent = null;
                // pop this bottom most leaf, then check if it has a right leaf, we set current node to null
                // if it doesn't, so it skips the while loop above next time and just pops another node
                // if there isn't a right child, it's time to head back up the tree.
                if (!nodeStack.isEmpty())
                {
                    parent = nodeStack.peek();
                    if (nextNode == parent.getLeftChild())
                        currentNode = parent.getRightChild();
                    else
                        currentNode = null;
                }
                else
                    currentNode = null;
            }
            else
            {
                throw new NoSuchElementException();
            } // end if

            return nextNode.getData();
        } // end next


        /**
         * Remove method not supported for this tree.
         */
        public void remove()
        {
            throw new UnsupportedOperationException();
        } // end remove
    } // end PostorderIterator


    /**
     * Creates an Iterator that uses a level-order traversal
     */
    private class LevelOrderIterator implements Iterator<T> {
        /** Holds the nodes that we do not need to pop yet as we traverse the tree
         * Because level-order traversal works a lot differently than the other traversals, it requires
         * a queue rather than a stack. (Since it moves horizontally rather than veritcally).*/
        private QueueInterface<RedBlackNode<T>> nodeQueue;

        /** constructor */
        public LevelOrderIterator()
        {
            nodeQueue = new LinkedQueue<>();
            if (root != null)
                nodeQueue.enqueue(root); // if it has a node, put the first one in the queue
        } // end default constructor

        /**
         * Detects if there is another node in the iteration.
         * @return true if there is another node in the iteration, false otherwise
         */
        public boolean hasNext()
        {
            return !nodeQueue.isEmpty();
        } // end hasNext


        /**
         * Returns the next node in the iteration.
         * (This method is simple and satisfying!)
         *
         * @return the next node in the iteration
         * @throws NoSuchElementException if there are no more nodes left in the iteration
         */
        public T next() {
            RedBlackNode<T> nextNode;

            // We continue to do this as long as we still have nodes in our stack!
            if (hasNext()) {
                //Remember: if our tree isn't empty, our root gets enqueued in the constructor.
                //So if we've made it here, there will always be something to dequeue.
                nextNode = nodeQueue.dequeue();

                //for a level order traversal, these are the next nodes we want to show anyway, since the nodes
                //on the next level, since we do left before right, there are always the left most nodes on a level
                //we haven't added to a queue yet.
                RedBlackNode<T> leftChild = nextNode.getLeftChild();
                RedBlackNode<T> rightChild = nextNode.getRightChild();

                // Add to queue in order of recursive calls
                // When we get to the leaves nothing gets added to the queue, representing being on the final
                // level for that subtree
                if (leftChild != null)
                    nodeQueue.enqueue(leftChild);

                if (rightChild != null)
                    nodeQueue.enqueue(rightChild);
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        } // end next


        /**
         * Remove method not supported for this tree.
         */
        public void remove()
        {
            throw new UnsupportedOperationException();
        } // end remove
    }
}
