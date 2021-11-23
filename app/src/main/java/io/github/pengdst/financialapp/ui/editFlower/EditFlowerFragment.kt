package io.github.pengdst.financialapp.ui.editFlower

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

private const val ARG_FLOWER_ID = "param_flower_id"
private const val ARG_FLOWER_NAME = "param_flower_name"
private const val ARG_FLOWER_IMAGE = "param_flower_image"

class EditFlowerFragment : Fragment() {
    private var flowerId: Int = 0
    private var flowerName: String = ""
    private var flowerImageUrl: String = ""

    private lateinit var etFlowerName: EditText
    private lateinit var etFlowerUrl: EditText
    private lateinit var btnSaveFlower: Button

    private lateinit var flowerDao: FlowerDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            flowerId = it.getInt(ARG_FLOWER_ID)
            flowerName = it.getString(ARG_FLOWER_NAME) ?: ""
            flowerImageUrl = it.getString(ARG_FLOWER_IMAGE) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        flowerDao = AppDatabase.newInstance(requireContext()).flowerDao()
        return inflater.inflate(R.layout.fragment_edit_flower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etFlowerName = view.findViewById(R.id.et_flower_name)
        etFlowerUrl = view.findViewById(R.id.et_flower_url)
        btnSaveFlower = view.findViewById(R.id.btn_save)

        etFlowerName.setText(flowerName)
        etFlowerUrl.setText(flowerImageUrl)
        btnSaveFlower.setOnClickListener {
            updateFlower(etFlowerName.text.toString(), etFlowerUrl.text.toString())
        }
    }

    private fun updateFlower(flowerName: String, flowerUrl: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                flowerDao.updateFlower(
                    flower = FlowerEntity(
                        id = flowerId,
                        flowerName = if (flowerName.isNotEmpty()) flowerName else this@EditFlowerFragment.flowerName,
                        flowerImageUrl = if (flowerUrl.isNotEmpty()) flowerUrl else flowerImageUrl
                    )
                )
            }catch (e: Exception) {
                Log.e("EditFlowerFragment", "updateFlower: ", e)
            }
            withContext(Dispatchers.Main) {
                requireActivity().onBackPressed()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(flowerId: Int, flowerName: String, flowerImage: String) =
            EditFlowerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_FLOWER_ID, flowerId)
                    putString(ARG_FLOWER_NAME, flowerName)
                    putString(ARG_FLOWER_IMAGE, flowerImage)
                }
            }
    }
}