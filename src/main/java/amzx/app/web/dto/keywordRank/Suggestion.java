package amzx.app.web.dto.keywordRank;

import lombok.Data;

@Data
public class Suggestion {
    private String value;
    private boolean spellCorrected;
}
