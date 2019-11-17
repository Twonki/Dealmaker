package dealmaker.entity;

import dealmaker.MyUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Transaction {

    private String transactionUUID;
    private String accountUUID;
    private double amount;
    private String creditDebitIndicator;
    private String currency;
    private String timestamp;
    private Date timestamp_date;
    private double latitude;
    private double longitude;
    private String status;
    private int accountId_int;

    private String catagory;

    public Transaction(String transactionUUID, String accountUUID, double amount, String creditDebitIndicator, String currency, String timestamp, double latitude, double longitude, String status) {
        this.transactionUUID = transactionUUID;
        this.accountUUID = accountUUID;
        this.amount = amount;
        this.creditDebitIndicator = creditDebitIndicator;
        this.currency = currency;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
    }


    public String getTransactionUUID() {
        return transactionUUID;
    }

    public String getAccountUUID() {
        return accountUUID;
    }

    public double getAmount() {
        return amount;
    }

    public String getCreditDebitIndicator() {
        return creditDebitIndicator;
    }

    public String getCurrency() {
        return currency;
    }

    public Date getTimestamp() {
        return timestamp_date;
    }

    public String getTimestampString(){
        return timestamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getStatus() {
        return status;
    }

    public String getCatagory() {
        return catagory;
    }

    public void updateVars(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try{
            this.timestamp_date = sdf.parse(timestamp);
        }catch (ParseException e){
            e.printStackTrace();
        }
        this.catagory = MyUtil.randomAssignCatagory();
        this.accountId_int = Integer.parseInt(accountUUID);
    }

    public String insertStatement(){
        String params = "\'"+transactionUUID+"\'," +accountId_int+","+amount+",\'"+currency+"\',"+timestamp_date+","+latitude+","+longitude+
                ",\'"+status+"\'"+",\'"+catagory+"\'";
        return "insert into transactions values("+params+");";

    }


}
