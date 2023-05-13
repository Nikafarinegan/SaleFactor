package ir.nikafarinegan.salefactor.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.nikafarinegan.salefactor.data.database.entity.SubSystem

/**
 * Created by Ali_Kazemi on 27/10/2021.
 */
@Dao
interface SubSystemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubSystem(model: SubSystem)

    @Query("SELECT * FROM tbl_SubSystem WHERE xSsId= :ssId")
    fun getSubSystem(ssId: Int): LiveData<SubSystem>

    @Query("DELETE FROM tbl_SubSystem WHERE xSsId= :ssId")
    fun deleteSubSystem(ssId: Int)
}