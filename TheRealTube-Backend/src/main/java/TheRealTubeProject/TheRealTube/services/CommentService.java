package TheRealTubeProject.TheRealTube.services;

import TheRealTubeProject.TheRealTube.models.Comment;

import java.util.List;

public interface CommentService {

    Comment addComment(String description, Long userId);
    List<Comment> getAllComments ();
    List<Comment> getAllCommentsByUser(Long userId);
    void deleteCommentById(Long commentId);
    Comment getCommentById(Long id);

}
