package com.projecthub.projecthub_api.repository;


import com.projecthub.projecthub_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository <User, UUID> {
    
}
