package ch.erebetez.marshall;

import java.util.Map;

public class Assay {
	String id;
	String token;
	Map<String, String> results;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Map<String, String> getResults() {
		return results;
	}

	public void setResults(Map<String, String> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "Assay [id=" + id + ", token=" + token + ", results=" + results
				+ "]";
	}

}
