package TheRealTubeProject.TheRealTube.services;

import TheRealTubeProject.TheRealTube.models.Comment;

import java.util.List;

public interface CommentService {

    Comment addComment(String description, Long userId, Long videoId);
    List<Comment> getAllComments ();
    List<Comment> getAllCommentsByUser(Long userId);
    List<Comment> getAllCommentsRelatedToVideo(Long videoId);
    void deleteCommentById(Long commentId);
    Comment getCommentById(Long id);

}
