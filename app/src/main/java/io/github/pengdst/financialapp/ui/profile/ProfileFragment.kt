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

class ProfileFragment : Fragment(), ProfileView {

    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var ivImage: ShapeableImageView

    private val apiService = ApiClient.build().create(ApiService::class.java)

    private lateinit var presenter: ProfilePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        presenter = ProfilePresenter(this, apiService)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvName = view.findViewById(R.id.tv_name)
        tvEmail = view.findViewById(R.id.tv_email)
        ivImage = view.findViewById(R.id.iv_image)

        presenter.loadUserProfile(0)
    }

    override fun showUserProfile(user: User) {
        tvName.text = user.name
        tvEmail.text = user.email

        Glide.with(requireContext())
            .load(user.imageUrl)
            .into(ivImage)
    }

    override fun failedLoadUserProfile(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}