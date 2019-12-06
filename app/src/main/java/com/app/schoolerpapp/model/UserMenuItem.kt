package com.app.schoolerpapp.model

class UserMenuItem {
    var menuName: String = ""
    var seleted: Boolean = false
    var icon: Int = 0
    var visible: Boolean=false
    var subCategory=ArrayList<UserSubMenuItem>()
}