package com.example.historicalfigures

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class MonthAdapter(private val context: Context, private val monthsGone: Int, private val totalMonths: Int) :
    RecyclerView.Adapter<MonthAdapter.MonthViewHolder>() {
    class MonthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val monthView: View = itemView.findViewById(R.id.monthView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_month, parent, false)
        return MonthViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        val monthView = holder.monthView
        if (position < monthsGone) {
            monthView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray))
        } else {
            monthView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
        }
        // Adding margin after every 4th element in each row (except the last row)
        val layoutParams =  monthView.layoutParams as ViewGroup.MarginLayoutParams
            if (position % 12 >= 4 && position % 12 < 8) {
                layoutParams.bottomMargin = 16 // Adding bottom margin after every 4th square
            } else {
                layoutParams.bottomMargin = 0 // Reset margin for other squares
            }
            monthView.layoutParams = layoutParams
        }
        override fun getItemCount(): Int {
            return totalMonths
        }

    }

