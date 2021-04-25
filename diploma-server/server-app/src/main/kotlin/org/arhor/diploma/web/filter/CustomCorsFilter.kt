//package org.arhor.diploma.web.filter
//
//import org.arhor.diploma.CsrfUtils
//import org.springframework.http.HttpHeaders.*
//import org.springframework.http.HttpMethod.*
//import org.springframework.web.filter.OncePerRequestFilter
//import javax.servlet.FilterChain
//import javax.servlet.http.HttpServletRequest
//import javax.servlet.http.HttpServletResponse
//
//private const val MAX_AGE = 3600.toString()
//
//private val ALLOWED_HEADERS = arrayOf(
//    "authorization",
//    "Content-Type",
//    "Authorization",
//    "credential",
//    CsrfUtils.CSRF_HEADER_NAME,
//    "x-requested-with"
//).joinToString(separator = ",")
//
//private val ALLOWED_METHODS = arrayOf(
//    GET,
//    POST,
//    PUT,
//    PATCH,
//    OPTIONS,
//    DELETE
//).joinToString(separator = ",")
//
//class CustomCorsFilter : OncePerRequestFilter() {
//
//    override fun doFilterInternal(
//        req: HttpServletRequest,
//        res: HttpServletResponse,
//        next: FilterChain
//    ) {
//        res.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, req.getHeader(ORIGIN))
//        res.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, true.toString())
//        res.setHeader(ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_METHODS)
//        res.setHeader(ACCESS_CONTROL_MAX_AGE, MAX_AGE)
//        res.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS)
//
//        if (OPTIONS.toString().equals(req.method, ignoreCase = true)) {
//            res.status = HttpServletResponse.SC_OK
//        } else {
//            next.doFilter(req, res)
//        }
//    }
//}
