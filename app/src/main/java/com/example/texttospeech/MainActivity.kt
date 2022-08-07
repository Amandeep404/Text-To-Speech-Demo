package com.example.texttospeech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import com.example.texttospeech.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts:TextToSpeech?=null
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        tts = TextToSpeech(this, this)
        binding?.button?.setOnClickListener{
            if (binding?.editText?.text!!.isEmpty()){
                Toast.makeText(this, "enter a text to speak", Toast.LENGTH_LONG).show()
            }else{
                    speakOut(binding?.editText?.text.toString())
                Toast.makeText(this, "text to speech worked", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.ENGLISH)

            if(result == TextToSpeech.LANG_MISSING_DATA || result== TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this, "language missing or not supported", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "text to speech failed", Toast.LENGTH_SHORT).show()
        }

    }

    private fun speakOut(text:String){
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        super.onDestroy()

        if (tts!=null){
            tts?.stop()
            tts?.shutdown()
        }
        binding= null
    }
}