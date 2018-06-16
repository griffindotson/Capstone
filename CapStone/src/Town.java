import java.util.ArrayList;

public class Town extends Area {
	int currentRoom = 0;
	Rat boss = new Rat();
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	String[] options1 ={"Move to battle tutorial"};
	int[] numKey = {1};
	Room tutorial = new Room(0, 0,"Welcome to Griffin's adventure.  \n"
			+ "Below is a list of possible movements, enter the number associate with the choice you wish to make"
			, options1, numKey);
	
	String[] options2 ={"Start battle"};
	int[] numKey2 = {2};
	Room battle = new Room(1, -1,"battles are turn based, on your turn select the party member you wish to act.\n"
			+ "Next select a move, then your enemies will take their turn", 
			options2, numKey2);
	
	String[] options3 ={"Go to the edge of town"};
	int[] numKey3 = {3};
	Room fight = new Room(2, 0,"You have completed the tutorial.  \nIt is time to begin your journey to prove yourself and become a knight\n"
			+ "Along your journey you will face many enemies but if you are looking for a fight, all you have to do is look around", 
			options3, numKey3);
	
	String[] options4 ={"Procceed into the forest"};
	int[] numKey4 = {-1};
	Room town = new Room(3, 0,"You stand at the begining of a trail into a vast forest", 
			options4, numKey4);
	
	Room[] area = {tutorial,battle,fight,town};
	
	public Town() {
		
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
