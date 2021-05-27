package me.jiho.fruitreactive.commons

class ApiResult<T: Any> private constructor(val success: Boolean, val response: T? = null, val error: ApiError? = null) {
    companion object {
        @JvmStatic
        fun<T: Any> success(response: T?): ApiResult<T> = ApiResult(success = true, response = response)
    }

    override fun toString(): String {
        return "ApiResult(success=$success, response=$response, error=$error)"
    }


}