package com.hslametshop.restapi.model.repos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hslametshop.restapi.model.entities.User;

public interface UserRepo extends JpaRepository<User, UUID> {
    
}
