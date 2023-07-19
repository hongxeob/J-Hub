package com.project.jhub.presentation.post;

import com.project.jhub.post.application.PostService;
import com.project.jhub.post.domain.Category;
import com.project.jhub.post.dto.response.PostListResponse;
import com.project.jhub.post.dto.response.PostResponse;
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
@RequestMapping("/posts")
public class PostViewController {

    private final PostService postService;

    @GetMapping
    public String posts(Model model, String title,
                        @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        PostListResponse posts = null;

        if (title == null) {
            posts = postService.findAllWithUserAndComments(pageable);
        } else {
            posts = postService.findByTitle(title, pageable);
        }

        model.addAttribute("postByTitle", posts);
        model.addAttribute("posts", posts);

        return "post/posts";
    }

    @GetMapping("/writeForm")
    public String writeForm() {
        return "post/writeForm";
    }

    @GetMapping("/category/{category}")
    public String postByCategory(@PathVariable Category category, String title, Model model, @PageableDefault(size = 5, sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable) {

        PostListResponse posts = null;

        if (title == null) {
            posts = postService.findByCategory(category, pageable);
        } else {
            posts = postService.findByTitle(title, pageable);
        }

        model.addAttribute("posts", posts);
        model.addAttribute("category", category);
        return "post/postByCategory";
    }

    @GetMapping("/{id}")
    public String post(@PathVariable Long id, Model model) {
        PostResponse post = postService.findByIdWithUserAndComments(id);
        model.addAttribute("post", post);

        return "post/post";
    }

    @GetMapping("/{postId}/updateForm")
    public String updateForm(@PathVariable Long postId, Model model) {
        PostResponse post = postService.findById(postId);

        model.addAttribute("post", post);

        return "post/updateForm";
    }
}
