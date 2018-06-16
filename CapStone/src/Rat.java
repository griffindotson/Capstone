import java.util.ArrayList;

public class Rat extends Enemy {
	String name = "Rat";
	int lvl = 1;
	int exp = 20;
	int str = 2, def = 1, mp=10, hp = 10, magDef = 3, mag = 1;
	int hpRemaining = hp;
	int mpRemaining = mp;
	
	public Rat() {
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
		str = (2 * (this.lvl - 1))+2;
		def =( 2 * (this.lvl - 1))+1;
		mp = (2 * (this.lvl - 1))+10;
		hp = (3 * (this.lvl - 1))+10;
		mag = (2 *(lvl-1))+3;
		magDef = (3 *(lvl-1))+1;
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
			defender.damage(str);
		}else {
			target.damage(str);
		}
		
	}

}
