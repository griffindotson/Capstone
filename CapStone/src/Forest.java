import java.util.ArrayList;

public class Forest extends Area {
	int currentRoom = 0;
	
	//Enemies in the area
	Rat boss = new Rat();
	Rat rat = new Rat();
	Rat rat2 = new Rat();
	Rat rat3 = new Rat();
	
	Wolf wolf = new Wolf();
	Wolf wolf2 = new Wolf();
	Wolf wolf3 = new Wolf();
	
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	//Create the separate rooms of the area and put them in an array
	String[] options1 ={"Return to Town", "Look around", "Move further into the forest"};
	int[] numKey = {-2,-3,1};
	Room entrance = new Room(0, 30,"You are at the begining of the forest just outside of town.", 
			options1, numKey);
	
	String[] options2 = {"Turn back", "Look around", "Go deeper into the forest"};
	int[] numKey2 = {0,-3,2};
	Room middle = new Room(1, 40,"You are in the middle of the forest, the town is now out of site.",
			options2, numKey2);
	
	String[] options3 = {"Turn back", "Look around", "Enter the cave"};
	int[] numKey3 = {1,-2,-1};
	Room exit = new Room(2, 30,"You have ventured further into the forest and have discovered a cave", 
			options3,numKey3);
	
	//The array of rooms creates the entire area of the world 
	Room[] area = {entrance, middle, exit};
	
	
	public Forest() {
		enemies.add(wolf);
		enemies.add(wolf2);
		enemies.add(wolf3);
		
		enemies.add(rat);
		enemies.add(rat2);
		enemies.add(rat3);
	}
	
	@Override 
	public void display() {
		//Displays the description of room and the options the player can make
		area[currentRoom].displayRoom();
	}
	
	
	@Override
	public int move(String input) {
		//Takes players option and displays the next room
		int in = -1;
		
		try {
			in = Integer.parseInt(input);
		}
		catch (NumberFormatException e){
			System.out.println("Not a valid move");
		}

		if(in >= area[currentRoom].options.length) {
			System.out.println("Failed to find new path");
		}else {
			if(area[currentRoom].move(in) >= 0) {
				currentRoom = area[currentRoom].move(in);
				display();
				return currentRoom;
			}
			else {
				return area[currentRoom].move(in);
			}
		}
		return area[currentRoom].move(in);
		
	}
	
	@Override
	public int perct() {
		return area[currentRoom].percentage;
	}
	@Override
	public Enemy boss() {
		return boss;
	}
	@Override
	public ArrayList<Enemy> enemyList(){
		return enemies;
	}
	

}
