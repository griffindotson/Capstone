import java.util.ArrayList;

public class Castle extends Area {
	int currentRoom = 0;
	Knight boss = new Knight();
	boolean bossDefeated = false;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	//Create the separate rooms of the area and put them in an array
		String[] options1 ={"Return to Town","Enter the castle"};
		int[] numKey = {-2,1};
		Room gate = new Room(0, 0,"After the long journey you have finally arrived at the royal castle."
				+ "It is time to prove yourself as a knight", 
				options1, numKey);
		
		String[] options2 = {"Turn back", "Begin the test"};
		int[] numKey2 = {0,2};
		Room finalFight = new Room(1, -1,"To become a knight you must be tested"
				+ "prove yourself by winning a fight against a knight",
				options2, numKey2);
	
	Room[] area = {gate,finalFight};
	
	
	public Castle() {
		
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
				if(bossDefeated == true) {
					currentRoom = 2;
				}
				if(currentRoom == 2) {
					//Stops the game when player wins
					System.out.println("You have completed Griffin's adventure");
					return -4;
				}else {
					currentRoom = area[currentRoom].move(in);
					display();
					return currentRoom;
				}
				
			}
			else {
				return area[currentRoom].move(in);
			}
		}
		return area[currentRoom].move(in);
		
	}
	@Override
	public int perct() {
		if(currentRoom >= area.length) {
			return 0;
		}
		else {
			return area[currentRoom].percentage;
		}
		
	}
	@Override
	public Enemy boss() {
		return boss;
	}
	@Override
	public ArrayList<Enemy> enemyList(){
		return enemies;
	}
	@Override
	public void bossDefeated() {
		bossDefeated = true;
	}

}
