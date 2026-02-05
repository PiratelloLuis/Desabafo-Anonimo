package com.desabafo.desabafoatonimo.repository;

import com.desabafo.desabafoatonimo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByIpHash(String ipHash);
    
    boolean existsByIpHash(String ipHash);
}
