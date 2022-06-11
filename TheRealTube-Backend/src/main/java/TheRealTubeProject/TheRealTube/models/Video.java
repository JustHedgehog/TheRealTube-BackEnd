package TheRealTubeProject.TheRealTube.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String fileurl;

    private String objectKey;

    private String description;

    @OneToMany
    Map<Long, VideoLike> likes = new HashMap<>();

    @ManyToOne
    @JsonManagedReference
    User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    List<Comment> comments = new ArrayList<>();

    public Video() {
    }

    public Video(Long id, String name, String fileurl, String objectKey, String description, User user) {
        this.id = id;
        this.name = name;
        this.fileurl = fileurl;
        this.objectKey = objectKey;
        this.description = description;
        this.user = user;
    }

}
