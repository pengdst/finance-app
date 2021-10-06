package io.github.pengdst.financialapp.ui.addFlower

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import io.github.pengdst.financialapp.R
import io.github.pengdst.financialapp.data.local.db.AppDatabase
import io.github.pengdst.financialapp.data.local.db.dao.FlowerDao
import io.github.pengdst.financialapp.data.local.db.model.FlowerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddFlowerFragment : Fragment() {

    private lateinit var etFlowerName: EditText
    private lateinit var etFlowerUrl: EditText
    private lateinit var btnSaveFlower: Button

    private lateinit var flowerDao: FlowerDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        flowerDao = AppDatabase.newInstance(requireContext()).flowerDao()
        return inflater.inflate(R.layout.fragment_add_flower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etFlowerName = view.findViewById(R.id.et_flower_name)
        etFlowerUrl = view.findViewById(R.id.et_flower_url)
        btnSaveFlower = view.findViewById(R.id.btn_save)

        btnSaveFlower.setOnClickListener {
            insertFlower(etFlowerName.text.toString(), etFlowerUrl.text.toString())
        }
    }

    private fun insertFlower(flowerName: String, flowerUrl: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                flowerDao.insertFlower(flower = FlowerEntity(
                    id = 0,
                    flowerName = flowerName,
                    flowerImageUrl = flowerUrl
                ))
            }catch (e: Exception) {
                Log.e("AddFlowerFragment", "insertFlower: ", e)
            }
            withContext(Dispatchers.Main) {
                requireActivity().onBackPressed()
            }
        }
    }
}