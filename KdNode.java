/*************************************************************************
 * Name: Uros Slana
 * Email: urossla(at)gmail.com
 *
 * Compilation: javac KdNode.java
 *
 * Description: Definition of a node in a KdTree
 *
 *************************************************************************/

public class KdNode {
	
	public final Point p; 
	//left/right node respectively of this node
	public KdNode lb;
	public KdNode rt;
	
	public KdNode(Point p) {
		this.p = p;
	}
}
