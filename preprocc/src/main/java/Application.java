import dealmaker.CapitalOneHttpClient;
import dealmaker.ObjectLoader;
import dealmaker.entity.Account;

public class Application {

    public static void main(String [] args) throws Exception{
        ObjectLoader objectLoader = new ObjectLoader();
        try {
            objectLoader.loadAllAccounts();
        }finally {
            objectLoader.closeClient();
        }


    }




}
