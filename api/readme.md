# Api

This is the API of *Dealmaker*

## Run 
On console: 'mvn clean jetty:run'

In Docker:

'docker build . -t api:tomcat'

then 

'docker run -p 8080:8080 api:tomcat'

## Check

look at http://localhost:8080/dealmakerapi-0.0.1-SNAPSHOT/health
 
## Resources

Where *XXX* is the AccountID

'/health' -> should show status:alive

'/matches/XXX' -> Returns all top-matches for this account id

'/info/XXX' -> Returns the categories for this account id

'/deals/XXX' -> shows every account which XXX liked, and has been liked back

'/deals/XXX?acc=YYY&like=True' -> *Swipes Right* from XXX to YYY (XXX Likes YYY)

'put $ /deals/XXX?acc=YYY&like=False' -> *Swipes Left* from XXX to YYY (XXX DisLikes YYY)

'get $ /bets/XXX' -> Gets an JSON with open and successfull bets

'put $ /bets/XXX?betid=ZZZ&offer=123&demand=321' -> Enters the offer and demand into db

Valid account: 73320507
