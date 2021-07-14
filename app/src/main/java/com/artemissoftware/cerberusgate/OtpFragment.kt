package com.artemissoftware.cerberusgate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mukesh.OnOtpCompletionListener
import com.mukesh.OtpView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class OtpFragment : Fragment(), OnOtpCompletionListener  {

    private lateinit var otpView: OtpView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var fragmentView = inflater.inflate(R.layout.fragment_otp, container, false);

        otpView = fragmentView.findViewById(R.id.otp_view);
        otpView.setOtpCompletionListener(this)


        (requireActivity() as MainActivity).updateStatusBarColor(R.color.colorError)
        return fragmentView
    }




    override fun onOtpCompleted(otp: String?) {

        otp.let {

            if(it.equals("1111")){

                Toast.makeText(requireContext(), otpView.getText(), Toast.LENGTH_SHORT).show();
            }
            else{
                otpView.setText("")
                otpView.setAnimationEnable(true)
                otpView.clearAnimation()

                Toast.makeText(requireContext(), "Erro", Toast.LENGTH_SHORT).show();
            }

        }


    }


}