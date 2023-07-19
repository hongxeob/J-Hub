package com.project.jhub.presentation;

import com.project.jhub.post.application.PostService;
import com.project.jhub.post.domain.Category;
import com.project.jhub.post.dto.response.PostListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String home(Model model, String title,
                       @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        PostListResponse posts = postService.findAllWithUserAndComments(pageable);
        PostListResponse knowledge = postService.findByCategory(Category.KNOWLEDGE, pageable);
        PostListResponse qna = postService.findByCategory(Category.QNA, pageable);

        model.addAttribute("qna", qna);
        model.addAttribute("knowledge", knowledge);
        model.addAttribute("postByTitle", posts);
        model.addAttribute("posts", posts);
        return "home";
    }
}
