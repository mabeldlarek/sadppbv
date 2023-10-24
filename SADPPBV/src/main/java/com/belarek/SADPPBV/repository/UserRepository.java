package com.belarek.SADPPBV.repository;

import com.belarek.SADPPBV.dto.UserDTO;
import com.belarek.SADPPBV.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<UserDTO> findByEmail(String email);
    Optional<UserDTO> findByRegistro(int register);
}
