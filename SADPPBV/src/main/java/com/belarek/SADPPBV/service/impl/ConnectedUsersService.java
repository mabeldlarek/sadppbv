package com.belarek.SADPPBV.service.impl;


import com.belarek.SADPPBV.entity.AuthToken;
import com.belarek.SADPPBV.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConnectedUsersService {

    @Autowired
    private TokenRepository authTokenRepository;

    private final Map<Long, String> connectedUsers = new ConcurrentHashMap<>();

    public void addUser(Long userId, String username) {
        connectedUsers.put(userId, username);
    }

    public void removeUser(Long userId) {
        connectedUsers.remove(userId);
    }

    public Map<Long, String> getConnectedUsers() {
        return new HashMap<>(connectedUsers);
    }

    public List<AuthToken> getValidTokens() {
        return authTokenRepository.findAllByIsValidoTrue();
    }

}