// --== CS400 Fall 2023 File Header Information ==--
// Name: Lucas Cheng
// Email: lcheng77@wisc.edu
// Group: A32
// TA: Grant Waldow
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class IterableMultiKeyRBT<T extends Comparable<T>> extends RedBlackTree<KeyListInterface<T>> implements IterableMultiKeySortedCollectionInterface<T> {
    private Comparable<T> iterationStartPoint; // starting node of iteration
    private int numKeys; // number of KeyLists in the tree

    /**
     * Tests value of NumKeys with at various stages after inserting Keys, specifically with an empty tree, a tree with
     * 3 different nodes with 1 value in each, 3 different nodes with 2 values in each, and 5 different nodes
     */
    @Test
    public void testNumKeys() {
        IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();
        // check new tree has size 0
        assertEquals(0, tree.numKeys, "new tree should have size 0 but was " + tree.numKeys);

        tree.insertSingleKey(1);
        tree.insertSingleKey(3);
        tree.insertSingleKey(5);

        // check tree has size 3 after 3 different nodes made
        assertEquals(3, tree.numKeys, "inserted 3 different keys, but size was" + tree.numKeys);

        tree.insertSingleKey(1);
        tree.insertSingleKey(3);
        tree.insertSingleKey(5);

        // check tree still has size 3 after inserting values that have an existing KeyList
        assertEquals(3, tree.size, "inserted 3 existing values, size should be 3" +
                " but size was" + tree.numKeys);

        tree.insertSingleKey(6);
        tree.insertSingleKey(7);

        // make sure can still make new KeyLists and numKeys updates
        assertEquals(5, tree.size, "inserted 5 different keys, but size was" + tree.numKeys);
    }

    /**
     * tests the Iterator function and if it returns an in-order traversal of the tree using an RBT of integers
     */
    @Test
    public void testIterator() {
        IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();
        tree.insertSingleKey(1);
        tree.insertSingleKey(2);
        tree.insertSingleKey(3);
        tree.insertSingleKey(4);
        tree.insertSingleKey(5);
        tree.insertSingleKey(6);

        //make iterator
        Iterator<Integer> iterator = tree.iterator();

        int count = 1; // incremented as iterator moves to next
        while (iterator.hasNext()) {
            Integer element = iterator.next();
            assertEquals(count, element, "in-order traversal failed, element should be " + count + " but was" +
                    element);
            count++;
        }
    }

    /**
     * checks if setStartPoint correctly changes the start point of the iterator using an RBT of integers
     */
    @Test
    public void testSetStartPoint() {
        IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();
        tree.insertSingleKey(1);
        tree.insertSingleKey(4);
        tree.insertSingleKey(5);
        tree.insertSingleKey(3);
        tree.insertSingleKey(6);
        tree.insertSingleKey(2);

        // set startpoint and make iterator
        tree.setIterationStartPoint(3);
        Iterator<Integer> iterator = tree.iterator();

        int count = 3; // incremented as iterator moves to next
        while (iterator.hasNext()) {
            Integer element = iterator.next();
            assertEquals(count, element, "in-order traversal failed, element should be " + count +
                    " but was" + element);
            count++;
        }
    }

    /**
     * Inserts value into tree that can store multiple objects per key by keeping
     * lists of objects in each node of the tree.
     *
     * @param key object to insert
     * @return true if a new node was inserted, false if the key was added into an existing node
     */
    @Override
    public boolean insertSingleKey(T key) {
        KeyList<T> newKeyList = new KeyList<>(key); // creates new KeyList with key of type T
        Node<KeyListInterface<T>> findNode = this.findNode(newKeyList); // attempt to find a node with matching key
        if (findNode != null) {// found existing node with key
            findNode.data.addKey(key); // add key to existing node
            this.numKeys++;
            return false;
        }
        else { // node with key does not yet exist
            this.insert(newKeyList); //insert KeyList as new Node into RBT
            this.numKeys++;
            return true;
        }
    }

    /**
     * iterates through an RBT based on starting point to return a stack that will be iterated through.
     *      if starting point is null, pushed all nodes along left path of tree to stack, else searches tree
     *      for starting point while adding any stepped into node with value larger than start point.
     *
     * @return stack - starting stack for iteration
     */
    protected Stack<Node<KeyListInterface<T>>> getStartStack() {
        Stack<Node<KeyListInterface<T>>> stack = new Stack<>();
        Node<KeyListInterface<T>> curr = this.root;
        if (curr == null) return stack; // no tree to iterate through

        if (this.iterationStartPoint == null) { //no start point, go from root to smallest
            stack.push(curr); // add root to stack
            while (curr.down[0] != null) { // keep adding left children to stack
                stack.push(curr.down[0]);
                curr = curr.down[0];
            }
        }
        else { // start point exists, search for node
            while (curr != null) { // keep looking for startPoint until not possible
                if (this.iterationStartPoint.compareTo(curr.data.iterator().next()) == 0) {
                    stack.push(curr); // found startPoint, push it to stack
                    curr = null; // search done, let iterator handle the rest
                }
                else if (this.iterationStartPoint.compareTo(curr.data.iterator().next()) > 0) { // startpoint is larger
                    curr = curr.down[1];
                }
                else { // start point is smaller
                    stack.push(curr); // push the Node to stack since it is larger
                    curr = curr.down[0]; // move curr to left child
                }
            }
        }
        return stack;
    }

    /**
     * @return the number of values in the tree.
     */
    @Override
    public int numKeys() {
        return this.numKeys;
    }

    /**
     * Returns an iterator that does an in-order iteration over the tree.
     */
    @Override
    public Iterator<T> iterator() {
        Iterator<T> iterator = new Iterator<T>() {
            Stack<Node<KeyListInterface<T>>> stack = getStartStack();
            Iterator<T> nodeIterator; // store for the iterator to account for dupes

            @Override
            public boolean hasNext() {
                return !stack.isEmpty() || nodeIterator.hasNext(); // next if stack not empty or nodeIterator has next
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException("no more nodes to iterate through");

                if (nodeIterator != null && nodeIterator.hasNext()) { // dupe exists in saved iterator
                    return nodeIterator.next(); // return next of iterator until it runs out of dupes
                }

                Node<KeyListInterface<T>> curr = stack.pop();
                nodeIterator = curr.data.iterator(); // initialize new nodeIterator for this newly entered node

                if (curr.down[1] != null) {
                    stack.push(curr.down[1]); // add right child to the stack
                    curr = curr.down[1];
                    while (curr.down[0] != null) { // push all of right child's left children to stack
                        stack.push(curr.down[0]);
                        curr = curr.down[0];
                    }
                }

                return nodeIterator.next(); // return first node from saved Iterator
            }
        };
        return iterator;
    }

    /**
     * Sets the starting point for iterations. Future iterations will start at the
     * starting point or the key closest to it in the tree. This setting is remembered
     * until it is reset. Passing in null disables the starting point.
     *
     * @param startPoint the start point to set for iterations
     */
    @Override
    public void setIterationStartPoint(Comparable<T> startPoint) {
        this.iterationStartPoint = startPoint;
    }

    /**
     * Removes all keys from the tree.
     */
    public void clear() {
        super.clear(); // calls BST implementation of clear
        this.numKeys = 0;
    }
}