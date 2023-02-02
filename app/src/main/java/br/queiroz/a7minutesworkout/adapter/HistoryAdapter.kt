package br.queiroz.a7minutesworkout.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.queiroz.a7minutesworkout.data.HistoryEntity
import br.queiroz.a7minutesworkout.databinding.ActivityHistoryBinding
import br.queiroz.a7minutesworkout.databinding.ItemHistoryRowBinding

class HistoryAdapter(
    private val histories:ArrayList<HistoryEntity>
):RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {

        val binding = ItemHistoryRowBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )

       return HistoryViewHolder(binding)

    }

    override fun getItemCount(): Int  = histories.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class  HistoryViewHolder(binding: ItemHistoryRowBinding)
        :RecyclerView.ViewHolder(binding.root){
            private val llHistoryItemMain = binding.llHistoryItemMain
            private val tvPosition = binding.tvPosition
            private val tvItem = binding.tvItem


            fun bind(position: Int){
                val index = position + 1
                tvPosition.text = index.toString()
                tvItem.text = histories[position].date

                if (position%2 == 0){
                    llHistoryItemMain.setBackgroundColor(
                       Color.parseColor("#EBEBEB"))
                }
                else{
                    llHistoryItemMain.setBackgroundColor(
                    Color.parseColor("#FFFFFF"))

                }
            }

        }

}