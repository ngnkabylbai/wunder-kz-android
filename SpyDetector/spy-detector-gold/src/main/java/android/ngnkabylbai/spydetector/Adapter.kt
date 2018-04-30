package android.ngnkabylbai.spydetector

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by Nurbek Kabylbay on 14.03.2018.
 */
class Adapter(private val context: Context) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var records = ArrayList<Record>()

    fun loadData(list: List<Record>) {
        records = ArrayList(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_record, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val record = records[position]

        holder?.nickTextView?.text = record.name
        holder?.scoreTextView?.text = record.score.toString()

    }

    override fun getItemCount(): Int {
        return records.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nickTextView = itemView.findViewById<TextView>(R.id.nickTextView)
        var scoreTextView = itemView.findViewById<TextView>(R.id.scoreTextView)
    }
}