package com.centric.productstore.functional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
// to cleanup test database before each test
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ApplicationFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String NAME = "Pilote";
    private static final String DESCRIPTION = "Rayban Pilote";
    private static final String BRAND = "Rayban";
    private static final String CATEGORY = "glasses";
    private static final String TAG_1 = "glasses";


    private static final String NAME_2 = "Aviator";


    @Test
    public void addProduct() throws Exception {

        String newProduct = "{\n" +
            "  \"brand\": \"" + BRAND + "\",\n" +
            "  \"category\": \"" + CATEGORY + "\",\n" +
            "  \"description\": \"" + DESCRIPTION + "\",\n" +
            "  \"name\": \"" + NAME + "\",\n" +
            "  \"tags\": [\n" +
            "    \"" + TAG_1 + "\"\n" +
            "  ]\n" +
            "}";

        mockMvc
            .perform(
                post("/products")
                    .content(newProduct)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(NAME))
            .andExpect(jsonPath("$.brand").value(BRAND))
            .andExpect(jsonPath("$.description").value(DESCRIPTION))
            .andExpect(jsonPath("$.category").value(CATEGORY))
            .andExpect(jsonPath("$.tags[0]").value(TAG_1))
        ;
    }

    @Test
    public void addProductAndFindThem() throws Exception {

        String newProduct1 = "{\n" +
            "  \"brand\": \"" + BRAND + "\",\n" +
            "  \"category\": \"" + CATEGORY + "\",\n" +
            "  \"description\": \"" + DESCRIPTION + "\",\n" +
            "  \"name\": \"" + NAME + "\",\n" +
            "  \"tags\": [\n" +
            "    \"" + TAG_1 + "\"\n" +
            "  ]\n" +
            "}";

        String newProduct2 = "{\n" +
            "  \"brand\": \"" + BRAND + "\",\n" +
            "  \"category\": \"" + CATEGORY + "\",\n" +
            "  \"description\": \"" + DESCRIPTION + "\",\n" +
            "  \"name\": \"" + NAME_2 + "\",\n" +
            "  \"tags\": [\n" +
            "    \"" + TAG_1 + "\"\n" +
            "  ]\n" +
            "}";

        mockMvc
            .perform(
                post("/products")
                    .content(newProduct1)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(NAME))
            .andExpect(jsonPath("$.brand").value(BRAND))
            .andExpect(jsonPath("$.description").value(DESCRIPTION))
            .andExpect(jsonPath("$.category").value(CATEGORY))
            .andExpect(jsonPath("$.tags[0]").value(TAG_1))
        ;

        mockMvc
            .perform(
                post("/products")
                    .content(newProduct2)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(NAME_2))
            .andExpect(jsonPath("$.brand").value(BRAND))
            .andExpect(jsonPath("$.description").value(DESCRIPTION))
            .andExpect(jsonPath("$.category").value(CATEGORY))
            .andExpect(jsonPath("$.tags[0]").value(TAG_1))
        ;

        mockMvc
            .perform(
                get("/products")
                    .param("category", CATEGORY)
                    .param("limit", "3")
                    .param("page", "0")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.currentPage").value(0))
            .andExpect(jsonPath("$.totalPages").value(1))
            .andExpect(jsonPath("$.totalElements").value(2))

            // most recent first
            .andExpect(jsonPath("$.products[0].name").value(NAME_2))
            .andExpect(jsonPath("$.products[0].brand").value(BRAND))
            .andExpect(jsonPath("$.products[0].description").value(DESCRIPTION))
            .andExpect(jsonPath("$.products[0].category").value(CATEGORY))
            .andExpect(jsonPath("$.products[0].tags[0]").value(TAG_1))

            .andExpect(jsonPath("$.products[1].name").value(NAME))
            .andExpect(jsonPath("$.products[1].brand").value(BRAND))
            .andExpect(jsonPath("$.products[1].description").value(DESCRIPTION))
            .andExpect(jsonPath("$.products[1].category").value(CATEGORY))
            .andExpect(jsonPath("$.products[1].tags[0]").value(TAG_1))
        ;
    }


    @Test
    public void onePerPage() throws Exception {

        String newProduct1 = "{\n" +
            "  \"brand\": \"" + BRAND + "\",\n" +
            "  \"category\": \"" + CATEGORY + "\",\n" +
            "  \"description\": \"" + DESCRIPTION + "\",\n" +
            "  \"name\": \"" + NAME + "\",\n" +
            "  \"tags\": [\n" +
            "    \"" + TAG_1 + "\"\n" +
            "  ]\n" +
            "}";

        String newProduct2 = "{\n" +
            "  \"brand\": \"" + BRAND + "\",\n" +
            "  \"category\": \"" + CATEGORY + "\",\n" +
            "  \"description\": \"" + DESCRIPTION + "\",\n" +
            "  \"name\": \"" + NAME_2 + "\",\n" +
            "  \"tags\": [\n" +
            "    \"" + TAG_1 + "\"\n" +
            "  ]\n" +
            "}";

        mockMvc
            .perform(
                post("/products")
                    .content(newProduct1)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(NAME))
            .andExpect(jsonPath("$.brand").value(BRAND))
            .andExpect(jsonPath("$.description").value(DESCRIPTION))
            .andExpect(jsonPath("$.category").value(CATEGORY))
            .andExpect(jsonPath("$.tags[0]").value(TAG_1))
        ;

        mockMvc
            .perform(
                post("/products")
                    .content(newProduct2)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(NAME_2))
            .andExpect(jsonPath("$.brand").value(BRAND))
            .andExpect(jsonPath("$.description").value(DESCRIPTION))
            .andExpect(jsonPath("$.category").value(CATEGORY))
            .andExpect(jsonPath("$.tags[0]").value(TAG_1))
        ;

        mockMvc
            .perform(
                get("/products")
                    .param("category", CATEGORY)
                    .param("limit", "1")
                    .param("page", "0")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.currentPage").value(0))
            .andExpect(jsonPath("$.totalPages").value(2))
            .andExpect(jsonPath("$.totalElements").value(2))

            .andExpect(jsonPath("$.products[0].name").value(NAME_2))
            .andExpect(jsonPath("$.products[0].brand").value(BRAND))
            .andExpect(jsonPath("$.products[0].description").value(DESCRIPTION))
            .andExpect(jsonPath("$.products[0].category").value(CATEGORY))
            .andExpect(jsonPath("$.products[0].tags[0]").value(TAG_1))
        ;

        mockMvc
            .perform(
                get("/products")
                    .param("category", CATEGORY)
                    .param("limit", "1")
                    .param("page", "1")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.currentPage").value(1))
            .andExpect(jsonPath("$.totalPages").value(2))
            .andExpect(jsonPath("$.totalElements").value(2))

            .andExpect(jsonPath("$.products[0].name").value(NAME))
            .andExpect(jsonPath("$.products[0].brand").value(BRAND))
            .andExpect(jsonPath("$.products[0].description").value(DESCRIPTION))
            .andExpect(jsonPath("$.products[0].category").value(CATEGORY))
            .andExpect(jsonPath("$.products[0].tags[0]").value(TAG_1))
        ;
    }


    @Test
    public void emptyCategory() throws Exception {
        mockMvc
            .perform(
                get("/products")
                    .param("category", "fake category")
                    .param("limit", "3")
                    .param("page", "0")

                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.currentPage").value(0))
            .andExpect(jsonPath("$.totalPages").value(0))
            .andExpect(jsonPath("$.totalElements").value(0))
            .andExpect(jsonPath("$.products").isEmpty())
        ;

    }


    // helper method, would move to a Util class in a real project
    public static <T> Object convertJSONStringToObject(String json, Class<T> objectClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);
        return mapper.readValue(json, objectClass);
    }

}