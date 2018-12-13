var fs = require('fs');

module.exports = {
  checkLogDirectory: function(){
      var dir = "./logs";
      if(!fs.existsSync(dir)){
        fs.mkdirSync(dir);
        console.log("logs dir is created");
        return false;
      } else return true;
  },

  checkTargetMessagesDirectory: function(){
    var userDir = arguments[0];
    var targetDir = arguments[1];
    var dir = "./logs/users/"+userDir+"/messages/"+targetDir;
    //var dir = path.concat(userDir);
    //dir = dir.concat("/messages/");
    if(fs.existsSync(dir)){
      return true;
    } else {
      fs.mkdirSync(dir);
      if(fs.existsSync(dir)){
        fs.appendFileSync("./logs/serverlogs/registration.txt","./logs/users/"+arguments[0]+"/messages/"+arguments[1]+" folder has been created for the use [SUCCESS 200]r\n");
        return true;
      }
      fs.appendFileSync("./logs/serverlogs/registration.txt","./logs/users/"+arguments[0]+"/messages/"+arguments[1]+" folder could not be created [ERROR 500]\n");
      return false;
    }
    return false;

  },

  checkUserMessagesDirectory: function(){
    var userDir = arguments[0];
    var path = "./logs/users/";
    var dir = path.concat(userDir);
    dir = dir.concat("/messages/");
    if(fs.existsSync(dir)){
      return true;
    } else {
      fs.mkdirSync(dir);
      if(fs.existsSync(dir)){
        fs.appendFileSync("./logs/serverlogs/registration.txt","./logs/users/" + arguments[0] + "/messages/ folder has been created for the use [SUCCESS 200]r\n");
        return true;
      }
      fs.appendFileSync("./logs/serverlogs/registration.txt","./logs/users/" + arguments[0] + "/messages/ folder could not be created [ERROR 500]\n");
      return false;
    }
    return false;
  },

  checkUserDirectory: function(){
      var userDir = arguments[0];
      var path = "./logs/users/";
      var dir = path.concat(userDir);
      if(fs.existsSync(dir)){
        return true;
      } else {
        fs.mkdirSync(dir);
        if(fs.existsSync(dir)){
          fs.appendFileSync("./logs/serverlogs/registration.txt",arguments[0] + " folder has been created for the use [SUCCESS 200]r\n");
          return true;
        }
        fs.appendFileSync("./logs/serverlogs/registration.txt",arguments[0] + "folder could not be created [ERROR 500]\n");
        return false;
      }
      return false;
  },

  checkUserMessagesFile: function(){
    var path = "./logs/users/"+arguments[0]+"/messages/"+arguments[1]+"/texts.txt";
    //dir = path.concat(targetDir);
    fs.open(path, "wx", function(error, fd){
      if (error) {
        fs.appendFileSync("./logs/serverlogs/registration.txt","./logs/"+arguments[0]+"/messages/"+arguments[1]+" file could not be created [ERROR 500]\n");
        return false;
      };
      fs.close(fd, function(error) {
        fs.appendFileSync("./logs/serverlogs/registration.txt","./logs/"+arguments[0]+"/messages/"+arguments[1]+" file could not be closed [ERROR 500]\n");
        return false;
      });
      return true;
    });
  },

  checkUserLogFile: function(){
    var path = "./logs/users/"+arguments[0]+"/logs.txt";
    //dir = path.concat(targetDir);
    fs.open(path, "wx", function(error, fd){
      if (error) {
        fs.appendFileSync(path,"user log file could not be created [ERROR 500]\n");
        return false;
      };
      fs.close(fd, function(error) {
        fs.appendFileSync(path,"user log file could not be closed [ERROR 500]\n");
        return false;
      });
      return true;
    });
  }

};


/*var t
if(fs.exists(targetDir)){
  return true;
} else {
  fs.writeFile(targetDir, {flag: 'wx'});
  if(fs.exists(targetDir)){
    fs.appendFileSync("./logs/serverlogs/registration.txt","./logs/users/messages/"+arguments[0]+" folder has been created for the use [SUCCESS 200]r\n");
    return true;
  }
  fs.appendFileSync("./logs/serverlogs/registration.txt","./logs/users/messages/"+arguments[0]+" folder could not be created [ERROR 500]\n");
  return false;
}*/
/*targetDir = arguments[0]+".txt";
var path = "./logs/"+arguments[0]+"/messages/";
dir = path.concat(targetDir);
fs.open(dir, "wx", function(error, fd){
  if (error) {
    fs.appendFileSync("./logs/serverlogs/registration.txt","./logs/"+arguments[0]+"/messages/"+arguments[1]+" folder could not be created [ERROR 500]\n");
    return false;
  };
  fs.close(fd, function(error) {
    fs.appendFileSync("./logs/serverlogs/registration.txt","./logs/"+arguments[0]+"/messages/"+arguments[1]+" folder could not be closed [ERROR 500]\n");
    return false;
  });
  return true;
});*/
