package com.hackernews.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="comments")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String comment;
	
	@ManyToOne
	@JoinColumn(name = "author")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "content")
	private Content content;
	
	@ManyToOne
    private Comment parentComment;
    
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> childComments;

	public Comment(int id, String comment, User user, Content content, Comment parentComment,
			List<Comment> childComments) {
		super();
		this.id = id;
		this.comment = comment;
		this.user = user;
		this.content = content;
		this.parentComment = parentComment;
		this.childComments = childComments;
	}

	public Comment() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public Comment getParentComment() {
		return parentComment;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}

	public List<Comment> getChildComments() {
		return childComments;
	}

	public void setChildComments(List<Comment> childComments) {
		this.childComments = childComments;
	}
    
    
	
	
	

	
	

}
