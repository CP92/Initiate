/*
 * Copyright (c) 2019 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.cp.android.initiate

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cp.android.initiate.databinding.RecyclerItemCreatureModelBinding
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager


class CreatureListFragment : Fragment() {

  private lateinit var listener: onCreatureSelected
  private lateinit var creatureList: MutableList<CreatureModel>

  companion object {

    val creatures = mutableMapOf<Int, CreatureModel>()
    fun newInstance(newCreatures: Array<CreatureModel>): CreatureListFragment {

      for (creature in newCreatures) {
      if (creature.id != 0) {
        if (creature.name == "") {
          creatures.remove(creature.id)

        }
        creatures[creature.id] = creature

      } else {
        var idSetSuccess = false
        var iD = creatures.size + 1
        while (idSetSuccess == false) {
          if (creatures.containsKey(iD)) {
            iD++
          } else {
            idSetSuccess = true
          }
        }
        creature.id = iD
        creatures[iD] = creature
      }
      }
      val args = Bundle()
      val fragment = CreatureListFragment()
      fragment.arguments = args
      return fragment

    }
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is onCreatureSelected) {
      listener = context
    } else {
      throw ClassCastException(
        context.toString() + " must implement OnCreatureSelected.")
    }
    val ids = creatures.keys

    val creatureMaps = mutableListOf<CreatureModel>()

    for (id in ids) {
      val creature = creatures[id]

      creatureMaps.add(creature!!)
    }

    creatureMaps.sortByDescending { it.initiative.toInt() }

    creatureList = creatureMaps
  }

  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view: View = inflater.inflate(R.layout.fragment_creature_list, container,
      false)
    val activity = activity as Context
    val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
    recyclerView.layoutManager = LinearLayoutManager(activity)
    recyclerView.adapter = CreatureListAdapter(activity)
    return view
  }

  internal inner class CreatureListAdapter(context: Context) : RecyclerView.Adapter<ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
      val recyclerCreatureModelBinding =
          RecyclerItemCreatureModelBinding.inflate(layoutInflater, viewGroup, false)
      return ViewHolder(recyclerCreatureModelBinding.root, recyclerCreatureModelBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
      val creature = creatureList[position]
      viewHolder.setData(creature)
      viewHolder.itemView.setOnClickListener {
        listener.onCreatureSelected(creature)
      }
    }

    override fun getItemCount() = creatureList.size
  }

  internal inner class ViewHolder constructor(itemView: View,
                                              private val recyclerItemCreatureListBinding:
                                              RecyclerItemCreatureModelBinding
  ) :
      RecyclerView.ViewHolder(itemView) {

    fun setData(creatureModel: CreatureModel) {
      recyclerItemCreatureListBinding.creatureModel = creatureModel
    }
  }

  fun deleteCreature(id: Int) {
    creatures.remove(id)
  }

  interface onCreatureSelected {
    fun onCreatureSelected(creatureModel: CreatureModel)
  }

}
