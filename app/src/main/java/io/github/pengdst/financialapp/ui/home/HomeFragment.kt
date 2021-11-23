package io.github.pengdst.financialapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.pengdst.financialapp.R
import io.github.pengdst.financialapp.data.local.db.AppDatabase
import io.github.pengdst.financialapp.data.local.db.dao.FlowerDao
import io.github.pengdst.financialapp.data.local.db.model.FlowerEntity
import io.github.pengdst.financialapp.ui.addFlower.AddFlowerFragment
import io.github.pengdst.financialapp.ui.editFlower.EditFlowerFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private val listOfFlower = listOf(
        Flower(flowerName = "Tulips", flowerImageUrl = "https://www.thespruce.com/thmb/y0RVZcelnyhPwZlY-vsbK5kf8Aw=/960x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/tulips-planting-and-growing-tulips-1402137-06-5c18a69c82114b16bc8eb3baf69935e3.jpg"),
        Flower(flowerName = "Raflesia Arnoldi", flowerImageUrl = "https://t-2.tstatic.net/tribunnewswiki/foto/bank/images/rafflesia-arnoldii.jpg"),
        Flower(flowerName = "Mawar", flowerImageUrl = "https://asset.kompas.com/crops/Xp29TEyfu6wLfZ6bq8c2IwBAWeA=/200x75:800x675/340x340/data/photo/2021/05/25/60ac6b0a8be74.jpg"),
        Flower(flowerName = "Anggrek", flowerImageUrl = "https://asset.kompas.com/crops/YeJJiCcc4WyuB3q1MSdna5CjPMs=/127x86:1816x1211/750x500/data/photo/2021/01/21/60094953238c1.jpg"),
        Flower(flowerName = "Melati", flowerImageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/da/Crape_Jasmine.jpg/1200px-Crape_Jasmine.jpg"),
    )

    private lateinit var rvFlower: RecyclerView
    private lateinit var btnAddFlower: FloatingActionButton
    private lateinit var flowerAdapter: FlowerAdapter
    private lateinit var swipeRefreshFlowers: SwipeRefreshLayout

    private lateinit var flowerDao: FlowerDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        flowerDao = AppDatabase.newInstance(requireContext()).flowerDao()
        flowerAdapter = FlowerAdapter()
        flowerAdapter.setOnItemClickListener(object : FlowerAdapter.ItemClick<Flower> {
            override fun onItemClick(view: View, data: Flower, position: Int) {
                Toast.makeText(context, "Bunga ${data.flowerName} diklik", Toast.LENGTH_SHORT).show()
                val editFlowerFragment = EditFlowerFragment.newInstance(
                    flowerId = data.flowerId,
                    flowerName = data.flowerName,
                    flowerImage = data.flowerImageUrl
                )
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, editFlowerFragment)
                    .addToBackStack(editFlowerFragment.tag)
                    .commit()
            }
        })

        insertDummyFlowers()
        getFlowerList()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAddFlower = view.findViewById(R.id.btn_add_flower)
        rvFlower = view.findViewById(R.id.rv_flowers)
        swipeRefreshFlowers = view.findViewById(R.id.swipe_refresh_flowers)

        rvFlower.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = flowerAdapter
        }

        swipeRefreshFlowers.setOnRefreshListener {
            getFlowerList()
        }

        btnAddFlower.setOnClickListener {
            val addFlowerFragment = AddFlowerFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, addFlowerFragment)
                .addToBackStack(addFlowerFragment.tag)
                .commit()
        }
    }

    private fun insertDummyFlowers() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val flowers = flowerDao.getAllFlower()
                if (flowers.isNullOrEmpty()) {
                    listOfFlower.map { flowerToEntity(it) }
                        .forEach {
                            flowerDao.insertFlower(it)
                        }
                }
            }catch (e: Exception) {
                Log.e("HomeFragment", "get or insert Flowers: ", e)
            }
        }
    }

    private fun getFlowerList() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val flowers = flowerDao.getAllFlower().map { entityToFlower(it) }

                withContext(Dispatchers.Main) {
                    flowerAdapter.submitList(flowers)
                }
            }catch (e: Exception) {
                Log.e("HomeFragment", "getFlowerList: ", e)
            }

            swipeRefreshFlowers.isRefreshing = false
        }
    }

    private fun entityToFlower(entity: FlowerEntity) = Flower(
        flowerId = entity.id,
        flowerName = entity.flowerName,
        flowerImageUrl = entity.flowerImageUrl
    )

    private fun flowerToEntity(flower: Flower) = FlowerEntity(
        id = flower.flowerId,
        flowerName = flower.flowerName,
        flowerImageUrl = flower.flowerImageUrl
    )
}