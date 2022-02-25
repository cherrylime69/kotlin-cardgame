package com.codesquard.kotlincardgame

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class CharacterFragment : Fragment() {
    private lateinit var charImageView: ImageView
    private lateinit var charText: TextView
    private lateinit var charBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_character, container, false)
        charImageView = view.findViewById(R.id.imageView_character)
        charText = view.findViewById(R.id.text_character)
        charBtn = view.findViewById(R.id.button_character)
        clickBtnToWeb()
        setTextAndImage()
        return view
    }

    private fun clickBtnToWeb() {
        charBtn.setOnClickListener {
            val webpage: Uri = Uri.parse("https://codesquad.kr/")
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            startActivity(intent)
        }
    }

    private fun setTextAndImage() {
        val nickname = arguments?.getString("name")
        val image = arguments?.getByteArray("char")
        val bitmap = image?.let { BitmapFactory.decodeByteArray(image, 0, it.size) }
        charText.text = nickname
        charImageView.setImageBitmap(bitmap)
    }

}