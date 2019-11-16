package servlets;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DealServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String requestUrl = request.getRequestURI();
		String acc = requestUrl.substring("/deals/".length());
		
		List<String> deals = getClosedDeals(acc);
		
		String json = "[";
		json += deals.stream().map(x -> "{\"acc\":\""+x+"\"}").collect(Collectors.joining(",\n"));
		json += "]";
		
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
		
		addDeal(acc,deal);
		
		response.setStatus(204);
	}
	
	private void addDeal(String acc, String dealAcc) {
		System.out.println("Recieved " + acc + " liked " + dealAcc);
	}
	

}
