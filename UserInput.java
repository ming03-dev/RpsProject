package RpsProject;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInput {
	Scanner sc = new Scanner(System.in);
	public int input;

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

	// 회원가입 - ID
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
	
	// 비번 유효성 검증
	private boolean isValidPassword(String password) {
		String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,12}$";
		return password.matches(passwordRegex);
	}
	
	// 메인 메뉴(1, 2, 3, 4, 5) input 정수 받기
		public boolean isInputMainMenu() {
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
	
}