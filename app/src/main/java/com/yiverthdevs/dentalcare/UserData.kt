package com.yiverthdevs.dentalcare

data class UserData(
    val userId: String,
    val name: String,
    val number: String,
    val gender: String
){
    // Constructor sin argumentos
    constructor() : this("", "", "", "")
}
