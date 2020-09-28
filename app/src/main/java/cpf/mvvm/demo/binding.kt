package cpf.mvvm.demo

import androidx.activity.ComponentActivity
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Author: cpf
 * Date: 2020/9/7
 * Email: cpf4263@gmail.com
 */
@MainThread
inline fun <reified T : ViewDataBinding> ComponentActivity.dataBindings(
    @LayoutRes layoutId: Int
): Lazy<T> = object : Lazy<T> {

    private var binding: T? = null

    override val value: T
        get() = binding ?: load()

    override fun isInitialized(): Boolean = binding != null

    private fun ComponentActivity.load(): T {
        return DataBindingUtil.setContentView<T>(this, layoutId).also {
            it.lifecycleOwner = this
            binding = it
        }
    }
}