package TheRealTubeProject.TheRealTube.repositories;

import TheRealTubeProject.TheRealTube.models.VideoLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoLikeRepository extends JpaRepository<VideoLike, Long> {
}
