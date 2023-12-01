package com.example.security1.repository;

import com.example.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//CRUD 함수를 JpaRepository가 들고 있음
//@repository라는 어노테이션이 없어도 Ioc되요. 이유는 JpaRespository를 상속했기 때문에
//@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // findBy규착 -> Username 문법
    // select * from user where username= 1?
   User findByUsername(String username);//JPA name 함수

}
