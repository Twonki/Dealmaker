package servlets;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import data.Match;

public class MatchServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String requestUrl = request.getRequestURI();
		String acc = requestUrl.substring("/matches/".length());
		
		List<Match> matches = getBestMatches(acc);
		
		String json = "[";
		json += matches.stream().map(x -> x.toJSON()).collect(Collectors.joining(",\n"));
		json += "]";
		
		response.getOutputStream().println(json);
		
	}
	
	private List<Match> getBestMatches(String account){
		
		List<Match> matches = new LinkedList<Match>();
		matches.add(Match.fakeMatch());
		matches.add(Match.fakeMatch());
		matches.add(Match.fakeMatch());
		
		return matches;
	}
	
}
