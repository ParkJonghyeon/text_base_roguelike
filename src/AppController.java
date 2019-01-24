public class AppController {
	private ExploreManager _exploreM;
	private BattleManager _battleM;
	private Player _player;
	private AppView _appView;
	private int managerSign; // 0이면 exploreM, 1이면 battleM
	private int floorCount;

	public AppController() {
		this.floorCount = 1;
		this._exploreM = new ExploreManager(floorCount, true); // 처음 생성해서 프로그램
																// 종료까지 계속 사용
		this._battleM = null; // 배틀 이벤트마다 새로 생성해서 사용
		this._player = new Player();
		this._appView = new AppView();
		this.managerSign = 0;
	}

	public void run() {
		this._appView.outputMsg(this._appView.MSG_StartProgram);
		while (!this._exploreM.isFloorEnd() && !this._player.isDead()) {
			if (managerSign == 0)
				this.explore();
			else
				this.battle();
		}
		if(this._player.isDead())
			this._appView.outputMsg(this._appView.MSG_GameOver);
		if(this._exploreM.isFloorEnd())
			this._appView.outputMsg(this._appView.MSG_GameClear);
		this._appView.outputMsg(this._appView.MSG_EndProgram);
	}

	public void explore() {
		// battle로 들어갈 때 managerSign을 1로 셋팅하고 반복문 탈출하여 run으로 돌아감
		this._exploreM.setPlayer(this._player);

		while (this._exploreM.getEncount() < 20 && !this._exploreM.isFloorEnd()) {
			this._exploreM.printCommand();
			this._appView.outputMsg(this._appView.MSG_InputCommand);
			int command = this._appView.inputInt();

			switch (command) {
			case 1:
			case 2:
			case 3:
			case 4:
				if (!this._exploreM.move(command))
					this._appView.outputMsg(this._appView.MSG_MovableError);
				break;
			case 5:
				this._exploreM.searchThisRoom();
				break;
			case 6:
				if (!this._player.useItem())
					this._appView.outputMsg(this._appView.MSG_ItemError);
				break;
			case 8:
				this.floorCount--;
				this._exploreM.nextFloor(floorCount, false);
				break;
			case 9:
				this.floorCount++;
				this._exploreM.nextFloor(floorCount, true);
				break;
			default : 
				this._appView.outputMsg(this._appView.MSG_CommandError);
				break;
			}
			// this._appView.lineClear();
		}
		this._player = this._exploreM.getPlayer();
		this.managerSign = 1;
	}

	public void battle() {
		// explore로 돌아갈 때 managerSign을 0으로 셋팅하고 반복문 탈출하여 run으로 돌아감
		// 종료 직전에 exM의 resetEncount로 인카운트 0로 초기화 후 종료
		this._appView.lineClear();
		this._appView.outputMsg(this._appView.MSG_BattleStart);
		int enemyNum = (int) (Math.random() * 1)+1; // 1~2 사이 값. 최대 2마리까지
		this._battleM = new BattleManager(this._player, enemyNum, this.floorCount);

		while (!this._player.isDead() && !this._battleM.isBattleEnd()) {
			this._battleM.printEnemyStatus();
			this._battleM.printPlayerStatus();
			this._appView.printBattleCommand();
			this._appView.outputMsg(this._appView.MSG_InputCommand);
			int command = this._appView.inputInt();
			
			if(0 < command && command < 4){
				if(command == 1){
					this._appView.outputMsg(this._appView.MSG_SelectTarget);
					int target = this._appView.inputInt();
					this._battleM.turnAct(command, target);
				} else {
					this._battleM.turnAct(command, 9);
				}
			}
			else
				this._appView.outputMsg(this._appView.MSG_CommandError);
			
		}
		this._player = this._battleM.getPlayer();
		this.managerSign = 0;
		this._exploreM.resetEncount();
		this._appView.outputMsg(this._appView.MSG_BattleEnd);
		

	}
}
