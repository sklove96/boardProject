package auth.service;

//아이디와 암호가 일치하면 로그인 한 사용자 정보 담은 User객체 리턴->잘못된 경우 LoginFailException 발생
public class User {

	private String id;
	private String name;

	public User(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
