package com.example.mobileappssuitmedia.presentation.login

import androidx.lifecycle.ViewModel
import com.example.mobileappssuitmedia.utils.PaliandromeChecker
import com.example.mobileappssuitmedia.utils.ResourceState

class LoginViewModel : ViewModel() {

    fun isPaliandromTrue(words: String): ResourceState<Boolean> {
        return if (PaliandromeChecker.paliandromeCheck(words)) {
            ResourceState.Success(data = true, "$words is Paliandrome")
        } else {
            ResourceState.Error(data = false, "$words is not Paliandrome")
        }
    }
}