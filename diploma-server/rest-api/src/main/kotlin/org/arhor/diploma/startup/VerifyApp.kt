package org.arhor.diploma.startup

import org.arhor.diploma.commons.ActionResult.Failure
import org.arhor.diploma.commons.ActionResult.Success
import org.arhor.diploma.commons.Priority
import org.arhor.diploma.commons.StartupTask
import org.arhor.diploma.commons.Verifiable
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.lang.invoke.MethodHandles
import java.text.DecimalFormat
import kotlin.system.exitProcess

@Component
class VerifyApp(private val verifiers: List<Verifiable>) : StartupTask {

    init {
        checkVerifiers()
    }

    override val priority = Priority.HIGH

    override fun execute() {
        log.info("Starting app verification")
        log.info("Found [${verifiers.size}] verifiers to run")

        val width = DecimalFormat("0".repeat(verifiers.size.toString().length))

        val success = verifiers.sorted().mapIndexed { i, verifier ->
            val result = verifier.verify()
            val verifierNum = width.format(i)
            when (result) {
                is Success -> log.info("{}: [SUCCESS] {}", verifierNum, result.value)
                is Failure -> log.error("{}: [FAILURE] {}", verifierNum, result.error.message)
            }
            result
        }.all { it.isSuccess }

        if (!success) {
            log.error("An error occurred during startup verification")
            exitProcess(0)
        }

        log.info("App verification finished successfully")
    }

    private fun checkVerifiers() {
        verifiers
            .groupBy { it.priority }
            .filterKeys { it == Priority.FIRST || it == Priority.LAST }
            .filterValues { it.size > 1 }
            .forEach { (priority, samePriorityVerifiers) ->
                log.warn(
                    "There are several startup verifiers with priority `{}`: {}",
                    priority,
                    samePriorityVerifiers.map { it::class.simpleName }
                )
            }
    }

    companion object {
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
    }
}