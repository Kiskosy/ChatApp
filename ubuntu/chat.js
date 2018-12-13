var express = require('express');
var MongoClient = require('mongodb').MongoClient;
var bodyParser = require("body-parser");
var MongoDBurl = "mongodb://localhost:27017/";
var fs = require('fs');
var sha256 = require('sha256');
var utf8 = require('utf8');
var logsScript = require('./logs');
var app = express();
var port = process.env.PORT || 8080;
app.use(bodyParser.json());

function base64_encode(file) {
	// read binary data
    var bitmap = fs.readFileSync(file);
    // convert binary data to base64 encoded string
    return Buffer.from(bitmap).toString('base64');
}

var http = require('http').Server(app);
var io = require('socket.io')(http);

app.get('/', function(req, res) {
   console.log('omg');
   res.send("asd");
});

var nsp = io.of('/UpdateConversation');
var connectedUsers = [];
nsp.on('UpdateConversation', function(socket) {
   console.log('someone connected');

   socket.on('PhoneToServerMessage', function(data) {

    var accountName = data.accountName;
   	var targetAccount = data.targetAccount;
   	var messageType = data.messageType;
   	var messageContent = data.messageContent;
   	var messageTime = data.messageTime;

   	if (logsScript.checkUserDirectory(accountName) == false){
   		respone.send("User folder on serverside could not be made. Please check");
   	}

     if (logsScript.checkUserDirectory(targetAccount) == false){
   		respone.send("User folder on serverside could not be made. Please check");
   	}

   	if (logsScript.checkUserMessagesDirectory(accountName) == false){
   		respone.send("User messages folder on serverside could not be made. Please check");
   	}

     if (logsScript.checkUserMessagesDirectory(targetAccount) == false){
   		respone.send("User messages folder on serverside could not be made. Please check");
   	}

   	if (logsScript.checkTargetMessagesDirectory(accountName,targetAccount) == false){
   		respone.send("TargetUser messages folder on serverside could not be made. Please check");
   	}

     if (logsScript.checkTargetMessagesDirectory(targetAccount,accountName) == false){
   		respone.send("TargetUser messages folder on serverside could not be made. Please check");
   	}

   	if (logsScript.checkUserMessagesFile(accountName,targetAccount) == false){
   		respone.send("UserMessage file on serverside could not be made. Please check");
   	}

     if (logsScript.checkUserMessagesFile(targetAccount,accountName) == false){
   		respone.send("UserMessage file on serverside could not be made. Please check");
   	}


     var path = "./logs/users/"+accountName+"/messages/"+targetAccount+"/texts.txt";
     	if (fs.appendFileSync(path,"{ accountName : '" + accountName +"', targetAccount : '" + targetAccount +"', messageType : '" + messageType +"', messageContent : '" + messageContent + "', messageTime : '" + messageTime + "' }\n")){
         console.log("done1");
     };

     var path2 = "./logs/users/"+targetAccount+"/messages/"+accountName+"/texts.txt";
       if (fs.appendFileSync(path2,"{ accountName : '" + targetAccount +"', targetAccount : '" + accountName +"', messageType : '" + "received" +"', messageContent : '" + messageContent + "', messageTime : '" + messageTime + "' }\n")){
         console.log("done2");
     };


   	//var test = Object.assign(friendList, base64strOfImage);
   	//console.log(test);

   	//respond.send(JSON.stringify(jsonObject));

     nsp.emit('PhoneToServerMessage', JSON.stringify("200"));

   });

   socket.on('RegisterUserMessageChat', function(data){
     //console.log(data.accountName);
    if(connectedUsers.push(data.accountName)){
      let path = "./logs/users/"+data.accountName+"/messages/"+data.targetAccount+"/texts.txt";
      let connectedUserChatUpdate = fs.readFileSync(path,'utf8')
      console.log(connectedUserChatUpdate);
      nsp.emit('test',"hello");
     };




     /*for( let i = 0; i < connectedUsers.length-1; i++ ){
       console.log(connectedUsers[i]);
     }*/


   });

   socket.on('DisconnectUserMessageChat', function(data){
     console.log(data.accountName);
     for( let i = 0; i < connectedUsers.length-1; i++){
       if (connectedUsers[i] === data.accountName){
         connectedUsers.splice(i,1);
       }
     }
   });

   //var contents = fs.readFileSync("./logs/users/kosy/messages/friends1/texts.txt",'utf8');
   nsp.emit('UpdateConversation', 'Hello everyone!');
});

var disconnectUsers = io.of('/DisconnectUsers');
disconnectUsers.on('connection', function(socket){

  socket.on('DisconnectALLUserMessageChat', function(data) {
    connectedUsers = [];
  });


});

