package TheRealTubeProject.TheRealTube.models;

import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
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
    User user;

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
