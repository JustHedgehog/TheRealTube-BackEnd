package TheRealTubeProject.TheRealTube.services;

import TheRealTubeProject.TheRealTube.exceptions.UserNotFoundException;
import TheRealTubeProject.TheRealTube.models.Comment;
import TheRealTubeProject.TheRealTube.models.User;
import TheRealTubeProject.TheRealTube.repositories.CommentRepository;
import TheRealTubeProject.TheRealTube.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultCommentService implements CommentService{

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    public DefaultCommentService(CommentRepository commentRepository,
                                 UserRepository userRepository,
                                 AuthService authService){
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.authService = authService;
    }

    @Override
    public Comment addComment(String description, Long userId) {

        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }

        Comment newComment = new Comment(description, user.get());
        user.get().getComments().add(newComment);

        commentRepository.save(newComment);
        userRepository.save(user.get());

        return newComment;
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> getAllCommentsByUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get().getComments();
        }
        return Collections.emptyList();
    }


    @Override
    public void deleteCommentById(Long commentId) {

        commentRepository.findById(commentId).ifPresent(comment -> {
            authService.isHeHasPermissions(comment.getUser().getId());

            userRepository.findById(comment.getUser().getId()).ifPresent(user -> {
                user.getComments().remove(comment);
                commentRepository.deleteById(commentId);
            });
        });
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).get();
    }
}
