package com.example.historicalfigures

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.historicalfigures.databinding.ItemCountryBinding


class HistoricalFigureAdapter : ListAdapter<Country, HistoricalFigureAdapter.CountryViewHolder>(CountryDiffCallback) {

    object CountryDiffCallback : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCountryBinding.inflate(layoutInflater, parent, false) // Use ItemCountryBinding here
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val historicalFigure = getItem(position)
        holder.bind(historicalFigure)
    }

    class CountryViewHolder(private val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(country: Country) {
            with(binding) {
                tvCountryName.text = country.name
                tvLifeExpectancyMale.text = "Male Life Expectancy: ${country.life_expectancy_male}"
                tvLifeExpectancyFemale.text = "Female Life Expectancy: ${country.life_expectancy_female}"
            }
        }
    }
}

