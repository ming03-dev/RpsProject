package RpsProject;

import java.util.Scanner;

public class GameController {
	public static void main(String[] args) {

		UserInput userInput = new UserInput();
		Scanner sc = new Scanner(System.in);

		RecordRepository recordRepo = new RecordRepository();
		recordRepo.membersLoad();
		recordRepo.recordsLoad();

		MemberServiceImpl memberService = new MemberServiceImpl(recordRepo);
		RecordServiceImpl recordService = new RecordServiceImpl(recordRepo);

		GameServiceImp gameService = null; // 로그인 성공 후 생성
		User currentUser = null; // 로그인한 유저

		// 1. 시작 메뉴
		while (currentUser == null) {
			// 시작 메뉴 출력
			System.out.println("========================================\n");
			System.out.println("가위바위보 게임에 오신걸 환영합니다.\n");
			System.out.println("로그인(1)   회원가입(2)");
			System.out.println("========================================\n");

			userInput.isInputStartMenu();

			switch (userInput.input) {
			case 1: { // 로그인
				String email = userInput.inputLoginEmail();
				String password = userInput.inputLoginPassword();
				User loginUser = new User(email, password);

				boolean sucess = memberService.login(loginUser);
				if (sucess) {
					// Map 안 실제 유저 객체 가져와서 lastLogin 포함된 상태로 출력
					currentUser = memberService.getUser(email);

					// 로그인 성공 후에만 gameService 생성
					gameService = new GameServiceImp(recordService, currentUser, sc);

					// 환영 + 마지막 로그인 출력
					userInput.showWelcomeMessage(currentUser, memberService.getLastLoginBeforeUpdate());

				} else {
					System.out.println("가입한 유저가 아닙니다. 다시 시도해 주세요.\n");
				}
				break;
			}

			case 2: { // 회원가입
				String newEmail = userInput.inputEmail();
				String newPassword = userInput.inputPassword();
				User newUser = new User(newEmail, newPassword);

				boolean sucess = memberService.signUp(newUser);
				if (sucess) {
					System.out.println("회원가입이 완료되었습니다. 이제 로그인 해주세요.\n");
				}
				break;
			}
			default:
				System.out.println("메뉴 확인 후 다시 입력해 주세요.\n");
				break;
			}
		}

		// 2. 메인 메뉴
		while (true) {

			// 메인 메뉴(1~5)
			System.out.println("========================================\n");
			System.out.println("아래 메뉴 중 하나를 선택하세요.\n");
			System.out.println("로그아웃(1)   게임시작(2)   내 전적 보기(3)   전체 랭킹 보기(4)   비번 변경하기(5)\n");
			System.out.println("========================================");
			System.out.println();

			userInput.isInputMainMenu();

			switch (userInput.input2) {
			case 1: // 로그아웃 = 프로그램 종료
				memberService.logout();
				System.out.println("로그아웃 되었습니다.\n프로그램을 종료합니다.");
				return;

			case 2: // 게임 시작
				if (gameService == null) {
                    System.out.println("로그인 후 이용 가능합니다.\n");
                    break;
                }
                gameService.startGame();
                break;

			case 3:
				System.out.println("내 전적 보기");
				recordService.showMyRecord(currentUser);
				break;

			case 4:
				System.out.println("전체 랭킹 보기");
				recordService.showRanking(false); // 내림차순 보기
				break;

			case 5: // 비밀번호 변경
				memberService.changePassword(currentUser, userInput);
				break;

			default:
				System.out.println("메뉴 확인 후 다시 입력해 주세요.\n");
				break;
			}
		}
	}
}