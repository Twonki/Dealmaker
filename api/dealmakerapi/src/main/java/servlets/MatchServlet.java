package servlets;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import data.Match;
import data.Repository;

public class MatchServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String requestUrl = request.getRequestURI();
		String acc = requestUrl.substring("/matches/".length());
		
		List<Match> matches = getBestMatches(acc);
		
		String json = "[";
		json += matches.stream().map(x -> x.toJSON()).collect(Collectors.joining(",\n"));
		json += "]";

		response.setContentType("application/json");
		response.getOutputStream().println(json);
	}
	
	private List<Match> getBestMatches(String account){
		int acc = Integer.parseInt(account);
		return Repository.getCategoryClosestNeighboors(acc)
				.entrySet()
				.stream()
				.sorted((a,b)-> Double.compare(a.getValue(), b.getValue()))
				.limit(5)
				.map(e -> {
					var m = new Match();
					m.account = e.getKey()+"";
					m.firstname = Repository.lookupFirstName(e.getKey());
					m.score = e.getValue();
					m.categories = Repository.getCategorySpendings(e.getKey());
					return m;
				})
				.collect(Collectors.toList());
	}
	
}
