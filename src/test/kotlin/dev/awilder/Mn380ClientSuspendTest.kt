package dev.awilder

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration.Companion.seconds

@MicronautTest
class Mn380ClientSuspendTest(
    private val catFactsClient: CatFactsClient
) : StringSpec({

    // unrelated timeout bugs
    // kotest timeout ignored
    "test catFactsClient returns a fact".config(timeout = 7.seconds) {
        // and this timeout is ignored
        withTimeout(5000) {
            catFactsClient.getRandomFact().shouldNotBeNull()
        }
    }
})
