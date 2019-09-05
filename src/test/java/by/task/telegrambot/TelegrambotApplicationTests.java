package by.task.telegrambot;

import by.task.telegrambot.model.City;
import by.task.telegrambot.repository.CityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import javax.annotation.PostConstruct;

import java.util.List;

import static by.task.telegrambot.CityData.*;
import static by.task.telegrambot.TestUtil.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
@Sql(scripts = {"classpath:initDB.sql","classpath:populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
public class TelegrambotApplicationTests {

	private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();
	private static final String REST_URL="/cities";

	static {
		CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
		CHARACTER_ENCODING_FILTER.setForceEncoding(true);
	}

	private MockMvc mockMvc;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@PostConstruct
	private void postConstruct() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.addFilter(CHARACTER_ENCODING_FILTER)
				.build();
	}


	@Test
	public void getCities() throws Exception {
		mockMvc.perform(get(REST_URL))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(CityData.contentJson(CityData.CITY1,CityData.CITY2));
	}

	@Test
	public void getCity() throws Exception {
		mockMvc.perform(get(REST_URL+"/"+100000))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(CityData.contentJson(CityData.CITY1));
	}

	@Test
	public void createCity()throws Exception{
		City created=new City("London","You can visit the Tower of London");
		ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.writeValue(created)));

		City returned = readFromJson(action,City.class);
		created.setId(returned.getId());

		assertMatch(returned, created);
		assertMatch(cityRepository.findAll(), List.of( CITY1,CITY2,created));
	}

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void createConflictCity()throws Exception{
        City created=new City("Минск","You can visit the Tower of London");
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isConflict());
    }

	@Test
	public void updateCity()throws Exception{
		City updated = new City(CITY_UPDATE.getCity(),CITY_UPDATE.getInfo());

		mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/"+100000)
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.writeValue(updated)))
				.andExpect(status().isNoContent());

		assertMatch(cityRepository.findAll(),List.of(CITY2,CITY_UPDATE));
	}

	@Test
	public void deleteCity()throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL  +"/"+100000))
				.andExpect(status().isNoContent());
		assertMatch(cityRepository.findAll(), List.of(CITY2));
	}

	@Test
	public void deleteNotFoundCity()throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL +"/"+ 1))
				.andExpect(status().isUnprocessableEntity());
	}
}
