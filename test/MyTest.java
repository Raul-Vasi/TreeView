import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import HTMLBuilder.*;
import controllers.Application;
import play.mvc.Controller;

public class MyTest extends Controller {

	@Test
	public void _test() {
		Div div = new Div();
		Div div1 = new Div();
		div.setID("test");
		div.setClas("clasTest");
		div.setContent("Test 123");
		System.out.println(div.getHtml());
	}

	@Test
	public void button_test() {

		Button bt = new Button();
		bt.setType("submit");
		bt.setID("bla");
		bt.setContent("push");
		System.out.println(bt.getHtml());
	}

	@Test
	public void jckson_test() {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = null;
		try {
			root = mapper.readTree(new File("/home/raul/test/frl%3A6376984/6376984.json"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// processObject(root);
		System.out.println(root.findValues("@id").get(1));
	}

	private void processObject(JsonNode jon) {
		getMainObject(jon);
		jon = jon.at("/hasPart");
		for (int i = 0; i < jon.size(); i++) {
			createObject(getFirst(jon.get(i)));
			createChildren(getFirst(jon.get(i)));

		}
	}

	private void getMainObject(JsonNode jon) {
		System.out.println("Main " + jon.get("@id"));
		System.out.println(jon.get("contentType"));
		System.out.println(jon.get("accessScheme"));
		System.out.println(jon.get("publishScheme"));
		System.out.println(jon.get("parentPid"));
	}

	private void createObject(JsonNode jon) {
		System.out.println("+++++createObject:+++++++");
		if (jon.has("hasData")) {
			System.out.println("Data: " + jon.get("hasData").get("@id"));
		}
		System.out.println("create: " + jon.get("@id"));
		System.out.println(getContentType(jon));
		System.out.println("parentPid: " + jon.get("parentPid"));
		System.out.println("----- ----- -----");
	}

	private String getContentType(JsonNode jon) {
		String content = jon.get("contentType").toString();
		return "\"contentType\" : " + content;
	}

	private void createChildren(JsonNode jon) {

		if (jon.has("hasPart")) {
			jon = jon.at("/hasPart");
			for (int i = 0; i < jon.size(); i++) {

				createObject(getFirst(jon.get(i)));
				processObject(getFirst(jon.get(i)));
			}
		}

	}

	private JsonNode getFirst(JsonNode c) {
		String key = c.findValue("@id").asText();
		JsonNode curPart = c.get(key);
		return curPart;
	}

	@Test
	public void test_list() {
		Map<String, String> list = new TreeMap<String, String>();

	}
}
