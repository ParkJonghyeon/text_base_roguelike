
public class Player {
	private int _hp, _maxHp;
	private int _str, _vit, _agi, _luk;
	private int _possItem;
	private int _playerCode;
	private boolean _guardSwitch;
	
	public Player(){
		this._hp = 100;
		this._maxHp = 100;
		this._str = 10;
		this._vit = 10;
		this._agi = 5;
		this._luk = 5;
		this._possItem = 0;
		this._guardSwitch = false;
		this._playerCode = 1001;
	}
	
	public boolean isDead(){
		//HP <= 0이면 true
		if(this._hp <= 0)
			return true;
		return false;
	}
	public void findItem(){
		this._possItem++;
	}
	public boolean useItem(){
		//possItem--. 7할로 회복 3할로 데미지
		if(this._possItem > 0)
			this._possItem--;
		else
			return false;
		
		int rd = (int) (Math.random()*9)+1;
		int rdHp = (int) (Math.random()*20)+5;
		System.out.println();
		
		switch(rd){
		case 2:
		case 5:
		case 8:
			System.out.println("상한 약 : 데미지를 입었습니다. HP - "+rdHp);
			System.out.println();
			recoveryOrDamaged(-rdHp);
			break;
		default : 
			System.out.println("회복 약 : 체력을 회복. HP + "+rdHp);
			System.out.println();
			recoveryOrDamaged(rdHp);
			break;
		}		
		return true;
	}
	public int attackEnemy(){
		//str 수치 기반하여 수식 처리 후 데미지 리턴
		int pAttack = (int) (Math.random()*3)+_str;
		int critical = (int) (Math.random()*_luk)*2;
		if(critical%10 < _luk)
			pAttack += pAttack/2;
		return pAttack;
	}
	public void switchDefence(){
		//디펜스 스위치 트루로
		if(this._guardSwitch == false)
			this._guardSwitch = true;
		else
			this._guardSwitch = false;
	}
	public boolean isDefence(){
		return this._guardSwitch;
	}
	public int defence(int getDamage){
		//getDamage로 받아온 값을 vit 수치 기반하여 수식 처리 후 hp에서 -
		//디펜스 스위치 켜져있으면 추가 처리
		if(this._guardSwitch == true)
			getDamage -= _vit;
		getDamage = getDamage-(int)(Math.random()*_vit/2);
		if(getDamage < 0){
			getDamage = 0;
		}
		this.recoveryOrDamaged(-getDamage);
		return getDamage;
	}
	public int getAgi(){
		return this._agi;
	}
	public void recoveryOrDamaged(int getHp){
		//getHp 수치만큼 hp 회복 또는 감소
		this._hp += getHp;
		if(this._hp > this._maxHp)
			this._hp = this._maxHp;
	}	
	public int getHp(){
		return this._hp;
	}
	public int getItem(){
		return this._possItem;
	}
	public int getPlayerCode(){
		return this._playerCode;
	}
}
