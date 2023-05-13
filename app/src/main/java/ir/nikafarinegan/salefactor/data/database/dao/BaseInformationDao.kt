package ir.nikafarinegan.salefactor.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.nikafarinegan.salefactor.data.database.entity.Customer

@Dao
interface BaseInformationDao {


    /** #Begin Customer =========================================================================**/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomer(customer: Customer)

    @Query("SELECT * FROM tbl_Base_Customer")
    fun getCustomers(): LiveData<Customer>

    @Query("DELETE FROM tbl_Base_Customer")
    fun deleteCustomers()


    /** #End Customer ===========================================================================**/
}
