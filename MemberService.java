package RpsProject;

public interface MemberService {
	
	boolean signUp(User newUser); // 회원가입
    boolean login(User loginUser); // 로그인
	void logout(); // 로그아웃
	void changePassword(User currentUser, UserInput userInput); // 비밀번호 변경
}
