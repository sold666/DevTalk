package com.dev_talk.auth

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.dev_talk.R

class ResultAdapter(

    private val context: Context,
    private val dataList: HashMap<String, List<String>>,
    private val titleList: ArrayList<String>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return this.titleList.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return this.dataList[this.titleList[p0]]!!.size
    }

    override fun getGroup(p0: Int): Any {
        return this.titleList[p0]
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return this.dataList[this.titleList[p0]]!![p1]
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        var convertView = p2
        var listTitle = getGroup(p0) as String
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.item_tag_result, null)
        }
        val listTitleTextView = convertView!!.findViewById<TextView>(R.id.tag_name)
        if (isGroupEmpty(p0)) {
            listTitle += " - no tags!"
            listTitleTextView.setTypeface(null, Typeface.BOLD_ITALIC)
            convertView.isEnabled = false
        } else {
            listTitleTextView.setTypeface(null, Typeface.BOLD)
        }
        listTitleTextView.text = listTitle
        return convertView
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        var convertView = p3
        val expandedListText = getChild(p0, p1) as String
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.item_tag_result, null)
        }
        val expandedListTextView = convertView!!.findViewById<TextView>(R.id.tag_name)
        expandedListTextView.text = expandedListText
        return convertView
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return false
    }

    private fun isGroupEmpty(p0: Int): Boolean {
        return getChildrenCount(p0) == 0
    }
}
