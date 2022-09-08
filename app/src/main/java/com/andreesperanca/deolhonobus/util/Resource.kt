package com.andreesperanca.deolhonobus.util

sealed class Resource <T> (var data: T? = null, val message: String? = null) {
    class Success<T>(data: T): Resource<T>(data)
    class Loading<T>(data: T? = null): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data,message)
}