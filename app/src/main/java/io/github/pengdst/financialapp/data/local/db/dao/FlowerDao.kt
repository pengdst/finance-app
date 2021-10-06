package io.github.pengdst.financialapp.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.pengdst.financialapp.data.local.db.model.FlowerEntity

@Dao
interface FlowerDao {

    @Query("SELECT * FROM flowerentity")
    suspend fun getAllFlower(): List<FlowerEntity>

    @Query("SELECT * FROM flowerentity WHERE id LIKE :flowerId")
    suspend fun getFlowerById(flowerId: Int): FlowerEntity?

    @Insert
    suspend fun insertFlower(flower: FlowerEntity)

}