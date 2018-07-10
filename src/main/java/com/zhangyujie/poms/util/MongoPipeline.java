package com.zhangyujie.poms.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.zhangyujie.global.env.GlobalEnv;
import com.zhangyujie.poms.entity.Relation;
import com.zhangyujie.poms.entity.Reposter;
import org.springframework.stereotype.Component;

import com.zhangyujie.poms.dao.mongo.WeiboDao;
import com.zhangyujie.poms.entity.Comment;
import com.zhangyujie.poms.entity.Weibo;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class MongoPipeline implements Pipeline {

    @Resource
    private WeiboDao dao;

    private static int num = 1;

    private void saveSpeech(ResultItems resultItems) {

        List<String> weiboIds = resultItems.get("weiboId");
        List<String> userIds = resultItems.get("userId");
        List<String> userNames = resultItems.get("userName");
        List<String> userGenders = resultItems.get("userGender");
        List<String> createdAts = resultItems.get("createdAt");
        List<String> attitudesCounts = resultItems.get("attitudesCount");
        List<String> commentsCounts = resultItems.get("commentsCount");
        List<String> repostsCounts = resultItems.get("repostsCount");
//        System.out.println(weiboIds);
        boolean isOver = false;
        for (int i = 0; i < weiboIds.size(); i++) {
            Weibo weibo = new Weibo(weiboIds.get(i).trim(), userIds.get(i).trim(), userNames.get(i).trim(),
                    userGenders.get(i).trim(), null, DateFormat.format(createdAts.get(i).trim()),
                    Integer.parseInt(attitudesCounts.get(i).trim()),
                    Integer.parseInt(commentsCounts.get(i).trim()), null,
                    Integer.parseInt(repostsCounts.get(i).trim()), null);
            if (DateFormat.parse(GlobalEnv.DATE) > DateFormat.parse(weibo.getCreatedAt())) {
                isOver = true;
            }
            dao.saveWeibo(weibo);
        }
        if (isOver) System.exit(0);
    }

    private void saveContent(ResultItems resultItems) {
        dao.insertContent(resultItems.get("weiboId").toString(), resultItems.get("content").toString());
    }

    private void saveComment(ResultItems resultItems) {

        String weiboId = resultItems.get("weiboId");
        List<String> userIds = resultItems.get("userId");
        List<String> commentIds = resultItems.get("commentId");
        List<String> userNames = resultItems.get("userName");
        List<String> createdAts = resultItems.get("createdAt");
        List<String> texts = resultItems.get("text");

        for (int i = 0; i < commentIds.size(); i++) {
            dao.addComments(weiboId, new Comment(userIds.get(i).trim(), userNames.get(i).trim(), commentIds.get(i).trim(), texts.get(i).trim(), DateFormat.format(createdAts.get(i).trim())));
        }

    }

    private void saveRelation(ResultItems resultItems) {
        String userId = resultItems.get("userId");
        List<String> follows = resultItems.get("follow");
        dao.saveRelation(new Relation(userId, follows));
    }

    private void saveRepost(ResultItems resultItems) {
        List<Reposter> reposters = new ArrayList<>();
        String weiboId = resultItems.get("weiboId");
        List<String> repostIds = resultItems.get("repostIds");
        List<String> repostTexts = resultItems.get("repostTexts");
        List<String> createdAts = resultItems.get("createdAt");
        for (int i = 0; i < repostIds.size(); i++) {
            reposters.add(new Reposter(repostIds.get(i), repostTexts.get(i), DateFormat.format(createdAts.get(i))));
        }
        dao.addReposts(weiboId, reposters);
    }

    public synchronized void process(ResultItems resultItems, Task task) {

        String type = resultItems.get("type");
        if ("speech".equals(type)) {
            saveSpeech(resultItems);
            System.out.println("<Saved " + (num++) + " speech>");
        } else if ("content".equals(type)) {
            System.out.println("saveContent......");
            saveContent(resultItems);
        } else if ("relation".equals(type)) {
            System.out.println("saveRelations......");
            saveRelation(resultItems);
        } else if ("repost".equals(type)) {
            System.out.println("saveReposts......");
            saveRepost(resultItems);
        } else if ("comment".equals(type)) {
            System.out.println("saveComments......");
            saveComment(resultItems);
        }
    }
}
