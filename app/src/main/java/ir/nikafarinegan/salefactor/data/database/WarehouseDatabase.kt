package ir.nikafarinegan.salefactor.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.nikafarinegan.salefactor.data.database.dao.BaseInformationDao
import ir.nikafarinegan.salefactor.data.database.dao.SubSystemDao
import ir.nikafarinegan.salefactor.data.database.entity.Customer
import ir.nikafarinegan.salefactor.data.database.entity.SubSystem

@Database(
    entities = [
        (SubSystem::class),
        (Customer::class)
    ],
    version = 1,
    exportSchema = false
)

abstract class WarehouseDatabase : RoomDatabase() {
    abstract fun subSystemDao(): SubSystemDao
    abstract fun baseInformation(): BaseInformationDao
}