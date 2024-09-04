package com.glucode.about_you.engineers

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.glucode.about_you.R
import com.glucode.about_you.databinding.FragmentEngineersBinding
import com.glucode.about_you.engineers.models.Engineer
import com.glucode.about_you.mockdata.MockData

class EngineersFragment : Fragment() {
    private lateinit var binding: FragmentEngineersBinding
    private var engineers: List<Engineer> = MockData.engineers
    private lateinit var adapter: EngineersRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEngineersBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setUpEngineersList(MockData.engineers) //engineers list
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_engineers, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_years -> {
                sortEngineersByYears()
                true
            }
            R.id.action_coffees -> {
                sortEngineersByCoffees()
                true
            }
            R.id.action_bugs -> {
                sortEngineersByBugs()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    // allows sorting to happen based on engineer attributes
    private fun sortEngineersByName() {
        engineers = engineers.sortedBy { it.name }
        updateEngineersList()
    }

    private fun sortEngineersByYears() {
        engineers = engineers.sortedBy { it.quickStats.years }
        updateEngineersList()
    }

    private fun sortEngineersByCoffees() {
        engineers = engineers.sortedBy { it.quickStats.coffees }
        updateEngineersList()
    }

    private fun sortEngineersByBugs() {
        engineers = engineers.sortedBy { it.quickStats.bugs }
        updateEngineersList()
    }

    private fun updateEngineersList() {
        adapter = EngineersRecyclerViewAdapter(engineers) {
            goToAbout(it)
        }
        binding.list.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun setUpEngineersList(engineers: List<Engineer>) {
        binding.list.adapter = EngineersRecyclerViewAdapter(engineers) {
            goToAbout(it)
        }
        val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(dividerItemDecoration)
    }

    private fun goToAbout(engineer: Engineer) {
        val bundle = Bundle().apply {
            putString("name", engineer.name)
            putString("role", engineer.role) // add role here to pass bundle to about and display the role
        }
        findNavController().navigate(R.id.action_engineersFragment_to_aboutFragment, bundle)
    }
}