/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */




function showhideOrigin() {
    
    var div = document.getElementById(arguments[0]);
    console.log(arguments[0]);
    if(div.style.display.valueOf() == ""){
         div.style.display = "none";
    };
    if (div.style.display != "none" ) {
        div.style.display = "none";
    } else {
        div.style.display = "block";
    }
}

function showhide() {
    var div = document.getElementById(arguments[0]);
    div.style.display = "block";
    
}

function getUploadName(){
    var ID =   document.getElementById(arguments[0]).value;
     const myArray = ID.split("\\");
     var songIDTail = myArray[myArray.length-1];
     
     const myArray2 = songIDTail.split(".");
     var songID = myArray2[0];
     document.getElementById("songId").value = songID;
     
     console.log(songID);
};