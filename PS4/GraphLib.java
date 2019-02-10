import java.util.*;

/**
 * Library for graph analysis
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2016
 * 
 */
public class graphLib{
	
	public static <V,E> Graph<V,E> bfs(Graph<V,E> g, V source){
//		enqueue the start vertex s onto the queue
//		remember that s has been added
//		repeat until we find the goal vertex or the queue is empty:
//		  dequeue the next vertex u from the queue
//		    (maybe do something while here)
//		    for all vertices v that are adjacent to u
//		      if haven't already added v
//		        enqueue v onto the queue
//		        remember that v has been added
	
			Graph<V,E>backTrack = new AdjacencyMapGraph<V,E>(); //initialize backTrack
			backTrack.insertVertex(source); //load start vertex with null parent
			Set<V> visited = new HashSet<V>(); //Set to track which vertices have already been visited
			Queue<V> queue = new LinkedList<V>(); //queue to implement BFS
			
			queue.add(source); //enqueue start vertex
			visited.add(source); //add start to visited Set
			while (!queue.isEmpty()) { //loop until no more vertices
				V u = queue.remove(); //dequeue
				for (V v : g.outNeighbors(u)) { //loop over out neighbors
					if (!visited.contains(v)) { //if neighbor not visited, then neighbor is discovered from this vertex
						visited.add(v); //add neighbor to visited Set
						queue.add(v); //enqueue neighbor
						backTrack.insertDirected(v, u, null); //save that this vertex was discovered from prior vertex
					}
				}
			}
			
			return backTrack;
		}
		
	
	
	public static <V,E> List<V> getPath(Graph<V,E> tree, V v){
		
		if (tree.numVertices()==0 || !tree.hasVertex(v) ) {
			System.out.println("Tree has no vertex " + v );
			return new ArrayList<V>();
		}
		//make sure end vertex in backTrack
		if (!tree.hasVertex(v)) {
			return new ArrayList<V>();
		}
		//start from end vertex and work backward to start vertex
		ArrayList<V> path = new ArrayList<V>(); //this will hold the path from start to end vertex
		V current = v; //start at end vertex
		//loop from end vertex back to start vertex
		while (current != null) {
			path.add(0,current); //add this vertex to front of arraylist path
			for( V vertex :tree.outNeighbors(current)) {
			current = vertex ; //get vertex that discovered this vertex
			}
		}
		System.out.println(path);
		return path;
	}
	
	
	public static <V,E> Set<V> missingVertices(Graph<V,E> graph, Graph<V,E> subgraph){
		Set<V> vertices = new HashSet<V>();
		for(V vertice :graph.vertices()) {
			vertices.add(vertice);
		}
		for(V vertice:subgraph.vertices()) {
			vertices.remove(vertice);
		}
			return vertices;
		
	}
	public static <V,E> double averageSeparation(Graph<V,E> tree, V root) {
		int numsteps=1;
		for(V v : tree.inNeighbors(root)) {
			numsteps=numsteps+1;
			averageSeparation(tree,v);
		}
		numsteps=numsteps/tree.numVertices();
		return numsteps;	
	}
}


	

