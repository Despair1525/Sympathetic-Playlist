create database prj301_ai1503;
use prj301_ai1503;


/* Create Table  */

create table User_HE150883(
userId VARCHAR(50) PRIMARY KEY,
userPassword VARCHAR(50),
userName nvarchar(50),
userGender BIT,
userEmail nvarchar(50),
userActive BIT default 0 ,
userPhone varchar(12),
userAddress NVARCHAR(50),
userType INT default 1,
userEnroll DATE default GETDATE()
);


create table Song_HE150883(
songID VARCHAR(50) PRIMARY KEY,
songName NVARCHAR(50),
artist NVARCHAR(50),
duration FLOAT,
genre VARCHAR(20) ,
linkSong varchar(100),
linkImage varchar(100),
songDate DATETIME NOT NULL DEFAULT GETUTCDATE(),
userId VARCHAR(50)
);
create table Genre_HE150883(
id VARCHAR(50) PRIMARY KEY,
nameGenre VARCHAR(50) NOT NULL
);

create table Song_Genre_HE150883(
songId VARCHAR(50) NOT NULL,
genreId VARCHAR(50) NOT NULL,
PRIMARY KEY(songId,genreId)
);


create table Playlist_HE150883(
playlistId VARCHAR(50) PRIMARY KEY,
playlistName NVARCHAR(20),
playlistDate DATETIME DEFAULT CURRENT_TIMESTAMP ,
playListPublic BIT,
userId VARCHAR(50)
);

create table Contains_HE150883(
songId VARCHAR(50) NOT NULL,
playlistId VARCHAR(50) NOT NULL,
PRIMARY KEY(songId,playlistId)
);

create table Rate_HE150883(
userId VARCHAR(50) NOT NULL,
Id VARCHAR(50) NOT NULL,
TimeRate DATETIME NOT NULL DEFAULT GETUTCDATE(),
Score INT,
PRIMARY KEY(userId,Id,TimeRate)
);

/* Create Realational  */
AlTER TABLE Song_Genre_HE150883
ADD FOREIGN KEY (songId) REFERENCES Song_HE150883(songID)
AlTER TABLE Song_Genre_HE150883
ADD FOREIGN KEY (genreId) REFERENCES Genre_HE150883(id)

AlTER TABLE Song_HE150883
ADD FOREIGN KEY (userId) REFERENCES User_HE150883(userId)

AlTER TABLE Contains_HE150883
ADD FOREIGN KEY (songId) REFERENCES Song_HE150883(songID)
AlTER TABLE Contains_HE150883
ADD FOREIGN KEY (playlistId) REFERENCES Playlist_HE150883(playlistId)

AlTER TABLE Playlist_HE150883
ADD FOREIGN KEY (userId) REFERENCES User_HE150883(userId)

AlTER TABLE Rate_HE150883
ADD FOREIGN KEY (userId) REFERENCES User_HE150883(userId)
AlTER TABLE Rate_HE150883
ADD FOREIGN KEY (Id) REFERENCES Song_HE150883(songID)


/* INSERT DATA */

insert into User_HE150883(userId,userPassword,userName,userGender,userEmail,userPhone,userAddress,userType) 
VALUES
('admin','admin','pdm',1,'manhpdhe150883@fpt.edu.vn','0389621169','HN',0);

insert into Genre_HE150883
values 
('0','Rock'),
('1','Latin'),
('10','Lofi'),
('11','Other'),
('2','Country'),
('3','Rap'),
('4','Pop'),
('5','Electronica'),
('6','Jazz'),
('7','R&B'),
('8','Reggae'),
('9','Gospel');

insert into Song_HE150883 (songID,songName,artist,linkSong,linkImage,userId)
values
('admin-1','glass','nekoi','musics/glass.mp3','images/glass.jpg','admin'),
('admin-2','jojo','maskly','musics/jojo.mp3','images/jojo.jpg','admin'),
('admin-3','cold','NEFFIX','musics/music-1.mp3','images/album-1.png','admin'),
('admin-4','funny sound'	,'MRc','musics/music-2.mp3','images/album-2.png','admin'),
('admin-5','holiday','MRc','musics/music-3.mp3','images/album-3.png','admin'),
('admin-6','Hurt','Nine Inch Nails','musics/hurt.mp3','images/hurt.jpg','admin'),
('admin-7',	'Y2Mate','Seven', 'musics/Y2Mate.is - Scissor Seven Chill theme Extended-eISQnxs-Kww-160k-1631720732420.mp3','images/53298670_294577467902233_462481537267400704_o.jpg','admin'),
('admin-8','Dance of The Violins','F-777', 'musics/F-777 - Dance of The Violins.mp3','images/violin.jpg','admin'),
('admin-9','let_you_go'	,'illenium','musics/illenium_let_you_go_lyrics_lyric_video_ft_ember_island_-2549070867355723212.mp3','images/life.jpg','admin'),
('admin-10','Hollow Purple Theme' ,'Kai','musics/Jujutsu Kaisen - Gojo Satoru Hollow Purple Theme _ EPIC VERSION (Besto Quality).mp3','images/185618434001202.jpg','admin'),
('admin-11','No Friends' ,'some one','musics/Nightcore - No Friends (Lyrics).mp3','images/74230344_p0_master1200.jpg','admin'),
('admin-12','can you hear me','Munn','musics/Munn - can you hear me_ (Lyrics _ Lyric Video).mp3','images/stay.jpg','admin'),
('admin-13','bring back the summer','Rain man','musics/Rain Man feat. Oly - Bring Back The Summer (Not Your Dope Remix).mp3','images/someone.jpg','admin'),
('admin-14','Addicted','Hotel' ,'musics/Addicted.mp3','images/someOne.png','admin' ),
('admin-15','Blinding Lights' ,'The Weeknd','musics/Blinding Lights - The Weeknd.mp3','images/350x350.jpg','admin')




/* Show Database */
select * from User_HE150883;
select * from Song_HE150883 ;
select * from Genre_HE150883;
select * from Song_Genre_HE150883;
select * from Playlist_HE150883;
select * from Contains_HE150883

/* Insert Update */

