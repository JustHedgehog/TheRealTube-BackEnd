package TheRealTubeProject.TheRealTube.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.net.URL;

@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private URL fileurl;

    private String objectKey;

    @ManyToOne
    User user;

    public Video() {
    }

    public Video(Long id, String name, URL fileurl, String objectKey, User user) {
        this.id = id;
        this.name = name;
        this.fileurl = fileurl;
        this.objectKey = objectKey;
        this.user = user;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public URL getFileurl() {
        return fileurl;
    }

    public void setFileurl(URL fileurl) {
        this.fileurl = fileurl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
