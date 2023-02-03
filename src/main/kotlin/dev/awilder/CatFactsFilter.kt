package dev.awilder

import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.annotation.Filter
import io.micronaut.http.filter.ClientFilterChain
import io.micronaut.http.filter.HttpClientFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.asPublisher
import kotlinx.coroutines.withContext
import org.reactivestreams.Publisher

@Filter(
    serviceId = [
        "https://cat-fact.herokuapp.com"
    ]
)
class CatFactsFilter : HttpClientFilter {
    override fun doFilter(request: MutableHttpRequest<*>, chain: ClientFilterChain): Publisher<out HttpResponse<*>> {
        return flow { emit("testString") }.flatMapMerge {
            chain.proceed(request.header("myTestHeader", it)).asFlow()
        }.asPublisher()
    }
}

suspend fun anotherValue(): String = withContext(Dispatchers.Default) { "string" }
