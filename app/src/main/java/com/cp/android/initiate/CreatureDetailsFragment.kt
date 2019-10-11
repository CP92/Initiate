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
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.cp.android.initiate.databinding.FragmentCreatureDetailsBinding
import kotlinx.android.synthetic.main.fragment_creature_details.*

class CreatureDetailsFragment : Fragment() {

  private lateinit var listener: OnCreatureAction

  companion object {
    private const val CREATUREMODEL = "model"
    fun newInstance(creatureModel: CreatureModel): CreatureDetailsFragment{
      val args = Bundle()
      args.putSerializable(CREATUREMODEL, creatureModel)
      val fragment = CreatureDetailsFragment()
      fragment.arguments = args
      return fragment
    }
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)

    if (context is OnCreatureAction) {
      listener = context
    } else {
      throw ClassCastException(
        context.toString() + " must implement OnCreatureSelected.")
    }
  }

  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val fragmentCreatureDetailsBinding =
      FragmentCreatureDetailsBinding.inflate(inflater, container, false)
    val model = arguments!!.getSerializable(CREATUREMODEL) as CreatureModel
    fragmentCreatureDetailsBinding.creatureModel = model
    return fragmentCreatureDetailsBinding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    saveButton.setOnClickListener {
      val initiativeBox = view.findViewById<EditText>(R.id.initiative)
      val initiative = initiativeBox.text.toString()
      val nameBox = view.findViewById<EditText>(R.id.name)
      val name = nameBox.text.toString()
      val id = nameBox.tag.toString()
      listener.onCreatureSave(name, initiative, id)
    }
    deleteCreature.setOnClickListener {
      val nameBox = view.findViewById<EditText>(R.id.name)
      val id = nameBox.tag.toString()
      listener.onCreatureDelete(id)
    }

  }

  interface OnCreatureAction{
    fun onCreatureSave(name: String, initiative: String, id: String)
    fun onCreatureDelete(id: String)
  }
}
