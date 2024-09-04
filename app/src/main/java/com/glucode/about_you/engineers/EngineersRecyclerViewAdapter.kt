package com.glucode.about_you.engineers
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glucode.about_you.R
import com.glucode.about_you.databinding.ItemEngineerBinding
import com.glucode.about_you.engineers.models.Engineer

class EngineersRecyclerViewAdapter(
    private var engineers: List<Engineer>,
    private val onClick: (Engineer) -> Unit
) : RecyclerView.Adapter<EngineersRecyclerViewAdapter.EngineerViewHolder>() {

    override fun getItemCount() = engineers.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EngineerViewHolder {
        return EngineerViewHolder(ItemEngineerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: EngineerViewHolder, position: Int) {
        holder.bind(engineers[position], onClick)
    }

    inner class EngineerViewHolder(private val binding: ItemEngineerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(engineer: Engineer, onClick: (Engineer) -> Unit) {
            binding.name.text = engineer.name
            binding.role.text = engineer.role
            binding.root.setOnClickListener {
                onClick(engineer)
            }
            // Set default image if profile image is not available
            loadProfileImage(binding.root.context, engineer.name)?.let {
                binding.profileImage.setImageBitmap(it)
            }

            binding.root.setOnClickListener {
                onClick(engineer)
            }
            val profileImage = loadProfileImage(binding.root.context, engineer.name)
            if (profileImage != null) {
                binding.profileImage.setImageBitmap(profileImage)
            } else {

                binding.profileImage.setImageResource(R.drawable.profile) // keeps image if nothing was changed
            }
        }

        // listens to sharedPreferences to update profile image
        private fun loadProfileImage(context: Context, profileName: String): Bitmap? {
            val sharedPreferences = context.getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
            val imageString = sharedPreferences.getString("profileImage_$profileName", null)

            return imageString?.let {
                try {
                    val imageBytes = Base64.decode(it, Base64.DEFAULT)
                    BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)?.copy(Bitmap.Config.ARGB_8888, true)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        }

    }

}