package com.evanemran.kmpfolio

class Greeting {
    private val platform: Platform = getPlatform()

    fun greeting(): String {
        return "Hello, This is running on ${platform.name}!"
    }
}