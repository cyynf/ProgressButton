package cpf.mvvm.demo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Author: cpf
 * Date: 2020/8/21
 * Email: cpf4263@gmail.com
 */
class MainViewModel : ViewModel() {

    private val model = MainModel()

    var progress = MutableLiveData<Int>()

    var btnText = MutableLiveData<String>()

    private var downloadTask: Job? = null

    init {
        btnText.value = "下载"
    }

    fun submit() {
        if (downloadTask == null) {
            downloadTask = viewModelScope.launch {
                model.download({
                    progress.value = it
                }, {
                    btnText.value = "重新下载"
                    downloadTask = null
                })
            }
        } else {
            downloadTask?.cancel()
            downloadTask = null
            btnText.value = "下载"
        }
    }

}