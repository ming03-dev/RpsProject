package RpsProject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RecordRepository {
	Map<String, User> users = new HashMap<>(); // 회원 전체

	// members.txt 불러오기(members.txt : email, password)
	public void membersLoad() {
		try {
			FileReader member = new FileReader("C:\\GbbGame\\members.txt");
			BufferedReader br = new BufferedReader(member);

			String memberLine;
			while ((memberLine = br.readLine()) != null) {
				// 1. 쉼표 분리
				String[] memberParts = memberLine.split(",");

				String email = memberParts[0].trim();
				String password = memberParts[memberParts.length - 1].trim();

				// 2. User 객체 생성
				User user = new User(email, password); // 회원 한 명

				// 3. Map에 저장
				users.put(email, user);

			}
			br.close();
		} catch (FileNotFoundException e) {
			// 첫 실행: 파일 없으면 빈 파일 생성하고 끝내기
			try {
				FileWriter fw = new FileWriter("C:\\GbbGame\\members.txt");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// records.txt 불러오기(records.txt : email, win, lose, draw, lastLogin)
	public void recordsLoad() {
		try {
			FileReader record = new FileReader("C:\\GbbGame\\records.txt");
			BufferedReader br2 = new BufferedReader(record);

			String recordLine;

			while ((recordLine = br2.readLine()) != null) {
				// 1. 쉼표 분리
				String[] recordParts = recordLine.split(",");

				String email = recordParts[0];
				int win = Integer.parseInt(recordParts[1].trim());
				int lose = Integer.parseInt(recordParts[2].trim());
				int draw = Integer.parseInt(recordParts[3].trim());
				String lastLogin = recordParts[recordParts.length - 1].trim();

				// 2. membersLoad에서 만들어둔 User를 가져온다
				User user = users.get(email);

				// 3. 기존 User 객체에 전적/마지막 로그인 채운다
				user.setWin(win);
				user.setLost(lose);
				user.setDraw(draw);
				user.setLastLogin(lastLogin);
			}
		} catch (FileNotFoundException e) {
			// 첫 실행: records.txt가 없으면 빈 파일 생성하고 끝내기
			try {
				FileWriter fw = new FileWriter("C:\\GbbGame\\members.txt");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 회원 정보 중복 검사
	public boolean isDupl(User newUser) {
		String email = newUser.getEmail();
		
		// Map의 key(email) 기준으로 중복 검사
		return users.containsKey(email);
	}

	// 저장
	void save(User user) {
		// User 객체 생성 후 값 user.add(email, password)?
		System.out.println("저장하기");
	}

}
