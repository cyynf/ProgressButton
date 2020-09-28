package cpf.mvvm.demo

import kotlinx.coroutines.delay

/**
 * Author: cpf
 * Date: 2020/9/4
 * Email: cpf4263@gmail.com
 */
class MainModel {

    suspend fun download(progress: (Int) -> Unit, finish: () -> Unit) {
        // 模拟下载
        val fileSize = 128
        var currSize = 0
        while (currSize < fileSize) {
            delay(50)
            currSize++
            progress(currSize * 100 / fileSize)
        }
        finish()
    }

}