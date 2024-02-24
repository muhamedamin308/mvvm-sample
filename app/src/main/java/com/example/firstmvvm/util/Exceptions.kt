package com.example.firstmvvm.util

import okio.IOException

class APIException(message: String): IOException(message)
class NoInternetException(message: String): IOException(message)