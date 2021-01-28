package com.artemissoftware.cerberusgate.util

class Constants {

    companion object{

        val PASSWORD = "1"
        val PASSWORD_SIZE = 6

        fun validatePassword(code: String): Boolean{

            var result = true

            Array(code.length) {code[it].toString()}.forEach {

                if(it.equals(PASSWORD) == false){
                    result = false;
                    return@forEach
                }
            }

            return  (code.length == PASSWORD_SIZE) && result


        }

    }

}