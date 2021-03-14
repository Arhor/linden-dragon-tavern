package org.arhor.diploma.web.security

import org.arhor.diploma.util.Common
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

/**
 * Implementation of [AuditorAware] based on Spring Security.
 */
@Component
class SpringSecurityAuditorAware : AuditorAware<String> {

    override fun getCurrentAuditor(): Optional<String> {
        val currentUserLogin =
            SecurityContextHolder.getContext().authentication
                ?.principal
                ?.let {
                    when (it) {
                        is UserDetails -> it.username
                        is String -> it
                        else -> null
                    }
                }

        return Optional.of(currentUserLogin ?: Common.SYSTEM_ACCOUNT)
    }
}