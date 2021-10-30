package com.yefimoyevhen.pecode.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.yefimoyevhen.pecode.databinding.ActivityTestBinding
import com.yefimoyevhen.pecode.viewmodel.TestViewModel
import dagger.hilt.android.AndroidEntryPoint

const val ARG_POSITION_KEY = "KEY"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestBinding
    private lateinit var viewModel: TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)
        val extras = intent.extras


        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusBar()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            val position = intent.extras?.getInt(ARG_POSITION_KEY, 0)
            if (position != null) {
                viewModel.updateLastPosition(position - 1)
            }
            Log.i("dev", "last pos $position")
        }
    }

    /*  override fun onStart() {
          super.onStart()
          val extras = intent.extras
          Log.i("dev", "activity onStart")
          if (extras != null) {
              Log.i("dev", "activity pos ${extras.getInt(ARG_POSITION_KEY)}")
              viewModel.updateLastPosition(extras.getInt(ARG_POSITION_KEY))
          }
      }*/

    private fun hideStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

}

