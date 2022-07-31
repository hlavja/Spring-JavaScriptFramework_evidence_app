package com.etnetera.hr.service;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Simple service for accessing logic operations with JavaScriptFramework entity.
 *
 * @author hlavja
 *
 */
@Service
public class JavaScriptFrameworkService {

    private final JavaScriptFrameworkRepository javaScriptFrameworkRepository;

    @Autowired
    public JavaScriptFrameworkService(JavaScriptFrameworkRepository javaScriptFrameworkRepository) {
        this.javaScriptFrameworkRepository = javaScriptFrameworkRepository;
    }

    public Iterable<JavaScriptFramework> getAllFrameworks() {
        return javaScriptFrameworkRepository.findAll();
    }

    public JavaScriptFramework createFramework(JavaScriptFramework javaScriptFramework) {
        return javaScriptFrameworkRepository.save(javaScriptFramework);
    }

    public boolean doNotExistsById(Long id) {
        return !javaScriptFrameworkRepository.existsById(id);
    }

    public void delete(Long id) {
        javaScriptFrameworkRepository.deleteById(id);
    }

    public Iterable<JavaScriptFramework> searchJavaScriptsFrameworksBy(Long id, String name, String version, Integer hypeLevel,  LocalDate deprecationDateGt,  LocalDate deprecationDateLt) {
        return javaScriptFrameworkRepository.searchJavaScriptFrameworkBy(id, name, version, hypeLevel, deprecationDateGt, deprecationDateLt);
    }

    public Iterable<JavaScriptFramework> searchJavaScriptsFrameworksByCriteria(Long id, String name, String version, Integer hypeLevel,  LocalDate deprecationDateGt,  LocalDate deprecationDateLt) {
        return javaScriptFrameworkRepository.searchJavaScriptFrameworkByCriteria(id, name, version, hypeLevel, deprecationDateGt, deprecationDateLt);
    }
    public Optional<JavaScriptFramework> getById(Long id) {
        return javaScriptFrameworkRepository.findById(id);
    }
}
