package com.example.raullino.ui.buildingDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.raullino.R
import com.example.raullino.databinding.FragmentBuildingDetailBinding
import com.example.raullino.jsonParse

class BuildingDetailFragment : Fragment() {

    private var _binding: FragmentBuildingDetailBinding? = null

    lateinit var textTitle: TextView
    lateinit var textInfo: TextView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val buildingDetailViewModel =
                ViewModelProvider(this).get(BuildingDetailViewModel::class.java)

        val t = jsonParse(requireContext())

        t.get_title(1);

        _binding = FragmentBuildingDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}