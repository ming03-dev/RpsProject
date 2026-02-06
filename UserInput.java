package RpsProject;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInput {
	Scanner sc = new Scanner(System.in);
	public int input, input2;

	// 시작화면 메뉴(1, 2) input 정수 받기
	public boolean isInputStartMenu() {
		while (true) {
			System.out.print("당신의 선택은? ");
			try {
				input = sc.nextInt();
				if (input < 1 || input > 5) {
					System.out.println("메뉴 확인 후 다시 입력해 주세요.");
					sc.nextLine();
					System.out.println();
				} else {
					System.out.println();
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("숫자로 다시 입력해 주세요.");
				sc.nextLine(); // 버퍼의 잘못된 입력값을 지우기 위해 필요하다
				System.out.println();
			}

		}
		return true;
	}

	// 로그인 - 이메일
	public String inputLoginEmail() {
		System.out.println("========================================\n");
		System.out.println("게임 이용을 위해 이메일을 입력해 주세요.");
		System.out.print("이메일: ");
		String email = sc.next();
		return email;
	}

	// 로그인 - 비밀번호
	public String inputLoginPassword() {
		System.out.println("========================================\n");
		System.out.println("게임 이용을 위해 비밀번호를 입력해 주세요.");
		System.out.print("비밀번호: ");
		String password = sc.next();
		return password;
	}

	// 회원가입 - 이메일
	public String inputEmail() {
		while (true) {
			System.out.println("========================================\n");
			System.out.println("회원가입을 위해 이메일을 입력해 주세요.");
			System.out.print("->");

			String email = sc.next();
			if (isValidEmail(email) != true) {
				System.out.println("이메일 형식에 맞춰 다시 입력해 주세요.");
				sc.nextLine();
				System.out.println();
			} else {

				System.out.println();
				return email;
			}
		}
	}

	// 이메일 유효성 검증
	private boolean isValidEmail(String email) {
		String idRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		return email.matches(idRegex);
	}

	// 회원가입 - 비밀번호
	public String inputPassword() {

		while (true) {
			System.out.println("========================================\n");
			System.out.println("회원가입을 위해 비밀번호를 입력해 주세요.");
			System.out.println("영어 소문자, 대문자, 특수문자를 포함하여 8~12자여야 합니다.");
			System.out.println("반드시 영어 대문자 1개 이상, 숫자 1개 이상, 특수문자 1개 이상이어야 합니다.");

			System.out.print("->");

			String password = sc.next();
			if (isValidPassword(password) != true) {
				System.out.println("조건에 맞춰 다시 입력해 주세요.");
				sc.nextLine();
				System.out.println();
			} else {
				System.out.println();
				return password;
			}

		}

	}

	// 비밀번호 변경 - 현재 비번 입력
	public String inputCurrentPassword() {
		System.out.println("========================================");
		System.out.println("현재 비밀번호를 입력해 주세요.");
		System.out.print("-> ");
		return sc.next();
	}

	// 비밀번호 변경 - 새 비번 입력
	public String inputNewPassword() {
		while (true) {
			System.out.println("========================================");
			System.out.println("새 비밀번호를 입력해 주세요.");
			System.out.println("대문자 1개 이상, 숫자 1개 이상, 특수문자 1개 이상 포함 (8~12자)");
			System.out.print("-> ");

			String password = sc.next();
			if (!isValidPassword(password)) {
				System.out.println("조건에 맞춰 다시 입력해 주세요.\n");
			} else {
				return password;
			}
		}
	}

	// 비번 유효성 검증
	private boolean isValidPassword(String password) {
		String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,12}$";
		return password.matches(passwordRegex);
	}

	// 환영인사 + 마지막 로그인 시간 출력
	public void showWelcomeMessage(User currentUser, String lastLogin) {
		String[] name = currentUser.getEmail().split("@");
		System.out.println("========================================\n");
		System.out.println(name[0] + "님 환영합니다.");
		System.out.println("마지막으로 접속하신 시간은 " + lastLogin + " 입니다.\n");
		System.out.println("========================================\n");

	}

	// 메인 메뉴(1, 2, 3, 4, 5) input 정수 받기
	public boolean isInputMainMenu() {
		while (true) {
			System.out.print("당신의 선택은? ");
			try {
				input2 = sc.nextInt();
				if (input2 < 1 || input2 > 5) {
					System.out.println("메뉴 확인 후 다시 입력해 주세요.");
					sc.nextLine();
					System.out.println();
				} else {
					System.out.println();
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("숫자로 다시 입력해 주세요.");
				sc.nextLine(); // 버퍼의 잘못된 입력값을 지우기 위해 필요하다
				System.out.println();
			}

		}
		return true;
	}

}