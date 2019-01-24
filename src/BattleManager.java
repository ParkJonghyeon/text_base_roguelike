
public class BattleManager {
	private Player _player;
	private Enemy[] _enemy;
	private int _enemyNum;
	private int[] _actPriority;	//2 = 플레이어, 0, 1 = 몬스터
	
	public BattleManager(Player givenPlayer, int enemyNum, int floorNum){
		this._player = givenPlayer;
		this._enemyNum = enemyNum;
		this._enemy = new Enemy[enemyNum];
		
		for(int i = 0; i<this._enemyNum; i++)
			this._enemy[i]=new Enemy(floorNum, i);
	}
	
	private void prioritySet(int command){
		//agi 기반 우선순위 셋팅 배열에 우선 공격하는 객체를 앞에 넣음
		//플레이어 가드 스위치 켜졌을 때는 맨앞으로
		AGIList temp;
		AGIList[] AGI;
		
		switch(command){
		case 1 :
			this._actPriority = new int[this._enemyNum+1];
			AGI = new AGIList[this._enemyNum+1];
			for(int i = 0; i<this._enemyNum; i++)
				AGI[i] = new AGIList(this._enemy[i].getAgi(), this._enemy[i].getEnemyCode());
			AGI[this._enemyNum] = new AGIList(this._player.getAgi(), this._player.getPlayerCode());
			
			for(int i = this._enemyNum; i>0; i--){
				for(int j = 0; j < i; j++){
					if(AGI[j].getAgi() < AGI[j+1].getAgi()){
						temp = AGI[j];
						AGI[j] = AGI[j+1];
						AGI[j+1] = temp;
					}
				}
			}
			
			for(int i = 0; i<this._enemyNum+1; i++){
				this._actPriority[i] = AGI[i].getCode();
			}
			
			break;
		case 2 :
			this._actPriority = new int[this._enemyNum+1];
			AGI = new AGIList[this._enemyNum];
			for(int i = 0; i<this._enemyNum; i++)
				AGI[i] = new AGIList(this._enemy[i].getAgi(), this._enemy[i].getEnemyCode());
			
			for(int i = this._enemyNum-1; i>0; i--){
				for(int j = 0; j < i; j++){
					if(AGI[j].getAgi() < AGI[j+1].getAgi()){
						temp = AGI[j];
						AGI[j] = AGI[j+1];
						AGI[j+1] = temp;
					}
				}
			}
			
			this._actPriority[0] = this._player.getPlayerCode();
			
			for(int i = 1; i<this._enemyNum+1; i++){
				this._actPriority[i] = AGI[i-1].getCode();
			}
			break;
		case 3 :
			this._actPriority = new int[this._enemyNum];
			AGI = new AGIList[this._enemyNum];
			for(int i = 0; i<this._enemyNum; i++)
				AGI[i] = new AGIList(this._enemy[i].getAgi(), this._enemy[i].getEnemyCode());
			
			for(int i = this._enemyNum-1; i>0; i--){
				for(int j = 0; j < i; j++){
					if(AGI[j].getAgi() < AGI[j+1].getAgi()){
						temp = AGI[j];
						AGI[j] = AGI[j+1];
						AGI[j+1] = temp;
					}
				}
			}
			
			for(int i = 0; i<this._enemyNum; i++){
				this._actPriority[i] = AGI[i].getCode();
			}
			break;
		}
		
	}
	public void turnAct(int command, int target){
		this.prioritySet(command);
		switch(command){
		case 1 :
			if(0<target && target <= this._enemyNum)
				this.attack(target);
			else
				System.out.println("ERROR : 해당 몬스터가 존재하지 않습니다.");
			break;
		case 2 :
			this._player.switchDefence();
			this.attack(target);
			this._player.switchDefence();
			break;
		case 3 :
			if(this._player.useItem())
				this.attackUseItem();
			else
				System.out.println("ERROR : 소지한 아이템이 없습니다.");
			break;
		}
		
	}
	
	public void attack(int target){
		for(int i = 0; i<this._enemyNum+1; i++){
			if(this._actPriority[i] == this._player.getPlayerCode()){
				if(this._player.isDefence()){
					System.out.println();
					System.out.println("플레이어는 방어하고 있다.");
				} else {
					int pAttack = this._player.attackEnemy();
					int eDamage = this._enemy[target-1].damaged(pAttack);
					System.out.println();
					System.out.println("플레이어의 공격!");
					System.out.println("몬스터"+target+" 에게 "+eDamage+"의 데미지");
					this.aliveCheck();
				}
			} else {
				int eNum = this._actPriority[i];
				int eAttack = this._enemy[eNum].attackPlayer();
				int pDamage = this._player.defence(eAttack);
				System.out.println();
				System.out.println("몬스터"+(eNum+1)+" 의 공격!");
				System.out.println("플레이어에게 "+pDamage+"의 데미지");
			}
		}
	}
	
	public void attackUseItem(){
		for(int i = 0; i<this._enemyNum; i++){
				int eNum = this._actPriority[i];
				int eAttack = this._enemy[eNum].attackPlayer();
				int pDamage = this._player.defence(eAttack);
				System.out.println();
				System.out.println("몬스터"+(eNum+1)+" 의 공격!");
				System.out.println("플레이어에게 "+pDamage+"의 데미지");
		}
	}
	
	public void aliveCheck(){
		int dead = 0;
		int deleteEnemy = 9;
		for(int i = 0; i<this._enemyNum; i++){
			if(this._enemy[i].isDead()){
				dead++;
				deleteEnemy = i;
			}
		}
		if(this._enemyNum == 2 && deleteEnemy == 0){
			this._enemy[0] = this._enemy[1];
		}
		this._enemyNum -= dead;	
	}
	
	public boolean isBattleEnd(){
		return this._enemyNum == 0;
	}
	public Player getPlayer(){
		return this._player;
	}
	public void printEnemyStatus(){
		System.out.println();
		System.out.println("<<적 Status>>");
		for(int i=0; i<this._enemyNum; i++){
			if(this._enemy[i] != null)
			System.out.print((i+1)+". 몬스터"+(this._enemy[i].getEnemyCode()+1)+" (HP:"+this._enemy[i].getHp()+")\t");
		}
		System.out.println();
		System.out.println();
	}
	public void printPlayerStatus(){
		System.out.println("<<플레이어 Status>>");
		System.out.println("HP:"+this._player.getHp()+"/ 소지 아이템 수:"+this._player.getItem());
		System.out.println();
	}
	
}
