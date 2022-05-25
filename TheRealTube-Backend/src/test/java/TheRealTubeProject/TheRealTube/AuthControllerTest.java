package TheRealTubeProject.TheRealTube;

import TheRealTubeProject.TheRealTube.controllers.AuthController;
import TheRealTubeProject.TheRealTube.mockRepository.MockUserRepo;
import TheRealTubeProject.TheRealTube.mockRepository.MockVideoRepo;
import TheRealTubeProject.TheRealTube.models.ERole;
import TheRealTubeProject.TheRealTube.models.Role;
import TheRealTubeProject.TheRealTube.models.User;
import TheRealTubeProject.TheRealTube.payload.request.LoginRequest;
import TheRealTubeProject.TheRealTube.payload.request.SignupRequest;
import TheRealTubeProject.TheRealTube.repositories.RoleRepository;
import TheRealTubeProject.TheRealTube.security.jwt.JwtUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootTest(
        classes = AuthControllerTest.class)
@ExtendWith(MockitoExtension.class)
@ComponentScan(basePackages = "TheRealTubeProject.TheRealTube")
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    MockUserRepo mockUserRepo;

    @Autowired
    MockVideoRepo mockVideoRepo;

    AuthController authController;


    private static final String userName = "HappyUserTest";
    private static final String userEmail = "HappyUser@Test.com";
    private static final String userPassword = "HappyUserPassword$&$";

    @BeforeEach
    public void init() {
        authController = new AuthController(jwtUtils, encoder,
                roleRepository,
                mockUserRepo,
                authenticationManager
        );
    }

    @Test
    void create_account_and_login_succesfully() {
        User user = registerUser();
        loginUser(user);
    }

    private User registerUser() {
        //given
        User user = new User();
        user.setUsername(userName);
        user.setPassword(userPassword);
        user.setEmail(userEmail);
        user.setRoles(Set.of(new Role(ERole.ROLE_USER)));


        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername(user.getUsername());
        signupRequest.setPassword(user.getPassword());
        signupRequest.setEmail(user.getEmail());
        signupRequest.setRole(Set.of("user"));

        //when
        ResponseEntity actual = authController.registerUser(signupRequest);

        //then

        ResponseEntity expected = new ResponseEntity<>("Zarejestrowano użytkownika!", HttpStatus.CREATED);

        Assertions.assertEquals(expected, actual);

        return user;
    }

    private void loginUser(User user) {
        //given
        LoginRequest lr = new LoginRequest();
        lr.setUsername(user.getUsername());
        lr.setPassword(user.getPassword());
        //when
        ResponseEntity response = authController.authenticateUser(lr);
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}
