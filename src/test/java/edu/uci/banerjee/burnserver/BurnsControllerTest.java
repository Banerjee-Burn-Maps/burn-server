package edu.uci.banerjee.burnserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BurnsControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void loadBurnDataExists() throws Exception {

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "file.csv",
                MediaType.TEXT_PLAIN_VALUE,
                "Test file".getBytes()
        );


        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart("/load").file(file))
                .andExpect(status().isOk());

    }
}