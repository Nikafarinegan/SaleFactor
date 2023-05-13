package ir.nikafarinegan.salefactor.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Ali_Kazemi on 27/10/2021.
 */

@Entity(tableName = "tbl_SubSystem")
class SubSystem {
    @PrimaryKey(autoGenerate = true)
    var xId: Int = 0
    var xSsId: Int?= null
    var xJson: String?= null
    var xUpdateDate: String?= null
}