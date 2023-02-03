package dev.awilder

import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@Client("https://cat-fact.herokuapp.com")
interface CatFactsClient {
    @Get("/facts/random")
    suspend fun getRandomFact(): CatFact
}

data class CatFact(
    val _id: String?,
    val _v: Int?,
    val user: String?,
    val text: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val sendDate: String?,
    val deleted: Boolean?,
    val source: String?,
    val type: String?,
    val status: Status?
)

data class Status(
    val verified: Boolean?,
    val feedback: String?,
    val sentCount: Int?
)

