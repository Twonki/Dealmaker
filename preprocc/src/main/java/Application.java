import dealmaker.CapitalOneHttpClient;
import dealmaker.DataUpload;
import dealmaker.MyUtil;
import dealmaker.ObjectLoader;
import dealmaker.entity.Account;
import dealmaker.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String [] args) throws Exception{
        ObjectLoader objectLoader = new ObjectLoader();
        try {

            objectLoader.loadAllAccounts();
            /*for(Account a: objectLoader.getAccounts()){
                System.out.println(a.insertStatement());
                for (Transaction t: a.getTransactions()){
                    System.out.println(t.insertStatement());
                }
            }*/
            DataUpload.upload(objectLoader.getAccounts());
        }finally {
            objectLoader.closeClient();
        }

    }




}
