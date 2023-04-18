package com.example.raullino.ui.buildingDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.raullino.JsonParse
import com.example.raullino.databinding.FragmentBuildingDetailBinding

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

        _binding = FragmentBuildingDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val jsonParse = JsonParse(requireContext())

        textTitle = binding.textTitle
        textInfo = binding.textInfo

        textTitle.text = jsonParse.get_title(3);
        textInfo.text = jsonParse.get_info(3);

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}