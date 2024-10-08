package com.glucode.about_you.about.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.glucode.about_you.R
import com.glucode.about_you.databinding.ViewAnswerCardBinding

class AnswerCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    val binding: ViewAnswerCardBinding =
        ViewAnswerCardBinding.inflate(LayoutInflater.from(context), this)
    @ColorInt
    private val selectedCardBackgroundColor: Int
    @ColorInt
    private val selectedTextColor: Int
    @ColorInt
    private val deselectedCardBackgroundColor: Int
    @ColorInt
    private val deselectedTextColor: Int

    var title: String? = null
        set(value) {
            field = value
            binding.title.text = value
        }

    init {
        val whiteColour = ContextCompat.getColor(context, R.color.white)
        val deepBlueColour = ContextCompat.getColor(context, R.color.deep_blue)
        selectedCardBackgroundColor = whiteColour
        selectedTextColor = deepBlueColour
        deselectedCardBackgroundColor = whiteColour
        deselectedTextColor = deepBlueColour
        radius = resources.getDimension(R.dimen.corner_radius_normal)
        elevation = resources.getDimension(R.dimen.elevation_normal)
        setCardBackgroundColor(ContextCompat.getColor(context, R.color.deep_blue))
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        if(selected) {
            setCardBackgroundColor(selectedCardBackgroundColor)
            binding.title.setTextColor(selectedTextColor)
        } else{
            setCardBackgroundColor(deselectedCardBackgroundColor)
            binding.title.setTextColor(deselectedTextColor)
        }
    }
}
