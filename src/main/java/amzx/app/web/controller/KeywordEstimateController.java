package amzx.app.web.controller;

import amzx.app.data.service.impl.KeywordEstimateService;
import amzx.app.web.dto.keywordRank.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/keywords")
@CrossOrigin(origins = "*", maxAge = 3600)
public class KeywordEstimateController {


  private final KeywordEstimateService keywordEstimateService;

  @Autowired
  public KeywordEstimateController(KeywordEstimateService keywordEstimateService) {
    this.keywordEstimateService = keywordEstimateService;
  }

  @GetMapping("/estimate")
  public Result getEstimate(@RequestParam String keyword) {
    return Result.builder()
        .keyword(keyword)
        .score(keywordEstimateService.estimateValueOfKeyword(keyword))
        .build();
  }
  @GetMapping("/test")
  public ResponseEntity<String> getTest(@RequestParam String keyword) {
    String test = "test";
    return ResponseEntity.ok(test);
  }

}
