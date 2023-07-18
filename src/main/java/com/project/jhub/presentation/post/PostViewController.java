package com.project.jhub.presentation.post;

import com.project.jhub.comment.application.CommentService;
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
    private final CommentService commentService;

    @GetMapping
    public String posts(Model model,
                        @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        PostListResponse posts = postService.findAllWithUserAndComments(pageable);
        model.addAttribute("posts", posts);
        return "post/posts";
    }

    @GetMapping("/writeForm")
    public String writeForm() {
        return "post/writeForm";
    }

    @GetMapping("/category/{category}")
    public String postByCategory(@PathVariable Category category, Model model, @PageableDefault(size = 5, sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable) {
        PostListResponse posts = postService.findByCategory(category, pageable);
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
