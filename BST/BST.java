//Anju Shrestha
//cs304
package collections.tree.bst;

import java.util.Scanner;
import java.util.Random;
import collections.list.generics.LinkedList;

enum Order {IN_ORDER, POST_ORDER, PRE_ORDER};
public class BST<T extends Comparable<T>>
{
   protected BSTNode<T> root;
   
   public void clear()
   {
      root = null;
   }

   public int size()
   {
      return sizeRec(root);
   }
   private int sizeRec(BSTNode<T> r)
   {
      if( r==null) return 0;
      return 1 + sizeRec(r.left) + sizeRec(r.right);
   }
   public void balance()
   {
      /* 
         1, get a LinkedList containing all elements of tree in_order
         2, clear our tree - we do have a clear method
         3, call balanceRec with 0 as low, size()-1 as high, and the two 
             arguments that don't change, THIS (our tree) and the linked list
              of values
         4, remember your stopping condition.  Add middle value. recurse to the 
               left and then the right sides of your list.
      */
      LinkedList<T> sortedList = traversal(Order.IN_ORDER);
      clear();
      balanceRec(this, 0, sortedList.size()-1, sortedList);
   }
   private void balanceRec(BST<T> tree, int low, int high, LinkedList<T> list)
   {
      /* this is our mid - guessing game.
         pick the mid (low and high are inclusive)
         get the value from the linked list and add to tree.
         recurse to left and right
      */
      if(low > high)
         return;
      int mid = (low + high)/ 2;
      tree.add(list.get(mid));
      balanceRec(tree, low, mid - 1, list);
      balanceRec(tree, mid + 1, high, list);


   }
   public int height()
   {
      return heightRec(root);
   }
   private int heightRec(BSTNode<T> tree)
   {
      if( tree == null || (tree.left==null && tree.right==null) ) return 0;
      return Math.max(1 + heightRec(tree.right), 1 + heightRec(tree.left));
   }
   public boolean contains(T val)
   {
      
      return containsRec(val, root);
   }
   private boolean containsRec(T val, BSTNode<T> r)
   {
      if(r == null)
     return false;
            
    int compare = val.compareTo(r.val);
            
    if( compare < 0 )
     return containsRec( val, r.left );
        
    else if( compare > 0 )
     return containsRec( val, r.right );
     
    else
     return true;      
      //return false;
   }
   private void preOrder(LinkedList<T> list, BSTNode<T> t)
   {
      if( t == null ) 
      {
         return;
      }
      list.add(t.val);
      preOrder(list, t.left);
      preOrder(list, t.right);
   }
   private void postOrder(LinkedList<T> list, BSTNode<T> t)
   {
      if(t == null)
         return;
      postOrder(list, t.left);
      postOrder(list, t.right);
      list.add(t.val);
      
   }
   private void inOrder(LinkedList<T> list, BSTNode<T> t)
   {
      if(t == null)
         return;
      inOrder(list, t.left);
      list.add(t.val);
      inOrder(list, t.right);
   }
   public LinkedList<T> traversal(Order o)
   {
      LinkedList<T> list = new LinkedList<>();
      if( o == Order.IN_ORDER ) 
      {
         inOrder(list, root);
      }
      else if( o == Order.PRE_ORDER ) 
      {
         preOrder(list, root);
      }
      else
      {
         postOrder(list, root);
      }
      return list;
   }
   
   public void add(T val)
   {
      if( root == null ) 
      {    
         root = new BSTNode<>(val);
      }
      else 
      {
         addRec(val, root);
      }  
   }
   public void addRec(T val, BSTNode<T> tree)
   {
      if( val.compareTo(tree.val) > 0 )
      {
         if( tree.right == null ) 
         {
            tree.right = new BSTNode<T>(val);
         }
         else
         {
            addRec(val, tree.right);
         }
      }
      else if( val.compareTo(tree.val) < 0 ) 
      {
         if( tree.left == null ) 
         {
            tree.left = new BSTNode<T>(val);
         }      
         else
         {
            addRec(val, tree.left);
         }
      }
      // return value is already in our tree
   }
   
   public void removeRec(BSTNode<T> parent, BSTNode<T> cur, T val)
   {
      if( cur == null ) return;
      if( cur.val.equals(val) ) 
      {
         remove( parent, cur);
      }
      else if( cur.val.compareTo(val) > 0 ) 
      {
         removeRec(cur, cur.left, val);  
      }
      else 
      {
         removeRec(cur, cur.right, val);
      }
   }
   
   public void replace( BSTNode<T> parent, BSTNode<T> cur, BSTNode<T> rep)
   {
      if( parent == null ) root = rep;
      else if( cur.val.compareTo(parent.val) > 0 ) parent.right = rep;
      else parent.left = rep;
   } 
   
   public void remove(T val)
   {
      removeRec(null, root, val);
   }
   
   public void remove(BSTNode<T> parent, BSTNode<T> cur)
   {
      if( cur.left == null )
      {
         replace( parent, cur, cur.right );
      }
      else if( cur.right == null ) 
      {
         replace( parent, cur, cur.left );
      }
      else // node to be removed has two children
      {
         T pred = max( cur.left );
         cur.val = pred;
         removeRec(cur, cur.left, cur.val); 
      }
   }
   private T max( BSTNode<T> cur )
   {
      if( cur.right == null ) return cur.val;
      return max(cur.right);
   }
   public LinkedList<T> toList()
   {
      LinkedList<T> list = traversal(Order.IN_ORDER);
      return list;
   }
   
   public String toString()
   {
      LinkedList<T> list = traversal(Order.IN_ORDER);
      return list.toString();
   }
   
   public static void main(String[] args)
   {
      int numVerts = 31;
      int maxVal = numVerts*10;
      BST<Integer> myTree = new BST<>();
      int seed = 2010;
      Random rndGen = new Random(seed);
      
      for( int i=0; i<numVerts; i++ ) 
      {
         myTree.add(rndGen.nextInt(maxVal)+1);
      }
      System.out.println( "Size of tree = " + myTree.size());
      System.out.println( "Height of tree = " + myTree.height());
      
      System.out.println(myTree);      
      System.out.println("Pre-order:");
      System.out.println(myTree.traversal(Order.PRE_ORDER));
      System.out.println("In-order:");
      System.out.println(myTree.traversal(Order.IN_ORDER));
      System.out.println("Post-order:");
      System.out.println(myTree.traversal(Order.POST_ORDER));
      
      System.out.println("--Balancing--");
      myTree.balance();
      System.out.println( "Height of tree = " + myTree.height());

      System.out.println(myTree);      
      System.out.println("Pre-order:");
      System.out.println(myTree.traversal(Order.PRE_ORDER));
      System.out.println("In-order:");
      System.out.println(myTree.traversal(Order.IN_ORDER));
      System.out.println("Post-order:");
      System.out.println(myTree.traversal(Order.POST_ORDER));

            
      int v = 0;
      for(int i=1; i<=maxVal; i++ )  
      {
         if( myTree.contains(i) )
         { 
            System.out.println( i + " is in tree!  Removing..");
            myTree.remove(i);
         }   
      }
      System.out.println( "Size of tree = " + myTree.size());
      System.out.println( "Height of tree = " + myTree.height());
   }
}