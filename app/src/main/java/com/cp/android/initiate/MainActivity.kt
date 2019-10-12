package com.cp.android.initiate

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity(), CreatureListFragment.onCreatureSelected, CreatureDetailsFragment.OnCreatureAction, ControlFragment.OnControls {

    private var roundInProgress = false

    private var currentCreature = arrayOf<CreatureModel>()

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

    override fun onCreatureDelete(creature: CreatureModel) {
        addToListFragment(arrayOf(creature))

    }

    override fun onCreatureSave(creature: CreatureModel) {
        addToListFragment(arrayOf(creature))
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
        val detailsFragment = CreatureDetailsFragment.newInstance(CreatureModel("", "", 0, false))
        val controlsFragment =
            ControlFragment.newInstance()
        supportFragmentManager
            .beginTransaction()

            .replace(R.id.root_layout, detailsFragment, "creatureDetails")
            .remove(controlsFragment)


            .addToBackStack(null)
            .commit()
    }

    override fun onStartRound() {
        roundInProgress = true

        startInitiative()
    }

    fun addToListFragment(newCreatures: Array<CreatureModel>) {
        val controlsFragment =
            ControlFragment.newInstance()
        val listFragment = CreatureListFragment.newInstance(newCreatures)
        supportFragmentManager
            .beginTransaction()
            // 2
            .replace(R.id.root_layout, listFragment, "creaturelist")
            .remove(controlsFragment)
            .add(R.id.root_layout, controlsFragment, "control")

            .addToBackStack(null)
            .commit()
    }

    override fun onMoveInitiativeForward() {
        val creatureList = getSortedCreatureList()
        val creatures = creatureList.map { it.second }
        val indexOf = creatures.indexOf(currentCreature[0])
        val previousCreature = creatures.get(indexOf)
        val creature: CreatureModel
        if (indexOf == creatures.size - 1) {
            creature = creatures.get(0)
            ControlFragment.incrementRound()
        } else {
            creature = creatures.get(indexOf + 1)
        }

        creature.turn = true
        previousCreature.turn = false
        setCurrentCreature(creature)
        addToListFragment(arrayOf(creature, previousCreature))
    }

    override fun onMoveInitiativeBack() {
        val creatureList = getSortedCreatureList()
        val creatures = creatureList.map { it.second }
        val indexOf = creatures.indexOf(currentCreature[0])
        val previousCreature = creatures.get(indexOf)
        val creature: CreatureModel
        if (indexOf == 0) {
            val control = ControlFragment
            creature = creatures.get(creatures.size - 1)
            if (control.round == 1) {
                creature.turn = false
            } else {
                creature.turn = true

            }
            control.decrementRound()
        } else {
            creature = creatures.get(indexOf - 1)
            creature.turn = true
        }

        previousCreature.turn = false
        setCurrentCreature(creature)
        addToListFragment(arrayOf(creature, previousCreature))
    }

    fun getSortedCreatureList(): List<Pair<Int, CreatureModel>> {

        return CreatureListFragment.creatures.toList().sortedByDescending { it.second.initiative.toInt()}
    }

    fun startInitiative() {
        val creatureSet = getSortedCreatureList()
        val creature = creatureSet[0].second
        creature.turn = true
        ControlFragment.incrementRound()
        setCurrentCreature(creature)
        addToListFragment(arrayOf(creature))
    }

    fun setCurrentCreature(creature: CreatureModel) {
        currentCreature = arrayOf(creature)
    }

}
