
# Create user
mongo --username root --password example --authenticationDatabase admin < test-user.js

# Create database
mongo --username root --password example --authenticationDatabase admin < test-database.js

# Verify the user
mongo --username test --password secret --authenticationDatabase admin  < test-verify.js
