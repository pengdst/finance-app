package io.github.pengdst.financialapp.data.local.db.dao

import androidx.room.*
import io.github.pengdst.financialapp.data.local.db.model.FlowerEntity

@Dao
interface FlowerDao {

    @Query("SELECT * FROM flowerentity")
    suspend fun getAllFlower(): List<FlowerEntity>

    @Query("SELECT * FROM flowerentity WHERE id LIKE :flowerId")
    suspend fun getFlowerById(flowerId: Int): FlowerEntity?

    @Insert
    suspend fun insertFlower(flower: FlowerEntity)

    @Update
    suspend fun updateFlower(flower: FlowerEntity)

    @Delete
    suspend fun deleteFlower(flower: FlowerEntity)

}