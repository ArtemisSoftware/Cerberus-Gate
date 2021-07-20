package com.artemissoftware.cerberusgate

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.artemissoftware.cerberusgate.databinding.FragmentMatrizPasswordBinding
import com.artemissoftware.cerberusgate.databinding.FragmentPasswordBinding
import com.artemissoftware.cerberusgate.util.Constants
import com.artemissoftware.cerberusgate.util.SoftKeyboardUtils
import com.google.android.material.textfield.TextInputEditText
import com.keijumt.passwordview.ActionListener
import com.keijumt.passwordview.PasswordView

class MatrizPasswordFragment : Fragment() {


    private var passwords = mutableListOf<PasswordView>()
    private var tokenInputs = mutableListOf<TextInputEditText>()

    private lateinit var password1: PasswordView
    private lateinit var tokenInput1: TextInputEditText

    private lateinit var password2: PasswordView
    private lateinit var tokenInput2: TextInputEditText

    private lateinit var password3: PasswordView
    private lateinit var tokenInput3: TextInputEditText

    private lateinit var password4: PasswordView

    private var _binding: FragmentMatrizPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMatrizPasswordBinding.inflate(inflater, container, false)
        val fragmentView = binding.root

        password1 = fragmentView.findViewById(R.id.passwordView1)
        password2 = fragmentView.findViewById(R.id.passwordView2)
        password3 = fragmentView.findViewById(R.id.passwordView3)

        passwords.add(password1)
        passwords.add(password2)
        passwords.add(password3)

        tokenInput1 = fragmentView.findViewById(R.id.token_input_1)
        tokenInput2 = fragmentView.findViewById(R.id.token_input_2)
        tokenInput3 = fragmentView.findViewById(R.id.token_input_3)

        tokenInputs.add(tokenInput1)
        tokenInputs.add(tokenInput2)
        tokenInputs.add(tokenInput3)




        //--passwords.forEach { item -> item.onClick(tokenInput1) }

        for (i in passwords.indices) {
            passwords[i].onClick(tokenInputs[i])
            tokenInputs[i].onChange(passwordView = passwords[i], textView =  tokenInputs[i])
            tokenInputs[i].onKeyFocusListener()
        }

//        (requireActivity() as MainActivity).updateStatusBarColor(R.color.colorSuccess)

        return fragmentView
    }


    //--------------
    //
    //--------------

    private fun matrizComplete(): Boolean{

        var result = false;

        val input = binding.tokenInput1.text?.toString() + binding.tokenInput2.text?.toString() + binding.tokenInput3.text?.toString()

        if (input?.length == 3) {
            result = true
        }

        return result;
    }

    private fun executeValidPassword(){

        val inputText = tokenInput1.text.toString() + tokenInput2.text.toString() + tokenInput3.text.toString()

        if(Constants.validatePassword(inputText, 3)){
            passwords.forEach { item -> item.correctAnimation() }
        }
        else{

            passwords.forEach { item -> item.incorrectAnimation() }

            Handler(Looper.getMainLooper()).postDelayed({

                passwords.forEach { item -> item.reset() }
                tokenInputs.forEach { item -> item.setText("") }
                tokenInputs[0].requestFocus();

            }, 500)

        }
    }




    //--------------
    //
    //--------------

    private fun PasswordView.onClick(tokenInput: TextInputEditText){
        this.setOnClickListener {
            tokenInput.clearFocus()
            tokenInput.requestFocus()

        }
    }

    private var salta = false

    private fun TextInputEditText.onKeyFocusListener(){

        this.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                SoftKeyboardUtils.openSoftKeyboard(activity, v)
            }
        }

        this.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL ) {

                if((v as TextInputEditText).text.toString().equals("")) {

                    if(salta) {
                        try {
                            val indexT = tokenInputs.indexOf(v)
                            //tokenInputs[indexT - 1].setText("")
                            tokenInputs[indexT - 1].requestFocus();
                            //passwords[indexT - 1].removeInputText()
                            //passwords[indexP + 1].onClick(tokenInputs[indexT + 1])
                        } catch (e: IndexOutOfBoundsException) {
                        }
                    }
                    salta = true
                    return@OnKeyListener true
                }
                else{
                    return@OnKeyListener false
                }
            }
            false
        })
    }



    private fun TextInputEditText.onChange(passwordView: PasswordView, textView: TextInputEditText) {
        this.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(otpCode: Editable?) {

                if (matrizComplete()) {
                    SoftKeyboardUtils.closeSoftKeyboard(activity)
                    //executeValidPassword()
                }
                else{

                    try{
                        if(textView.text!!.toString().equals("") == false) {
                            val indexT = tokenInputs.indexOf(textView)
                            tokenInputs[indexT + 1].requestFocus();
                            //passwords[indexP + 1].onClick(tokenInputs[indexT + 1])
                        }
                    }
                    catch (e: IndexOutOfBoundsException){}

                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s?.length == 1) {
                    salta = false
                }
                else{
                    salta =  true
                }
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                //--passwordView.addCircleViewstroke(Color.parseColor(getResources().getString(R.color.grey_inactive)))

                //var char : String = s!!.toString()
                var char : String = "1"
                if(s?.length == 1) {

                    passwordView.appendInputText(char)

                }
                else {

                    passwordView.removeInputText()

                    try{
                        //val indexT = tokenInputs.indexOf(textView)
                        //tokenInputs[indexT - 1].requestFocus();
                    }
                    catch (e: IndexOutOfBoundsException){}


                    //tokenInputs[indexT + 1].requestFocus();
                    //passwords.forEach { item -> item.removeInputText() }
                }

            }
        })
    }



    fun openFragment(fragment: Fragment) {
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}