var testIO = io.of('/test');
testIO.on('connection', function(socket){
  socket.on('test', function(data){
    console.log(socket.accountName + " ............ ");
    testIO.emit('hi','hello everyone');
  })
});


app.post('/GetFriendList', function(request, respond){
	console.log(request.body.accountName);
	//respond.send(JSON.stringify("SUCCESS"));
	//respond.sendFile("/home/kosy/Desktop/express/chat/logs/users/kosy/sunnykicsi.jpg");
	//var friendlist = fs.readFileSync("./logs/users/kosy/details.txt",'utf8');
	//var base64strOfImage = base64_encode("/home/kosy/Desktop/express/chat/logs/users/kosy/sunnykicsi.jpg");

  var jsonObject = {}
  var key = 'friendDetails';
  jsonObject[key] = [];
	var friendList = { friendList : fs.readFileSync("./logs/users/kosy/details.txt",'utf8') };
	var base64strOfImage = { base64strOfImage : base64_encode("/home/kosy/Desktop/express/chat/logs/users/kosy/sunnykicsi.jpg") };

  jsonObject[key].push(friendList);
  jsonObject[key].push(base64strOfImage);
	//var test = Object.assign(friendList, base64strOfImage);
	//console.log(test);

	respond.send(JSON.stringify(jsonObject));
});

app.post('/LoginAccount', function(request, respond){

	fs.appendFileSync("./logs/serverlogs/loginrequests.txt","accountName : "+ request.body.accountName + " , password : " + request.body.password + " login request to the server\n");
	MongoClient.connect(MongoDBurl, function(err, db) {
		if (err) throw err;
		var DataBase = db.db("Chat");
		var hash = sha256(request.body.password);
		var accountNamequery = { accountName : request.body.accountName };
		var saltpassQuery = { saltpassInHexa : "4a2ddeb1f28d6a7d2dbc6832a0b6292c4640885c77e9c764e341f34b7f2b7b274372dedfe0e3dca98a9ce2e5831ddf534b297bfaae514d86b4d5df6b497cb791"};


		DataBase.collection("Accounts").findOne(accountNamequery, function(err, result){
			if (err) {
				fs.appendFileSync("./logs/serverlogs/loginrequests.txt",request.body.accountName + " provided accountName is Wrong [ERROR 500]\n");
				respond.send(JSON.stringify("ERROR accountName incorrect"));
				throw err;
			}
			fs.appendFileSync("./logs/serverlogs/loginrequests.txt",request.body.accountName + " provided accountName exists in MONGODB [SUCCESS 200]\n");
			//console.log(result.saltValueInHexa);
			var temp = hash.concat(result.saltValueInHexa);
			saltpassQuery = { saltpassInHexa : temp};
			db.close();
		});


		DataBase.collection("Accounts").findOne(saltpassQuery, function(err, result){
			if (err) {
				fs.appendFileSync("./logs/serverlogs/loginrequests.txt",request.body.accountName + " provided password is Wrong [ERROR 500]\n");
				respond.send(JSON.stringify("ERROR password incorrect"));
				throw err;
			}

			fs.appendFileSync("./logs/serverlogs/loginrequests.txt",request.body.accountName + " provided password is Correct, User is logged in [SUCCESS 200]\n");
			respond.send(JSON.stringify("SUCCESS"));
			//console.log(result);
		});

		db.close();
	});
});

app.post('/RegisterAccount', function(request, respond){

  var UserCreationBoolean;
  var userHasDirectory;
  var userHasMessageDirectory;
	//console.log("ii was called");
	console.log(request.body);
	MongoClient.connect(MongoDBurl, function(err, db) {
		if (err) throw err;
		var DataBase = db.db("Chat");
		//DataBase.collection("Accounts").find({}).toArray(function(err, result) {
		DataBase.collection("Accounts").insertOne(request.body, function(err, result){
			if (err) throw err;
			console.log(result.body + " has been inserted");
			fs.appendFileSync("./logs/serverlogs/registration.txt",result.body + " has been inserted into Accounts DB\n");
			db.close();
			//respond.send(JSON.stringify("200"));
		});
	});

  var accountName = request.body.accountName;
  if (logsScript.checkUserDirectory(accountName) == false){

		respone.send("User folder on serverside could not be made. Please check");
	}

  if (logsScript.checkUserMessagesDirectory(accountName) == false){
		respone.send("User messages folder on serverside could not be made. Please check");
	}

  respond.send(JSON.stringify("200"));


});

app.get('/GetToken', function(req, res){
	MongoClient.connect(MongoDBurl, function(err, db) {
		if (err) throw err;
		var DataBase = db.db("Chat");
		var options = {"sort": {_id : -1}};
		DataBase.collection("Accounts").find({},"token:1").sort({_id:-1}).limit(1).toArray(function(err,result){
			if (err) throw err;
			res.send(result[0].token);
		});
		/*//var query = { token: "1" };
		var max = DataBase.collection("Accounts").find().sort({_id:-1}).;
		//db.getCollection("Accounts").find().sort({_id:-1}).limit(1).pretty()
		//DataBase.collection("Accounts").find(query).toArray(function(err, result) {
			//if (err) throw err;
			//console.log(result + " .. token");
			res.send(max);
			db.close();
		//});*/
	});
});

