package com.example.beehive.data

class DTO {
}

data class UserLoginDTO(
    var email:String,
    var password:String,
)

data class UserRegisterDTO(
    var email:String,
    var password:String,
    var name:String,
    var role:Int,
    var birthday:String,
)
data class CreateLelangStingDTO(
    var title:String,
    var requirement:String,
    var price:Int,
    var category:Int
)
data class TopUpDTO(
    var nominal:Int
)