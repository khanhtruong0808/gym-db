// Adding event listener to textarea to reset after an error
let textArea = document.getElementById('input-text');
textArea.addEventListener("focus", function (event) {
    if (textArea.id != 'input-text') {
        textArea.placeholder = "Enter Gym Name Here (Up to 25 characters)";
        textArea.id = 'input-text';
    }
})


// Get config settings
let getconfig = {
    method: "get", headers: {
        'Content-Type': 'application/json,' + 'Access-Control-Allow-Origin:*'
    },
};

// This function will get the data, make a table and add it to div#result
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

        // Replace value in div#result with the current table
        document.getElementById('result').replaceChildren(table);
    }).catch(err => console.log("err", err));
}

getGymData();

// Insert function, called when enter is clicked
function insert() {
    let gymName = document.getElementById('input-text').value;

    if (gymName.length != 0) {
        let url = "https://gym-db-174.herokuapp.com/insert?gym_name=" + gymName;

        // Post request
        fetch(url).then((data) => {

            // If successful then this tells the table to update
            getGymData();
            // Clears textarea
            textArea.value = '';
        }).catch(err => console.log("err", err));
    } else {
        // If nothing is being submitted then this alerts the user of an error
        textArea.id = "input-text-error"
        textArea.placeholder = "You can't submit an empty value!";
    }
}
