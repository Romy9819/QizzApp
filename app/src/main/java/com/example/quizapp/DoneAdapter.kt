package com.example.quizapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class DoneAdapter(private val context: Context,
                  private val info: Models.DoneFeed) : BaseAdapter() {
    override fun getCount(): Int {
        return 1
    }

    override fun getItem(position: Int): Any {
        return position.toLong()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val statRow: View = layoutInflater.inflate(R.layout.donelist, viewGroup, false)

        statRow.findViewById<TextView>(R.id.q_number).text = "Attempted Questions: ${info.qNumber}"

        return statRow
    }

}