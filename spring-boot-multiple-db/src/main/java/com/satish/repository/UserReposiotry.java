package com.satish.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.satish.model.User;

@Repository
public interface UserReposiotry extends JpaRepository<User, Integer>{

}
