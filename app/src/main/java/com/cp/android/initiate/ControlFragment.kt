package com.cp.android.initiate

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.control_fragment.*

class ControlFragment : Fragment() {

    private lateinit var listener: OnControls

    companion object {
        fun newInstance() = ControlFragment()
    }

    private lateinit var viewModel: ControlViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnControls) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.control_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ControlViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addCreatureButton.setOnClickListener {
            listener.onAddCreature()
        }
    }

    interface OnControls{
        fun onAddCreature()
    }

}
