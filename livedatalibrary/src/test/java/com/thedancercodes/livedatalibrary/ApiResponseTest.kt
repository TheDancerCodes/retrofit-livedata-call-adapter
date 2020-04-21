package com.thedancercodes.livedatalibrary

import com.thedancercodes.livedatalibrary.ApiResponse.*
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class ApiResponseTest {

    @Test
    fun exception() {
        val exception = Exception("foo")
        val (errorMessage) = ApiResponse.create<String>(exception)
        assertThat<String>(errorMessage, `is`("foo"))
    }

    @Test
    fun success_200() {
        val apiResponse: ApiSuccessResponse<String> = ApiResponse
            .create<String>(Response.success("foo")) as ApiSuccessResponse<String>
        assertThat<String>(apiResponse.body, `is`("foo"))
    }

    @Test
    fun error_400() {
        val errorResponse = Response.error<String>(
            400,
            ResponseBody.create(MediaType.parse("application/txt"), "400 Bad Request ")
        )
        val (errorMessage) = ApiResponse.create<String>(errorResponse) as ApiErrorResponse<String>
        assertThat<String>(errorMessage, `is`("400 Bad Request "))
    }

    @Test
    fun error_401() {
        val errorResponse = Response.error<String>(
            401,
            ResponseBody.create(MediaType.parse("application/txt"), "401 Unauthorized. Token may be invalid.")
        )
        val (errorMessage) = ApiResponse.create<String>(errorResponse) as ApiErrorResponse<String>
        assertThat<String>(errorMessage, `is`("401 Unauthorized. Token may be invalid."))
    }

}