package TheRealTubeProject.TheRealTube.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.net.URL;

@Data
@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private URL fileurl;

    private String objectKey;

    private String description;

    @ManyToOne
    @JsonManagedReference
    User user;

    public Video() {
    }

    public Video(Long id, String name, URL fileurl, String objectKey, String description, User user) {
        this.id = id;
        this.name = name;
        this.fileurl = fileurl;
        this.objectKey = objectKey;
        this.description = description;
        this.user = user;
    }


}
