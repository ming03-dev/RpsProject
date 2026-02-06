package RpsProject;

import java.util.Scanner;

class Rps {

	private int userVal;
	private int comVal;
	private int win;
	private int draw;
	private int lose;

	// 사용자 입력
	public boolean inputSomething(Scanner sc) {
		while (true) {
			System.out.print("가위(1), 바위(2), 보(3) 입력 (종료: 0): ");
			String input = sc.nextLine().trim();

			if (input.equals("0")) {
				return false; // 게임 종료
			}

			try {
				userVal = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				switch (input) {
				case "가위":
					userVal = 1;
					break;
				case "바위":
					userVal = 2;
					break;
				case "보":
					userVal = 3;
					break;
				default:
					System.out.println("잘못된 입력입니다. 다시 입력하세요.");
					continue;
				}
			}

			if (userVal < 1 || userVal > 3) {
				System.out.println("잘못된 입력입니다. 다시 입력하세요.");
				continue;
			}

			return true;
		}
	}

	// 승패 판단
	public void judge() {
		comVal = (int) (Math.random() * 3) + 1;

		if (comVal == userVal)
			draw++;
		else if ((userVal == 1 && comVal == 3) || (userVal == 2 && comVal == 1) || (userVal == 3 && comVal == 2))
			win++;
		else
			lose++;

	}

	private String getRoundResult() {
		if (userVal == comVal) {
			return "무승부입니다. 0ㅇ0";
		}

		if ((userVal == 1 && comVal == 3) || (userVal == 2 && comVal == 1) || (userVal == 3 && comVal == 2)) {
			return "당신이 이겼습니다.^3^";
		}

		return "컴퓨터가 이겼습니다.*^-^*";

	}

	// 출력용
	public void printResult() {
		System.out.println("사용자: " + convert(userVal));
		System.out.println("컴퓨터: " + convert(comVal));
		System.out.println();
		System.out.println("결과: " + getRoundResult());
		System.out.println("---------------------------------");
	}

	// 게임 계속할건지
	public boolean isReplay(Scanner sc) {
		while (true) {
			System.out.print("한 판 더 하시겠습니까? (Y/N): ");

			String input = sc.nextLine();

			// 1) 앞뒤 공백 제거
			input = input.trim();

			// 2) 전각 문자(ｙ／ｎ 등)를 반각(y/n)으로 정규화
			input = java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFKC);

			// 3) 소문자로 통일
			input = input.toLowerCase();

			if (input.equals("y"))
				return true;
			if (input.equals("n"))
				return false;

			System.out.println("Y 또는 N으로 입력하세요.");
		}
	}

	private String convert(int v) {
		return v == 1 ? "가위" : v == 2 ? "바위" : "보";
	}

	public int getWin() {
		return win;
	}

	public int getLose() {
		return lose;
	}

	public int getDraw() {
		return draw;
	}

}
