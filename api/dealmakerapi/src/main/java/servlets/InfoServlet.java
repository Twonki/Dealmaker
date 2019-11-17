package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.Match;
import data.Repository;


public class InfoServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String requestUrl = request.getRequestURI();
		String acc = requestUrl.substring("/dealmakerapi-0.0.1-SNAPSHOT/info/".length());
		
		Match info = getInfo(acc);

		response.setContentType("application/json");
		response.getOutputStream().println(info.toJSON());
	}
	
	private Match getInfo(String acc) {
		return getInfo(Integer.parseInt(acc));
	}
	
	private Match getInfo(int acc) {
			var m = new Match();
			m.account = acc+"";
			m.firstname = Repository.lookupFirstName(acc);
			m.score = 1.0;
			m.categories = Repository.getCategorySpendings(acc);
			return m;
	}
	
}
	