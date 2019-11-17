package dealmaker;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MyUtil {

    private static HashMap<Integer,String> catagories = new HashMap<Integer, String>();

    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static String getRandomGender(){
        int x =getRandomNumberInRange(0,2);
        if(x==0)return "F";
        else if(x ==1)return "M";
        else return "U";
    }

    public static void setCatagories(){
        catagories.put(0,"SHOPPING");
        catagories.put(1,"INVESTMENT");
        catagories.put(2,"GROCERY");
        catagories.put(3,"DINING_OUT");
        catagories.put(4,"HOUSING");
        catagories.put(5,"HEALTHCARE");
        catagories.put(6,"GAMING");
        catagories.put(7,"ENTERTAINMENT");
    }
    public static String randomAssignCatagory(){
        setCatagories();
        int x = getRandomNumberInRange(0,7);
        return catagories.get(x);
    }

    public static String readsql(){
        String fileAsString ="";
        try {
            InputStream is = new FileInputStream("src/main/resources/sql/CreateTables.sql");
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            try{ String line = buf.readLine();
                StringBuilder sb = new StringBuilder();
                while (line != null) {
                    sb.append(line).append("\n");
                    line = buf.readLine();
                }

                fileAsString = sb.toString();
                System.out.println("sql : " + fileAsString);
            }catch(IOException e2) {
                e2.printStackTrace();
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return fileAsString;
    }


}
