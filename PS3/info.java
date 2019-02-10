//This class represents a data type info thats hold a frequency and a name.
public class info {
	Character name ;
	Integer frequency;
	//Creates an info object
	public  info(Character a,Integer b){
		this.name=a;
		this.frequency=b;
	}
	//Allows to chacnge name
	public void addName(Character a) {
		this.name=a;
	}
	//Allows to change frequency
	public void addFrequency(Integer a) {
		this.frequency=a;
	}
	//Allows users to get name
	public Character getName() {
		return this.name;
	
	}
	//Allows users to get the frequency
	public Integer getFrequency() {
		return this.frequency;
	
	}
	@Override
	//Prints out the stirng of the data
	public String toString() {
		String s = name +" and "+frequency;
		return s;
	}
}
