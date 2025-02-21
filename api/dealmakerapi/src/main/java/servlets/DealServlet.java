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
		String acc = requestUrl.substring("/dealmakerapi-0.0.1-SNAPSHOT/deals/".length());
		var accId  =Integer.parseInt(acc);
		
		System.out.println("Acc " + acc + " refreshed his deals");
		
		List<Integer> likes = Repository.getLikes(accId);
		List<Integer> liked = Repository.getLiked(accId);
		var hits = likes.stream().filter(x -> liked.contains(x)).collect(Collectors.toSet()).stream().collect(Collectors.toList());
		
		Repository.openBets(accId,hits);
		Repository.closeDeals(accId,hits);
		
		String json = "[";
		json += hits.stream().map(x->x+"").collect(Collectors.joining(","));
		json += "]";

		System.out.println("Acc " + acc + " had " + likes.size() + " likes and liked " + liked.size());
		System.out.println("Acc " + acc + " had " + hits.size() + " matching deals");
		
		
		response.setContentType("application/json");
		response.getOutputStream().println(json);
		
	}
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String requestUrl = request.getRequestURI();
		String acc = requestUrl.substring("/dealmakerapi-0.0.1-SNAPSHOT/deals/".length());
		
		String deal = request.getParameter("acc");
		if(Boolean.parseBoolean(request.getParameter("like")))
		{
			System.out.println("acc" + acc + " swiped like on " + deal);
			Repository.addDeal(acc,deal,true);
		}
		else {
			Repository.addDeal(acc,deal,true);
			System.out.println("acc" + acc + " swiped no like on " + deal);
		}
		
		response.setStatus(204);
	}
	
	

}
