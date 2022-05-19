package TheRealTubeProject.TheRealTube.mockRepository;

import TheRealTubeProject.TheRealTube.models.Video;
import TheRealTubeProject.TheRealTube.repositories.VideoRepository;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@TestComponent
public class MockVideoRepo implements VideoRepository {

    public ConcurrentHashMap<Long, Video> videoMapRepo = new ConcurrentHashMap<>();

    @Override
    public List<Video> findAll() {
        return new ArrayList<>(videoMapRepo.values());
    }

    @Override
    public List<Video> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Video> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Video> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return videoMapRepo.size();
    }

    @Override
    public void deleteById(Long aLong) {
        videoMapRepo.remove(aLong);
    }

    @Override
    public void delete(Video entity) {
        videoMapRepo.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Video> entities) {

    }

    @Override
    public void deleteAll() {
    }

    @Override
    public <S extends Video> S save(S entity) {
        if (entity.getId() == null) {
            entity.setId(Long.valueOf(UUID.randomUUID().toString()));
        }
        videoMapRepo.put(entity.getId(), entity);

        return entity;
    }


    @Override
    public <S extends Video> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Video> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Video> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Video> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Video> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Video getOne(Long aLong) {
        return null;
    }

    @Override
    public Video getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Video> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Video> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Video> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Video> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Video> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Video> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Video, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
