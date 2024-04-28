package net.osable.core

import jakarta.servlet.RequestDispatcher
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus

data class Error(val code: Int, val info: String)

fun HttpServletRequest.getErrorCode() = getAttribute(RequestDispatcher.ERROR_STATUS_CODE) as Int

fun getHttpStatusMessage(code: Int) = HttpStatus.valueOf(code).reasonPhrase