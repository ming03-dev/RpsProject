package RpsProject;

import java.util.Scanner;

public class GameServiceImp implements GameService {

	private final RecordService recordService;
	private final User currentUser;
	private final Scanner sc;

	 public GameServiceImp(RecordService recordService, User currentUser, Scanner sc) {
	        this.recordService = recordService;
	        this.currentUser = currentUser;
	        this.sc = sc;
	    }

	@Override
	public void startGame() {
		Rps game = new Rps();

		System.out.println("========================================\n");
		System.out.println("가위바위보 게임을 시작합니다.");
		System.out.println("========================================\n");

		while (true) {
			boolean ok = game.inputSomething(sc);
			if (!ok) {
				System.out.println("게임을 종료합니다.");
				break;
			}

			game.judge();
			game.printResult();

			// 한 판 끝나고 계속할지 물어봐서 false면 반복 종료
			boolean replay = game.isReplay(sc);

			if (!replay) {
                recordService.updateRecord(currentUser, game.getWin(), game.getLose(), game.getDraw());
				System.out.println("메인 메뉴로 돌아갑니다.");
				break;

			}
		}
	}
}
