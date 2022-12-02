// Get config settings
let getconfig = {
    method: "get", headers: {
        'Content-Type': 'application/json,' + 'Access-Control-Allow-Origin:*'
    },
};

function getGymData() {
// Get Gym Data, parse, and create table
    fetch('https://gym-db-174.herokuapp.com/select', getconfig)
        .then((response) => {
            return response.json();
        }).then(data => {

        // Creating Table
        let table = document.createElement("table");

        // Creating Table header
        let thread = table.createTHead();
        let row = thread.insertRow();
        let th = document.createElement("th");
        th.appendChild(document.createTextNode("id"));
        row.appendChild(th);
        th = document.createElement("th");
        th.appendChild(document.createTextNode("gym_name"));
        row.appendChild(th);
        th = document.createElement("th");
        th.appendChild(document.createTextNode("location"));
        row.appendChild(th);

        row = thread.insertRow();

        let body = table.createTBody();
        // Creating table rows for data
        for (let key in data) {
            // Add row to table
            row = body.insertRow();

            // Go through each json object, stringify and parse
            let jsondata = JSON.stringify(data[key]);
            let obj = JSON.parse(jsondata);

            // Insert a cell and set the value for each gym column
            let cell = row.insertCell();
            let ctext = document.createTextNode(obj["id"]);
            cell.appendChild(ctext);
            cell = row.insertCell();
            ctext = document.createTextNode(obj["gymName"]);
            cell.appendChild(ctext);
            cell = row.insertCell();
            ctext = document.createTextNode(obj["location"]);
            cell.appendChild(ctext);
        }

        // Appending built table to div with id=result
        document.getElementById('result').innerHTML = '';
        document.getElementById('result').appendChild(table);
    }).catch(err => console.log("err", err));
}

getGymData();

// Insert function, called when enter is clicked
function insert() {
    let gymName = document.getElementById('input-text').value;

    let url = "https://gym-db-174.herokuapp.com/insert?gym_name=" + gymName;

    fetch(url).then((data) => {
        console.log(data);
        getGymData();
        document.getElementById('input-text').value = '';
    }).catch(err => console.log("err", err));
}