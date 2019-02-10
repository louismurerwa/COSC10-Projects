import java.awt.Color;
import java.awt.Point;

public class message {
	private Shape curr = null;					// current shape (if any) being drawn
	private Sketch sketch;
	String[] message1;
	String msg;
	public message() {
		
	}
	//	This method takes in a command
	public message(Editor e,String msg,Sketch sketch) {
		this.msg=msg;
		this.sketch = sketch;
	}
	//This method edits an input sketch with a command
	public int update(String msg,Sketch sketch) {
		Sketch temp = new Sketch ();
		//Split message
		message1 = msg.split(" ");
		//Check if our first key equals the command
		if(message1[0].equals("draw")) {
			if(message1[1].equals("polyline")){
				String[]points = message1[2].split(",");
				System.out.println(points);
				
				curr=new Polyline(new Point(Integer.parseInt(points[0]),Integer.parseInt(points[1])),new Color(Integer.parseInt(message1[3])));
				for(int i=0;i<points.length;i=i+2) {
					((Polyline) curr).setEnd(new Point(Integer.parseInt(points[i]),Integer.parseInt(points[i+1])));
				
				}
				
			}
			if(message1[1].equals("ellipse")) {
				curr=new Ellipse(Integer.parseInt(message1[2]),Integer.parseInt(message1[3]),Integer.parseInt(message1[4]),Integer.parseInt(message1[5]),new Color(Integer.parseInt(message1[6])));
				
			}
			else if(message1[1].equals("rectangle")){
				curr = new Rectangle(Integer.parseInt(message1[2]),Integer.parseInt(message1[3]),Integer.parseInt(message1[4]),Integer.parseInt(message1[5]),new Color(Integer.parseInt(message1[6])));
				
			}
			else if(message1[1].equals("segment")) {
				curr=new Segment(Integer.parseInt(message1[2]),Integer.parseInt(message1[3]),Integer.parseInt(message1[4]),Integer.parseInt(message1[5]),new Color(Integer.parseInt(message1[6])));
				
			}
		
			sketch.addSketch(curr);
			
			
		}
		else if(message1[0].equals("move")) {
				curr=sketch.getShape(Integer.parseInt(message1[1]));
				if(curr!=null) {
					curr.moveBy(Integer.parseInt(message1[4]),Integer.parseInt(message1[5]));	
				}
				
		
		}
		else if(message1[0].equals("delete")) {
			int id = Integer.parseInt(message1[1]);
			curr=sketch.getShape(id);
			sketch.remove(id);
		
		}
		else if(message1[0].equals("recolor")) {
			curr=sketch.getShape(Integer.parseInt(message1[1]));
			
			curr.setColor(new Color(Integer.parseInt(message1[2])));
			
			
		}
		else if(message1[0].equals("update")) {
			
			
			if(message1[2].equals("polyline")){
				String[]points = message1[3].split(",");
				System.out.println(points);
				
				curr=new Polyline(new Point(Integer.parseInt(points[0]),Integer.parseInt(points[1])),new Color(Integer.parseInt(message1[4])));
				for(int i=0;i<points.length;i=i+2) {
					((Polyline) curr).setEnd(new Point(Integer.parseInt(points[i]),Integer.parseInt(points[i+1])));
				
				}
				sketch.addSketch(Integer.parseInt(message1[5]),curr);
			}
			else if(message1[2].equals("ellipse")) {
				curr=new Ellipse(Integer.parseInt(message1[3]),Integer.parseInt(message1[4]),Integer.parseInt(message1[5]),Integer.parseInt(message1[6]),new Color(Integer.parseInt(message1[7])));
				sketch.addSketch(Integer.parseInt(message1[8]),curr);
			}
			else if(message1[2].equals("rectangle")){
				curr = new Rectangle(Integer.parseInt(message1[3]),Integer.parseInt(message1[4]),Integer.parseInt(message1[5]),Integer.parseInt(message1[6]),new Color(Integer.parseInt(message1[7])));
				sketch.addSketch(Integer.parseInt(message1[8]),curr);
			}
			else if(message1[2].equals("segment")) {
				curr = new Segment(Integer.parseInt(message1[3]),Integer.parseInt(message1[4]),Integer.parseInt(message1[5]),Integer.parseInt(message1[6]),new Color(Integer.parseInt(message1[7])));
				sketch.addSketch(Integer.parseInt(message1[8]),curr);
			}
		
	
			
		
	}
		else if(message1[0].equals("start")) {
			
			temp=new Sketch();
    		sketch=temp;
    		
    		
    	}
		return sketch.getId(curr);
	
}
}
