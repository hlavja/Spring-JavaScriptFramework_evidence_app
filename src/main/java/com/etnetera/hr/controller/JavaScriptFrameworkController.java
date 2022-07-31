package com.etnetera.hr.controller;

import com.etnetera.hr.service.JavaScriptFrameworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.etnetera.hr.data.JavaScriptFramework;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

/**
 * Simple REST controller for accessing application logic.
 *
 * @author Etnetera
 */
@RestController
public class JavaScriptFrameworkController {

    private final JavaScriptFrameworkService javaScriptFrameworkService;

    @Autowired
    public JavaScriptFrameworkController(JavaScriptFrameworkService javaScriptFrameworkService) {
        this.javaScriptFrameworkService = javaScriptFrameworkService;
    }

    /**
     * GET method for getting all framework entities
     * @return framework entities
     */
    @GetMapping("/frameworks")
    public Iterable<JavaScriptFramework> frameworks() {
        return javaScriptFrameworkService.getAllFrameworks();
    }

    /**
     * POST method for creating new framework entity
     * @param javaScriptFramework new framework entity
     * @return created framework entity
     */
    @PostMapping("/framework")
    public ResponseEntity<JavaScriptFramework> createFramework(@RequestBody JavaScriptFramework javaScriptFramework) {
        HttpStatus responseStatus;

        if (javaScriptFramework.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "JavaScriptFramework should not have ID");
        }

        JavaScriptFramework result = javaScriptFrameworkService.createFramework(javaScriptFramework);

        responseStatus = result != null ? HttpStatus.CREATED : HttpStatus.CONFLICT;

        // ----------------------- return
        return new ResponseEntity<>(result, responseStatus);
    }

    /**
     * PUT method for modifying framework entity
     * @param javaScriptFramework edited framework entity
     * @return modified framework entity
     */
    @PutMapping("/framework")
    public ResponseEntity<JavaScriptFramework> updateFramework(@RequestBody JavaScriptFramework javaScriptFramework) {
        HttpStatus responseStatus;

        if (javaScriptFramework.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "JavaScriptFramework should have ID");
        }

        if (javaScriptFrameworkService.doNotExistsById(javaScriptFramework.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "JavaScriptFramework not found");
        }

        JavaScriptFramework result = javaScriptFrameworkService.createFramework(javaScriptFramework);

        responseStatus = result != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        // ----------------------- return
        return new ResponseEntity<>(result, responseStatus);
    }

    /**
     * DELETE method for deleting entity
     * @param id identification of entity
     * @return HTTP status
     */
    @DeleteMapping("/framework/{id}")
    public ResponseEntity<JavaScriptFramework> deleteFramework(@PathVariable Long id) {

        if (javaScriptFrameworkService.doNotExistsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "JavaScriptFramework not found");
        }

        javaScriptFrameworkService.delete(id);

        // ----------------------- return
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * GET method for getting entities by query parameters
     * @param id framework entity id
     * @param name framework name
     * @param version framework version
     * @param hypeLevel hype level of framework
     * @param deprecationDateGt deprecation date filter greater than
     * @param deprecationDateLt deprecation date filter lower than
     * @return list of framework entities for search filter
     */
    @GetMapping("/framework")
    public Iterable<JavaScriptFramework> searchFrameworks(@RequestParam(required = false) Long id,
                                                          @RequestParam(required = false) String name,
                                                          @RequestParam(required = false) String version,
                                                          @RequestParam(required = false) Integer hypeLevel,
                                                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deprecationDateGt,
                                                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deprecationDateLt) {
        return javaScriptFrameworkService.searchJavaScriptsFrameworksByCriteria(id, name, version, hypeLevel, deprecationDateGt, deprecationDateLt);
    }
}
