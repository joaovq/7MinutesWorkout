package br.queiroz.a7minutesworkout.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.queiroz.a7minutesworkout.R
import br.queiroz.a7minutesworkout.databinding.ItemExerciseStatusBinding
import br.queiroz.a7minutesworkout.model.ExerciseModel

class ExerciseStatusAdapter(
    val items:ArrayList<ExerciseModel>
) : RecyclerView.Adapter<ExerciseStatusAdapter.ExerciseStatusViewHolder>() {
    inner class ExerciseStatusViewHolder(binding: ItemExerciseStatusBinding)
        :RecyclerView.ViewHolder(binding.root){
        val tvItem = binding.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseStatusViewHolder {
        val binding = ItemExerciseStatusBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        val holder = ExerciseStatusViewHolder(binding)
        return holder
    }

    override fun onBindViewHolder(holder: ExerciseStatusViewHolder, position: Int) {
        val model:ExerciseModel = items[position]
        holder.tvItem.text = model.getId().toString()

        when{
            model.getIsSelected() ->{
                holder.tvItem.background = ContextCompat.getDrawable(
                    holder.tvItem.context,
                    R.drawable.item_circular_thin_color_accent_border
                )
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
            model.getIsCompleted()->{
                holder.tvItem.background = ContextCompat.getDrawable(
                    holder.tvItem.context,
                    R.drawable.item_circular_color_accent_background
                )
                holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else->{
                holder.tvItem.background = ContextCompat.getDrawable(
                    holder.tvItem.context,
                    R.drawable.item_circular_color_gray_background
                )
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}