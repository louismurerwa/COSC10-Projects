

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
public class playGame {
	//Thsi method is responsible for the gaming interface
	public static <V,E> void getlist( Graph<V,E> actors) throws Exception {
		Graph<V,E> universe = null;
		  System.out.println("Commands:\n" + 
		  		"c : list top (positive number) or bottom (negative) centers of the universe, sorted by average separation\n" + 
		  		"dlow or dhigh : list actors sorted by degree, with degree between low and high\n" + 
		  		"i: list actors with infinite separation from the current center\n" + 
		  		"p press enter then enter the <Vertexname>: find path from <name> to current center of the universe\n" + 
		  		"slow or shigh: list actors sorted by non-infinite separation from the current center, with separation between low and high\n" + 
		  		"u  press enter and then enter <Vertexname>: make <name> the center of the universe\n" + 
		  		"q: quit game");
		  Scanner in = new Scanner(System.in);
		  while (true) {
			  System.out.print(">");
			  String command = in.nextLine();
			  String key[] =command.split(",");
			  
			  //Pressing c calls the list top method to return sorted items
			  if(command.equals("c")) {
				  listtop(actors);  
			  }
			  //Returns actors sorted by their number of neighbours
			  else if(command.equals("dlow")){
				  
				  degree(actors,"low");
				  
			  }
			  //Returns actors sorted by their number of neigbours
			  else if(command.equals("dhigh")) {
				  degree(actors,"high");
			  }
			  //Returns actors with infinite separation from the center actor
			  else if(command.equals("i")) {
				  infinite(actors,universe);
			  }
			  //Returns the path from enterd actor to the current center
			  else if(key[0].equals("p")) {
				  System.out.print("Enter Vertexname>");
				  String input = in.nextLine();
				  V inputs = (V) input;
				  path(universe,inputs);
			  }
			  //Sets the center to the input vertex
			  else if(key[0].equals("u")) {
				  System.out.print("Enter Vertexname>");
				  String input = in.nextLine();
				  V inputs = (V) input;
				  universe = center(actors,inputs);
			
			  }
			  //Prints the actors sorted by their non - infinite distance from  the current center
			  else if (command.equals("shigh")) {
				  separation(universe,"high");
			  }
			  else if (command.equals("slow")) {
				  separation(universe,"low");
			  }
			  //Quits the aplication from running
			  else if (command.equals("q")) {
				  quit();
		  }
		}
	}
	
	//Returns sorted vertices		
	public static <V,E> void listtop( Graph<V,E> actors) {
		ArrayList<V> listofvertices = new ArrayList<V>();
		for(V current:actors.vertices()) {
			listofvertices.add(current);
		}
		//Sorts vertices with respect to average separation
		System.out.println("Sorted actors by average separation");
		System.out.println("Loading please be patient this is a complicated calculation");
		listofvertices.sort( (n1,n2)-> (int) graphLibrary.averageSeparation(graphLibrary.bfs(actors,n2),n2)-(int)graphLibrary.averageSeparation(graphLibrary.bfs(actors,n1),n1));
		System.out.println(listofvertices);
		
		
		
	}
	//Sorts the neighbours with respect to the number of neighbours
	public static <V, E> void degree(Graph<V,E> actors,String degree) {
		ArrayList<V> listofvertices = new ArrayList<V>();
		for(V current:actors.vertices()) {
			listofvertices.add(current);
			}
		//Returns actors sorted from lowest to highest
		if(degree.equals("low")) {
			 System.out.println("Vertices sorted with respect to the number of neighbours");
			  listofvertices.sort( (n1,n2)-> (int) actors.inDegree(n1)-(int) actors.inDegree(n2));
			  System.out.println(listofvertices);
		 }
		//Returns actors sorted from lowest to highest
		else if (degree.equals("high")) {
			listofvertices.sort( (n1,n2)-> (int) actors.inDegree(n2)-(int) actors.inDegree(n1));
			System.out.println("Vertices sorted with respect to the number of neighbours");
			System.out.println(listofvertices);
		}
	}
	
	//Returns actors which have an infinite disttance from current actor
	public static <V, E> void infinite(Graph<V,E> actors , Graph<V,E> input) {
		//Checks if input is null to prevent errors
		if(input==null) {
			System.out.println("Set center first by pressing using the command u");
		}
		//Uses sets to remove intems which are in one set but not the other 
		else {
			System.out.println("Vertex with infinite separation from current center");
			Graph<V,E> current = input;
			Set<V> infinite = graphLibrary.missingVertices(actors,current );

			System.out.println(infinite);
		}
	 }	
	//Returns path from input vertex to the current center vertex
	public static <V, E> void path(Graph<V,E> actors , V input) {
		if(actors==null) {
			System.out.println("Add the center of the universe first using the command u");
		}
		else {
		System.out.println( input + " number is "+(graphLibrary.getPath(actors,input).size()-1));
		System.out.println("This is the path to the center");
		System.out.println(graphLibrary.getPath(actors,input));
		}
	}	
	//Takes an input vertex and sets it as a center and finally it returns a tree with the centerd vertex
	public static <V, E> Graph<V, E> center( Graph<V,E> actors,V input) {
		Graph<V,E> current=null ;
		
		if(actors.hasVertex(input)) {
			current = graphLibrary.bfs(actors,input);
			System.out.println("Center set to "+input+" who is connected to "+(current.numVertices()-1)+"/9235 with an average separation of "+graphLibrary.averageSeparation(current, input));
			return current;
		}
		else {
			System.out.println("Add valid center vertex in the form Vertexname");
			return current;
		}
		
	}
	//Calclates the average separation between vertices and returns them sorted in this order.
	public static <V,E> void separation(Graph<V,E> currenttree,String input){
		
		ArrayList<V> listofvertices = new ArrayList<V>();
		if(currenttree==null) {
			System.out.println("Add the center of the universe first using the command u");
		}
		
		else if(input.equals("low")) {
			for(V current:currenttree.vertices()) {
				listofvertices.add(current);
				}
			listofvertices.sort( (n1,n2)-> (int) graphLibrary.averageSeparation(graphLibrary.bfs(currenttree,n1),n1)-(int)graphLibrary.averageSeparation(graphLibrary.bfs(currenttree,n2),n2));
			System.out.println(listofvertices);
		}
		else if(input.equals("high")) {
			for(V current:currenttree.vertices()) {
				listofvertices.add(current);
				}
			listofvertices.sort( (n1,n2)-> (int) graphLibrary.averageSeparation(graphLibrary.bfs(currenttree,n2),n2)-(int)graphLibrary.averageSeparation(graphLibrary.bfs(currenttree,n1),n1));
			System.out.println(listofvertices);
		}
	}
	//Quits the aplication
	public static void quit() {
		Runtime.getRuntime().exit(1);
		
	}
	
	
}

