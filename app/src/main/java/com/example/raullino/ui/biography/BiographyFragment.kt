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
import com.example.raullino.R
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
            TextView.text=resources.getString(R.string.nb_biografia_EN)
            val htmlText = "<b>Full name:</b><br/>Raul Lino da Silva<br/><b>Birth:</b> November 21, 1879, Lisbon<br/><b>Death:</b><br/>July 13, 1974 (94 years old), Lisbon"
            TextView3.text = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
            TextView2.text=resources.getString(R.string.nb_titulo_EN);
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}