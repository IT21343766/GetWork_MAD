package com.example.getwork

import com.example.getwork.models.EmployeePaymentInfoModel
import org.junit.Test
import org.junit.Before
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class EmployeePaymentInfoTest {
    private lateinit var myModel:EmployeePaymentInfoModel

    @Before
    fun setup() {
        myModel = EmployeePaymentInfoModel(
            "uid",
            "accHolderName",
            "accNo",
            "bankName",
            "bankBranchName",
            EmployeePaymentInfoModel.PENDING,
        )
    }

    @Test
    fun testDefaultValues() {
        // Verify default values of properties
        assertEquals("uid", myModel.uid)
        assertEquals("accHolderName", myModel.accHoldersName)
        assertEquals("accNo", myModel.accNo)
        assertEquals("bankName", myModel.bankName)
        assertEquals("bankBranchName", myModel.bankBranchName)
        assertEquals(EmployeePaymentInfoModel.PENDING, myModel.approval)
    }
}