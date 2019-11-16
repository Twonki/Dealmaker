package data;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Match {
	
	public String account;
	public String firstname;
	public Double score;
	public Map<String,Double> categories = new HashMap<>();
	
	public String toJSON() {
		String json = "{\n";
		json += "\"account\":\""+account+"\",\n";
		json += "\"firstname\":\""+firstname+"\",\n";
		json += "\"score\":"+score+",\n";
		json += "\"category\":[\n";
		json += 
			categories.entrySet().stream()
			.map(e -> "{\t\"name\":\""+e.getKey()+"\",\n\t\"percent\":" + e.getValue()+"\n}")
			.collect( Collectors.joining( ",\n" ) );
		
		json += "] \n }";
		return json;
	}
	
	public static Match fakeMatch() {
		Match m = new Match();
		
		m.account = Math.round(Math.random()*1000)+"";
		m.firstname="Bert"+m.account;
		m.score =  Math.random();
		
		m.categories.put("Food", Math.random());
		m.categories.put("Other",Math.random());
		
		return m;
	}
	
	
}
