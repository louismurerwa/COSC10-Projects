import java.util.ArrayList;
import java.util.List;

/**
 * A point quadtree: stores an element at a 2D position, 
 * with children at the subdivided quadrants
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2015
 * @author CBK, Spring 2016, explicit rectangle
 * @author CBK, Fall 2016, generic with Point2D interface
 * 
 */
public class PointQuadtree<E extends Point2D> {
	private E point;							// the point anchoring this node
	private int x1, y1;							// upper-left corner of the region
	private int x2, y2;							// bottom-right corner of the region
	private PointQuadtree<E> c1, c2, c3, c4;	// children
	List<E> allpoints = new ArrayList<>() ;
	
	/**
	 * Initializes a leaf quadtree, holding the point in the rectangle
	 */
	public PointQuadtree(E point, int x1, int y1, int x2, int y2) {
		this.point = point;
		this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;
	}

	// Getters
	
	public E getPoint() {
		return point;
	}

	public int getX1() {
		return x1;
	}

	public int getY1() {
		return y1;
	}

	public int getX2() {
		return x2;
	}

	public int getY2() {
		return y2;
	}

	/**
	 * Returns the child (if any) at the given quadrant, 1-4
	 * @param quadrant	1 through 4
	 */
	public PointQuadtree<E> getChild(int quadrant) {
		if (quadrant==1) return c1;
		if (quadrant==2) return c2;
		if (quadrant==3) return c3;
		if (quadrant==4) return c4;
		return null;
	}

	/**
	 * Returns whether or not there is a child at the given quadrant, 1-4
	 * @param quadrant	1 through 4
	 */
	public boolean hasChild(int quadrant) {
		return (quadrant==1 && c1!=null) || (quadrant==2 && c2!=null) || (quadrant==3 && c3!=null) || (quadrant==4 && c4!=null);
	}

	/**
	 * Inserts the point into the tree
	 */
	public void insert(E p2) {
		// TODO: YOUR CODE HERE
		//Checking the quadrant of the p2 with relative to first point
		//Checking if a quadrant  has a child
		//inserts p2 if current node has a child
		//Creates a new node if current node doesn't have a child
		//Goes through all the children
		if(p2.getX()>=point.getX()) {
			if(p2.getY()<=point.getY()) {				
				if (hasChild(1)) {					
					c1.insert(p2);
					}				
				else { c1 = new PointQuadtree<E>(p2,(int)point.getX(),getY1(),getX2(),(int)point.getY());
				}
			}
		
			else if(p2.getY()>=point.getY()) {
				if (hasChild(4)){
					c4.insert(p2);
				}
				else { c4 = new PointQuadtree<E>(p2,(int)point.getX(),(int)point.getY(),getX2(),getY2());
				}
			}
		}
		
		
		else if(p2.getX()<=point.getX()) {
			if(p2.getY()<=point.getY()) {
				if (hasChild(2)) {				
					c2.insert(p2);
				}
				else { c2 = new PointQuadtree<E>(p2,getX1(),getY1(),(int)point.getX(),(int)point.getY());
				}
			}
			else if(p2.getY()>=point.getY()) {
				if (hasChild(3)) {
					c3.insert(p2);
				}
				else { c3 = new PointQuadtree<E>(p2,getX1(),(int)point.getY(),(int)point.getX(),getY2());
				}
			}
		}
	}
	
	/**
	 * Finds the number of points in the quadtree (including its descendants)
	 */
	public int size() {
		// TODO: YOUR CODE HERE
		//return size of array which contains all the points in the tree
		return allPoints().size();
		
		
	}
	
	/**
	 * Builds a list of all the points in the quadtree (including its descendants)
	 */
	public List<E> allPoints() {
		// TODO: YOUR CODE HERE
		//add current point to the list of points
		allpoints.add(point);
		//Recurse through the children of the point
		for(int x =1 ; x<5 ;x++) {
			if(hasChild(x)) {
				point=getChild(x).getPoint();
				allPoints();
			}
		}
		return allpoints;
	}	
		
	

	/**
	 * Uses the quadtree to find all points within the circle
	 * @param cx	circle center x
	 * @param cy  	circle center y
	 * @param cr  	circle radius
	 * @return    	the points in the circle (and the qt's rectangle)

	 */
	public List<E> findInCircle(double cx, double cy, double cr) {
		// TODO: YOUR CODE HERE
		//Create an array to contain our found points
		//Call helper function to look for points in given circle
		//Return the found points
		List<E> findpoints = new ArrayList<>() ;
		findpoints=findInCircleHelper(findpoints,cx,cy,cr);
		return findpoints;
		
	}
	
	// TODO: YOUR CODE HERE for any helper methods
	
	public List<E> findInCircleHelper(List<E> a,double cx, double cy, double cr) {
		// TODO: YOUR CODE HERE
		//Checks to see if the circle intersects the rectangle
		//Checks to see if the point to be checked is in the circle
		//Adds the point to a list of points ion circle
		//Recurses through all points in the tree
		if (Geometry.circleIntersectsRectangle(cx , cy , cr, x1, y1,  x2, y2)){
			if(Geometry.pointInCircle(point.getX(), point.getY(), cx, cy,  cr)) {
				a.add(point);
			}
		}
		for(int i =1 ; i<5 ;i++) {
			if(hasChild(i)) { 
				getChild(i).findInCircleHelper(a,cx,cy,cr);
				}		
			}
		
		return a;
		
	}
}
	
	




