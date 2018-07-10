package com.zhangyujie.poms.dao.mongo;

import javax.annotation.Resource;

import com.zhangyujie.poms.entity.Relation;
import com.zhangyujie.poms.entity.Reposter;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.zhangyujie.poms.entity.Comment;
import com.zhangyujie.poms.entity.Weibo;

import java.util.List;

@Repository("weiboDao")
public class WeiboDaoImpl implements WeiboDao {

    @Resource(name = "mongoTemplate")
    private MongoOperations mo;

    @Override
    public String saveRelation(Relation relation) {
        mo.save(relation);
        return relation.getUserId();
    }

    @Override
    public String saveWeibo(Weibo weibo) {
        String weiboId = weibo.getWeiboId();
        Weibo w = mo.findOne(Query.query(Criteria.where("weiboId").is(weiboId)),Weibo.class);
        if (w == null) {
            mo.save(weibo);
        }
//        System.out.println(weibo);
        return weibo.getWeiboId();
    }

    @Override
    public Weibo findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String insertContent(String weiboId, String content) {
        mo.updateFirst(Query.query(Criteria.where("weiboId").is(weiboId)), Update.update("content", content), Weibo.class);
        return weiboId;
    }

    @Override
    public String addComments(String weiboId, Comment comment) {
        mo.updateFirst(Query.query(Criteria.where("weiboId").is(weiboId)), new Update().addToSet("comments", comment), Weibo.class);
        return weiboId;
    }

    @Override
    public String addReposts(String weiboId, List<Reposter> reposters) {
        reposters.forEach(reposter -> mo.updateFirst(Query.query(Criteria.where("weiboId").is(weiboId)), new Update().addToSet("reposters", reposter), Weibo.class));
        return weiboId;
    }

}
