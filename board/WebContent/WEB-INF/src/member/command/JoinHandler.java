package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.service.DuplicateIdException;
import member.service.JoinRequest;
import member.service.JoinService;
import mvc.command.CommandHandler;


//Get 방식으로 요청이 오면 폼 보여주는 뷰인 joinForm.jsp 리턴
//Post 방식으로 요청이 오면 회원가입 처리하고 결과 보여주는 뷰 리턴
//=> 입력 잘못됐을 때: joinForm.jsp / 회원가입 성공: joinSuccess.jsp

public class JoinHandler implements CommandHandler { //CommandHandler 인터페이스 구현

	private static final String FORM_VIEW = "/WEB-INF/view/joinForm.jsp";
	private JoinService joinService = new JoinService();
	
	public String process(HttpServletRequest req, HttpServletResponse res) {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res); //get일 때
			
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res); //post일 때
			
		} else { //예외 처리
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	//폼 보여주는 뷰 리턴
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}

	//폼 전송 처리
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		//폼에 입력한 데이터 이용하여 JoinRequest 객체 생성
		JoinRequest joinReq = new JoinRequest();
		joinReq.setId(req.getParameter("id"));
		joinReq.setName(req.getParameter("name"));
		joinReq.setPassword(req.getParameter("password"));
		joinReq.setConfirmPassword(req.getParameter("confirmPassword"));
		
		//에러 정보 담을 객체 생성 -> errors에 저장
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors); //발생한 에러에 따라 알맞은 에러 메시지 보여주기 위해
	
		//JoinRequest의 값이 올바른지 검사 -> 올바르지 않을 경우 errors에 키 추가
		joinReq.validate(errors);
		
		//errors 맵 객체에 데이터 존재하면, 폼 뷰 경로 리턴.
		//에러 존재-> 위에서 키가 추가 되었다는 것을 의미!
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		try {
			joinService.join(joinReq); //join 메서드 실행하여 성공시 뷰 화면 리턴
			return "/WEB-INF/view/joinSuccess.jsp";
		} catch (DuplicateIdException e) { //동일 아이디로 회원가입하면
			errors.put("duplicateId", Boolean.TRUE); //errors에 duplicateId 키 추가하여 뷰 경로 리턴
			return FORM_VIEW;
		}
	}

}


