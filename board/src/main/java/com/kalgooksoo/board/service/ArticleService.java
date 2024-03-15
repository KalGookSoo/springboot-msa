package com.kalgooksoo.board.service;

import com.kalgooksoo.board.command.CreateArticleCommand;
import com.kalgooksoo.board.command.DislikesCommand;
import com.kalgooksoo.board.command.LikesCommand;
import com.kalgooksoo.board.command.UpdateArticleCommand;
import com.kalgooksoo.board.domain.Article;

import java.util.List;

public interface ArticleService {

    Article create(CreateArticleCommand command);

    List<Article> findAllByCategoryId(String categoryId);

    /**
     * 게시글 상세 조회
     * 게시글을 조회하였을 경우 조회수를 1 증가시킨다.
     * 게시글을 조회한 사람을 영속 데이터에 저장한다.
     * 게시글을 이미 조회한 사람이 다시 조회했을 경우 조회수를 증가시키지 않는다.
     *
     * @param id 식별자
     * @return 게시글
     */
    Article findById(String id);

    Article update(String id, UpdateArticleCommand command);

    void delete(String id);

    /**
     * 게시글의 좋아요 수를 1 증가 또는 감소시킨다.
     * 게시글에 좋아요를 누른 사람을 영속 데이터에 저장한다.
     * 게시글에 이미 좋아요를 누른 사람이 다시 좋아요를 눌렀을 경우 좋아요 수를 1 감소시킨다.
     *
     * @param id 식별자
     */
    void likes(String id, LikesCommand command);

    /**
     * 게시글의 싫어요 수를 1 증가 또는 감소시킨다.
     * 게시글에 싫어요를 누른 사람을 영속 데이터에 저장한다.
     * 게시글에 이미 싫어요를 누른 사람이 다시 싫어요를 눌렀을 경우 싫어요 수를 1 감소시킨다.
     *
     * @param id 식별자
     */
    void dislikes(String id, DislikesCommand command);

}
