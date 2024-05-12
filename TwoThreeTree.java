package aisd_semestrov2;

import java.util.LinkedList;
import java.util.Queue;

public class TwoThreeTree {

  private int size;
  private TreeNode root;
  private boolean successfulInsertion;
  private boolean successfulDeletion;
  private boolean split;
  private boolean underflow;
  private boolean first;
  private boolean singleNodeUnderflow;

  private enum Nodes {
    LEFT, MIDDLE, RIGHT, DUMMY
  }

  public TwoThreeTree() {

    size = 0;
    root = null;
    successfulInsertion = false;
    successfulDeletion = false;
    underflow = false;
    singleNodeUnderflow = false;
    split = false;
    first = false;
  }

  private class Node {

  }

  private class TreeNode extends Node {

    int keys[];
    Node children[];
    int degree;

    TreeNode() {

      keys = new int[2];
      children = new Node[3];
      degree = 0;
    }

    void print() {

      if (degree == 1) {
        System.out.print("(-,-)");
      } else if (degree == 2) {
        System.out.print("(" + keys[0] + ",-) ");
      } else {
        System.out.print("(" + keys[0] + "," + keys[1] + ") ");
      }
    }
  }

  private class LeafNode extends Node {

    int key;

    LeafNode(int key) {
      this.key = key;
    }

    void print() {
      System.out.print(key + " ");
    }
  }

  private void insertKey(int key, IntegerWrapper count) {

    Node[] array = new Node[2];

    array = insert(key, root, count);

    if (array[1] == null) {

      root = (TreeNode) array[0];
      count.value += 1;
    } else {

      TreeNode treeRoot = new TreeNode();
      treeRoot.children[0] = array[0];
      treeRoot.children[1] = array[1];
      updateTree(treeRoot, count);
      root = treeRoot;
      count.value += 5;
    }
    count.value += 4;
  }

