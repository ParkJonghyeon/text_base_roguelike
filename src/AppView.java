import java.util.Scanner;


public class AppView {
	private Scanner _scanner;
	
	public String MSG_StartProgram = "<<프로그램을 시작합니다>>\n";
	public String MSG_EndProgram = "<<프로그램을 종료합니다>>\n";
	public String MSG_InputCommand = "무엇을 하시겠습니까? : ";
	public String MSG_BattleStart = "--------------------------\n\t몬스터의 습격!\n--------------------------\n";
	public String MSG_SelectTarget = "대상을 선택하세요 : ";
	public String MSG_BattleEnd = "--------------------------\n\t전투 종료!\n--------------------------\n";
	public String MSG_GameOver = "플레이어의 HP가 0이 되었습니다. Game Over\n";
	public String MSG_GameClear = "마지막 층에 도달했습니다! Game Clear\n";
	public String MSG_MovableError = "ERROR : 해당 방향으로는 진행 할 수 없습니다.\n";
	public String MSG_ItemError = "ERROR : 소지한 아이템이 없습니다.\n";
	public String MSG_CommandError = "ERROR : 올바른 커맨드를 입력해주세요.\n";
	
	public AppView(){
		this._scanner = new Scanner(System.in);
	}
	
	public void outputMsg(String aString){
		System.out.print(aString);
	}
	
	public String inputString(){
		return this._scanner.nextLine();
	}
	
	public int inputInt(){
		return Integer.parseInt(this.inputString());
	}
	
	public void printBattleCommand(){
		System.out.println("<<커맨드 선택>>");
		System.out.println("1-공격\t2-방어\t3-아이템 사용");
	}
	
	public void lineClear(){
		for(int i = 0; i<10; i++){
			System.out.println();
		}
	}
}
