package collections.tree.bst;

public class BSTNode<T>
{
   T val;
   BSTNode<T>  left;
   BSTNode<T>  right;
   public BSTNode(T data)
   {
      this.val = data;
   }
/*   
   public static void main(String[] args)
   {
      BSTNode<Integer> a = new BSTNode<>(20);
      BSTNode<Integer> b = new BSTNode<>(40);
      BSTNode<Integer> c = new BSTNode<>(10);
      a.left = c;
      a.right = b;
      BSTNode<Integer> d = new BSTNode<>(30);
      BSTNode<Integer> e = new BSTNode<>(15);
      a.right.left = d;
      a.left.right = e;
            
      
      
   } 
   */ 
}
