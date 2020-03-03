package threewks.framework.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import threewks.framework.ref.ReferenceDataDto;
import threewks.framework.ref.ReferenceDataService;

import java.util.List;
import java.util.Map;


@RestController
public class ReferenceDataController {

    private final ReferenceDataService referenceDataService;

    public ReferenceDataController(ReferenceDataService referenceDataService) {
        this.referenceDataService = referenceDataService;
    }

    @GetMapping("/api/reference-data")
    public Map<String, List<ReferenceDataDto>> getReferenceData() {
        return referenceDataService.getReferenceData();
    }
}
