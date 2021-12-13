const BtnRight=document.getElementById('btnRight');

const response = await;
// fetch('http://localhost:8080/test',
// {
//     method: 'POST',
//     headers: {
//         'Accept': 'application/json',
//         'Content-Type': 'application/json',
//         'Authorization': 'Bearer  eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyaXRpayIsImlhdCI6MTYzNjgyMTE4M30.fkZUcUJ0dv0PJme9V9V08pzrud5wHgvBvOAZEq7ScGZXXOnLAusTKX6HNCckmIXD1sTgNWCnaUvw14pBLkTPSQ'
//     },
//     body: `{
//         input: "ritik,simu,rishav,ritik,rishu",
//          tidyUp: true,
//          attackClones: false,
//          explode: "Commas",
//          quotes: "no",
//          delimiter: "Spaces",
//          openTag:"",
//          closeTag:"",
//          interval: 2,
//          intervalOpenWrap:"<ul>",
//          intervalCloseWrap:"</ul>"
//     }`,
// })
// response.json().then(data => {
//     console.log(data);
// })

const sendHttpRequest= (method, url, data) =>{
    return fetch(url, {
        method: method,
        body: JSON.stringify(data),
        headers:{'Content-Type': 'application/json',
        'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyaXRpayIsImlhdCI6MTYzNjgyMTE4M30.fkZUcUJ0dv0PJme9V9V08pzrud5wHgvBvOAZEq7ScGZXXOnLAusTKX6HNCckmIXD1sTgNWCnaUvw14pBLkTPSQ'}
    }).then(response => {
        return response.json();
    });
};

const postData =() =>{
    sendHttpRequest('POST','http://localhost:8080/test',{
        input: "ritik,simu,rishav,ritik,rishu",
        tidyUp: true,
        attackClones: false,
        explode: "Commas",
        quotes: "no",
        delimiter: "Spaces",
        openTag:"",
        closeTag:"",
        interval: 2,
        intervalOpenWrap:"<ul>",
        intervalCloseWrap:"</ul>"

    }).then(responseData => {
        console.log(responseData);
    });
};
