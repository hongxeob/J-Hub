package com.project.jhub.presentation.user;

import com.project.jhub.user.application.UserService;
import com.project.jhub.user.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserViewController {

    private final UserService userService;

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/{id}")
    public String user(@PathVariable Long id, Model model) {
        UserResponse user = userService.findById(id);
        model.addAttribute("user", user);
        return "user/user";
    }
}
