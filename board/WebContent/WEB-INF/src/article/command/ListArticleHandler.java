package article.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticlePage;
import article.service.ListArticleService;
import mvc.command.CommandHandler;

//게시글 목록 웹 요청 처리
public class ListArticleHandler implements CommandHandler {

	private ListArticleService listService = new ListArticleService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) 
			throws Exception {
		String pageNoVal = req.getParameter("pageNo"); //pageNo 파라미터 이용하여 읽어올 페이지 번호 구함
		int pageNo = 1;
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		ArticlePage articlePage = listService.getArticlePage(pageNo); //지정한 페이지 번호에 해당하는 게시글 데이터
		req.setAttribute("articlePage", articlePage); //jsp에서 사용가능하도록
		return "/WEB-INF/view/listArticle.jsp";
	}

}
