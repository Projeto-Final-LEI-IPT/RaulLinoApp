package app.ipt.raullino.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.ipt.raullino.Flags
import app.ipt.raullino.R
import app.ipt.raullino.databinding.FragmentHomeBinding

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

        val textView3 = binding.textView3
        val textView4 = binding.textView4
        val textView5 = binding.textView5
        if(Flags.selectedFlag=="EN"){
            textView3.text = resources.getString(R.string.esta_app_EN)
            textView4.text = resources.getString(R.string.hyperlink_EN)
            textView5.text = resources.getString(R.string.este_projeto_EN)
        }
        textView4.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.techneart.ipt.pt/raulino"))
            startActivity(browserIntent)
        }
        /* Código antigo
        val TextView4 = binding.textView4
        val TextView3 = binding.textView3
        val TextView2 = binding.textView2
        val TextView = binding.textView

        if(Flags.selectedFlag=="EN"){
            TextView4.text =resources.getString(R.string.o_inicio_EN)
            TextView.text=resources.getString(R.string.as_suas_janelas_s_o_molduras__EN)
            TextView3.text=resources.getString(R.string.destaca_se_por_linhas_verticais__EN)
            TextView2.text=resources.getString(R.string.mestre_da_arquitetura_portuguesa_EN)
        }*/

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}