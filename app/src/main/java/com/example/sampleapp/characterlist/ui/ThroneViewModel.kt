package com.example.sampleapp.characterlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleapp.characterlist.di.APIInterface
import com.example.sampleapp.characterlist.di.DaggerViewModelInjector
import com.example.sampleapp.characterlist.di.NetworkModule
import com.example.sampleapp.characterlist.di.ViewModelInjector
import com.example.sampleapp.characterlist.model.CharacterDao
import com.example.sampleapp.characterlist.model.CharacterDatabase
import com.example.sampleapp.characterlist.model.Characters
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ThroneViewModel : ViewModel() {

    @Inject
    lateinit var homeApi: APIInterface

    @Inject
    lateinit var characterDao: CharacterDao

    private lateinit var disposable: Disposable
    private var characterLiveData = MutableLiveData<List<Characters>>()
    var errorMessage: MutableLiveData<String> = MutableLiveData()

    init {
        val injector: ViewModelInjector = DaggerViewModelInjector.builder()
            .networkModule(NetworkModule())
            .build()

        injector.inject(this)
        getPopularMovies()

    }

    private fun getPopularMovies() {
        disposable = homeApi.getCharacters().subscribeOn(
            Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
//                    result.forEach {
//                        insertRecord(it)
//                    }
//                    deleteAllRecord()
                    higherOrdFunc { characterDao.getAll() }
//                    getAllRecords()
//                    getFilm()
//                    characterLiveData.postValue(result)
                },
                {
                    errorMessage.value = it.toString()
                }

            )

    }

    // Implementing HigherOrderFunction
    private fun higherOrdFunc(getAll:()-> List<Characters>){
        var list = getAll()
        characterLiveData.value = list
    }

    fun observeCharacterLiveData(): LiveData<List<Characters>> {
        return characterLiveData
    }

    private fun getAllRecords() {
        val list = characterDao.getAll()
        characterLiveData.value = list
    }


    fun getFilm() {
        val list = characterDao.getFilm()
        characterLiveData.value = list
    }

    fun getUserDao(characterDatabase: CharacterDatabase): CharacterDao {
        return characterDatabase.getCharacterDAO()
    }

}

// Extension Methode
fun ThroneViewModel.insertRecord(characters: Characters) {
    characterDao.insert(characters)
}

// Extension Methode
fun ThroneViewModel.deleteAllRecord() {
    characterDao.deleteAll()
}
