package amzx.app.web.dto.keywordRank;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Result {
    private String keyword;
    private int score;
}
