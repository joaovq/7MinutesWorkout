package br.queiroz.a7minutesworkout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
    }

    override fun getItemCount(): Int {
        return items.size
    }
}