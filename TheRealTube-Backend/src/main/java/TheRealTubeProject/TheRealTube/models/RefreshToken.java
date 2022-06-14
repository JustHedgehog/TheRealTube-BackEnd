package TheRealTubeProject.TheRealTube.models;
import lombok.Data;
import org.hibernate.annotations.OnDelete;

import java.time.Instant;
import javax.persistence.*;

@Entity(name = "refreshtoken")
@Data
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(nullable = false, unique = true)
    private String token;
    @Column(nullable = false)
    private Instant expiryDate;
}
