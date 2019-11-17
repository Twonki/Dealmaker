package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.javatuples.Pair;
import org.javatuples.Triplet;

public class Repository {
	
	public static String pathToSQLLite = "";
	static Connection conn = null;
	
	public static void connect() {
        try {
            String url = "jdbc:sqlite:"+pathToSQLLite;
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
	
	public static Optional<String> mostSharedCategory(Map<String,Double> a, Map<String,Double> b) {
		var u = a.keySet();
		u.addAll(b.keySet());
		return u.stream()
			.map(k -> Pair.with(k, Math.abs(a.getOrDefault(k,0.0)-b.getOrDefault(k,0.0))))
			.sorted((t,z)-> Double.compare(t.getValue1(), z.getValue1()))
			.limit(1)
			.map(Pair::getValue0)
			.findFirst();
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
			e.printStackTrace();
		}
		return spendings;
	}
	

	public static void addBet(int betid,int acc, double offer, double demand) {
		if(conn==null)
			connect();
		try {
			var stmt = conn.prepareStatement("UPDATE bets SET offer = ?,demand = ? WHERE accountId=? AND betId=?");
			stmt.setDouble(0, offer);
			stmt.setDouble(1, demand);
			stmt.setInt(acc, betid);
			var res = stmt.executeQuery();
			res.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean betFullfilled(int betid){
		if(conn==null)
			connect();
		try {
			var stmt = conn.prepareStatement("SELECT offer,demand FROM bets where betid=? ");
			stmt.setInt(0, betid);
			var res = stmt.executeQuery();
			var offerA = res.getDouble("offer");
			var demandA = res.getDouble("demand");
			res.next();
			var offerB = res.getDouble("offer");
			var demandB = res.getDouble("demand");
			
			res.close();
			conn.close();
			
			return offerA>demandB && offerB>demandA;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public static Pair<Integer,Integer> getBetMatch(int betid){
		if(conn==null)
			connect();
		try {
			var stmt = conn.prepareStatement("SELECT accountId FROM bets where betId =?");
			stmt.setInt(0, betid);
			
			var res = stmt.executeQuery();
			var accA = res.getInt("accountId");
			res.next();
			var accB = res.getInt("accountId");
			
			res.close();
			conn.close();
			
			return Pair.with(accA, accB);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Pair.with(0,0);
	}
	
	public static List<Integer> getLikes(int accountId){
		List<Integer> results = new ArrayList();
		if(conn==null)
			connect();
		try {
			var stmt = conn.prepareStatement("SELECT dealer FROM deals where dealed =?");
			stmt.setInt(0, accountId);
			
			var res = stmt.executeQuery();
			do {
				results.add(res.getInt("dealer"));
			} while (res.next());			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}

	public static List<Integer> getLiked(int accountId){
		List<Integer> results = new ArrayList();
		if(conn==null)
			connect();
		try {
			var stmt = conn.prepareStatement("SELECT dealed FROM deals where dealer =?");
			stmt.setInt(0, accountId);
			
			var res = stmt.executeQuery();
			do {
				results.add(res.getInt("dealed"));
			} while (res.next());			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return results;
	}

	public static void openBets(int accId, List<Integer> hits) {
		if(conn==null)
			connect();
		try {
			for(int b : hits) {
				if(hasBet(b,accId)) {
					// do nuffing
				}
				else {
					var stmt = conn.prepareStatement(
							"INSERT INTO bets (accountId) VALUES (?),(?)");
					stmt.setInt(0,accId);
					stmt.setInt(1, b);
					var res = stmt.execute();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static void closeDeals(int accId, List<Integer> hits) {
		if(conn==null)
			connect();
		try {
			for(int b : hits) {
				if(hasBet(b,accId)) {
					// do nuffing
				}
				else {
					var stmt = conn.prepareStatement(
							"UPDATE deals SET liked=False WHERE dealer=? AND dealed=?");
					stmt.setInt(0,accId);
					stmt.setInt(1, b);
					var res = stmt.execute();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean hasBet(int accA, int accB) {
		if(conn==null)
			connect();
		try {
			var stmt = conn.prepareStatement(
							"SELECT * FROM pairs WHERE accountA = ? AND accountB = ?");
			stmt.setInt(0,accA);
			stmt.setInt(1,accB);
			var res = stmt.executeQuery();
			if(res.next())
				return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	public static List<Integer> getBetsForAccount(int acc){
		List<Integer> betids = new ArrayList<>();
		if(conn==null)
			connect();
		try {
			var stmt = conn.prepareStatement("SELECT betid FROM bets where accountId =?");
			stmt.setInt(0, acc);
			
			var res = stmt.executeQuery();
			do {
				betids.add(res.getInt("betid"));
			} while (res.next());			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return betids;
	}
	
	public static List<Integer> getOpenBetsForAccount(int acc){
		List<Integer> betids = new ArrayList<>();
		if(conn==null)
			connect();
		try {
			var stmt = conn.prepareStatement("SELECT betid FROM bets where accountId =? AND offer IS NULL OR demand IS NULL");
			stmt.setInt(0, acc);
			
			var res = stmt.executeQuery();
			do {
				betids.add(res.getInt("betid"));
			} while (res.next());			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return betids;
	}
}
