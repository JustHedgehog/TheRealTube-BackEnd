package TheRealTubeProject.TheRealTube.services;

import TheRealTubeProject.TheRealTube.exceptions.UserNotFoundException;
import TheRealTubeProject.TheRealTube.exceptions.VideoNotFoundException;
import TheRealTubeProject.TheRealTube.models.Comment;
import TheRealTubeProject.TheRealTube.models.User;
import TheRealTubeProject.TheRealTube.models.Video;
import TheRealTubeProject.TheRealTube.repositories.CommentRepository;
import TheRealTubeProject.TheRealTube.repositories.UserRepository;
import TheRealTubeProject.TheRealTube.repositories.VideoRepository;
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
    private final VideoRepository videoRepository;

    public DefaultCommentService(CommentRepository commentRepository,
                                 UserRepository userRepository,
                                 AuthService authService,
                                 VideoRepository videoRepository){
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.authService = authService;
        this.videoRepository = videoRepository;
    }

    @Override
    public Comment addComment(String description, Long userId, Long videoId) {

        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }

        Optional<Video> video = videoRepository.findById(videoId);
        if(!video.isPresent()){
            throw new VideoNotFoundException();
        }

        Comment newComment = new Comment(description, user.get(), video.get());
        user.get().getComments().add(newComment);
        video.get().getComments().add(newComment);

        commentRepository.save(newComment);
        userRepository.save(user.get());
        videoRepository.save(video.get());

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
    public List<Comment> getAllCommentsRelatedToVideo(Long videoId) {
        Optional<Video> video = videoRepository.findById(videoId);
        if(video.isPresent()){
            return video.get().getComments();
        }
        return Collections.emptyList();
    }

    @Override
    public void deleteCommentById(Long commentId) {

        commentRepository.findById(commentId).ifPresent(comment -> {
            authService.isHeHasPermissions(comment.getUser().getId());

            userRepository.findById(comment.getUser().getId()).ifPresent(user -> {

                videoRepository.findById(comment.getVideo().getId()).ifPresent(video -> {
                    user.getComments().remove(comment);
                    video.getComments().remove(comment);
                    commentRepository.deleteById(commentId);
                });
            });
        });
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).get();
    }
}
