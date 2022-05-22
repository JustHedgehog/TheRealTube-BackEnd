package TheRealTubeProject.TheRealTube.repositories;

import TheRealTubeProject.TheRealTube.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findVideosByNameRegex();

}
