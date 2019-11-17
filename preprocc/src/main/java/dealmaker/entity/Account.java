package dealmaker.entity;
import dealmaker.MyUtil;
import dealmaker.ObjectLoader;

import java.util.List;

public class Account {
    private String accountId;
    private int accountId_int;
    private String firstname;
    private String phoneNumber;
    private String developerId;
    //private int devid_int;
    private String uci;
    private int uci_int;
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
        this.accountId_int = Integer.parseInt(accountId);
        this.uci_int = Integer.parseInt(uci);
        //this.devid_int = Integer.parseInt(developerId);
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
    public int getAccountId_int(){
        return accountId_int;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public String insertStatement(){
        String params = accountId_int + ",\'"+firstname+"\'"+",\'"+lastname+"\',"+age+",\'"+gender+"\'"+",\'"+phoneNumber+"\'"+",\'"+developerId+"\',"+uci_int+
                ","+riskScore+",\'"+currencyCode+"\'"+",\'"+productType+"\'"+",\'"+homeAddress+"\'";
        return "insert into accounts(paramname,paramname,paramname) values("+params+");";


    }}
