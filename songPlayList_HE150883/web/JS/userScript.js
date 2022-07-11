let currentSong = document.getElementById("mainSong");
let currentSongSource = currentSong.getAttribute('src');

let listSong = document.querySelectorAll('.song-list .son');
let mainSong = document.querySelector('.main-song song');
let title = document.querySelector('.main-song .title');
console.log(mainSong);
listSong.forEach(song => {
    song.onclick = () => {
        listSong.forEach(son => son.classList.remove('active'));
        song.classList.add('active');
        if (song.classList.contains('active')) {
            let src = song.children[0].children[0].getAttribute('src');
            let imgScr = song.getElementsByTagName('img')[0].getAttribute('src');
            document.getElementById('mainSong').setAttribute('src', src);
            document.getElementById('mainImage').setAttribute('src', imgScr);
            currentSongSource = src;
            let test = document.getElementById('mainSong').getAttribute('src');
            let text = song.children[1].innerHTML;
            title.innerHTML = text;
            song.children[0].load();
            document.getElementById('mainSong').autoplay = true;

        }
        ;

    }
})


function checkSong(song) {
    let src = song.children[0].children[0].getAttribute('src');
    return (src == currentSongSource)
}
function changeSong(event) {
    console.log("hello");
    let listSong = document.querySelectorAll('.song-list .son');
    const arr = Array.from(listSong);

    let numberSong = arr.length;

// let index = arr.findIndex(checkSong)

    let indexSong = arr.findIndex(checkSong);



    let nextSong = 0;
    if (event) {
        nextSong = indexSong + 1
        if (nextSong >= numberSong) {
            nextSong = 0;
        }
    } else {
        nextSong = indexSong - 1
        if (nextSong < 0) {
            nextSong = numberSong - 1;
        }


    }
    ;
    let song = arr[nextSong];
    song.click();
}


var aud =document.getElementById("mainSong");
aud.onended = function() {
   console.log("hello");
    let listSong = document.querySelectorAll('.song-list .son');
    const arr = Array.from(listSong);
    let numberSong = arr.length;
// let index = arr.findIndex(checkSong)
    let indexSong = arr.findIndex(checkSong);
    let nextSong = indexSong + 1
        if (nextSong >= numberSong) {
            nextSong = 0;
        };
        
        let song = arr[nextSong];
    song.click();
    
};

