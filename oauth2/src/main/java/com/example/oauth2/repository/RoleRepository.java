package com.example.oauth2.repository;

import com.example.oauth2.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hcj on 18-7-22
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

}
