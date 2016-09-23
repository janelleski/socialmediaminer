/** 
 * Copyright CodeJava.net To Present
 * All rights reserved. 
 */
package net.codejava.framework.action;

import java.util.List;

import net.codejava.framework.dao.PostDAO;
import net.codejava.framework.model.Post;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ListPostAction extends ActionSupport {
	private PostDAO postDAO;
	private List<Post> listPost;

	public void setPostDAO(PostDAO postDAO) {
		this.postDAO = postDAO;
	}

	public String execute() {
		listPost = postDAO.list();
		return SUCCESS;
	}

	public List<Post> getListPost() {
		return listPost;
	}
}
