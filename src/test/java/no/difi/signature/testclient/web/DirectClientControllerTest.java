package no.difi.signature.testclient.web;

import com.jayway.jsonpath.JsonPath;
import no.difi.signature.testclient.configuration.SignatureClientConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Base64;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(properties = {
        "signatureService.clientKeyStoreResource=/avsender.jks",
        "signatureService.clientKeyStorePassword=changeit",
        "signatureService.privateKeyPassword=changeit",
        "signatureService.privateKeyAlias=difi",
        "signatureService.url=https://api.difitest.signering.posten.no/api",
//      "signatureService.url=https://api.difiqa.signering.posten.no/api",
        "signatureService.senderOrganizationNumber=991825827"
})
@ContextConfiguration(classes = SignatureClientConfiguration.class)
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class DirectClientControllerTest {

    @Autowired
    private WebApplicationContext springContext;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(springContext).build();
    }

    @Test
    @Ignore
    public void testCreateJob() throws Exception {
        String jsonResponse = mockMvc.perform(post("/jobs/21106744421"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.signatureJobId", is(notNullValue())))
                .andExpect(jsonPath("$.redirectUrl", is(startsWith("https://difitest.signering.posten.no/redirect/#/"))))
                .andExpect(jsonPath("$.statusUrl.statusUrl", is(startsWith("https://api.difitest.signering.posten.no/api/signature-jobs/"))))
                .andReturn().getResponse().getContentAsString();
        String statusUrl = JsonPath.compile("$.statusUrl.statusUrl").read(jsonResponse);
        Integer jobId = JsonPath.compile("$.signatureJobId").read(jsonResponse);
        String statusUrlB64Encoded = Base64.getUrlEncoder().encodeToString(statusUrl.getBytes("UTF-8"));
        mockMvc.perform(get("/jobs/status/{statusUrl}", statusUrlB64Encoded))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.signatureJobId", is(equalTo(jobId))))
                .andExpect(jsonPath("$.status", is(equalTo("CREATED"))))
                .andReturn().getResponse().getContentAsString();
    }



}
