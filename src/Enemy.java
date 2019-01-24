
public class Enemy {
	private int _hp;
	private int _str, _vit, _agi, _luk;
	private int _enemyCode;
	
	public Enemy(int floorNum, int code){
		this._hp = 30+floorNum*10+(int)(Math.random()*9);
		this._str = 2+floorNum;
		this._vit = 1+floorNum;
		this._agi = (int) (Math.random()*5);
		this._luk = 1;
		this._enemyCode = code;
	}
	
	public boolean isDead(){
		if(this._hp <= 0)
			return true;
		return false;
	}
	public int attackPlayer(){
		int eAttack = (int) (Math.random()*3)+_str*2;
		int critical = (int) (Math.random()*_luk)*2;
		if(critical%10 < _luk)
			eAttack += eAttack/2;
		return eAttack;
	}
	public int damaged(int getDamage){
		getDamage = getDamage-(int)(Math.random()*_vit/2);
		if(getDamage < 0)
			getDamage = 0;
		this._hp -= getDamage;
		return getDamage;
	}
	public int getHp(){
		return this._hp;
	}
	public int getAgi(){
		return this._agi;
	}
	public int getEnemyCode(){
		return this._enemyCode;
	}
}
