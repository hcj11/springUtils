package com.example.oauth2.repository;

import com.example.oauth2.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hcj on 18-7-22
 */
public interface UserRepository extends JpaRepository<User,Long>{


  Optional<User> findByLogin(String name);

  Optional<User> findByAccessToken(String accessToken);

}
