package com.cp.android.initiate

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CreatureListFragment.onCreatureSelected, CreatureDetailsFragment.OnCreatureAction {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addCreatureButton.setOnClickListener {
            val forward = findViewById<Button>(R.id.forwardButton)
            forward.visibility = View.GONE
            val detailsFragment =
                CreatureDetailsFragment.newInstance(CreatureModel("", "", 0))
            supportFragmentManager
                .beginTransaction()

                .replace(R.id.root_layout, detailsFragment, "creatureDetails")

                .addToBackStack(null)
                .commit()
        }
    }

    override fun onCreatureDelete(id: String) {


        val listFragment = CreatureListFragment.newInstance("", "", id.toInt())
        supportFragmentManager
            .beginTransaction()

            .replace(R.id.root_layout, listFragment, "creaturelist")

            .addToBackStack(null)
            .commit()

    }

    override fun onCreatureSave(name: String, initiative: String, id: String) {
        val listFragment = CreatureListFragment.newInstance(name, initiative, id.toInt())
        supportFragmentManager
            .beginTransaction()
            // 2
            .replace(R.id.root_layout, listFragment, "creaturelist")
            // 3
            .addToBackStack(null)
            .commit()
    }

    override fun onCreatureSelected(creatureModel: CreatureModel) {
        val forward = findViewById<Button>(R.id.forwardButton)
        forward.visibility = View.GONE

        val detailsFragment =
            CreatureDetailsFragment.newInstance(creatureModel)
        supportFragmentManager
            .beginTransaction()

            .replace(R.id.root_layout, detailsFragment, "creatureDetails")

            .addToBackStack(null)
            .commit()
    }

}
