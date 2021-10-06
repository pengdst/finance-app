package io.github.pengdst.financialapp.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import io.github.pengdst.financialapp.R
import io.github.pengdst.financialapp.data.remote.ApiClient
import io.github.pengdst.financialapp.data.remote.ApiService
import io.github.pengdst.financialapp.data.remote.model.UserDto
import io.github.pengdst.financialapp.domain.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var ivImage: ShapeableImageView

    private val apiService = ApiClient.build().create(ApiService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvName = view.findViewById(R.id.tv_name)
        tvEmail = view.findViewById(R.id.tv_email)
        ivImage = view.findViewById(R.id.iv_image)

        apiService.getDetailUser(0).enqueue(object : Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                if (response.isSuccessful) {
                    response.body()?.let { dto ->
                        showUserProfile(dtoToUser(dto))
                    }
                }
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                Toast.makeText(context, "Ada yang salah.", Toast.LENGTH_LONG).show()
                Log.e("ProfileFragment", "Failure: ",t)
            }

        })
    }

    private fun showUserProfile(user: User) {
        tvName.text = user.name
        tvEmail.text = user.email

        Glide.with(requireContext())
            .load(user.imageUrl)
            .into(ivImage)
    }

    private fun dtoToUser(dto: UserDto) = User(
        id = dto.id ?: 0,
        name = dto.name ?: "",
        email = dto.email ?: "",
        imageUrl = dto.imageUrl ?: ""
    )
}