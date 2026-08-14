package com.projecthub.projecthub_api.User.repository;


import com.projecthub.projecthub_api.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository <User, UUID> {

}
