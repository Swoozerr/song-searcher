// --== CS400 Fall 2023 File Header Information ==--
// Name: Lucas Cheng
// Email: lcheng77@wisc.edu
// Group: A32
// TA: Grant Waldow
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> implements SortedCollectionInterface<T> {

    /**
     * Provided RBT Node Class
     */
    protected static class RBTNode<T> extends Node<T> {
        public int blackHeight = 0;
        public RBTNode(T data) { super(data); }
        public RBTNode<T> getUp() { return (RBTNode<T>)this.up; }
        public RBTNode<T> getDownLeft() { return (RBTNode<T>)this.down[0]; }
        public RBTNode<T> getDownRight() { return (RBTNode<T>)this.down[1]; }
    }

    /**
     * insert method uses BST insert then makes RBT corrections as needed
     *
     * @param data - data of node
     * @return - true if insertion was succesful, else false
     * @throws NullPointerException - if null val was attempted to be inserted
     */
    @Override
    public boolean insert(T data) throws NullPointerException {
        if (data == null) throw new NullPointerException("Can't insert null value");
        RBTNode<T> node = new RBTNode<>(data);
        boolean returnVal = this.insertHelper(node);
        if (!returnVal) return false; // node couldn't be inserted
        enforceRBTreePropertiesAfterInsert(node); // Fix red node violations
        ((RBTNode<T>) this.root).blackHeight = 1; // make root black
        return true; // RBT should be proper now
    }

    /**
     * when called, fixes red black tree properties after an insertion if needed
     *
     * @param node to be fixed
     */
    protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> node) {
        // Case 1: root was inserted, do nothing since root is changed to black outside of this func
        if (node.getUp() == null) return;

        // Case 2: if parent of this inserted red node is black, exit because satisfies RBT properties
        if (node.getUp().blackHeight == 1) return;

        // get parent/gp pointers
        RBTNode<T> parent = node.getUp();
        RBTNode<T> grandparent = node.getUp().getUp();
        // Check parent node's sibling (aunt) to see if black or red
        // see if parent is left or right child for aunt
        int auntColor;
        if (parent.data.compareTo(grandparent.data) > 0) { // parent larger than gp, parent is right child
            if (grandparent.getDownLeft() == null) auntColor = 1;
            else auntColor = grandparent.getDownLeft().blackHeight;
        }
        else { //parent is less than gp, parent is left child (note: equal was accounted for before this func)
            if (grandparent.getDownRight() == null) auntColor = 1;
            else auntColor = grandparent.getDownRight().blackHeight;
        }

        // Case 3: aunt is black
        if (auntColor == 1) {
            // check first if violating nodes are on same side, if not rotate node with parent first
            if (parent.data.compareTo(grandparent.data) != node.data.compareTo(parent.data)) {
                // not same relative relation, so not same side violation
                // left or right rotation already handled in rotate function
                rotate(node, parent);
                rotate(node, grandparent);
                // swap colors
                node.blackHeight = 1;
                parent.blackHeight = 0;
                grandparent.blackHeight = 0;
            } else { // nodes are on same side already
                rotate(parent, grandparent);
                // swap colors
                parent.blackHeight = 1;
                node.blackHeight = 0;
                grandparent.blackHeight = 0;
            }
        }

        // Case 4: aunt is red
        else if (auntColor == 0) {
            // swap colors of grandparent with children, parent or aunt can't be null atp
            grandparent.blackHeight = 0;
            grandparent.getDownLeft().blackHeight = 1;
            grandparent.getDownRight().blackHeight = 1;

            // make root black then call enforce again on grandparent node to fix
            ((RBTNode<T>) this.root).blackHeight = 1;
            enforceRBTreePropertiesAfterInsert(grandparent);
        }
    }

    /**
     * Tests for case where violating case is on same side (red node and its sibling are both a right or left child) and
     *     the parent red node's sibling is black
     *
     * expected functionality is for parent red node to rotate with black parent then swap colors
     */
    @Test
    public void blackSiblingSameSideViolation() {
    	RBTNode<Integer> root = new RBTNode<>(5);
        root.blackHeight = 1;
        root.down[0] = new RBTNode<>(4);
        root.down[0].up = root;
        root.down[1] = new RBTNode<>(7);
        ((RBTNode<T>) root.down[1]).blackHeight = 1;
        root.down[1].up = root; 
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.root = root;
        tree.size = 3;

        tree.insert(3);
        assertEquals("[ 4, 3, 5, 7 ]", tree.toLevelOrderString());
        assertEquals(tree.size, 4, "size not correct");
        assertEquals(tree.root.data, 4, "new root not set, check for null parent");

    	// Mirror image test
	    RBTNode<Integer> root2 = new RBTNode<>(5);
        root2.blackHeight = 1;
        root2.down[0] = new RBTNode<>(4);
        ((RBTNode<T>)root2.down[0]).blackHeight = 1;
	    root2.down[0].up = root2;
    	root2.down[1] = new RBTNode<>(7);
        root2.down[1].up = root2; 
        RedBlackTree<Integer> tree2 = new RedBlackTree<>();
        tree2.root = root2;
        tree2.size = 3;

        tree2.insert(10);
        assertEquals("[ 7, 5, 10, 4 ]", tree2.toLevelOrderString());
        assertEquals(tree2.size, 4, "size not correct");
        assertEquals(tree2.root.data, 7, "new root not set, check for null parent");
   
    }

    /**
     * Tests for case where violating case is not on  side (red node and its sibling differ in relative relation) and
     *     the parent red node's sibling is black
     *
     * expected functionality is to first rotate sibling red with parent red then for parent red node to rotate with
     *     black parent then swap colors
     */
    @Test
    public void blackSiblingDifferentSideViolation() {
       	RBTNode<Integer> root = new RBTNode<>(5);
        root.blackHeight = 1;
        root.down[0] = new RBTNode<>(3);
        root.down[0].up = root;
        root.down[1] = new RBTNode<>(7);
        ((RBTNode<T>) root.down[1]).blackHeight = 1;
        root.down[1].up = root; 
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.root = root;
        tree.size = 3;

        tree.insert(4); // 4 rotated up with 3, then 4 rotated up with 5 and color swap
        assertEquals("[ 4, 3, 5, 7 ]", tree.toLevelOrderString());
        assertEquals(tree.size, 4, "size not correct");
        assertEquals(tree.root.data, 4, "new root not set, check for null parent");

        // Mirror image test
	    RBTNode<Integer> root2 = new RBTNode<>(5);
        root2.blackHeight = 1;
        root2.down[0] = new RBTNode<>(3);
        ((RBTNode<T>) root2.down[0]).blackHeight = 1;
    	root2.down[0].up = root2;
	    root2.down[1] = new RBTNode<>(7);
        root2.down[1].up = root2; 
        RedBlackTree<Integer> tree2 = new RedBlackTree<>();
        tree2.root = root2;
        tree2.size = 3;

        tree2.insert(6);
        assertEquals("[ 6, 5, 7, 3 ]", tree2.toLevelOrderString());
        assertEquals(tree2.size, 4, "size not correct");
        assertEquals(tree2.root.data, 6, "new root not set, check for null parent");
   
    }

    /**
     * Tests for case where violating parent red node's sibling is also red
     *
     * expected functionality is for parent red node to swap colors with black parent, then check and fix grandparent of
     *     the red node if needed
     */
   
    @Test
    public void redSiblingViolation() {
        RBTNode<Integer> root = new RBTNode<>(5);
        ((RBTNode<T>) root).blackHeight = 1;
        root.down[0] = new RBTNode<>(4);
        root.down[0].up = root;
        root.down[1] = new RBTNode<>(7);
        root.down[1].up = root;
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.root = root;
        tree.size = 3;

        tree.insert(3); // colors should be swapped between 5 and 4,7, then 5 should be set to black bc it is root
        assertEquals("[ 5, 4, 7, 3 ]", tree.toLevelOrderString());
        assertEquals(((RBTNode<T>)tree.root.down[0]).blackHeight, 1, "red not color swapped");
        assertEquals(((RBTNode<T>)tree.root.down[1]).blackHeight, 1, "red not color swapped");
        assertEquals(((RBTNode<T>)tree.root).blackHeight, 1, "need to change root to black bc it is root");
        assertEquals(tree.size, 4, "size not correct");
        assertEquals(tree.root.data, 5, "new root not set, check for null parent");
    }
}

