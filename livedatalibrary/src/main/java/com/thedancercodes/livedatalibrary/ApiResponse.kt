package com.thedancercodes.livedatalibrary

import android.util.Log
import retrofit2.Response

/**
 * Copied from Architecture components google sample:
 * https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/api/ApiResponse.kt
 */
@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<T> {
    /**
     * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
     */
    class ApiEmptyResponse<T> : ApiResponse<T>()

    data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>() {}

    data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()

    companion object {
        private val TAG: String = "AppDebug"

        // Returns an error
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(
                error.message ?: "unknown error"
            )
        }

        // Returns a response
        fun <T> create(response: Response<T>): ApiResponse<T> {

            Log.d(TAG, "GenericApiResponse: response: ${response}")
            Log.d(TAG, "GenericApiResponse: raw: ${response.raw()}")
            Log.d(TAG, "GenericApiResponse: headers: ${response.headers()}")
            Log.d(TAG, "GenericApiResponse: message: ${response.message()}")

            if(response.isSuccessful){
                val body = response.body()
                return if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else if(response.code() == 401){
                    ApiErrorResponse(
                        "401 Unauthorized. Token may be invalid."
                    )
                } else {
                    ApiSuccessResponse(
                        body = body
                    )
                }
            }
            else{
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                return ApiErrorResponse(
                    errorMsg ?: "unknown error"
                )
            }
        }
    }
}