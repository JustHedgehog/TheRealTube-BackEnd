package TheRealTubeProject.TheRealTube.models;

import lombok.Data;

@Data
public class ReturnMessage {
    Long id;
    String message;

    public ReturnMessage(Long id, String message) {
        this.id = id;
        this.message = message;
    }
}
