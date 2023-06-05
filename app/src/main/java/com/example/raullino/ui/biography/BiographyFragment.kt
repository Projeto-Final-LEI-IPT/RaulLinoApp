package com.example.raullino.ui.biography

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.raullino.Flags
import com.example.raullino.databinding.FragmentBiographyBinding

class BiographyFragment : Fragment() {

    private var _binding: FragmentBiographyBinding? = null
    val flag: Boolean = true
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBiographyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val TextView = binding.biografia
        val TextView2 = binding.titulo
        val TextView3 = binding.dados

        if(Flags.selectedFlag=="EN"){
            TextView.text="He was a unique personality on the Portuguese art scene, largely due to the fact that he managed to articulate the Portuguese tradition with the innovative European currents of the early 20th century. [2] With 70 years of professional activity, Lino is the author of more than 700 works. It is also important to mention that despite his range of projects, he was also a man with a vast theoretical or written work, which became very determinant, for his followers over decades in Portugal."
            val htmlText = "<b>Full name:</b><br/>Raul Lino da Silva<br/><b>Birth:</b> November 21, 1879, Lisbon<br/><b>Death:</b><br/>July 13, 1974 (94 years old), Lisbon"
            TextView3.text = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
            TextView2.text="Biographical note";
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}