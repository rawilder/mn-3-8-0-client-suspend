package dev.awilder

import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.annotation.Filter
import io.micronaut.http.filter.ClientFilterChain
import io.micronaut.http.filter.HttpClientFilter
import kotlinx.coroutines.Dispatchers
import org.reactivestreams.Publisher
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.asPublisher
import kotlinx.coroutines.reactive.publish
import kotlinx.coroutines.withContext
import reactor.core.publisher.Flux

@Filter(
    serviceId = [
        "https://cat-fact.herokuapp.com"
    ]
)
class CatFactsFilter : HttpClientFilter {
    override fun doFilter(request: MutableHttpRequest<*>, chain: ClientFilterChain): Publisher<out HttpResponse<*>> {
        return Flux.from(publish { send(anotherValue()) }).flatMap {
            chain.proceed(request)
        }
    }
}

suspend fun anotherValue(): String = withContext(Dispatchers.Default) { "string" }
