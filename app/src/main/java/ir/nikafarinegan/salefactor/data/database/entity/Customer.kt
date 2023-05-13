package ir.nikafarinegan.salefactor.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_Base_Customer")
class Customer {
    @PrimaryKey(autoGenerate = true)
    var xId: Int = 0
    var xJson: String?= null
    var xUpdateDate: String?= null
}