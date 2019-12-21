# Revolut Challenge - Money Transfers

### How to build:
mvn clean package

### How to execute:
java -jar target/transfer-1.0.jar server app.yml

### Solution
The transfer comprises of a debit in the origin account and a credit in the destination account.
The accounts are stored in a ConcurrentHashMap where the key is the accountId.
Each of these operations (debit and credit) are atomic, but the whole transfer is not. I did this way to avoid
synchronizing the two calls, making the service more scalable. Since each of them is thread-safe, the whole transfer is eventual consistent.
In worst case, the client will notice a delay between the debit and the credit operations.
