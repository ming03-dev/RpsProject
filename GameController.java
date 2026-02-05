package RpsProject;

public class GameController {
	public static void main(String[] args) {

		UserInput userInput = new UserInput();
		MemberServiceImpl memberService = new MemberServiceImpl();
		GameServiceImp gameService = new GameServiceImp();
		RecordServiceImpl recordService = new RecordServiceImpl();
		RecordRepository recordRepo = new RecordRepository();
		
		String email = null, password = null;
		User newUser = new User(email, password);
		
		// 게임 시작 후 불러옴.
		recordRepo.membersLoad(); // 회원 정보
		recordRepo.recordsLoad(); // 전적 기록

		while (true) {
			// 시작 메뉴 출력
			System.out.println("========================================\n");
			System.out.println("가위바위보 게임에 오신걸 환영합니다.\n");
			System.out.println("로그인(1)   회원가입(2)");
			System.out.println("========================================\n");

			userInput.isInputStartMenu();
			switch (userInput.input) {
			case 1: // 로그인
				if((memberService.login(newUser) == true)){
					break;
				} else continue; 

			case 2: // 회원가입
				while (true) {
					email = userInput.inputEmail();
					password = userInput.inputPassword();
					
					// MemberServiceImpl에 검증된 아이디, 비번 보내기
					if ((memberService.signUp(newUser) == true)) {
						System.out.println("회원가입이 완료되었습니다.");
						break;
					} else {
						continue;
					}
				}
				break;
			default:
				userInput.isInputStartMenu();
				break;
			}

			// 로그인 성공 시 lastLogin 출력
			System.out.println("========================================\n");
			System.out.println("님 환영합니다.");
			System.out.println("마지막으로 접속하신 시간은 " + " 입니다.\n");
			System.out.println("========================================");

			// 메인 메뉴(1~5)
//			System.out.println("========================================\n");
//			System.out.println("아래 메뉴 중 하나를 선택하세요.\n");
//			System.out.println("로그아웃(1)   게임시작(2)   내 전적 보기(3)   전체 랭킹 보기(4)   비번 변경하기(5)\n");
//			System.out.println("========================================");
//			System.out.print("당신의 선택은? ");
//		int input2 = sc.nextInt();

//		switch (input2) {
//		case 1:
//			memberService.logout();
//			break;
//		case 2:
//			gameService.startGame();
//			;
//			break;
//		case 3:
//			recordRepository.showMyRecord();
//			;
//			break;
//		case 4:
//			recordRepository.showRanking(true);
//			break; // 오름차순
//		case 5:
//			memberService.changePassword();
//			break;
//		}

		}

	}
}
