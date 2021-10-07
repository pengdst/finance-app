package io.github.pengdst.financialapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import io.github.pengdst.financialapp.data.remote.ApiClient
import io.github.pengdst.financialapp.data.remote.ApiService
import io.github.pengdst.financialapp.data.repository.UserRepository
import io.github.pengdst.financialapp.data.vo.ResultResource
import io.github.pengdst.financialapp.databinding.FragmentProfileBinding
import io.github.pengdst.financialapp.domain.model.User
import io.github.pengdst.financialapp.ui.ViewModelFactory

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    val binding: FragmentProfileBinding get() = _binding!!

    private val apiService = ApiClient.build().create(ApiService::class.java)

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val repository = UserRepository(apiService)
        val viewModelFactory = ViewModelFactory(repository)
        profileViewModel = ViewModelProvider(viewModelStore, viewModelFactory)[ProfileViewModel::class.java]
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.loadUserProfile(0)
        profileViewModel.userProfile.observe(viewLifecycleOwner) {
            when(it) {
                is ResultResource.Success -> {
                    it.data?.let { user ->
                        showUserProfile(user)
                    }
                }

                is ResultResource.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showUserProfile(user: User) {
        with(binding) {
            tvName.text = user.name
            tvEmail.text = user.email

            Glide.with(requireContext())
                .load(user.imageUrl)
                .into(ivImage)
        }
    }
}