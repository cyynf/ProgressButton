package cpf.mvvm.demo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import cpf.mvvm.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val dataBinding: ActivityMainBinding by dataBindings(R.layout.activity_main)

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.viewModel = viewModel
        viewModel.progress.observe(this) {
            println(it)
            dataBinding.progressButton.setProgress(it)
        }
    }
}