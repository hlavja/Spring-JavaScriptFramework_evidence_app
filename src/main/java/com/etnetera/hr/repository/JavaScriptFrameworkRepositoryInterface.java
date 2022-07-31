package com.etnetera.hr.repository;

import com.etnetera.hr.data.JavaScriptFramework;

import java.time.LocalDate;

/**
 * Interface for custom repository extension
 *
 * @author hlavja
 *
 */
public interface JavaScriptFrameworkRepositoryInterface {

    Iterable<JavaScriptFramework> searchJavaScriptFrameworkByCriteria(Long id, String name, String version, Integer hypeLevel,
                                                                      LocalDate deprecationDateGt, LocalDate deprecationDateLt);
}
