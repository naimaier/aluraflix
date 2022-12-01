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
class CategoriaControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	void shouldReturnFourResultsWhenSearchingAllCategories() throws Exception {
		
		URI uri = new URI("/categorias");
	
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.get(uri))
		.andExpect(MockMvcResultMatchers
				.status()
				.isOk())
		.andReturn();
		
		String jsonResult = result.getResponse().getContentAsString();
		
		JSONAssert.assertEquals("{totalElements:4}", jsonResult, JSONCompareMode.LENIENT);
	}
	
	
	@Test
	void shouldReturnCorrectCategoryWhenSearchingById() throws Exception {
		
		URI uri = new URI("/categorias/2");
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.get(uri))
		.andExpect(MockMvcResultMatchers
				.status()
				.isOk())
		.andReturn();
		
		String jsonResult = result.getResponse().getContentAsString();
		
		JSONAssert.assertEquals("{titulo:C}", jsonResult, JSONCompareMode.LENIENT);
	}
	
	
	@Test
	void shouldReturnLivreCategoryWhenSearchingForId1() throws Exception {
		
		URI uri = new URI("/categorias/1");
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.get(uri))
		.andExpect(MockMvcResultMatchers
				.status()
				.isOk())
		.andReturn();
		
		String jsonResult = result.getResponse().getContentAsString();
		
		JSONAssert.assertEquals("{titulo:Livre}", jsonResult, JSONCompareMode.LENIENT);
	}

	
	@Test
	void shouldReturnNotFoundWhenSearchingNonexistentId() throws Exception {
		
		URI uri = new URI("/categorias/8");
		
		mockMvc.perform(MockMvcRequestBuilders
				.get(uri))
		.andExpect(MockMvcResultMatchers
				.status()
				.isNotFound());
	}
	
	
	@Test
	void shouldNReturnBadRequestWhenCreatingACategoryMissingFields() throws Exception {
		
		URI uri = new URI("/categorias");
		
		String jsonInput = "{\"cor\":\"000\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(jsonInput)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.isBadRequest());
	}
	
	
	@Test
	void shouldReturnCreatedWhenCreatingAValidCategory() throws Exception {
		
		URI uri = new URI("/categorias");
		
		String jsonInput = "{\"titulo\":\"Nova categoria\",\"cor\":\"000\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(jsonInput)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.isCreated());
	}
	
	
	@Test
	void shouldReturnNotFoundWhenTryingToUpdateANonxistentCategory() throws Exception {
		
		URI uri = new URI("/categorias/8");
		
		String jsonInput = "{\"titulo\":\"Nova categoria\",\"cor\":\"000\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.put(uri)
				.content(jsonInput)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.isNotFound());
	}
	
	
	@Test
	void shouldReturnBadRequestWhenUpdatingACategoryMissingFields() throws Exception {
		
		URI uri = new URI("/categorias/2");
		
		String jsonInput = "{\"cor\":\"000\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.put(uri)
				.content(jsonInput)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.isBadRequest());
	}
	
	
	@Test
	void shouldUpdateAValidCategory() throws Exception {
		
		URI uri = new URI("/categorias/2");
		
		String jsonInput = "{\"titulo\":\"Nova categoria\",\"cor\":\"000\"}";
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.put(uri)
				.content(jsonInput)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.isOk())
		.andReturn();
		
		String jsonResult = result.getResponse().getContentAsString();
		
		String jsonExpected = "{id:2, titulo:'Nova categoria',cor:'000'}";
		
		JSONAssert.assertEquals(jsonExpected, jsonResult, JSONCompareMode.LENIENT);
	}
	
	
	@Test
	void shouldReturnNotFoundWhenTryingToDeleteANonexistentCategory() throws Exception {
		
		URI uri = new URI("/categorias/8");
		
		mockMvc.perform(MockMvcRequestBuilders
				.delete(uri))
		.andExpect(MockMvcResultMatchers
				.status()
				.isNotFound());
	}
	
	
	@Test
	void shouldReturnBadRequestWhenDeletingACategoryWithVideos() throws Exception {
		
		URI uri = new URI("/categorias/2");
		
		mockMvc.perform(MockMvcRequestBuilders
				.delete(uri))
		.andExpect(MockMvcResultMatchers
				.status()
				.isBadRequest());
	}
	
	
	@Test
	void shouldReturnNoContentWhenDeletingACategoryWithNoVideos() throws Exception {
		
		URI uri = new URI("/categorias/4");
		
		mockMvc.perform(MockMvcRequestBuilders
				.delete(uri))
		.andExpect(MockMvcResultMatchers
				.status()
				.isNoContent());
	}
	
	
	@Test
	void shouldReturnNotFoundWhenSearchingVideosByANonexistentCategory() throws Exception {
		
		URI uri = new URI("/categorias/8/videos");
		
		mockMvc.perform(MockMvcRequestBuilders
				.get(uri))
		.andExpect(MockMvcResultMatchers
				.status()
				.isNotFound());
	}
	
	
	@Test
	void shouldReturn0ResultsWhenSearchingVideosByCategoryWithNoVideos() throws Exception {
		
		URI uri = new URI("/categorias/4/videos");
		
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
	void shouldReturn2ResultsWhenSearchingVideosByCategory() throws Exception {
		
		URI uri = new URI("/categorias/2/videos");
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.get(uri))
		.andExpect(MockMvcResultMatchers
				.status()
				.isOk())
		.andReturn();
		
		String jsonResult = result.getResponse().getContentAsString();
		
		JSONAssert.assertEquals("{totalElements:2}", jsonResult, JSONCompareMode.LENIENT);
	}
}
