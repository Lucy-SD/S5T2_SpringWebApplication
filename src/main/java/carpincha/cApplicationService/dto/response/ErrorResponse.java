package carpincha.cApplicationService.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        int status,
        String error,
        String message,
        @JsonFormat(pattern = "yy-MM-dd HH:mm")
        LocalDateTime timestamp,
        String path
) {
    public ErrorResponse {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }

}
