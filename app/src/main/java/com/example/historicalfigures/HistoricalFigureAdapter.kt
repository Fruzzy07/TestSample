package com.example.historicalfigures

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.historicalfigures.databinding.ItemHistoricalFigureBinding

class HistoricalFigureAdapter : ListAdapter<HistoricalFigure, HistoricalFigureAdapter.HistoricalFigureViewHolder>(HistoricalFigureDiffCallback) {

    object HistoricalFigureDiffCallback : DiffUtil.ItemCallback<HistoricalFigure>() {
        override fun areItemsTheSame(oldItem: HistoricalFigure, newItem: HistoricalFigure): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: HistoricalFigure, newItem: HistoricalFigure): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricalFigureViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoricalFigureBinding.inflate(layoutInflater, parent, false)
        return HistoricalFigureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoricalFigureViewHolder, position: Int) {
        val historicalFigure = getItem(position)
        holder.bind(historicalFigure)
    }

    class HistoricalFigureViewHolder(private val binding: ItemHistoricalFigureBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(historicalFigure: HistoricalFigure) {
            with(binding) {
                tvHistoricalFigureName.text = historicalFigure.name
                tvBirthdate.text = "Birthdate: ${historicalFigure.info.born ?: "Unknown"}"
                tvDied.text = "Died: ${historicalFigure.info.died ?: "Unknown"}"
                tvOccupation.text = "Occupation: ${historicalFigure.info.occupation?.joinToString(", ") ?: "Unknown"}"
                tvContribution.text = "Contribution: ${historicalFigure.info.notableWork?.joinToString(", ") ?: "Unknown"}"
            }
        }
    }
}
