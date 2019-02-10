import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

import jdk.nashorn.api.tree.Tree;


public class Compressor  {
	ArrayList<Integer> code = new ArrayList<Integer>();
	TreeComparator infoCompare =  new TreeComparator();
	PriorityQueue<BinaryTree<info>> infoPriorityQueue = new PriorityQueue<BinaryTree<info>>(infoCompare);
	public Character current;
	Map<Character, Integer> charDictionary = new HashMap<Character, Integer>() ;
	
	BinaryTree<info> cull ;
	

	public Compressor() {
		
	}

	//This method adds all the read characters into a dictionary and it calls queue
	public void addDictionary(Character current){
		
		if(charDictionary.containsKey(current)){ //Checks if the current current if in the dictionary.If its not there it adds the character else it just increases the frequency
			charDictionary.put(current,charDictionary.get(current)+1);
		}
		else {
			charDictionary.put(current, 1);
		}
		
	}
		
	//This method reads a file and adds it to character dictionary
	public void loadFile(String pathName) throws Exception  {
			
		BufferedReader input = new BufferedReader(new FileReader(pathName));
		int r;
		while ((r = input.read()) != -1) {
			char ch = (char) r;
			addDictionary(ch);           
		}
		input.close();		
	        
			}
		
	//This method take the keys int the dictionary it adds the keys to a queue
	public void queue() {

		for (Character key : charDictionary.keySet()){//Reads through every character in the Dictionary
			info infomation = new info (key,charDictionary.get(key));//Creates a new datatype info which holds the frequency and the name of the character
			BinaryTree<info> node = new BinaryTree<info>(infomation);//Craetes a node of Data type info
			infoPriorityQueue.add(node);//Adds the data to priority queue which holds nodes of Data type info
		}
		
	}
	///The encode method builds a single tree which contains nodes of Datatype info
	public Map<Character,String> encode() throws Exception { 
		
		while(infoPriorityQueue.size()!=1 && infoPriorityQueue.size()!=0) {
			//Removes nodes with the lowest frequency
			BinaryTree<info> T1=infoPriorityQueue.remove();
			BinaryTree<info> T2=infoPriorityQueue.remove();
			//Sums the frequency of the the nodes
			int sum =T1.getData().getFrequency() + T2.getData().getFrequency();
			info newfrequency = new info (null,sum);
			BinaryTree<info> T = new BinaryTree<info>(newfrequency,T1,T2);
			infoPriorityQueue.add(T);
			
		}
		
		return createCode(infoPriorityQueue.peek());
			
	}
	///Methods creates a map of charaters and their binary represantation with the help of a helper method and returns this to the main
	public Map<Character,String> createCode(BinaryTree<info> current ) {
		Map<Character, String> mapofbitcodes = new HashMap<Character,String>();
		if(current==null) {
			return mapofbitcodes;
		}
		if(current.size()==1) {
			String s ="0";
			mapofbitcodes.put(current.getData().getName(), s);
			return mapofbitcodes;				 
			}
		else {
			String s="";
			helper(mapofbitcodes,current, s);
			return mapofbitcodes;
		}	
				
	}
		
	//This is a helper method that traverse the string and gets the string represantations of Data and a adding the string to a map
	public 	void helper(Map<Character,String> mapofcodes,BinaryTree<info> Tree ,String s) {
			
		if(Tree.isLeaf()) {
			mapofcodes.put(Tree.getData().getName(), s);
			s = method(s);	
		}
		if (Tree.hasLeft()) {
			helper(mapofcodes,Tree.getLeft(),s+"0");		
		}
		if(Tree.hasRight()) {	
			helper(mapofcodes,Tree.getRight(),s+"1");	
		}
			
	}
		
	//This methods prints out the string represantaion of a binary tree during testing
	public String method(String str) {
		
	    if (str != null && str.length() > 0 ) {
		        str = str.substring(0, str.length() - 1);
		    }
		    System.out.println(str);
		    return str;
	}
		
	//This methods write the Bits to a fill as encodingby calling  the write file methhod
	public void writeTheBits(String pathName,String pathName2,Map<Character ,String> mapwithcodes) throws Exception{
			
			WriteFile(pathName,pathName2,mapwithcodes);
			
	}
	//This methods directly writes the compressed files as bits
	public void WriteFile(String pathName ,String pathName2,Map<Character,String> mapwithcodes) throws Exception  {
		try {
			BufferedReader input = new BufferedReader(new FileReader(pathName));///Creater Writer and Reader Objects
			BufferedBitWriter bitOutput = new BufferedBitWriter(pathName2);
			int r;		
			while ((r = input.read()) != -1) {
				char ch = (char) r;		
				String bitrep = mapwithcodes.get((Character)ch);//Get the bit represantation of each character form a mapp of codes
				for (char b : bitrep.toCharArray()) {///Loop over the string represantation to get bits
					if(b=='1') {
						bitOutput.writeBit(true);
					}
					else if (b=='0') {
						bitOutput.writeBit(false);
					}
						
			}
					
		        
		}
		input.close();//Close files
		bitOutput.close();
		}
		catch( IOException e) {
		System.out.println("Error in Loading Files");
		}
																			
	}
			
		
		//The methods decoresses a Hayman compressed file
		
	public void decompress(String decompressedPathName , String compressedPathName) throws Exception {
		//Try block catches errors
		try {
			BufferedBitReader bitInput = new BufferedBitReader(compressedPathName);//Create new BitReader objetc
			BufferedWriter output = new BufferedWriter(new FileWriter(decompressedPathName)); //Created a Writer object
			BinaryTree<info> tree = infoPriorityQueue.peek();//Get the tree in our priority Tree which contains all the elents in the file as leaves
			
			
		while (bitInput.hasNext()) {
			 boolean bit = bitInput.readBit();//Reads a compressed file
			///Traverse through tree to get with guidance of the bits from the compressed file
			if (bit ==true) {	
				tree = tree.getRight();
				if (tree.isLeaf()) {	  
					char name = tree.getData().getName();  
					output.write(name);
					tree=infoPriorityQueue.peek();
				}		  
			
			}
			else if(bit==false) {
				
				if ( infoPriorityQueue.peek().isLeaf()){  
					char name = tree.getData().getName();
					output.write(name);
				} 
				
				else if (tree.getLeft().isLeaf()) {					  
					  tree=tree.getLeft();
					  char name = tree.getData().getName();
						
					  output.write(name);
					  tree=infoPriorityQueue.peek();
				}
				else {
						tree=tree.getLeft();
						  				  
					  }
				
				}
			}
				  

			output.close();
			bitInput.close();
		}
		catch( IOException e) {
			System.out.println("Error in Loading Files");
		}
	}
		
}
		

				
			
		
		
					
				
			
			
		
	

	

	
	

