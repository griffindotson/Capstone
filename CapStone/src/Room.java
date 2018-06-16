public class Room {
	
	int id;
	String description;
	String[] options;
	int[] numKey;
	int percentage;
	
	public Room(int id, int percentage, String description, String[] options, int[] numKey) {
		this.id = id;
		this.description = description;
		this.options = options;
		this.numKey = numKey;
		this.percentage = percentage;
	}
	
	public void displayRoom() {
		System.out.println(this.description);
		for(int i = 0;i<this.options.length;i++) {
			System.out.print(i+" ");
			System.out.println(options[i]);
		}
	}
	
	public int move(int choice) {
		int nextRoom = this.id;
		if(choice >= 0 && choice < numKey.length) {
			nextRoom = this.numKey[choice];
		}
		else {
			System.out.println("You look around but failed to find new path, pick again!");
		}
		return nextRoom;
	}

}