  private Node[] insert(int key, Node n, IntegerWrapper count) {

    Node array[] = new Node[2];

    Node catchArray[] = new Node[2];

    TreeNode t = null;

    if (n instanceof TreeNode) {
      t = (TreeNode) n;
      count.value += 1;
    }
    count.value += 4;

    if (root == null && !first) {
      first = true;

      TreeNode newNode = new TreeNode();
      t = newNode;

      t.children[0] = insert(key, t.children[0], count)[0];
      updateTree(t, count);

      array[0] = t;
      array[1] = null;
      count.value += 8;
    }

    else if (t != null && !(t.children[0] instanceof LeafNode)) {

      if (key < t.keys[0]) {

        catchArray = insert(key, t.children[0], count);

        t.children[0] = catchArray[0];
        count.value += 3;

        if (split) {

          if (t.degree <= 2) {

            split = false;

            t.children[2] = t.children[1];
            t.children[1] = catchArray[1];
            updateTree(t, count);
            array[0] = t;
            array[1] = null;
            count.value += 6;
          } else if (t.degree > 2) {

            TreeNode newNode = new TreeNode();
            newNode.children[0] = t.children[1];
            newNode.children[1] = t.children[2];
            updateTree(newNode, count);
            t.children[1] = catchArray[1];
            t.children[2] = null;
            updateTree(t, count);
            array[0] = t;
            array[1] = newNode;
            count.value += 11;
          }
        } else {

          updateTree(t, count);
          array[0] = t;
          array[1] = null;
          count.value += 5;
        }
        count.value += 1;
      }

      else if (key >= t.keys[0] && (t.children[2] == null || key < t.keys[1])) {

        catchArray = insert(key, t.children[1], count);

        t.children[1] = catchArray[0];
        count.value += 2;

        if (split) {

          if (t.degree <= 2) {

            split = false;

            t.children[2] = catchArray[1];
            updateTree(t, count);
            array[0] = t;
            array[1] = null;
            count.value += 6;
          } else if (t.degree > 2) {

            TreeNode newNode = new TreeNode();
            newNode.children[0] = catchArray[1];
            newNode.children[1] = t.children[2];
            updateTree(newNode, count);
            t.children[2] = null;
            updateTree(t, count);
            array[0] = t;
            array[1] = newNode;
            count.value += 10;
          }
        } else {

          updateTree(t, count);
          array[0] = t;
          array[1] = null;
          count.value += 5;
        }
        count.value += 1;
      }

      else if (key >= t.keys[1]) {

        catchArray = insert(key, t.children[2], count);

        t.children[2] = catchArray[0];

        if (split) {
          if (t.degree > 2) {

            TreeNode newNode = new TreeNode();
            newNode.children[0] = catchArray[0];
            newNode.children[1] = catchArray[1];
            updateTree(newNode, count);
            t.children[2] = null;
            updateTree(t, count);
            array[0] = t;
            array[1] = newNode;
            count.value += 10;

          }
        } else {

          updateTree(t, count);
          array[0] = t;
          array[1] = null;
          count.value += 5;
        }
      }
    }

    else if (t != null && t.children[0] instanceof LeafNode) {

      LeafNode l1 = null, l2 = null, l3 = null;
      if (t.children[0] != null && t.children[0] instanceof LeafNode) {
        l1 = (LeafNode) t.children[0];
      }
      if (t.children[1] != null && t.children[1] instanceof LeafNode) {
        l2 = (LeafNode) t.children[1];
      }
      if (t.children[2] != null && t.children[2] instanceof LeafNode) {
        l3 = (LeafNode) t.children[2];
      }

      if (t.degree <= 2) {

        if (t.degree == 1 && key > l1.key) {
          LeafNode leaf = new LeafNode(key);
          t.children[1] = leaf;
        } else if (t.degree == 1 && key < l1.key) {
          LeafNode leaf = new LeafNode(key);
          t.children[1] = l1;
          t.children[0] = leaf;
        } else if (t.degree == 2 && key < l1.key) {
          LeafNode leaf = new LeafNode(key);
          t.children[2] = l2;
          t.children[1] = l1;
          t.children[0] = leaf;
        } else if (t.degree == 2 && key < l2.key && key > l1.key) {
          LeafNode leaf = new LeafNode(key);
          t.children[2] = l2;
          t.children[1] = leaf;
        } else if (t.degree == 2) {
          LeafNode leaf = new LeafNode(key);
          t.children[2] = leaf;
        }

        updateTree(t, count);
        array[0] = t;
        array[1] = null;
      } else if (t.degree > 2) {

        split = true;

        if (key < l1.key) {

          LeafNode leaf = new LeafNode(key);
          TreeNode newNode = new TreeNode();
          t.children[0] = leaf;
          t.children[1] = l1;
          t.children[2] = null;
          updateTree(t, count);
          newNode.children[0] = l2;
          newNode.children[1] = l3;
          updateTree(newNode, count);
          array[0] = t;
          array[1] = newNode;
          count.value += 11;
        } else if (key >= l1.key && key < l2.key) {

          LeafNode leaf = new LeafNode(key);
          TreeNode newNode = new TreeNode();
          t.children[1] = leaf;
          t.children[2] = null;
          updateTree(t, count);
          newNode.children[0] = l2;
          newNode.children[1] = l3;
          updateTree(newNode, count);
          array[0] = t;
          array[1] = newNode;
          count.value += 10;
        } else if (key >= l2.key && key < l3.key) {

          LeafNode leaf = new LeafNode(key);
          t.children[2] = null;
          updateTree(t, count);
          TreeNode newNode = new TreeNode();
          newNode.children[0] = leaf;
          newNode.children[1] = l3;
          updateTree(newNode, count);
          array[0] = t;
          array[1] = newNode;
          count.value += 9;
        } else if (key >= l3.key) {

          LeafNode leaf = new LeafNode(key);
          t.children[2] = null;
          updateTree(t, count);
          TreeNode newNode = new TreeNode();
          newNode.children[0] = l3;
          newNode.children[1] = leaf;
          updateTree(newNode, count);
          array[0] = t;
          array[1] = newNode;
          count.value += 9;
        }
      }

      successfulInsertion = true;
      count.value += 10;
    } else if (n == null) {

      successfulInsertion = true;
      array[0] = new LeafNode(key);
      array[1] = null;
      count.value += 4;
      return array;
    }
    count.value += 1;
    return array;
  }

