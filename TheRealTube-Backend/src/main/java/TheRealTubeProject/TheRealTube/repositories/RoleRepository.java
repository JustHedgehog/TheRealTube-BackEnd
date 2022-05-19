package TheRealTubeProject.TheRealTube.repositories;

import TheRealTubeProject.TheRealTube.models.ERole;
import TheRealTubeProject.TheRealTube.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole role);
}
