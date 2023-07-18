package com.project.jhub.presentation.user;

import com.project.jhub.post.application.PostService;
import com.project.jhub.post.dto.response.PostListResponse;
import com.project.jhub.user.application.UserService;
import com.project.jhub.user.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    private final PostService postService;

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/{id}")
    public String user(@PathVariable Long id, Model model,
                       @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        UserResponse user = userService.findById(id);
        PostListResponse posts = postService.findByUserId(id, pageable);

        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        return "user/user";
    }
}
