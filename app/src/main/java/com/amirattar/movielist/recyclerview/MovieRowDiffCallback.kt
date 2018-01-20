package com.amirattar.movielist.recyclerview

import android.support.v7.util.DiffUtil
import com.amirattar.movielist.Movie

/**
 * Created by pantos27 on 08/12/17.
 */
class MovieRowDiffCallback(private val newRows : List<Movie>, private val oldRows : List<Movie>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = true

    override fun getOldListSize() = oldRows.size

    override fun getNewListSize() = newRows.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldRows[oldItemPosition].id==newRows[newItemPosition].id

}