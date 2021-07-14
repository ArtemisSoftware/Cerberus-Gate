package com.artemissoftware.cerberusgate

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
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
        tokenInput1 = fragmentView.findViewById(R.id.token_input_1)

        passwords.add(password1)
        passwords.add(password2)
        passwords.add(password3)

        passwords.forEach { item -> item.onClick(tokenInput1) }


        tokenInput1.onChange(passwordView = password1, textView =  tokenInput1)

        //--password1.setListener(this)
//
//
//        password1.setOnClickListener {
//            tokenInput1.clearFocus()
//            tokenInput1.requestFocus()
//        }

        tokenInput1.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                SoftKeyboardUtils.openSoftKeyboard(activity, v)
            }
        }


//        (requireActivity() as MainActivity).updateStatusBarColor(R.color.colorSuccess)

        return fragmentView
    }


    private fun matrizComplete(): Boolean{

        var result = false;

        val input = binding.tokenInput1.text?.toString() + binding.tokenInput2.text?.toString() + binding.tokenInput3.text?.toString()

        if (input?.length == 3) {
            result = true
        }

        return result;
    }



    private fun PasswordView.onClick(tokenInput: TextInputEditText){
        this.setOnClickListener {
            tokenInput.clearFocus()
            tokenInput.requestFocus()
        }
    }


    private fun TextInputEditText.onChange(passwordView: PasswordView, textView: TextInputEditText) {
        this.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(otpCode: Editable?) {

                if (matrizComplete()) {
                    SoftKeyboardUtils.closeSoftKeyboard(activity)

                    val inputText = tokenInput1.text

                    if(Constants.validatePassword(inputText.toString()!!, 3)){

                        passwords.forEach { item -> item.correctAnimation() }

                    }
                    else{

                        passwords.forEach { item -> item.incorrectAnimation() }


                        Handler(Looper.getMainLooper()).postDelayed({

                            passwords.forEach { item -> item.reset() }

                            tokenInput1.setText("")
                        }, 500)

                    }


                    //--setLoadingState(true)
                    //--listener.validateToken(otpCode.toString(), this@SmsRequestFragment)
                }


            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                //--passwordView.addCircleViewstroke(Color.parseColor(getResources().getString(R.color.grey_inactive)))

                //var char : String = s!!.toString()
                var char : String = "1"
                if(s?.length == 1) {

                    password1.appendInputText(char)
                    password2.removeInputText()
                }
                else if(s?.length == 2) {

                    password2.appendInputText(char)
                    password3.removeInputText()
                }
                else if(s?.length == 3) {

                    password3.appendInputText(char)
                }
                else {

                    passwords.forEach { item -> item.removeInputText() }
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