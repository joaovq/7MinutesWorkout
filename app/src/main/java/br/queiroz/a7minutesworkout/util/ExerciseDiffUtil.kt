package br.queiroz.a7minutesworkout.util

import androidx.recyclerview.widget.DiffUtil
import br.queiroz.a7minutesworkout.model.ExerciseModel

class ExerciseDiffUtil(
    val newList: ArrayList<ExerciseModel>,
    val oldList: ArrayList<ExerciseModel>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = newListSize

    override fun getNewListSize(): Int = oldListSize

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
    ): Boolean =
        newList[newItemPosition] == oldList[oldItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        when {
            oldList[oldItemPosition].getId() != newList[newItemPosition].getId() -> false
            oldList[oldItemPosition].getImage() != newList[newItemPosition].getImage() -> false
            oldList[oldItemPosition].getName() != newList[newItemPosition].getName() -> false
            oldList[oldItemPosition].getIsCompleted() != newList[newItemPosition].getIsCompleted() -> false
            oldList[oldItemPosition].getIsSelected() != newList[newItemPosition].getIsSelected() -> false
            else -> true
        }
}
