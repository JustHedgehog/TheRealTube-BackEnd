package TheRealTubeProject.TheRealTube.models;

import TheRealTubeProject.TheRealTube.services.CommentService;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne
    @JsonManagedReference
    private User user;

    public Comment() {
    }

    public Comment(String description, User user) {
        this.description = description;
        this.user = user;
    }

}
