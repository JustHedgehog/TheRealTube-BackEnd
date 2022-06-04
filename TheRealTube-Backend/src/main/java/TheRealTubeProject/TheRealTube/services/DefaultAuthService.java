package TheRealTubeProject.TheRealTube.services;

import TheRealTubeProject.TheRealTube.exceptions.NoPermissionException;
import TheRealTubeProject.TheRealTube.repositories.UserRepository;
import TheRealTubeProject.TheRealTube.security.services.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultAuthService implements AuthService{

    private final UserRepository userRepository;

    public DefaultAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isHeHasPermissions(Long userId) {

        UserDetailsImpl userDetails =(UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        userRepository.findById(userId).ifPresent(user -> {
            if(!user.getEmail().equals(userDetails.getEmail()) && roles.contains("ROLE_USER")) {
                throw new NoPermissionException();
            }
        });
        return true;
    }
}
