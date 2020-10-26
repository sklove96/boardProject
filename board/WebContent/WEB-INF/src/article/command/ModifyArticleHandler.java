package article.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticleData;
import article.service.ArticleNotFoundException;
import article.service.ModifyArticleService;
import article.service.ModifyRequest;
import article.service.PermissionDeniedException;
import article.service.ReadArticleService;
import auth.service.User;
import mvc.command.CommandHandler;

public class ModifyArticleHandler implements CommandHandler {
	private static final String FORM_VIEW = "/WEB-INF/view/modifyForm.jsp";

	private ReadArticleService readService = new ReadArticleService();
	private ModifyArticleService modifyService = new ModifyArticleService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) { //수정할 게시글 데이터 읽어와 폼으로 보여줌
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) { //게시글 수정함, equalsIgnoreCase: 대소문자 구별없이 비교
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	//폼 요청 처리(get)
	private String processForm(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		try {
			String noVal = req.getParameter("no");
			int no = Integer.parseInt(noVal);
			ArticleData articleData = readService.getArticle(no, false); //폼에 보여줄 게시글 구하기
			User authUser = (User) req.getSession().getAttribute("authUser"); //현재 로그인한 사용자 정보 구하기
			if (!canModify(authUser, articleData)) { //현재 로그인한 사용자와 게시글 작성자가 불일치하면
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
			//폼에 보여줄 데이터 객체 생성, req에 저장
			//ModifyRequest 객체로 변환해서 전달함 -> 폼 보여줄때와 폼 전송 후 다시 폼을 보여줘야할 때 동일한 JSP 코드 사용하기 위해!
			ModifyRequest modReq = new ModifyRequest(authUser.getId(), no,
					articleData.getArticle().getTitle(),
					articleData.getContent());

			req.setAttribute("modReq", modReq);
			return FORM_VIEW;
			
		} catch (ArticleNotFoundException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}

	private boolean canModify(User authUser, ArticleData articleData) {
		String writerId = articleData.getArticle().getWriter().getId();
		return authUser.getId().equals(writerId);
	}

	//폼 전송 처리(post)
	private String processSubmit(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		User authUser = (User) req.getSession().getAttribute("authUser"); //게시글 수정을 요청한 사용자 정보 구하기
		String noVal = req.getParameter("no");
		int no = Integer.parseInt(noVal);

		ModifyRequest modReq = new ModifyRequest(authUser.getId(), no,
				req.getParameter("title"),
				req.getParameter("content"));
		req.setAttribute("modReq", modReq); //modReq에 Modifyrequest 객체 저장

		Map<String, Boolean> errors = new HashMap<>(); //에러 정보 담을 맵 객체 생성
		req.setAttribute("errors", errors);
		modReq.validate(errors);
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		try { //게시글 수정 기능 실행
			modifyService.modify(modReq);
			return "/WEB-INF/view/modifySuccess.jsp";
		} catch (ArticleNotFoundException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} catch (PermissionDeniedException e) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
	}
}
