package TheRealTubeProject.TheRealTube.models;

import TheRealTubeProject.TheRealTube.services.CommentService;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne()
    @JsonManagedReference
    private User user;

    @ManyToOne()
    @JsonBackReference
    private Video video;

    public Comment() {
    }

    public Comment(String description, User user, Video video) {
        this.description = description;
        this.user = user;
        this.video = video;
    }

}
