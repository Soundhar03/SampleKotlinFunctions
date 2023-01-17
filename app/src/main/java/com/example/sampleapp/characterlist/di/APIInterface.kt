package com.example.sampleapp.characterlist.di

import com.example.sampleapp.characterlist.model.Characters
import io.reactivex.Observable
import retrofit2.http.GET

interface APIInterface {
    @GET("Characters")
    fun getCharacters() : Observable<List<Characters>>
}
