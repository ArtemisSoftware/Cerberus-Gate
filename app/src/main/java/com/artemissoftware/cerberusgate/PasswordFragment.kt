package com.artemissoftware.cerberusgate

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.artemissoftware.cerberusgate.util.Constants
import com.artemissoftware.cerberusgate.util.SoftKeyboardUtils
import com.google.android.material.textfield.TextInputEditText
import com.keijumt.passwordview.ActionListener
import com.keijumt.passwordview.PasswordView


class PasswordFragment : Fragment(), ActionListener,TextWatcher {


    private lateinit var password: PasswordView
    private lateinit var tokenInput: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var fragmentView = inflater.inflate(R.layout.fragment_password, container, false)

        password = fragmentView.findViewById(R.id.passwordView)
        tokenInput = fragmentView.findViewById(R.id.token_input)

        password.setListener(this)


        password.setOnClickListener {
            tokenInput.clearFocus()
            tokenInput.requestFocus()
        }

        tokenInput.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                SoftKeyboardUtils.openSoftKeyboard(activity, v)
            }
        }

        tokenInput.addTextChangedListener(this)


        return fragmentView
    }

    override fun onCompleteInput(inputText: String) {
        inputText.let {

            if(Constants.validatePassword(it)){

                password.correctAnimation()
                Toast.makeText(requireContext(), inputText, Toast.LENGTH_SHORT).show();
            }
            else{

                password.incorrectAnimation()
                password.reset()
                tokenInput.setText("")

                Toast.makeText(requireContext(), "Erro: " + inputText, Toast.LENGTH_SHORT).show();
            }

        }
    }

    override fun onEndJudgeAnimation() {

    }



    override fun afterTextChanged(code: Editable?) {
        if (code?.length == Constants.PASSWORD_SIZE) {
            SoftKeyboardUtils.closeSoftKeyboard(activity)
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        if (count == 1) {
            var char : String = s!!.get(start + before).toString()
            password.appendInputText(char)
        } else {
            password.removeInputText()
        }
    }


}