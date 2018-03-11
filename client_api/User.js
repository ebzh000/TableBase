const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const mysql = require('mysql');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));

// connection configurations
const db = mysql.createConnection({
    host: 'localhost',
    user: 'erikz',
    password: 'ADASDxcaq2',
    database: 'table_base_gui'
});

// connect to database
db.connect();

// default route
app.get('/', function (req, res) {
    return res.send({error: true, message: 'Please enter a valid URL'})
});

app.get('/user', function (req, res) {
    let email = req.query.email;
    let password = req.query.password;

    if (!password) {
        return res.status(400).send({error: true, message: 'Please enter a password'})
    }

    if (!email) {
        db.query("SELECT * FROM users WHERE username = ?", username, function (error, results, fields) {
            if (error) throw error;
            return res.send({error: false, data: results[0]})
        });
    } else {
        db.query("SELECT * FROM users WHERE email = ?", email, function (error, results, fields) {
            if (error) throw error;
            return res.send({error: false, data: results[0]})
        });
    }
});

app.post('/user', function (req, res) {
    let username = req.body.username;
    let email = req.body.email;
    let password = req.body.password;

    if (!username || !email || !password) {
        return res.status(400).send({error: true, message: 'Please provide a user'})
    }

    db.query("INSERT INTO users (username, email, password) VALUES(?,?,?)", [username, email, password], function (error, results, field) {
        if (error) throw error;
        return res.send({error: false, data: results, message: 'User has been added'})
    })
});

// port must be set to 8080 because incoming http requests are routed from port 80 to port 8080
app.listen(8081, function () {
    console.log('Node app is running on port 8081');
});

// allows "grunt dev" to create a development server with livereload
module.exports = app;
