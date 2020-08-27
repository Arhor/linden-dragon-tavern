package org.arhor.diploma.web.filter

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders.*
import org.springframework.http.HttpMethod.*
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class CustomCorsFilter : OncePerRequestFilter() {

    companion object {

        private const val MAX_AGE = 3600.toString()

        @JvmStatic
        private val ALLOWED_HEADERS = arrayOf(
            "authorization",
            "Content-Type",
            "Authorization",
            "credential",
            "x-csrf-token",
            "x-requested-with"
        ).joinToString(separator = ",")

        @JvmStatic
        private val ALLOWED_METHODS = arrayOf(
            GET,
            POST,
            PUT,
            PATCH,
            OPTIONS,
            DELETE
        ).joinToString(separator = ",")
    }

    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        next: FilterChain
    ) {
        res.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, req.getHeader(ORIGIN))
        res.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, true.toString())
        res.setHeader(ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_METHODS)
        res.setHeader(ACCESS_CONTROL_MAX_AGE, MAX_AGE)
        res.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS)

        if (OPTIONS.toString().equals(req.method, ignoreCase = true)) {
            res.status = HttpServletResponse.SC_OK
        } else {
            next.doFilter(req, res)
        }
    }
}
