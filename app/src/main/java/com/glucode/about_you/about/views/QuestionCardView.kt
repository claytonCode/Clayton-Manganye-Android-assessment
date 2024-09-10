package com.glucode.about_you.about.views

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.glucode.about_you.R
import com.glucode.about_you.databinding.ViewQuestionCardBinding


class QuestionCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    private val binding: ViewQuestionCardBinding =
        ViewQuestionCardBinding.inflate(LayoutInflater.from(context), this)

    var title: String? = null
        set(value) {
            field = value
            binding.title.text = value
        }

    var answers: List<String> = listOf()
        set(value) {
            field = value
            binding.answers.removeAllViews()
            value.forEach { answer ->
                addAnswer(answer)
            }
        }

    var selection: Int? = null
        set(value) {
            field = value
            value ?: return
            binding.answers.children.elementAt(value).isSelected = true
        }

    init {
        radius = resources.getDimension(R.dimen.corner_radius_normal)
        elevation = resources.getDimension(R.dimen.elevation_normal)
        setCardBackgroundColor(ContextCompat.getColor(context, R.color.deep_blue))
    }

    private fun addAnswer(title: String) {
        val answerView = AnswerCardView(context)
        answerView.title = title
        answerView.setOnClickListener { onAnswerClick(it) }
        binding.answers.addView(answerView)
    }

    private fun onAnswerClick(view: View) {
        val clickedIndex = binding.answers.indexOfChild(view)

        // Proceed only if the clicked answer is different from the current selection
        if (selection != clickedIndex) {
            // Deselect the previously selected answer, if any
            selection?.let { previousSelection ->
                val previousView = binding.answers.getChildAt(previousSelection) as AnswerCardView
                previousView.isSelected = false
                previousView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.deep_blue))
                previousView.binding.title.setTextColor(Color.WHITE)
                previousView.binding.title.setTypeface(null, Typeface.NORMAL)
            }
            // Select the new answer
            selection = clickedIndex
            val selectedView = view as AnswerCardView
            selectedView.isSelected = true
            selectedView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
            selectedView.binding.title.setTextColor(ContextCompat.getColor(context, R.color.deep_blue))
            //set bold on answer card
            selectedView.binding.title.setTypeface(null, Typeface.BOLD)

        }
    }


    private fun setSelection(index: Int) {
        binding.answers.children.forEachIndexed { i, view ->
            view.isSelected = i == index
        }
    }
}