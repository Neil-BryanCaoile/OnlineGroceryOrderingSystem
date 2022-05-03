package ca.sheridancollege.caoilen;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import ca.sheridancollege.caoilen.beans.Item;
import ca.sheridancollege.caoilen.security.DatabaseAccess;

@SpringBootTest
@AutoConfigureMockMvc
class Week9Lecture2SpringSecurity2ApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private DatabaseAccess ds;
	@Test
	public void testLoadingIndexPage() throws Exception{
		//Test going to the root directory will take us to index.html
		this.mockMvc.perform(get("/"))//get local root
		.andExpect(model().attributeExists("itemsLists"))
		.andExpect(status().isOk())
		.andExpect(view().name("index.html"));
   }
	
	@Test
	public void InitialGetAllItems() throws Exception{
		List<Item> items = ds.getItemAllItemList();
		int initialSize = items.size();
		assertThat(initialSize).isEqualTo(9);
   }
	
}
