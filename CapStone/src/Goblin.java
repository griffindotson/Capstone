import java.util.ArrayList;

public class Goblin extends Enemy{
	String name = "Goblin";
	int lvl = 1;
	int exp = 50;
	int str = 3, def = 3, mp=25, hp = 22, magDef = 5, mag = 5;
	int hpRemaining = hp;
	int mpRemaining = mp;
	
	public Goblin() {
		stats();
	}
	
	@Override
	public int getExp() {
		return exp;
	}
	@Override
	public void determineLvl(Hero[] party) {
		int adjLvl = 0;
		for(int i = 0;i<party.length;i++) {
			adjLvl += party[i].getLvl();
		}
		
		lvl = adjLvl/party.length;
		stats();
	}
	
	public void stats() {
		str = (2 * (this.lvl - 1))+3;
		def =( 2 * (this.lvl - 1))+3;
		mp = (4 * (this.lvl - 1))+25;
		hp = (2 * (this.lvl - 1))+22;
		mag = (4 *(lvl-1))+5;
		magDef = (5 *(lvl-1))+5;
		hpRemaining = hp;
		mpRemaining = mp;
		exp *= 2*lvl;
	}
	@Override
	public void displayStats() {
		System.out.print(name+" lvl:"+ lvl+" hp:"+hpRemaining+"/"+hp+" mp:"+mpRemaining+"/"+mp+"  ");
	}
	@Override
	public int damage(int hit) {
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
			return exp;
		}
		else {
			return 0;
		}
	}
	@Override
	public int magicDamage(int hit) {
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
			return exp;
		}
		else {
			return 0;
		}
	}
	@Override
	public int getHp() {
		// TODO Auto-generated method stub
		return hpRemaining;
	}
	
	@Override
	public void fight(Hero[] party) {
		//Describes how this enemy acts in battle
		boolean bool = true;
		Hero target = null;
		Hero defender = null;
		ArrayList<Hero> guarded = null;
		
		for(int i = 0;i<party.length;i++) {
			if(party[i] instanceof Defender) {
				defender = party[i];
				break;
			}
		}
		//Get the list of people guarded by the defender
		if(defender != null) {
			guarded = defender.isGuarding();
		}
		
		while(bool) {
			target = party[(int)( Math.random()*party.length)];
			if(target.alive()) {
				bool = false;
			}
		}
		
		if(guarded.contains(target)) {
			//If target is guarded by defender
			//Pass damage to defender
			System.out.println("Defender protected "+target.getName());
			if(mpRemaining >= 5) {
				mpRemaining -=5;
				defender.magicDamage(mag);
			}
			else {
				defender.damage(str);
			}
			
		}
		else {
			if(mpRemaining >= 5) {
				mpRemaining -=5;
				target.magicDamage(mag);
			}
			else {
				target.damage(str);
			}
		}
		
	}

}
