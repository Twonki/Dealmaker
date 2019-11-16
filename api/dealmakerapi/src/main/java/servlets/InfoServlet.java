package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.Match;


public class InfoServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String requestUrl = request.getRequestURI();
		String acc = requestUrl.substring("/info/".length());
		
		Match info = getInfo(acc);
		
		response.getOutputStream().println(info.toJSON());
		
	}
	
	private Match getInfo(String acc) {
		Match m = Match.fakeMatch();
		m.account=acc;
		m.score=1.0d;
		return m;
	}
}
	