package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.javatuples.Pair;
import org.javatuples.Triplet;

public class Repository {
	
	public static String pathToSQLLite = "";
	static Connection conn = null;
	
	public static void connect() {
        try {
            // db parameters
            String url = "jdbc:sqlite:"+pathToSQLLite;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public static String lookupFirstName(int accId) {
		String fn = "Unkown";
		if(conn==null)
			connect();
		try {
			var stmt = conn.prepareStatement("SELECT firstname FROM accounts WHERE accountId=?");
			stmt.setInt(0, accId);
			var res = stmt.executeQuery();
			fn = res.getString("firstname");
			res.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fn;
	}
	
	public static Map<Integer,Double> getCategoryClosestNeighboors(Integer acc){
		var raw = relativeSpendings();
		
		List<Integer> accs = raw.stream().map(x->x.getValue0()).collect(Collectors.toList());
		
		accs.removeIf(x->x==acc);
		Map<String,Double> accSpending = getCategorySpendings(acc);
	
		return accs.stream()
			.map(a -> Pair.with(a,getCategorySpendings(a)))
			.map(ap -> Pair.with(ap.getValue0(), categoryDistance(ap.getValue1(),accSpending)))
			.sorted((a,b) -> Double.compare(a.getValue1(), b.getValue1()))
			.collect(Collectors.toMap(x->x.getValue0(), x->x.getValue1()));
	}
	
	public static Map<String,Double> getCategorySpendings(int acc){
		return relativeSpendings().stream()
			.filter(x -> x.getValue0() == acc)
			.collect(Collectors.toMap(x->x.getValue1(),x-> x.getValue2()));
	}
	
	public static double categoryDistance(Map<String,Double> firstCats, Map<String,Double> secondCats) {
		return firstCats.entrySet().stream()
			.mapToDouble(ent -> Math.abs(ent.getValue() - secondCats.getOrDefault(ent.getKey(), 0.0)))
			.sum()
			+
		secondCats.entrySet().stream()
			.filter(x -> ! firstCats.containsKey(x.getKey()))
			.mapToDouble(x -> x.getValue())
			.sum();
	}

	public static List<Triplet<Integer,String,Double>> relativeSpendings() {
		List<Triplet<Integer,String,Double>> spendings = lookupSpendings();
		Map<Integer,Double> totalspendings = lookupTotalSpendings();
		
		return spendings.stream()
			.map(x->Triplet.with(x.getValue0(), x.getValue1(), x.getValue2()/totalspendings.getOrDefault(x.getValue0(),1.0)))
			.collect(Collectors.toList());
	}
	
	public static Map<Integer,Double> lookupTotalSpendings(){
		Map<Integer,Double> spendings = new HashMap<>();
		if(conn==null)
			connect();
		try {
			var stmt = conn.prepareStatement("SELECT accountId, totalamount FROM totalspending");
			var res = stmt.executeQuery();
			do {
				var acc = res.getInt("accountId");
				var amt = res.getDouble("totalamount");
				spendings.put(acc, amt);
			}
			while(res.next());
			res.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return spendings;
	}
	
	public static List<Triplet<Integer,String,Double>> lookupSpendings() {
		List<Triplet<Integer,String,Double>> spendings = new ArrayList<>();
		if(conn==null)
			connect();
		try {
			var stmt = conn.prepareStatement("SELECT accountId, category,categoryamount FROM categoryspending");
			var res = stmt.executeQuery();
			do {
				var acc = res.getInt("accountId");
				var cat = res.getString("category");
				var amt = res.getDouble("categoryamount");
				spendings.add(Triplet.with(acc,cat, amt));
			}
			while(res.next());
			res.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return spendings;
	}
	
	
}
