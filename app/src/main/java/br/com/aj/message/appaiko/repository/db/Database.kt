package br.com.aj.message.appaiko.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.aj.message.appaiko.data.*

@Database(
    entities = [
        CorredorItem::class,
        Parada::class,
        PositionVehicles::class,
               L::class,
               V::class,
        PrevisaoParada::class],
    version = 1,
    exportSchema = false
)
//@TypeConverters(ConvertersPositionVehicles::class)
abstract class MyDatabase : RoomDatabase() {
    abstract val daoCorredor: DaoCorredor
    abstract val daoParada: DaoParada
    abstract val daoBus: DaoBus

    companion object {

        @Volatile
        private var instancie: MyDatabase? = null

        fun getInstancie(ctx: Context): MyDatabase? {
            if (instancie == null) {
                synchronized(this) {
                    instancie = Room.databaseBuilder(
                        ctx.applicationContext,
                        MyDatabase::class.java,
                        "my_database"
                    ).fallbackToDestructiveMigration().build()
                }
            }

            return instancie
        }

    }

}