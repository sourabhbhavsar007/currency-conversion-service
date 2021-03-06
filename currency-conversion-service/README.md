This application is facilitates Currency Conversion for multiple currencies. 

For this, we use external API http://data.fixer.io which has all the listed currencies along with their conversion rates.

We use Spring Web Client to communicate with external API and fetch results.

We currently have 2 endpoints :

1) http://localhost:8080/api/exchange-rate/{baseCurrency}/{targetCurrency}/{units}

The above endpoints takes in date, baseCurrency and targetCurrency and units as parameters and return currency conversion rate 
for targetCurrency w.r.t baseCurrency.

Note : The above endpoint is implemented and working fine, but since, our external API (http://data.fixer.io)
ony allows request from its premium or paid members, we are getting response as Forbidden Access, since I'm using it as a trial user.


Since, a lot of API's are blocked or restricted to trial users on external API (http://data.fixer.io), it becomes 
very difficult to analyze trend of currency conversion rates in past dates and implement any other functionalities.

Also, I tried using many other external API's for currency conversion, but almost all of them seem to be paid versions.


2) http://localhost:8080/api/exchange-rate/getAllCurrencies

The above endpoint fetches all currency conversion rates which are listed on our external API (http://data.fixer.io)

We see all the currency conversion rates with the base as Euro.

3) http://localhost:8080/api/exchange-rate/redirect

The above endpoint redirects, returns URL of external public URL which interactively depicts charts of currency exchange rates.
