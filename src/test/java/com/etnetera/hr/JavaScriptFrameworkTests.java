package com.etnetera.hr;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.etnetera.hr.service.JavaScriptFrameworkService;
import com.etnetera.hr.utils.TestUtils;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class used for Spring Boot/MVC based tests.
 * 
 * @author Etnetera
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class JavaScriptFrameworkTests {
    @Autowired
    private MockMvc restMockMvc;
    @Autowired
    private JavaScriptFrameworkRepository javaScriptFrameworkRepository;
    @Autowired
    private JavaScriptFrameworkService javaScriptFrameworkService;

    /**
     * Clear database data before each test
     */
    @BeforeEach
    public void flushDatabase() {
        javaScriptFrameworkRepository.deleteAll();
    }

    /**
     * Testing creating new framework entity in repository
     * @throws Exception ex
     */
    @Test
    public void frameworkPostEndpointTest() throws Exception {
        JavaScriptFramework frameworkDTO = new JavaScriptFramework("com.etnetera.test", LocalDate.now(), "1.0.0", 5);

        // ----------------------- send request with ID
        frameworkDTO.setId(1L);
        restMockMvc.perform(post("/framework")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.convertObjectToJsonBytes(frameworkDTO)))
                .andExpect(status().isBadRequest());

        // ----------------------- send request without ID
        frameworkDTO.setId(null);
        restMockMvc.perform(post("/framework")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.convertObjectToJsonBytes(frameworkDTO)))
                .andExpect(status().isCreated());

        Iterable<JavaScriptFramework> databaseTableSize = javaScriptFrameworkService.getAllFrameworks();
        assertThat(StreamSupport.stream(databaseTableSize.spliterator(), false).count()).isEqualTo(1);
        JavaScriptFramework entity = databaseTableSize.iterator().next();
        assertThat(entity.getName()).isEqualTo("com.etnetera.test");
        assertThat(entity.getVersion()).isEqualTo("1.0.0");
        assertThat(entity.getHypeLevel()).isEqualTo(5);
    }

    /**
     * Testing modifying framework entity in repository
     * @throws Exception ex
     */
    @Test
    public void frameworkPutEndpointTest() throws Exception {
        JavaScriptFramework frameworkDTO = new JavaScriptFramework("com.etnetera.test", LocalDate.now(), "1.0.0", 5);

        // ----------------------- send request without ID
        restMockMvc.perform(put("/framework")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.convertObjectToJsonBytes(frameworkDTO)))
                .andExpect(status().isBadRequest());

        // ----------------------- send request with ID
        frameworkDTO.setId(100L);
        restMockMvc.perform(put("/framework")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.convertObjectToJsonBytes(frameworkDTO)))
                .andExpect(status().isNotFound());

        // ----------------------- send request with ID and exists entity in database
        JavaScriptFramework frameworkEntity = new JavaScriptFramework("com.etnetera.test", LocalDate.now(), "0.0.1", 1);
        JavaScriptFramework savedJavaScriptFramework = javaScriptFrameworkService.createFramework(frameworkEntity);
        frameworkDTO.setId(savedJavaScriptFramework.getId());

        restMockMvc.perform(put("/framework")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.convertObjectToJsonBytes(frameworkDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.version").value("1.0.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hypeLevel").value(5));

        // ----------------------- check database save
        Optional<JavaScriptFramework> savedEntity = javaScriptFrameworkService.getById(savedJavaScriptFramework.getId());
        assertThat(savedEntity).isPresent();
        assertThat(savedEntity.get().getHypeLevel()).isEqualTo(5);
        assertThat(savedEntity.get().getVersion()).isEqualTo("1.0.0");
    }

    /**
     * Testing deletion of framework from repository
     * @throws Exception ex
     */
    @Test
    public void frameworkDeleteEndpointTest() throws Exception {
        JavaScriptFramework frameworkDTO = new JavaScriptFramework("com.github.test", LocalDate.now(), "1.0.0", 5);

        // ----------------------- send request without existing ID
        restMockMvc.perform(delete("/framework/{id}", 50)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.convertObjectToJsonBytes(frameworkDTO)))
                .andExpect(status().isNotFound());


        // ----------------------- send request without existing ID
        javaScriptFrameworkService.createFramework(frameworkDTO);
        Iterable<JavaScriptFramework> databaseTableSize = javaScriptFrameworkService.getAllFrameworks();
        JavaScriptFramework entity = databaseTableSize.iterator().next();
        long databaseSize = StreamSupport.stream(databaseTableSize.spliterator(), false).count();
        assertThat(databaseSize).isEqualTo(1);

        restMockMvc.perform(delete("/framework/{id}", entity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        databaseTableSize = javaScriptFrameworkService.getAllFrameworks();
        assertThat(StreamSupport.stream(databaseTableSize.spliterator(), false).count()).isEqualTo(databaseSize - 1);
    }

    /**
     * Testing framework filtering by name, version, deprecation date
     * @throws Exception ex
     */
    @Test
    public void frameworkGetEndpointTest() throws Exception {
        javaScriptFrameworkService.createFramework(
                new JavaScriptFramework("com.etnetera.test", LocalDate.now(), "1.0.0", 5)
        );
        javaScriptFrameworkService.createFramework(
                new JavaScriptFramework("com.etnetera.test", LocalDate.now().plusDays(20), "1.5.0", 1)
        );
        javaScriptFrameworkService.createFramework(
                new JavaScriptFramework("com.etnetera.test", LocalDate.now().plusDays(100), "2.0.0", 3)
        );
        javaScriptFrameworkService.createFramework(
                new JavaScriptFramework("com.hlavja.test", LocalDate.now().plusDays(100), "2.0.1", 3)
        );

        // ----------------------- send request with id
        restMockMvc.perform(get("/framework")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("com.etnetera.test"));

        // ----------------------- send request with name
        restMockMvc.perform(get("/framework")
                        .param("name", "com.etnetera.test"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("com.etnetera.test"));

        // ----------------------- send request with name and version
        restMockMvc.perform(get("/framework")
                        .param("name", "com.etnetera.test")
                        .param("version", "1.5.0"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].version").value("1.5.0"));

        // ----------------------- send request with name and version
        restMockMvc.perform(get("/framework")
                        .param("name", "com.etnetera.test")
                        .param("version", "1.5.0"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("com.etnetera.test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].version").value("1.5.0"));

        // ----------------------- send request with deprecationDate greater than
        restMockMvc.perform(get("/framework")
                        .param("deprecationDateGt", LocalDate.now().plusDays(20).toString()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("com.etnetera.test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("com.hlavja.test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].version").value("2.0.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].version").value("2.0.1"));

        // ----------------------- send request with deprecationDate lover than and name
        restMockMvc.perform(get("/framework")
                        .param("name", "com.etnetera.test")
                        .param("deprecationDateLt", LocalDate.now().plusDays(20).toString()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("com.etnetera.test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].version").value("1.0.0"));
    }
}
