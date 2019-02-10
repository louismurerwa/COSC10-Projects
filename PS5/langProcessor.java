import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class langProcessor {
	//Initialize Data structures to be used in the language processing class
    
    Map < String, Map < String, Integer >> transitionstags;
    Map < String, Map < String, Integer >> transitionswords;
    Map < String, Map < String, Double >> trainedtags;
    Map < String, Map < String, Double >> trainedwords;
    ArrayList < String > testTags;
    ArrayList < String > path;
    
    //This method loads the words in a sentence into an array
    public ArrayList < String > loadSentences() {
    	
        ArrayList < String > testSentences = new ArrayList < String > ();
        BufferedReader input = null;

        try {
            input = new BufferedReader(new FileReader("/Users/louismurerwa/eclipse-workspace/cs10/PS5/brown-train-sentences.txt"));
            String line;
            while ((line = input.readLine()) != null) {
                String ids2 = line.toLowerCase();
                String idsl[] = ids2.split(" ");
                testSentences.add("#");
                for (int i = 0; i < idsl.length; i++) {
                    testSentences.add(idsl[i]);
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
        try {
            input.close();
        } catch (IOException e) {
            
            e.printStackTrace();
        }
       
        return testSentences;
    }

    //This methods reads and loads tags into an ArrayList 
    public ArrayList < String > loadTags() {

        testTags = new ArrayList < String > ();
        BufferedReader inputWords = null;
        
        try {
            inputWords = new BufferedReader(new FileReader("/Users/louismurerwa/eclipse-workspace/cs10/PS5/brown-train-tags.txt"));
            String line;
            while ((line = inputWords.readLine()) != null) {
                String ids1 = line.toLowerCase();
                String ids[] = ids1.split(" ");
                testTags.add("#");
                for (int i = 0; i < ids.length; i++) {

                    testTags.add(ids[i]);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputWords.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            inputWords.close();
        } catch (IOException e) {
           
            e.printStackTrace();
        }
       
        return testTags;
    }

    //This methods trains our program to recognize Tags
    public void train(ArrayList < String > testtags, ArrayList < String > testSentence) {
    	
        //Data structures to facillitate training
        transitionstags = new HashMap < String, Map < String, Integer >> ();
        transitionswords = new HashMap < String, Map < String, Integer >> ();
        
        //Counter keeps track of the nexttag and word in the testTags and testSentence ArrayList
        int i = 0;
        
        
        //Loop through the tags array to grab each tag
        for (String tag: testtags) {
        	
            //Counter is incremented to keep track of next tag
            if (i != testtags.size() - 2) {
                i = i + 1;
            
                //Check if the current tags exists in the transitions map
                if (transitionstags.containsKey(tag)) {
                    
                    //if the current tag exists in the transitions we check if the transition to the next tag has been made before
                    if (transitionstags.get(tag).containsKey(testtags.get(i))) {
                        //if we have made the transition before ,we increment the frequency by 1
                        Integer current = transitionstags.get(tag).get(testtags.get(i)) + 1;
                        transitionstags.get(tag).put(testtags.get(i), current);
                    } else {
                        // if we have not transitioned to the next state before we put it into the map
                        transitionstags.get(tag).put(testtags.get(i), 1);
                    }
                } 
                else {
                    // if this is our first time encountering this tag we put it into the transitions map
                    String next = testtags.get(i);
                    Map < String, Integer > nexttag = new HashMap < String, Integer > ();
                    nexttag.put(next, 1);
                    transitionstags.put(tag, nexttag);
                }
            }
        }

        int i1 = 0;
        for (String word: testSentence) {

            //Check if the current word exists in the transitions map
            if (transitionswords.containsKey(testtags.get(i1))) {
                //if the current word exists in the transitions we check if the transition to the next tag has been made before
                if (transitionswords.get(testtags.get(i1)).containsKey(word)) {
                    //if we have made the transition before ,we increment the frequency by 1
                    Integer current = transitionswords.get(testtags.get(i1)).get(word) + 1;
                    transitionswords.get(testtags.get(i1)).put(word, current);
                } else {
                    // if we have not transitioned to the next state before we put it into the map
                    transitionswords.get(testtags.get(i1)).put(word, 1);
                }
            } 
            else {
                // if this is our first time encountering this tag we put it into the transitions map
                String next = word;
                Map < String, Integer > nexttag = new HashMap < String, Integer > ();
                nexttag.put(next, 1);
                transitionswords.put(testtags.get(i1), nexttag);
            }
            //Counter is incremented to keep track of next tag

            i1 = i1 + 1;


        }

        //This map holds  Map<Tag,Map<Tag,Probability>>
        trainedtags = new HashMap < String, Map < String, Double >> ();

        //Loop through the tags in the training arraylist
        for (String tag: transitionstags.keySet()) {

            Integer numberoftransitions = 0;
            for (String current: transitionstags.get(tag).keySet()) {
                numberoftransitions = numberoftransitions + transitionstags.get(tag).get(current);
            }
          //Calculate the probability of transition
            Map < String, Double > nextstate = new HashMap < String, Double > ();
            for (String currenttag: transitionstags.get(tag).keySet()) {

                Double probability = (double)(transitionstags.get(tag).get(currenttag)) / (double)(numberoftransitions);
                nextstate.put(currenttag, Math.log(probability));
            }

            trainedtags.put(tag, nextstate);
        }
        
        trainedwords = new HashMap < String, Map < String, Double >> ();
        //Loop through the tags in the training file
        for (String tag: transitionswords.keySet()) {

            Integer numberoftransitions = 0;
            for (String current: transitionswords.get(tag).keySet()) {
                numberoftransitions = numberoftransitions + transitionswords.get(tag).get(current);
            }
          //Calculate the probability of transition
            Map < String, Double > nextstate = new HashMap < String, Double > ();
            for (String currenttag: transitionswords.get(tag).keySet()) {

                Double probability = (double)(transitionswords.get(tag).get(currenttag)) / (double)(numberoftransitions);
                nextstate.put(currenttag, Math.log(probability));


            }
            trainedwords.put(tag, nextstate);


        }

    }
//This method takes in the observations from the test-sentences-file and creates an array list of the input words
    public ArrayList < String > input() {

        ArrayList < String > observations = new ArrayList < String > ();

        BufferedReader inputWords = null;
        try {
            inputWords = new BufferedReader(new FileReader("/Users/louismurerwa/eclipse-workspace/cs10/PS5/brown-test-sentences.txt"));
            String line;


            while ((line = inputWords.readLine()) != null) {
                String ids1 = line.toLowerCase();
                String ids[] = ids1.split(" ");
                observations.add("#");
                for (int i = 0; i < ids.length; i++) {

                    observations.add(ids[i]);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputWords.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            inputWords.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return observations;
    }


//Decode uses the Virtebi Algorithm to tag the input sentence


    public void decode(ArrayList < String > observation) {
    	//Ude virtebi to decode a sentence
        ArrayList < Map < String, String >> backPedal = new ArrayList < Map < String, String >> ();
        Set < String > currStates = new HashSet < String > ();
        currStates.add("#");
        Map < String, Double > currScores = new HashMap < String, Double > ();
        currScores.put("#", 0.0);
        for (int i = 0; i < observation.size() - 1; i++) {
            Set < String > nextStates = new HashSet < String > ();
            Map < String, Double > nextScores = new HashMap < String, Double > ();
            Map < String, String > backtrace = new HashMap < String, String > ();

            for (String currentState: currStates) {
                for (String nextState: trainedtags.get(currentState).keySet()) {

                    nextStates.add(nextState);
                    Double nextScore;

                    if (!trainedwords.get(nextState).containsKey(observation.get(i + 1))) {

                        nextScore = (double) currScores.get(currentState) + // path to here
                            trainedtags.get(currentState).get(nextState) -
                            100; // take a step to there


                    } else {

                        nextScore = (double) currScores.get(currentState) + // path to here
                            trainedtags.get(currentState).get(nextState) +
                            trainedwords.get(nextState).get(observation.get(i + 1)); // take a step to there

                    }

                    if (!nextScores.containsKey(nextState) || nextScore > nextScores.get(nextState)) {
                        nextScores.put(nextState, nextScore);
                        backtrace.put(nextState, currentState);
                        

                    }

                }

            }

            backPedal.add(backtrace);
            currStates = nextStates;
            currScores = nextScores;



        }

        // get largest score in current score map
        String biggestScore = null;

        for (String k: currScores.keySet()) {
            if (biggestScore == null) {
                biggestScore = k;
            } else if (currScores.get(k) > currScores.get(biggestScore)) {
                biggestScore = k;

            }

        }

        // follow its back pointers
        path = new ArrayList < String > ();
        String current = biggestScore;
        for (int i1 = backPedal.size() - 1; i1 > -1; i1--) {
        	//Add then state with the biggest score to the ArrayList path
            path.add(0, current);
            current = backPedal.get(i1).get(current);
        }
        // print the path to the console
        for (int i2 = 0; i2 < path.size(); i2++) {
            if (path.get(i2).equals(".")) {

            } else if (path.get(i2).equals("#")) {
                System.out.println(" ");

            } else {
                System.out.print(path.get(i2).toUpperCase() + " ");
            }
        }

       

    }



    //This methods takes input from the command line and tags it by calling the decode method
    public ArrayList<String> entercommands(langProcessor sudiChild) {
    	ArrayList<String> inputs = new ArrayList<String>();
    	System.out.println(" ");
        System.out.println("Commands:\n" +
            "c:Enter Sentences To Tag From The Command Line");
        Scanner in = new Scanner(System.in);
        while (true) {
        	
            System.out.print(":>");
            String command = in .nextLine();
            String key[] = command.split(" ");
            inputs.add("#");
            for (int i = 0; i < key.length; i++) {

                inputs.add(key[i]);
                
            }
               if(inputs.size()==0) {
            	   System.out.println("Come on enter a valid input");
               }
               else {
            	   sudiChild.decode(inputs);
            	   inputs = new ArrayList<String>();
            	   System.out.println(" ");
               }
                
            }
        }
    //The performance algorithm takes tags from the test tags file and compares them with our output to dertemine the perfomance of our algorithim
    public void perfomance() {

        ArrayList < String > perfomance = new ArrayList < String > ();
        BufferedReader input = null;
        //Reads and stores the correct tags into the perfomance arralyist
        try {
            input = new BufferedReader(new FileReader("/Users/louismurerwa/eclipse-workspace/cs10/PS5/brown-test-tags.txt"));
            String line;
            while ((line = input.readLine()) != null) {
                String ids2 = line.toLowerCase();
                String idsl[] = ids2.split(" ");
                perfomance.add("#");
                for (int i = 0; i < idsl.length; i++) {
                    perfomance.add(idsl[i]);
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
        try {
            input.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //Checks for matches in our path arraylist and the test tags.If the tags match at a particular index it increments the number of matches
        Integer matches = 0;
        Integer notmatches = 0;
        Integer start=0;
        for (int i = 0; i < path.size(); i++) {
            if(perfomance.get(i + 1).equals("#")) {
            	start=start+1;
        	}
        	else if (perfomance.get(i + 1).equals(path.get(i))) {
                matches = matches + 1;
            } else {
                notmatches = notmatches + 1;

            }
        }
        //	Prints out the number of matches in our file
        System.out.println("");
        System.out.println("This complex algorthim found " + matches+ " matches and " + notmatches + " non-matches tags in this trial");

    }


    public static void main(String[] args) throws Exception {
    	
    	//Create the language processor object named sudi
        langProcessor sudi = new langProcessor();
        //Load the tags and words from the input files
        ArrayList < String > Tags = sudi.loadTags();
        ArrayList < String > Words = sudi.loadSentences();
        //Train the languages processor object to recognize words and tag them
        sudi.train(Tags, Words);
        //Run decode to tag the input sentence from our files
        sudi.decode(sudi.input());
        //Check for our performance of the tagging process
        sudi.perfomance();
        
        //Create a second language processor object that reads input from the command line
        langProcessor sudiChild = new langProcessor();
        //Load the tags and words from the input files
        ArrayList < String > TagsChild = sudiChild.loadTags();
        ArrayList < String > WordsChild = sudiChild.loadSentences();
        //Train the languages processor object to recognize words and tag them
        sudiChild.train(TagsChild, WordsChild);
        //Run entercommands  to enter words and tag the input sentence from our files
        sudiChild.entercommands(sudiChild);
        
        
        
    }



}