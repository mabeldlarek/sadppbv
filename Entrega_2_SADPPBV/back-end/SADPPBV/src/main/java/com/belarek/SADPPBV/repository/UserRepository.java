package com.belarek.SADPPBV.repository;

import com.belarek.SADPPBV.dto.UserDTO;
import com.belarek.SADPPBV.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);

    User findByRegistro(int registro);
    void deleteByRegistro(int registro);

}