app.post('/SendMessages', function(request, respond){
	var accountName = request.body.accountName;
	var targetAccount = request.body.targetAccount;
	var messageType = request.body.messageType;
	var messageContent = request.body.messageContent;
	var messageTime = request.body.messageTime;

	if (logsScript.checkUserDirectory(accountName) == false){
		respone.send("User folder on serverside could not be made. Please check");
	}

  if (logsScript.checkUserDirectory(targetAccount) == false){
		respone.send("User folder on serverside could not be made. Please check");
	}

	if (logsScript.checkUserMessagesDirectory(accountName) == false){
		respone.send("User messages folder on serverside could not be made. Please check");
	}

  if (logsScript.checkUserMessagesDirectory(targetAccount) == false){
		respone.send("User messages folder on serverside could not be made. Please check");
	}

	if (logsScript.checkTargetMessagesDirectory(accountName,targetAccount) == false){
		respone.send("TargetUser messages folder on serverside could not be made. Please check");
	}

  if (logsScript.checkTargetMessagesDirectory(targetAccount,accountName) == false){
		respone.send("TargetUser messages folder on serverside could not be made. Please check");
	}

	if (logsScript.checkUserMessagesFile(accountName,targetAccount) == false){
		respone.send("UserMessage file on serverside could not be made. Please check");
	}

  if (logsScript.checkUserMessagesFile(targetAccount,accountName) == false){
		respone.send("UserMessage file on serverside could not be made. Please check");
	}

	console.log(request.body);

  var path = "./logs/users/"+accountName+"/messages/"+targetAccount+"/texts.txt";
  	if (fs.appendFileSync(path,"{ accountName : '" + accountName +"', targetAccount : '" + targetAccount +"', messageType : '" + messageType +"', messageContent : '" + messageContent + "', messageTime : '" + messageTime + "' }\n")){
      console.log("done1");
  };

  var path2 = "./logs/users/"+targetAccount+"/messages/"+accountName+"/texts.txt";
    if (fs.appendFileSync(path2,"{ accountName : '" + targetAccount +"', targetAccount : '" + accountName +"', messageType : '" + "received" +"', messageContent : '" + messageContent + "', messageTime : '" + messageTime + "' }\n")){
      console.log("done2");
  };

  respond.send(JSON.stringify("200"));
});

app.post('/GetMessages', function(request, respond){
	var accountName = request.body.accountName;
	var targetAccount = request.body.targetAccount;
	if (logsScript.checkUserDirectory(accountName) == false){
		respone.send("User folder on serverside could not be made. Please check");
	}

	if (logsScript.checkUserMessagesDirectory(accountName) == false){
		respone.send("User messages folder on serverside could not be made. Please check");
	}

	if (logsScript.checkTargetMessagesDirectory(accountName,targetAccount) == false){
		respone.send("TargetUser messages folder on serverside could not be made. Please check");
	}

	if (logsScript.checkUserMessagesFile(accountName,targetAccount) == false){
		respone.send("UserMessage file on serverside could not be made. Please check");
	}
	console.log(request.body);

	var jsonObj;
	var path = "./logs/users/"+accountName+"/messages/"+targetAccount+"/texts.txt";
	fs.readFile(path,'utf8',function (err,data){
		if (err){
			respone.send("User messages folder on serverside could not be made. Please check");
		}
	});

});

//app.listen(port, '192.168.0.109');
http.listen(8080, '192.168.0.109');
console.log('Server running at http://192.168.0.109:8080/');







































//res.send("im called");
/*var checkLogDirectory = logsScript.checkLogDirectory();
if (logsScript.checkLogDirectory() == true){
  console.log("Lol");
};
var contents = fs.readFileSync("./logs/users/kosy/details.txt",'utf8');
console.log(contents);
res.send(contents);*/

//res.sendFile("/home/kosy/Desktop/express/chat/logs/users/kosy/sunnykicsi.jpg");
//var base64str = base64_encode("/home/kosy/Desktop/express/chat/logs/users/kosy/sunnykicsi.jpg");
//console.log(base64str);
//res.send(JSON.stringify(base64str));

/*MongoClient.connect(MongoDBurl, function(err, db) {
  if (err) throw err;
var dbo = db.db("Chat");
dbo.collection("Accounts").findOne({}, function(err, result) {
    if (err) throw err;
    console.log(result);
    res.send(result);
    db.close();
  });
}); */
