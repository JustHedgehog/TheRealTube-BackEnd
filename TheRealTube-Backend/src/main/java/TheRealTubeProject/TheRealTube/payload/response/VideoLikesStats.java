package TheRealTubeProject.TheRealTube.payload.response;

import lombok.Data;

@Data
public class VideoLikesStats {

    public Long likes;
    public Long disLikes;

    public VideoLikesStats withLikes(Long likeCounts){
        this.likes=likeCounts;
        return this;
    }
    public VideoLikesStats withDislikes(Long DislikeCounts){
        this.disLikes=DislikeCounts;
        return this;
    }

}
