package com.example.fitnessapp

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.data.GymExercise
import com.example.fitnessapp.network.RetrofitInstance
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.http2.Http2Reader
import retrofit2.HttpException
import java.io.IOException

class SharedViewModel: ViewModel() {
    private var _data =  MutableLiveData<List<GymExercise>>()
    val data = _data

    fun updateData(data: List<GymExercise>){
        _data.postValue(data)
    }


    fun fetchData(){

        viewModelScope.launch(Dispatchers.IO){
            try {
//                _data.postValue(RetrofitInstance.api.getExercise())
                Http2Reader.logger.info { " data size is  ${data.value?.size.toString()} " }
            } catch (e: IOException) {
                // Handle network-related errors
                Http2Reader.logger.warning("Network error: ${e.message}")
            } catch (e: HttpException) {
                // Handle HTTP errors
                Http2Reader.logger.warning("HTTP error: ${e.code()}: ${e.message()}")
            } catch (e: Exception) {
                // Handle other types of exceptions
                Http2Reader.logger.warning("Error: ${e.message}")
            }

        }

    }

    fun getExerciseByName(name: String): GymExercise? {
        return _data.value?.find { it.name == name }
    }


}