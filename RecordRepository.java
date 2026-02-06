package RpsProject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class RecordRepository {
	Map<String, User> users = new HashMap<>(); // 회원 전체

	// members.txt 불러오기(members.txt : email, password)
	public void membersLoad() {
		ensureBaseDir();
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

	private void ensureBaseDir() {
		File dir = new File("C:\\GbbGame");
		if (!dir.exists())
			dir.mkdirs();
	}

	// records.txt 불러오기(records.txt : email, win, lose, draw, lastLogin)
	public void recordsLoad() {
		ensureBaseDir();

		try (BufferedReader br2 = new BufferedReader(new FileReader("C:\\GbbGame\\records.txt"))) {

			String recordLine;
			while ((recordLine = br2.readLine()) != null) {
				String[] recordParts = recordLine.split(",");

				// 형식이 다르면 스킵
				if (recordParts.length < 5)
					continue;

				String email = recordParts[0].trim();

				int win, lose, draw;
				try {
					win = Integer.parseInt(recordParts[1].trim());
					lose = Integer.parseInt(recordParts[2].trim());
					draw = Integer.parseInt(recordParts[3].trim());
				} catch (NumberFormatException e) {
					// 숫자 자리인데 비번 같은 게 들어간 경우 -> 스킵
					continue;
				}

				String lastLogin = recordParts[4].trim();

				User user = users.get(email);
				if (user == null)
					continue;

				user.setWin(win);
				user.setLose(lose);
				user.setDraw(draw);
				user.setLastLogin(lastLogin);
			}

		} catch (FileNotFoundException e) {
			try (FileWriter fw = new FileWriter("C:\\GbbGame\\records.txt")) {
				// 빈 파일 생성
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 회원 정보 중복 검사, 로그인 시 이메일 검사 가능
	public boolean isregisteredEmail(User newUser) {
		String email = newUser.getEmail();

		// Map의 key(email) 기준으로 중복 검사-> T면 파일에 저장
		return users.containsKey(email);
	}

	// 가입된 비번인지 확인
	public boolean isRightEmailPassword(User newUser) {
		//
		String email = newUser.getEmail();
		String password = newUser.getPassword();

		User u = users.get(email);
		// Map의 key(email) 기준으로 같은지 검사
		if (u == null)
			return false;
		String savedPw = u.getPassword();
		if (savedPw == null)
			return false;
		return savedPw.equals(password);
	}

	// 현재 시간 저장 (yyyy-MM-dd a KK:mm)2026-02-04 10:30으로 날짜 저장될수있게 하기
	public String currentTime() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 a hh시 mm분");

		String formatted = now.format(formatter);

		return formatted;
	}

	// 로그인 성공 시 lastLogin을 "현재시간"으로 갱신 (Map만 수정)
	public void currentTimeSave(User loginUser) {
		String email = loginUser.getEmail();
		User u = users.get(email);
		if (u == null)
			return; // 안전장치

		u.setLastLogin(currentTime());
	}

	// 저장된 유저 가져오기
	public User findByEmail(String email) {
		return users.get(email);
	}

	// 비번 변경
	public void updatePassword(String email, String newPassword) {
		User u = users.get(email);
		if (u == null)
			return;
		u.setPassword(newPassword);
	}

	// Map(users)의 전적/lastLogin을 records.txt로 "전체 다시 저장" (rewrite)
	public void saveAllRecords() {
		ensureBaseDir();

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\GbbGame\\records.txt"))) {
			for (Map.Entry<String, User> entry : users.entrySet()) {
				User u = entry.getValue();

				// records 데이터가 없으면(회원만 있고 전적없음) 스킵할지/초기화할지 선택
				// 여기서는 lastLogin null이면 스킵
				String last = (u.getLastLogin() == null || u.getLastLogin().isBlank()) ? "없음" : u.getLastLogin();
				String row = u.getEmail() + "," + u.getWin() + "," + u.getLose() + "," + u.getDraw() + "," + last;

				bw.write(row);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// members.txt 전체 다시 저장
	public void saveAllMembers() {
		ensureBaseDir();

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\GbbGame\\members.txt"))) {
			for (User u : users.values()) {
				// members.txt는 email,password만 저장
				if (u.getEmail() == null || u.getPassword() == null)
					continue;
				bw.write(u.getEmail() + "," + u.getPassword());
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 회원가입 시 records.txt에 초기 레코드 1줄 생성 (append)
	// email,0,0,0,없음
	public void recordSave(User newUser) {
		ensureBaseDir();

		String email = newUser.getEmail();

		// Map에 유저가 없으면 먼저 넣어둠 (membersSave 후 호출해도 안전)
		User u = users.get(email);
		if (u == null) {
			users.put(email, newUser);
			u = newUser;
		}

		// records가 이미 존재하는 유저면 중복 생성 방지
		if (u.getLastLogin() != null)
			return;

		u.setWin(0);
		u.setLose(0);
		u.setDraw(0);
		u.setLastLogin("없음");

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\GbbGame\\records.txt", true))) {
			bw.write(email + ",0,0,0," + u.getLastLogin());
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 회원 정보 저장
	public void memberSave(User newUser) {
		ensureBaseDir();

		String email = newUser.getEmail();
		String password = newUser.getPassword();

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\GbbGame\\members.txt", true))) { // true =
																											// append
			bw.write(email + "," + password);
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 메모리(Map)에도 즉시 반영 (다음 동작에 바로 쓰려고)
		users.put(email, newUser);
	}

	public User getUser(String email) {
		return users.get(email);
	}

	public Map<String, User> getUsers() {
		return users;
	}

}
