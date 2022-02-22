package com.codesquard.kotlincardgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputLayout.*

class MainActivity : AppCompatActivity() {
    private lateinit var nextActivityBtn: Button
    private lateinit var nicknameText: TextInputEditText
    private lateinit var firstBtn: ImageButton
    private lateinit var secondBtn: ImageButton
    private lateinit var thirdBtn: ImageButton
    private lateinit var fourthBtn: ImageButton
    private lateinit var characterImage: ImageView
    private lateinit var selectedBtn: ImageButton
    var isBtnSelected = false
    var nickname = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nextActivityBtn = findViewById(R.id.btn_nextActivity)
        nicknameText = findViewById(R.id.nickname_text)
        firstBtn = findViewById(R.id.first_btn)
        secondBtn = findViewById(R.id.second_btn)
        thirdBtn = findViewById(R.id.third_btn)
        fourthBtn = findViewById(R.id.fourth_btn)
        characterImage = findViewById(R.id.character_image)
        setNicknameEditTextLayout()
        setNicknameEditText()
        checkNicknameText()
        clickBtnToShowImage()
        clickBtnToMoveCharActivity()
    }

    private fun clickBtnToMoveCharActivity() {
        nextActivityBtn.setOnClickListener {
            if (isBtnSelected) {
                val intent = Intent(this, CharacterActivity::class.java).apply {
                    putExtra("character", selectedBtn.id)
                    putExtra("nickname", nickname)
                }
                startActivity(intent)
            } else {
                Snackbar.make(it, "캐릭터를 선택하세요", LENGTH_SHORT).show()
            }
        }
    }

    private fun clickBtnToShowImage() {
        firstBtn.setOnClickListener {
            characterImage.setImageDrawable(getDrawable(R.drawable.first))
            isBtnSelected = true
            selectedBtn = firstBtn
        }
        secondBtn.setOnClickListener {
            characterImage.setImageDrawable(getDrawable(R.drawable.second))
            isBtnSelected = true
            selectedBtn = secondBtn
        }
        thirdBtn.setOnClickListener {
            characterImage.setImageDrawable(getDrawable(R.drawable.third))
            isBtnSelected = true
            selectedBtn = thirdBtn
        }
        fourthBtn.setOnClickListener {
            characterImage.setImageResource(R.drawable.fourth)
            isBtnSelected = true
            selectedBtn = fourthBtn
        }
    }

    private fun checkNicknameText() {
        nicknameText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if ((p0?.length ?: 0) == 0) nextActivityBtn.isEnabled = false
            }

            override fun afterTextChanged(p0: Editable?) {
                nextActivityBtn.isEnabled = true
                if ((p0?.contains("[A-Z|a-z]".toRegex())) == false) nextActivityBtn.isEnabled = false
                p0?.forEach {
                    if (!it.isLetterOrDigit()) nextActivityBtn.isEnabled = false
                } ?: throw IllegalArgumentException("잘못된 값입니다")
                nickname = p0.toString()
            }
        })
    }

    private fun setNicknameEditTextLayout() {
        val nicknameTextLayout: TextInputLayout = findViewById(R.id.nickname_text_layout)
        nicknameTextLayout.endIconMode = END_ICON_CLEAR_TEXT
        nicknameTextLayout.helperText = "최대 5글자, 공백/특수문자 제외, 최소 1글자는 알파벳 포함"
        nicknameTextLayout.isCounterEnabled = true
        nicknameTextLayout.counterMaxLength = 5
        nicknameTextLayout.hint = "닉네임"
    }

    private fun setNicknameEditText() {
        val nicknameText: TextInputEditText = findViewById(R.id.nickname_text)
        nicknameText.filters = arrayOf(InputFilter.LengthFilter(5))
        nicknameText.setSingleLine()
    }
}