public class Maze {
	private MazeList _mazeList; // 던전 셋팅 상황 저장 된 리스트
	private int[][] _mazeSetting; // 던전 셋팅 상황
	private Room[][] _maze; // 던전
	private Room _room; // 현재 위치한 방
	private int _floorNum; // 층 넘버
	private int _X, _Y; // 현재 위치 좌표
	private boolean forward; // 밑층에서 역방향으로 올라온 경우는 prev가 false

	public Maze(int givenFloorNum, boolean givenForward) {
		this._mazeList = new MazeList();
		this._floorNum = givenFloorNum;
		this._room = null;
		this.forward = givenForward;
		if (this.createMaze(_floorNum)) {
			this.settingRoom();
			this.movableSetting();
		}
		this.forward = true; // 역방향으로 올라왔을 경우 스타트 지점만 잡아주고 다시 초기화
	}

	public boolean createMaze(int givenFloorNum) {
		// 받아온 변수값 따라서 1에서 4층 던전 cre
		this._mazeSetting = this._mazeList.maze(_floorNum);
		this._maze = new Room[this._mazeSetting.length][this._mazeSetting.length];
		if (this._mazeSetting == null)
			return false;
		return true;
	}

	private void settingRoom() {
		for (int i = 0; i < _mazeSetting.length; i++) {
			for (int j = 0; j < _mazeSetting[i].length; j++) {
				this._maze[i][j] = new Room(this._mazeSetting[i][j]);
				if (forward == true && this._mazeSetting[i][j] == 9) {
					this._room = this._maze[i][j]; // 셋팅 중 스타트 지점(9)을 찾으면 현재
													// room을 설정
					this._X = i;
					this._Y = j;
				} else if (forward != true && this._mazeSetting[i][j] == 8) {
					this._room = this._maze[i][j]; // 셋팅 중 도착 지점(8)을 찾으면 현재
													// room을 설정
					this._X = i;
					this._Y = j;
				}
			}
		}
	}

	private void movableSetting() {
		for (int i = 0; i < _maze.length; i++) {
			for (int j = 0; j < _maze[i].length; j++) {
				if (this._maze[i][j].getOption() != 1) {
					if (i - 1 >= 0) {
						if (this._maze[i - 1][j].getOption() != 1)
							this._maze[i][j].setMovableN(true);
					}
					// north 이동 가능 체크 setMovableN
					if (i + 1 < 10) {
						if (this._maze[i + 1][j].getOption() != 1)
							this._maze[i][j].setMovableS(true);
					}
					// south 체크 setMovableS
					if (j - 1 >= 0) {
						if (this._maze[i][j - 1].getOption() != 1)
							this._maze[i][j].setMovableW(true);
					}
					// west 체크 setMovableW
					if (j + 1 < 10) {
						if (this._maze[i][j + 1].getOption() != 1)
							this._maze[i][j].setMovableE(true);
					}
					// east 체크 setMovableE
				}
			}
		}
	}

	public boolean goNorth() {
		if (this._maze[_X][_Y].getMovableN()) {
			this._X -= 1; // 다음 이동 좌표
			this._room = this._maze[this._X][this._Y]; // 다음 방
		} else
			return false;
		return true;
	}

	public boolean goSouth() {
		if (this._maze[_X][_Y].getMovableS()) {
			this._X += 1; // 다음 이동 좌표
			this._room = this._maze[this._X][this._Y]; // 다음 방
		} else
			return false;
		return true;
	}

	public boolean goWest() {
		if (this._maze[_X][_Y].getMovableW()) {
			this._Y -= 1; // 다음 이동 좌표
			this._room = this._maze[this._X][this._Y]; // 다음 방
		} else
			return false;
		return true;
	}

	public boolean goEast() {
		if (this._maze[_X][_Y].getMovableE()) {
			this._Y += 1; // 다음 이동 좌표
			this._room = this._maze[this._X][this._Y]; // 다음 방
		} else
			return false;
		return true;
	}

	public int getFloorNum() {
		return this._floorNum;
	}

	public String getXY() {
		return this._X + ", " + this._Y;
	}

	public Room getRoom() {
		return this._room;
	}
}
