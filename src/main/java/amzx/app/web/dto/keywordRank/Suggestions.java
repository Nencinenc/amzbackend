package amzx.app.web.dto.keywordRank;

import lombok.Data;

import java.util.List;

@Data
public class Suggestions {
    private List<Suggestion> suggestions;
}
