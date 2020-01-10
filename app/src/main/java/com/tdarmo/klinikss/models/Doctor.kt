package com.tdarmo.klinikss.models

class Doctor (var id: String, var Name: String, var Clinic: String, var Email: String, var Password: String){
    constructor(): this("", "", "", "", ""){

    }
}