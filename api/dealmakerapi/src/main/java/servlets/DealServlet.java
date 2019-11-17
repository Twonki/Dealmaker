package servlets;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.Repository;

public class DealServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String requestUrl = request.getRequestURI();
		String acc = requestUrl.substring("/deals/".length());
		var accId  =Integer.parseInt(acc);
		
		List<String> deals = getClosedDeals(acc);
		
		List<Integer> likes = Repository.getLikes(accId);
		List<Integer> liked = Repository.getLiked(accId);
		var hits = likes.stream().filter(x -> liked.contains(x)).collect(Collectors.toList());
		
		Repository.openBets(accId,hits);
		Repository.closeDeals(accId,hits);
		
		String json = "[";
		json += hits.stream().map(x -> "{\"acc\":\""+x+"\"}").collect(Collectors.joining(",\n"));
		json += "]";

		response.setContentType("application/json");
		response.getOutputStream().println(json);
		
	}
	
	private List<String> getClosedDeals(String account){
		List<String> deals = new LinkedList<String>();
		
		deals.add(Math.round(Math.random()*100)+"");
		deals.add(Math.round(Math.random()*100)+"");
		deals.add(Math.round(Math.random()*100)+"");
		
		return deals;
	}
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String requestUrl = request.getRequestURI();
		String acc = requestUrl.substring("/deals/".length());
		
		String deal = request.getParameter("acc");
		
		if(request.getParameter("like").toLowerCase().equals("true") || request.getParameter("Like").toLowerCase().equals("true"))
			addDeal(acc,deal,true);
		else 
			addDeal(acc,deal,false);
		
		response.setStatus(204);
	}
	
	private void addDeal(String acc, String dealAcc, boolean liked) {
		System.out.println("Recieved " + acc + " liked " + dealAcc + " " + liked);
	}
	

}
