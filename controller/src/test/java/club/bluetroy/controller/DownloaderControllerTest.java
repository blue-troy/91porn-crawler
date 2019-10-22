package club.bluetroy.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author heyixin
 * Date: 2019-01-21
 * Time: 18:52
 */
@Disabled
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DownloaderControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getPath() throws Exception {
        mvc.perform(get("/download/path"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/native/dirChooser"));
    }
}