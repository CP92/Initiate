package com.cp.android.initiate

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CreatureListFragment.onCreatureSelected, CreatureDetailsFragment.OnCreatureAction, ControlFragment.OnControls {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val controlsFragment =
            ControlFragment.newInstance()
        supportFragmentManager
            .beginTransaction()

            .add(R.id.root_layout, controlsFragment, "control")

            .addToBackStack(null)
            .commit()

    }

    override fun onCreatureDelete(id: String) {


        val controlsFragment =
            ControlFragment.newInstance()
        val listFragment = CreatureListFragment.newInstance("", "", id.toInt())
        supportFragmentManager
            .beginTransaction()

            .replace(R.id.root_layout, listFragment, "creaturelist")

            .add(R.id.root_layout, controlsFragment, "control")
            .addToBackStack(null)
            .commit()

    }

    override fun onCreatureSave(name: String, initiative: String, id: String) {
        val controlsFragment =
            ControlFragment.newInstance()
        val listFragment = CreatureListFragment.newInstance(name, initiative, id.toInt())
        supportFragmentManager
            .beginTransaction()
            // 2
            .replace(R.id.root_layout, listFragment, "creaturelist")
            .add(R.id.root_layout, controlsFragment, "control")
            .addToBackStack(null)
            .commit()
    }

    override fun onCreatureSelected(creatureModel: CreatureModel) {
        val detailsFragment =
            CreatureDetailsFragment.newInstance(creatureModel)
        val controlsFragment =
            ControlFragment.newInstance()
        supportFragmentManager
            .beginTransaction()

            .replace(R.id.root_layout, detailsFragment, "creatureDetails")
            .remove(controlsFragment)


            .addToBackStack(null)
            .commit()
    }

    override fun onAddCreature() {
        val detailsFragment = CreatureDetailsFragment.newInstance(CreatureModel("", "", 0))
        val controlsFragment =
            ControlFragment.newInstance()
        supportFragmentManager
            .beginTransaction()

            .replace(R.id.root_layout, detailsFragment, "creatureDetails")
            .remove(controlsFragment)


            .addToBackStack(null)
            .commit()
    }

}
