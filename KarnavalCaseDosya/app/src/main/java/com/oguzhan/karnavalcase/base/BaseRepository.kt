package com.oguzhan.karnavalcase.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRepository {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): T? {

        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall.invoke()
                response
            } catch (e: Exception) {
                throw (e)
            }
        }
    }
}