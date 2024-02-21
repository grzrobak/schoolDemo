package pl.robak.softwarepartner.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.robak.softwarepartner.model.summary.SchoolSummary;
import pl.robak.softwarepartner.service.SchoolSummaryService;

@RestController
@RequestMapping(path = "/school")
public class SchoolRestController {


    private final SchoolSummaryService schoolSummaryService;

    @Autowired
    public SchoolRestController(SchoolSummaryService schoolSummaryService) {
        this.schoolSummaryService = schoolSummaryService;
    }

    @GetMapping(value = "/{id}/{year}/{month}", produces = "application/json")
    public ResponseEntity<SchoolSummary> getSchoolSummary(@PathVariable Long id, @PathVariable int year, @PathVariable int month) {
        return ResponseEntity.status(200).body(schoolSummaryService.createSchoolSummary(id,year, month));
    }

}
