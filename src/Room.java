public class Room {
	private boolean _searchSpot, _downStair, _upStair;
	private boolean _movableN, _movableS, _movableW, _movableE;
	private int _encountPoint;
	private int _option;

	public Room(int givenOption) {
		this._option = givenOption;
		this._searchSpot = false;
		this._downStair = false;
		this._upStair = false;
		this._movableN = false;
		this._movableS = false;
		this._movableW = false;
		this._movableE = false;
		this.setRoom();
	}

	public void setRoom() {
		int searchRandom = (int) (Math.random()*10);
		switch (this._option) {
		case 0:
			//3할로 탐색 포인트 활성. 인카운트 포인트는 20포인트 100%로 방마다 2~5점 할당.
			if(searchRandom <= 3)
				this._searchSpot = true;
			this._encountPoint = (int) (Math.random()*3)+2;
			break;
		case 8:
			this._downStair = true;
			break;
		case 9:
			this._upStair = true;
			break;
		default:
			break;
		}
	}
	
	public void setSearchSpot(boolean TF){
		this._searchSpot = TF;
	}

	public void setMovableN(boolean TF){
		this._movableN = TF;
	}
	public void setMovableS(boolean TF){
		this._movableS = TF;
	}
	public void setMovableW(boolean TF){
		this._movableW = TF;
	}
	public void setMovableE(boolean TF){
		this._movableE = TF;
	}
	
	public boolean getMovableN(){
		return this._movableN;
	}
	public boolean getMovableS(){
		return this._movableS;
	}
	public boolean getMovableW(){
		return this._movableW;
	}
	public boolean getMovableE(){
		return this._movableE;
	}
	public boolean getSearchSpot(){
		return this._searchSpot;
	}
	public boolean getDownStair(){
		return this._downStair;
	}
	public boolean getUpStair(){
		return this._upStair;
	}
	
	public int getEncountPoint() {
		// 익스플로러 매니저에 인카운트 포인트 반환. 매니저는 그 값을 받아서 더함
		return this._encountPoint;
	}

	public int getOption() {
		return this._option;
	}
}
