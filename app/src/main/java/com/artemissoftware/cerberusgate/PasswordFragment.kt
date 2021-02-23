package com.artemissoftware.cerberusgate

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.artemissoftware.cerberusgate.databinding.FragmentPasswordBinding
import com.artemissoftware.cerberusgate.util.Constants
import com.artemissoftware.cerberusgate.util.SoftKeyboardUtils
import com.google.android.material.textfield.TextInputEditText
import com.keijumt.passwordview.ActionListener
import com.keijumt.passwordview.PasswordView


class PasswordFragment : Fragment(), ActionListener,TextWatcher {


    private lateinit var password: PasswordView
    private lateinit var tokenInput: TextInputEditText

    private var _binding: FragmentPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPasswordBinding.inflate(inflater, container, false)
        val fragmentView = binding.root

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

//        val color_int = ContextCompat.getColor(context!!, R.color.colorError)
//        val colorName = Color.BLACK
//        binding.outline = ContextCompat.getColor(context, R.color.colorError);

        return fragmentView
    }

    override fun onCompleteInput(inputText: String) {
        inputText.let {

            if(Constants.validatePassword(it)){

                password.correctAnimation()
                openFragment(SuccessPopupFragment())
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


    fun openFragment(fragment: Fragment) {
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}