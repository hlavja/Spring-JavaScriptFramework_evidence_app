package com.etnetera.hr.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.etnetera.hr.data.JavaScriptFramework;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

/**
 * Spring data repository interface used for accessing the data in database.
 * 
 * @author Etnetera
 *
 */
public interface JavaScriptFrameworkRepository extends CrudRepository<JavaScriptFramework, Long>, JavaScriptFrameworkRepositoryInterface {

    @Query("from JavaScriptFramework where " +
                "(:id is null or id = :id) and " +
                "(:name is null or name = :name) and " +
                "(:version is null or version = :version) and" +
                "(:hypeLevel is null or hypeLevel = :hypeLevel) and" +
                "(:deprecationDateGt is null or deprecationDate > :deprecationDateGt) and" +
                "(:deprecationDateLt is null or deprecationDate < :deprecationDateLt)")
    Iterable<JavaScriptFramework> searchJavaScriptFrameworkBy(@Param("id") Long id,
                                                              @Param("name") String name,
                                                              @Param("version") String version,
                                                              @Param("hypeLevel") Integer hypeLevel,
                                                              @Param("deprecationDateGt") LocalDate deprecationDateGt,
                                                              @Param("deprecationDateLt") LocalDate deprecationDateLt);
}
