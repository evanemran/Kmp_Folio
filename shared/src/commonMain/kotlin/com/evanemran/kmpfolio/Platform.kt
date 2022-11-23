package com.evanemran.kmpfolio

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform