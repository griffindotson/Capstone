import java.util.ArrayList;
import java.util.Scanner;

public class World {
	int currentArea = 0; 
	//Create all the areas of the world
	Town town = new Town();
	Forest forest = new Forest();
	Cave cave = new Cave();
	Mountain mountain = new Mountain();
	Castle castle = new Castle();
	Warrior warrior = new Warrior();
	Healer healer = new Healer();
	Mage mage = new Mage();
	Defender defender = new Defender();
	
	Scanner scan = new Scanner(System.in);
	String input;
	
	Hero[] party = {warrior,healer,mage,defender};
	
	//Enemy[] enemies = {enemy};
	//the array of areas creates the world of the game
	Area[] world = {town,forest,cave,mountain,castle};
	
	public World() {
		
	}
	
	public void game() {
		//Display the first room of the game
		world[currentArea].display();
		journey();
	}
	public void battle(Hero[] party, ArrayList<Enemy> enemies) {
		boolean fight = true;
		//Bool determines what team's turn it is
		boolean yourTurn = true;
		boolean actionChar = true;
		boolean actionMove = true;
		Hero defender = null;
		boolean successfulAtk;
		int atkingEnemy = 0;
		int character = 0;
		
		int in;
		
		
		for(int i = 0;i<party.length;i++) {
			if(party[i] instanceof Defender) {
				defender = party[i];
				break;
			}
		}
		
		while(fight) {
			//Show the user the current stats of the party
			for(int i = 0; i<party.length;i++) {
				if(i == 2) {
					System.out.println();
				}
				System.out.print(i+" ");
				party[i].displayStats();
			}
			System.out.println("\n");
			//Show the user the current stats of the enemies
			for(int j = 0; j<enemies.size();j++) {
				if(j == 2) {
					System.out.println();
				}
				System.out.print(j+" ");
				enemies.get(j).displayStats();
			}
			
			
			while(yourTurn) {
				while(actionChar) {
					in = -1;
					//Take user input to select character will act
					System.out.println();
					System.out.println("Pick character to act");
					System.out.print("> ");
					input = scan.nextLine();
					System.out.println();
					
					try {
						in = Integer.parseInt(input);
					}
					catch (NumberFormatException e){
						System.out.println("Not a valid move");
					}
					
					if(in < party.length && in>=0) {
						//Check to make sure the character hp isn't 0
						if(party[in].alive()) {
							party[in].name();
							character = in;
							System.out.println("-1 back to characters");
							party[in].displayAbilities();
							actionChar = false;
						}
						else {
							System.out.println("Character is to weak to fight");
						}
					}
					else {
						System.out.println("Pick character 0-3");
					}
				}
				
				//Select a move for the character to take
				while(actionMove) {
					in = -1;
					//Take user input to select how character will act
					System.out.print("> ");
					input = scan.nextLine();
					System.out.println();
					
					try {
						in = Integer.parseInt(input);
					}
					catch (NumberFormatException e){
						System.out.println("Not a valid move");
					}
					if(in == -1) {
						System.out.println("Pick a character");
						actionMove = false;
					}
					else if(in >= 0 && in< party[character].abilityCount()) {
						//Activate attack and select target
						successfulAtk = party[character].activateAbility(in, enemies,party);
						if(successfulAtk == true) {
							actionMove = false;
							//Change to signal it is now enemies turn
							yourTurn = false;
						}
						else {
							System.out.println("Pick an action");
						}
					}
					else {
						System.out.println("This character does not know how to react to this command");
					}	
				}
				enemyDown(enemies);
				actionChar = true;
				actionMove = true;	
			}
			//Check if any enemies are left in the fight
			if(enemies.isEmpty()) {
				fight = false;
				System.out.println("Fight won");
				
				if(defender != null) {
					//End the defenders guard when match ends
					defender.endGuard();
				}
			}
			else {
				//Enemy takes their turn
				System.out.println();
				if(enemies.size() == 1) {
					atkingEnemy = 0;
				}
				if(!enemies.isEmpty()) {
					enemies.get(atkingEnemy).fight(party);
					//Change to signal it is the players turn
					atkingEnemy += 1;
					if(atkingEnemy >= enemies.size()) {
						atkingEnemy = 0;
					}
				}
				if(partyAlive(party)) {
					yourTurn = true;
				}
				else {
					fight = false;
					System.out.println("Your party has fallen");
				}
				
			}
			System.out.println();
		}
	}
	
