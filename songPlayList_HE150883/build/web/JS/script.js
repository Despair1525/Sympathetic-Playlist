let closePlayer = document.querySelector('#close-player');
let musicPlayer = document.querySelector('.music-player');
let boxContainer = document.querySelector('.container .box-container');


function showhide() {
    var div = document.getElementById(arguments[0]);
    if (div.style.display !== "none") {
        div.style.display = "none";
    } else {
        div.style.display = "block";
    }
}
closePlayer.onclick = () => {
    closePlayer.classList.toggle('fa-times');
    musicPlayer.classList.toggle('active');
    boxContainer.classList.toggle('active');
}

let boxes = document.querySelectorAll('.container .box-container .box');

boxes.forEach(box => {

    box.querySelector('.image').onclick = () => {
        let src = box.getAttribute('data-src');
        let text = box.querySelector('.content h3').innerText;
        closePlayer.classList.add('fa-times');
        musicPlayer.classList.add('active');
        boxContainer.classList.add('active');
        musicPlayer.querySelector('h3').innerText = text;
        musicPlayer.querySelector('audio').src = src;
        musicPlayer.querySelector('audio').play();
    }

});

