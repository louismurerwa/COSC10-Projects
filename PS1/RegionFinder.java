import java.awt.*;
import java.awt.image.*;
import java.util.*;

/**
 * Region growing algorithm: finds and holds regions in an image.
 * Each region is a list of contiguous points with colors similar to a target color.
 * Scaffold for PS-1, Dartmouth CS 10, Fall 2016
 * 
 * @author Louis Murerwa, Spring 2018 (based on a very different structure from Fall 2012)
 * @author Travis W. Peters, Dartmouth CS 10, Updated Winter 2015
 * @author CBK, Spring 2015, updated for CamPaint
 */
public class RegionFinder {
	private static final int maxColorDiff = 1000;				// how similar a pixel color must be to the target color, to belong to a region
	private static final int minRegion = 50;//minimum region of points
	private BufferedImage visited;//
	private BufferedImage image;   // the image in which to find regions
	private BufferedImage largeRecoloredImage; //image with largest regions painted
	private BufferedImage recoloredImage;// the image with identified regions recolored
	private ArrayList<ArrayList<Point>> regions ;// a region is a list of points
	
	

	public RegionFinder() {  //Constructor
		this.image = null;
	}

	public RegionFinder(BufferedImage image) {//Constructor
		this.image = image;		
	}

	public void setImage(BufferedImage image) { //Set Image
		this.image = image;
	}

	public BufferedImage getImage() {  //Get Image
		return image;
	}

	public BufferedImage getRecoloredImage() {//Get recoloured image
		return recoloredImage;
	}
	
	public BufferedImage getLargeRecoloredImage() {//Get recoloured image
		return largeRecoloredImage;
	}

	/**
	 * Sets regions to the flood-fill regions in the image, similar enough to the trackColor.
	 */
	public void findRegions(Color targetColor) {
		// TODO: YOUR CODE HERE
		
		regions = new ArrayList<ArrayList<Point>>();
		int cx=0; int cy=0;
		visited = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++){
				Color c1 = new Color(image.getRGB(x,y));
				Color c2 = targetColor;
				
				if (visited.getRGB(x, y) == 0 && colorMatch(c1, c2)) {
					ArrayList<Point>region = new ArrayList<Point>();
					ArrayList<Point>needToCheck = new ArrayList<Point>();
					cx = x; cy = y;
					Point Louis = new Point (cx,cy);
					region.add(Louis);
					needToCheck.add(Louis);
					visited.setRGB(x, y, 1);
					
					while (needToCheck.size()!=0) {
						
						Point current = needToCheck.get(0);
						if(bounds(current.x+1,current.y) &&(visited.getRGB(current.x+1,current.y)==0)) {

							Color test = new Color(image.getRGB(current.x+1,current.y));
							if (colorMatch(test,targetColor)){
								region.add(new Point(current.x+1,current.y));
								needToCheck.add(new Point(current.x+1,current.y));
								visited.setRGB(current.x+1,current.y,1);
								
							}
						}
						if(bounds(current.x-1,current.y)&&(visited.getRGB(current.x-1,current.y)==0)) {

							Color test1 = new Color(image.getRGB(current.x-1,current.y));
							if (colorMatch(test1,targetColor)){
								region.add(new Point(current.x-1,current.y));
								needToCheck.add(new Point(current.x-1,current.y));
								visited.setRGB(current.x-1,current.y,1);
							}
						}
						if (bounds(current.x,current.y+1)&&(visited.getRGB(current.x,current.y+1)==0)) {
								
							Color test2 = new Color(image.getRGB(current.x,current.y+1));
							if (colorMatch(test2,targetColor)){
								region.add(new Point(current.x,current.y+1));
								needToCheck.add(new Point(current.x,current.y+1));
								visited.setRGB(current.x,current.y+1,1);
							}
						}
								
								
						if(bounds(current.x,current.y-1)&&(visited.getRGB(current.x,current.y-1)==0)) {

							Color test3 = new Color(image.getRGB(current.x,current.y-1));
							if (colorMatch(test3,targetColor)){
								region.add(new Point(current.x,current.y-1));
								needToCheck.add(new Point(current.x,current.y-1));
								visited.setRGB(current.x,current.y-1,1);
							}
						}
						
					needToCheck.remove(0);
				}
					regions.add(region);
			}
		}
	}

	}


	/**Checks if a point is in the bounds of the image
	 * 
	 * @param x
	 * @param y
	 * @return
	 */

	private boolean bounds(int x, int y) {
		if(x < visited.getWidth() && (x>0) && (y<visited.getHeight()) && (y>0)) {
			return true;
		}
		return false;
	}
			
		
	




	/**
	 * Tests whether the two colors are "similar enough" (your definition, subject to the maxColorDiff threshold, which you can vary).
	 */
	private static boolean colorMatch(Color c1, Color c2) {
		// TODO: YOUR CODE HERE
		int d = (c1.getRed() - c2.getRed()) * (c1.getRed() - c2.getRed())
				+ (c1.getGreen() - c2.getGreen()) * (c1.getGreen() - c2.getGreen())
				+ (c1.getBlue() - c2.getBlue()) * (c1.getBlue() - c2.getBlue());
			
		if (d < maxColorDiff) {
			return true;
			}
		else {
			return false;
		}
		
			
			
		
	}

	/**
	 * Returns the largest region detected (if any region has been detected)
	 */
	public ArrayList<Point> largestRegion() {
		ArrayList<Point> largest = new ArrayList<Point>();
		
		// TODO: YOUR CODE HERE
		for (ArrayList<Point> region : regions ) {
			if (region.size() > largest.size()) {
				largest=region;
				
			}
		}
		 return largest;
		}
	

	/**
	 * Sets recoloredImage to be a copy of image, 
	 * but with each region a uniform random color, 
	 * so we can see where they are
	 */
	public void recolorImage() {
		// First copy the original
		recoloredImage = new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null);
		// Now recolor the regions in it
		// TODO: YOUR CODE HERE
		for (ArrayList<Point> group : regions) {
			if(group.size()>minRegion) {
				Color random = new Color((int)(Math.random() *0x16777216));
				Color red = new Color(255, 0, 0);
				for(int i=0 ; i < group.size() ; i++) {
					recoloredImage.setRGB(group.get(i).x, group.get(i).y,random.getRGB());
					}
				}
			
			}
		}
		
	/**Recolors the largest regions in an image
	 * @param largest
	 * @param brush
	 */
	public void recolorLargestImage(ArrayList<Point> largest, Color brush) {
		// First copy the original
		 largeRecoloredImage = new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null);
		// Now recolor the regions in it
		// TODO: YOUR CODE HERE
		
		for (Point group : largest) {
			 largeRecoloredImage.setRGB(group.x, group.y,brush.getRGB());
			
			
				
				
		}
	}
}
