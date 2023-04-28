package com.hakancevik.hiltprojectartbook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hakancevik.hiltprojectartbook.model.ImageResponse
import com.hakancevik.hiltprojectartbook.repo.ArtRepositoryInterface
import com.hakancevik.hiltprojectartbook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageApiViewModel @Inject constructor(
    private val repository: ArtRepositoryInterface
) : ViewModel() {

    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() = selectedImage


    fun setSelectedImage(url: String) {
        selectedImage.postValue(url)
        //selectedImage.value = url
    }


    fun searchForImage(searchString: String) {
        if (searchString.isEmpty()) {
            return
        }

        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }

    }


}