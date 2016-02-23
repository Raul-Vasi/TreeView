package controllers;

import java.io.File;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.mvc.Controller;
import play.mvc.Result;
import views.ProcessObj;

public class Application extends Controller {

	public Result index() {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = null;
		ProcessObj po = new ProcessObj();
		try {
			root = mapper.readTree(new File("/home/raul/test/frl%3A6376984/6376984.json"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		response().setContentType("text/html");
		return ok(views.html.index.render(po.startTree(root)));
	}

}
