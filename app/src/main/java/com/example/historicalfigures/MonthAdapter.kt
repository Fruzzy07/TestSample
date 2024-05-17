package com.example.historicalfigures

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.activity.result.ActivityResultLauncher

class MonthAdapter(
    private val context: Context,
    private val monthsGone: Int,
    private val totalMonths: Int,
    private val detailActivityLauncher: ActivityResultLauncher<Intent>
) : RecyclerView.Adapter<MonthAdapter.MonthViewHolder>() {
    private val monthStatuses = Array(totalMonths) { "DEFAULT" }
    class MonthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val monthView: View = itemView.findViewById(R.id.monthView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_month, parent, false)
        return MonthViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {

        holder.itemView.setBackgroundColor(getColorForStatus(monthStatuses[position]))

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("MONTH_INDEX", position)
            detailActivityLauncher.launch(intent)
        }

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

    fun updateMonthStatus(monthIndex: Int, status: String) {
        monthStatuses[monthIndex] = status
        notifyItemChanged(monthIndex)
    }

    private fun getColorForStatus(status: String): Int {
        return when (status) {
            "GOOD" -> Color.GREEN
            "OK" -> Color.YELLOW
            "BAD" -> Color.RED
            else -> Color.GRAY
        }
    }


}



