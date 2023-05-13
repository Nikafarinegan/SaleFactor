package ir.nikafarinegan.salefactor.data.database

import ir.nikafarinegan.salefactor.data.database.entity.Customer
import ir.nikafarinegan.salefactor.data.database.entity.SubSystem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Ali_Kazemi on 27/10/2021.
 */
class LocalRepository(
    private val database: WarehouseDatabase
) {

    /** #Begin SubSystem ========================================================================**/

    fun getSubSystem(subSystemId: Int) = database.subSystemDao().getSubSystem(subSystemId)

    suspend fun insertSubSystem(model: SubSystem) {
        withContext(Dispatchers.IO) {
            database.subSystemDao().insertSubSystem(model)
        }
    }

    suspend fun deleteSubSystem(subSystemId: Int){
        withContext(Dispatchers.IO) {
            database.subSystemDao().deleteSubSystem(subSystemId)
        }
    }

    /** #End Customer ===========================================================================**/
    //
    //
    //
    //
    //
    /** #Begin Customer =========================================================================**/

    fun getCustomers() = database.baseInformation().getCustomers()

    suspend fun insertCustomer(customer: Customer){
        withContext(Dispatchers.IO){
            database.baseInformation().insertCustomer(customer)
        }
    }

    suspend fun deleteCustomer(){
        withContext(Dispatchers.IO){
            database.baseInformation().deleteCustomers()
        }
    }


    /** #End Customer ===========================================================================**/

}