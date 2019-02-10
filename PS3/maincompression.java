import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;

public class maincompression {
		
	    static Map<Character,String> Code ;
		public static void main(String[] args) throws Exception {
			
			///Test Case 1 Reading an empty file Empty file
			Compressor Louis = new Compressor();//Create Compressor Object
			Louis.loadFile("/Users/louismurerwa/eclipse-workspace/cs10/PS3/emptyfile.txt") ;//Load the file to be compresessed
			Louis.queue();///Create a que of keys
			Code = Louis.encode();//Create a Binary tree of keys and get a mapp of binary represantations of characters
			
			Louis.writeTheBits("/Users/louismurerwa/eclipse-workspace/cs10/PS3/emptyfile.txt" , "/Users/louismurerwa/eclipse-workspace/cs10/PS3/compressedemptyfile.txt",Code);///Write Bits to A FILE
			Louis.decompress("/Users/louismurerwa/eclipse-workspace/cs10/PS3/decompressedemptyfile.txt", "/Users/louismurerwa/eclipse-workspace/cs10/PS3/compressedemptyfile.txt");//dECOMPRESS
			
				
			
			///Test Case 2 Reading a one  One Sentence file
			
			Compressor  OneSentence = new Compressor();
			OneSentence.loadFile("/Users/louismurerwa/eclipse-workspace/cs10/PS3/onesentence.txt") ;
			OneSentence.queue();
			Code = OneSentence.encode();
			
			OneSentence.writeTheBits("/Users/louismurerwa/eclipse-workspace/cs10/PS3/onesentence.txt" , "/Users/louismurerwa/eclipse-workspace/cs10/PS3/compressedonesentence.txt",Code);
			OneSentence.decompress("/Users/louismurerwa/eclipse-workspace/cs10/PS3/decompressedonesentence.txt", "/Users/louismurerwa/eclipse-workspace/cs10/PS3/compressedonesentence.txt");
			
				
			/// Case 3 Reading the USConstitution
			
			Compressor USConstitution = new Compressor();
			USConstitution.loadFile("/Users/louismurerwa/eclipse-workspace/cs10/PS3/USConstitution.txt") ;
			USConstitution.queue();
			Code = USConstitution.encode();
			USConstitution.writeTheBits("/Users/louismurerwa/eclipse-workspace/cs10/PS3/USConstitution.txt" , "/Users/louismurerwa/eclipse-workspace/cs10/PS3/compressedUSConstitution.txt",Code);
			USConstitution.decompress("/Users/louismurerwa/eclipse-workspace/cs10/PS3/decompressedUSConstitution.txt", "/Users/louismurerwa/eclipse-workspace/cs10/PS3/compressedUSConstitution.txt");
			
				
			// Case 4 War and Peace Book
		
			Compressor WarandPeace = new Compressor();
			WarandPeace .loadFile("/Users/louismurerwa/eclipse-workspace/cs10/PS3/WarandPeace.txt") ;
			WarandPeace .queue();
			Code = WarandPeace .encode();
			
			WarandPeace .writeTheBits("/Users/louismurerwa/eclipse-workspace/cs10/PS3/WarandPeace.txt" , "/Users/louismurerwa/eclipse-workspace/cs10/PS3/compressedWarandPeace.txt",Code);
			WarandPeace .decompress("/Users/louismurerwa/eclipse-workspace/cs10/PS3/decompressedWarandPeace.txt", "/Users/louismurerwa/eclipse-workspace/cs10/PS3/compressedWarandPeace.txt");
			
				
			}
}
				
			
			
		