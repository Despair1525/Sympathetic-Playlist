use prj301_ai1503;

DROP TABLE IF EXISTS Song_HE150883;
create table Song_HE150883(
songId VARCHAR(20) PRIMARY KEY,
songName NVARCHAR(50),
artist NVARCHAR(50),
duration FLOAT,
genre VARCHAR(20) ,
linkSong varchar(100),
linkImage varchar(100),
songDate DATETIME NOT NULL DEFAULT GETUTCDATE(),
userId VARCHAR(20)
);
DROP TABLE IF EXISTS Genre_HE150883;

create table Genre_HE150883(
genreId VARCHAR(20) PRIMARY KEY,
genreName VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS User_HE150883;
create table User_HE150883(
userId VARCHAR(20) PRIMARY KEY,
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

insert into User_HE150883(userId,userPassword,userName,userGender,userEmail,userPhone,userAddress,userType) 
VALUES
('user1','123','nnl',0,'smileymask.hn@gmail.com','015082001','hn',1);
('admin','admin','pdm',1,'manhpdhe150883@fpt.edu.vn','0389621169','HN',0);

select * from User_HE150883;



create table Playlist_HE150883(
playlistId VARCHAR(20) PRIMARY KEY,
playlistName NVARCHAR(5),
playlistDate DATETIME DEFAULT CURRENT_TIMESTAMP ,
playListPublic BIT,
userId VARCHAR(20)
);

create table Contains_HE150883(
songId VARCHAR(20) NOT NULL,
playlistId VARCHAR(20) NOT NULL,
PRIMARY KEY(songId,playlistId)
);

create table Rate_HE150883(
userId VARCHAR(20) NOT NULL,
Id VARCHAR(20) NOT NULL,
TimeRate DATETIME NOT NULL DEFAULT GETUTCDATE(),
Score INT,
PRIMARY KEY(userId,Id,TimeRate)
);




Insert into Rate_HE150883(userId,Id,Score) VALUES ('admin','150883',5);

select * from Rate_HE150883;
select a.userId,a.Id,b.Score,a.LastTime from
(SELECT DISTINCT  userId, Id ,MAX(TimeRate)
OVER (PARTITION BY userId, Id) as 'LastTime'
FROM Rate_HE150883 ) a 
join Rate_HE150883 b
on a.userId = b.userId and a.Id = b.Id and a.LastTime = b.TimeRate;


select * from Contains_HE150883
select * from Playlist_HE150883;

alter table Song_HE150883
ADD CONSTRAINT FK_Song_Genre FOREIGN KEY(genre) REFERENCES Genre_HE150883(genreId)

alter table Contains_HE150883
ADD CONSTRAINT FK_Song_Playlist FOREIGN KEY(songId) REFERENCES Song_HE150883(songId)

alter table Contains_HE150883
ADD CONSTRAINT FK_Song_Playlist2 FOREIGN KEY(playlistId) REFERENCES Playlist_HE150883(playlistId)

alter table Rate_HE150883
ADD CONSTRAINT FK_Rate_Song FOREIGN KEY(Id) REFERENCES Song_HE150883(songId)

alter table Rate_HE150883
ADD CONSTRAINT FK_Rate_Playlist FOREIGN KEY(Id) REFERENCES Playlist_HE150883(playlistId)


alter table Rate_HE150883
ADD CONSTRAINT FK_Rate_User FOREIGN KEY(userId) REFERENCES User_HE150883(userId)



alter table Song_HE150883
ADD CONSTRAINT FK_Song_User FOREIGN KEY(userId) REFERENCES User_HE150883(userId)


alter table Playlist_HE150883
ADD CONSTRAINT FK_Playlist_User FOREIGN KEY(userId) REFERENCES User_HE150883(userId)



select * from User_HE150883;
select * from Song_HE150883;


select * from Genre_HE150883;
select * from Song_Genre_HE150883;

select * from Playlist_HE150883;
select * from Contains_HE150883

delete Contains_HE150883