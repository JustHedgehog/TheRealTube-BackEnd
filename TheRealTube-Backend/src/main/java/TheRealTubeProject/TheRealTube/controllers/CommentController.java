package TheRealTubeProject.TheRealTube.controllers;


import TheRealTubeProject.TheRealTube.models.Comment;
import TheRealTubeProject.TheRealTube.payload.response.ReturnMessage;
import TheRealTubeProject.TheRealTube.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Comment>> getAllComments(){
        List<Comment> temp = commentService.getAllComments();
        return new ResponseEntity<>(temp, HttpStatus.OK);
    }

    @GetMapping("/video/{videoId}")
    public ResponseEntity<List<Comment>> getAllCommentsRelatedToVideo(@PathVariable(name = "videoId") Long videoId){

        return new ResponseEntity<>(commentService.getAllCommentsRelatedToVideo(videoId), HttpStatus.OK);

    }

    @GetMapping("/{commentId}")
    public  ResponseEntity<Comment> getComment (@PathVariable(name = "commentId") Long commentId){
        return new ResponseEntity<>(commentService.getCommentById(commentId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Comment>> getAllCommentsByUser(@PathVariable(name = "userId") Long userId){
        return new ResponseEntity<>(commentService.getAllCommentsByUser(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    @Transactional
    public ResponseEntity<ReturnMessage> deleteComment(@PathVariable(name = "commentId") Long commentId){

        commentService.deleteCommentById(commentId);
        return new ResponseEntity<>(new ReturnMessage(commentId, "Comment deleted"), HttpStatus.OK);
    }

    @PostMapping("/{videoId}/{userId}")
    public ResponseEntity<Comment> addComment(@PathVariable(name = "userId")Long userId,
                                              @PathVariable(name = "videoId")Long videoId,
                                              @RequestPart(name="description") String description){

        Comment comment = commentService.addComment(description, userId, videoId);

        return new ResponseEntity<>(comment,HttpStatus.CREATED);
    }
}
