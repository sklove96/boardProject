package article.service;

import java.util.List;

import article.model.Article;

// 게시글 목록 제공
public class ArticlePage {

	private int total; //전체 게시글의 수
	private int currentPage; // 사용자가 요청한 페이지 번호
	private List<Article> content; //화면에 출력할 게시글 목록 보관
	private int totalPages; //전체 페이지 개수
	private int startPage;
	private int endPage; //맨 밑에 보여줄 이동 링크의 시작번호와 끝번호

	public ArticlePage(int total, int currentPage, int size, List<Article> content) {
		
		this.total = total;
		this.currentPage = currentPage;
		this.content = content;
		if (total == 0) {
			totalPages = 0;
			startPage = 0;
			endPage = 0;
		} else { //게시글 개수를 이용하여 전체 페이지 개수 구하기
			totalPages = total / size;
			if (total % size > 0) { 
				totalPages++;
			}
			//화면 하단에 보여줄 페이지 이동 링크의 시작 페이지 번호 구하기
			int modVal = currentPage % 5;
			startPage = currentPage / 5 * 5 + 1;
			if (modVal == 0) startPage -= 5;
			
			//끝 페이지 번호 구하기
			endPage = startPage + 4;
			if (endPage > totalPages) endPage = totalPages;
		}
	}

	public int getTotal() {
		return total;
	}

	public boolean hasNoArticles() {
		return total == 0;
	}

	public boolean hasArticles() {
		return total > 0;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public List<Article> getContent() {
		return content;
	}

	public int getStartPage() {
		return startPage;
	}
	
	public int getEndPage() {
		return endPage;
	}
}
