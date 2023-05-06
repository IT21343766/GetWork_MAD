package com.example.getwork

import com.example.getwork.models.EmployeePaymentInfoModel
import com.example.getwork.models.PaymentModel
import org.junit.Test
import org.junit.Before
import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PaymentUnitTest {
    private lateinit var myModel: PaymentModel
    private lateinit var currDateTime: String

    @Before
    fun setup() {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        currDateTime = formatter.format(Date())

        myModel = PaymentModel(
            "uid",
            "payment_id",
            "50000",
            "currency",
            "description",
            currDateTime
        )
    }

    @Test
    fun testDefaultValues() {
        // Verify default values of properties
        assertEquals("uid", myModel.uid)
        assertEquals("payment_id", myModel.payment_id)
        assertEquals("50000", myModel.amount)
        assertEquals("currency", myModel.currency)
        assertEquals("currency", myModel.description)
        assertEquals(currDateTime, myModel.time)
    }
}