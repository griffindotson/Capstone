import java.util.ArrayList;

public class Cave extends Area {
	int currentRoom = 0;
	boolean bossDefeated = false;
	
	//Enemies in the area
	Bandit boss = new Bandit();
	Rat rat = new Rat();
	Rat rat2 = new Rat();
	Rat rat3 = new Rat();
	
	Goblin goblin = new Goblin();
	Goblin goblin2 = new Goblin();
	
	Wolf wolf = new Wolf();
	Wolf wolf2 = new Wolf();
	Wolf wolf3 = new Wolf();
	
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	String[] options1 ={"Return to the forest", "Look around", "Move further into the cave"};
	int[] numKey = {-2,-3,1};
	Room entrance = new Room(0, 50,"You are at the enterance of the cave.", 
			options1, numKey);
	
	String[] options2 ={"Return to the entrance of the cave", "Look around", "Take the left path","Take the right path"};
	int[] numKey2 = {0,-3,2,3};
	Room cave = new Room(1, 20,"You walk further into the cave and it is getting harder to see as you move further from the light.\n"
			+ "The path now splits into two tunnels", 
			options2, numKey2);
	
	String[] options3 ={"Turn back", "Keep moving forward"};
	int[] numKey3 = {1,4};
	Room leftPath = new Room(2, 30,"You take the left path and continue forward.\n"
			+ "There is no longer any light and you cannot see", 
			options3, numKey3);
	
	String[] options4 ={"Turn back", "Keep moving forward"};
	int[] numKey4 = {1,5};
	Room rightPath = new Room(3, 20,"You take the right path and continue forward.\n"
			+ "There is no longer any light and you cannot see", 
			options4, numKey4);
	
	String[] options5 ={"Turn back", "Keep moving forward"};
	int[] numKey5 = {2,6};
	Room leftPathCont = new Room(4, 25,"You keep moving forward in the complete darkness", 
			options5, numKey5);
	
	String[] options6 ={"Turn back", "Keep moving forward"};
	int[] numKey6 = {3,7};
	Room rightPathCont = new Room(5, 10,"You keep moving forward in the complete darkness", 
			options6, numKey6);
	
	String[] options7 ={"Turn back", "Search for another path"};
	int[] numKey7 = {4,8};
	Room openArea = new Room(6, 15,"The tunnel has opened up but you still can't see", 
			options7, numKey7);
	
	String[] options8 ={"Turn back", "Move towards the light"};
	int[] numKey8 = {5,9};
	Room firstLight = new Room(7, 10,"As you continue further in the cave you begin to see light ahead", 
			options8, numKey8);
	
	String[] options9 ={"Return to the entrance of the cave"};
	int[] numKey9 = {0};
	Room deadEnd = new Room(8, 20,"As you walk around you begin to feel like you made the wrong choice", 
			options9, numKey9);
	
	String[] options10 ={"Go back into the cave", "Look around","Go to the town"};
	int[] numKey10 = {7,-3,10};
	Room exit = new Room(9, 5,"You come to the exit of the cave and can see a town nearby", 
			options10, numKey10);
	
	String[] options11 ={"Go back to the cave", "Take a rest at the inn","Leave the town"};
	int[] numKey11 = {9,11,12};
	Room town = new Room(10, 0,"You are standing in the middle of the town\n"
			+ "You can see an inn and the path out of town", 
			options11, numKey11);

	String[] options12 ={"Leave the inn"};
	int[] numKey12 = {10};
	Room inn = new Room(11, -2,"You spend the night at the inn to recover from the journey", 
			options12, numKey12);
	
	String[] options13 ={"Turn back","Engauge"};
	int[] numKey13 = {10,13};
	Room outskirt = new Room(12, -1,"As you attempt to leave the town a bandit blocks your path\n"
			+ "Do you fight or run away", 
			options13, numKey13);
	
	String[] options14 ={"Return to town","Go to the mountain"};
	int[] numKey14 = {10,-1};
	Room nextArea = new Room(13, 0,"You are now just outside of town and can see a mountain in the distance", 
			options14, numKey14);
	
	
	//The array of rooms creates the entire area of the world 
	Room[] area = {entrance,cave,leftPath,rightPath,leftPathCont,rightPathCont,
			openArea,firstLight,deadEnd,exit,town,inn,outskirt,nextArea};
	
	public Cave() {
		enemies.add(rat);
		enemies.add(rat2);
		enemies.add(rat3);
		
		enemies.add(goblin);
		enemies.add(goblin2);
		
		enemies.add(wolf);
		enemies.add(wolf2);
		enemies.add(wolf3);
		
		
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
				//Make sure the player dosen't re-fight the boss if they return to the area
				if(currentRoom ==12 && bossDefeated == true) {
					currentRoom += 1;
				}
				//Mark the boss as defeated when they get to the room after the fight
				if(currentRoom == 13) {
					bossDefeated = true;
				}
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
	@Override
	public void bossDefeated() {
		bossDefeated = true;
	}

}
