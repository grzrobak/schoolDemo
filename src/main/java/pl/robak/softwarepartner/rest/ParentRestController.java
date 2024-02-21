package pl.robak.softwarepartner.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.robak.softwarepartner.model.summary.ParentSummary;
import pl.robak.softwarepartner.model.summary.SchoolSummary;
import pl.robak.softwarepartner.service.ParentSummaryService;
import pl.robak.softwarepartner.service.SchoolSummaryService;

@RestController
@RequestMapping(path = "/parent")
public class ParentRestController {


    private final ParentSummaryService parentSummaryService;

    @Autowired
    public ParentRestController(ParentSummaryService parentSummaryService) {
        this.parentSummaryService = parentSummaryService;
    }

    @GetMapping(value = "/{id}/{year}/{month}", produces = "application/json")
    public ResponseEntity<ParentSummary> getParentSummary(@PathVariable Long id, @PathVariable int year, @PathVariable int month) {
        return ResponseEntity.status(200).body(parentSummaryService.createParentSummary(id, year, month));
    }

}
