package com.andreesperanca.deolhonobus.util


inline fun <T> apiCall(action: () -> Resource<T>): Resource<T> {
    return  try {
        action()
    } catch (e: Exception) {
        Resource.Error(e.message ?: "")
    }
}