  private Node remove(int key, Node n, IntegerWrapper count) {

    TreeNode t = null;
    if (n instanceof TreeNode) {
      t = (TreeNode) n;
    }

    if (n == null) {
      return null;
    }

    if (t != null && t.children[0] instanceof TreeNode) {

      if (key < t.keys[0]) {

        t.children[0] = remove(key, t.children[0], count);

        if (singleNodeUnderflow) {

          TreeNode child = (TreeNode) t.children[0];
          TreeNode rightChild = (TreeNode) t.children[1];

          if (rightChild.degree == 2) {
            rightChild.children[2] = rightChild.children[1];
            rightChild.children[1] = rightChild.children[0];
            rightChild.children[0] = child;
            updateTree(rightChild, count);
            t.children[0] = rightChild;
            t.children[1] = t.children[2];
            t.children[2] = null;
            count.value += 10;

            if (t.degree == 2) {
              singleNodeUnderflow = true;
              t = (TreeNode) t.children[0];
            } else {
              singleNodeUnderflow = false;
            }
          }

          else if (rightChild.degree == 3) {
            TreeNode newNode = new TreeNode();
            newNode.children[0] = t.children[0];
            newNode.children[1] = rightChild.children[0];
            t.children[0] = newNode;
            updateTree(newNode, count);
            rightChild.children[0] = rightChild.children[1];
            rightChild.children[1] = rightChild.children[2];
            rightChild.children[2] = null;
            updateTree(rightChild, count);
            singleNodeUnderflow = false;
            count.value += 11;
          }
        }

        else if (underflow) {

          underflow = false;

          TreeNode child = (TreeNode) t.children[0];
          TreeNode rightChild = (TreeNode) t.children[1];

          if (rightChild.degree == 3) {
            Node reference = rightChild.children[0];
            rightChild.children[0] = rightChild.children[1];
            rightChild.children[1] = rightChild.children[2];
            rightChild.children[2] = null;
            updateTree(rightChild, count);
            child.children[1] = reference;
            updateTree(child, count);
            count.value += 11;
          }

          else if (rightChild.degree == 2) {
            Node reference = child.children[0];
            rightChild.children[2] = rightChild.children[1];
            rightChild.children[1] = rightChild.children[0];
            rightChild.children[0] = reference;
            updateTree(rightChild, count);
            t.children[0] = rightChild;
            count.value += 6;

            if (t.degree == 3) {
              t.children[1] = t.children[2];
              t.children[2] = null;
              count.value += 2;
            }

            else {
              Node ref = t.children[0];
              t = (TreeNode) ref;
              singleNodeUnderflow = true;
              count.value += 3;
            }
          }
        }
        updateTree(t, count);
      } else if (key >= t.keys[0] && (t.children[2] == null || key < t.keys[1])) {

        t.children[1] = remove(key, t.children[1], count);

        if (singleNodeUnderflow) {

          TreeNode leftChild = (TreeNode) t.children[0];
          TreeNode child = (TreeNode) t.children[1];
          TreeNode rightChild = (TreeNode) t.children[2];
          count.value += 4;

          if (leftChild.degree == 2) {
            leftChild.children[2] = child;
            t.children[1] = rightChild;
            t.children[2] = null;
            updateTree(leftChild, count);
            count.value += 5;

            if (t.degree == 2) {
              singleNodeUnderflow = true;
              t = (TreeNode) t.children[0];
            } else {
              singleNodeUnderflow = false;
            }
          }

          else if (rightChild != null && rightChild.degree == 2) {
            rightChild.children[2] = rightChild.children[1];
            rightChild.children[1] = rightChild.children[0];
            rightChild.children[0] = child;
            updateTree(rightChild, count);
            t.children[1] = rightChild;
            t.children[2] = null;
            singleNodeUnderflow = false;
            count.value += 8;

          }

          else if (leftChild.degree == 3) {
            TreeNode newNode = new TreeNode();
            newNode.children[0] = leftChild.children[2];
            newNode.children[1] = child;
            t.children[1] = newNode;
            updateTree(newNode, count);
            updateTree(leftChild, count);
            singleNodeUnderflow = false;
            count.value += 8;
          }

          else if (rightChild != null && rightChild.degree == 3) {
            TreeNode newNode = new TreeNode();
            newNode.children[0] = child;
            newNode.children[1] = rightChild.children[0];
            rightChild.children[0] = rightChild.children[1];
            rightChild.children[1] = rightChild.children[2];
            rightChild.children[2] = null;
            t.children[1] = newNode;
            updateTree(newNode, count);
            updateTree(rightChild, count);
            singleNodeUnderflow = false;
            count.value += 12;
          }
        } else if (underflow) {

          underflow = false;

          TreeNode leftChild = (TreeNode) t.children[0];
          TreeNode child = (TreeNode) t.children[1];
          TreeNode rightChild = (TreeNode) t.children[2];

          if (leftChild.degree == 3) {
            Node reference = leftChild.children[2];
            leftChild.children[2] = null;
            child.children[1] = child.children[0];
            child.children[0] = reference;
            updateTree(leftChild, count);
            updateTree(child, count);
            count.value += 14;
          }

          else if (rightChild != null && rightChild.degree == 3) {
            Node reference = rightChild.children[0];
            rightChild.children[0] = rightChild.children[1];
            rightChild.children[1] = rightChild.children[2];
            rightChild.children[2] = null;
            updateTree(rightChild, count);
            child.children[1] = reference;
            updateTree(child, count);
            count.value += 12;
          }

          else if (leftChild.degree == 2) {
            Node reference = child.children[0];
            leftChild.children[2] = reference;
            updateTree(leftChild, count);
            t.children[1] = null;

            if (t.degree == 3) {
              t.children[1] = t.children[2];
              t.children[2] = null;
              count.value += 2;
            }

            else {
              singleNodeUnderflow = true;
              t = (TreeNode) t.children[0];
              count.value += 2;
            }
            count.value += 12;
          }

          else if (rightChild != null && rightChild.degree == 2) {
            Node reference = child.children[0];
            rightChild.children[2] = rightChild.children[1];
            rightChild.children[1] = rightChild.children[0];
            rightChild.children[0] = reference;
            updateTree(rightChild, count);
            t.children[1] = rightChild;
            t.children[2] = null;
            singleNodeUnderflow = false;
            count.value += 15;
          }
        }
        updateTree(t, count);
        count.value += 1;
      } else if (key >= t.keys[1]) {

        t.children[2] = remove(key, t.children[2], count);

        if (singleNodeUnderflow) {

          TreeNode child = (TreeNode) t.children[2];
          TreeNode leftChild = (TreeNode) t.children[1];
          count.value += 2;

          if (leftChild.degree == 2) {
            leftChild.children[2] = child;
            t.children[2] = null;
            updateTree(leftChild, count);
            count.value += 3;
          }

          else if (leftChild.degree == 3) {
            TreeNode newNode = new TreeNode();
            newNode.children[0] = leftChild.children[2];
            newNode.children[1] = t.children[2];
            t.children[2] = newNode;
            updateTree(newNode, count);
            updateTree(leftChild, count);
            count.value += 8;
          }

          singleNodeUnderflow = false;
        } else if (underflow) {

          underflow = false;

          TreeNode leftChild = (TreeNode) t.children[1];
          TreeNode child = (TreeNode) t.children[2];
          count.value += 3;

          if (leftChild.degree == 3) {
            Node reference = leftChild.children[2];
            leftChild.children[2] = null;
            child.children[1] = child.children[0];
            child.children[0] = reference;
            updateTree(leftChild, count);
            updateTree(child, count);
            count.value += 7;
          }

          else if (leftChild.degree == 2) {
            Node reference = child.children[0];
            leftChild.children[2] = reference;
            updateTree(leftChild, count);
            t.children[2] = null;
            count.value += 6;
          }
        }
        updateTree(t, count);
        count.value += 1;
      }
    } else if (t != null && t.children[0] instanceof LeafNode) {

      LeafNode l1 = null, l2 = null, l3 = null;
      if (t.children[0] != null && t.children[0] instanceof LeafNode) {
        l1 = (LeafNode) t.children[0];
      }
      if (t.children[1] != null && t.children[1] instanceof LeafNode) {
        l2 = (LeafNode) t.children[1];
      }
      if (t.children[2] != null && t.children[2] instanceof LeafNode) {
        l3 = (LeafNode) t.children[2];
      }

      if (t.degree == 3) {

        if (key == l1.key) {
          t.children[0] = l2;
          t.children[1] = l3;
          t.children[2] = null;
        } else if (key == l2.key) {
          t.children[1] = l3;
          t.children[2] = null;
        } else if (key == l3.key) {
          t.children[2] = null;
        }

        updateTree(t, count);
      } else if (t.degree == 2) {

        underflow = true;

        if (l1.key == key) {
          t.children[0] = l2;
          t.children[1] = null;
        } else if (l2.key == key) {
          t.children[1] = null;
        }
      } else if (t.degree == 1) {

        if (l1.key == key) {
          t.children[0] = null;
        }
      }

      successfulDeletion = true;
    }
    count.value += 20;
    return t;
  }

