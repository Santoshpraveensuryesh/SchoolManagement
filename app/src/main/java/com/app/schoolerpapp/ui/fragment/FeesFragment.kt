package com.app.schoolerpapp.ui.fragment

import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.schoolerpapp.R
import com.app.schoolerpapp.databinding.FragmentFeesBinding
import com.app.schoolerpapp.model.Fees
import com.app.schoolerpapp.model.Paytm
import com.app.schoolerpapp.model.StudentFees
import com.app.schoolerpapp.paytm.Checksum
import com.app.schoolerpapp.ui.activity.DashBoardActivity
import com.app.schoolerpapp.ui.adapter.FeeAdapter
import com.app.schoolerpapp.utility.Constant
import com.app.schoolerpapp.viewmodel.StudentViewModel
import com.paytm.pgsdk.PaytmOrder
import com.paytm.pgsdk.PaytmPGService
import com.paytm.pgsdk.PaytmPaymentTransactionCallback
import com.viewmodel.app.api.APIService
import com.viewmodel.app.utility.AppUtils
import kotlinx.android.synthetic.main.fee_item.view.*
import kotlinx.android.synthetic.main.fragment_fees.view.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.HashMap

class FeesFragment : Fragment() {
    private lateinit var fragmentFeesBinding: FragmentFeesBinding
    private lateinit var activity: DashBoardActivity
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var feeAdapter: FeeAdapter
    private var studentfeeList = ArrayList<StudentFees>()
    private var feeList = ArrayList<Fees>()
    private var fromMenu: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as DashBoardActivity
        if (arguments != null) {
            if (arguments!!.containsKey("fromMenu"))
                fromMenu = arguments!!.getString("fromMenu")!!
            studentfeeList = arguments!!.getParcelableArrayList<StudentFees>("dueFeeList")!!
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var param: RelativeLayout.LayoutParams =
            activity.txt_title.layoutParams as RelativeLayout.LayoutParams
        param.marginEnd = 0
        param.marginStart = 0
        activity.txt_title.layoutParams = param
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFeesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_fees, container, false)
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java)
        setView(fragmentFeesBinding.root)
        return fragmentFeesBinding.root
    }

    private fun setView(root: View) {

        linearLayoutManager = LinearLayoutManager(activity)
        root.rv_feesList.hasFixedSize()
        root.rv_feesList.layoutManager = linearLayoutManager

        if (studentfeeList.size <= 0) {
            for (i in 1..100) {
                var studentFess = StudentFees()
                studentFess.amount = "1000"
                studentfeeList.add(studentFess)
            }
        } else {
            for (studentFees in studentfeeList) {
                for (fees in studentFees.feesList) {
                    var paidAmount: Double = 0.0
                    for (amountDetail in fees.amountDetailList) {
                        paidAmount += amountDetail.amount.toDouble()
                    }
                    if (fees.amount.toDouble() == paidAmount) fees.status = "1"
                    else if (paidAmount == 0.0) fees.status = "0"
                    else if (paidAmount > 0.0 && paidAmount < fees.amount.toDouble())
                        fees.status = "1"
                    feeList.add(fees)
                }
            }
        }

        feeAdapter =
            FeeAdapter(
                activity,
                feeList,
                studentViewModel,
                fromMenu,
                object : FeeAdapter.OnClickFeesListener {
                    override fun onPaymentClick(pos: Int) {
//                        generateCheckSum()
                    }

                    override fun onFeeClick(pos: Int) {
                        var feeDetailsFragment = FeeDetailsFragment()
                        var bundle = Bundle()
                        bundle.putParcelable("fees", feeList.get(pos))
                        feeDetailsFragment.arguments = bundle
                        AppUtils.replaceFragment(feeDetailsFragment, activity, R.id.container)
                    }

                })
        root.rv_feesList.adapter = feeAdapter

    }

    val CHECKSUMlINK = "http://www.impetrosys.com/shree_jewellers/webservices/PHP/"

    private fun generateCheckSum() {

        val progressDialog = AppUtils.showProgressDialog(activity)
        //creating a retrofit object.
        val retrofit = Retrofit.Builder()
            .baseUrl(CHECKSUMlINK)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //creating the retrofit api service
        val apiService = retrofit.create(APIService::class.java!!)
        var userId = "1010"

        var paymentAmount = "1000"

        //creating paytm object
        //containing all the values required
        val paytm = Paytm(
            Constant.M_ID,
            userId,
            Constant.CHANNEL_ID,
            paymentAmount,
            Constant.WEBSITE,
            Constant.CALLBACK_URL,
            Constant.INDUSTRY_TYPE_ID
        )

        //creating a call object from the apiService
        val call = apiService.getChecksum(
            paytm.getmId(),
            paytm.orderId,
            paytm.custId,
            paytm.channelId,
            paytm.txnAmount,
            paytm.website,
            paytm.callBackUrl,
            paytm.industryTypeId
        )
        //making the call to generate checksum
        call.enqueue(object : Callback<Checksum> {
            override fun onResponse(call: Call<Checksum>, response: Response<Checksum>) {

                //once we get the checksum we will initiailize the payment.
                //the method is taking the checksum we got and the paytm object as the parameter
                initializePaytmPayment(response.body()!!.getChecksumHash(), paytm)
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<Checksum>, t: Throwable) {
                Log.e("error", "")
                progressDialog.dismiss()
            }
        })
    }

    private fun initializePaytmPayment(checksumHash: String, paytm: Paytm) {

        //getting paytm service
        val Service = PaytmPGService.getStagingService()

        //use this when using for production
        //PaytmPGService Service = PaytmPGService.getProductionService();

        //creating a hashmap and adding all the values required
        val paramMap = HashMap<String, String>()
        paramMap["MID"] = Constant.M_ID
        paramMap["ORDER_ID"] = paytm.orderId
        paramMap["CHANNEL_ID"] = paytm.channelId
        paramMap["CUST_ID"] = paytm.custId
        paramMap["TXN_AMOUNT"] = paytm.txnAmount
        paramMap["WEBSITE"] = paytm.website
        paramMap["CALLBACK_URL"] = paytm.callBackUrl
        paramMap["INDUSTRY_TYPE_ID"] = paytm.industryTypeId
        paramMap["CHECKSUMHASH"] = checksumHash


        //creating a paytm order object using the hashmap
        val order = PaytmOrder(paramMap)

        //intializing the paytm service
        Service.initialize(order, null)

        //finally starting the payment transaction
        Service.startPaymentTransaction(getActivity(), true, true, object :
            PaytmPaymentTransactionCallback {
            override fun onTransactionResponse(inResponse: Bundle) {
                Log.d("paytm", inResponse.toString())
                Toast.makeText(
                    context,
                    "Payment Transaction response $inResponse",
                    Toast.LENGTH_LONG
                ).show()
                if (inResponse.getString("STATUS")!!.equals("TXN_SUCCESS", ignoreCase = true))
                else {
                }
            }

            override fun networkNotAvailable() {

            }

            override fun clientAuthenticationFailed(inErrorMessage: String) {

            }

            override fun someUIErrorOccurred(inErrorMessage: String) {

            }

            override fun onErrorLoadingWebPage(
                iniErrorCode: Int,
                inErrorMessage: String,
                inFailingUrl: String
            ) {

            }

            override fun onBackPressedCancelTransaction() {

            }

            override fun onTransactionCancel(inErrorMessage: String, inResponse: Bundle) {

            }
        })

    }

    override fun onResume() {
        super.onResume()
        activity.showMenuIcon()
        if (fromMenu.equals("fromMenu"))
            activity.txt_title.setText(activity.resources.getString(R.string.fee_info))
        else
            activity.txt_title.setText(activity.resources.getString(R.string.student_info))

    }

}