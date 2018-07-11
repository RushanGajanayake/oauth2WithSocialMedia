package com.oauth2sociallogin.demo.repository;

import com.oauth2sociallogin.demo.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rusha on 7/10/2018.
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("Select u From User u Where u.userId=?1")
    public User findUserByUserId(String userId);
}
