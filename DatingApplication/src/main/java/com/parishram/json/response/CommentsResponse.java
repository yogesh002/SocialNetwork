package com.parishram.json.response;

import java.io.Serializable;

public class CommentsResponse implements Serializable {
	private static final long serialVersionUID = 2502870332699764406L;
	private String image;
	private String comment;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
