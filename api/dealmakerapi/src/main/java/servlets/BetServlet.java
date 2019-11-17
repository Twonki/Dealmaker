package servlets;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javatuples.Pair;

import data.Match;
import data.Repository;

public class BetServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String requestUrl = request.getRequestURI();
		String acc = requestUrl.substring("/dealmakerapi-0.0.1-SNAPSHOT/bets/".length());
		int accId = Integer.parseInt(acc);
		
		System.out.println("Acc " + acc + " refreshed his bets");
		
		List<Integer> openBets = Repository.getOpenBetsForAccount(accId);
		
		var foundMatches = Repository.getBetsForAccount(accId).stream()
			.filter(x -> Repository.betFullfilled(x))
			.map(x-> Repository.getBetMatch(x))
			.map(Pair::getValue1)
			.map(ac ->{
				var m = new Match();
				m.account = ac+"";
				m.firstname = Repository.lookupFirstName(ac);
				m.score = 1.0;
				m.categories = Repository.getCategorySpendings(ac);
				return m;
			})
			.map(Match::toJSON)
			.collect(Collectors.toList());
		
		System.out.println("There were "+ foundMatches.size() + " matched bets for "+ acc + " refreshed his bets");
		
		String json = "{\n" ;
		json += "\"open\":[";
		json += openBets.stream().map(x -> "{\"betid\":"+ x+ "}").collect(Collectors.joining(","));
		json += "], \"won\":[";
		json += foundMatches.stream().collect(Collectors.joining(","));		
		json += "]}";

		response.setContentType("application/json");
		response.getOutputStream().println(json);
	}
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String requestUrl = request.getRequestURI();
		String acc = requestUrl.substring("/dealmakerapi-0.0.1-SNAPSHOT/bets/".length());
		
		System.out.println("Acc " + acc + " has posted a bet to " + request.getParameter("betid") );
		
		
		var accId = Integer.parseInt(acc);
		var bet = Integer.parseInt(request.getParameter("betid"));
		var offer =  Double.parseDouble(request.getParameter("offer"));
		var demand = Double.parseDouble(request.getParameter("demand"));
		
		Repository.addBet(bet, accId, offer, demand);
		
		response.setStatus(204);
		
	}

	

}
