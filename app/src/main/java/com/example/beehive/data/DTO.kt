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
data class BuyStingDTO(
    var REQUIREMENT_PROJECT:String,
)
data class TopUpDTO(
    var nominal:Int
)
data class ChangePasswordDTO(
    var new:String,
    var confirm:String
)
data class ChangeProfileUserDTO(
    var name:String,
    var bio:String
)
data class DeclineTransactionStingDTO(
    var complain:String,
)
data class CompleteTransactionStingDTO(
    var rating:Int,
    var review:String,
)