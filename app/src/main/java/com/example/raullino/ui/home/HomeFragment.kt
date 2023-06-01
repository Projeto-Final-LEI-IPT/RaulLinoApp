package com.example.raullino.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.raullino.R
import com.example.raullino.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val flag: Boolean = true
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val TextView4 = binding.textView4
        val TextView3 = binding.textView3
        val TextView2 = binding.textView2
        val TextView = binding.textView
        val fw =he


        if(flag){
            TextView4.text = "The begining"
            TextView.text="A master of Portuguese architecture, he imprints life in iconic arches. Graceful and solid arches transcend time, revealing a singular beauty."
            TextView3.text="It stands out for its imposing vertical lines. Magnificent columns that convey grandeur and sophistication in their timeless designs";
            TextView2.text="Its windows are frames for discovery. They reveal breathtaking landscapes, inviting you to explore the magic beyond their openings.";
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}