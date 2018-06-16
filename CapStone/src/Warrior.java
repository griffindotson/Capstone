import java.util.ArrayList;
import java.util.Scanner;

public class Warrior extends Hero {
	String name = "warrior";
	int lvl = 1;
	int exp = 0;
	int expNextLvl = 100;
	int expGain;
	
	int str = 5, def = 3, mag=3, magDef=3,mp = 10, hp = 25;
	int hpRemaining = hp;
	int mpRemaining = mp;
	ArrayList<String> abilities = new ArrayList<String>();
	
	public Warrior() {
		addAbility();
		stats();
	}
	
	public void addExp(int expEarned) {
		boolean lvlUp = false;
		//Level up character
		exp += expEarned;
		while(exp >= expNextLvl) {
			exp -= expNextLvl;
			lvl += 1;		
			expNextLvl *=2;
			lvlUp = true;
		}	
		if(lvlUp) {
			stats();
			addAbility();
			System.out.println(name+" is now lvl "+lvl);
		}
	}
	
	public void stats() {
		//It done this way in case I want to changes the level
		//before the game starts
		//Improve stats as character levels up
		str = (3 *(lvl-1))+5;
		def = (3 *(lvl-1))+3;
		mag = (2 *(lvl-1))+3;
		magDef = (3 *(lvl-1))+3;
		mp = (2 *(lvl-1))+10;
		hp = (4 *(lvl-1))+25;
		hpRemaining = hp;
		mpRemaining = mp;
	}
	
	//Methods for each ability
	public void attack(Enemy enemy) {
		expGain = enemy.damage(str);
		if(expGain > 0) {
			addExp(expGain);
		}
	}
	public void energyBlade(Enemy enemy) {
		expGain = enemy.magicDamage(mag);
		if(expGain > 0) {
			addExp(expGain);
		}
	}
	public void powerAttack(Enemy enemy) {
		expGain = enemy.damage(str*3);
		if(expGain > 0) {
			addExp(expGain);
		}
	}
	
	@Override
	public boolean activateAbility(int select, ArrayList<Enemy> enemies, Hero[] party) {
		int in = 0;
		Enemy target = null;
		Scanner scan = new Scanner(System.in);
		String input;
		boolean bool = true;
		boolean atk = true;
		
		while(bool) {
			System.out.println("Select a Target");
			System.out.println("-1 Go back");
			//Take user input to select character will act
			System.out.print("> ");
			input = scan.nextLine();
			System.out.println();
			
			try {
				in = Integer.parseInt(input);
			}
			catch (NumberFormatException e){
				System.out.println("Not a valid move");
			}
			if(in >= 0 && in<enemies.size()) {
				target = enemies.get(in);
				bool = false;
			}
			else if(in == -1) {
				System.out.println("Pick an action");
				return false;
			}
			else {
				System.out.println("Target not found");
			}
		}
		
		
		if(select == 0) {
			attack(target);
			return true;
		}
		else if(select == 1) {
			if(mpRemaining >= 5) {
				//Energy Blade
				mpRemaining -= 5;
				energyBlade(target);
				return true;
			}
			else {
				System.out.println("Not enough mp for this attack");
				return false;
			}
			
		}
		else if(select == 2) {
			if(mpRemaining >= 10) {
				//Power attack
				mpRemaining -= 10;
				powerAttack(target);
				return true;
			}
			else {
				System.out.println("Not enough mp for this attack");
				return false;
			}
					
		}
		scan.close();
		return false;
	}
	
	
	@Override
	public int abilityCount() {
		return abilities.size();
	}
	public void addAbility() {
		//Adds abilities to character as they level up
		if(lvl >= 1 && !abilities.contains("Attack")) {
			abilities.add("Attack");
		}
		if(lvl >=  5 && !abilities.contains("Energy blade")) {
			abilities.add("Energy blade");
		}
		if(lvl >=  15 && !abilities.contains("Power attack")) {
			abilities.add("Power attack");
		}
	}
	public void displayAbilities() {
		//Show the current abilities of the character
		for(int i = 0;i<abilities.size();i++) {
			System.out.println(i+" "+abilities.get(i));
		}
	}
	
	@Override
	public void displayStats() {
		
		System.out.print(name+" lvl:"+ lvl+" hp:"+hpRemaining+"/"+hp+" mp:"+mpRemaining+"/"+mp+"  ");
	}
	
	@Override
	public void name() {
		System.out.println(name+":");
	}
	
	@Override
	public boolean alive() {
		//Check to see if the character has hp to battle
		if(hpRemaining != 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//Methods to take damage
	@Override
	public void damage(int hit) {
		//Takes damage when attacked
		//Damage based on "physical" stats
		int damage = hit - (def/2);
		if(damage <= 0) {
			damage = 1;
		}
		
		System.out.println(name+" took "+damage+" points of damage");
		hpRemaining -= damage;
		//Make sure health doesn't fall below zero
		if(hpRemaining < 0) {
			hpRemaining = 0;
		}
		//Display message when defeated
		if(hpRemaining == 0) {
			System.out.println(name+" has fallen");
		}
	}
	
	@Override
	public void magicDamage(int hit) {
		//Takes damage when attacked
		//Damage based on "magic" stats
		int damage = hit - (magDef/2);
		if(damage <= 0) {
			damage = 1;
		}
		System.out.println(name+" took "+damage+" points of damage");
		hpRemaining -= damage;
		//Make sure health doesn't fall below zero
		if(hpRemaining < 0) {
			hpRemaining = 0;
		}
		//Display message when defeated
		if(hpRemaining == 0) {
			System.out.println(name+" has fallen");
		}
	}
	
	@Override
	public void cure(int heal) {
		hpRemaining += heal;
		if(hpRemaining > hp) {
			hpRemaining = hp;
		}
	}
	
	@Override
	public void rez() {
		if(hpRemaining == 0) {
			hpRemaining = hp/2;
		}
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public int getLvl() {
		return lvl;
	}
	@Override
	public void rest() {
		hpRemaining = hp;
		mpRemaining = mp;
	}

}
