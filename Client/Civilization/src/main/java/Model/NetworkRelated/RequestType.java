package Model.NetworkRelated;

public enum RequestType {
    Register,
    inviteAcceptation, //params 1:acceptBool 2:senderUsername 3:inviteeUsername
    startGame,
    Login, Logout, Users, ChangeNickname, ChangePassword, NextProfilePic, PrevProfilePic, ChoosePic, sendInvite,registerReaderSocket
    , Friendship, ShowFriendshipRequests, AcceptFriendship, RejectFriendship, UpdateGame , peaceRequest,
    demandRequest,declareWar, ForSavingChats;
}
