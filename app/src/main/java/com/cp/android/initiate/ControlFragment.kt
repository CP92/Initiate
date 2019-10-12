package com.cp.android.initiate

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import kotlinx.android.synthetic.main.control_fragment.*

class ControlFragment : Fragment() {

    private lateinit var listener: OnControls

    companion object {
        var round = 0
        fun newInstance() = ControlFragment()
        fun incrementRound() {
            round++
        }
        fun decrementRound() {
            round--
        }

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

        val view = inflater.inflate(R.layout.control_fragment, container, false)

        val roundButton = view.findViewById<Button>(R.id.roundButton)

        if (round != 0) {
            roundButton.text = "ROUND: " + round.toString()
        }

        return view
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
        roundButton.setOnClickListener {
            if (CreatureListFragment.creatures.isNotEmpty() && round < 1) {
                listener.onStartRound()
            } else {

            }
        }

        forwardButton.setOnClickListener {
            if (round > 0) {
                listener.onMoveInitiativeForward()
            }
        }

        backButton.setOnClickListener {
            if (round != 0) {
                listener.onMoveInitiativeBack()
            }
        }
    }

    interface OnControls{
        fun onAddCreature()
        fun onStartRound()
        fun onMoveInitiativeForward()
        fun onMoveInitiativeBack()
    }

}
