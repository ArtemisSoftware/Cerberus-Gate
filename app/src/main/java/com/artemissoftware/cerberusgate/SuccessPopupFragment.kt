package com.artemissoftware.cerberusgate

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SuccessPopupFragment : Fragment(), TextWatcher {

    private lateinit var txtAmount: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var fragmentView = inflater.inflate(R.layout.fragment_success_popup, container, false);

        txtAmount = fragmentView.findViewById(R.id.txt_amount)
        txtAmount.addTextChangedListener(this)
        txtAmount.hint = "00.00"

        getActivity()?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        return fragmentView
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        s?.let {

            if(s.length == 0){
                var lolo = txtAmount.layoutParams
                lolo.width = ViewGroup.LayoutParams.WRAP_CONTENT
                txtAmount.layoutParams = lolo
                txtAmount.hint = "00.00"
                return
            }


//            var lolo = txtAmount.layoutParams
//            lolo.width = 55
//            txtAmount.layoutParams = lolo

            var lolo = txtAmount.layoutParams
            lolo.width = (txtAmount.textSize + ((txtAmount.textSize /2.1 ) * s.length)).toInt()//ViewGroup.LayoutParams.WRAP_CONTENT
            txtAmount.layoutParams = lolo


            try {
                if(count == 1){
                    txtAmount.setSelection(start + 1)
                }
                else{
                    txtAmount.setSelection(start)
                }
            }
            catch (e: IndexOutOfBoundsException){
                txtAmount.setSelection(start)
            }
        }


    }


}