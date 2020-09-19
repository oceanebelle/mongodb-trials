

###  Install mongodb clients

Ubuntu

`sudo apt install mongodb-clients`

 ### Connecting to default mongodb using admin user
 
 `mongo --username root --password example --authenticationDatabase admin`
 
 
 ### To execute scripts written to js
 
 Create user
 
 `mongo --username root --password example --authenticationDatabase admin < test-user.js`
 
 Create database and collection
 
 `mongo --username root --password example --authenticationDatabase admin < test-database.js`
 
 Verify the creation was successful
 
 `mongo --username test --password secret --authenticationDatabase admin < test-verify.js`