  private void updateTree(TreeNode t, IntegerWrapper count) {

    if (t != null) {

      if (t.children[2] != null && t.children[1] != null && t.children[0] != null) {

        t.degree = 3;

        t.keys[0] = getValueForKey(t, Nodes.LEFT, count);
        t.keys[1] = getValueForKey(t, Nodes.RIGHT, count);
        count.value += 10;
      } else if (t.children[1] != null && t.children[0] != null) {

        t.degree = 2;

        t.keys[0] = getValueForKey(t, Nodes.LEFT, count);
        t.keys[1] = 0;
        count.value += 12;
      } else if (t.children[0] != null) {

        t.degree = 1;

        t.keys[1] = t.keys[0] = 0;
        count.value += 11;
      }
    }
    count.value += 1;
  }

  private int getValueForKey(Node n, Nodes whichVal, IntegerWrapper count) {

    int key = -1;

    TreeNode t = null;
    LeafNode l = null;
    if (n instanceof TreeNode) {
      t = (TreeNode) n;
      count.value += 1;
    } else {
      l = (LeafNode) n;
      count.value += 1;
    }

    if (l != null) {
      key = l.key;
      count.value += 1;
    }

    if (t != null) {

      if (null != whichVal) {
        switch (whichVal) {

          case LEFT:

            key = getValueForKey(t.children[1], Nodes.DUMMY, count);
            count.value += 4;
            break;

          case RIGHT:

            key = getValueForKey(t.children[2], Nodes.DUMMY, count);
            count.value += 4;
            break;

          case DUMMY:

            key = getValueForKey(t.children[0], Nodes.DUMMY, count);
            count.value += 4;
            break;
          default:
            break;
        }
      }
      count.value += 1;
    }
    count.value += 8;
    return key;
  }

