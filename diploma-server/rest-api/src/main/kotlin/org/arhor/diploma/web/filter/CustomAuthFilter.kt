package org.arhor.diploma.web.filter

import org.arhor.diploma.web.security.TokenProvider
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.lang.invoke.MethodHandles
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthFilter(
    private val tokenProvider: TokenProvider,
    private val userDetailsService: UserDetailsService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        next: FilterChain
    ) {
        try {
            req.getHeader(tokenProvider.authHeaderName)?.let {
                attemptAuthenticate(it, req)
            }
        } catch (e: Exception) {
            log.error("Cannot set user authentication -> Message: {}", e.message)
        }
        next.doFilter(req, res)
    }

    private fun attemptAuthenticate(authHeader: String, req: HttpServletRequest) {
        val token = tokenProvider.parse(authHeader)

        if (tokenProvider.validate(token)) {
            tokenProvider.parseUsername(token)
                ?.let(userDetailsService::loadUserByUsername)
                ?.let { user -> UsernamePasswordAuthenticationToken(user, token, user.authorities) }
                ?.let { auth ->
                    auth.details = WebAuthenticationDetailsSource().buildDetails(req)
                    SecurityContextHolder.getContext().authentication = auth
                }
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
    }
}
