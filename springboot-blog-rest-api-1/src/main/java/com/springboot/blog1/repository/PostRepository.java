package com.springboot.blog1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.blog1.entity.Post;


/*if repository interface extending jpaRepository then it will be consider 
 * "@repository" 
 * automatically so if we not mention then also it will work beacuase that annotation present in jparepository
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

}
