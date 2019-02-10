import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class fileReader {
	//Reads the movie and actor test and returns maps.
	public static Map<String,String> loadActors(String pathName) throws Exception  {
		BufferedReader input = null ;
		Map<String,String> idsactors = new HashMap<String,String>();
		try {
			input = new BufferedReader(new FileReader(pathName));

			String line;
			while ((line = input.readLine()) != null) {
				String[] ids = line.split("\\|");
				idsactors.put(ids[0],ids[1]);
	        
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return idsactors;
	}
	//Reads the maovie actor file and returns a movie actor map.
	public static Map<String, ArrayList<String>> loadMovieActors(String pathName) throws Exception  {
		BufferedReader input = null ;
		@SuppressWarnings("rawtypes")
		Map<String, ArrayList<String>> movieactors = new HashMap<String,ArrayList<String>>();
		try {
			input = new BufferedReader(new FileReader(pathName));

			String line;
			while ((line = input.readLine()) != null) {
				String ids [] = line.split("\\|");
				if(!movieactors.containsKey(ids[0])) {
					ArrayList<String> actors = new ArrayList<String>();
					actors.add(ids[1]);
					movieactors.put(ids[0],actors);
				}
				else {
					ArrayList<String> actors = movieactors.get(ids[0]);
					actors.add(ids[1]);
					movieactors.put(ids[0],actors);
				}	
	        
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return movieactors;
	}
	
	
	
}
