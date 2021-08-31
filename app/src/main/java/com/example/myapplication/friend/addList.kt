package com.example.myapplication.friend

//친구 검색
class addlist(val name: String, val email: String){

}
class friendaddList() {
    val FriendaddList = ArrayList<addlist>()

    fun addFriend(addlist: addlist) {
        FriendaddList.add(addlist)
    }
}

//친구 목록 조회
class friendview(val name: String, val achievementRate:Double, val accumulatedTime: String){

}
class friendListView() {
    val FriendListview = ArrayList<friendview>()

    fun viewFriend(friendview: friendview) {
        FriendListview.add(friendview)
    }
}