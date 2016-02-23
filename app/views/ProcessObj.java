package views;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.fasterxml.jackson.databind.JsonNode;

public class ProcessObj {

	StringBuilder sb = new StringBuilder();

	public String startTree(JsonNode root) {

		sb.append("<ul id=\"root_tree\">");
		sb.append("<li>" + root.get("@id"));
		printTree(root);
		sb.append("</li>");
		sb.append("</ul>");
		Document doc = Jsoup.parseBodyFragment(sb.toString());
		return doc.html().toString();

	}

	private void printTree(JsonNode actualObject) {
		sb.append("<ul>");
		printChildren(actualObject);
		sb.append("</ul>");
	}

	private void printChildren(JsonNode root) {
		JsonNode hasPart = root.at("/hasPart");
		if (hasPart.size() > 0) {
			for (int i = 0; i < hasPart.size(); i++) {
				JsonNode actualObject = getFirst(hasPart.get(i));
				if (isLeaf(actualObject)) {
					processLeaf(actualObject);
				} else {
					sb.append("<li>" + actualObject.get("@id"));
					printTree(actualObject);
					sb.append("</li>");
				}
			}
		} else {
			sb.append(root.get("@id"));
		}
	}

	private boolean isLeaf(JsonNode actualObject) {
		return !actualObject.has("hasPart");
	}

	private void processLeaf(JsonNode actualObject) {
		sb.append("<li>");
		printChildren(actualObject);
		sb.append("</li>");
	}

	private JsonNode getFirst(JsonNode c) {
		String key = c.findValue("@id").asText();
		JsonNode curPart = c.get(key);
		return curPart;
	}
}
