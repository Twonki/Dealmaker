package dealmaker.entity;
import dealmaker.MyUtil;
import dealmaker.ObjectLoader;

import java.util.List;

public class Account {
    private String accountId;
    private String firstname;
    private String phoneNumber;
    private String developerId;
    private String uci;
    private int riskScore;
    private String currencyCode;
    private String productType;
    private String email;
    private String lastname;
    private String homeAddress;
    private List<Transaction> transactions;

    private String gender;
    private int age;

    public Account(String accountId, String firstname, String phoneNumber, String developerId, String uci, int riskScore, String currencyCode, String productType, String email, String lastname, String homeAddress) throws Exception{
        this.accountId = accountId;
        this.firstname = firstname;
        this.phoneNumber = phoneNumber;
        this.developerId = developerId;
        this.uci = uci;
        this.riskScore = riskScore;
        this.currencyCode = currencyCode;
        this.productType = productType;
        this.email = email;
        this.lastname = lastname;
        this.homeAddress = homeAddress;
    }

    public void update() throws Exception{
        this.age = MyUtil.getRandomNumberInRange(15,80);
        this.gender = MyUtil.getRandomGender();
        loadTransactions(5); //account has less than 1 transaction create 5

    }

    public void loadTransactions(int quantity) throws Exception{
        //retrieve transactions
        this.transactions = ObjectLoader.getAllTransactionsForID(this.accountId);
        //populate transactions if blank
        if(transactions.size()<1){
            ObjectLoader.createTransaction(this.accountId,quantity);
            this.transactions = ObjectLoader.getAllTransactionsForID(this.accountId);
        }
        if(transactions.size()>0){
            for(Transaction t: transactions){
                t.updateVars();
            }
        }

    }

    public String getAccountId() {
        return accountId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public String getUci() {
        return uci;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getProductType() {
        return productType;
    }

    public String getEmail() {
        return email;
    }

    public String getLastname() {
        return lastname;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }
}
