package app.ipt.raullino.ui.buildingDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import app.ipt.raullino.JsonParse
import app.ipt.raullino.databinding.FragmentBuildingDetailBinding

class BuildingDetailFragment : Fragment() {

    private var _binding: FragmentBuildingDetailBinding? = null
    lateinit var textTitle: TextView
    lateinit var textYear: TextView
    lateinit var textLocal: TextView
    lateinit var textType: TextView
    lateinit var textInfo: TextView
    private lateinit var imageViewPagerAdapter: ImageViewPagerAdapter
    val imageDrawableList = ArrayList<Int>()

    companion object {
        fun newInstance(id_edificio: String): BuildingDetailFragment {
            val fragment = BuildingDetailFragment()
            val args = Bundle()
            args.putString("id_edificio", id_edificio)
            fragment.arguments = args
            return fragment
        }
    }

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Volta diretamente ao fragmento do mapa
                parentFragmentManager.popBackStack("mapFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


        // Id do Edificio
        var buildingId = arguments?.getString("id_edificio") as String

        _binding = FragmentBuildingDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val jsonParse = JsonParse(requireContext())

        textTitle = binding.textTitle
        textYear = binding.textYear
        textLocal = binding.textLocal
        textType = binding.textType
        textInfo = binding.textInfo

        textTitle.text = jsonParse.get_title(buildingId)
        textYear.text = jsonParse.get_year(buildingId)
        textLocal.text = jsonParse.get_location(buildingId)
        textType.text = jsonParse.get_typology(buildingId)
        textInfo.text = jsonParse.get_info(buildingId)

        var imagesArray = jsonParse.get_image(buildingId)

        for (image in imagesArray) {
            var imagePath = image.split("/").toTypedArray()  //array do path da imagem
            // tamanho do array
            var Arraynum = imagePath.size
            // dividir o nome da imagem em 2 (Ex:['sam_7432','jpg'])
            var image = imagePath[Arraynum - 1].split(".").toTypedArray()
            // tratamento do nome da imagem
            var imageName =
                image[0].toLowerCase().replace("-", "_").replace(" ", "_").replace("(", "_")
                    .replace(")", "_")
            val drawableName = imageName
            val packageName = requireContext().packageName
            val resourceId =
                requireContext().resources.getIdentifier(drawableName, "drawable", packageName)
            imageDrawableList.add(resourceId)
        }

        // initializing the adapter
        imageViewPagerAdapter = ImageViewPagerAdapter(imageDrawableList)

        setUpViewPager()

        return root
    }

    private fun setUpViewPager() {
        binding.viewPager.adapter = imageViewPagerAdapter
        // set the orientation of the viewpager using ViewPager2.orientation
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        // select any page you want as your starting page
        val currentPageIndex = 0
        binding.viewPager.currentItem = currentPageIndex

        // registering for page change callback
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    //update the image number textview
                    binding.buildingImage.text = "${position + 1} / ${imageDrawableList.size}"
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        // unregistering the onPageChangedCallback
        binding.viewPager.unregisterOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {}
        )
    }
}