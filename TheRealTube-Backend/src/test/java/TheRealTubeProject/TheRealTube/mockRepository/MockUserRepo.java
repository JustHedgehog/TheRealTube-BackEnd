package TheRealTubeProject.TheRealTube.mockRepository;

import TheRealTubeProject.TheRealTube.models.User;
import TheRealTubeProject.TheRealTube.repositories.UserRepository;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@TestComponent
public class MockUserRepo implements UserRepository {

    public ConcurrentHashMap<Long, User> userMapRepo
            = new ConcurrentHashMap<>();

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMapRepo.values());
    }

    @Override
    public List<User> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<User> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return userMapRepo.size();
    }

    @Override
    public void deleteById(Long aLong) {
        userMapRepo.remove(aLong);
    }

    @Override
    public void delete(User entity) {
        userMapRepo.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {
    }

    @Override
    public <S extends User> S save(S entity) {
        if (entity.getId() == null) {
            entity.setId(new Random().nextLong());
        }
        userMapRepo.put(entity.getId(), entity);

        return entity;
    }


    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.of(userMapRepo.get(aLong));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends User> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<User> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public User getOne(Long aLong) {
        return null;
    }

    @Override
    public User getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userMapRepo.values().stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userMapRepo.values().stream().anyMatch(user -> user.getUsername().equals(username));


    }

    @Override
    public Boolean existsByEmail(String email) {
        return userMapRepo.values().stream().anyMatch(user -> user.getEmail().equals(email));

    }

    public void clean() {
        userMapRepo.clear();
    }
}
