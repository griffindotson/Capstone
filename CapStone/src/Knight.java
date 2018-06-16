import java.util.ArrayList;

public class Knight extends Enemy {
	String name = "Knight";
	int lvl = 1;
	int exp = 200;
	int str = 10, def = 7, mp=15, hp = 30, magDef = 5, mag = 5;
	int hpRemaining = hp;
	int mpRemaining = mp;
	
	
	public Knight() {
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
			if(adjLvl < party[i].getLvl()) {
				adjLvl = party[i].getLvl();
			}
		}
		
		lvl = adjLvl + 2;
		stats();
	}
	
	public void stats() {
		str = (7 * (this.lvl - 1))+10;
		def =( 7 * (this.lvl - 1))+7;
		mp = (6 * (this.lvl - 1))+15;
		hp = (10 * (this.lvl - 1))+30;
		mag = (4 *(lvl-1))+5;
		magDef = (3 *(lvl-1))+5;
		hpRemaining = hp;
		mpRemaining = mp;
		exp *= 2*lvl; 
	}
	@Override
	public void displayStats() {
		System.out.println(name+" lvl:"+ lvl+" hp:"+hpRemaining+"/"+hp+" mp:"+mpRemaining+"/"+mp+"  ");
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
		}
		else {
			if(target instanceof Defender || target instanceof Warrior && mpRemaining >= 15 && hpRemaining > hp/4) {
				target.magicDamage(mag*2);
				System.out.println("Knight used energy blade");
				mpRemaining -=15;
			}
			else if(hpRemaining < hp/4 && mpRemaining >= 20){
				target.damage(str*3);
				System.out.println("Knight used power attack");
				mpRemaining -= 20;
			}
			else {
				target.damage(str);
			}	
		}
		
		
	}

}
