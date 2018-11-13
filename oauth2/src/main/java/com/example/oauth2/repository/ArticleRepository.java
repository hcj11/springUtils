package com.example.oauth2.repository;

import com.example.oauth2.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hcj on 18-7-26
 */
public interface ArticleRepository extends JpaRepository<Article,Long>{

}
