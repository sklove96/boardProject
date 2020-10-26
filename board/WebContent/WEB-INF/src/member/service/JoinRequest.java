package member.service;

import java.util.Map;

//JoinService가 회원가입을 구현할 때 필요한 요청 데이터 담는 클래스
//회원가입에 필요한 데이터 담아 JoinService에 객체 전달
public class JoinRequest {

	private String id;
	private String name;
	private String password;
	private String confirmPassword;

	//각 필드 get/set
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public boolean isPasswordEqualToConfirm() { //필드값 일치여부
		return password != null && password.equals(confirmPassword);
	}

	//각 필드의 데이터 유효한지 검사
	//errors 맵 객체는 에러 정보 담기 위해 사용
	public void validate(Map<String, Boolean> errors) { //validate: 값이 올바른지 검색하는 메서드
		checkEmpty(errors, id, "id");
		checkEmpty(errors, name, "name");
		checkEmpty(errors, password, "password");
		checkEmpty(errors, confirmPassword, "confirmPassword");
		if (!errors.containsKey("confirmPassword")) {
			if (!isPasswordEqualToConfirm()) {
				errors.put("notMatch", Boolean.TRUE);
			}
		}
	}
	
	//value가 없는 경우 errors맵 객체의 fieldName 키에 true값 추가
	private void checkEmpty(Map<String, Boolean> errors, 
			String value, String fieldName) {
		if (value == null || value.isEmpty())
			errors.put(fieldName, Boolean.TRUE);
	}
}
