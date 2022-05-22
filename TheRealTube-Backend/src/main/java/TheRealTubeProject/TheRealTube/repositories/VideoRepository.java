package TheRealTubeProject.TheRealTube.repositories;

import TheRealTubeProject.TheRealTube.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query("SELECT v from Video v WHERE v.name LIKE %:name%")
    List<Video> findVideosByNameReg(@Param("name") String name);

}
