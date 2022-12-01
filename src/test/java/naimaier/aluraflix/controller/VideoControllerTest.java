package naimaier.aluraflix.controller;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql({"/test-data.sql"})
class VideoControllerTest {

	
	@Autowired
	MockMvc mockMvc;
	
	
	@Test
	void shouldReturnThreeElementsWhenSearchingAllVideos() throws Exception {
		
		URI uri = new URI("/videos");
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.get(uri))
		.andExpect(MockMvcResultMatchers
				.status()
				.isOk())
		.andReturn();
		
		String jsonResult = result.getResponse().getContentAsString();
		
		JSONAssert.assertEquals("{totalElements:3}", jsonResult, JSONCompareMode.LENIENT);
	}
	
	
	@Test
	void shouldReturnTwoElementsWhenSearchingAula() throws Exception {
		
		URI uri = new URI("/videos?search=aula");
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.get(uri))
		.andExpect(MockMvcResultMatchers
				.status()
				.isOk())
		.andReturn();
		
		String jsonResult = result.getResponse().getContentAsString();
		
		JSONAssert.assertEquals("{totalElements:2}", jsonResult, JSONCompareMode.LENIENT);
	}
	
	
	@Test
	void shouldReturnNoElementsWhenSearchingNonexistentTitle() throws Exception {
		
		URI uri = new URI("/videos?search=carro");
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.get(uri))
		.andExpect(MockMvcResultMatchers
				.status()
				.isOk())
		.andReturn();
		
		String jsonResult = result.getResponse().getContentAsString();
		
		JSONAssert.assertEquals("{totalElements:0}", jsonResult, JSONCompareMode.LENIENT);
	}
	
	
	@Test
	void shouldReturnCorrectVideoWhenSearchingById() throws Exception {
		
		URI uri = new URI("/videos/2");
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.get(uri))
		.andExpect(MockMvcResultMatchers
				.status()
				.isOk())
		.andReturn();
		
		String jsonResult = result.getResponse().getContentAsString();
		
		JSONAssert.assertEquals("{titulo:\"Aula Java\"}", jsonResult, JSONCompareMode.LENIENT);
	}
	
	
	@Test
	void shouldReturnNotFoundWhenSearchingForNonexistentId() throws Exception {
		
		URI uri = new URI("/videos/8");
		
		mockMvc.perform(MockMvcRequestBuilders
				.get(uri))
		.andExpect(MockMvcResultMatchers
				.status()
				.isNotFound());
	}
	
	
	@Test
	void shouldReturnCreatedWhenCreatingAValidVideo() throws Exception {
		
		URI uri = new URI("/videos");
		
		String jsonInput = "{\"titulo\":\"Nova aula\",\"descricao\":\"Aula\",\"url\":\"http://www.novaaula.com\",\"categoriaId\":\"2\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(jsonInput)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.isCreated());
	}
	
	
	@Test
	void shouldReturnBadRequestWhenCreatingAVideoMissingFields() throws Exception {
		
		URI uri = new URI("/videos");
		
		String jsonInput = "{\"descricao\":\"Aula\",\"url\":\"http://www.novaaula.com\",\"categoriaId\":\"2\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(jsonInput)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.isBadRequest());
	}
	
	
	@Test
	void shouldAssignCategory1WhenCreatingAVideoWithoutCategory() throws Exception {
		
		URI uri = new URI("/videos");
		
		String jsonInput = "{\"titulo\":\"Nova aula\",\"descricao\":\"Aula\",\"url\":\"http://www.novaaula.com\"}";
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(jsonInput)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.isCreated())
		.andReturn();
		
		String jsonResult = result.getResponse().getContentAsString();
		
		JSONAssert.assertEquals("{categoriaId:1}", jsonResult, JSONCompareMode.LENIENT);
	}
	
	
	@Test
	void shouldReturnNotFoundWhenUpdatingANonexistentVideo() throws Exception {
		
		URI uri = new URI("/videos/8");
		
		String jsonInput = "{\"titulo\":\"Nova aula\",\"descricao\":\"Aula\",\"url\":\"http://www.novaaula.com\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.put(uri)
				.content(jsonInput)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.isNotFound());
	}
	
	
	@Test
	void shouldReturnBadRequestWhenUpdatingAVideoWithMissingFields() throws Exception {
		URI uri = new URI("/videos/2");
		
		String jsonInput = "{\"titulo\":\"Nova aula\",\"url\":\"http://www.novaaula.com\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.put(uri)
				.content(jsonInput)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.isBadRequest());
	}
	
	
	@Test
	void shouldUpdateAValidVideo() throws Exception {
		URI uri = new URI("/videos/2");
		
		String jsonInput = "{\"titulo\":\"Nova aula\",\"descricao\":\"Aula\",\"url\":\"http://www.novaaula.com\"}";
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.put(uri)
				.content(jsonInput)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.isOk())
		.andReturn();
		
		String jsonResult = result.getResponse().getContentAsString();
		
		String jsonExpected = "{id:2,titulo:'Nova aula',descricao:'Aula',url:'http://www.novaaula.com'}";
		
		JSONAssert.assertEquals(jsonExpected, jsonResult, JSONCompareMode.LENIENT);
	}
	
	
	@Test
	void shouldReturnNotFoundWhenTryingToDeleteANonexistentVideo() throws Exception {
		
		URI uri = new URI("/videos/8");
		
		mockMvc.perform(MockMvcRequestBuilders
				.get(uri))
		.andExpect(MockMvcResultMatchers
				.status()
				.isNotFound());
	}
	
	
	@Test
	void shouldReturnNoContentWhenDeletingAValidVideo() throws Exception {
		
		URI uri = new URI("/videos/2");
		
		mockMvc.perform(MockMvcRequestBuilders
				.delete(uri))
		.andExpect(MockMvcResultMatchers
				.status()
				.isNoContent());
	}
}
