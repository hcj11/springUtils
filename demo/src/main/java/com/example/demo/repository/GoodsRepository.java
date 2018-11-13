package com.example.demo.repository;

import com.example.demo.domain.Goods;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by hcj on 18-7-22
 */
public interface GoodsRepository extends JpaRepository<Goods,Integer>{


}
