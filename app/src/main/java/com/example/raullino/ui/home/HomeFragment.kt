package com.example.raullino.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.raullino.Flags
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

        if(Flags.selectedFlag=="EN"){
            TextView4.text =resources.getString(R.string.o_inicio_EN)
            TextView.text=resources.getString(R.string.as_suas_janelas_s_o_molduras__EN)
            TextView3.text=resources.getString(R.string.destaca_se_por_linhas_verticais__EN)
            TextView2.text=resources.getString(R.string.mestre_da_arquitetura_portuguesa_EN)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}