  private boolean search(int key, Node n, IntegerWrapper count) {

    boolean found = false;

    TreeNode t = null;
    LeafNode l = null;
    if (n instanceof TreeNode) {
      t = (TreeNode) n;
      count.value += 1;
    } else {
      l = (LeafNode) n;
      count.value += 1;
    }

    if (t != null) {

      if (t.degree == 1) {

        found = search(key, t.children[0], count);
        count.value += 3;
      } else if (t.degree == 2 && key < t.keys[0]) {

        found = search(key, t.children[0], count);
        count.value += 6;
      } else if (t.degree == 2 && key >= t.keys[0]) {

        found = search(key, t.children[1], count);
        count.value += 9;
      } else if (t.degree == 3 && key < t.keys[0]) {

        found = search(key, t.children[0], count);
        count.value += 12;
      } else if (t.degree == 3 && key >= t.keys[0] && key < t.keys[1]) {

        found = search(key, t.children[1], count);
        count.value += 17;
      } else if (t.degree == 3 && key >= t.keys[1]) {

        found = search(key, t.children[2], count);
        count.value += 20;
      }
    } else if (l != null && key == l.key) {
      count.value += 4;
      return true;
    }
    count.value += 7;
    return found;
  }

  private void keyOrderList(Node n) {

    TreeNode t = null;
    LeafNode l = null;
    if (n instanceof TreeNode) {
      t = (TreeNode) n;
    } else {
      l = (LeafNode) n;
    }

    if (t != null) {

      if (t.children[0] != null) {

        keyOrderList(t.children[0]);
      }

      if (t.children[1] != null) {

        keyOrderList(t.children[1]);
      }

      if (t.children[2] != null) {

        keyOrderList(t.children[2]);
      }
    } else if (l != null) {

      System.out.print(l.key + " ");
    }
  }

