package fr.sg.bankaccount.ws;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import fr.sg.bankaccount.model.TransferHistory;

public class TransferHistoryRessourceTest extends AbstractTest {
	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void transferHistoryByAccountNumber() throws Exception {
		String uri = "/transferHistoryByAccountNumber?accountNumber=1";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		TransferHistory[] productlist = super.mapFromJson(content,
				TransferHistory[].class);
		assertEquals(productlist.length, 0);
	}

}
