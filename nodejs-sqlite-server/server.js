var express = require('express');
var app = express();

app.get('/', function(req, res){
  console.log('hello world');

  var arr = [];
  db.serialize(function(){
    db.all('select playlistid, name from playlist', function(err, rows) {
      if(err) {
        res.json({'message':'error'});
      }
      cosole.log(rows);
      res.json(rows);
    });
  });


});

var sqlite3 = require('sqlite3').verbose();

var db = new sqlite3.Database('./db/chinook.db',  sqlite3.OPEN_READWRITE, function(err){
  if(err) { 
    console.error(err.message);
  }
  console.log('connected to the in-memory SQlite database.');
});
//========================================
var server = app.listen(3000, function() {
  console.log('load success!!');
});
