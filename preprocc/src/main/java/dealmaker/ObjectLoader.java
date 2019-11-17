package dealmaker;

import dealmaker.entity.Account;
import dealmaker.entity.Transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectLoader {

    private static CapitalOneHttpClient client = new CapitalOneHttpClient();
    private List<Account> accounts;


    public ObjectLoader() {
    }

    public void createAccounts(int quantity) throws Exception{

            System.out.println("creating "+quantity+" accounts:");
            client.createAccounts(readKey(),String.valueOf(quantity));

    }
    public void loadAllAccounts() throws Exception{

            System.out.println("get all accounts:");
            accounts = client.getAllAccounts(readKey());

            if(accounts.size()<1){
                createAccounts(3);
            }
            if(accounts.size()>0){
                for(Account a: accounts){
                    a.update();
                }
            }
    }

    public static void createTransaction(String accountId, int quantity)throws Exception{
        System.out.println("get all transactions for accountId "+accountId+":");
        client.createTransactions(readKey(),accountId,quantity);
    }

    public static List<Transaction> getAllTransactionsForID(String accountId) throws Exception{
        List<Transaction> transactions = new ArrayList<Transaction>();

            System.out.println("get all transactions:");
            transactions = client.getAllTransactionsForID(readKey(),accountId);

        return transactions;

    }

    public static String readKey(){
        String fileAsString ="";
        try {
            InputStream is = new FileInputStream("src/main/resources/key.txt");
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            try{ String line = buf.readLine();
                StringBuilder sb = new StringBuilder();
                while (line != null) {
                    sb.append(line).append("\n");
                    line = buf.readLine();
                }

                fileAsString = sb.toString();
                //System.out.println("key : " + fileAsString);
            }catch(IOException e2) {
                e2.printStackTrace();
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return fileAsString;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void closeClient()throws IOException{
        client.close();
    }

}
