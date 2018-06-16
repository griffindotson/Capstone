import java.util.ArrayList;

public class Mountain extends Area{
	int currentRoom = 0;
	boolean bossDefeated = false;
	//Enemies in the area
	Dragon boss = new Dragon();
	Rat rat = new Rat();
	Rat rat2 = new Rat();
	Rat rat3 = new Rat();
	
	Wolf wolf = new Wolf();
	Wolf wolf2 = new Wolf();
	Wolf wolf3 = new Wolf();
	
	Goblin goblin = new Goblin();
	Goblin goblin2 = new Goblin();
	Goblin goblin3 = new Goblin();
	
	Thief thief = new Thief();
	Thief thief2 = new Thief();
	Thief thief3 = new Thief();
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	String[] options0 ={"Return to the town", "Look around", "Read the sign","Start up the mountain"};
	int[] numKey0 = {-2,-3,1,2};
	Room entrance = new Room(0, 10,"You have arrived at the base of the mountain.\n"
			+ "You see the trail head up the mountain marked by a sign", 
			options0, numKey0);
	
	String[] options1 ={"Turn back","Start up the mountain"};
	int[] numKey1 = {0,2};
	Room sign = new Room(1, 0,"Venture off the trail at your own risk", 
			options1, numKey1);
	
	String[] options2 ={"Turn back", "Look around", "Stay on the trail","Climb up"};
	int[] numKey2 = {0,-3,3,5};
	Room trail = new Room(2, 20,"You start up the mountain and see an area that looks climbable\n"
			+ "You think that climbing will save time but could be risky", 
			options2, numKey2);
	
	String[] options3 ={"Turn back", "Look around", "Keep moving"};
	int[] numKey3 = {1,-3,4};
	Room trail2 = new Room(3, 15,"You decided to stay on the trail as you continue up the mountain", 
			options3, numKey3);
	
	String[] options4 ={"Turn back", "Look around", "Keep moving","Climb up"};
	int[] numKey4 = {3,-3,6,7};
	Room trail3 = new Room(4, 35,"You keep moving but there is still a long way to go\n"
			+ "you can see other climbale area", 
			options4, numKey4);
	
	String[] options5 ={"Keep moving"};
	int[] numKey5 = {6};
	Room climb = new Room(5, 0,"You successfully climbed up the mountain", 
			options5, numKey5);
	
	String[] options6 ={"Turn back", "Look around", "Keep moving"};
	int[] numKey6 = {4,-3,8};
	Room trail4 = new Room(6, 60,"You proceed up the mountain", 
			options6, numKey6);
	
	String[] options7 ={"slide"};
	int[] numKey7 = {0};
	Room slide = new Room(7, 0,"You attempt to climb but rocks give way and you slide down the mountain", 
			options7, numKey7);
	
	String[] options8 ={"Turn back", "Look around", "Keep moving","Climb up"};
	int[] numKey8 = {6,-3,9,7};
	Room trail5 = new Room(8, 15,"You keep moving but there is still a long way to go\n"
			+ "you can see other climbale area", 
			options8, numKey8);
	
	String[] options9 ={"Turn back", "Look around", "Keep moving"};
	int[] numKey9 = {8,-3,10};
	Room trail6 = new Room(9, 70,"You keep moving and are half way up the mountain", 
			options9, numKey9);
	
	String[] options10 ={"Turn back", "Look around", "Keep moving","Climb up"};
	int[] numKey10 = {9,-3,12,11};
	Room trail7 = new Room(10, 30,"You keep moving and see other climbale area", 
			options10, numKey10);
	
	String[] options11 ={"Keep moving"};
	int[] numKey11 = {14};
	Room climb2 = new Room(11, 0,"You successfully climbed up the mountain", 
			options11, numKey11);
	
	String[] options12 ={"Turn back", "Look around", "Keep moving"};
	int[] numKey12 = {10,-3,13};
	Room trail8 = new Room(12, 25,"You begin to feel tired but can see the top of the mountain isn't far off", 
			options12, numKey12);
	
	String[] options13 ={"Turn back", "Look around", "Keep moving"};
	int[] numKey13 = {12,-3,14};
	Room trail9 = new Room(13, 45,"You are almost at the top of the mountain", 
			options13, numKey13);
	
	String[] options14 ={"Turn back", "Look for the path down"};
	int[] numKey14 = {13,15};
	Room top = new Room(14, 0,"You have reached the top of the mountain", 
			options14, numKey14);
	
	String[] options15 ={"Turn back", "Engauge"};
	int[] numKey15 = {14,16};
	Room fight = new Room(15, -1,"A dragon blocks your path and you will need to defeat it to proceed", 
			options15, numKey15);
	
	
	String[] options16 ={"Turn back", "descend"};
	int[] numKey16 = {14,17};
	Room descent = new Room(16, 15,"You find the path down the mountain", 
			options16, numKey16);
	
	String[] options17 ={"Turn back", "Look for a place to rest","Continue on your journey"};
	int[] numKey17 = {16,18,-1};
	Room village = new Room(17, 0,"You arrive at a small village", 
			options17, numKey17);
	
	String[] options18 ={"Leave"};
	int[] numKey18 = {17};
	Room inn = new Room(18, -2,"You find an inn and spend the night", 
			options18, numKey18);
	
	Room[] area = {entrance, sign,trail,trail2,trail3,climb,trail4,slide,trail5,
			trail6,trail7,climb2,trail8,trail9,top,fight,descent,village,inn};
	public Mountain() {
		enemies.add(wolf);
		enemies.add(wolf2);
		enemies.add(wolf3);
		
		enemies.add(thief);
		enemies.add(thief2);
		enemies.add(thief3);
		
		enemies.add(goblin);
		enemies.add(goblin2);
		enemies.add(goblin3);
		
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
				//Make sure the player dosen't re-fight the boss if they return to the area
				if(currentRoom ==15 && bossDefeated == true) {
					currentRoom += 1;
				}
				//Mark the boss as defeated when they get to the room after the fight
				if(currentRoom == 16) {
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
