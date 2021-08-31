package com.example.myapplication.friend


import com.google.gson.annotations.SerializedName

// 친구 삭제
data class DeleteModel(
    var code : Int,
    var message: String
)
class CheckDeleteModel(
    val checkRoomList3 : List<DeleteModel>
)


// 친구 검색
data class GetModel(
    var userId : Long,
    var username: String,
    var email: String
)
class CheckGetModel(
    @SerializedName("userList")
    val checkRoomList : List<GetModel>
)

// 친구 목록
data class GetModel2(
    var userId : Long,
    var username: String,
    var achievementRate: Double,
    var accumulatedTime: String,
    var studyStatus: Boolean
)
class CheckGetModel2(
    @SerializedName("followList")
    val checkRoomList2 : List<GetModel2>
)



// 친구 요청
data class PostModel(
    var code : Int,
    var message: String
)
class CheckPostModel(
    val checkRoomList4 : List<PostModel>
)