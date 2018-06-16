import java.util.ArrayList;

public class Dragon extends Enemy {
	String name = "Dragon";
	int lvl = 1;
	int exp = 100;
	int str = 5, def = 7, mp=20, hp = 20, magDef = 3, mag = 7;
	int hpRemaining = hp;
	int mpRemaining = mp;
	
	
	public Dragon() {
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
		
		lvl = adjLvl+2;
		stats();
	}
	
	public void stats() {
		str = (3 * (this.lvl - 1))+5;
		def =( 4 * (this.lvl - 1))+7;
		mp = (4 * (this.lvl - 1))+20;
		hp = (7 * (this.lvl - 1))+20;
		mag = (4 *(lvl-1))+7;
		magDef = (2 *(lvl-1))+3;
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
		int atk = 0;
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
			atk = (int) (Math.random()*4);
			if(mpRemaining >= 15 && hpRemaining > hp/4 && atk > 1) {
				target.magicDamage(mag);
				System.out.println("Dragon used fire");
				mpRemaining -=15;
			}
			else if(hpRemaining < hp/4 && mpRemaining >= 20){
				target.magicDamage(mag*3);
				System.out.println("Dragon used flare");
				mpRemaining -= 20;
			}
			else {
				target.damage(str);
			}	
		}
		
		
	}

}
