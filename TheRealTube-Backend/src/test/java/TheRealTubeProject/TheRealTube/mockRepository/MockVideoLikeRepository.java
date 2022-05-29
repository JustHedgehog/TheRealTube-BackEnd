package TheRealTubeProject.TheRealTube.mockRepository;

import TheRealTubeProject.TheRealTube.models.VideoLike;
import TheRealTubeProject.TheRealTube.repositories.VideoLikeRepository;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
@TestComponent
public class MockVideoLikeRepository implements VideoLikeRepository {

    public ConcurrentHashMap<Long, VideoLike> videoLikeMapRepo = new ConcurrentHashMap<>();


    @Override
    public List<VideoLike> findAll() {
        return videoLikeMapRepo.values().stream().toList();
    }

    @Override
    public List<VideoLike> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<VideoLike> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<VideoLike> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return videoLikeMapRepo.size();
    }

    @Override
    public void deleteById(Long aLong) {
        videoLikeMapRepo.remove(aLong);

    }

    @Override
    public void delete(VideoLike entity) {
        videoLikeMapRepo.remove(entity.getId());

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends VideoLike> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends VideoLike> S save(S entity) {
        if (entity.getId() == null) {
            entity.setId(new Random().nextLong());
        }
        videoLikeMapRepo.put(entity.getId(), entity);

        return entity;    }

    @Override
    public <S extends VideoLike> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<VideoLike> findById(Long aLong) {
        return Optional.ofNullable(videoLikeMapRepo.get(aLong));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends VideoLike> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends VideoLike> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<VideoLike> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public VideoLike getOne(Long aLong) {
        return null;
    }

    @Override
    public VideoLike getById(Long aLong) {
        return videoLikeMapRepo.get(aLong);
    }

    @Override
    public <S extends VideoLike> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends VideoLike> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends VideoLike> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends VideoLike> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends VideoLike> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends VideoLike> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends VideoLike, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
