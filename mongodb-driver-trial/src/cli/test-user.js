use admin

db.createUser({
	"user" : "test",
	"pwd" : "secret",
	"roles" : [
		{
			"role" : "readWrite",
			"db" : "test"
		}
	],
	passwordDigestor: "server"
})
