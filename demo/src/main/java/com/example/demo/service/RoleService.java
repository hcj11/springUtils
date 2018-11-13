package com.example.demo.service;

import com.example.demo.domain.Role;
import com.example.demo.repository.RoleRepository;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hcj on 18-7-22
 */
@Service
public class RoleService implements Serializable{
  @Autowired
  private RoleRepository roleRepository;

  @Transactional(readOnly = true)
  public Role findOne(Long id){
   return  roleRepository.findById(id).orElse(null);
  }
}
