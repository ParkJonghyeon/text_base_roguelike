public class ExploreManager {
	private Maze _maze;
	private Room _room;
	private Player _player;
	private boolean _isGameEnd;
	private int _encountPercentage; // 20포인트 만점. 20포인트 이상이면 배틀로 넘어감

	public ExploreManager(int floorCount, boolean forward) {
		this._maze = new Maze(floorCount, forward);
		this._room = this._maze.getRoom();
		this.roomRefresh(this._room);
		this._isGameEnd = false;
		this._encountPercentage = 0;
	}

	public void nextFloor(int nextFloor, boolean forward) {
		if (nextFloor < 5){
			this._maze = new Maze(nextFloor, forward);
			this._room = this._maze.getRoom();
		}
		else {
			this._isGameEnd = true;
		}
		this.resetEncount();
	}

	public void setPlayer(Player aPlayer) {
		this._player = aPlayer;
	}
	
	public Player getPlayer(){
		return this._player;
	}

	public void resetEncount(){
		this._encountPercentage = 0;
	}
	
	public int getEncount() {
		return this._encountPercentage;
	}

	public boolean move(int direction) {
		boolean TF;
		// direction 따라 해당하는 방향의 _maze의 go 실행
		switch (direction) {
		case 1:
			TF = this._maze.goNorth();
			break;
		case 2:
			TF = this._maze.goSouth();
			break;
		case 3:
			TF = this._maze.goWest();
			break;
		case 4:
			TF = this._maze.goEast();
			break;
		default:
			TF = false;
			break;
		}
		if(!this._room.equals(this._maze.getRoom())){
			this._room = this._maze.getRoom();
			this.roomRefresh(this._room);
		}
		return TF;
	}

	public void searchThisRoom() {
		if (this._room.getSearchSpot()) {
			int rd = (int) (Math.random()*9)+1;
			int rdHp = (int) (Math.random()*20)+5;
			this._room.setSearchSpot(false);
			System.out.println();
			System.out.println("현재 방을 조사합니다.");
			
			switch(rd){
			case 1:
			case 3:
			case 5:
				System.out.println("함정에 걸렸습니다. HP - "+rdHp);
				System.out.println();
				this._player.recoveryOrDamaged(-rdHp);
				break;
			case 2:
			case 8:
				System.out.println("회복 포인트를 발견. HP + "+rdHp);
				System.out.println();
				this._player.recoveryOrDamaged(rdHp);
				break;
			default:
				System.out.println("아이템을 발견 했습니다.");
				System.out.println();
				this._player.findItem();
				break;
			}
		} else {
			System.out.println("이 방에는 더 이상 조사할 것이 없다.");
			System.out.println();
		}
		// 3할로 트랩. 5할로 아이템. 2할로 회복 포인트
	}

	public void roomRefresh(Room thisRoom) {
		// 다음 방 이동 후 상태 갱신. 인카운트율 등등
		this._encountPercentage += this._room.getEncountPoint();
	}

	public boolean isFloorEnd() {
		return this._isGameEnd;
	}

	public void printCommand() {
		boolean mN, mS, mW, mE, search, up, down;
		mN = this._room.getMovableN();
		mS = this._room.getMovableS();
		mW = this._room.getMovableW();
		mE = this._room.getMovableE();
		search = this._room.getSearchSpot();
		up = this._room.getUpStair();
		down = this._room.getDownStair();

		System.out.println();
		System.out.println("<<플레이어 Status>>");
		System.out.println("HP:"+this._player.getHp()+"/ 소지 아이템 수:"+this._player.getItem());
		System.out.println("인카운트 " + this._encountPercentage
				+ " point / 현재 좌표는 (" + this._maze.getXY() + ") 입니다.");

		System.out.println();
		
		System.out.println("<<커맨드 선택>>");
		if (mN == true)
			System.out.print("1-북쪽으로 이동\t");
		if (mS == true)
			System.out.print("2-남쪽으로 이동\t");
		if (mW == true)
			System.out.print("3-서쪽으로 이동\t");
		if (mE == true)
			System.out.print("4-동쪽으로 이동");

		System.out.println();

		if (search == true)
			System.out.print("5-현재 방을 조사한다\t");
		if (this._player.getItem() > 0)
			System.out.print("6-아이템을 사용한다\t");
		if (up == true && this._maze.getFloorNum() != 1)
			System.out.print("8-이전 층으로 돌아간다\t");
		if (down == true && this._maze.getFloorNum() != 4)
			System.out.print("9-다음 층으로 내려간다");
		if (down == true && this._maze.getFloorNum() == 4)
			System.out.print("9-미궁 탈출");

		System.out.println();
		
	}
}
