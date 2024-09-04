package com.glucode.about_you.engineers

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
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
                showToast("filtered by years")
                true
            }
            R.id.action_coffees -> {
                sortEngineersByCoffees()
                showToast("filtered by coffee")
                true
            }
            R.id.action_bugs -> {
                sortEngineersByBugs()
                showToast("filtered by bugs")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showToast( message:String ){
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
    }

    // allows sorting to happen based on engineer attributes
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
    //updates the engineer list when change happens
    @SuppressLint("NotifyDataSetChanged")
    private fun updateEngineersList() {
        adapter = EngineersRecyclerViewAdapter(engineers) {
            goToAbout(it)
        }
        binding.list.adapter = adapter
        adapter.notifyDataSetChanged()
    }
   //Adapter Setup: Configures the RecyclerView with an adapter that displays the list of engineers and handles item clicks.
    private fun setUpEngineersList(engineers: List<Engineer>) {
        binding.list.adapter = EngineersRecyclerViewAdapter(engineers) {
            goToAbout(it)
        }
        val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(dividerItemDecoration)
    }
    // binds data to the profile fot update engineer info
    private fun goToAbout(engineer: Engineer) {
        val bundle = Bundle().apply {
            putString("name", engineer.name)
            putString("role", engineer.role) // add role here to pass bundle to about and display the role
        }
        findNavController().navigate(R.id.action_engineersFragment_to_aboutFragment, bundle)
    }
}