	public boolean partyAlive(Hero[] party) {
		//Check if the party is still alive
		boolean bool = false;
		for(int i =0;i<party.length;i++) {
			if(party[i].alive()) {
				bool = true;
				break;
			}
		}
		return bool;
	}
	
	public void enemyDown(ArrayList<Enemy> enemies) {
		//Removes the enemy from the battle if they have no Hp
		for(int i = 0; i<enemies.size();i++) {
			if(enemies.get(i).getHp() == 0) {
				enemies.remove(i);
			}
		}
	}
	
	public void randomEncounter(int percentage, ArrayList<Enemy> enemies, Enemy boss) {
		ArrayList<Enemy> encounter = new ArrayList<Enemy>();
		if(percentage == -1) {
			//Initiate boss fight
			boss.determineLvl(party);
			encounter.add(boss);
			battle(party, encounter);
			world[currentArea].bossDefeated();
			encounter.clear();
		}
		else if(percentage == -2) {
			//Player stayed at inn, heal party
			for(int i = 0; i<party.length;i++) {
				party[i].rest();
			}
		}
		else {
			//Determine if there is a random encounter
			if((int)(Math.random()*101) <= percentage){
				//Battle
				encounter = enemyParty(enemies);
				if(!encounter.isEmpty()) {
					for(int j = 0; j<encounter.size();j++) {
						encounter.get(j).determineLvl(party);
					}
					battle(party, encounter);
				}
				encounter.clear();
			}
		}
	}
	
	public ArrayList<Enemy> enemyParty(ArrayList<Enemy> enemies){
		//Build the enemy party the player will fight
		ArrayList<Enemy> enemyParty = new ArrayList<Enemy>();
		ArrayList<Integer> used = new ArrayList<Integer>();
		boolean bool = true;
		int rand = 0;
		//Random decides how many enemies from 1-4
		int enemyNum = ((int) (Math.random()*4))+1;
		
		if(enemies.size()<4||enemies.isEmpty()) {
			enemyParty = null;
		}
		else {
			//Build the party at random
			for(int i = 0; i<enemyNum;i++) {
				bool = true;
				while(bool) {
					//Make sure an enemy isn't added more than once
					rand = (int) (Math.random()*enemies.size());
					if(!used.contains(rand)) {
						used.add(rand);
						enemyParty.add(enemies.get(rand));
						bool = false;
					}
				}	
			}
		}	
		return enemyParty;
	}
	
	public void journey() {
		//The method is what allows for playing the game
		int event;
		int percentage = 0;
		boolean gameOn = true;
		ArrayList<Enemy> enemies = world[currentArea].enemyList();
		Enemy boss = world[currentArea].boss();
		
		while(gameOn) {
			//Take user input to determine action
			System.out.print("> ");
			String input2 = scan.nextLine();
			System.out.println();
			
			if(percentage != 0) {
				randomEncounter(percentage,enemies,boss);
			}
			
			
			event = world[currentArea].move(input2);
			percentage = world[currentArea].perct();
			
			
			//If the player choose an action that is not traversing the current area
			//a negative number is returned and this interprets these movements
			if(event == -1) {
				currentArea += 1; 
				world[currentArea].display();
				boss = world[currentArea].boss();
				enemies = world[currentArea].enemyList();
			}
			else if(event == -2) {
				currentArea -= 1;
				world[currentArea].display();
				boss = world[currentArea].boss();
				enemies = world[currentArea].enemyList();
			}
			else if(event == -3) {
				System.out.println("\nfight!");
				battle(party,enemies);
				world[currentArea].display();
			}
			else if(event == -4) {
				gameOn = false;
			}
		}
		scan.close();
	}


}
