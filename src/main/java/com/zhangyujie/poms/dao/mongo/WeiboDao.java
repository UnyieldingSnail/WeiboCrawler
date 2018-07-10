package com.zhangyujie.poms.dao.mongo;

import com.zhangyujie.poms.entity.Comment;
import com.zhangyujie.poms.entity.Relation;
import com.zhangyujie.poms.entity.Reposter;
import com.zhangyujie.poms.entity.Weibo;

import java.util.List;

public interface WeiboDao {
	public String saveRelation(Relation relation);
	public String saveWeibo(Weibo weibo);
	public Weibo findAll();
	public String insertContent(String weiboId, String content);
	public String addComments(String weiboId, Comment comment);

    public String addReposts(String weiboId, List<Reposter> reposters);
}
