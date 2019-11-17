
CREATE TABLE IF NOT EXISTS accounts 
(
    accountId INTEGER PRIMARY KEY,
    firstname TEXT,
	lastname TEXT,
	Age INTEGER,
	Gender TEXT,
	Phonenumber TEXT,
	Devid TEXT,
	Uci INTEGER,
	riskScore INTEGER,
	CurrencyCode TEXT,
	Producttype TEXT,
	Homeaddress TEXT
);

CREATE TABLE IF NOT EXISTS transactions(
	tid TEXT PRIMARY KEY,
	accountId INTEGER REFERENCES accounts (accountId) 
         ON DELETE CASCADE ,
	amount FLOAT,
	currency TEXT,
	timestamp DATETIME,
	Latitude FLOAT,
    longitude FLOAT,
    tstatus TEXT,
    category TEXT
);

CREATE TABLE IF NOT EXISTS deals(
	Dealer INTEGER REFERENCES accounts (accountId) 
         ON DELETE CASCADE ,
	Dealed INTEGER REFERENCES accounts (accountId) 
         ON DELETE CASCADE ,
	Liked Boolean
);

CREATE VIEW IF NOT EXISTS categoryspending AS 
SELECT accountId, category, sum(amount) as categoryamount
FROM transactions
GROUP BY accountId, category;

CREATE VIEW IF NOT EXISTS totalspending AS
SELECT accountId, sum(amount) as totalamount
FROM transactions;

CREATE TABLE IF NOT EXISTS bets (
	betid INTEGER PRIMARY KEY AUTOINCREMENT,
	accountId INTEGER,
	offer FLOAT,
	demand FLOAT
);