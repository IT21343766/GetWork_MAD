package com.example.getwork

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.example.getwork.databinding.ActivityPaymentConfirmBinding
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.stripe.android.PaymentConfiguration
import com.stripe.android.payments.core.injection.PUBLISHABLE_KEY
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.PaymentSheetResultCallback
import org.json.JSONException
import org.json.JSONObject

class PaymentConfirmActivity: AppCompatActivity() {

    private lateinit var binding: ActivityPaymentConfirmBinding

    var Publishable_key: String =
        "pk_test_51N2BPpISxwbF0W8Rj1bW0rAUSkgtQjXQAPzZKI6qX2wHH6dydBnfwBmo4gldTk2wuribBwbSlaMv3kZMrTHg3SRq0059vDJdLs"
    var SecretKey: String =
        "sk_test_51N2BPpISxwbF0W8RB3c51bGc0GmhADzgieHSXE7tChwSG15rOFzmFUE1PjWRfzkOob4SEgbLAx4zcNHO1UhVDPk400bPQaSwDO"
    lateinit var CustomerId: String
    lateinit var EphericalKey: String
    lateinit var ClientSecret: String
    lateinit var paymentSheet: PaymentSheet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backFromPaymentConfirm.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.proceedToPayment.setOnClickListener({
            paymentFlow()
        })

        PaymentConfiguration.init(this, Publishable_key)
        paymentSheet = PaymentSheet(this, PaymentSheetResultCallback {
            onPaymentSheetResult(it)
        })

        val req = object: StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers",
            Response.Listener<String> { response ->
                try {
                    var obj: JSONObject = JSONObject(response)
                    CustomerId = obj.getString("id")
                    Toast.makeText(this, CustomerId, Toast.LENGTH_SHORT).show()

                    getEmphericalKey()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {error ->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $SecretKey"
                return headers
            }
        }

        val reqQue = Volley.newRequestQueue(this)
        reqQue.add(req)
    }

    private fun paymentFlow() {
        paymentSheet.presentWithPaymentIntent(ClientSecret,PaymentSheet.Configuration(
            "Odyssey",
             PaymentSheet.CustomerConfiguration(CustomerId,EphericalKey)
        ))
    }

    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        if (paymentSheetResult is PaymentSheetResult.Completed  ) {
            Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getEmphericalKey() {
        val req = object: StringRequest(Request.Method.POST, "https://api.stripe.com/v1/ephemeral_keys",
            Response.Listener<String> { response ->
                try {
                    var obj: JSONObject = JSONObject(response)
                    CustomerId = obj.getString("id")
                    Toast.makeText(this, CustomerId, Toast.LENGTH_SHORT).show()

                    getClientSecret(CustomerId, EphericalKey)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {error ->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $SecretKey"
                headers["Stripe-Version"] = "2022-11-15"
                return headers
            }

            override fun getParams(): Map<String, String>? {
                val params = mapOf(
                    "customer" to CustomerId
                )

                return params
            }
        }

        val reqQue = Volley.newRequestQueue(this)
        reqQue.add(req)
    }

    private fun getClientSecret(customerId: String, ephericalKey: String) {

        val req = object: StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents",
            Response.Listener<String> { response ->
                try {
                    var obj: JSONObject = JSONObject(response)
                    ClientSecret = obj.getString("client_secret")
                    Toast.makeText(this, ClientSecret, Toast.LENGTH_SHORT).show()


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {error ->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $SecretKey"
                return headers
            }

            override fun getParams(): Map<String, String>? {
                val params = mapOf(
                    "customer" to CustomerId,
                    "amount" to "100"+"00",
                    "currency" to "LKR",
                    "automatic_payment_methods[enabled]" to "true"
                )

                return params
            }
        }

        val reqQue = Volley.newRequestQueue(this)
        reqQue.add(req)

    }
}