  private void bfsList(Node n) {

    Queue<Node> queueOne = new LinkedList<Node>();
    Queue<Node> queueTwo = new LinkedList<Node>();

    if (n == null) {
      return;
    }

    queueOne.add(n);

    Node first = null;
    TreeNode t = null;

    while (!queueOne.isEmpty() || !queueTwo.isEmpty()) {

      while (!queueOne.isEmpty()) {

        first = queueOne.poll();

        if (first instanceof TreeNode) {
          t = (TreeNode) first;
          t.print();
        }

        if (t.children[0] != null && !(t.children[0] instanceof LeafNode)) {
          queueTwo.add(t.children[0]);
        }
        if (t.children[1] != null && !(t.children[1] instanceof LeafNode)) {
          queueTwo.add(t.children[1]);
        }
        if (t.children[2] != null && !(t.children[2] instanceof LeafNode)) {
          queueTwo.add(t.children[2]);
        }

      }

      if (!queueOne.isEmpty() || !queueTwo.isEmpty()) {
        System.out.println();
      }

      while (!queueTwo.isEmpty()) {

        first = queueTwo.poll();

        if (first instanceof TreeNode) {
          t = (TreeNode) first;
          t.print();
        }

        if (t.children[0] != null && !(t.children[0] instanceof LeafNode)) {
          queueOne.add(t.children[0]);
        }
        if (t.children[1] != null && !(t.children[1] instanceof LeafNode)) {
          queueOne.add(t.children[1]);
        }
        if (t.children[2] != null && !(t.children[2] instanceof LeafNode)) {
          queueOne.add(t.children[2]);
        }

      }

      if (!queueOne.isEmpty() || !queueTwo.isEmpty()) {
        System.out.println();
      }
    }

    System.out.println();
    keyOrderList(root);
    System.out.println();
  }

  private int height(Node n) {

    TreeNode t = null;
    LeafNode l = null;
    if (n instanceof TreeNode) {
      t = (TreeNode) n;
    } else {
      l = (LeafNode) n;
    }

    if (t != null) {

      return 1 + height(t.children[0]);
    }

    return 0;
  }

  public boolean insert(int key, IntegerWrapper count) {
    count.value = 0;
    boolean insert = false;

    split = false;

    if (!search(key, count)) {

      insertKey(key, count);
      count.value += 1;
    }

    if (successfulInsertion) {

      size++;
      insert = successfulInsertion;
      successfulInsertion = false;
      count.value += 3;
    }
    count.value += 6;
    return insert;
  }

  public boolean search(int key, IntegerWrapper count) {
    count.value = 0;
    count.value += 2;
    return search(key, root, count);
  }

  public boolean remove(int key, IntegerWrapper count) {
    count.value = 0;
    boolean delete = false;
    singleNodeUnderflow = false;
    underflow = false;

    if (search(key, count)) {

      root = (TreeNode) remove(key, root, count);

      if (root.degree == 1 && root.children[0] instanceof TreeNode) {

        root = (TreeNode) root.children[0];
        count.value += 1;
      }
      count.value += 6;
    }

    if (successfulDeletion) {

      size--;
      delete = successfulDeletion;
      successfulDeletion = false;
      count.value += 3;
    }

    count.value += 7;
    return delete;
  }

  public void keyOrderList() {

    System.out.println("Keys");
    keyOrderList(root);
    System.out.println();
  }

  public void bfsList() {

    System.out.println("Tree");
    bfsList(root);
  }

  public int numberOfNodes() {

    return size;
  }

  public int height() {

    return height(root);
  }

}