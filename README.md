# LittleBankerApplication
Welcome to little banker application, a RESTful service that mimics small banking enterprises

## Controllers
The little-banker service provides the following controllers:

## Customer Management Controller
 -Create a new customer  
 -Remove an existing customer  
 -Modify customer details

## Account Management Controller
 -Create a new account  
 -Remove an existing account  
 -Modify account details  
 -View account summary  

## Transaction Operations Controller
 -Transfer credits from one account to another  
 -View history of transactions  
 -Lookup and search in the history of transactions, search by amount, IBAN, message  

## Domain Model
little banker application contains the following attributes for the entities:  

## Customer
 -Name  
 -Surname  
 -Sex  
 -Nationality  
 -Date of birth  
 -Card number  
 -Card date of issue  
 -Card date of expiry  

## Account
 -IBAN  
 -Currency  
 -Balance  
 -Transfer  
 -Date  
 -Amount  
 -Source IBAN  
 -Target IBAN  
 -Message  

## Installation
To run the little banker service, you need to follow these steps:

Clone the repository from GitHub.  
Install the required dependencies by running mvn install.  
Start the service by running mvn spring-boot:run.  

## Usage
Once you have installed and started the service, you can interact with it using HTTP requests.

Here are some examples:  

For creating customer HTTP request.  

POST /customer/register
{
    "name": "John",
    "surname": "Black",
    "sex": "male",
    "nationality": "American",
    "dateOfBirth": "1980-01-01",
    "cardNumber": "1234-5678-9012-3456",
    "dateOfCardIssue": "2010-01-01",
    "dateOfCardExpiration": "2020-01-01"
}
-------------------------------------------------
For checking all register customers HTTP request.

GET /customer/getAll
-------------------------------------------------

For creating account HTTP request.

POST /account/register

{
  "iban": "CZ008484521335489",
  "currency": "CZK",
  "accountBalance": 1000.0
}
--------------------------------------------------

For creating second account (for testing transaction between 2 accounts) HTTP request.

POST /account/register

{
  "iban": "CZ085462000087502",
  "currency": "CZK",
  "accountBalance": 1000.0
}
--------------------------------------------------

For making transfer HTTP request.

POST /transaction/transfer

{
"sourceIban": "CZ008484521335489",
"targetIban": "CZ085462000087502",
"amountTransferred": 150.0,
"message": "Debts"
}
----------------------------------------------------

For view transaction history HTTP request.

GET /transaction/history
-----------------------------------------------------

For search transaction by iban HTTP request

GET /transaction/search/by-iban/CZ008484521335489

------------------------------------------------------

## Contact
If you have any questions or comments about little banker application, please feel free to contact me at mat.bures@gmail.com.
