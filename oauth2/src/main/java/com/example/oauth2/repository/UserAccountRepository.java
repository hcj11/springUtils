package com.example.oauth2.repository;

import com.example.oauth2.Resource.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hcj on 18-7-29
 */
public interface UserAccountRepository extends JpaRepository<UserAccount,Long>{

}
