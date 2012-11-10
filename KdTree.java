/*************************************************************************
 * Name: Uros Slana
 * Email: urossla(at)gmail.com
 *
 * Compilation: javac KdTree.java
 * Dependencies: Point.java  
 *
 * Description: Builds KdTree 
 *************************************************************************/

public class KdTreeJG {
	
	private  class Node {
		
		private Point p; 
		//left/right node respectively of this node
		private Node lb;
		private Node rt;

		public Node(Point p) {
			this.p = p;
		}
	}
	
	private Node root;
	//number of nodes in the tree
	private int N; 			

	public KdTree() {
	} 

	public boolean isEmpty() {                      
		return N == 0; 
	}

	public int size() {                            
		return N;
	}
	
	// add the point p to the set (if it is not already in the set)
	public void insert(Point p) {                  
		
		if (root == null) {
		   root = new Node(p);
		   N++;
		   return;
		}
        
		//if odd split vertical, else split horizontal
		int depth = 1; 
		Node cur = root;
		
		while (cur != null) {
			 //point is already in tree
			 if (cur.p.compareTo(p) == 0) {
				return;
			 }
			 //split vertical or horizontal
			 if ((depth%2 == 1 && p.x() < cur.p.x()) || (depth%2 == 0 && p.y() < cur.p.y())) {
				if (cur.lb == null) {
					cur.lb = new Node(p);
					N++;
					return;
				}
				else {
					cur = cur.lb;
				}
			}
			else {
				if (cur.rt == null) {
					cur.rt = new Node(p);
					N++;
					return;
				}
				else {
					cur = cur.rt;
				}
			}
			depth++;
		}
	}

	// does the set contain the point p?
	public boolean contains(Point p) {
		
		if (root == null) {
			return false;
		}

		int depth = 1;
		Node cur = root;
		
		while (cur != null) {
			if (cur.p.compareTo(p) == 0) {
				return true;
			}
			if (depth % 2 == 1) {
				if ((depth%2 == 1 && p.x() < cur.p.x()) || (depth%2 == 0 && p.y() < cur.p.y())) {
					cur = cur.lb;
				}
				else {
					cur = cur.rt;
				}
			}
			depth++;
		}
		return false;
	}
}
