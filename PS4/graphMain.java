import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class graphMain {
	

	public static void main(String [] args) throws Exception {
		//Calls the file reader to read the files and create the maps of movie,actors
		Graph<String, String> actors = new AdjacencyMapGraph<String, String>();
		Map<String,String> actorMap=fileReader.loadActors("/Users/louismurerwa/eclipse-workspace/cs10/ps4/actors.txt");
		Map<String,String> movieMap=fileReader.loadActors("/Users/louismurerwa/eclipse-workspace/cs10/ps4/movies.txt");
		Map<String, ArrayList<String>> movieActorsMap=fileReader.loadMovieActors("/Users/louismurerwa/eclipse-workspace/cs10/ps4/movie-actors.txt");

		//Loops and inserts all the actors in a vertex
		for(String id:actorMap.keySet()) {
			actors.insertVertex(actorMap.get(id));
			
		}
		//Adds the vertices of the graph as movies
		for(String movie: movieActorsMap.keySet()) {
			
			ArrayList<String> list_of_actors = movieActorsMap.get(movie);
			for(String actor:list_of_actors) {
				for(int i = 0;i < list_of_actors.size();i++) {
					actors.insertUndirected(actorMap.get(actor),actorMap.get((list_of_actors.get(i))),movieMap.get(movie));
				}
			}
			
		}
		//Tests
		Graph<String,String> current = graphLibrary.bfs(actors,"Kevin Bacon");
		graphLibrary.getPath(current, "Alice");
		graphLibrary.missingVertices(actors,current );
		graphLibrary.averageSeparation(current, "Kevin Bacon");
		//Playing the game
		playGame.getlist(actors);
			
			
			
		
	}
	
	
	
}
