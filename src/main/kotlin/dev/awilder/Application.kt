package dev.awilder

import io.micronaut.runtime.Micronaut.run
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.asPublisher
import kotlinx.coroutines.reactive.publish
import reactor.core.publisher.Flux
import kotlin.system.exitProcess

suspend fun main(args: Array<String>) {

    flow<Int> {
        throw Exception("test")
    }.asPublisher()

    publish { send("string1") }
        .subscribe(subscriber)

    flow { emit("string2") }
        .take(1)
        .asPublisher().subscribe(subscriber)

    flow { emit("string3") }
        .onCompletion { println("done") }
        .collect {
            println(it)
        }

    Flux.from(publish { send("string4") })
        .subscribe(subscriber)

    Flowable.fromPublisher<String>(publish { send("string5") })
        .subscribe(subscriber)

    val app = run(*args)
    app.getBean(CatFactsClient::class.java).getRandomFact().also(::println)
    println("done")
    exitProcess(0)
}

val subscriber = object : org.reactivestreams.Subscriber<String> {
    var subscription: org.reactivestreams.Subscription? = null
    override fun onSubscribe(s: org.reactivestreams.Subscription?) {
        subscription = s
        subscription?.request(1)
    }

    override fun onNext(t: String?) {
        println(t)
    }

    override fun onError(t: Throwable?) {
        t?.printStackTrace()
    }

    override fun onComplete() {
        println("done")
    }
}

