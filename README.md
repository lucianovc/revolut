# Revolut Challenge - Money Transfers

### How to build:
mvn `clean package`

### How to execute:
`java -jar target/transfer-1.0.jar server app.yml`

### Solution
The transfer comprises of a debit in the origin account and a credit in the destination account.
The accounts are stored in a ConcurrentHashMap where the key is the accountId.
Each of these operations (debit and credit) are atomic, but the whole transfer is not. I did this way to avoid
synchronizing the two calls, making the service more scalable. Since each of them is thread-safe, the whole transfer is eventual consistent.
In worst case, the client will notice a delay between the debit and the credit operations.

### REST services
Besides the /transfer service, I also created balance, deposit and withdraw services to help in testing the transfer. All of them are under the 8080 port as POST services.

* `/transfer`
* `/deposit`
* `/balance`
* `/withdraw`

#### Example:
* `curl -d '{"to":"1", "amount":10}' -H "Content-Type: application/json" -X POST localhost:8080/deposit`
* `curl -d '{"to":"2", "amount":10}' -H "Content-Type: application/json" -X POST localhost:8080/deposit`
* `curl -d '{"accountId":"1"}' -H "Content-Type: application/json" -X POST localhost:8080/balance`
* `curl -d '{"accountId":"2"}' -H "Content-Type: application/json" -X POST localhost:8080/balance`
* `curl -d '{"from":1, "to":"2", "amount":10}' -H "Content-Type: application/json" -X POST localhost:8080/transfer`
* `curl -d '{"from":"2", "amount":10}' -H "Content-Type: application/json" -X POST localhost:8080/withdraw`
