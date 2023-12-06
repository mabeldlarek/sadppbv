package com.belarek.SADPPBV.controller;

import com.belarek.SADPPBV.entity.AuthToken;
import com.belarek.SADPPBV.service.ConnectedUsersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class WebController {

    @Autowired
    private ConnectedUsersService connectedUsersService;

    @GetMapping("/connected-users")
    public String showConnectedUsers(Model model) {
        List<AuthToken> validTokens = connectedUsersService.getValidTokens();
        model.addAttribute("validTokens", validTokens);

        Map<Long, String> connectedUsers = connectedUsersService.getConnectedUsers();
        model.addAttribute("connectedUsers", connectedUsers);

        return "connected-users";
    }
}