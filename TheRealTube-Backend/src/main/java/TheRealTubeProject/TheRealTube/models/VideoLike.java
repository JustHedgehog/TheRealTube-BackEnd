package TheRealTubeProject.TheRealTube.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class VideoLike {

    Boolean liked;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
