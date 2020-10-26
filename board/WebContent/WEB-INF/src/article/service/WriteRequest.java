package article.service;

import java.util.Map;

import article.model.Writer;

//게시글 쓰기에 필요한 데이터 제공
public class WriteRequest {

	private Writer writer;
	private String title;
	private String content;

	public WriteRequest(Writer writer, String title, String content) { //작성자, 제목, 내용
		this.writer = writer;
		this.title = title;
		this.content = content;
	}

	public Writer getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public void validate(Map<String, Boolean> errors) {
		if (title == null || title.trim().isEmpty()) {
			errors.put("title", Boolean.TRUE);
		}
	}
}
