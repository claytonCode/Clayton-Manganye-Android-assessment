package com.glucode.about_you.about

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.glucode.about_you.R
import com.glucode.about_you.about.views.QuestionCardView
import com.glucode.about_you.databinding.FragmentAboutBinding
import com.glucode.about_you.databinding.ProfileBinding
import com.glucode.about_you.mockdata.MockData
import java.io.ByteArrayOutputStream

class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    private lateinit var profileBinding: ProfileBinding
    private lateinit var profileName: String

    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri: Uri? = result.data?.data
                uri?.let {
                    updateProfileImage(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileBinding = ProfileBinding.inflate(LayoutInflater.from(requireContext()), binding.container, true)
        profileName = arguments?.getString("name") ?: "defaultProfile"
        val engineerRole = arguments?.getString("role")

        profileBinding.devName.text = profileName
        profileBinding.devRole.text = engineerRole

        loadProfileImage()?.let {
            profileBinding.profileImage.setImageBitmap(it)
        } ?: profileBinding.profileImage.setImageResource(R.drawable.profile)

        profileBinding.profileImage.setOnClickListener {
            pickImageFromGallery()
        }

        setUpQuestions()
    }

    override fun onPause() {
        super.onPause()
        val drawable = profileBinding.profileImage.drawable
        if (drawable is BitmapDrawable) {
            saveProfileImage(drawable.bitmap)
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        pickImageLauncher.launch(intent)
    }

    private fun updateProfileImage(uri: Uri) {
        try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            inputStream?.use {
                val newBitmap = BitmapFactory.decodeStream(it)
                profileBinding.profileImage.setImageBitmap(newBitmap)
                saveProfileImage(newBitmap)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveProfileImage(bitmap: Bitmap) {
        val sharedPreferences = requireContext().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val imageBytes = outputStream.toByteArray()
        val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)

        editor.putString("profileImage_$profileName", imageString)
        editor.apply()
    }

    private fun loadProfileImage(): Bitmap? {
        val sharedPreferences = requireContext().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        val imageString = sharedPreferences.getString("profileImage_$profileName", null)

        return imageString?.let {
            val imageBytes = Base64.decode(it, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)?.copy(Bitmap.Config.ARGB_8888, true)
        }
    }

    private fun setUpQuestions() {
        val engineer = MockData.engineers.first { it.name == profileName }

        engineer.questions.forEach { question ->
            val questionView = QuestionCardView(requireContext())
            questionView.title = question.questionText
            questionView.answers = question.answerOptions
            questionView.selection = question.answer.index

            binding.container.addView(questionView)
        }
    }
}
