import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;
import java.util.TreeMap;
//Acts as the drawing canvas
public class Sketch {
	public TreeMap<Integer,Shape> shapes ;
	public int id = 0;
	
	public Sketch() {
		shapes = new TreeMap<Integer,Shape>();
	}
	//add shape without id
	public int addSketch(Shape S) {
		shapes.put(id,S);
		id=id+1;
		return id;
	}
	//add shape with id
	public void addSketch(int id,Shape S) {
		shapes.put(id,S);

	}
	//Get shape variable
	public Shape getShape(int id) {
		return shapes.get(id);
	}
	//Check if shape has point
	public Shape containspoint(Point p) {
		
		for(Integer id :shapes.descendingKeySet()) {
			if(shapes.get(id).contains(p.x, p.y)) {
				return shapes.get(id);
			}
		}
		return null;
	}
	//draw shape
	public void draw(Graphics g) {
		
		for(Shape shape : shapes.values()) {
			shape.draw(g);
		}
		}	
	//remove shape
	public void remove(int id1) {
		shapes.remove(id1,shapes.get(id1));

	}
	//get shape id
	public int getId(Shape s) {
		for(int id:shapes.keySet()) {
			if(shapes.get(id).equals(s)) {
				return id;
			}
		}
		return -10;
	}
	//return all shapes in the sketch
	public  TreeMap<Integer, Shape> getShapes() {
		return shapes;
	}
	//empties the sketch
	public void empty() {
		shapes=new TreeMap<Integer,Shape>();
	}
	
}
