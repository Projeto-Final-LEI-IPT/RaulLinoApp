package app.ipt.raullino.ui.biography

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.ipt.raullino.Flags
import app.ipt.raullino.R
import app.ipt.raullino.databinding.FragmentBiographyBinding

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
        val TextView_2 = binding.biografia2
        val TextView2 = binding.titulo

        if(Flags.selectedFlag=="EN"){
            TextView.text=resources.getString(R.string.nb_biografia1_EN)
            TextView_2.text=resources.getString(R.string.nb_biografia2_EN)
            TextView2.text=resources.getString(R.string.nb_titulo_EN);
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}