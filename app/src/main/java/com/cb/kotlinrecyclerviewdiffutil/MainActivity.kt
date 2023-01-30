package com.cb.kotlinrecyclerviewdiffutil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.cb.kotlinrecyclerviewdiffutil.adapters.PersonAdapter
import com.cb.kotlinrecyclerviewdiffutil.databinding.ActivityMainBinding
import com.cb.kotlinrecyclerviewdiffutil.model.Person
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val personAdapter by lazy { PersonAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadItems()
        personAdapter.setItems(persons)

        binding.apply {
            rvMain.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = personAdapter
            }
            fabShuffle.setOnClickListener {
                personAdapter.setItems(persons.shuffled())
            }
        }


    }

    private var persons: MutableList<Person> = mutableListOf()

    private fun loadItems(): MutableList<Person> {

        persons.add(Person(1, "Chirag"))
        persons.add(Person(2, "Prashant"))
        persons.add(Person(3, "Meena"))
        persons.add(Person(4, "Kajal"))
        persons.add(Person(5, "Heer"))
        persons.add(Person(6, "Hirav"))
        persons.add(Person(7, "Mom"))
        persons.add(Person(8, "Dad"))
        persons.add(Person(9, "Hari"))
        persons.add(Person(10, "Bhavesh"))

        return persons

    }
}