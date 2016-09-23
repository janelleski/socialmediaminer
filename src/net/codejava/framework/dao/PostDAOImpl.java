/** 
 * Copyright CodeJava.net To Present
 * All rights reserved. 
 */
package net.codejava.framework.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

import net.codejava.framework.model.Post;

public class PostDAOImpl implements PostDAO {
	private SessionFactory sessionFactory;

	public PostDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public List<Post> list() {
		@SuppressWarnings("unchecked")
		List<Post> listPost = (List<Post>)
			sessionFactory.getCurrentSession().createCriteria(Post.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
		return listPost;
	}

}
